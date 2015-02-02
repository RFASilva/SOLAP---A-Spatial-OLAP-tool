package solap.clustering;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import oracle.spatial.geometry.JGeometry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pt.uminho.ubicomp.concaveHull.ConcaveHullEngine;
import pt.uminho.ubicomp.concaveHull.Point;

import solap.clustering.regionalizationAlgorithms.RegionalizationAlgorithm;
import solap.clustering.spatialAlgorithms.MixedDBScan;
import solap.clustering.support.ClusterObject;
import solap.clustering.support.CollectionSpatialObjects;
import solap.clustering.support.IDatabase;
import solap.clustering.support.InputParams;
import solap.clustering.support.Instance;

import solap.params.SOLAPAttributeParams;

import solap.utils.DBUtils;
import solap.utils.GeoUtils;
import solap.utils.ITriple;
import solap.utils.MappingUtils;
import solap.utils.XMLUtils;

public class SummarizationPreProcessing {

    private enum geometries {POINT, POLYGON, LINE};
    
    private MappingUtils mapUtils;
    private DBUtils dbUtils;
    private IClusteringData clusteringData;
    private CollectionSpatialObjects objects;
    private IDatabase clusteredObjects;
    private List<Vector<Point>> createdPolygons;
    
    private int numObjects;
    private Map<String, JGeometry> newSpatialObjects;
    
    public SummarizationPreProcessing(SOLAPAttributeParams spatialGeom, HashMap<String,Vector<String>> results, MappingUtils mapUtils) {
        this.mapUtils = mapUtils;
        this.dbUtils = new DBUtils();
        this.objects = getSpatialObjectInstances(spatialGeom, results);
        this.clusteringData = new ClusteringData();
        
        this.numObjects = 0;
        for(Map.Entry<String,Vector<String>> e : results.entrySet()){
            numObjects += e.getValue().size();
        }
    }
    
    private CollectionSpatialObjects getSpatialObjectInstances(SOLAPAttributeParams spatialGeom, HashMap<String,Vector<String>> results){
        HashMap<String, JGeometry> geometries = getGeometries(spatialGeom);
        CollectionSpatialObjects objects = new CollectionSpatialObjects();
        Set<String> spatialAttributesValues = results.keySet();
        for(String charac : spatialAttributesValues){
            Vector<String> v = results.get(charac);
            for(String value : v){
                JGeometry geom = geometries.get(value);
                Instance newInstance = new Instance(value, geom);
                newInstance.setAttributeHigherLevel("");
                newInstance.setCharacterization(charac);
                objects.addInstance(newInstance);
            }
        }
        objects.setLevelName(mapUtils.getNameLevel(spatialGeom.getLevelId()));
        System.out.println("Tamanho do objects: " + objects.numberInstances());
        return objects;
    }
    
    private HashMap<String, JGeometry> getGeometries(SOLAPAttributeParams spatialAtt){
        // Processing spatialAtt
        String spatialAttributeColumn = mapUtils.getAttributeColumn( spatialAtt.getId());
        String spatialTableId = mapUtils.getTableId(spatialAtt.getLevelId(), spatialAtt.getDimensionId());
        String spatialTable = mapUtils.getTableName(spatialTableId);
        
        String displayAttID = mapUtils.getDisplayAttributeIdFromLevel(spatialAtt.getLevelId());
        String semanticSpatialAtt = mapUtils.getAttributeColumn(displayAttID);
        
        // Podia receber os values todos e meter uma serie de ous na clausula where assim nao recebia itens desnecessarios
        String query = "SELECT " + semanticSpatialAtt + " as elem1, " + spatialAttributeColumn + " as elem2";
        query +=" FROM " + spatialTable;
        System.out.println(query);
        HashMap<String, JGeometry> geometries = dbUtils.obtainJGeometries(query);
        System.out.println("Tamanho do mapa retornado: " + geometries.size());
        return geometries;
    }
    
    public void applyClustering(String geometryType, String zoomLevel, String groupsParam, String preComputedDistancesTableId){
        System.out.println("VOU APLICAR O ALGORITMO DE AGRUPAMENTO");
        if(geometryType.toUpperCase().equals(geometries.POINT.toString())){
            System.out.println("ENTREI NA PARTE DOS PONTOS");
            InputParams parameters = new InputParams();
            double eps = MixedDBScan.computeEps(objects, groupsParam, "");
            parameters.addParameter(eps);
            parameters.addParameter(zoomLevel);
            parameters.addParameter(getBaseZoomLevel());
            clusteredObjects = clusteringData.applySpatialPointAlgorithm(IClusteringData.pointAlgorithm.MIXEDDBSCAN, parameters, objects);
            System.out.println("ACABEI DE APLICAR O ALGORITMO DE AGRUPAMENTO");
        }
        else if(geometryType.toUpperCase().equals(geometries.POLYGON.toString())){
            System.out.println("ENTREI NA PARTE DOS POLIGONOS");
            String query=buildQuery(preComputedDistancesTableId);
            Map<ITriple<String,String,String>, Double> sharedBordersBetweenPolygons = dbUtils.getPreComputedPolygonsDistances(query);
            objects.setSharedBordersBetweenInstances(sharedBordersBetweenPolygons);
            RegionalizationAlgorithm regAlg = new RegionalizationAlgorithm();
            clusteredObjects = regAlg.runAlgorithm(objects);
            System.out.println("ACABEI DE APLICAR O ALGORITMO DE REGIONALIZACAO");
        }
    }
    
    public void buildNewSpatialObjects(String geometryType) {
        newSpatialObjects = new HashMap<String, JGeometry>();
    
        //Polygon spatial clustering
        if(geometryType.toUpperCase().equals(geometries.POLYGON.toString())) {
            newSpatialObjects = buildUnionOfPolygons();
        }
        else if(geometryType.toUpperCase().equals(geometries.POINT.toString())) {
            Map<String, JGeometry> newObjects = new HashMap<String, JGeometry>();
            Iterator<Map.Entry<String, List<Instance>>> it = clusteredObjects.getIteratorSpatialObjectsByGroup();
            while(it.hasNext()) {
                Map.Entry<String, List<Instance>> next = it.next();
                System.out.println("Tamanho da lista para " + next.getKey() + " : " + next.getValue().size());
                newObjects.put(next.getKey(), GeoUtils.computeCentroidOfPoints(next.getValue()));
            }
            newSpatialObjects = newObjects;   
        }
    }
    
    private Map<String, JGeometry> buildUnionOfPolygons() {
        String nameTable = "clustering_polygons";
        
        dbUtils.executeQuery("drop table " + nameTable);
        //create new temporary table
        String query = "create table " + nameTable + " (temp_id number primary key, groupId varchar2(200), geom SDO_GEOMETRY)";
        //System.out.println("Query do BuildUnionPolygons:\n" + query);
        dbUtils.executeQuery(query);
        dbUtils.insertPolygons(clusteredObjects.getSpatialObjectsGroup());   
        return dbUtils.getUnionPolygons();
    }
    
    public void createPolygonRepresentation() {
        System.out.println("Vou criar os Polignos a partir dos pontos agrupados");
        createAuxPolygonRepresentation();
        
        createdPolygons = new LinkedList<Vector<Point>>();
        List<String> groupsPolygonsCharacteristics = new LinkedList<String>();
        List<Vector<Point>> polygons2 = new LinkedList<Vector<Point>>();
        
        //Build the polygon representation and insert in auxiliary table
        Iterator<Map.Entry<String, List<Instance>>> it = clusteredObjects.getIteratorSpatialObjectsByGroup();
        while(it.hasNext()) {
            Map.Entry<String, List<Instance>> next = it.next();
            groupsPolygonsCharacteristics.add(next.getValue().get(0).getCharacterization());
            Vector<Point> array = new Vector<Point>();
            for (Instance point: next.getValue()){
                Point p = new Point(point.getPoint().getX(), point.getPoint().getY());
                array.add(p);
            }
            // Concave Hull Approach: Generates a concave polygon
            int k1 = 3; //Define the inicial K value
            ConcaveHullEngine e1 = new ConcaveHullEngine(); // Create a new engine
            Vector<Point> polygonRepresentation = e1.doConcaveHull(array, k1); //Calls the service and gets the results
            createdPolygons.add(polygonRepresentation);
            System.out.println("Adicionei novo poligono");
        }
        dbUtils.insertCharacterizedGroupsPolygons(createdPolygons,groupsPolygonsCharacteristics);
        //dbUtils.insertGroupsPolygons(createdPolygons, polygons2, 1);
        System.out.println("Fim da criacao de poligonos");
    }
    
    private void createAuxPolygonRepresentation() {
        //Create auxiliary table
        String nameTable = "polygon_point_group";
        dbUtils.executeQuery("drop table " + nameTable);
        //String query = "create table " + nameTable + " (temp_id1 number, polygon1 SDO_GEOMETRY)";
        String query = "create table " + nameTable + " (temp_id1 number, polygon1 SDO_GEOMETRY, characteristic1 varchar2(500))";
        dbUtils.executeQuery(query);
    }
    
    private String getBaseZoomLevel() {
        XMLUtils xmlUtils = new XMLUtils();
        Document metadata = xmlUtils.filetoDoc(mapUtils.getFilename());
        Element cube = xmlUtils.getElement(metadata, "cube", mapUtils.getCubeId());
        String value = cube.getElementsByTagName("basemap").item(0).getAttributes().getNamedItem("zoomLevel").getNodeValue();
        
        return value;
    }
    
    private String buildQuery(String tableId) {
        String tableName = mapUtils.getTableName(tableId);
        return "select * from " + tableName;
    }
    
    public IDatabase getClusteredObjects() {
        return clusteredObjects;
    }
    
    public Vector<Instance> getNotClusteredElements(){
        Vector<Instance> result = new Vector<Instance>();
        Map<String, ClusterObject> map = clusteredObjects.getClusterBySemanticAttr();
        Set<String> semanticValues = map.keySet();
        for(String semanticValue : semanticValues){
            int id = map.get(semanticValue).getClusterLabel();
            if( id == ClusterObject.NOISE || id == ClusterObject.UNCLASSIFIED){
                result.add(map.get(semanticValue).getInstance());
            }
        }
        return result;
    }
    
    //public Vector<double[]> getCreatedPolygons(){
    //    Vector<double[]> result = new Vector<double[]>();
    //    for(int i=0;i < createdPolygons.size(); i++)
    //        result.add(toPointsArray(i));
    //    return result;
    //}
    
    //private double[] toPointsArray(int i) {
    //    System.out.println("polygons size: " + createdPolygons.size() + " i " + i);
    //    try {
    //        if(i >= createdPolygons.size())
    //            return null;
    //        
    //        double[] result = new double[(createdPolygons.get(i).size()*2)+2];
    //        int index = 0;
    //        
    //        for(int j = 0; j < result.length-3; j+=2) {
    //            result[j] = createdPolygons.get(i).get(index).getX();
    //            result[j+1] = createdPolygons.get(i).get(index).getY();
    //            index++;
    //        }
    //        
    //        result[result.length-2] = createdPolygons.get(i).get(0).getX();
    //        result[result.length-1] = createdPolygons.get(i).get(0).getY();
    //        
    //        return result;
    //    }catch (Exception e) {
    //        System.out.println("ERRO NO toPointsArray" + e.getMessage());
    //        return null;
    //    }
    //}
    
    public Map<String, JGeometry> getNewSpatialObjects() {
        return newSpatialObjects;
    }
}
