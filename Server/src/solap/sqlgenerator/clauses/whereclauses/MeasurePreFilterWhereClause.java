package solap.sqlgenerator.clauses.whereclauses;

import solap.params.SOLAPFieldFilterParams;
import solap.utils.MappingUtils;

public class MeasurePreFilterWhereClause extends WhereClause {
    
    private String measureId;
    private String operator;
    private String value;
    
    private String factTableName;
    
    public MeasurePreFilterWhereClause(MappingUtils mapUtils, SOLAPFieldFilterParams measurePreFilter, String factTableName) {
        super(mapUtils);
        
        this.measureId = measurePreFilter.getMeasureId();
        this.operator = measurePreFilter.getOperator();
        this.value = measurePreFilter.getValue1();
        
        this.factTableName = factTableName;
    }
    
    public String toSQL() {
        String colName = mapUtils.getMeasureColumn(measureId);
        String expression = factTableName + "." + colName;
        
        return getCondition(expression, operator, value);
    }
    
    private String getCondition(String tableAttribute, String operator, String value1) {
        if (operator.compareTo("EQUAL") == 0)
            return tableAttribute + " = '" + value1 + "'";
        if (operator.compareTo("NOT_EQUAL") == 0)
            return tableAttribute + " <> '" + value1 + "'";
        if (operator.compareTo("GREATER") == 0)
            return tableAttribute + " > '" + value1 + "'";
        if (operator.compareTo("LESS") == 0)
            return tableAttribute + " < '" + value1 + "'";
        if (operator.compareTo("GREATER_OR_EQUAL") == 0)
            return tableAttribute + " >= '" + value1 + "'";
        if (operator.compareTo("LESS_OR_EQUAL") == 0)
            return tableAttribute + " <= '" + value1 + "'";
        
        return null;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof MeasurePreFilterWhereClause)) {
            return false;
        }
        final MeasurePreFilterWhereClause other =
            (MeasurePreFilterWhereClause)object;
        if (!(measureId == null ? other.measureId == null : measureId.equals(other.measureId))) {
            return false;
        }
        if (!(operator == null ? other.operator == null : operator.equals(other.operator))) {
            return false;
        }
        if (!(factTableName == null ? other.factTableName == null : factTableName.equals(other.factTableName))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = super.hashCode();
        result = PRIME * result + ((measureId == null) ? 0 : measureId.hashCode());
        result = PRIME * result + ((operator == null) ? 0 : operator.hashCode());
        result = PRIME * result + ((factTableName == null) ? 0 : factTableName.hashCode());
        return result;
    }
}
