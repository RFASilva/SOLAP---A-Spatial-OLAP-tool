package solap.styles;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class TypeComposite extends TypeStyle {

    private Map<TypeStyle, String> compositeWith;
    
    public TypeComposite(String description) {
       super(description);
       compositeWith = new HashMap<TypeStyle, String>();
    }
    
    public void addTypeStyle(TypeStyle typeStyle) {
        compositeWith.put(typeStyle, "");
    }
    
    public Set<TypeStyle> getCompoundsStyles() {
        return compositeWith.keySet();
    }

    @Override
    public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                            + ((compositeWith == null) ? 0 : compositeWith.hashCode());
            return result;
    }

    @Override
    public boolean equals(Object obj) {
            if (this == obj)
                    return true;
            if (obj == null)
                    return false;
            if (!(obj instanceof TypeComposite))
                    return false;
            TypeComposite other = (TypeComposite) obj;
            if (compositeWith == null) {
                    if (other.compositeWith != null)
                            return false;
            } else if (!compositeWith.equals(other.compositeWith))
                    return false;
            return true;
    }
    
}
