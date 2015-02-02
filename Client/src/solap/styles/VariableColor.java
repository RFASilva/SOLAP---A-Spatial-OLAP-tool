package solap.styles;

import java.util.Map;
import java.awt.Color;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;

public class VariableColor extends VisualProperty {
    
    //Default Values
    private static final String[] defaultColors = {"blue", "yellow", "cyan", "green", "magenta","orange", "red", "pink", "black"};
    
    private static final String[] characterizationColors = {"#BEBADA", "#8DD3C7", "#FB8072", "#80B1D3","#FDB462", "#B3DE69", "#FCCDE5", "#D9D9D9","#BC80BD","#CCEBC5","#FFED6F"};
    
    /*Default Values (para experimentar)
    private static final String[] defaultColors = {"#990000", "#660000","#FF0000", "#CC0000", "#330000","orange",
                                        "red", "pink"};
    */

    //Colors defined by user
    private List<String> manualColors;
    
    //To use only with spatial object is a point
    private String marker;
    private String size;
    
    private int indexColor;

    public VariableColor() {
        super();
        this.indexColor = 0;
        this.marker  = "";
        manualColors = new LinkedList<String>();
    }

    public void setManualColors(Map<String, String> manualColors) {
        this.manualColors.addAll(manualColors.keySet());
    }
    
    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getMarker() {
        return marker;
    }
    
    public String getOneColor(int index) {
        if(index >= manualColors.size()) {
            return defaultColors[indexColor++];
        }
        else return manualColors.get(index);
    }
    
    public String getOneRandomColor(int index){
        if(index < characterizationColors.length) {
            return characterizationColors[indexColor++];
        }
        else{
            System.out.println("Vou gerar uma cor");
            Random generator = new Random();
            float r = generator.nextFloat();
            float g = generator.nextFloat();
            float b = generator.nextFloat();
            Color randomColor = new Color(r, g, b);
            return "#"+convertRGBtoHex(randomColor.getRed(),randomColor.getGreen(), randomColor.getBlue());
        }
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

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public String info() {
        String result = "";
        
        result+="List Of Colors=";
        if(manualColors.size() == 0) {
            for(int i = 0; i < indexColor; i++) {
                if( i == (indexColor -1) )
                    result += defaultColors[i];
                else 
                    result += defaultColors[i] + ",";
            }
        }
        
        if(!marker.equals(ManagerStyles.EMPTY)) {
            result += " ,";
            result += "marker=" + marker;
        }
        
        if(!size.equals(ManagerStyles.EMPTY)) {
            if(!marker.equals(ManagerStyles.EMPTY))
                result += ", ";
            result += "size=" + size;
        }
        
        return result;
    }
    
    public void reset() {
        indexColor = 0;
    }
}
