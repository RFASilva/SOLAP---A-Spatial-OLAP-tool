package solap.styles;

public class TypeSimple extends TypeStyle {

    private String typeStyle;
    
    public TypeSimple(String description, String typeStyle) {
        super(description);
        this.typeStyle = typeStyle;
    }
    
    public String getTypeStyle() {
        return typeStyle;
    }
    

    @Override
    public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                            + ((typeStyle == null) ? 0 : typeStyle.hashCode());
            return result;
    }

    @Override
    public boolean equals(Object obj) {
            if (this == obj)
                    return true;
            if (obj == null)
                    return false;
            if (!(obj instanceof TypeSimple))
                    return false;
            TypeSimple other = (TypeSimple) obj;
            if (typeStyle == null) {
                    if (other.typeStyle != null)
                            return false;
            } else if (!typeStyle.equals(other.typeStyle))
                    return false;
            return true;
    }


}
