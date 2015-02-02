package solap.entities;

import java.io.Serializable;

import solap.utils.CommProtocolUtils;

public class InterfaceLevel implements Serializable {
    private String dimensionId;
    private String id;
    private String name;
    private String spatialType;
    private String displayAttributeIDColRef;
    private String displayAttributeID;
    
    public InterfaceLevel(String dimensionId, String id, String name, String spatialType) {
        this.dimensionId = dimensionId;
        this.id = id;
        this.name = name;
        this.spatialType = spatialType;
    }
    
    public InterfaceLevel(SOLAPLevel level) {
        this.dimensionId = level.getDimensionId();
        this.id = level.getId();
        this.name = level.getName();
        this.spatialType = level.getSpatialType();
        this.displayAttributeIDColRef = level.getColumnRefDisplayAttribute();
        this.displayAttributeID = level.getDisplayAttributeID();
    }
    
    public String getXML() {
        return CommProtocolUtils.buildLevel(dimensionId, id);
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getDimensionId() {
        return dimensionId;
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

    public void setSpatialType(String spatialType) {
        this.spatialType = spatialType;
    }

    public String getSpatialType() {
        return spatialType;
    }

    public String getDisplayAttributeIDColRef() {
        return displayAttributeIDColRef;
    }

    public String getDisplayAttributeID() {
        return displayAttributeID;
    }
}
