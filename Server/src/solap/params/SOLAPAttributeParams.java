package solap.params;

public class SOLAPAttributeParams {
    
    //required
    private String id;
    private String levelId;
    private String dimensionId;
    
    public SOLAPAttributeParams() {
        
    }
    
    public SOLAPAttributeParams(String id, String levelId, String dimensionId) {
        this.id = id;
        this.levelId = levelId;
        this.dimensionId = dimensionId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getDimensionId() {
        return dimensionId;
    }
}
