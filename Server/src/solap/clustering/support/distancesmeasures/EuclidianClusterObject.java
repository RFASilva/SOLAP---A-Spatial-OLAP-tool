package solap.clustering.support.distancesmeasures;

import solap.clustering.support.ClusterObject;
import solap.clustering.support.Database;
import solap.clustering.support.Distances;
import solap.clustering.support.IDatabase;
import solap.clustering.support.Instance;

public class EuclidianClusterObject implements ClusterObject {
    
    private Instance instance;
    private String key;
    private int clusterID;

    // Holds the status for this ClusterObject (true, if it has been processed, else false)
    private boolean processed;

    //Holds the database, that is the keeper of this DataObject
    protected IDatabase database;

    public EuclidianClusterObject(Instance originalInstance, String key, Database database) {
        this.database = database;
        this.key = key;
        instance = originalInstance;
        clusterID = ClusterObject.UNCLASSIFIED;
        processed = false;
    }

    public boolean equals(ClusterObject dataObject) {
        if (this == dataObject) return true;
        if (!(dataObject instanceof EuclidianClusterObject)) return false;

        final EuclidianClusterObject euclidianDataObject = (EuclidianClusterObject) dataObject;

        if (getInstance().equals(euclidianDataObject.getInstance())) {
            for (int i = 0; i < getInstance().numberAttributes(); i++) {
                double i_value_Instance_1 = getInstance().getCoordinate(i);
                double i_value_Instance_2 = euclidianDataObject.getInstance().getCoordinate(i);

                if (i_value_Instance_1 != i_value_Instance_2) return false;
            }
            return true;
        }
        
        return false;
    }

    public double distance(ClusterObject dataObject) {
        if (!(dataObject instanceof EuclidianClusterObject)) return Double.NaN;
        
        if (!getInstance().equals(dataObject.getInstance())) {
            return Distances.computeEuclidianDistance(getInstance().getPoint(), 
                                                      dataObject.getInstance().getPoint(),
                                                      database.getAttributeMinValues(),
                                                      database.getAttributeMaxValues());
            
        }
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
