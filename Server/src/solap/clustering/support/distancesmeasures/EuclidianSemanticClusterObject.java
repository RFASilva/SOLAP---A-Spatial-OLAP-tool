package solap.clustering.support.distancesmeasures;

import solap.clustering.support.ClusterObject;
import solap.clustering.support.Database;
import solap.clustering.support.Distances;
import solap.clustering.support.Instance;

public class EuclidianSemanticClusterObject extends EuclidianClusterObject{
    
    public EuclidianSemanticClusterObject(Instance originalInstance, String key, Database database) {
        super(originalInstance, key, database);
    }
    
    public boolean equals(ClusterObject dataObject) {
        if (this == dataObject) return true;
        if (!(dataObject instanceof EuclidianSemanticClusterObject)) return false;

        final EuclidianSemanticClusterObject euclidianDataObject = (EuclidianSemanticClusterObject) dataObject;
        
        if(!getInstance().getCharacterization().equals(euclidianDataObject.getInstance().getCharacterization()))
            return false;

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
        //System.out.println("UTILIZEI A DISTANCE DA NOVA CLASSE CRIADA");
        if (!(dataObject instanceof EuclidianSemanticClusterObject)) return Double.NaN;
        
        if(!getInstance().getCharacterization().equals(dataObject.getInstance().getCharacterization()))
            return Double.NaN;
        
        if (!getInstance().equals(dataObject.getInstance())) {
            return Distances.computeEuclidianDistance(getInstance().getPoint(), 
                                                      dataObject.getInstance().getPoint(),
                                                      database.getAttributeMinValues(),
                                                      database.getAttributeMaxValues());
            
        }
        return Double.NaN;
    }
}
