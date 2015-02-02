package solap.clustering.regionalizationAlgorithms;

import java.util.Iterator;

import java.util.List;

import solap.clustering.PreProcessing;
import solap.clustering.support.ClusterObject;
import solap.clustering.support.CollectionSpatialObjects;
import solap.clustering.support.Database;
import solap.clustering.support.IDatabase;
import solap.clustering.support.distancesmeasures.SharedBorderClusterObject;

public class RegionalizationAlgorithm {
    
    //Holds the number of clusters generated
    private int numberOfGeneratedClusters;
    
    // Holds spatial objects involved in clustering
    private IDatabase database;
    
    //Auxiliary variables
    private int clusterID;
    
    public RegionalizationAlgorithm() {
        
    }
    
    public IDatabase runAlgorithm(CollectionSpatialObjects objects){
        database = new Database(objects);
        for(int i = 0; i <  database.getInstances().numberInstances(); i++){
            ClusterObject dataObject = new SharedBorderClusterObject(database.getInstances().getInstance(i), Integer.toString(i), (Database) database);
            database.insert(dataObject);
            database.addClusterBySemanticAttr(dataObject.getInstance().getSemancticAttribute(), dataObject);
        }
        
        Iterator iterator = database.clusterObjectIterator();
        while (iterator.hasNext()) {
            ClusterObject dataObject = (ClusterObject) iterator.next();
            if (dataObject.getClusterLabel() == ClusterObject.UNCLASSIFIED) {
                if (expandCluster(dataObject)) {
                    clusterID++;
                    numberOfGeneratedClusters++;
                }
            }   
        }
        
        return database;
    }
    
    private boolean expandCluster(ClusterObject dataObject) {
        List seedList = database.adjacentObjects(dataObject);
        System.out.println("Adjacentes de " + dataObject.getInstance().getSemancticAttribute());
        System.out.print("[");
        for (int i = 0; i < seedList.size(); i++) {
            ClusterObject seedListDataObject = (ClusterObject) seedList.get(i);
            System.out.print(seedListDataObject.getInstance().getSemancticAttribute() + ", ");
        }
        System.out.print("]\n");
        if(seedList.size()==0) {
            System.out.println("Fui considerado noise: " + dataObject.getInstance().getSemancticAttribute());
            dataObject.setClusterLabel(ClusterObject.NOISE);
            database.addClusterBySemanticAttr(dataObject.getInstance().getSemancticAttribute(), dataObject);
            return false;
        }
        
        for (int i = 0; i < seedList.size(); i++) {
            System.out.println("Fui considerado core: " + clusterID);
            ClusterObject seedListDataObject = (ClusterObject) seedList.get(i);
            seedListDataObject.setClusterLabel(clusterID);
            
            //Create the table with information to help further aggregations <semanticAttr, groupID>
            database.addClusterBySemanticAttr(seedListDataObject.getInstance().getSemancticAttribute(), seedListDataObject);
            database.addSpatialObjectByGroup(PreProcessing.NAME_GROUP + clusterID, seedListDataObject.getInstance());
            
            if (seedListDataObject.equals(dataObject)) {
                seedList.remove(i);
                i--;
            }
        }
        
        for (int j = 0; j < seedList.size(); j++) {
            ClusterObject seedListDataObject = (ClusterObject) seedList.get(j);           
            List seedListDataObject_Neighbourhood = database.adjacentObjects(seedListDataObject);
            
            // polygon is core polygon
            if(isExpandable(seedListDataObject)) {
                for (int i = 0; i < seedListDataObject_Neighbourhood.size(); i++) {
                    ClusterObject p = (ClusterObject) seedListDataObject_Neighbourhood.get(i);
                    if (p.getClusterLabel() == ClusterObject.UNCLASSIFIED || p.getClusterLabel() == ClusterObject.NOISE) {
                        if (p.getClusterLabel() == ClusterObject.UNCLASSIFIED) {
                            seedList.add(p);
                        }
                        p.setClusterLabel(clusterID);
                        //Create the table with information to help further aggregations <semanticAttr, groupID>
                        database.addClusterBySemanticAttr(p.getInstance().getSemancticAttribute(), p);
                        database.addSpatialObjectByGroup(PreProcessing.NAME_GROUP + clusterID, p.getInstance());
                    }
                }
            }
            
            seedList.remove(j);
            j--;
        }
        
        return true;
    }
    
    private boolean isExpandable(ClusterObject dataObject) {    
        // Number of adjacent objects
        int numberPartitions = database.adjacentObjects(dataObject).size();
        if(numberPartitions > 0){
            return true;
        }
        return false;
    }
}
