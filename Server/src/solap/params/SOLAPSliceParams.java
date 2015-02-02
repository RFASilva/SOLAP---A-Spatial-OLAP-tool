package solap.params;

public class SOLAPSliceParams {
    
    //required
    private String operator;
    private String value1;
    private String attributeId;
    private String levelId;
    private String dimensionId;
    //optional
    private String value2;
    private String slider;

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue2() {
        return value2;
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

    public void setSlider(String slider) {
        this.slider = slider;
    }

    public String getSlider() {
        return slider;
    }
}
