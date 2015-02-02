package solap.params;

public class SOLAPSpatialSliceParams {
    
    //required
    private String attributeId;
    private String levelId;
    private String dimensionId;
    private String layerId;
    private String operator;
    //optional
    private String value;
    private String unit;

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

    public void setLayerId(String layerId) {
        this.layerId = layerId;
    }

    public String getLayerId() {
        return layerId;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public void setValue(String distance) {
        this.value = distance;
    }

    public String getValue() {
        return value;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}
