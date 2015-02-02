package solap.entities;

import java.io.Serializable;

public class SumarizationMeasure implements Serializable {
    
    private String id;
    private String operator;
    private String name;
    private String nameToShow;
    private boolean noOperator;
    private boolean checked;
    private String threshold;
    
    public SumarizationMeasure(String id, String operator, String name) {
        this.id = id;
        this.operator = operator;
        this.nameToShow = name;
        if(!operator.equals("noop"))
            this.name = name.substring(4,name.length()-1);
        else
            this.name= name;
        this.checked = operator.equals("noop");
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
        final SumarizationMeasure other = (SumarizationMeasure)obj;
        
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

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }
    
    public String generateGeneralizationXML(){
        String request = "<measure id=\""+id+"\" name=\"" + name + "\" threshold=\"" + threshold + "\" agg=\"" + operator + "\" createHierarchy=\"" + checked + "\">\n";
        request += "</measure>\n";
        return request;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setNameToShow(String nameToShow) {
        this.nameToShow = nameToShow;
    }

    public String getNameToShow() {
        return nameToShow;
    }
}
