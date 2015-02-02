package solap.clustering.support;

public interface ClusterObject {

    static final int UNCLASSIFIED = -1;
    static final int NOISE = Integer.MIN_VALUE;
    static final double UNDEFINED = Integer.MAX_VALUE;

    public boolean equals(ClusterObject dataObject);

    public double distance(ClusterObject dataObject);
    
    public boolean isAdjacent(ClusterObject dataObject);

    public Instance getInstance();

    public String getKey();

    public void setKey(String key);

    public void setClusterLabel(int clusterID);

    public int getClusterLabel();

    public void setProcessed(boolean processed);

    public boolean isProcessed();

}
