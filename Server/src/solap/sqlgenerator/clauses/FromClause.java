package solap.sqlgenerator.clauses;

import solap.utils.MappingUtils;

public class FromClause {
    
    protected String table;
    protected MappingUtils mapUtils;
    protected String alias;

    public FromClause(MappingUtils mapUtils, String dimensionId) {
        String tableId = mapUtils.getDimensionTableId(dimensionId);
        this.table = mapUtils.getTableName(tableId);
        this.mapUtils = mapUtils;
        alias = table;
    }
    
    public FromClause(MappingUtils mapUtils, String levelId, String dimensionId) {
        String tableId = mapUtils.getTableId(levelId, dimensionId);
        this.table = mapUtils.getTableName(tableId);
        this.mapUtils = mapUtils;
        alias = table;
    }
    
    public FromClause(String tableName) {
        this.table = tableName;
    }
    
    public FromClause(MappingUtils mapUtils) {
        this.mapUtils = mapUtils;
    }
    
    public FromClause (String table, String alias) {
        this.table = table;
        this.alias = alias;
    }
    
    public String toSQL() {
        return table;
    }
    
    public String toSQLWithAlias() {
        return table + " " + alias;        
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof FromClause)) {
            return false;
        }
        final FromClause other = (FromClause)object;
        if (!(table == null ? other.table == null : table.equals(other.table))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((table == null) ? 0 : table.hashCode());
        return result;
    }

    public String getAlias() {
        return alias;
    }

    public String getTable() {
        return table;
    }
}
