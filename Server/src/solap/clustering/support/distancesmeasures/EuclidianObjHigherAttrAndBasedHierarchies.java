package solap.clustering.support.distancesmeasures;

import solap.clustering.support.ClusterObject;
import solap.clustering.support.Database;
import solap.clustering.support.Distances;
import solap.clustering.support.Instance;

public class EuclidianObjHigherAttrAndBasedHierarchies extends EuclidianClusterObject {
    
    public EuclidianObjHigherAttrAndBasedHierarchies(Instance originalInstance, String key, Database database) {
        super(originalInstance, key, database);
    }
    
    public double distance(ClusterObject dataObject) {
        if (!(dataObject instanceof EuclidianObjHigherAttrAndBasedHierarchies)) return Double.NaN;
        
        if(!getInstance().getHigherValues().equals(dataObject.getInstance().getHigherValues())) return Double.NaN;
        if(!getInstance().getAttributeHigherLevel().equals( dataObject.getInstance().getAttributeHigherLevel())) return Double.NaN;
        
        if (!getInstance().equals(dataObject.getInstance())) {
           return Distances.computeEuclidianDistance(getInstance().getPoint(), 
                                                     dataObject.getInstance().getPoint(),
                                                     database.getAttributeMinValues(),
                                                     database.getAttributeMaxValues());
        }
        return Double.NaN;
    }
}
