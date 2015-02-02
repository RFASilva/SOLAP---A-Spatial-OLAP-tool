package solap.sqlgenerator.clauses.whereclauses;

import solap.utils.MappingUtils;

public class RestrictionWhereClause extends WhereClause {
    
    private String table1;
    private String attr1;
    
    private String table2;
    private String attr2;
    
    public RestrictionWhereClause(MappingUtils mapUtils, String dimensionId, String factTableName) {
        super(mapUtils);
        String tableId = mapUtils.getDimensionTableId(dimensionId);
        String foreignKey = mapUtils.getForeignKey(factTableName, mapUtils.getTableName(tableId));
        table1 = factTableName;
        attr1 = foreignKey;
        table2 = mapUtils.getDimensionName(dimensionId);
        attr2 = mapUtils.getKeyColumn(dimensionId);
    }
    
    //Constructor to deal with snowflaked dimensions
    public RestrictionWhereClause(MappingUtils mapUtils, String levelId, String dimensionId, String snowflaked) {
        super(mapUtils);
        table1 = mapUtils.getTableName(mapUtils.getDimensionTableId(dimensionId));
        String tableId1 = mapUtils.getTableId(levelId, dimensionId);
        table2 = mapUtils.getTableName(tableId1);
        attr2 = mapUtils.getAttributeColumn(mapUtils.getPrimaryAttributeIdFromLevel(levelId));
        attr1 = mapUtils.getForeignKey(table1, table2);
    }

    public String toSQL() {
        return table1 + "." + attr1 + "=" + table2 + "." + attr2;
    }
    
    public String getTable1() {
        return table1;
    }

    public String getTable2() {
        return table2;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof RestrictionWhereClause)) {
            return false;
        }
        final RestrictionWhereClause other = (RestrictionWhereClause)object;
        if (!(table1 == null ? other.table1 == null : table1.equals(other.table1))) {
            return false;
        }
        if (!(attr1 == null ? other.attr1 == null : attr1.equals(other.attr1))) {
            return false;
        }
        if (!(table2 == null ? other.table2 == null : table2.equals(other.table2))) {
            return false;
        }
        if (!(attr2 == null ? other.attr2 == null : attr2.equals(other.attr2))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = super.hashCode();
        result = PRIME * result + ((table1 == null) ? 0 : table1.hashCode());
        result = PRIME * result + ((attr1 == null) ? 0 : attr1.hashCode());
        result = PRIME * result + ((table2 == null) ? 0 : table2.hashCode());
        result = PRIME * result + ((attr2 == null) ? 0 : attr2.hashCode());
        return result;
    }


}
