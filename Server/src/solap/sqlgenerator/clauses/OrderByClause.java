package solap.sqlgenerator.clauses;

import solap.utils.MappingUtils;

public class OrderByClause {
    
    private String table;
    private String attr;


    public OrderByClause(MappingUtils mapUtils, String attrId, String levelId, String dimensionId) {
        this.table = mapUtils.getTableName(mapUtils.getTableId(levelId, dimensionId));
        this.attr = mapUtils.getAttributeColumn(attrId);
    }
    
    public String toSQL() {
        return table + "." + attr;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof OrderByClause)) {
            return false;
        }
        final OrderByClause other = (OrderByClause)object;
        if (!(table == null ? other.table == null : table.equals(other.table))) {
            return false;
        }
        if (!(attr == null ? other.attr == null : attr.equals(other.attr))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((table == null) ? 0 : table.hashCode());
        result = PRIME * result + ((attr == null) ? 0 : attr.hashCode());
        return result;
    }

}
