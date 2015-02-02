package solap.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;

import solap.entities.SOLAPAttribute;


public class MatrixBean {
    private List<SOLAPAttribute> attributes;

    public List<SOLAPAttribute> getAttributes() { 
        return attributes;
    }
    public void setAttributes(List<SOLAPAttribute> attributes) {
        this.attributes = attributes;
    }
    
    private List<SOLAPAttribute> attributesI;

    public List<SOLAPAttribute> getAttributesI() { 
        return attributesI;
    }
    public void setAttributesI(List<SOLAPAttribute> attributes) {
        this.attributesI = attributes;
    }
    
    private List<SOLAPAttribute> measures;

    public List<SOLAPAttribute> getMeasures() { 
        return measures;
    }
    public void setMeasures(List<SOLAPAttribute> attributes) {
        this.measures = attributes;
    }
    
    @PostConstruct
    public void init() {
      /*  attributes = new ArrayList<SOLAPAttribute>();
        attributes.add(new SOLAPAttribute("1", "Poluente", "3", "1"));
        attributes.add(new SOLAPAttribute("2", "Meio", "2", "1"));
        
        attributesI = new ArrayList<SOLAPAttribute>();
        attributesI.add(new SOLAPAttribute("1", "Distrito", "3", "1"));
        
        measures = new ArrayList<SOLAPAttribute>();
        measures.add(new SOLAPAttribute("1", "Quantidade Emitida (AVG)", "3", "1"));
        measures.add(new SOLAPAttribute("2", "Limiar (MAX)", "2", "1"));
        //attributes.add(new SOLAPAttribute("3", "CCC", "2", "1"));
        //attributes.add(new SOLAPAttribute("4", "DDD", "2", "1"));*/
    }
}
