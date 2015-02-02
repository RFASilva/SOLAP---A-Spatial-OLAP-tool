package solap.styles;

import solap.styles.VisualProperty;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

public class VariableShape extends VisualProperty {

    //By default have a list of shapes
    private static final String[] defaultShapes = {"STAR", "CIRCLE", "SQUARE", 
                            "TRIANGLE", "RECTANGLE", "LOZENGE", "ELLIPSE" };

    private Map<String, String> manualShapes;
    private int index;
    
    private String color;
    private String size;
    
    public VariableShape() {
        super();
        this.index = 0;
    }

    public void setManualShapes(Map<String, String> manualShapes) {
        this.manualShapes = manualShapes;
    }
    
    public String getDefaultShapes() {
        return defaultShapes[index++];
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public String info() {
        //TODO
        return "nada definido";
    }
}
