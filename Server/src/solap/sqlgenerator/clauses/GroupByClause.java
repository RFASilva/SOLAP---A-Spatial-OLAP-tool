package solap.sqlgenerator.clauses;

public class GroupByClause {
    
    private String definition;

    public GroupByClause(String definition) {
        this.definition = definition;
    }
    
    public String toSQL() {
        return definition;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof GroupByClause)) {
            return false;
        }
        final GroupByClause other = (GroupByClause)object;
        if (!(definition == null ? other.definition == null : definition.equals(other.definition))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((definition == null) ? 0 : definition.hashCode());
        return result;
    }
}
