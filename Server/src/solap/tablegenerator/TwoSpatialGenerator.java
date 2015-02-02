package solap.tablegenerator;

import java.awt.geom.Point2D;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import oracle.spatial.geometry.JGeometry;

import org.w3c.dom.Document;

import solap.params.SOLAPAttributeParams;
import solap.DataRequestProcessor;

import solap.clustering.PreProcessing;

import solap.utils.GeoUtils;
import solap.utils.MappingUtils;
import solap.utils.Triple;
import solap.utils.ITriple;

public class TwoSpatialGenerator extends PivotTableGenerator implements TableGenerator{

    //Types of Object (Alert: This information must be especified correctly in metamodel(XML file))
    public static final String POLYGON = "polygon";
    public static final String LINE = "line";
    public static final String POINT = "point";

    //Store the spatial combinations and the new geometry of the object (arc)
    private Map<ITriple<String, String, String>, JGeometry> spatialCombinations;
    
    private Map<String, JGeometry> spatialObjects1;
    private Map<String, JGeometry> spatialObjects2;
    
    //Type of attributes' objects
    private String geometryType1;
    private String geometryType2;
        
    //"Handler" to comunicate with SQL generator
    private DataRequestProcessor sqlGenerator;
    
    public TwoSpatialGenerator(DataRequestProcessor sqlGenerator, String geometryType1, String geometryType2, boolean clustering) {
        super("twospatial", sqlGenerator.getHeaderAttributes(), sqlGenerator.getSpatialAttr()
              , sqlGenerator.getNumberOfMeasures(), sqlGenerator.getHigherLevelNames(), sqlGenerator.buildQuery()
              , sqlGenerator.getMapUtils(), clustering, sqlGenerator);
        
        spatialCombinations = new HashMap<ITriple<String, String, String>, JGeometry>();
        
        this.geometryType1 = geometryType1;
        this.geometryType2 = geometryType2;
        this.sqlGenerator = sqlGenerator;
        
        spatialObjects1 = new HashMap<String, JGeometry>();
        spatialObjects2 = new HashMap<String, JGeometry>();
    }

    public void generateTable() {
        createTable();
        fillTable();
        super.createIndex("geom");
            
        super.createIndex("object1");
        super.createIndex("object2");
    }

    public String getQuery() {
        String query = "SELECT temp_id as idin, ";
        
        for(int j = 0; j < numberInlineAttr; j++)
            query += "alphaNumeric" + j + ", ";
        
        for (int i = 0; i < numberOfColumns - numberInlineAttr; i++)
            query += "c" + i + ", ";
                
        String temp = name + "x";
        query += "name, (SELECT geom FROM " + name + " " + temp + " WHERE " + temp + "." + "temp_id = " + name + ".temp_id) glocal FROM " + name;
        
        return query;
    }
    
    protected void createTable() {
        //drop previous table
        dbUtils.executeQuery("drop table " + name);
        
        //create new temporary table
        String query = "create table " + name + " (temp_id number primary key";
        for(int i = 0; i < numberInlineAttr; i++)
            query += ", alphaNumeric" + i + " varchar2(200)";
        
        for (int i = 0; i < (numberOfColumns-numberInlineAttr); i++) {
            query += ", c" + i + " number";
        }
        query += ", name varchar2(200), geom SDO_GEOMETRY, object1 SDO_GEOMETRY, object2 SDO_GEOMETRY, " +
            "labelGroups1 varchar2(100), labelGroups2 varchar2(100), labelNames1 varchar2(100), labelNames2 varchar2(100), labelAll1 varchar2(100), labelAll2 varchar2(100) )";
        
        dbUtils.executeQuery(query);    
    }

    private void fillTable() {
        Map<String, Object> labelAux = new HashMap<String, Object>();
        
        ArcGenerator.reset();
        
        if(!super.clustering)
            spatialCombinations = dbUtils.computeArcs(sqlGenerator.computeSpatialQuery(), this.numberMeasures, this.numberMeasures + this.numberInlineAttr, this);
        else 
            spatialCombinations = dbUtils.computeArcsWithClustering(sqlGenerator.computeSpatialQuery(), this.numberMeasures, this.numberMeasures + this.numberInlineAttr, 
                                                                    super.groupsObjects, super.preProcessing.getClusteredObjects(), this);
        
        Map<String, String> labels1 = new HashMap<String, String>();
        Map<String, String> labels2 = new HashMap<String, String>();
        
        for (int i = 0; i < data.size(); i++) {
            insertRow(data.elementAt(i), i, labelAux, labels1, labels2);
        }
        
    }
    
    private void insertRow(Vector<String> rowValues, int index, Map<String, Object> labelAux, Map<String, String> labels1, Map<String, String> labels2) {

        //Make key
        String[] locationsValues = spatialCombinationInfo2.get(index).split(",");
        ITriple<String, String, String> tempKey =
            new Triple<String, String, String>(locationsValues[0], locationsValues[1], "");
        
        JGeometry ob1 = spatialObjects1.get(locationsValues[0]);
        JGeometry ob2 = spatialObjects2.get(locationsValues[1]);
        
        
        if(!clustering) {
            //Insert row including the new spatial object
            dbUtils.insertTwoSpatialNormal(spatialCombinations.get(tempKey),spatialCombinationInfo2.get(index), rowValues, index, this.name, ob1, ob2, labels1, labels2);
        }
        else {
            boolean first = false;
            boolean second = false;
            String groupId1 = "";
            String groupId2 = "";
            if(tempKey.getFirst().contains(PreProcessing.NAME_GROUP)) {
                first = true;
                groupId1 = tempKey.getFirst().substring(5,tempKey.getFirst().length());
            }
            if(tempKey.getSecond().contains(PreProcessing.NAME_GROUP)) {
                second = true;
                groupId2 = tempKey.getSecond().substring(5,tempKey.getSecond().length());
            }
            
            dbUtils.insertSRowTwoSpatialObjectClustering(spatialCombinations.get(tempKey), spatialCombinationInfo2.get(index), rowValues, index, this.name, ob1, ob2, first, second, groupId1, groupId2, labels1, labels2);
        }
    }
    
    
    // Pre-process the spatial query in order to indicate the right spatial operations
    // if necessary (ex: centroid of a polygon)
    public static void preProcessSpatialQuery(String geometryType1, String geometryType2, DataRequestProcessor sqlGenerator) {
        //Both spatial objects are points   
        if ( geometryType1.equals(POINT) && geometryType2.equals(POINT)) {
            //do nothing
        }
        //One spatial object is a point and the another is a polygon
        if ( geometryType1.equals(POINT) && geometryType2.equals(POLYGON) )
            sqlGenerator.centroidObject(1);
        
        if  (geometryType1.equals(POLYGON) && geometryType2.equals(POINT))
            sqlGenerator.centroidObject(0);
        //Both spatial objects are polygons
        if (geometryType1.equals(POLYGON) && geometryType2.equals(POLYGON)) {
            sqlGenerator.centroidObject(0);
            sqlGenerator.centroidObject(1);
        }
        
        //One Spatial Object is a point and the another is a line
        if ( (geometryType1.equals(POINT) && (geometryType2.equals(LINE))) || 
        (geometryType1.equals(LINE) && geometryType2.equals(POINT))) {
            //TODO
        }
        //One Spatial Object is a polygon and the another is a line
        if ( (geometryType1.equals(LINE) && geometryType2.equals(POLYGON))
         ||  (geometryType1.equals(POLYGON) && geometryType2.equals(LINE)) ) {
            //TODO
        }
        //Both spatial objects are lines
        if (geometryType1.equals(LINE) && geometryType2.equals(LINE)) {
            //TODO
        }
    }
    
    public String getQueryForFirstDimensionSObjects() {
        return "select temp_id as idin, object1 as glocal, labelNames1 from " + name;   
    }
    
    public String getQueryForSecondDimensionSObjects() {
        return "select temp_id as idin, object2 as glocal, labelNames2  from " + name;
    }

    public Document getClusteri1ngData() {
        return super.getClusteringData();
    }
    
    public void addSpatialObject1(String semanticAttr, JGeometry object) {
        spatialObjects1.put(semanticAttr, object);
    }
    
    public void addSpatialObject2(String semanticAttr, JGeometry object) {
        spatialObjects2.put(semanticAttr, object);
    }

    public boolean didClustering() {
        return super.clustering;
    }
}
