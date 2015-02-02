package solap.styles;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class VariableTexture extends VisualProperty {

    //By default have a list of shapes
    private static final String[] defaultTextures = {"HORIZONTAL LINES", "VERTICAL LINES" };
    
    private Map<String, String> manualTextures;

    public VariableTexture() {
        super();
        manualTextures = new HashMap<String, String>();
    }

    public void setManualTextures(Map<String, String> manualTextures) {
        this.manualTextures = manualTextures;
    }

    public String info() {
        //TODO
        return null;
    }
}
