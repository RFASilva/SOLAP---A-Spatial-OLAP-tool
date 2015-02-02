package solap.entities;

public class SOLAPMappedDimension {
    private String table;
    private String key;
    
    public SOLAPMappedDimension() {
    }
    
    public SOLAPMappedDimension(String table, String key) {
        this.table = table;
        this.key = key;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
