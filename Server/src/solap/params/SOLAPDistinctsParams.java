package solap.params;

public class SOLAPDistinctsParams {
    
    //required
    private String attributeId;
    private String levelId;
    private String dimensionId;
    //optional
    private String name;
    
    public SOLAPDistinctsParams () {
        
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeId() {
        return attributeId;
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
