package solap.params;

public class SOLAPFieldFilterParams {
    
    //required
    private String measureId;
    private String operator;
    private String value1;
    
    //optional
    private String value2;

    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }

    public String getMeasureId() {
        return measureId;
    }

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
}
