package solap.clustering.support.distancesmeasures;

import solap.clustering.support.ClusterObject;
import solap.clustering.support.CollectionSpatialObjects;
import solap.clustering.support.Database;
import solap.clustering.support.IDatabase;
import solap.clustering.support.Instance;

public class HausdorffModifiedClusterObject implements ClusterObject {

    private Instance instance;
    private String key;
    private int clusterID;

    // Holds the status for this ClusterObject (true, if it has been processed, else false)
    private boolean processed;

    //Holds the coreDistance for this DataObject
    private double c_dist;
    
    //Holds the reachabilityDistance for this DataObject
    private double r_dist;

    //Holds the database, that is the keeper of this DataObject
    private IDatabase database;

    public HausdorffModifiedClusterObject(Instance originalInstance, String key, Database database) {
        this.database = database;
        this.key = key;
        instance = originalInstance;
        clusterID = ClusterObject.UNCLASSIFIED;
        processed = false;
        c_dist = ClusterObject.UNDEFINED;
        r_dist = ClusterObject.UNDEFINED;
    }

    public boolean equals(ClusterObject dataObject) {
        if (this == dataObject) return true;
        if (!(dataObject instanceof HausdorffModifiedClusterObject)) return false;

        final HausdorffModifiedClusterObject HausdorffModifiedDataObject = (HausdorffModifiedClusterObject) dataObject;

        if (getInstance().equals(HausdorffModifiedDataObject.getInstance())) {
           return true;
        }
        
        return false;
    }

    public double distance(ClusterObject dataObject) {
        
        if (!(dataObject instanceof HausdorffModifiedClusterObject)) return Double.NaN;
        
        double distance = CollectionSpatialObjects.getDistanceBetweenObjects(getInstance().getSemancticAttribute(), dataObject.getInstance().getSemancticAttribute());
        if(distance != -1)
            return distance;
        
        return Double.NaN;
    }


    public Instance getInstance() {
        return instance;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }   

    public void setClusterLabel(int clusterID) {
        this.clusterID = clusterID;
    }

    public int getClusterLabel() {
        return clusterID;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public boolean isProcessed() {
        return processed;
    }

    public boolean isAdjacent(ClusterObject dataObject) {
        // Not implemented becouse in this case this method will not be used
        return false;
    }
}

