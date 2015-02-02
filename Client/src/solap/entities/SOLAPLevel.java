package solap.entities;

import java.util.Vector;


public class SOLAPLevel {
    private String id;
    private String dimensionId;
    private String name;
    private Vector<SOLAPAttribute> attributes;
    private String spatialType = "";
    private String spatialAttr;
    private SOLAPPreComputingDistance preComputing;
    private SOLAPPreComputingSharedBorders preComputingSharedBorders;
    private String displayAttributeID;
    
    public SOLAPLevel () {   
    }
    
    public SOLAPLevel(String id, String dimensionId, String name, Vector<SOLAPAttribute> attributes, String spatialType, String spatialAttr, String displayAttributeID) {
        this.id = id;
        this.dimensionId = dimensionId;
        this.name = name;
        this.attributes = attributes;
        this.spatialType = spatialType;
        this.spatialAttr = spatialAttr;
        this.displayAttributeID = displayAttributeID;
    }
    
    public SOLAPLevel(String id, String dimensionId, String name, Vector<SOLAPAttribute> attributes, String spatialType, String spatialAttr, String displayAttributeID, SOLAPPreComputingDistance preComputing, SOLAPPreComputingSharedBorders preComputingSharedBorders) {
        this.id = id;
        this.dimensionId = dimensionId;
        this.name = name;
        this.attributes = attributes;
        this.spatialType = spatialType;
        this.spatialAttr = spatialAttr;
        this.preComputing = preComputing;
        this.preComputingSharedBorders = preComputingSharedBorders;
        this.displayAttributeID = displayAttributeID;
    }
    
    public SOLAPLevel(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAttributes(Vector<SOLAPAttribute> attributes) {
        this.attributes = attributes;
    }

    public Vector<SOLAPAttribute> getAttributes() {
        return attributes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getDimensionId() {
        return dimensionId;
    }

    public void setSpatialType(String spatialType) {
        this.spatialType = spatialType;
    }

    public String getSpatialType() {
        return spatialType;
    }

    public String getSpatialAttr() {
        return spatialAttr;
    }

    public SOLAPPreComputingDistance getPreComputing() {
        return preComputing;
    }
    
    public String getColumnRefDisplayAttribute() {
        for(SOLAPAttribute attr: attributes) {
            if(attr.getId().equals(displayAttributeID))
                return attr.getColumnRef();
        }
        return "";
    }
    
    public SOLAPAttribute getAttributeRefDisplayAttribute(){
        for(SOLAPAttribute attr: attributes) {
            if(attr.getId().equals(displayAttributeID))
                return attr;
        }
        return null;
    }

    public String getDisplayAttributeID() {
        return displayAttributeID;
    }

    public SOLAPPreComputingSharedBorders getPreComputingSharedBorders() {
        return preComputingSharedBorders;
    }
}
