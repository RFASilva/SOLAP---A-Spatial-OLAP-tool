package solap.styles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import solap.utils.ITriple;
import solap.utils.Triple;

public class PieChart extends Chart {

    //Default values
    private static final String defaultRadius="3";
    
    private static final String[] defaultColors = {"#0000FF", "#FFFF00", 
                        "#CC0066", "#33FF00", "#FF6600",
                        "#FF0000"};
    
    private String radius;
    private String numberOfSectors;
    
    //Store information about the bar<name, color, nothing>
    private List<ITriple<String, String, String>> sectorColors;
    
    public PieChart(String name, String radius, String numberOfSectors) {
        super(name);
        this.radius = radius;
        this.numberOfSectors = numberOfSectors;
        this.sectorColors = new LinkedList<ITriple<String, String, String>>(); 
    }
    
    public void addSector(String name, String sectorColor) {
        ITriple<String, String, String> temp = new Triple<String, String, String>(name, sectorColor, "");
        if(! sectorColors.contains(temp))
            sectorColors.add(temp);
    }
    
    public String getRadius() {
        if(radius.equals(ManagerStyles.EMPTY))
            return defaultRadius;
        return radius;
    }
    
    public String toXMLRequestMapViewer(Context context, int index, ITriple<String, String, String> xmlLegend) {
        String result = "";
        
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
        result+="<PieChartStyle pieradius=\""+ this.getRadius() + "\" >\n";
        
        for(int i=index; i < context.getNumberNumericalColumns(); i++) {
            int temp = 0;
            if(this.sectorColors.get(i) != null) {
                result+="<PieSlice name=\"" + context.getMeasureByIndex(i).getFirst() + "\" color=\"" + this.sectorColors.get(i).getSecond() + "\" />\n";
            }
            else {
                result+="<PieSlice name=\"" + context.getMeasureByIndex(i).getFirst() + "\" color=\"" + defaultColors[temp] + "\" />\n";
                temp++;
            }
        }
        
        result +="</PieChartStyle>\n";
        result +="</AdvancedStyle>";
        
        return result;
    }

    public VisualProperty getVisualProperty() {
        return null;
    }

    public ArrayList<ITriple<String, String, String>> info() {
        return null;
    }
}
