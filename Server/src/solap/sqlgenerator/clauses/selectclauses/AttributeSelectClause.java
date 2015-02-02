package solap.sqlgenerator.clauses.selectclauses;

import solap.params.SOLAPLevelParams;
import solap.utils.MappingUtils;

public class AttributeSelectClause extends SelectClause {
    
    private String levelId;
    private String dimensionId;
    private String attrId;
    
    protected boolean distinct;
    
    public AttributeSelectClause(MappingUtils mapUtils, SOLAPLevelParams level) {
        super(mapUtils);
        
        this.levelId = level.getId();
        this.dimensionId = level.getDimensionId();
        
        String tableId = mapUtils.getTableId(levelId, dimensionId);
        
        String tableName = mapUtils.getTableName(tableId);
        String displayAttribute = mapUtils.getAttributeColumn(mapUtils.getDisplayAttributeIdFromLevel(levelId));
        
        this.attrId = mapUtils.getDisplayAttributeIdFromLevel(levelId);
        
        groupBy.add(tableName + "." + displayAttribute);
    }
    
    public AttributeSelectClause(MappingUtils mapUtils, String attrId, String levelId, String dimensionId) {
        super(mapUtils);
        
        this.attrId = attrId;
        this.levelId = levelId;
        this.dimensionId = dimensionId;
        
        String tableId = mapUtils.getTableId(this.levelId, this.dimensionId);
        
        String tableName = mapUtils.getTableName(tableId);
        String displayAttribute = mapUtils.getAttributeColumn(attrId);
        
        groupBy.add(tableName + "." + displayAttribute);
    }

    public String toSQL() {
        String tableId = mapUtils.getTableId(levelId, dimensionId);
        //System.out.println("LevelID: " + levelId + " dimensionID: " + dimensionId + " fui buscar esta tabelaId: " +tableId);
        String tableName = mapUtils.getTableName(tableId);
        String displayAttribute = mapUtils.getAttributeColumn(attrId);
        String attributeName = mapUtils.getAttributeName(attrId);
        
        //return tableName + "." + displayAttribute + " as \"" + attributeName + "\"";
        
        //Aspas provocam um erro quando se faz o parser do xml
        if(!distinct)
            return tableName + "." + displayAttribute + " as " + attributeName;
        else
            return "DISTINCT (" + tableName + "." + displayAttribute + ") as " + attributeName;
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
    
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }
    
    public String getAttrId() {
        return attrId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof AttributeSelectClause)) {
            return false;
        }
        final AttributeSelectClause other = (AttributeSelectClause)object;
        if (!(levelId == null ? other.levelId == null : levelId.equals(other.levelId))) {
            return false;
        }
        if (!(dimensionId == null ? other.dimensionId == null : dimensionId.equals(other.dimensionId))) {
            return false;
        }
        if (!(attrId == null ? other.attrId == null : attrId.equals(other.attrId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = super.hashCode();
        result = PRIME * result + ((levelId == null) ? 0 : levelId.hashCode());
        result = PRIME * result + ((dimensionId == null) ? 0 : dimensionId.hashCode());
        result = PRIME * result + ((attrId == null) ? 0 : attrId.hashCode());
        return result;
    }


 
}
