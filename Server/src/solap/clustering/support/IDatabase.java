package solap.clustering.support;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Vector;

public interface IDatabase {

    public ClusterObject getClusterObject(String key);
    
    public void addClusterBySemanticAttr (String semanticAttr, ClusterObject clusterObject);
    
    public ClusterObject getClusterBySemanticAttr (String semanticAttr);
    
    public void addSpatialObjectByGroup(String group, Instance instanceElement);
    
    public List<Instance> getSpatialObjectsByGroup(String group);
    
    public Iterator<Map.Entry<String, List<Instance>>> getIteratorSpatialObjectsByGroup() ;
    
    public int size();
    
    public Iterator keyIterator();
    
    public Iterator<ClusterObject> clusterObjectIterator();

    public boolean contains(ClusterObject dataObject_Query);

    public void insert(ClusterObject dataObject);

    public CollectionSpatialObjects getInstances();

    public void setMinMaxValues();

    public double[] getAttributeMinValues();

    public double[] getAttributeMaxValues();
    
    public List epsilonRangeQuery(double epsilon, ClusterObject queryDataObject);

    public int radialPartitionsQuery(ClusterObject dataObject, List<ClusterObject> seedList,
                                     int radialPartitions, double eps);
    
    public Map<String, List<Instance>> getSpatialObjectsGroup();
    
    public Vector<String> getSemancticValuesInGroup(String groupName);
    
    public Map<String, ClusterObject> getClusterBySemanticAttr();
    
    public List adjacentObjects(ClusterObject queryDataObject);
}
