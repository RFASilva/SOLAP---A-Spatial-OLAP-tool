package solap.entities;

import java.io.Serializable;

public class SOLAPAttribute implements Serializable {
    private String dimensionId;
    private String levelId;
    private String id;
    private String name;
    
    public SOLAPAttribute(String id, String name, String dimensionId, String levelId) {
        super();
        this.id = id;
        this.name = name;
        this.dimensionId = dimensionId;
        this.levelId = levelId;
    }
    
    public SOLAPAttribute() {
        
    }
    
    //****** required for converter ******
    public String toString() {
        return id + ":" + name + ":" + dimensionId + ":" + levelId;
    }
    @Override
    public int hashCode() {      
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((dimensionId == null) ? 0 : dimensionId.hashCode());
        result = prime * result + ((levelId == null) ? 0 : levelId.hashCode());
        
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SOLAPAttribute other = (SOLAPAttribute)obj;
        
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (dimensionId == null) {
            if (other.dimensionId != null)
                return false;
        } else if (!dimensionId.equals(other.dimensionId))
            return false;
        if (levelId == null) {
            if (other.levelId != null)
                return false;
        } else if (!levelId.equals(other.levelId))
            return false;
        return true;
    }
    //************************************

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getDimensionId() {
        return dimensionId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevelId() {
        return levelId;
    }
}