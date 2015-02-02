package solap.clustering.support.distancesmeasures;

import solap.clustering.support.ClusterObject;
import solap.clustering.support.Database;
import solap.clustering.support.Distances;
import solap.clustering.support.Instance;

public class EuclidianClusterObjectHigherAttr extends EuclidianClusterObject {
    
    public EuclidianClusterObjectHigherAttr(Instance originalInstance, String key, Database database) {
        super(originalInstance, key, database);
    }
    
    public double distance(ClusterObject dataObject) {
        if (!(dataObject instanceof EuclidianClusterObjectHigherAttr)) return Double.NaN;
        
        //System.out.println("Higher objects1: " + dataObject.getInstance().getHigherValues());
        //System.out.println("THIS: " + getInstance().getHigherValues());
        //System.out.println("RESPONSE: "  + getInstance().getHigherValues().equals(dataObject.getInstance().getHigherValues()));
        
        if(!getInstance().getHigherValues().equals(dataObject.getInstance().getHigherValues())) return Double.NaN;
        
        if (!getInstance().equals(dataObject.getInstance())) {
        
           return Distances.computeEuclidianDistance(getInstance().getPoint(), 
                                                     dataObject.getInstance().getPoint(),
                                                     database.getAttributeMinValues(),
                                                     database.getAttributeMaxValues());
        }
        return Double.NaN;
    }
}
