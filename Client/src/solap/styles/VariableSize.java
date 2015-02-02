package solap.styles;

import solap.styles.VisualProperty;

public class VariableSize extends VisualProperty {

    //Default values
    private static final String defaultShapeMarker = "star";
    private static final String defaultInitialSize = "10";
    private static final String defaultIncrement = "5";
    private static final String defaultColor = "black";

    private String shapeMarker;
    private String color;
    private String initialSize;
    private String finalSize;
    private String increment;
    
    public VariableSize(String shapeMarker, String initialSize, String finalSize) {
        super();
        this.shapeMarker = shapeMarker;
        this.initialSize = initialSize;
        this.finalSize = finalSize;
        this.color = "";
    }
    
    public VariableSize(String shapeMarker, String initialSize) {
        super();
        this.shapeMarker = shapeMarker;
        this.initialSize = initialSize;
        this.finalSize = "";
        this.color = "";
    }

    public String getShapeMarker() {
        return shapeMarker;
    }

    public String getInitialSize() {
        if(initialSize.equals(ManagerStyles.EMPTY))
            return defaultInitialSize;
        return initialSize;
    }

    public String getFinalSize() {
        return finalSize;
    }

    public String getIncrement() {
        if(increment.equals(ManagerStyles.EMPTY))
            return defaultIncrement;
        return increment;
    }

    public void setIncrement(String increment) {
        this.increment = increment;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        if(defaultColor.equals(ManagerStyles.EMPTY))
            return defaultColor;
        return color;
    }


    public String info() {
        String result = "";
     
        if(!shapeMarker.equals(ManagerStyles.EMPTY))
            result += "marker=" + shapeMarker;
        
        if(!color.equals(ManagerStyles.EMPTY)) {
            result += " ,";
            result += "color=" + color;
        }
        
        if(!initialSize.equals(ManagerStyles.EMPTY)) {
            result += " ,";
            result += "initialSize=" + initialSize;
        }
        
        if(!finalSize.equals(ManagerStyles.EMPTY)) {
            result += ", ";
            result += "finalSize=" + finalSize;
        }
        
        if(!increment.equals(ManagerStyles.EMPTY)) {
            result += ", ";
            result += "finalSize=" + increment;
        }
        
        return result;
    }
}
