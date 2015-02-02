package solap.tablegenerator;

import java.util.*;

import oracle.spatial.geometry.JGeometry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import solap.DataRequestProcessor;

import solap.clustering.PreProcessing;


import solap.params.SOLAPAttributeParams;
import solap.params.SOLAPMeasureParams;

import solap.utils.MappingUtils;
import solap.utils.XMLUtils;
import solap.utils.DBUtils;

public class PivotTableGenerator {

    //Constants
    public static final String NULL_MARK = "0";

    //Name of the table
    protected String name;
    
    //Store different types os attributes
    protected Vector<String> associatedAttributes;
    protected Vector<String> inlineAttributes;
    protected Vector<String> headersAttributes;
    protected int numberMeasures;
    protected int numberInlineAttr;
    
    //Connectivity with the database
    protected DBUtils dbUtils;
    protected MappingUtils mapUtils;
    protected XMLUtils xmlUtils;
    
    //Pivot table
    protected Document xmlData;
    protected Vector<Vector<String>> data;
    protected int numberOfColumns;
    protected int numberOfRows;
    
    //Store spatial combination and the the index of line in pivot table
    protected Map<String, Integer> spatialCombinationInfo;
    protected Map<Integer, String> spatialCombinationInfo2;
    protected Map<String, String> measuresAggOperator;
    
    //Auxiliary
    protected int numberRowsInOriginalData;
    protected Vector<SOLAPAttributeParams> spatialAttributes;
    protected PreProcessing preProcessing;
    
    //To be used in present of clustering
    protected boolean clustering;
    protected DataRequestProcessor sqlGenerator;
    protected Document transformedData;
    
    protected List<Map<String, JGeometry>> groupsObjects;
    
    public PivotTableGenerator(){}
    
    public PivotTableGenerator(DataRequestProcessor sqlGenerator) {
        this.sqlGenerator = sqlGenerator;
        this.dbUtils = new DBUtils();
        this.xmlUtils = new XMLUtils();
        this.mapUtils = sqlGenerator.getMapUtils();
        fillMeasuresOperator();    
    }
    
    public PivotTableGenerator(String name, Vector<String> headers, 
            Vector<SOLAPAttributeParams> spatialAttributes, int numberMeasures, Vector<String> inlineAttr,
            String semanticQuery, MappingUtils mapUtils, boolean clustering, DataRequestProcessor sqlGenerator) {
        this.name = name;
        this.sqlGenerator = sqlGenerator;
        this.clustering = clustering;
        associatedAttributes = new Vector<String>();
        
        for (int i = 0; i < spatialAttributes.size(); i++)
            associatedAttributes.add(mapUtils.getDisplayAttributeIdFromLevel(spatialAttributes.elementAt(i).getLevelId()));
        
        this.spatialAttributes = spatialAttributes;
        this.headersAttributes = headers;
        this.numberMeasures = numberMeasures;
        this.inlineAttributes = inlineAttr;
        this.numberInlineAttr = inlineAttributes.size();
        this.dbUtils = new DBUtils();
        this.mapUtils = mapUtils;
        this.xmlUtils = new XMLUtils();
        this.spatialCombinationInfo = new HashMap<String, Integer>();
        this.spatialCombinationInfo2 = new HashMap<Integer, String>();
        this.xmlData = dbUtils.executeQuery(semanticQuery);
        fillMeasuresOperator(); 
        
        //Could be necessary a onespatialgenerator without a clustering
        if(clustering) {
            if(performClustering())
                xmlData = transformedData;
                numberRowsInOriginalData = getNumberOfRowsInXmlData();
                numberOfColumns = getNumberOfColumns();
                numberOfRows = getNumberOfRows();
                data = getStructuredData();
        }
        else {
            numberRowsInOriginalData = getNumberOfRowsInXmlData();
            numberOfColumns = getNumberOfColumns();
            numberOfRows = getNumberOfRows();
            data = getStructuredData();
        }
        
    }
    
    protected boolean performClustering() {
        //Preprocessing data
        preProcessing = new PreProcessing(xmlData, sqlGenerator);
        //Get the distincts spatials objects envolved in query
        preProcessing.initializeProcess();
        boolean doClustering = true;
        
        //Apply the spatial algorithms to those objects
        doClustering = preProcessing.applyClusteringAlgorithm(sqlGenerator.getGeometryType().get(0), sqlGenerator.getAssociatedAttributes().size(), sqlGenerator.getParams().getClusteringParams() );
        if(doClustering) {
                //Aggregate data of elements belonging to the same group
                transformedData = preProcessing.transformXMLData(sqlGenerator.getAssociatedAttributes(), measuresAggOperator);
                
                //Build the new spatial object of group
                groupsObjects = preProcessing.buildNewSpatialObjects(sqlGenerator.getGeometryType().get(0));
                if(!sqlGenerator.getGeometryType().get(0).equals("polygon"))
                    preProcessing.createPolygonRepresentation();
            return true;
        }
        else  {
            clustering = false;
            return false;
        }
        
    }
    
    public Document getDetailTable(String query) {
        xmlData = dbUtils.executeQuery(query);
        
        //Preprocessing data
        preProcessing = new PreProcessing(xmlData, sqlGenerator);
        //Get the distincts spatials objects envolved in query
        preProcessing.initializeProcess();
        
        //Apply the spatial algorithms to those objects
        preProcessing.applyClusteringAlgorithm(sqlGenerator.getGeometryType().get(0), sqlGenerator.getAssociatedAttributes().size(), sqlGenerator.getParams().getClusteringParams() );
        
        return preProcessing.detailGroupXMLData(sqlGenerator.getParams().getGroupDetailParams(), sqlGenerator.getAssociatedAttributes(), measuresAggOperator);
    }
    
    protected String getQuery() {
        String query = "SELECT temp_id as idin, ";
        
        for (int i = 0; i < numberOfColumns - numberInlineAttr; i++)
            query += "c" + i + ", ";
        
        for(int j = 0; j < numberInlineAttr; j++)
            query += "alphaNumeric" + j + ", ";
        
        String temp = name + "x";
        query += "name, (SELECT geom FROM " + name + " " + temp + " WHERE " + temp + "." + "temp_id = " + name + ".temp_id) glocal, label, labelall, labelname FROM " + name;
        
        return query;
    }
    
    protected void createTable() {
        //drop previous table
        dbUtils.executeQuery("drop table " + name);
        
        //create new temporary table
        String query = "create table " + name + " (temp_id number primary key";
        for(int i = 0; i < numberInlineAttr; i++) {
            query += ", alphaNumeric" + i + " varchar2(500)";
        }
        
        for (int i = 0; i < (numberOfColumns-numberInlineAttr); i++) {
            query += ", c" + i + " number";
        }
        
        query += ", name varchar2(200), geom SDO_GEOMETRY, label varchar2(100), labelAll varchar2(150), labelName varchar2(150))";
        
        dbUtils.executeQuery(query);    
    }
    
    protected void createIndex(String attr) {
        dbUtils.executeQuery("drop INDEX "+name+"_spatial_idx");
        dbUtils.executeQuery("CREATE INDEX "+name+"_spatial_idx ON " + name + "(" + attr+ ") INDEXTYPE IS MDSYS.SPATIAL_INDEX");
    }
    
    //Auxiliary
    private int getNumberOfRowsInXmlData() {
        Element rowset = xmlData.getDocumentElement();
        return (xmlUtils.getAllChildElementNamed(rowset, "ROW", true).length);
    }
    
    private int getElementPosition(String name) {
        Element rowset = xmlData.getDocumentElement();
        Element oneRow = (Element)rowset.getElementsByTagName("ROW").item(0);
        
        NodeList nodes = oneRow.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (name.compareTo(nodes.item(i).getNodeName()) == 0)
                return i;
        }
        
        //not found
        return -1;
    }
    
    private String getElementValue(int rowPos, int elemPos) {
        Element rowset = xmlData.getDocumentElement();
        Element row = (Element)rowset.getElementsByTagName("ROW").item(rowPos);

        return row.getChildNodes().item(elemPos).getFirstChild().getNodeValue();
    }
    
    private Vector<String> getRowsetDistincts(int elemPos) {
        Vector<String> values = new Vector<String>();
        
        for (int i = 0; i < numberRowsInOriginalData; i++) {
            if (!values.contains(getElementValue(i, elemPos))) {
                values.add(getElementValue(i, elemPos));
            }
        }
        
        return values;
    }
    
    //Returns distinct values for attribute i inside headerAttributes
    private Vector<String> getHeaderValues(int index) {
        Vector<String> values = new Vector<String>();
        String attribute = headersAttributes.elementAt(index);
        
        int elementPositionInRow = getElementPosition(attribute.toUpperCase());
        values = getRowsetDistincts(elementPositionInRow);
        
        return values;
    }
    
    //Returns number of columns in the temporary table
    private int getNumberOfColumns() {
        int temp = 1;
        
        for (int i = 0; i < headersAttributes.size(); i++) {
            temp *= getHeaderValues(i).size();
        }
        
        return (temp * numberMeasures) + numberInlineAttr;
    }

    
    //Returns number of rows in the final table
    private int getNumberOfRows() {
        //parse all <ROW>
        int index = 0;
        for (int i = 0; i < numberRowsInOriginalData; i++) {
            String possibleNewCombination = "";
            //parse all associated attributes
            for (int j = 0; j < associatedAttributes.size(); j++) {
                int pos = getElementPosition(mapUtils.getAttributeName(associatedAttributes.elementAt(j)).toUpperCase());
                if(j == associatedAttributes.size() - 1)
                    possibleNewCombination += getElementValue(i, pos);
                else possibleNewCombination += getElementValue(i, pos) + ",";    
            }
            //add if it's not there already
            if (!spatialCombinationInfo.containsKey(possibleNewCombination)) {
                spatialCombinationInfo.put(possibleNewCombination, index);
                spatialCombinationInfo2.put(index, possibleNewCombination);
                index++;
            }      
        }
        
        return spatialCombinationInfo.size();
    }
    
    //Table creation
    //Returns table data in pivot way (Vector<Vector<String>>)
    private Vector<Vector<String>> getStructuredData() {
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        
        //initialize structure
        for (int i = 0; i < numberOfRows; i++) {
            Vector<String> stubs = new Vector<String>();
            for (int j = 0; j < numberOfColumns; j++) {
                stubs.add(NULL_MARK);
            }
            data.add(stubs);
        }

        //add data from ROWSET
        data = addRowsetData(data);
        return data;
    }
    
    //adds the actual data to the initialized structure
    private Vector<Vector<String>> addRowsetData(Vector<Vector<String>> data) {
        //prepare nDistincts auxiliary structure
        Vector<Integer> nDistincts = new Vector<Integer>();
        nDistincts.add(numberMeasures);
        for (int j = 0; j <headersAttributes.size(); j++) {
            int value = getHeaderValues(j).size();
            nDistincts.add(value);
        }
        
        //prepare factors auxiliary structure
        Vector<Integer> factors = new Vector<Integer>();
        factors.add(1);     //measures multiplying factor (1)
        for (int j = 0; j < headersAttributes.size(); j++) {
            int value = factors.elementAt(factors.size()-1) * nDistincts.elementAt(factors.size()-1);
            factors.add(value);
            //System.out.println("Factor: " + value);
        }
        
        //parse all <ROW>
        for (int i = 0; i < numberRowsInOriginalData; i++) {
            //get correct row
            String spatialCombination = "";
            //parse all associated attributes
            for (int k = 0; k < associatedAttributes.size(); k++) {
                int pos = getElementPosition(mapUtils.getAttributeName(associatedAttributes.elementAt(k)).toUpperCase());
                if(k == associatedAttributes.size() - 1)
                    spatialCombination += getElementValue(i, pos);
                else spatialCombination += getElementValue(i, pos)+ ",";
            }
            
            int row = spatialCombinationInfo.get(spatialCombination);
            int temp = 0;
            //for each header attribute
            for (int j = 0; j < headersAttributes.size(); j++) {
                int elemPos = getElementPosition(headersAttributes.elementAt(j).toUpperCase());
                Vector <String> attributeDistincts = getRowsetDistincts(elemPos);
                int valueIndex = attributeDistincts.indexOf(getElementValue(i, elemPos));
                temp = temp + valueIndex * factors.elementAt(j+1);
            }
            //for each measure (also add number of inline attributes)
            for (int j = 0; j < numberMeasures; j++) {
                int col = (temp + j) + numberInlineAttr;
                //System.out.println("Linha " + i + " Pos: " + col);
                String measureValue = getElementValue(i, j);
                data.elementAt(row).set(col, measureValue);
            }
            
            //add inline values
            for (int j = 0; j < numberInlineAttr; j++) {
                int elemPos =  getElementPosition(mapUtils.getAttributeName(inlineAttributes.elementAt(j)).toUpperCase());
                String value = getElementValue(i, elemPos);
                data.elementAt(row).set(j, value);
            }
        }
        
        //return modified structure
        return data;
    }
    
    public void setClustering(boolean clustering) {
        this.clustering = clustering;
    }

    public boolean isClustering() {
        return clustering;
    }
    
    public Document getClusteringData() {        
        return transformedData;
    }

    private void fillMeasuresOperator() {
        measuresAggOperator = new HashMap<String, String>();
        for(SOLAPMeasureParams param: sqlGenerator.getParams().getMeasureParams()) {
            String colName = mapUtils.getMeasureColumn(param.getId());
            measuresAggOperator.put(param.getOperator().toUpperCase() + "_" + colName.toUpperCase(), param.getOperator());
        }
    }
}
