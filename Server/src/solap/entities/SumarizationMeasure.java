package solap.entities;

public class SumarizationMeasure {
    
    private String id;
    private String name;
    private String operator;
    private String threshold;
    private String create;
    
    public SumarizationMeasure(String id, String name, String operator, String threshold, String create) {
        this.id = id;
        this.name = name;
        this.operator = operator;
        this.threshold = threshold;
        this.create = create;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getThreshold() {
        return threshold;
    }
    
    public void printInfo(){
        System.out.println("Measure ID = " + id + " Name = " + name +" threshold = " + threshold +" operador = " + operator +" createHierarchy = " + create);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SumarizationMeasure)) {
            return false;
        }
        final SumarizationMeasure other = (SumarizationMeasure)object;
        if (!(id == null ? other.id == null : id.equals(other.id))) {
            return false;
        }
        if (!(name == null ? other.name == null : name.equals(other.name))) {
            return false;
        }
        if (!(operator == null ? other.operator == null : operator.equals(other.operator))) {
            return false;
        }
        if (!(threshold == null ? other.threshold == null : threshold.equals(other.threshold))) {
            return false;
        }
        if (!(create == null ? other.create == null : create.equals(other.create))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        result = PRIME * result + ((name == null) ? 0 : name.hashCode());
        result = PRIME * result + ((operator == null) ? 0 : operator.hashCode());
        result = PRIME * result + ((threshold == null) ? 0 : threshold.hashCode());
        result = PRIME * result + ((create == null) ? 0 : create.hashCode());
        return result;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getCreate() {
        return create;
    }
}
