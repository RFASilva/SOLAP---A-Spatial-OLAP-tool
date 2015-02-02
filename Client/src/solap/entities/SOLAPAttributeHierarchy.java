package solap.entities;

import java.io.Serializable;

import java.util.Vector;

public class SOLAPAttributeHierarchy implements Serializable {
    
    private Vector<SOLAPAttribute> attributesFromHierarchy = new Vector<SOLAPAttribute>();
    private String attName;
    private String description;
    private int sliderOptions;
    private int selectedValue = 0;
    private String selectedAttributeName;
    private String type;
    
    public SOLAPAttributeHierarchy(String attName, String type) {
        this.attName = attName;
        this.type = type;
    }
    
    public void addAttribute(SOLAPAttribute att){
        attributesFromHierarchy.add(att);
        //description = hierarchyToString();
    }

    public void setAttributesFromHierarchy(Vector<SOLAPAttribute> attributesFromHierarchy) {
        this.attributesFromHierarchy = attributesFromHierarchy;
    }

    public Vector<SOLAPAttribute> getAttributesFromHierarchy() {
        return attributesFromHierarchy;
    }
    
    private String hierarchyToString(){
        String result ="";
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
        if(selectedValue != 0){
            for(SOLAPAttribute att : attributesFromHierarchy)
                request += "<upperLevelAttribute id=\"" + att.getId() + "\" />\n";
            request += "<generalizeAttributeTo id=\"" + attributesFromHierarchy.get(selectedValue).getId() + "\" type=\"" + type +"\" />\n";
        }
        return request;
    }

    public void setSliderOptions(int sliderOptions) {
        this.sliderOptions = sliderOptions;
    }

    public int getSliderOptions() {
        sliderOptions = attributesFromHierarchy.size()-1;
        return sliderOptions;
    }

    public void setSelectedValue(int selectedValue) {
        this.selectedValue = selectedValue;
    }

    public int getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedAttributeName(String selectedAttributeName) {
        this.selectedAttributeName = selectedAttributeName;
    }

    public String getSelectedAttributeName() {
        selectedAttributeName = attributesFromHierarchy.get(selectedValue).getName();
        return selectedAttributeName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
