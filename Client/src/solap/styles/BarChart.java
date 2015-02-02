package solap.styles;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

import solap.utils.ITriple;
import solap.utils.Triple;

public class BarChart extends Chart {

    //Default values
    //Default Values
    private static final String[] defaultColors = {"#3399CC", "#FFFF00", 
                        "#CC0066", "#33FF00", "#FF6600",
                        "#FF0000"};
    private static final String defaultWidth = "40";
    private static final String defaultHeight = "60";
    private static final String defaultAxisXOn = "false";
    private static final String defaultGlobalScale = "true";
    
    private String width;
    private String height;
    
    private String numberOfBars;
    
    //Store information about the bar<name, color, nothing>
    private List<ITriple<String, String, String>> barsColors;
    
    //Optional information
    private String axisXOn;
    private String globalScale;

    public BarChart(String id, String width, String height, String numberOfBars, String axisXOn, String globalScale) {
        super(id);
        this.width = width;
        this.height = height;
        this.numberOfBars = numberOfBars;
        this.axisXOn = axisXOn;
        this.globalScale =globalScale;
        barsColors = new LinkedList<ITriple<String, String, String>>();
    }
    
    public void addBar(String name, String barColor) {
        ITriple<String, String, String> temp = new Triple<String, String, String>(name, barColor, "");
        if(! barsColors.contains(temp))
            barsColors.add(temp);
    }

    public List<ITriple<String, String, String>> getBarsColors() {
        return barsColors;
    }

    public String getWidth() {
        if(width.equals(ManagerStyles.EMPTY))
            return defaultWidth;
        return width;
    }

    public String getHeight() {
        if(height.equals(ManagerStyles.EMPTY))
            return defaultHeight;
        return height;
    }

    public String getNumberOfBars() {
        return numberOfBars;
    }

    public String getAxisXOn() {
        if(axisXOn.equals(ManagerStyles.EMPTY))
            return defaultAxisXOn;
        return axisXOn;
    }

    public String getGlobalScale() {
        if(globalScale.equals(ManagerStyles.EMPTY))
            return defaultGlobalScale;
        return globalScale;
    }
    
    public String toXMLRequestMapViewer(Context context, int index, ITriple<String, String, String> xmlLegend) {
        String result = "";
        
        /* Not supported by mapviewer
        if(context.getNumberOfSpatialObjects() == 2) { 
            
            //Generate the style to line (black and thin)
            
            result += "<g class=\"line\" style=\"fill:black;stroke-width:1\"> \n" + 
                            "<line class=\"base\"/>" + 
                      "</g>";
            
            result += ManagerStyles.TOKEN;
            result += ManagerStyles.getName();
            result += ManagerStyles.TOKEN;
            
            result += generateSimpleXML(context, index);
            result += ManagerStyles.TOKEN;
            result += ManagerStyles.getName();
            result += ManagerStyles.TOKEN;
            
            
            String[] temp = result.split(ManagerStyles.TOKEN);
            result+="<AdvancedStyle>\n";
            result+="<CollectionStyle>\n";
            for(int i = 1; i < temp.length; i+=2) {
                result+="<style name=\"" + temp[i] + "\" />\n";
            }
            
            result+="</CollectionStyle>\n";
            result+="</AdvancedStyle>\n";
            
            result+=ManagerStyles.TOKEN;
            result+=ManagerStyles.getName();
            
            result += ManagerStyles.TOKEN_SEPARATE_STYLE;
            result += super.generateMarkerStyle("star","yellow","8", "8");
            result += ManagerStyles.TOKEN;
            result += ManagerStyles.getName();
            result += ManagerStyles.TOKEN_SEPARATE_STYLE;
            result += super.generateMarkerStyle("star","red","8", "8");
            result += ManagerStyles.TOKEN;
            result += ManagerStyles.getName();
            
            return result;
        } */
        
        result = generateSimpleXML(context, index);
        result += ManagerStyles.TOKEN;
        String nameStyle = ManagerStyles.getName();
        result += nameStyle;
        
        xmlLegend.setFirst(xmlLegend.getFirst() + super.addEntryToLegend("", nameStyle));
        
        if(context.isSpatialPolygon()) {
            result += ManagerStyles.TOKEN;
        
            result += generatePostXML(context, nameStyle);
            result += ManagerStyles.TOKEN;
            
            result+=ManagerStyles.getName();
        }
        
        return result;
    }
    
    private String generatePostXML(Context context, String nameStyle) {
        String result ="";
        
        // If target spatial object is a polygon its necessary generate a 
        // collection of styles to view the boundary of a polygon
        result += "<AdvancedStyle>\n";
        result+="<CollectionStyle>\n";
        
        
        result+="<style name=\"" + ManagerStyles.BASE_STYLE_AREA + "\" />\n";
        result+="<style name=\"" + nameStyle + "\" />\n";
        
        result+="</CollectionStyle>\n";    
        result+="</AdvancedStyle>";    

        return result;
    }
    
    public String generateSimpleXML(Context context, int index) {
        String result = "<AdvancedStyle>" + "\n";
        
        //Fill attributes
        result+="<BarChartStyle width=\""+ this.getWidth() +"\" height=\""+ this.getHeight() + "\"";
        
        if(Boolean.parseBoolean(getAxisXOn())) {
            result += " show_x_axis=\"" + this.getAxisXOn() + "\"";
        }
        
        if(Boolean.parseBoolean(getGlobalScale())) {
            result += " share_scale=\"" + this.getGlobalScale() + "\"";
            ITriple<Double, Double, String> extremesValues = extremesFromAllMeasures(context, index);
            
            result += " min_value=\"" + extremesValues.getFirst() + "\"";
            result += " max_value=\"" + extremesValues.getSecond() + "\"";
        }
        result+=">\n";
        
        for(int i=index; i < context.getNumberNumericalColumns(); i++) {
        //TODO nao é verificado se a cor que vamos usar como default nao esta a ser ja utilizada
            int temp = 0;
            if(this.barsColors.get(i) != null) {
                result+="<Bar name=\"" + context.getMeasureByIndex(i).getFirst() + "\" color=\"" + this.barsColors.get(i).getSecond() + "\" />\n";
            }
            else {
                result+="<Bar name=\"" + context.getMeasureByIndex(i).getFirst() + "\" color=\"" + defaultColors[temp] + "\" />\n";
                temp++;
            }
        }
        
        result +="</BarChartStyle>\n";
        result +="</AdvancedStyle>";
        
        return result;
    }

    private ITriple<Double, Double, String> extremesFromAllMeasures(Context context, int index) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        
        for(int i = index; i < context.getNumberOfNumericalCol(); i++) {
            ITriple<String, Double, Double> measure = context.getMeasureByIndex(i);
            
            if(measure.getSecond() < min)
                min = measure.getSecond();
            
            if(measure.getThird() > max)
                max = measure.getThird();
        }
        
        return new Triple<Double, Double, String>(min, max, "");
    }

    public VisualProperty getVisualProperty() {
        return null;
    }

    public ArrayList<ITriple<String, String, String>> info() {
      /*  return "barchart";
       **/
        return null;
    }
}
