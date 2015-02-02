package solap.sqlgenerator.clauses.selectclauses;

import java.util.LinkedList;
import java.util.List;
import solap.utils.MappingUtils;

abstract public class SelectClause {
    
    protected static MappingUtils mapUtils;
    protected String alias;
    
    // Store the group by definition
    // Avoid further work
    protected List<String> groupBy;

    public SelectClause(MappingUtils mapUtils) {
        this.mapUtils = mapUtils;
        this.groupBy = new LinkedList<String>();
    }

    public String getAlias() {
        return alias;
    }
    
    public List<String> getGroupBy() {
        return groupBy;
    }
    
    abstract public String toSQL();
    
    abstract public String getTable();
    
    abstract public String getDimensionId();
    
    abstract public String getLevelId();
    
}
