package solap.clustering.support;

import java.awt.geom.Point2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import java.util.Vector;

import solap.utils.GeoUtils;

public class Database implements IDatabase {

    //Store all the ClusterObject
    private TreeMap treeMap;

    private CollectionSpatialObjects instances;
    
    //Holds information about group to each semantic attr
    private Map<String, ClusterObject> clusterBySemanticAttr;
    
    //Holds information about spatial objects belonging to each group
    private Map<String, List<Instance>> spatialObjectsGroup;
    
    //Holds the minimum value for each attribute
    private double[] attributeMinValues;

    //Holds the maximum value for each attribute
    private double[] attributeMaxValues;


    public Database(CollectionSpatialObjects instances) {
        this.instances = instances;
        this.clusterBySemanticAttr = new HashMap<String, ClusterObject>();
        treeMap = new TreeMap();
        this.spatialObjectsGroup = new HashMap<String, List<Instance>>();
    }

    public ClusterObject getClusterObject(String key) {
        return (ClusterObject) treeMap.get(key);
    }
    
    public void addClusterBySemanticAttr (String semanticAttr, ClusterObject clusterObject) {
        clusterBySemanticAttr.put(semanticAttr, clusterObject);
    }
    
    public void addSpatialObjectByGroup(String group, Instance spatialDefinition) {
        if(spatialObjectsGroup.get(group) == null)
            spatialObjectsGroup.put(group, new LinkedList<Instance>());
        
        spatialObjectsGroup.get(group).add(spatialDefinition);
    }
    
    public List<Instance> getSpatialObjectsByGroup(String group) {
        return spatialObjectsGroup.get(group);
    }
    
    public Iterator<Map.Entry<String, List<Instance>>> getIteratorSpatialObjectsByGroup() {
        return spatialObjectsGroup.entrySet().iterator();
    }

    public void setMinMaxValues() {
        attributeMinValues = new double[getInstances().numberAttributes()];
        attributeMaxValues = new double[getInstances().numberAttributes()];

        //Init
        for (int i = 0; i < getInstances().numberAttributes(); i++)
            attributeMinValues[i] = attributeMaxValues[i] = Double.NaN;
        
        if(this.getInstances().getInstance(0).getPolygon() != null) setMinMaxPolygons();
        else setMinMaxPoints();
        
    }
    
    public double[] getAttributeMinValues() {
        return attributeMinValues;
    }

    public double[] getAttributeMaxValues() {
        return attributeMaxValues;
    }

    public List epsilonRangeQuery(double epsilon, ClusterObject queryDataObject) {
        ArrayList epsilonRange_List = new ArrayList();
        Iterator iterator = clusterObjectIterator();
        while (iterator.hasNext()) {
            ClusterObject dataObject = (ClusterObject)iterator.next();
            
             double distance = queryDataObject.distance(dataObject);
            
            if (distance < epsilon) {
                epsilonRange_List.add(dataObject);
            }
        }

        return epsilonRange_List;
    }
    
    public List adjacentObjects(ClusterObject queryDataObject){
        ArrayList adjacent_List = new ArrayList();
        Iterator iterator = clusterObjectIterator();
        while (iterator.hasNext()) {
            ClusterObject dataObject = (ClusterObject)iterator.next();
            if(queryDataObject.isAdjacent(dataObject) && queryDataObject.getInstance().getCharacterization().equals(dataObject.getInstance().getCharacterization()))
                adjacent_List.add(dataObject);
        }

        return adjacent_List;
    }

    public int size() {
        return treeMap.size();
    }

    public Iterator keyIterator() {
        return treeMap.keySet().iterator();
    }

    public Iterator<ClusterObject> clusterObjectIterator() {
        return treeMap.values().iterator();
    }

    public boolean contains(ClusterObject dataObject_Query) {
        Iterator iterator = clusterObjectIterator();
        while (iterator.hasNext()) {
            ClusterObject dataObject = (ClusterObject) iterator.next();
            if (dataObject.equals(dataObject_Query)) return true;
        }
        return false;
    }

    public void insert(ClusterObject dataObject) {
        treeMap.put(dataObject.getKey(), dataObject);
    }

    public CollectionSpatialObjects getInstances() {
        return instances;
    }

    public ClusterObject getClusterBySemanticAttr (String semanticAttr) {
        return clusterBySemanticAttr.get(semanticAttr);
    }

    private void setMinMaxPoints() {
        Iterator iterator = clusterObjectIterator();
        while (iterator.hasNext()) {
            ClusterObject dataObject = (ClusterObject) iterator.next();
            for (int j = 0; j < getInstances().numberAttributes(); j++) {
                if (Double.isNaN(attributeMinValues[j])) {
                    attributeMinValues[j] = dataObject.getInstance().getCoordinate(j);
                    attributeMaxValues[j] = dataObject.getInstance().getCoordinate(j);
                } else {
                    if (dataObject.getInstance().getCoordinate(j) < attributeMinValues[j])
                        attributeMinValues[j] = dataObject.getInstance().getCoordinate(j);
                    if (dataObject.getInstance().getCoordinate(j) > attributeMaxValues[j])
                        attributeMaxValues[j] = dataObject.getInstance().getCoordinate(j);
                }
            }
        }
    }
    
    private void setMinMaxPolygons() {
        Iterator iterator = clusterObjectIterator();
        while (iterator.hasNext()) {
            ClusterObject dataObject = (ClusterObject) iterator.next();
            Polygon p = dataObject.getInstance().getPolygon();
            for(int w = 0; w < p.getNumberPoints(); w++) {
                Point2D point = p.getPoint(w);
                double[] coordinates = {point.getX(), point.getY()};
                for (int j = 0; j < coordinates.length; j++) {
                            
                    if (Double.isNaN(attributeMinValues[j])) {
                        attributeMinValues[j] = coordinates[j];
                        attributeMaxValues[j] = coordinates[j];
                    } else {
                        if (coordinates[j] < attributeMinValues[j])
                            attributeMinValues[j] = coordinates[j];
                        if (coordinates[j] > attributeMaxValues[j])
                            attributeMaxValues[j] = coordinates[j];
                    }
                }
            }
        }
    }

    public int radialPartitionsQuery(ClusterObject dataObject, List<ClusterObject> seedList,  int radialPartitions, double eps) {
        int result = 0;
        System.out.println("POLIGONOS 1");
        double anglePerPartition = 360 / (radialPartitions+0.0);
        Point2D center = GeoUtils.computeCentroidOfPolygon(dataObject.getInstance().getSpatialObject().getOrdinatesArray());
        System.out.println("POLIGONOS 2");
        
        //Initialize structure
        Map<String, Boolean> processedPartition = new HashMap<String, Boolean>(radialPartitions*2);
        for(double angle = anglePerPartition; angle <= 360; angle+=anglePerPartition) {
            double previousAngle = (angle-anglePerPartition);
            String key = previousAngle + "," + angle;
            processedPartition.put(key, false);
        }
        
        System.out.println("POLIGONOS 3");
        for(ClusterObject object: seedList) {
            double[] points = object.getInstance().getSpatialObject().getOrdinatesArray();
            for(int i = 0; i < points.length-1; i++) {
                if(findDistinctPartition(center, new Point2D.Double(points[i], points[i+1]), processedPartition)) {
                    result++;
                    if(result == radialPartitions)
                        return result;
                }
            }
        }
        System.out.println("POLIGONOS 4");
            
        return result;
    }


    private boolean findDistinctPartition(Point2D center, Point2D.Double target, Map<String, Boolean> processedPartition) {
        double angle = GeoUtils.findAngle(center, target);
        
        for(String key: processedPartition.keySet()) {
            String[] deltaAngles = key.split(",");
            double angle1 = Double.parseDouble(deltaAngles[0]);
            double angle2 = Double.parseDouble(deltaAngles[1]);
            if(angle >= angle1 && angle < angle2) {
                if(!processedPartition.get(key)) {
                    return true;
                }
                else return false;
            }
        }
        
        return false;
    }

    public Map<String, List<Instance>> getSpatialObjectsGroup() {
        return spatialObjectsGroup;
    }
    
    public Vector<String> getSemancticValuesInGroup(String groupName){
        List<Instance> list = spatialObjectsGroup.get(groupName);
        Vector<String> result = new Vector<String>();
        for(Instance instance : list)
            result.add(instance.getSemancticAttribute());
        return result;
    }

    public Map<String, ClusterObject> getClusterBySemanticAttr() {
        return clusterBySemanticAttr;
    }
}
