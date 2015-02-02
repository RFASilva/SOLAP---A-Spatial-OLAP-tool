package solap.sqlgenerator.clauses.selectclauses;

import solap.entities.SumarizationMeasure;

import solap.params.SOLAPMeasureParams;
import solap.sqlgenerator.Query;
import solap.utils.MappingUtils;

public class MeasureSelectClause extends SelectClause {
    
    private String columnName;
    private String operator;
    
    private boolean numeric;
    private String table;
    
    //To Sumarization
    private boolean toSumarizationQuery;

    public MeasureSelectClause(MappingUtils mapUtils, SOLAPMeasureParams measure, String table) {
        super(mapUtils);
        
        String idMeasure = measure.getId();
        this.columnName = mapUtils.getMeasureColumn(idMeasure);
        this.operator = measure.getOperator();
        this.numeric = true;
        
        //Gives information about type of measure (calculated or normal aggregation operator
        if(operator.equals(""))
            numeric = false;
        
        this.table = table;
        super.groupBy = null;
    }
    
    public MeasureSelectClause(MappingUtils mapUtils, SumarizationMeasure measure, String table) {
        super(mapUtils);
        String idMeasure = measure.getId();
        this.columnName = mapUtils.getMeasureColumn(idMeasure);
        this.operator = measure.getOperator();
        this.numeric = true;
        
        //Gives information about type of measure (calculated or normal aggregation operator
        if(operator.equals(""))
            numeric = false;
        
        this.table = table;
        super.groupBy = null;
        
        this.toSumarizationQuery = true;
    }
    
    public String getTable() {
        return table;
    }
    
    public String getLevelId() {
        return Query.EMPTY;
    }

    public String toSQL() {
        super.alias = operator + "_" + columnName;
        
        if(operator.equals("noop"))
            super.alias = columnName;
        if(toSumarizationQuery)
            return table + "." + columnName + " as " + super.alias;
        
        if(numeric) return operator + "(" + table + "." + columnName + ") as " + super.alias;
        else //TODO: Calculated Measures
            
        return null;
    }
    
    public String getDimensionId() {
        return Query.EMPTY;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof MeasureSelectClause)) {
            return false;
        }
        final MeasureSelectClause other = (MeasureSelectClause)object;
        if (!(columnName == null ? other.columnName == null : columnName.equals(other.columnName))) {
            return false;
        }
        if (!(operator == null ? other.operator == null : operator.equals(other.operator))) {
            return false;
        }
        if (numeric != other.numeric) {
            return false;
        }
        if (!(table == null ? other.table == null : table.equals(other.table))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = super.hashCode();
        result = PRIME * result + ((columnName == null) ? 0 : columnName.hashCode());
        result = PRIME * result + ((operator == null) ? 0 : operator.hashCode());
        result = PRIME * result + (numeric ? 0 : 1);
        result = PRIME * result + ((table == null) ? 0 : table.hashCode());
        return result;
    }
}
