package solap.params;

public class SOLAPNFiltersParams {
    
    //required
    private String measureId;
    private String operator;
    private String nRows;
    //optional
    private String measureOperator;

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

    public void setNRows(String nRows) {
        this.nRows = nRows;
    }

    public String getNRows() {
        return nRows;
    }

    public void setMeasureOperator(String measureOperator) {
        this.measureOperator = measureOperator;
    }

    public String getMeasureOperator() {
        return measureOperator;
    }
}
