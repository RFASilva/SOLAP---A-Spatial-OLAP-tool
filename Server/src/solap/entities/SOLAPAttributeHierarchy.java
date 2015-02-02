package solap.entities;

import java.util.Vector;

public class SOLAPAttributeHierarchy {
    private Vector<SOLAPAttribute> attributesFromHierarchy;
    private String attName;
    private String description;
    
    public SOLAPAttributeHierarchy(String attName) {
        this.attName = attName;
        attributesFromHierarchy = new Vector<SOLAPAttribute>();
    }
    
    public void addAttribute(SOLAPAttribute att){
        attributesFromHierarchy.add(att);
        description = hierarchyToString();
    }

    public void setAttributesFromHierarchy(Vector<SOLAPAttribute> attributesFromHierarchy) {
        this.attributesFromHierarchy = attributesFromHierarchy;
    }

    public Vector<SOLAPAttribute> getAttributesFromHierarchy() {
        return attributesFromHierarchy;
    }
    
    private String hierarchyToString(){
        String result = attName + " > ";
        for(SOLAPAttribute att : attributesFromHierarchy)
            result += att.getName() + " > ";
        return result.substring(0,result.length()-3);
    }

    public void setAttName(String attName) {
        this.attName = attName;
    }

    public String getAttName() {
        return attName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    public String generateUpperLevelAtributesXML(){
        String request="";
        for(SOLAPAttribute att : attributesFromHierarchy)
            request += "<upperLevelAttribute id=\"" + att.getId() + "\" />\n";
        return request;
    }
}
