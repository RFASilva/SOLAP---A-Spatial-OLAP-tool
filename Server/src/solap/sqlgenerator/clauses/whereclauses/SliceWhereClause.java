package solap.sqlgenerator.clauses.whereclauses;

import java.util.LinkedList;
import java.util.List;
import solap.utils.MappingUtils;

public class SliceWhereClause extends WhereClause {
    
    private String attrId;
    private String levelId;
    private String dimensionId;
    
    private String table;
    private String attr;
    private List<String> values;
    
    public SliceWhereClause(MappingUtils mapUtils, String attrId, String levelId, String dimensionId) {
        super(mapUtils);
        
        this.attrId = attrId;
        this.levelId = levelId;
        this.dimensionId = dimensionId;
        this.values = new LinkedList<String>();
        
        String tableId = mapUtils.getTableId(levelId, dimensionId);
        table = mapUtils.getTableName(tableId);
        
        attr = mapUtils.getAttributeColumn(attrId);
    }
    
    public String toSQL() {
        String result = "";
        for(String value: values)
            result += table + "." + attr + "=\'" + value + "\' OR ";
        result = result.substring(0, result.length()-3);
        return "(" + result + ")";
    }
    
    public void addValue(String value) {
        if(!values.contains(value))
            values.add(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SliceWhereClause)) {
            return false;
        }
        final SliceWhereClause other = (SliceWhereClause)object;
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
