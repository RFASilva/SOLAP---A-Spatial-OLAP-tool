package solap.clustering.spatialAlgorithms;

import java.lang.reflect.Constructor;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import solap.clustering.PreProcessing;
import solap.clustering.support.CollectionSpatialObjects;
import solap.clustering.support.ClusterObject;
import solap.clustering.support.Database;
import solap.clustering.support.IDatabase;
import solap.clustering.support.InputParams;
import solap.clustering.support.Instance;
import solap.clustering.support.PriorityQueue;

public class PDBSCAN {
  
    //Specifies the next parameter: the radius for a range-query
    private double epsilon = 2.0;

    //Specifies the next parameter: the density (the range-query must contain at least minPolygons DataObjects)
    private int minPolygons = 2;
    
    //Specifies the next parameter: the number of equal-size sectors radially partitioning the space around e polygon p
    private int radialPartitions = 6;
    
    //Specifies the next parameter: the number of radial spatial partions non-empty
    private int minS = 5;

    //Holds the number of clusters generated
    private int numberOfGeneratedClusters;

    //Holds the distance-type that is used
    private static String distanceMeasure = "solap.clustering.support.distancesmeasures.HausdorffModifiedClusterObject";
    
    // Holds spatial objects involved in clustering
    private IDatabase database;

    //Auxiliary variables
    private int clusterID;
    private int processed_InstanceID;
    private double elapsedTime;

    public PDBSCAN() {
    }
    
    public IDatabase runAlgorithm(CollectionSpatialObjects instances, InputParams params) throws Exception {
        long time_1 = System.currentTimeMillis();

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
        
        // polygon is not core polygon
        if(!isExpandable(dataObject, seedList)) {
            dataObject.setClusterLabel(ClusterObject.NOISE);
            database.addClusterBySemanticAttr(dataObject.getInstance().getSemancticAttribute(), dataObject);
            return false;
        }
        // polygon is core object
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
            List seedListDataObject_Neighbourhood = database.epsilonRangeQuery(getEpsilon(), dataObject);
            
            // polygon is core polygon
            if(isExpandable(seedListDataObject, seedListDataObject_Neighbourhood)) {
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

    public int numberOfClusters() throws Exception {
        return numberOfGeneratedClusters;
    }

    public static ClusterObject dataObjectByDistanceMeasure(Instance instance, String key, Database database) {
        Object o = null;

        Constructor co = null;
        try {
            Class clas = (Class.forName(distanceMeasure));
            co = clas.getConstructor(new Class[]{Instance.class, String.class, Database.class});
            
            o = co.newInstance(new Object[]{instance, key, database});
        } catch (Exception e) {
            System.out.println("Error instantiating the cluster object: " + e.getMessage());
        }

        return (ClusterObject) o;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public String getDistanceMeasure() {
        return distanceMeasure;
    }

    public void setDistanceMeasure(String database_distanceType) {
        this.distanceMeasure = database_distanceType;
    }
    
    private boolean isExpandable(ClusterObject dataObject, List<ClusterObject> seedList) {    
        //Number of radial spatial partitons that are non-empty
        int numberPartitions = database.radialPartitionsQuery(dataObject, seedList, radialPartitions, epsilon);
        
        if(numberPartitions >= minS) {
            if(seedList.size() >= minPolygons) {
                return true;
            }
            return false;   
        }
        
        return false;
    }
    
    public static List<Double> sortedKDistGraph (CollectionSpatialObjects instances, int k) {
        List<Double> sortedKDistGraph = new LinkedList<Double>();
        //Value of k: 2*dimension-1
        //int k = (2*2)-1;
        
        IDatabase database = new Database(instances);
        distanceMeasure = "solap.clustering.support.distancesmeasures.HausdorffModifiedClusterObject";
        for (int i = 0; i < database.getInstances().numberInstances(); i++) {
            ClusterObject dataObject = dataObjectByDistanceMeasure(database.getInstances().getInstance(i),
                    Integer.toString(i),
                    (Database) database);
         
            database.insert(dataObject);
            database.addClusterBySemanticAttr(dataObject.getInstance().getSemancticAttribute(), dataObject);
        }
        
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
             
            double dist = spatialObjects.getInstances().getDistanceBetweenObjects(clusterObject.getInstance().getSemancticAttribute(),
                                                                                                  obj.getInstance().getSemancticAttribute());
            
            if(priorityQueue.size() < k) {
                priorityQueue.add(dist, obj);
            } else {
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
        System.out.println("ESTOU DENTRO DO computeEps do PDBSCAN");
        int n = 0;
        double mean = 0.0;
        double sum = 0.0;
        double beforedelta = 0.0;
        double delta = 0.0;
        
        List<Double> values = sortedKDistGraph(instances,3);
        System.out.println(values.isEmpty());
        System.out.println("1");
        double first = values.get(values.size()-1);
        double second = values.get(values.size()-2);
        for(int j=3; j >= 1; j--) {
            for(int i = values.size()-1; i > 2; i--) {
                first = values.get(i-1);
                second = values.get(i-2);
                
                delta = second - first;
                sum += delta;
                n++;
                mean = sum/(n+0.0);
                
                double threshold = (mean*j);
                System.out.println("i: " + i);
                if(delta > threshold) {
                    System.out.println("AQUI");
                    epss.add(i);
                    sum = 0;
                    n = 0;
                }
                
                beforedelta = delta;
                first = second;
            }
            
            if(!(epss.size() == 0)) {
                break;
            }
        
        }
        
        System.out.println("2");
            
        if(epss.size() == 0) {    
            for(int i = values.size(); i > (values.size() - 5); i--) {
                epss.add(i);
            }
            
            if(values.size()<5){
                for(int i = values.size(); i < 5; i++) {
                    epss.add(values.size()-1);
                }
            }
        }
        
        System.out.println("3");
        
        double value_1 = 0;
        double value_2 = 0;
        double value_3 = 0;
        double value_4 = 0;
        double value_5 = 0;
        
        if(epss.size() >= 5) {
            System.out.println("ESTOU COM TAMANHO >= 5");
            System.out.println("values: " + values);
            System.out.println("epss: " + epss);
            
            value_1 = values.get(epss.get(epss.size()-1));
            System.out.println("CHEGUEI AQUI");
            value_2 = values.get(epss.get(epss.size()-2));
            System.out.println("CHEGUEI AQUI");
            value_3 = values.get(epss.get(epss.size()-3));
            System.out.println("CHEGUEI AQUI");
            value_4 = values.get(epss.get(epss.size()-4));
            System.out.println("CHEGUEI AQUI");
            value_5 = values.get(epss.get(epss.size()-5));
            System.out.println("CHEGUEI AQUI");
        }
        else if(epss.size() == 4) {
            System.out.println("ESTOU COM TAMANHO == 4");
            //TODO
        }
        else if(epss.size() == 3) {
            System.out.println("ESTOU COM TAMANHO == 3");
            //TODO
        }
        else if(epss.size() == 2) {
            System.out.println("ESTOU COM TAMANHO == 2");
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
            System.out.println("ESTOU COM TAMANHO == 1");
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
        
        System.out.println("4");
        
        if(groups.equals("-2"))  return value_1;
        else if(groups.equals("-1")) return value_2;
        else if(groups.equals("0")) return value_3;
        else if(groups.equals("1")) return value_4;
        else if(groups.equals("2")) return value_5;
        
        return -1;
    }
}
