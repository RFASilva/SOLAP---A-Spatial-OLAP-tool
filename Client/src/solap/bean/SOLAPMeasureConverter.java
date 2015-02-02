package solap.bean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import solap.entities.InterfaceMeasure;


public class SOLAPMeasureConverter implements javax.faces.convert.Converter {
    public Object getAsObject(FacesContext context, UIComponent component,
                              String value) {
        String[] words = value.split(":");
    
        String id = words[0];
        String operator = words[1];
        String name = words[2];
        InterfaceMeasure wonder = new InterfaceMeasure(id, operator, name);
        
        return wonder;
    }

    public String getAsString(FacesContext context, UIComponent component,
                              Object value) {
        return value.toString();
    }
}