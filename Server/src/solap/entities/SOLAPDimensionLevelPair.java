package solap.entities;

public class SOLAPDimensionLevelPair {
    
    private String dimensionId;
    private String levelId;
    
    public SOLAPDimensionLevelPair(String dimensionId, String levelId) {
        this.dimensionId = dimensionId;
        this.levelId = levelId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getDimensionId() {
        return dimensionId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevelId() {
        return levelId;
    }
}
