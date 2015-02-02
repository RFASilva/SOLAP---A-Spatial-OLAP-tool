package solap.clustering.spatialAlgorithms;

import java.util.Collections;
import java.util.Iterator;

import java.util.LinkedList;
import java.util.List;

import solap.clustering.PreProcessing;
import solap.clustering.support.ClusterObject;
import solap.clustering.support.CollectionSpatialObjects;
import solap.clustering.support.Database;
import solap.clustering.support.IDatabase;
import solap.clustering.support.InputParams;
import solap.clustering.support.PriorityQueue;

public class MixedDBScan extends DBScan {
    public MixedDBScan() {
        super();
    }
    
    public IDatabase runAlgorithm(CollectionSpatialObjects instances, InputParams params) throws Exception {
        System.out.println("ENTREI NO ALGORITMOS DE AGRUPAMENTO MISTO!!!");
        long time_1 = System.currentTimeMillis();
        
        this.minPoints = 3;
        this.epsilon = (Double) params.getParameter(0);

        processed_InstanceID = 0;
        numberOfGeneratedClusters = 0;
        clusterID = 0;
        
        database = new Database(instances);
        
        for (int i = 0; i < database.getInstances().numberInstances(); i++) {
            ClusterObject dataObject = dataObjectByDistanceMeasure(database.getInstances().getInstance(i),
                    Integer.toString(i),
                    (Database) database);
         
            database.insert(dataObject);
            database.addClusterBySemanticAttr(dataObject.getInstance().getSemancticAttribute(), dataObject);
        }
        
        database.setMinMaxValues();
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

        long time_2 = System.currentTimeMillis();
        elapsedTime = (double) (time_2 - time_1) / 1000.0;
        
        return database;
    }
    
    private boolean expandCluster(ClusterObject dataObject) {
        List seedList = database.epsilonRangeQuery(getEpsilon(), dataObject);
        
        // ClusterObject is no core object
        if (seedList.size() < getMinPoints()) {
            dataObject.setClusterLabel(ClusterObject.NOISE);
            database.addClusterBySemanticAttr(dataObject.getInstance().getSemancticAttribute(), dataObject);
            return false;
        }
        
        // ClusterObject is core object
        for (int i = 0; i < seedList.size(); i++) {
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
            
            List seedListDataObject_Neighbourhood = database.epsilonRangeQuery(getEpsilon(), seedListDataObject);

            if (seedListDataObject_Neighbourhood.size() >= getMinPoints()) {
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
    
    public static List<Double> sortedKDistGraph (CollectionSpatialObjects instances, int k) {
        List<Double> sortedKDistGraph = new LinkedList<Double>();
        
        IDatabase database = new Database(instances);
        distanceMeasure = "solap.clustering.support.distancesmeasures.EuclidianSemanticClusterObject";
        for (int i = 0; i < database.getInstances().numberInstances(); i++) {
            ClusterObject dataObject = dataObjectByDistanceMeasure(database.getInstances().getInstance(i), Integer.toString(i), (Database) database);
            database.insert(dataObject);
            database.addClusterBySemanticAttr(dataObject.getInstance().getSemancticAttribute(), dataObject);
        }
        
        database.setMinMaxValues();
        Iterator<ClusterObject> it = database.clusterObjectIterator();
        while(it.hasNext()) {
            ClusterObject obj = it.next();
            PriorityQueue kNeighborhoods = firstKNeighborhoods(k,obj, database);
            //getPriority(0) reference the k priority position
            sortedKDistGraph.add(kNeighborhoods.getPriority(0));
        }
        Collections.sort(sortedKDistGraph, Collections.reverseOrder());
        return sortedKDistGraph;
    }
    
    private static PriorityQueue firstKNeighborhoods(int k, ClusterObject clusterObject, IDatabase spatialObjects) {
        PriorityQueue priorityQueue = new PriorityQueue();
        Iterator<ClusterObject> it = spatialObjects.clusterObjectIterator();
        while(it.hasNext()) {
            ClusterObject obj = it.next();
            
            if(clusterObject.equals(obj))
                continue;
             
            double dist = clusterObject.distance(obj);
            if(priorityQueue.size() < k){
                priorityQueue.add(dist, obj);
            }
            else{
                if (dist < priorityQueue.getPriority(0)) {
                    priorityQueue.next(); //removes the highest distance
                    priorityQueue.add(dist, clusterObject);
                }
            }
        }
        return priorityQueue;
    }
    
    public static double computeEps(CollectionSpatialObjects instances, String groups, String variant) {
        List<Integer> epss = new LinkedList<Integer>();
        
        int n = 0;
        double mean = 0.0;
        double sum = 0.0;
        double beforedelta = 0.0;
        double delta = 0.0;
        
        List<Double> values = sortedKDistGraph(instances,3);
        System.out.println("VALORES DA HEURISTICA: " + values);
        
        double first = values.get(values.size()-1);
        double second = values.get(values.size()-2);
                
        for(int i = values.size(); i > 2; i--) {
            first = values.get(i-1);
            second = values.get(i-2);
            
            delta = second - first;
            sum += delta;
            n++;
            mean = sum/(n+0.0);
            
            double threshold = (mean*3);
            
            if(delta > threshold) {
                epss.add(i);
                sum = 0;
                n = 0;
            }
            
            beforedelta = delta;
            first = second;
        }
        
        double value_1 = 0;
        double value_2 = 0;
        double value_3 = 0;
        double value_4 = 0;
        double value_5 = 0;
        
        System.out.println("Quebras: " + epss);
        
        if(epss.size() >= 5) {
            value_1 = values.get(epss.get(epss.size()-1));
            value_2 = values.get(epss.get(epss.size()-2));
            value_3 = values.get(epss.get(epss.size()-3));
            value_4 = values.get(epss.get(epss.size()-4));
            value_5 = values.get(epss.get(epss.size()-5));    
        }
        else if(epss.size() == 4) {
            int index = epss.get(epss.size()-1);
            int indexPer = (index-0)/2;
            
            if(values.get(index-(indexPer)) != null)
                value_1 = values.get(index-(indexPer));
            else value_1 = 0;
            
            value_2 = values.get(epss.get(epss.size()-1));
            value_3 = values.get(epss.get(epss.size()-2));
            value_4 = values.get(epss.get(epss.size()-3));
            value_5 = values.get(epss.get(epss.size()-4));
            
        }
        else if(epss.size() == 3) {
            int index = epss.get(epss.size()-1);
            int indexPer = (index-0)/2;
            
            if(values.get(index-(indexPer)) != null)
                value_1 = values.get(index-(2*indexPer));
            else value_1 = 0;
            
            value_2 = values.get(epss.get(epss.size()-1));
            value_3 = values.get(epss.get(epss.size()-2));
            value_4 = values.get(epss.get(epss.size()-3));
            
            index = values.size()-index;
            indexPer = ((values.size() - index)/2);
            if(values.get(index+indexPer) != null)
                value_4 = values.get(index+indexPer);
            else value_4 = 0.0;
            
        }
        else if(epss.size() == 2) {
            int index = epss.get(epss.size()-1);
            int indexPer = (index-0)/4;
            if(values.get(index-(2*indexPer)) != null)
                value_1 = values.get(index-(2*indexPer));
            else value_1 = 0;
            
            if(values.get(index-indexPer) != null)
                value_2 = values.get(index-indexPer);
            else value_2 = 0.0;
            
            
            value_3 = values.get(epss.get(epss.size()-1));
            index = values.size()-index;
            indexPer = ((values.size() - index)/4);
            if(values.get(index+indexPer) != null)
                value_4 = values.get(index+indexPer);
            else value_4 = 0.0;
            
            if(values.get(index+(2*indexPer)) != null)
                value_5 = values.get(index+(2*indexPer));
            else value_5 = 0.0;
            
        }
        else if(epss.size() == 1) {
            int index = epss.get(epss.size()-1);
            int indexPer = (index-0)/4;
            value_1 = values.get(index-(2*indexPer));
            value_2 = values.get(index-indexPer);
            value_3 = values.get(epss.get(epss.size()-1));
            index = values.size()-index;
            indexPer = ((values.size() - index)/4);
            value_4 = values.get(index+indexPer);
            value_5 = values.get(index+(2*indexPer));
        }
        
        System.out.println("HEURISTICA: " + value_1);
        System.out.println("HEURISTICA: " + value_2);
        System.out.println("HEURISTICA: " + value_3);
        System.out.println("HEURISTICA: " + value_4);
        System.out.println("HEURISTICA: " + value_5);
        
        if(groups.equals("-2"))  return value_1;
        else if(groups.equals("-1")) return value_2;
        else if(groups.equals("0")) return value_3;
        else if(groups.equals("1")) return value_4;
        else if(groups.equals("2")) return value_5;
        
        return -1;
    }
}
