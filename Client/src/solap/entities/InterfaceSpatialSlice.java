package solap.entities;

import java.io.Serializable;

import solap.utils.CommProtocolUtils;

public class InterfaceSpatialSlice implements Serializable {
    private String id;
    private String dimensionId;
    private String levelId;
    private String name;
    private String layerId;
    private String filterName;
    private String filterDescription;
    private String operator;
    private String value;
    private String unit;
    
    //constructor
    public InterfaceSpatialSlice(String dimensionId, String levelId, String id, String name, String layerId) {
        this.id = id;
        this.dimensionId = dimensionId;
        this.levelId = levelId;
        this.name = name;
        this.layerId = layerId;
    }
    
    //request generation
    public String getXML() {
        String temp = CommProtocolUtils.buildSpatialSlice(dimensionId, levelId, id, layerId, operator, value, unit);
        return temp;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLayerId(String layerId) {
        this.layerId = layerId;
    }

    public String getLayerId() {
        return layerId;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterDescription(String filterDescription) {
        this.filterDescription = filterDescription;
    }

    public String getFilterDescription() {
        return filterDescription;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof InterfaceSpatialSlice)) {
            return false;
        }
        final InterfaceSpatialSlice other = (InterfaceSpatialSlice)object;
        if (!(id == null ? other.id == null : id.equals(other.id))) {
            return false;
        }
        if (!(dimensionId == null ? other.dimensionId == null : dimensionId.equals(other.dimensionId))) {
            return false;
        }
        if (!(levelId == null ? other.levelId == null : levelId.equals(other.levelId))) {
            return false;
        }
        if (!(layerId == null ? other.layerId == null : layerId.equals(other.layerId))) {
            return false;
        }
        if (!(operator == null ? other.operator == null : operator.equals(other.operator))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        result = PRIME * result + ((dimensionId == null) ? 0 : dimensionId.hashCode());
        result = PRIME * result + ((levelId == null) ? 0 : levelId.hashCode());
        result = PRIME * result + ((layerId == null) ? 0 : layerId.hashCode());
        result = PRIME * result + ((operator == null) ? 0 : operator.hashCode());
        return result;
    }
}
