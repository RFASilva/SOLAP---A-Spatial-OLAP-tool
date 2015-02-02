package solap.entities;

import java.io.Serializable;

import java.util.Vector;

public class SOLAPHierarchy implements Serializable {
    private String id;
    private String name;
    private Vector<SOLAPLevel> levels;
    private String type;
    
    public SOLAPHierarchy () {
        
    }
    
    public SOLAPHierarchy(String id, String name, String type, Vector<SOLAPLevel> levels) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.levels = levels;
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

    public void setLevels(Vector<SOLAPLevel> levels) {
        this.levels = levels;
    }

    public Vector<SOLAPLevel> getLevels() {
        return levels;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    
    public boolean containsLevel(String id) {
        for(SOLAPLevel level: levels) {
            if(id.equals(level.getId()))
                return true;
        }
        return false;
    }
}
