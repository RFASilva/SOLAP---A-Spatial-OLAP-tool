package solap.clustering.support;


public class EpsilonRangeElement {

    private ClusterObject dataObject;
    private double distance;

    public EpsilonRangeElement(double distance, ClusterObject dataObject) {
        this.distance = distance;
        this.dataObject = dataObject;
    }

    public double getDistance() {
        return distance;
    }

    public ClusterObject getClusterObject() {
        return dataObject;
    }
}
