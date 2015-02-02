package solap.entities;

import java.io.Serializable;

import solap.utils.CommProtocolUtils;

public class InterfaceMeasure implements Serializable {
    private String id;
    private String operator;
    private String name;
    private boolean noOperator;
    
    public InterfaceMeasure(String id, String operator, String name) {
        this.id = id;
        this.operator = operator;
        this.name = name;
    }
    
    public String getXML() {
        return CommProtocolUtils.buildMeasure(id, operator);
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    //****** required for converter ******
    public String toString() {
        return id + ":" + operator + ":" + name;
    }
    @Override
    public int hashCode() {      
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((operator == null) ? 0 : operator.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final InterfaceMeasure other = (InterfaceMeasure)obj;
        
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (operator == null) {
            if (other.operator != null)
                return false;
        } else if (!operator.equals(other.operator))
            return false;
        return true;
    }
    //************************************

    public void setNoOperator(boolean noOperator) {
        this.noOperator = noOperator;
    }

    public boolean isNoOperator() {
        if(operator.equals("noop"))
            noOperator=true;
        else
            noOperator=false;
        return noOperator;
    }
}
