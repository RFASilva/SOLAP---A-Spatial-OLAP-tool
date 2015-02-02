package solap.tablegenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import oracle.spatial.geometry.JGeometry;

import org.w3c.dom.Document;

import org.w3c.dom.Element;

import org.w3c.dom.NodeList;

import solap.DataRequestProcessor;

import solap.entities.SumarizationMeasure;

import solap.params.SOLAPAttributeParams;

import solap.utils.DBUtils;
import solap.utils.MappingUtils;
import solap.utils.XMLUtils;

public class SumarizationGenerator extends OneSpatialGenerator implements TableGenerator {

    protected Vector<String> measureHierarchies;
    protected int numberMeasureHierarchies;
    protected boolean isToCharacterizeByMeasure;
    protected String generalizationType;
    //protected Vector<double[]> polygons;
    protected int numberOfGroups;
    protected Map<String, JGeometry> objectsForGroups;

    public SumarizationGenerator(String name, int numberMeasures, 
        Vector<String> inlineAttr, Vector<String> measureHierarchies, Document xmlData, MappingUtils mapUtils, 
        Vector<SOLAPAttributeParams> spatialAttr, 
        DataRequestProcessor sqlGenerator, String characterizationByMeaseure, String type, int numberOfGroups,
        Map<String, JGeometry> createdGroups) {
        
        if(characterizationByMeaseure.equals("Yes"))
            isToCharacterizeByMeasure=true;
        this.name = name;
        this.generalizationType = type;
        this.numberOfGroups = numberOfGroups;
        this.objectsForGroups = createdGroups;
        this.sqlGenerator = sqlGenerator;
        this.clustering = false;
        this.associatedAttributes = new Vector<String>();
        
        this.spatialAttributes = spatialAttr;

        for (int i = 0; i < spatialAttributes.size(); i++)
            associatedAttributes.add(mapUtils.getDisplayAttributeIdFromLevel(spatialAttributes.elementAt(i).getLevelId()));
        
        this.headersAttributes = new Vector<String>();
        this.numberMeasures = numberMeasures;
        
        this.inlineAttributes = inlineAttr;
        this.numberInlineAttr = inlineAttributes.size();
        this.measureHierarchies = measureHierarchies;
        this.numberMeasureHierarchies = measureHierarchies.size();
        
        this.dbUtils = new DBUtils();
        this.mapUtils = mapUtils;
        this.xmlUtils = new XMLUtils();
        this.spatialCombinationInfo = new HashMap<String, Integer>();
        this.spatialCombinationInfo2 = new HashMap<Integer, String>();
        this.xmlData = xmlData;
        //System.out.println("XML PASSADO PARA O GERADOR:\n" + xmlUtils.docToXml(xmlData));
        fillMeasuresOperator(); 

        //Because clustering is false
        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||");
        this.numberRowsInOriginalData = getNumberOfRowsInXmlData();
        System.out.println("numberRowsInOriginalData: " + numberRowsInOriginalData);
        this.numberOfColumns = this.numberMeasures + this.numberInlineAttr + this.numberMeasureHierarchies;
        System.out.println("numberOfColumns: " + numberOfColumns);
        this.numberOfRows = getNumberOfRows();
        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||");
        this.data = getStructuredData();
        
    }
    
    private void fillMeasuresOperator() {
        measuresAggOperator = new HashMap<String, String>();
        for(SumarizationMeasure m: sqlGenerator.getParams().getMeasuresToGeneralize()) {
            String colName = mapUtils.getMeasureColumn(m.getId());
            measuresAggOperator.put(m.getOperator().toUpperCase() + "_" + colName.toUpperCase(), m.getOperator());
        }
    }
    
    private int getNumberOfRowsInXmlData() {
        Element rowset = xmlData.getDocumentElement();
        return xmlUtils.getAllChildElementNamed(rowset, "ROW", true).length;
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
                else 
                    possibleNewCombination += getElementValue(i, pos) + ","; 
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
    
    private int getElementPosition(String name) {
        Element rowset = xmlData.getDocumentElement();
        Element oneRow = (Element)rowset.getElementsByTagName("ROW").item(0);
        
        NodeList nodes = oneRow.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (name.compareToIgnoreCase(nodes.item(i).getNodeName()) == 0)
                return i;
        }
        
        //not found
        return -1;
    }
    
    private String getElementValue(int rowPos, int elemPos) {      
        Element row = (Element)xmlData.getElementsByTagName("ROW").item(rowPos);
        String result = row.getChildNodes().item(elemPos).getFirstChild().getNodeValue();
        return result;
    }
    
    //Table creation
    //Returns table data in pivot way (Vector<Vector<String>>)
    private Vector<Vector<String>> getStructuredData() {
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        
        //initialize structure
        System.out.println("------ Numero de rows: " + numberOfRows);
        System.out.println("------ Numero de columns: " + numberOfColumns);
        for (int i = 0; i < numberOfRows; i++) {
            Vector<String> stubs = new Vector<String>();
            for (int j = 0; j < numberOfColumns; j++) {
                stubs.add(NULL_MARK);
            }
            data.add(stubs);
        }

        //add data from ROWSET
        data = addRowsetData(data);
        System.out.println("------------------------------SAI NO ADDROWSETDATA------------------------------");
        return data;
    }
    
    //adds the actual data to the initialized structure
    private Vector<Vector<String>> addRowsetData(Vector<Vector<String>> data) {
        System.out.println("------------------------------ENTREI NO ADDROWSETDATA------------------------------");
        
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
            
            //for each measure (also add number of inline attributes)
            for (int j = 0; j < numberMeasures; j++) {
                //int col = j + numberInlineAttr + numberMeasureHierarchies;
                //String measureValue = getElementValue(i, col +1);
                //System.out.println(measureValue);
                //data.elementAt(row).set(col, measureValue);
                int col = j + numberInlineAttr;
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
            
            for (int j = 0; j < numberMeasureHierarchies; j++) {
                int elemPos =  getElementPosition(measureHierarchies.elementAt(j).toUpperCase());
                String value = getElementValue(i, elemPos);
                //System.out.println(value);
                data.elementAt(row).set(numberInlineAttr + j, value);
            }
        }
        //return modified structure
        return data;
    }
    
    public void generateTable() {
        System.out.println("ENTREI NO GENERATE TABLE");
        this.createTable();
        System.out.println("PASSEI DO CREATE TABLE");
        this.fillTable();
        System.out.println("PASSEI DO FILL TABLE");
        super.createIndex("geom");
        System.out.println("PASSEI DO CREATE INDEX");
    }
    
    protected void createTable() {
        //drop previous table
        dbUtils.executeQuery("drop table " + name);
        
        //create new temporary table
        String query = "create table " + name + " (temp_id number primary key";
        if((numberInlineAttr + numberMeasureHierarchies) > 0)
            query += ", alphaNumeric1 varchar2(500)";
        
        for (int i = 0; i < (numberOfColumns-numberInlineAttr-numberMeasureHierarchies); i++) {
            query += ", c" + i + " varchar2(500)";
        }
        
        query += ", name varchar2(200), geom SDO_GEOMETRY, label varchar2(100), labelAll varchar2(150), labelName varchar2(150))";
        
        dbUtils.executeQuery(query);    
    }
    
    protected void fillTable() {
        System.out.println("ENTREI NO FILL TABLE");
        //System.out.println("Tamanho: " + data.size());
        //for(Vector<String> v : data){
        //    System.out.println("**********");
        //    for (String s: v)
        //        System.out.println(s);
        //    System.out.println("**********");
        //}
        for (int i = 0; i < data.size(); i++) {
            insertRow(data.elementAt(i), i);
        }
        System.out.println("ACABEI O FILL TABLE");
    }
    
    private void insertRow(Vector<String> rowValues, int index) {
        System.out.println("---------- index: " + index +" ----------");
        //Variables to generate INSERT clause
        String spatialAttributeId = spatialAttributes.elementAt(0).getId();
        String spatialLevelId = spatialAttributes.elementAt(0).getLevelId();
        String spatialDimensionId = spatialAttributes.elementAt(0).getDimensionId();
        String spatialAttribute = mapUtils.getAttributeColumn(spatialAttributeId);
        String spatialTableId = mapUtils.getTableId(spatialLevelId, spatialDimensionId);
        String spatialTable = mapUtils.getTableName(spatialTableId);
        String associatedColumn = mapUtils.getAttributeColumn(associatedAttributes.elementAt(0));
        
        //add values to the row
        String query = "insert into " + name + " values (" + index + ", ";
        
        if(inlineAttributes.size() + measureHierarchies.size() != 0)
            query += "'";
        
        Vector<String> addCs = new Vector<String>();
        String addAlphaNumeric1 = "";
        int alphanumericValues = 0;
        for (int i = 0; i < rowValues.size(); i++) {
            if(alphanumericValues < inlineAttributes.size() + measureHierarchies.size()) {
                if(alphanumericValues < inlineAttributes.size())
                    addAlphaNumeric1 += rowValues.elementAt(i);
                else
                    addAlphaNumeric1 += rowValues.elementAt(i);
                alphanumericValues++;
                if(alphanumericValues == inlineAttributes.size() + measureHierarchies.size())
                    query += addAlphaNumeric1 + "', ";
                else
                    addAlphaNumeric1 += " ";
            }
            else{
                String newC = rowValues.elementAt(i);
                addCs.add(newC);
                query += "'" + newC + "', ";
            }
                
        }
        
        String spatialCombination = spatialCombinationInfo2.get(index);
        query += "'" +spatialCombination+ "', ";
        if( (!generalizationType.equals("SPATIAL")) && index < numberOfGroups ){
            dbUtils.insertGroupCentroidInSummarizationTable(objectsForGroups.get(spatialCombination),spatialCombination, addAlphaNumeric1, addCs, index, name);
            //query +="(SELECT polygon1 FROM polygon_point_group WHERE polygon_point_group.temp_id1 = " + (Integer.parseInt(spatialCombination.split(" ")[1])-1)  + "), ' ', '" + spatialCombination +"','" + spatialCombination +"' )";
        }
        else{
            query += "(SELECT " + spatialAttribute + " FROM " + spatialTable + " WHERE " + spatialTable + "." + associatedColumn + " = '" + spatialCombination  + "'), ' ', '" + spatialCombination +"','" + spatialCombination +"' )";
            System.out.println("QUERY: " + query);
            dbUtils.executeQuery(query);
        }
        //    query += "(SELECT " + spatialAttribute + " FROM " + spatialTable + " WHERE " + spatialTable + "." + associatedColumn + " = '" + spatialCombination  + "'), ' ', '" + spatialCombination +"','" + spatialCombination +"' )";
        //System.out.println("QUERY: " + query);
        //dbUtils.executeQuery(query);
    }
    
    public String getQuery() {
        String query = "SELECT temp_id as idin, ";
        if(isToCharacterizeByMeasure){
            for (int i = 0; i < numberOfColumns - numberInlineAttr - numberMeasureHierarchies; i++)
                query += "c" + i + ", ";
        }
        
        if((numberInlineAttr + numberMeasureHierarchies) > 0)
            query += "alphaNumeric1, ";
        
        String temp = name + "x";
        query += "name, (SELECT geom FROM " + name + " " + temp + " WHERE " + temp + "." + "temp_id = " + name + ".temp_id) glocal, label, labelall, labelname FROM " + name;
        
        return query;
    }
}
