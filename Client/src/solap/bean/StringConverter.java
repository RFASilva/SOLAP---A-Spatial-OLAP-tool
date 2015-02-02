package solap.bean;

import java.util.HashMap;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

class StringConverter implements Converter{  
   
        //converter uses a HashMap for switching from selectItems to your objects  
        private HashMap<String, String> map;  
  
        public StringConverter(List<String> objekts) {  
                map=new HashMap<String, String>();  
                for(String o : objekts){  
                        map.put(Integer.toString(o.hashCode()), o);  
                }  
        }  
  
        public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {  
                return map.get(string);  
        }  
   
        public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {  
                if(obj instanceof String)   
                        return Integer.toString(((String)obj).hashCode());  
                return null;  
        }  
}  