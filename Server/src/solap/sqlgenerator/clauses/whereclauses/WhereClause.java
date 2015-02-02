package solap.sqlgenerator.clauses.whereclauses;

import solap.utils.MappingUtils;

abstract public class WhereClause {
    
    protected static MappingUtils mapUtils;

    public WhereClause(MappingUtils mapUtils) {
        this.mapUtils = mapUtils;
    }

    abstract public String toSQL();
    
}
