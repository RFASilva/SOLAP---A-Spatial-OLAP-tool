package solap.clustering;

import solap.clustering.spatialAlgorithms.DBScan;
import solap.clustering.spatialAlgorithms.MixedDBScan;
import solap.clustering.spatialAlgorithms.PDBSCAN;
import solap.clustering.support.CollectionSpatialObjects;
import solap.clustering.support.IDatabase;
import solap.clustering.support.InputParams;

public class ClusteringData implements IClusteringData  {
    
    public ClusteringData() {
    }

    public IDatabase applySpatialPointAlgorithm(IClusteringData.pointAlgorithm algorithm, InputParams params,
                                           CollectionSpatialObjects instances) {
        
        if(algorithm.equals(IClusteringData.pointAlgorithm.DBSCAN)) {
            try {
                applyZoomFactor(params);
                
                DBScan dbscan = new DBScan();
                
                boolean areHigherAttr = instances.getInstance(0).getHigherValues().size() > 0;
                boolean basedOnHierarchies = !instances.getInstance(0).getAttributeHigherLevel().equals(""); 
         
                if(areHigherAttr && basedOnHierarchies)
                    dbscan.setDistanceMeasure("solap.clustering.support.distancesmeasures.EuclidianObjHigherAttrAndBasedHierarchies");
                else if(areHigherAttr)
                    dbscan.setDistanceMeasure("solap.clustering.support.distancesmeasures.EuclidianClusterObjectHigherAttr");
                else if(basedOnHierarchies)
                    dbscan.setDistanceMeasure("solap.clustering.support.distancesmeasures.EuclidianClusterObjectBasedHierarchies");
                
                IDatabase database = dbscan.runAlgorithm(instances, params);
                
                return database;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error executing DBSCAN algorithm: " + e.getMessage());
            }
        }
        
        if(algorithm.equals(IClusteringData.pointAlgorithm.MIXEDDBSCAN)) {
            try {
                applyZoomFactor(params);
                
                MixedDBScan mdbscan = new MixedDBScan();
                mdbscan.setDistanceMeasure("solap.clustering.support.distancesmeasures.EuclidianSemanticClusterObject");
                
                IDatabase database = mdbscan.runAlgorithm(instances, params);
                
                return database;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error executing MixedDBSCAN algorithm: " + e.getMessage());
            }
        }
        
        return null;
        
    }

    public IDatabase applySpatialPolygonAlgorithm(IClusteringData.polygonAlgorithm algorithm, InputParams params,
                                             CollectionSpatialObjects instances) {
        
        if(algorithm.equals(IClusteringData.polygonAlgorithm.PDBSCAN)) {
            try {
                PDBSCAN pdbscan = new PDBSCAN();
                applyZoomFactor(params);
                
                
                boolean basedOnHierarchies = !instances.getInstance(0).getAttributeHigherLevel().equals("");
                
                if(basedOnHierarchies)
                    pdbscan.setDistanceMeasure("solap.clustering.support.distancesmeasures.HausdorffModifiedClusterObjectBasedHierarchies");
                
                
                return pdbscan.runAlgorithm(instances, params);
            } catch (Exception e) {
               // e.printStackTrace();
                System.out.println("Error executing PDBSCAN algorithm: " + e.getMessage());
            }
        }
        
        return null;
    }
    
    public void applyZoomFactor(InputParams params) { 
        double eps = (Double) params.getParameter(0);
        
        //TODO: remove hardcoded
        double maxLevels = 15;
        
        int baseZoom = Integer.parseInt((String)params.getParameter(params.size()-1));
        int zoom = Integer.parseInt((String)params.getParameter(params.size()-2));
        
        double value = (eps * (maxLevels - zoom)) / ((maxLevels - baseZoom) + 0.0);
        System.out.println("ORIGINAL EPS: " + eps  + " After zoom factor: " + value);
        params.setParameter(0, value);
    }
}
