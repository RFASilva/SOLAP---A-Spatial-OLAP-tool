package solap.sqlgenerator.clauses.whereclauses;

import java.util.Map;
import solap.params.SOLAPSpatialSliceParams;
import solap.utils.MappingUtils;

public class SpatialSliceWhereClause extends WhereClause {
    private String attrId;
    private String levelId;
    private String dimensionId;
    private String layerId;
    private String spatialOperator;
    
    //Information depending on type of spatial operator
    private String distance;
    private String unit;
    
    private String value;
    
    private Map<String, String> tableAlias;
    
    public SpatialSliceWhereClause(MappingUtils mapUtils, Map<String, String> tableAlias, SOLAPSpatialSliceParams spatialSlice) {
        super(mapUtils);
        
        this.tableAlias = tableAlias;
        
        attrId = spatialSlice.getAttributeId();
        levelId = spatialSlice.getLevelId();
        dimensionId = spatialSlice.getDimensionId();
        layerId = spatialSlice.getLayerId();
        this.spatialOperator = spatialSlice.getOperator();
        
        if (spatialOperator.equals("WITHIN_DISTANCE")) {
            distance = spatialSlice.getValue();
            unit = spatialSlice.getUnit();   
        }
        else if (spatialOperator.equals("NEIGHBOURS")) value = spatialSlice.getValue();     
    }

    public String toSQL() {
        if (spatialOperator.equals("WITHIN_DISTANCE")) return toSQLWithinDistance();    
        else if (spatialOperator.equals("INSIDE")) return toSQLInside();
        else if (spatialOperator.equals("NEIGHBOURS")) return toSQLNeighbours();
        
        //TODO: more spatial operators
        return null;
    }
    
    private String toSQLWithinDistance() {
        String colName = mapUtils.getAttributeColumn(attrId);
        String tableId = mapUtils.getTableId(levelId, dimensionId);
        String tableName = mapUtils.getTableName(tableId);
        
        String layerTable = mapUtils.getLayerTableName(layerId);
        String geomAttribute = mapUtils.getLayerGeomAttribute(layerId);
        
        return "SDO_WITHIN_DISTANCE (" + tableAlias.get(tableName) + "." + colName + ", " + tableAlias.get(layerTable) 
               + "." + geomAttribute + ", 'distance = " + distance + " unit = " + unit + "')='TRUE'";
    }
    
    private String toSQLInside() {
        String colName = mapUtils.getAttributeColumn(attrId);
        String tableId = mapUtils.getTableId(levelId, dimensionId);
        String tableName = mapUtils.getTableName(tableId);
        
        String layerTable = mapUtils.getLayerTableName(layerId);
        String geomAttribute = mapUtils.getLayerGeomAttribute(layerId);
        return "SDO_INSIDE (" + tableAlias.get(tableName) + "." + colName + ", " + tableAlias.get(layerTable) + "." + geomAttribute + ")='TRUE'";
    }
    
    private String toSQLNeighbours() {
        // TODO
        return null;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SpatialSliceWhereClause)) {
            return false;
        }
        final SpatialSliceWhereClause other = (SpatialSliceWhereClause)object;
        if (!(attrId == null ? other.attrId == null : attrId.equals(other.attrId))) {
            return false;
        }
        if (!(levelId == null ? other.levelId == null : levelId.equals(other.levelId))) {
            return false;
        }
        if (!(dimensionId == null ? other.dimensionId == null : dimensionId.equals(other.dimensionId))) {
            return false;
        }
        if (!(layerId == null ? other.layerId == null : layerId.equals(other.layerId))) {
            return false;
        }
        if (!(spatialOperator == null ? other.spatialOperator == null : spatialOperator.equals(other.spatialOperator))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = super.hashCode();
        result = PRIME * result + ((attrId == null) ? 0 : attrId.hashCode());
        result = PRIME * result + ((levelId == null) ? 0 : levelId.hashCode());
        result = PRIME * result + ((dimensionId == null) ? 0 : dimensionId.hashCode());
        result = PRIME * result + ((layerId == null) ? 0 : layerId.hashCode());
        result = PRIME * result + ((spatialOperator == null) ? 0 : spatialOperator.hashCode());
        return result;
    }
}
