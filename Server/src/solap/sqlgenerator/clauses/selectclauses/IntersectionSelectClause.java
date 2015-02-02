package solap.sqlgenerator.clauses.selectclauses;

import java.util.Vector;
import solap.params.SOLAPLevelParams;

import solap.sqlgenerator.Query;

import solap.utils.MappingUtils;

public class IntersectionSelectClause extends SelectClause {
    
    private String attrId1;
    private String levelId1;
    
    private String attrId2;
    private String levelId2;
    
    private String dimensionId;

    public IntersectionSelectClause(MappingUtils mapUtils, Vector<SOLAPLevelParams> levels) {
        super(mapUtils);
        
        this.levelId1 = levels.get(0).getId();
        this.attrId1 = mapUtils.getSpatialAttributeIdFromLevel(this.levelId1);
        this.dimensionId = levels.get(0).getDimensionId();
        
        this.levelId2 = levels.get(1).getId();
        this.attrId2 = mapUtils.getSpatialAttributeIdFromLevel(this.levelId2);
        
        super.groupBy.addAll(SpatialSelectClause.generateGroupBy(this.levelId1, this.dimensionId));
        super.groupBy.addAll(SpatialSelectClause.generateGroupBy(this.levelId2, this.dimensionId));
    }

    public String toSQL() {
        String result = "";
        String firstTable = SpatialSelectClause.generateClause(this.attrId1, this.levelId1, this.dimensionId, false, "");
        String secondTable = SpatialSelectClause.generateClause(this.attrId2, this.levelId2, this.dimensionId, false, "");
        super.alias = "glocal";
        
        String idin = mapUtils.getTableName(mapUtils.getTableId(levelId1, dimensionId)) + "." + mapUtils.getAttributeColumn(mapUtils.getPrimaryAttributeIdFromLevel(levelId1));
        
        result = idin + " as idin,";
        result += "SDO_GEOM.SDO_INTERSECTION(" + firstTable + ", SDO_DIM_ARRAY(SDO_DIM_ELEMENT('X', -180, 180,.00000005), SDO_DIM_ELEMENT('Y', -90, 90, .00000005)), " + secondTable + ", SDO_DIM_ARRAY(SDO_DIM_ELEMENT('X', -180, 180, .00000005), SDO_DIM_ELEMENT('Y', -90, 90, .00000005))) " + alias;
        
        return result;
    }
    
    public String getTable() {
        //When we are in intersection case the dimension is the same
        String tableId = mapUtils.getTableId(levelId1, dimensionId);
        return mapUtils.getTableName(tableId);
    }
    
    public String getDimensionId() {
        return dimensionId;
    }
    
    public String getLevelId() {
        return Query.EMPTY;
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof IntersectionSelectClause)) {
            return false;
        }
        final IntersectionSelectClause other =
            (IntersectionSelectClause)object;
        if (!(attrId1 == null ? other.attrId1 == null : attrId1.equals(other.attrId1))) {
            return false;
        }
        if (!(levelId1 == null ? other.levelId1 == null : levelId1.equals(other.levelId1))) {
            return false;
        }
        if (!(attrId2 == null ? other.attrId2 == null : attrId2.equals(other.attrId2))) {
            return false;
        }
        if (!(levelId2 == null ? other.levelId2 == null : levelId2.equals(other.levelId2))) {
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
        result = PRIME * result + ((attrId1 == null) ? 0 : attrId1.hashCode());
        result = PRIME * result + ((levelId1 == null) ? 0 : levelId1.hashCode());
        result = PRIME * result + ((attrId2 == null) ? 0 : attrId2.hashCode());
        result = PRIME * result + ((levelId2 == null) ? 0 : levelId2.hashCode());
        result = PRIME * result + ((dimensionId == null) ? 0 : dimensionId.hashCode());
        return result;
    }


}
