package solap.sqlgenerator.clauses.selectclauses;

import java.util.LinkedList;
import java.util.List;

import solap.params.SOLAPLevelParams;

import solap.sqlgenerator.Query;

import solap.utils.MappingUtils;

public class SpatialSelectClause extends SelectClause {
    
    private String attrId;
    private String levelId;
    private String dimensionId;
    
    private static boolean spatialCentroid;
    
    public SpatialSelectClause(MappingUtils mapUtils, SOLAPLevelParams level) {
        super(mapUtils);
        
        this.spatialCentroid = false;
        this.levelId = level.getId();
        this.attrId = mapUtils.getSpatialAttributeIdFromLevel(this.levelId);
        this.dimensionId = level.getDimensionId();
        
        super.groupBy = generateGroupBy(this.levelId, this.dimensionId);
    }

    public String toSQL() {
        return generateClause(attrId, levelId, dimensionId, true, "glocal");
    }
    
    public static String generateClause(String attrId, String levelId, String dimensionId, boolean withAlias, String alias) {
        String attributeName = mapUtils.getAttributeColumn(attrId);
        String tableId = mapUtils.getTableId(levelId, dimensionId);
        String tableName = mapUtils.getTableName(tableId);

        String primaryAttributeId = mapUtils.getPrimaryAttributeIdFromLevel(levelId);
        String primaryAttribute = mapUtils.getAttributeColumn(primaryAttributeId);
        
        String result = "(SELECT " + attributeName + " FROM " + tableName + " " + tableName + "x" + " WHERE " + tableName + "x." + primaryAttribute + "=" + tableName + "." + primaryAttribute + ")";
        
        if(spatialCentroid)
            result = "SDO_GEOM.SDO_CENTROID("+result+",10)";
        
        if(withAlias)
            result+= " as " + alias;
        
        return result;
    }
    
    public static List<String> generateGroupBy(String levelId, String dimensionId) {
        List<String> result = new LinkedList<String>();
        
        String tableId = mapUtils.getTableId(levelId, dimensionId);
        String tableName = mapUtils.getTableName(tableId);

        String primaryAttributeId = mapUtils.getPrimaryAttributeIdFromLevel(levelId);
        String primaryAttribute = mapUtils.getAttributeColumn(primaryAttributeId);
        String displayAttribute = mapUtils.getAttributeColumn(mapUtils.getDisplayAttributeIdFromLevel(levelId));
        
        result.add(tableName + "." + primaryAttribute);
        result.add(tableName + "." + displayAttribute);
        
        return result;
    }
    
    public String getTable() {
        String tableId = mapUtils.getTableId(levelId, dimensionId);
        return mapUtils.getTableName(tableId);
    }
    
    public String getDimensionId() {
        return dimensionId;
    }
    
    public String getLevelId() {
        return levelId;
    }
    
    public String getAttrId() {
        return attrId;
    }
    
    public void setSpatialCentroid(boolean spatialCentroid) {
        this.spatialCentroid = spatialCentroid;
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SpatialSelectClause)) {
            return false;
        }
        final SpatialSelectClause other = (SpatialSelectClause)object;
        if (!(attrId == null ? other.attrId == null : attrId.equals(other.attrId))) {
            return false;
        }
        if (!(levelId == null ? other.levelId == null : levelId.equals(other.levelId))) {
            return false;
        }
        if (!(dimensionId == null ? other.dimensionId == null : dimensionId.equals(other.dimensionId))) {
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
        return result;
    }
}
