package solap.params;

public class SOLAPMeasureParams {
    
    //required
    private String id;
    //optional
    private String operator;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
