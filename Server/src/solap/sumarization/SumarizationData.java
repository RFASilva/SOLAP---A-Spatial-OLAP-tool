package solap.sumarization;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import oracle.spatial.geometry.JGeometry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import solap.clustering.SummarizationPreProcessing;
import solap.clustering.support.Instance;

import solap.entities.SumarizationAttribute;
import solap.entities.SumarizationMeasure;

import solap.params.SOLAPAttributeParams;
import solap.params.SOLAPGeneralizationParams;
import solap.params.SOLAPParamsObject;

import solap.sumarization.hierarchies.NumericHierarchy;
import solap.sumarization.hierarchies.NumericHierarchySegment;

import solap.utils.MappingUtils;
import solap.utils.SumarizationUtils;

public class SumarizationData {
    
    private static final String separationCharacter = "%%";
    private static final String UNDEFINED_STRING ="*UNDEFINED*";
    
    // Initial data
    private Document data;
    private MappingUtils mapUtils;
    private SOLAPGeneralizationParams generalizationInfo;
    private Vector<SumarizationAttribute> attributes;
    private Vector<SumarizationMeasure> measures;
    
    //Responses
    private String response;
    private String responseDetail;
    
    // Auxiliar Data 
    private String spatialAttributeName;
    private SOLAPAttributeParams spatialAttribute;
    private SOLAPAttributeParams spatialAttributeGeom;
    private String geomType;
    
    private Vector<String> nonSpatialAttributes;
    private Vector<SOLAPAttributeParams> nonSpatialAtt;
    private Vector<String> nonSpatialAttributesIds;
    
    private Vector<String> measuresWithHierarchy_Name;
    private Vector<String> measuresNoHierarchy_Name;
    private HashMap<String,NumericHierarchy> numericHierarchies;
    
    private SummarizationPreProcessing preProcessing;
    private int numberOfGroups;
    private Map<String, JGeometry> objectsForGroups;
    
    // Results
    private HashMap<String,SpatialCharacteristics> resultsSD;
    private HashMap<String,Vector<String>> resultsNSD;
    
    public SumarizationData(SOLAPParamsObject params, Document data) {
        this.data = data;
        this.generalizationInfo = params.getGeneralizationParams();
        this.attributes = params.getAttributesToGeneralize();
        this.measures = params.getMeasuresToGeneralize();
        this.mapUtils = new MappingUtils(params.getFilename(), params.getCubeId());
        this.numericHierarchies = new HashMap<String,NumericHierarchy>();
        this.numberOfGroups = 0;
        this.objectsForGroups = new HashMap<String, JGeometry>();
    }
    
    public String getResponse(){
        return response;
    }
    
    public String getResponseDetail() {
        return responseDetail;
    }
    
    public void applyGeneralizationToData(){
        String generalizationType = generalizationInfo.getGeneralizationType();
        
        getChosenTopSpatialAndNonSpatialAttribute();
        getChosenMeasures();
        createNumericHierarchies();
        
        if(generalizationType.equals("SPATIAL"))
            spatialDominantGeneralization();
        else
            nonSpatialDominantGeneralization();
    }
    
    private void spatialDominantGeneralization(){
        System.out.println("Entrei no metodo de generalizacao de dominacia espacial");
        
        // Characterization Process
        resultsSD = characterizationSpatialValues();
        //printResultsFromSpatialProcess();
        buildSpatialDominantResponse();
        
        System.out.println("Cheguei ao fim do metodo de generalizacao de dominacia espacial");
    }
    
    private void nonSpatialDominantGeneralization(){
        System.out.println("Entrei no metodo de generalizacao de dominacia nao espacial");
        
        // Characterization process for the initial spaces
        resultsSD = characterizationSpatialValues();
        resultsNSD = new HashMap<String,Vector<String>>();
        
        Set<String> spatialValues = resultsSD.keySet();
        for(String sValue : spatialValues){
            String predominantCharacteristic="";
            // In case of more than you he will use the first one to be choosen.
            if(generalizationInfo.getCharMeasures().equals("Yes") && measuresNoHierarchy_Name.size()>0)
                predominantCharacteristic = resultsSD.get(sValue).getPredominantCharacteristicByMeasure(measuresNoHierarchy_Name.get(0));
            else
                predominantCharacteristic = resultsSD.get(sValue).getPredominantCharacteristicBySemanticAtt();
            
            //if(predominantCharacteristic.equals(""))
            //    System.out.println("E ESTEEEEEE!!!! " + sValue);
            
            // Add information
            Vector<String> newVector = new Vector<String>();
            if(resultsNSD.containsKey(predominantCharacteristic))
                newVector = resultsNSD.get(predominantCharacteristic);
            if(!newVector.contains(sValue))
                newVector.add(sValue);
            resultsNSD.put(predominantCharacteristic,newVector);
        }
        System.out.println("---------------------- CHAVES NO resultsNSD ----------------------");
        System.out.println(resultsNSD.keySet());
        //printResultsFromNonSpatialProcess();
        
        // Generate clusters
        preProcessing = new SummarizationPreProcessing(spatialAttributeGeom, resultsNSD, mapUtils);
        preProcessing.applyClustering(geomType, generalizationInfo.getZoomLevel(), generalizationInfo.getGroupsParam(), obtainPreComputedSharedBorderTableID());
        
        printGroupInfo();
        
        preProcessing.buildNewSpatialObjects(geomType);
        if(!geomType.equals("polygon"))
            preProcessing.createPolygonRepresentation();
        
        buildNonSpatialDominantResponse();
        
        System.out.println("Cheguei ao fim do metodo de generalizacao de dominacia nao espacial");
    }
    
    private String obtainPreComputedSharedBorderTableID (){
        String result = null;
        for(SumarizationAttribute a:  attributes){
            if(a.getAttId().equals(spatialAttribute.getId()))
                return a.getPreComputedSharedBorderTableID();
        }
        return result;
    }
    
    private HashMap<String,SpatialCharacteristics> characterizationSpatialValues(){
        HashMap<String,SpatialCharacteristics> results = new HashMap<String,SpatialCharacteristics>();
        
        NodeList rows = data.getElementsByTagName("ROW");
        int numRows = rows.getLength();
        System.out.println("Numero de linhas a processar: " + numRows);
        for(int i=0; i<numRows; i++){
            String spatialAttValue = ((Element) rows.item(i)).getElementsByTagName(spatialAttributeName.toUpperCase()).item(0).getTextContent();
            
            SpatialCharacteristics characteristics = new SpatialCharacteristics();
            if(results.containsKey(spatialAttValue))
                characteristics = results.get(spatialAttValue);
            else
                results.put(spatialAttValue, characteristics);
            
            String characteristic="";
            for(String nonSpatialAttName : nonSpatialAttributes){
                String nonSpatialAttValue = ((Element) rows.item(i)).getElementsByTagName(nonSpatialAttName.toUpperCase()).item(0).getTextContent();
                characteristic +=  nonSpatialAttValue + separationCharacter;
            }
            
            for(String measureName : measuresWithHierarchy_Name){
                NumericHierarchy h = numericHierarchies.get(measureName);
                String mValue = ((Element) rows.item(i)).getElementsByTagName(measureName.toUpperCase()).item(0).getTextContent();
                characteristic +=  h.getSegmentName(Double.parseDouble(mValue)) + separationCharacter; 
            }       
            characteristic = characteristic.substring(0,characteristic.length()-2);
            characteristics.addCharacteristic(characteristic);
            
            for(String measureName : measuresNoHierarchy_Name){
                String mValue = ((Element) rows.item(i)).getElementsByTagName(measureName.toUpperCase()).item(0).getTextContent();
                double value = Double.parseDouble(mValue);
                String operator = measureName.split("_")[0];
                characteristics.addMeasureValue(measureName,characteristic,value,operator);
            }
            
            results.put(spatialAttValue,characteristics);
        }
        return results;
    }
    
    private void createNumericHierarchies(){
        for(SumarizationMeasure m : measures){
            if(Boolean.parseBoolean(m.getCreate()))
                createNumericHierarchy(m);
        }
    }
    
    private void createNumericHierarchy(SumarizationMeasure m){
        String mName = mapUtils.getMeasureColumn(m.getId());
        if(!m.getOperator().equals("noop"))
            mName = m.getOperator() + "_" + mName;
        numericHierarchies.put(mName,createMeasureHierarchy(mName,Integer.parseInt(m.getThreshold())));
    }
    
    private void printResultsFromSpatialProcess() {
        System.out.println("Vou imprimir a informacao da generalizacao de dominancia espacial");
        System.out.println("-----------------------------------");
        Set<String> spatialValues = resultsSD.keySet();
        for(String sValue : spatialValues){
            System.out.println("* " + sValue);
            resultsSD.get(sValue).printCharacteristics();
        }
        System.out.println("-----------------------------------");
    }
    
    private void printResultsFromNonSpatialProcess() {
        System.out.println("Vou imprimir a informacao da generalizacao de dominancia nao espacial");
        System.out.println("-----------------------------------");
        Set<String> spatialValues = resultsNSD.keySet();
        for(String sValue : spatialValues){
            System.out.println("* " + sValue);
            System.out.println("Elementos com esta caracteristica:\n" + resultsNSD.get(sValue));
        }
        System.out.println("-----------------------------------");
    }
    
    private void printGroupInfo(){
        Set<String> characteristics = resultsNSD.keySet();
        for(String characteristic : characteristics){
            System.out.println("**************************************************");
            System.out.println("Caracteristica: " + characteristic);
            System.out.println("**************************************************");
            Set<Map.Entry<String,List<Instance>>> groups = preProcessing.getClusteredObjects().getSpatialObjectsGroup().entrySet();
            for(Map.Entry<String,List<Instance>> group : groups){
                System.out.println("NOME: " + group.getKey());
                for(Instance i: group.getValue())
                    System.out.print( i.getSemancticAttribute() + " - ");
                System.out.println("\n-------------------------------------------");
            }
            System.out.println("**************************************************");
        }
    }
    
    private void buildSpatialDominantResponse() {
        //System.out.println("--------------------------SUPPORT TABLE-----------------------------");
        String xml = buildSpatialDominantSupportTableData();
        //System.out.println(xml);
        //System.out.println("--------------------------FIM SUPPORT TABLE-------------------------");
        response = xml;
        //System.out.println("---------------------------DETAIL TABLE-----------------------------");
        String xml2 = buildSpatialDominantDetailTableData();
        //System.out.println(xml2);
        //System.out.println("---------------------------FIM DETAIL TABLE-------------------------");
        responseDetail = xml2;
    }
    
    private String buildSpatialDominantSupportTableData() {
        String xml = "<ROWSET>";
        Set<String> spatialValues = resultsSD.keySet();
        for(String sValue : spatialValues){
            String rowToADD = "<ROW>";
            
            String predominantCharacteristic="";
            // In case of more than you he will use the first one to be choosen.
            if(generalizationInfo.getCharMeasures().equals("Yes") && measuresNoHierarchy_Name.size()>0)
                predominantCharacteristic = resultsSD.get(sValue).getPredominantCharacteristicByMeasure(measuresNoHierarchy_Name.get(0));
            else
                predominantCharacteristic = resultsSD.get(sValue).getPredominantCharacteristicBySemanticAtt();
            String[] tokens = predominantCharacteristic.split(separationCharacter);
            
            for(int i=0; i<measuresNoHierarchy_Name.size(); i++){
                String name = measuresNoHierarchy_Name.get(i);
                String operator = name.split("_")[0];
                if(verifyIsUndefined(predominantCharacteristic))
                    rowToADD += "<" + name + ">" + " "+ "</" + name + ">";
                else
                    rowToADD += "<" + name + ">" + resultsSD.get(sValue).getMeasureValueForCharacteristic(name,predominantCharacteristic,operator) + "</" + name + ">";
            }
            
            rowToADD += "<" + spatialAttributeName + ">" + sValue + "</" + spatialAttributeName + ">";

            for(int i=0; i<nonSpatialAttributes.size(); i++){
                rowToADD += "<" + nonSpatialAttributes.get(i) + ">" + tokens[i] + "</" + nonSpatialAttributes.get(i) + ">";
            }
            
            int lastIndex = nonSpatialAttributes.size();
            for(int i=0; i<measuresWithHierarchy_Name.size(); i++){
                rowToADD += "<" + measuresWithHierarchy_Name.get(i) + ">" + tokens[lastIndex + i] + "</" + measuresWithHierarchy_Name.get(i) + ">";
            }

            rowToADD += "</ROW>";
            xml += rowToADD;
        }
        xml += "</ROWSET>";
        return xml;
    }

    private String buildSpatialDominantDetailTableData() {
        String xml = "<ROWSET>";
        Set<String> spatialValues = resultsSD.keySet();
        for(String sValue : spatialValues){
            
            Vector<String> characteristics = resultsSD.get(sValue).getDetailResults();
            Vector<Integer> counts = resultsSD.get(sValue).getOccurrences();
              
            for(int index=0;index<characteristics.size();index++){
                String rowToADD = "<ROW>";
                
                String characteristic = characteristics.get(index);
                String[] tokens = characteristic.split(separationCharacter);
                
                for(int i=0; i<measuresNoHierarchy_Name.size(); i++){
                    String name = measuresNoHierarchy_Name.get(i);
                    String operator = name.split("_")[0];
                    if(verifyIsUndefined(characteristic))
                        rowToADD += "<" + name + ">" + ""+ "</" + name + ">";
                    else
                        rowToADD += "<" + name + ">" + resultsSD.get(sValue).getMeasureValueForCharacteristic(name,characteristic,operator) + "</" + name + ">";
                }
                
                rowToADD += "<" + spatialAttributeName + ">" + sValue + "</" + spatialAttributeName + ">";
                
                for(int i=0; i<nonSpatialAttributes.size(); i++){
                    rowToADD += "<" + nonSpatialAttributes.get(i) + ">" + tokens[i] + "</" + nonSpatialAttributes.get(i) + ">";
                }
                
                int lastIndex = nonSpatialAttributes.size();
                for(int i=0; i<measuresWithHierarchy_Name.size(); i++){
                    rowToADD += "<" + measuresWithHierarchy_Name.get(i) + ">" + tokens[lastIndex + i] + "</" + measuresWithHierarchy_Name.get(i) + ">";
                }
                
                rowToADD += "<Occurrences>" + counts.get(index) + "</Occurrences>";
                
                rowToADD += "</ROW>";
                xml += rowToADD;
            }
        }
        xml += "</ROWSET>";
        return xml;
    }
    
    private void buildNonSpatialDominantResponse(){
        //System.out.println("--------------------------SUPPORT TABLE-----------------------------");
        String xml = buildNonSpatialDominantSupportTableData();
        //System.out.println(xml);
        //System.out.println("--------------------------FIM SUPPORT TABLE-------------------------");
        response = xml;
        //System.out.println("---------------------------DETAIL TABLE-----------------------------");
        String xml2 = buildNonSpatialDominantDetailTableData();
        //System.out.println(xml2);
        //System.out.println("---------------------------FIM DETAIL TABLE-------------------------");
        responseDetail = xml2;
    }
    
    private String buildNonSpatialDominantSupportTableData() {
        System.out.println("DENTRO DO buildNonSpatialDominantSupportTableData");
        String xml = "<ROWSET>";
        //Set<String> characteristics = resultsNSD.keySet();
        //for(String characteristic : characteristics){
        //    String[] tokens = characteristic.split(separationCharacter);
            Set<Map.Entry<String,List<Instance>>> groups = preProcessing.getClusteredObjects().getSpatialObjectsGroup().entrySet();
            System.out.println("ANTES DO FOR DOS GRUPOS");
            for(Map.Entry<String,List<Instance>> group : groups){
                String characteristic = group.getValue().get(0).getCharacterization();
                String[] tokens = characteristic.split(separationCharacter);
                // Get Geom
                JGeometry geom = preProcessing.getNewSpatialObjects().get(group.getKey());
                // Create Document
                String rowToADD = "<ROW>";
                //System.out.println("Antes da metrica");
                for(int i=0; i<measuresNoHierarchy_Name.size(); i++){
                    String name = measuresNoHierarchy_Name.get(i);
                    String operator = name.split("_")[0];
                    if(characteristic.contains("*UNDEFINED*"))
                        rowToADD += "<" + name + ">" + " " + "</" + name + ">";
                    else
                        rowToADD += "<" + name + ">" + calcMeasureForGroup(group.getKey(), characteristic, name, operator) + "</" + name + ">";
                }
                //System.out.println("Antes do objecto espacial");
                numberOfGroups++;
                String groupName = "Group "+ numberOfGroups;
                rowToADD += "<" + spatialAttributeName + ">" + groupName + "</" + spatialAttributeName + ">";
                objectsForGroups.put(groupName,geom);
                //System.out.println("Antes dos atributos semanticos");
                for(int i=0; i<nonSpatialAttributes.size(); i++){
                    rowToADD += "<" + nonSpatialAttributes.get(i) + ">" + tokens[i] + "</" + nonSpatialAttributes.get(i) + ">";
                }
                //System.out.println("Antes da metrica com hierarquia");
                int lastIndex = nonSpatialAttributes.size();
                for(int i=0; i<measuresWithHierarchy_Name.size(); i++){
                    rowToADD += "<" + measuresWithHierarchy_Name.get(i) + ">" + tokens[lastIndex + i] + "</" + measuresWithHierarchy_Name.get(i) + ">";
                }

                rowToADD += "</ROW>";
                xml += rowToADD;
                //System.out.println(rowToADD);
            }
        //}
        //for(String characteristic : characteristics){
            //String[] tokens = characteristic.split(separationCharacter);
            System.out.println("-----------------------------------------------------------");
            Vector<Instance> notclusteresElements = preProcessing.getNotClusteredElements();
            //System.out.println(notclusteresElements);
            System.out.println("NUMERO DE ELEMENTOS COM ESTA CARACTERISTICA DOMINANTE QUE NAO ENTRARAM NOS GRUPOS: " + notclusteresElements.size());
            System.out.println("-----------------------------------------------------------");
            for(Instance inst : notclusteresElements){
                String element = inst.getSemancticAttribute();
                String characteristic = inst.getCharacterization();
                //System.out.println(characteristic);
                String[] tokens = characteristic.split(separationCharacter);
                String rowToADD = "<ROW>";
                for(int i=0; i<measuresNoHierarchy_Name.size(); i++){
                    String name = measuresNoHierarchy_Name.get(i);
                    String operator = name.split("_")[0];
                    if(verifyIsUndefined(characteristic))
                        rowToADD += "<" + name + ">" + " "+ "</" + name + ">";
                    else
                        rowToADD += "<" + name + ">" + resultsSD.get(element).getMeasureValueForCharacteristic(name,characteristic,operator) + "</" + name + ">";
                }
                rowToADD += "<" + spatialAttributeName + ">" + escapeCharacters(element) + "</" + spatialAttributeName + ">";
                for(int i=0; i<nonSpatialAttributes.size(); i++){
                    rowToADD += "<" + nonSpatialAttributes.get(i) + ">" + tokens[i] + "</" + nonSpatialAttributes.get(i) + ">";
                }
                int lastIndex = nonSpatialAttributes.size();
                for(int i=0; i<measuresWithHierarchy_Name.size(); i++){
                    rowToADD += "<" + measuresWithHierarchy_Name.get(i) + ">" + tokens[lastIndex + i] + "</" + measuresWithHierarchy_Name.get(i) + ">";
                }

                rowToADD += "</ROW>";
                xml += rowToADD;
            }
        //}
        xml += "</ROWSET>";
        return xml;
    }
    
    private boolean verifyIsUndefined(String characteristic){
        String[] tokens = characteristic.split(separationCharacter);
        for(String value : tokens){
            if(!value.equals(UNDEFINED_STRING))
                return false;
        }
        return true;
    }
    
    private double calcMeasureForGroup(String groupName, String characteristic, String measureName, String operator){
        //System.out.println("Entrei no calcMeasureForGroup");
        double result = 0;
        Vector<String> values = preProcessing.getClusteredObjects().getSemancticValuesInGroup(groupName);
        //System.out.println(values);
        for(String sValue : values){
            //if(!resultsSD.containsKey(sValue))
            //    System.out.println("Nao devia aparecer isto escrito");
            double measureValue = resultsSD.get(sValue).getMeasureValueForCharacteristic(measureName,characteristic,operator);
            System.out.println(measureValue);
            if(operator.equals("SUM") || operator.equals("AVG")){
                result = result + measureValue;
            }
            else if(operator.equals("MAX")){
                if(measureValue > result)
                    result = measureValue;
            }
            else if(operator.equals("MIN")){
                if(measureValue < result)
                    result = measureValue;
            }
        }
        if(operator.equals("AVG")){
            double size = values.size();
            result = result / size;
        }
        //System.out.println("Vou sair do calcMeasureForGroup");    
        return result;
    }
    
    private String buildNonSpatialDominantDetailTableData() {
        String xml = "<ROWSET>";
        //Set<String> characteristics = resultsNSD.keySet();
        int groupCount=1;
        //for(String characteristic : characteristics){
        //    String[] tokens = characteristic.split(separationCharacter);
            Set<Map.Entry<String,List<Instance>>> groups = preProcessing.getClusteredObjects().getSpatialObjectsGroup().entrySet();
            for(Map.Entry<String,List<Instance>> group : groups){
                for(Instance instance : group.getValue()){
                    String characteristic = instance.getCharacterization();
                    String[] tokens = characteristic.split(separationCharacter);
                    String rowToADD = "<ROW belongTo=\"Group "+ groupCount + "\">";
                    
                    for(int i=0; i<measuresNoHierarchy_Name.size(); i++){
                        String name = measuresNoHierarchy_Name.get(i);
                        String operator = name.split("_")[0];
                        if(verifyIsUndefined(characteristic))
                            rowToADD += "<" + name + ">" + " "+ "</" + name + ">";
                        else
                            rowToADD += "<" + name + ">" + resultsSD.get(instance.getSemancticAttribute()).getMeasureValueForCharacteristic(name,characteristic,operator) + "</" + name + ">";
                    }
                    
                    rowToADD += "<" + spatialAttributeName + ">" + escapeCharacters(instance.getSemancticAttribute()) + "</" + spatialAttributeName + ">";
                    
                    for(int i=0; i<nonSpatialAttributes.size(); i++){
                        rowToADD += "<" + nonSpatialAttributes.get(i) + ">" + tokens[i] + "</" + nonSpatialAttributes.get(i) + ">";
                    }
                    
                    int lastIndex = nonSpatialAttributes.size();
                    for(int i=0; i<measuresWithHierarchy_Name.size(); i++){
                        rowToADD += "<" + measuresWithHierarchy_Name.get(i) + ">" + tokens[lastIndex + i] + "</" + measuresWithHierarchy_Name.get(i) + ">";
                    }
                    
                    rowToADD += "</ROW>";
                    xml += rowToADD;
                }
                groupCount++;
            }
        //}
        xml += "</ROWSET>";
        return xml;
    }
    
    private String escapeCharacters(String s){
        String result = s.replace("&", "&#x26;");
        return result;
    }
    
    private void getChosenTopSpatialAndNonSpatialAttribute() {
        spatialAttributeName = "";
        nonSpatialAttributes = new Vector<String>();
        nonSpatialAtt = new Vector<SOLAPAttributeParams>();
        nonSpatialAttributesIds = new Vector<String>();
        for(SumarizationAttribute att : attributes){
            if(att.getToType() != null){
                if(att.getToType().equals("geometric")){
                    String attToId = att.getAttributeToId();
                    spatialAttributeName = mapUtils.getAttributeName(attToId);
                    //spatialAttributeColumn = mapUtils.getAttributeColumn(attToId);
                    // get geom attribute
                    String levelId = mapUtils.getLevelByAttribute(attToId);
                    String dimensionId = mapUtils.getDimensionByLevel(levelId);
                    spatialAttribute = new SOLAPAttributeParams(attToId,levelId,dimensionId);
                    String spatialId = mapUtils.getSpatialAttributeIdFromLevel(levelId);
                    spatialAttributeGeom = new SOLAPAttributeParams(spatialId, levelId, dimensionId);
                    geomType = mapUtils.getAttributeColumnType(spatialId);
                }
                if(att.getToType().equals("semantic")){
                    String attToId = att.getAttributeToId();
                    nonSpatialAttributes.add(mapUtils.getAttributeName(attToId));
                    nonSpatialAttributesIds.add(attToId);
                    String levelId = mapUtils.getLevelByAttribute(attToId);
                    String dimensionId = mapUtils.getDimensionByLevel(levelId);
                    nonSpatialAtt.add(new SOLAPAttributeParams(attToId,levelId,dimensionId));
                }
            }
            else{
                String attId = att.getAttId();
                String attLevel = mapUtils.getLevelByAttribute(attId);
                if(!mapUtils.getSpatialAttributeIdFromLevel(attLevel).isEmpty())
                {
                    System.out.println("SOU UM ATRIBUTO SOZINHO QUE NAO E GENERALIZADO PARA NENHUM OUTRO");
                    spatialAttributeName = mapUtils.getAttributeName(attId);
                    //spatialAttributeColumn = mapUtils.getAttributeColumn(attId);
                    // get geom attribute
                    String dimensionId = mapUtils.getDimensionByLevel(attLevel);
                    spatialAttribute = new SOLAPAttributeParams(attId,attLevel,dimensionId);
                    String spatialId = mapUtils.getSpatialAttributeIdFromLevel(attLevel);
                    spatialAttributeGeom = new SOLAPAttributeParams(spatialId, attLevel, dimensionId);
                    geomType = mapUtils.getAttributeColumnType(spatialId);
                }
                else{
                    nonSpatialAttributes.add(mapUtils.getAttributeName(attId));
                    nonSpatialAttributesIds.add(attId);
                    String levelId = mapUtils.getLevelByAttribute(attId);
                    String dimensionId = mapUtils.getDimensionByLevel(levelId);
                    nonSpatialAtt.add(new SOLAPAttributeParams(attId,levelId,dimensionId));
                }
            }
        }
    }
    

    
    private void getChosenMeasures() {
        measuresWithHierarchy_Name = new Vector<String>();
        measuresNoHierarchy_Name = new Vector<String>();
        for(SumarizationMeasure m : measures){
            String mName = mapUtils.getMeasureColumn(m.getId());
            if(!m.getOperator().equals("noop"))
                mName = m.getOperator() + "_" + mName;
            
            if(Boolean.parseBoolean(m.getCreate()))
                measuresWithHierarchy_Name.add(mName);
            else
                measuresNoHierarchy_Name.add(mName);
        }
        //if(!measuresNoHierarchy_Name.isEmpty()){
        //    this.measuresAggValues = new  HashMap<String,HashMap<String,Double>>(measuresNoHierarchy_Name.size());
        //    for(String measureName : measuresNoHierarchy_Name){
        //        measuresAggValues.put(measureName, new HashMap<String,Double>());
        //    }
        //}
    }
    
    private NumericHierarchy createMeasureHierarchy(String measureName, int thresold){
        ArrayList<Double> values = new ArrayList<Double>();
        NodeList rows = data.getDocumentElement().getElementsByTagName("ROW");
        int size = rows.getLength();
        System.out.println("Tamanho: " + size);
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        for(int i=0; i < size; i++) {
            Double value = Double.parseDouble(((Element)rows.item(i)).getElementsByTagName(measureName.toUpperCase()).item(0).getTextContent());
            if(max<value)
                max = value;
            if(min>value)
                min = value;
            values.add(value);
        }

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        Vector<NumericHierarchySegment> segments = SumarizationUtils.getSegmentsMyAlgorithm(values,thresold);
        for(NumericHierarchySegment s : segments)
            System.out.println(s.toString());
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        
        return new NumericHierarchy(segments);
    }


    public Vector<String> getNonSpatialAttributes() {
        return nonSpatialAttributes;
    }

    public Vector<String> getMeasuresWithHierarchy_Name() {
        return measuresWithHierarchy_Name;
    }

    public Vector<String> getMeasuresNoHierarchy_Name() {
        return measuresNoHierarchy_Name;
    }

    public SOLAPAttributeParams getSpatialAttributeGeom() {
        return spatialAttributeGeom;
    }

    public Vector<String> getNonSpatialAttributesIds() {
        return nonSpatialAttributesIds;
    }

    public String getSpatialAttributeName() {
        return spatialAttributeName;
    }

    public Vector<SOLAPAttributeParams> getNonSpatialAtt() {
        return nonSpatialAtt;
    }

    public SOLAPAttributeParams getSpatialAttribute() {
        return spatialAttribute;
    }

    public String getGeomType() {
        return geomType;
    }
    
    //public Vector<double[]> getGroupsPolygons(){
    //    return preProcessing.getCreatedPolygons();
    //}

    public int getNumberOfGroups() {
        return numberOfGroups;
    }

    public Map<String, JGeometry> getObjectsForGroups() {
        return objectsForGroups;
    }
}
