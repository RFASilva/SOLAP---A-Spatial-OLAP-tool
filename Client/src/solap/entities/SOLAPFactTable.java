package solap.entities;

import java.io.Serializable;

public class SOLAPFactTable implements Serializable {
    
    private String id;
    private String name;

    public SOLAPFactTable(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public String generateGeneralizationXML(){
        String request = "<facttable id=\""+id+"\" name=\"" + name + "\"/>\n";
        return request;
    }
}
