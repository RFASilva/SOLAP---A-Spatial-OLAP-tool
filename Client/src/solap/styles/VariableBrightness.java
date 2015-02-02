package solap.styles;

import java.awt.Color;
import java.util.Map;
import java.util.HashMap;


public class VariableBrightness extends VisualProperty {

    //DEFAULT VALUES 
    private String defaultBaseColor = "red";
    private String defaultStrokeColor ="black";

    // Base color defined
    private String baseColor;
    private String strokeColor;
    
    private Color tempColor;
    private Color colorToFill;
    
    //If to be apply to a marker
    private String marker;
    private String size;
    
    //Or manual colors defined
    private Map<String, String> manualColors;
    
    private boolean first;
    private double decrement;
    
    public VariableBrightness(String baseColor, String strokeColor) {
        if(baseColor.equals(ManagerStyles.EMPTY))
            this.baseColor = defaultBaseColor;
        else this.baseColor = baseColor;
        this.marker = "";
        this.strokeColor = strokeColor;
        first = true;
        buildColor();
    }
    
    public VariableBrightness(String strokeColor) {
        super();
        this.manualColors = new HashMap<String, String>();
        this.marker = "";
    }

    public void setBaseColor(String baseColor) {
        this.baseColor = baseColor;
    }

    public String getBaseColor() {
        if(baseColor.equals(ManagerStyles.EMPTY))
            return defaultBaseColor;
        else return baseColor;
    }

    public void setStrokeColor(String strokeColor) {
        this.strokeColor = strokeColor;
    }

    public String getStrokeColor() {
        if(strokeColor.equals(ManagerStyles.EMPTY))
            return defaultStrokeColor;
        else return strokeColor;
    }
 
    public String getColorById(String id) {
        return manualColors.get(id);
    }

    public void setManualColors(Map<String, String> manualColors) {
        this.manualColors = manualColors;
    }
    
    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getMarker() {
        return marker;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
    
    public String darker(int numberClasses) {
        float[] darkerColor = new float[3];        
        if(first) {
            Color.RGBtoHSB(colorToFill.getRed(), colorToFill.getGreen(), colorToFill.getBlue(), darkerColor);
            //the way to decrease the brightness of color depende on number of classes
            decrement = (darkerColor[2]+ 0.0) / (numberClasses + 0.0);
            first = false;
            darkerColor[2] -= decrement;
        }
        else {
            Color.RGBtoHSB(colorToFill.getRed(), colorToFill.getGreen(), colorToFill.getBlue(), darkerColor);
            darkerColor[2] -= decrement;
        
            if(darkerColor[2] < 0)
                darkerColor[2] = 0;
        }
            
        Color temp2 = new Color(colorToFill.getRGB());
        colorToFill = Color.getHSBColor(darkerColor[0], darkerColor[1], darkerColor[2]);
        return convertRGBtoHex(temp2.getRed(), temp2.getGreen(), temp2.getBlue());
    }

    private void buildColor() {
        
        if(this.baseColor.toLowerCase().equals("red")) {
            this.colorToFill = Color.red;
        }
        else if(this.baseColor.toLowerCase().equals("blue")) {
            this.colorToFill = Color.blue;
        }
        else if(this.baseColor.toLowerCase().equals("cyan")) {
            this.colorToFill = Color.cyan;
        }
        else if(this.baseColor.toLowerCase().equals("green")) {
            this.colorToFill = Color.green;
        }
        else if(this.baseColor.toLowerCase().equals("magenta")) {
            this.colorToFill = Color.magenta;
        }
        else if(this.baseColor.toLowerCase().equals("pink")) {
            this.colorToFill = Color.pink;
        }
        else if(this.baseColor.toLowerCase().equals("orange")) {
            this.colorToFill = Color.orange;
        }
        else if(this.baseColor.toLowerCase().equals("yellow")) {
            this.colorToFill = Color.yellow;
        }
        
        tempColor = new Color(colorToFill.getRGB());
    }
    
    private String convertRGBtoHex(int r, int g, int b) {
        String red = Integer.toHexString( r & 0x00ffffff );
        String green = Integer.toHexString( g & 0x00ffffff );
        String blue = Integer.toHexString( b & 0x00ffffff );
        
        if(red.equals("0"))
            red="00";
        if(green.equals("0"))
            green="00";
        if(blue.equals("0"))
            blue="00";
        
        if(red.length() == 1)
            red = "0" + red;
        if(blue.length() == 1)
            blue = "0" + blue;
        if(green.length() == 1)
            green = "0" + green;
            
        return red + green + blue;
    }
    
    public void reset() {
        colorToFill = tempColor;
    }

    public String info() {
        String result = "";
        
        if(!baseColor.equals(ManagerStyles.EMPTY))
            result += "baseColor=" + baseColor;
        
        if(!strokeColor.equals(ManagerStyles.EMPTY)) {
            result += " ,";
            result += "strokeColor=" + strokeColor;
        }
        
        if(!marker.equals(ManagerStyles.EMPTY)) {
            result += " ,";
            result += "marker=" + marker;
        }
        
        if(!size.equals(ManagerStyles.EMPTY)) {
            result += ", ";
            result += "size=" + size;
        }
        
        return result;
    }    
}
