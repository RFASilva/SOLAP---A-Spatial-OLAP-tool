package solap.clustering.support.distancesmeasures;

import solap.clustering.support.ClusterObject;
import solap.clustering.support.CollectionSpatialObjects;
import solap.clustering.support.Database;
import solap.clustering.support.IDatabase;
import solap.clustering.support.Instance;

public class SharedBorderClusterObject  implements ClusterObject {
    
    private Instance instance;
    private String key;
    private int clusterID;

    // Holds the status for this ClusterObject (true, if it has been processed, else false)
    private boolean processed;
    
    //Holds the database, that is the keeper of this DataObject
    private IDatabase database;
    
    public SharedBorderClusterObject(Instance originalInstance, String key, Database database) {
        this.database = database;
        this.key = key;
        this.instance = originalInstance;
        this.clusterID = ClusterObject.UNCLASSIFIED;
        this.processed = false;
    }

    public boolean equals(ClusterObject dataObject) {
        if (this == dataObject) return true;
        if (!(dataObject instanceof SharedBorderClusterObject)) return false;

        final SharedBorderClusterObject other = (SharedBorderClusterObject) dataObject;

        if (getInstance().equals(other.getInstance())) {
           return true;
        }
        
        return false;
    }

    public double distance(ClusterObject dataObject) {
        // Not implemented becouse in this case this method will not be used
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
        //System.out.println("ESTOU NO ISADJACENT");
        if (!(dataObject instanceof SharedBorderClusterObject)) return false;
        
        double sharedBorder = CollectionSpatialObjects.getSharedBordersBetweenObjects(getInstance().getSemancticAttribute(), dataObject.getInstance().getSemancticAttribute());
        if(sharedBorder > 0.0)
            return true;
        
        return false;
    }
}
