package solap.entities;

import java.io.Serializable;

import solap.utils.CommProtocolUtils;

public class InterfaceFilter implements Serializable {
    private String id;
    private String name;
    private String filterName;
    private String filterDescription;
    private String operator1;
    private String operator2;
    private String value1;
    private String value2;
    
    //constructors
    public InterfaceFilter(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public InterfaceFilter(String id, String name, String filterName, String filterDescription, String operator1, String operator2, String value1, String value2) {
        this.id = id;
        this.name = name;
        this.filterName = filterName;
        this.filterDescription = filterDescription;
        this.operator1 = operator1;
        this.operator2 = operator2;
        this.value1 = value1;
        this.value2 = value2;
    }
    
    //request generation
    public String getXML() {
        String temp1 = CommProtocolUtils.buildFieldFilter(id, operator1, value1);
        String temp2 = CommProtocolUtils.buildFieldFilter(id, operator2, value2);
    
        return temp1 + temp2;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterDescription(String filterDescription) {
        this.filterDescription = filterDescription;
    }

    public String getFilterDescription() {
        return filterDescription;
    }

    public void setOperator1(String operator1) {
        this.operator1 = operator1;
    }

    public String getOperator1() {
        return operator1;
    }

    public void setOperator2(String operator2) {
        this.operator2 = operator2;
    }

    public String getOperator2() {
        return operator2;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue2() {
        return value2;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof InterfaceFilter)) {
            return false;
        }
        final InterfaceFilter other = (InterfaceFilter)object;
        if (!(id == null ? other.id == null : id.equals(other.id))) {
            return false;
        }
        if (!(operator1 == null ? other.operator1 == null : operator1.equals(other.operator1))) {
            return false;
        }
        if (!(operator2 == null ? other.operator2 == null : operator2.equals(other.operator2))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        result = PRIME * result + ((operator1 == null) ? 0 : operator1.hashCode());
        result = PRIME * result + ((operator2 == null) ? 0 : operator2.hashCode());
        return result;
    }
}
