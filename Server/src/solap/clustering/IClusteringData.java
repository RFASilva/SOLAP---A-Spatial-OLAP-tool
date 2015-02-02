package solap.clustering;

import solap.clustering.support.CollectionSpatialObjects;
import solap.clustering.support.IDatabase;
import solap.clustering.support.InputParams;

public interface IClusteringData {
    
    //Algorithms implemented
    public enum pointAlgorithm {DBSCAN, MIXEDDBSCAN};
    public enum polygonAlgorithm {PDBSCAN};
    
    public IDatabase applySpatialPointAlgorithm(pointAlgorithm algorithm, InputParams params, CollectionSpatialObjects instances);
    
    public IDatabase applySpatialPolygonAlgorithm(polygonAlgorithm algorithm, InputParams params, CollectionSpatialObjects instances);
}
