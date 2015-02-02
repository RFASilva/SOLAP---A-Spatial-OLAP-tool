package solap.bean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import solap.entities.InterfaceAttribute;


public class SOLAPConverter implements javax.faces.convert.Converter {
    public Object getAsObject(FacesContext context, UIComponent component,
                              String value) {
        String[] words = value.split(":");
    
        String dimensionId = words[0];
        String levelId = words[1];
        String id = words[2];
        String name = words[3];
        InterfaceAttribute wonder = new InterfaceAttribute(dimensionId, levelId, id, name);
        
        return wonder;
    }

    public String getAsString(FacesContext context, UIComponent component,
                              Object value) {
        return value.toString();
    }
}