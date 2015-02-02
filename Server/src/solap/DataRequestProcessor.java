package solap;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.Vector;
import solap.entities.SOLAPAttribute;
import solap.entities.SOLAPDimensionLevelPair;
import solap.params.SOLAPAttributeParams;
import solap.params.SOLAPParamsObject;
import solap.params.SOLAPSliceParams;
import solap.utils.AttributesUtils;
import solap.utils.MappingUtils;
import solap.tablegenerator.OneSpatialGenerator;
import solap.tablegenerator.TwoSpatialGenerator;
import solap.tablegenerator.SpatialIntersectionGenerator;
import solap.tablegenerator.TableGenerator;
import java.util.List;
import java.util.LinkedList;

import java.util.Map;

import java.util.Scanner;

import oracle.spatial.geometry.JGeometry;

import org.w3c.dom.Document;

import solap.entities.SOLAPFactTable;
import solap.entities.SumarizationAttribute;

import solap.entities.SumarizationMeasure;

import solap.params.SOLAPFieldFilterParams;
import solap.params.SOLAPMeasureParams;

import solap.params.SOLAPSpatialSliceParams;

import solap.sqlgenerator.IQuery;
import solap.sqlgenerator.Query;

import solap.tablegenerator.SumarizationGenerator;


public class DataRequestProcessor {
    private boolean usingAggregate = false;
    private String factTableName;
    
    // Auxiliary flags to know which temp table use if is the case
    private boolean oneTempTable = false;
    private boolean twoTempTable = false;
    private boolean spatialIntersectTempTable = false;
    
    //attributes by type of level (relative to the lower spatial attribute) (vectors have the attributes' ids)
    private Vector<String> differentDimension = new Vector<String>();
    private Vector<String> differentHierarchy = new Vector<String>();
    private Vector<String> lowerLevel = new Vector<String>();
    private Vector<String> higherLevel = new Vector<String>();
    private Vector<String> sameLevel = new Vector<String>();
    
    //semantic associated attributes
    private Vector<SOLAPAttributeParams> spatialAttr = new Vector<SOLAPAttributeParams>();
    
    private int numberOfMeasures;
    private List<String> geometryType;
    private MappingUtils mapUtils;
    private SOLAPParamsObject params;
    private TableGenerator tableGenerator;
    private boolean clustering;
    
    private IQuery query;
    
    //Construtor to use by Sumarization
    public DataRequestProcessor (SOLAPParamsObject params, String type){
        this.params = params;
        this.mapUtils = new MappingUtils(params.getFilename(), params.getCubeId());
        try{
            if(params.getFactTables().isEmpty() && !params.getMeasuresToGeneralize().isEmpty())
                query = new Query(mapUtils, mapUtils.getFactTableName(params.getCubeId()));
            else
                query = new Query(mapUtils);
            if(type.equals("focus")){
                for(SOLAPFactTable ft : params.getFactTables())
                    query.addFactTable(ft);
                for(SumarizationAttribute a : params.getAttributesToGeneralize())
                    query.addSummarizationAttribute(a);
                for(SumarizationMeasure m : params.getMeasuresToGeneralize())
                    query.addSummarizationMeasure(m);
            }
            //By query
            else{
            
            }
            for(SOLAPSliceParams slice: params.getSliceParams())
                query.addSlice(slice);
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error in data request processor construtor for sumarization: " + e.getMessage());
       }
    }
    
    //Construtor use by default
    public DataRequestProcessor (SOLAPParamsObject params, boolean clustering) {
        this.clustering = clustering;
        this.geometryType = new LinkedList<String>();
        this.numberOfMeasures = params.getMeasureParams().size();
        this.params = params;
        this.mapUtils = new MappingUtils(params.getFilename(), params.getCubeId());
        this.spatialAttr = new Vector<SOLAPAttributeParams>();
        
         
        //***** select best aggregate *****
        //levels referenced by the query (to be used when selecting aggregates)
        //these are the levels referenced by <level>, <attribute>, <slice> and <spatialslice>
        Vector<SOLAPDimensionLevelPair> referencedLevels = new Vector<SOLAPDimensionLevelPair>();
        
        //get referenced levels
        referencedLevels = getReferencedLevels(params);
        
        //select the best aggregate - related levels to process are in 'referencedLevels'
        chooseAggregate(referencedLevels, params);
        //***** end select best aggregate *****
        
        try{
            query = new Query(mapUtils);
            for(SOLAPMeasureParams measure: params.getMeasureParams()) query.addMeasure(measure, factTableName);
            query.addLevels(params.getLevelParams());
            
            for(SOLAPAttributeParams attr: params.getAttributeParams()) query.addAttribute(attr);
            for(SOLAPSliceParams slice: params.getSliceParams()) query.addSlice(slice);
            for(SOLAPSpatialSliceParams spatialSlice: params.getSpatialSliceParams()) query.addSpatialSlice(spatialSlice);
            for(SOLAPFieldFilterParams fieldFilter: params.getFieldFilterParams()) query.addMeasurePreFilter(fieldFilter);
            
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error in data request processor construtor: " + e.getMessage());
        }
        
        fillSpatialAttr(params);
        sortAttributesByTypeOfLevel(params);
    }
    
    //Construtor to use by distinct request
    public DataRequestProcessor (SOLAPParamsObject params) {
        this.mapUtils = new MappingUtils(params.getFilename(), params.getCubeId());
        this.params = params;
        this.factTableName = "";
        
        try{
            query = new Query(mapUtils, mapUtils.getFactTableName(params.getCubeId()));
                    
            String attrId = params.getDistinctParams().firstElement().getAttributeId();
            String levelId = params.getDistinctParams().firstElement().getLevelId();
            String dimensionId = params.getDistinctParams().firstElement().getDimensionId();
            
            query.addAttribute(new SOLAPAttributeParams(attrId, levelId, dimensionId));
            
            for(SOLAPSliceParams slice: params.getSliceParams()) query.addSlice(slice);
            for(SOLAPSpatialSliceParams spatialSlice: params.getSpatialSliceParams()) query.addSpatialSlice(spatialSlice);
            for(SOLAPFieldFilterParams fieldFilter: params.getFieldFilterParams()) query.addMeasurePreFilter(fieldFilter);
            
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error in data request processor construtor: " + e.getMessage());
        }
    }

    //Build query for Summarization
    public String buildFocusSumarizationQuery() {
        String result = "";
        if(params.getGeneralizationParams().getDistinct().equals("Yes"))
            result = query.buildFocusDistinctSummarizationQuery();
        else
            result = query.buildFocusSummarizationQuery();
        return result;
    }
    
    public String getQueryFromFile(String filename){
        String result = "";
        try {
            FileReader fin;
            fin = new FileReader(filename);
            Scanner scan = new Scanner(fin);
            result = scan.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
        }
        System.out.println("Extrai esta query do ficheiro:\n" + result);
        return result;
    }

    public String buildQuery() {        
        String result = query.buildSemanticQuery();
        System.out.println("QUERY SEMANTICA: " + result);
        return result;
    }
    
    public String buildDistinctQuery() {
        String result = "";
        System.out.println("ANTS DE GERAR A QUERY DISTINCT");
        result = query.buildDistinctQuery();
        System.out.println("DISTINCT QUERY: " + result);
        return result;
    }
    
    public String computeSpatialQueryByIndex(int index , String dimensionId, String levelId) {
        
        return query.buildSpatialQueryByIndex(index, levelId, dimensionId, getHigherAttributes());
    }
    
    public String computeSpatialQuery() {
        return query.buildSpatialQuery();
    }
    
    public String buildSpatialQueryForSumarization(Document xmlData, int numberOfMeasures, Vector<String> inlineAttr, Vector<String> measureHierarchies, Vector<SOLAPAttributeParams> spatialAttr, String type, int numberOfGroups, Map<String, JGeometry> createdGroups){
        System.out.println("Obter a query espacial para o processo de sumarizacao");
        String result = "";
        tableGenerator = new SumarizationGenerator("SUMARIZATION_TABLE", numberOfMeasures, inlineAttr, measureHierarchies, xmlData, mapUtils, spatialAttr, this, params.getGeneralizationParams().getCharMeasures(), type, numberOfGroups, createdGroups);
        System.out.println("PASSEI PELO CONSTRUTOR");
        tableGenerator.generateTable();
        System.out.println("ACABEI DE GERAR A QUERY");
        result = tableGenerator.getQuery();
        return result;
    }
    
    public List<String> buildSpatialQuery() {
       List<String> result = new LinkedList<String>();
        
       // Identify case of 2 spatial attributes from diferent dimensions
       if (twoTempTable) {
            String geometryType1 = getGeometrySpatialByIndex(0);
            String geometryType2 = getGeometrySpatialByIndex(1);
            
            this.geometryType = new LinkedList<String>();
            this.geometryType.add("line");
            this.geometryType.add("point");
            this.geometryType.add("point");
            
            TwoSpatialGenerator.preProcessSpatialQuery(geometryType1, geometryType2, this);
            tableGenerator = new TwoSpatialGenerator(this, geometryType1, geometryType2, clustering);
            tableGenerator.generateTable();
         
            result.add(tableGenerator.getQuery());
            result.add( ((TwoSpatialGenerator)tableGenerator).getQueryForFirstDimensionSObjects() );
            result.add( ((TwoSpatialGenerator)tableGenerator).getQueryForSecondDimensionSObjects() );
        
            return result;
       }
       
       // Apply TempTableGenerator when is necessary build
       // pivot table to maintain 1:1 relationship with the map or clustering
       else if (oneTempTable || clustering){
            tableGenerator = new OneSpatialGenerator("onespatial", getHeaderAttributes(), 
                numberOfMeasures, higherLevel, buildQuery(), mapUtils, spatialAttr, clustering, this);
            if(clustering) {
                if(!tableGenerator.didClustering() && !oneTempTable) {
                    result.add(computeSpatialQuery());
                    return result;
                }
                
            }

            System.out.println("Poligono temp table: " + tableGenerator.getQuery());                
            tableGenerator.generateTable();
            result.add(tableGenerator.getQuery());
            
            return result;
            
        }
        else if (spatialIntersectTempTable) {
            tableGenerator = new SpatialIntersectionGenerator("spatialintersection", getHeaderAttributes(), 
                numberOfMeasures, higherLevel, buildQuery(), mapUtils, spatialAttr, this, clustering);
            tableGenerator.generateTable();       
            result.add(tableGenerator.getQuery());
            
            return result;
        }
        else {
            result.add(computeSpatialQuery());
            return result;
        }
       
    }
    

    //getters for attributes by type (converted from ID to attribute names)
    public Vector<String> getDifferentDimensionNames() {
        Vector<String> temp = new Vector<String>();
        
        for (int i = 0; i < differentDimension.size(); i++) {
            temp.add(mapUtils.getAttributeName(differentDimension.elementAt(i)));
        }
        
        return temp;
    }
    
    public Vector<String> getDifferentHierarchyNames() {
        Vector<String> temp = new Vector<String>();
        
        for (int i = 0; i < differentHierarchy.size(); i++) {
            temp.add(mapUtils.getAttributeName(differentHierarchy.elementAt(i)));
        }
        
        return temp;
    }
    
    public Vector<String> getLowerLevelNames() {
        Vector<String> temp = new Vector<String>();
        
        for (int i = 0; i < lowerLevel.size(); i++) {
            temp.add(mapUtils.getAttributeName(lowerLevel.elementAt(i)));
        }
        
        return temp;
    }
    
    public Vector<String> getHigherLevelNames() {
        Vector<String> temp = new Vector<String>();
        
        for (int i = 0; i < higherLevel.size(); i++) {
            temp.add(mapUtils.getAttributeName(higherLevel.elementAt(i)));
        }
        
        return temp;
    }
    
    public Vector<String> getSameLevelNames() {
        Vector<String> temp = new Vector<String>();
        
        for (int i = 0; i < sameLevel.size(); i++) {
            temp.add(mapUtils.getAttributeName(sameLevel.elementAt(i)));
        }
        
        return temp;
    }
    
    //puts together all attribute names that go in the headers (used for temporary table creation only)
    public Vector<String> getHeaderAttributes() {
        Vector<String> headers = new Vector<String>();
        
        //different hierarchies
        Vector<String> temp = new Vector<String>();
        temp = getDifferentHierarchyNames();
        for (int i = 0; i < temp.size(); i++) {
            headers.add(temp.elementAt(i));
        }
        
        //lower attributes
        temp = new Vector<String>();
        temp = getLowerLevelNames();
        for (int i = 0; i < temp.size(); i++) {
            headers.add(temp.elementAt(i));
        }
        
        //different dimension
        temp = new Vector<String>();
        temp = getDifferentDimensionNames();
        for (int i = 0; i < temp.size(); i++) {
            headers.add(temp.elementAt(i));
        }
        
        return headers;
    }
    
    public List<String> getGeometryType() {
        return geometryType;
    }
    
    public void centroidObject(int spatialObjectIndex) {
        query.centroidOperation(spatialObjectIndex);
    }
    
    public TableGenerator getTableGenerator() {
        return tableGenerator;
    }

    public SOLAPParamsObject getParams() {
        return params;
    }
    
    public String getNameLevelByIndex(int index) {
        return mapUtils.getNameLevel(params.getLevelParams().get(index).getId());
    }

    public MappingUtils getMapUtils() {
        return mapUtils;
    }
    
    public String getGeometrySpatialByIndex(int index) {
        return query.getColumnTypeSpatial(index);
    }

    public int getNumberOfMeasures() {
        return numberOfMeasures;
    }
    
    public Vector<String> getAssociatedAttributes() {
        Vector<String> result = new Vector<String>();
        for(SOLAPAttributeParams attr: spatialAttr)
            result.add(mapUtils.getAttributeName(mapUtils.getDisplayAttributeIdFromLevel(attr.getLevelId())));
        
        return result;
    }
    
    public Vector<SOLAPAttribute> getAssociatedInfo() {
        Vector<SOLAPAttribute> result = new Vector<SOLAPAttribute>();
        for(SOLAPAttributeParams attr: spatialAttr)
            result.add(new SOLAPAttribute(mapUtils.getDisplayAttributeIdFromLevel(attr.getLevelId()), mapUtils.getAttributeName(mapUtils.getDisplayAttributeIdFromLevel(attr.getLevelId())), attr.getDimensionId(), attr.getLevelId()));
        
        return result;
    }
    
    public Vector<SOLAPAttributeParams> getSpatialAttr() {
        return spatialAttr;
    }
    
    private void fillSpatialAttr(SOLAPParamsObject params) {
        System.out.println("FILL SPATIAL ATTR");
        for (int i = 0; i < params.getLevelParams().size(); i++) {
            String spatialId = mapUtils.getSpatialAttributeIdFromLevel(params.getLevelParams().elementAt(i).getId());
            System.out.println("SPATIAL ID: " + spatialId);
            //is spatial level
            if (!spatialId.equals("")) {
                System.out.println("HERE: ");
                SOLAPAttributeParams newAttribute = new SOLAPAttributeParams(spatialId, params.getLevelParams().elementAt(i).getId(), params.getLevelParams().elementAt(i).getDimensionId()); 
                spatialAttr.add(newAttribute);
                geometryType.add(getGeometrySpatialByIndex(i));
            }
        }
    }
    
    private boolean isAttFromSpatialDimension(SOLAPAttributeParams attr) {
        for(int j = 0; j < spatialAttr.size(); j++) {
            if (attr.getDimensionId().equals(spatialAttr.get(j).getDimensionId()))
                    return true;
        }
        return false;
    }
    
    private Vector<SOLAPDimensionLevelPair> getReferencedLevels(SOLAPParamsObject params) {
        Vector<SOLAPDimensionLevelPair> referencedLevels = new Vector<SOLAPDimensionLevelPair>();
        
        //process <level> elements
        for (int i = 0; i < params.getLevelParams().size(); i++)
            referencedLevels.add(new SOLAPDimensionLevelPair(params.getLevelParams().get(i).getDimensionId(), params.getLevelParams().get(i).getId()));
        //process <attribute> elements
        for (int i = 0; i < params.getAttributeParams().size(); i++)
            referencedLevels.add(new SOLAPDimensionLevelPair(params.getAttributeParams().get(i).getDimensionId(), params.getAttributeParams().get(i).getLevelId()));
        //process <slice> elements
        for (int i = 0; i < params.getSliceParams().size(); i++)
            referencedLevels.add(new SOLAPDimensionLevelPair(params.getSliceParams().get(i).getDimensionId(), params.getSliceParams().get(i).getLevelId()));
        //process <spatialslice> elements
        for (int i = 0; i < params.getSpatialSliceParams().size(); i++)
            referencedLevels.add(new SOLAPDimensionLevelPair(params.getSpatialSliceParams().get(i).getDimensionId(), params.getSpatialSliceParams().get(i).getLevelId()));
        
        return referencedLevels;
    }
    
    private void chooseAggregate(Vector<SOLAPDimensionLevelPair> referencedLevels, SOLAPParamsObject params) {
        //data structures
        Vector<String> topAggregates = mapUtils.getTopAggregates();
        Vector<String> toAnalyze = new Vector<String>();
        Vector<String> suitableAggregates = new Vector<String>();
        
        //add topAggregates to toAnalyze
        for (int i = 0; i < topAggregates.size(); i++) {
            toAnalyze.add(topAggregates.get(i));
        }
        
        //while there are aggregates to be analyzed...
        while (!toAnalyze.isEmpty()) {
            //check if head element is suitable to answer query
            if (suitable(referencedLevels, toAnalyze.firstElement())) {
                suitableAggregates.add(toAnalyze.firstElement());
            }
            else {
                //add children aggregates (finer)
                Vector<String> children = mapUtils.getChildAggregates(toAnalyze.firstElement());
                for (int i = 0; i < children.size(); i++)
                    toAnalyze.add(children.elementAt(i));
            }
            
            //remove head element (already analyzed)
            toAnalyze.remove(toAnalyze.firstElement());
        }

        //choose aggregate from suitableAggregates
        String aggregateId = "0";
        //case 1: no aggregate suitable, use fact table for this query
        if (suitableAggregates.size() == 0) {
            aggregateId = params.getCubeId();
            factTableName = mapUtils.getFactTableName(params.getCubeId());
        }
        //case 2: *one* suitable aggregate, use it
        else if (suitableAggregates.size() == 1) {
            usingAggregate = true;
            aggregateId = suitableAggregates.firstElement();
            factTableName = mapUtils.getAggregateName(aggregateId);
        }
        //case 3: multiple suitable aggregates, use heuristic to select one
        else {
            usingAggregate = true;
            aggregateId = selectBestAggregate(suitableAggregates);
            factTableName = mapUtils.getAggregateName(aggregateId);
        }
        
    }
    
    private boolean suitable(Vector<SOLAPDimensionLevelPair> referencedLevels, String aggregateId) {
        //for each level in query, check if the lowest level in the aggregate for that dimension is equal or lower
        for (int i = 0; i < referencedLevels.size(); i++) {
            String lowerLevel = mapUtils.getLowestAggregateLevel(aggregateId, referencedLevels.get(i).getDimensionId());
            //if the aggregate's lowest level is higher than any level in query, then it's not suitable
            if (!mapUtils.lowerOrEqual(lowerLevel, referencedLevels.get(i).getLevelId())) {
                //System.out.println("Aggregate" + aggregateId + " is *not* suitable!");
                return false;
            }
        }
        //System.out.println("Aggregate" + aggregateId + " is suitable!");
        return true;
    }

    private String selectBestAggregate(Vector<String> suitableAggregates) {
        return suitableAggregates.firstElement();
    }
    
    private void sortAttributesByTypeOfLevel(SOLAPParamsObject params) {
        AttributesUtils attributesUtils = new AttributesUtils(mapUtils);
        
        if(spatialAttr.size() > 1) {
            int value = attributesUtils.getType(spatialAttr.get(0), spatialAttr.get(1));
            if(value == 4) twoTempTable = true;
            if(value == 3) spatialIntersectTempTable = true;
        }
        
        for (int x = 0; x < spatialAttr.size(); x++) {
        //main spatial attribute
        SOLAPAttributeParams mainAttribute = spatialAttr.get(x);

            //Spatial attributes from params were already removed by spatial parser, process all others (semantic)
            for (int i = 0; i < params.getAttributeParams().size(); i++) {
                SOLAPAttributeParams temp = params.getAttributeParams().elementAt(i);
                
                if (attributesUtils.getType(mainAttribute, temp) == 4) { 
                    if (!differentDimension.contains(temp.getId()) && !isAttFromSpatialDimension(temp)) {
                        System.out.println("Different dimension: " + mapUtils.getAttributeName(temp.getId()) + " ID: " + temp.getId());
                        differentDimension.add(temp.getId());
                    }
                    oneTempTable = true;
                }
                
                else if (attributesUtils.getType(mainAttribute, temp) == 0) {
                    System.out.println("Same level: " + mapUtils.getAttributeName(temp.getId()));
                    if (!sameLevel.contains(temp.getId()))
                        sameLevel.add(temp.getId());
                }
                else if (attributesUtils.getType(mainAttribute, temp) == 1) {
                    System.out.println("Lower level: " + mapUtils.getAttributeName(temp.getId()));
                    if (!lowerLevel.contains(temp.getId())) {
                        lowerLevel.add(temp.getId());
                    }
                    oneTempTable = true;
                }
                else if (attributesUtils.getType(mainAttribute, temp) == 2) {
                    System.out.println("Higher level: " + mapUtils.getAttributeName(temp.getId()));
                    if (!higherLevel.contains(temp.getId()))
                        higherLevel.add(temp.getId());
                }
                else if (attributesUtils.getType(mainAttribute, temp) == 3) {
                    System.out.println("Different hierarchy: " + mapUtils.getAttributeName(temp.getId()));                  
                    if (!differentHierarchy.contains(temp.getId())) {            
                        differentHierarchy.add(temp.getId());
                    }
                    oneTempTable = true;
                }
            }
        }
        
        if(spatialIntersectTempTable && oneTempTable)
            oneTempTable = false;
        
        System.out.println("ONE TEMP TABLE: " + oneTempTable);
        System.out.println("SPATIAL INTERSECTION TABLE: " + spatialIntersectTempTable);
        System.out.println("TWO TEMP TABLE: " + twoTempTable);
    }
    
    private Vector<SOLAPAttributeParams> getHigherAttributes() {
        Vector<SOLAPAttributeParams> result = new Vector<SOLAPAttributeParams>();
        
        for(String higherAttr: this.getHigherLevelNames()) {
            for(SOLAPAttributeParams attrParams: params.getAttributeParams()) {
                if(mapUtils.getAttributeName(attrParams.getId()).equals(higherAttr)) {
                    result.add(attrParams);
                }
            }
        }
        
        return result;
    }
    
}
