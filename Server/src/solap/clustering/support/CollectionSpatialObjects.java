package solap.clustering.support;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import solap.utils.ITriple;
import solap.utils.Triple;

public class CollectionSpatialObjects {
    
    //Holds the corresponding spatial level of objects
    private String levelName;
    
    //Holds the all instances of spatial objects
    private List<Instance> allInstances;
    //Holds the pre-calculated distances between objects
    private static Map<ITriple<String,String,String>, Double> distancesBetweenInstances;
    //Holds the pre-calculated shared borders between objects
    private static Map<ITriple<String,String,String>, Double> sharedBordersBetweenInstances;
    
    
    public CollectionSpatialObjects() {
        this.allInstances = new LinkedList<Instance>();
        this.distancesBetweenInstances = new HashMap<ITriple<String,String,String>, Double>();
        this.sharedBordersBetweenInstances = new HashMap<ITriple<String,String,String>, Double>();
    }
    
    public void addInstance(Instance i) {
        allInstances.add(i);
    }
    
    public int numberInstances() {
        return allInstances.size();
    }
    
    public Instance getInstance(int index) {
        return allInstances.get(index);
    }
    
    public int numberAttributes() {
        //There are only group spatial objects with 2 dimensions
        return 2;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setDistancesBetweenInstances(Map<ITriple<String, String, String>, Double> distancesBetweenInstances) {
        this.distancesBetweenInstances = distancesBetweenInstances;
    }
    
    public static double getDistanceBetweenObjects(String seman1, String seman2) {
        if(distancesBetweenInstances.get(new Triple<String, String, String>(seman1, seman2,"")) == null)
            return -1;
        
        double result = distancesBetweenInstances.get(new Triple<String, String, String>(seman1, seman2,""));
        return result;
    }
    
    public static double getSharedBordersBetweenObjects(String seman1, String seman2) {
        if(sharedBordersBetweenInstances.get(new Triple<String, String, String>(seman1, seman2,"")) == null)
            return -1;
        
        double result = sharedBordersBetweenInstances.get(new Triple<String, String, String>(seman1, seman2,""));
        return result;
    }

    public List<Instance> getAllInstances() {
        return allInstances;
    }

    public void setSharedBordersBetweenInstances(Map<ITriple<String, String, String>, Double> sharedBordersBetweenInstances) {
        this.sharedBordersBetweenInstances = sharedBordersBetweenInstances;
    }

    public Map<ITriple<String, String, String>, Double> getSharedBordersBetweenInstances() {
        return sharedBordersBetweenInstances;
    }
}
