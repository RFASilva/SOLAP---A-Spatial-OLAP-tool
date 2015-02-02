package solap.styles;

import java.util.ArrayList;

import solap.utils.ITriple;

abstract public class Style {

    protected String id;
    protected IStylesCache styleCache;

    public Style(String id) {
        this.id = id;
    }
    
    public abstract VisualProperty getVisualProperty();
    
    //index: Defined which "column" apply the style
    public abstract String toXMLRequestMapViewer(Context context, int index, ITriple<String, String, String> xmlLegend);

    public abstract ArrayList<ITriple<String, String, String>> info();

    public String getId() {
        return id;
    }
    
    protected String generateMarkerStyle(String marker, String color, String width, String height) {

        String result ="";
        if(marker.toLowerCase().equals("circle")) {
            result += 
                     "<g class=\"marker\" style=\"stroke:#000000;fill:"+color+";width:"+width+";height:"+height+"\"> \n" + 
                        "<circle cx=\"0\" cy=\"0\" r=\"0\"/>\n" + 
                     "</g>";
        }
        else if(marker.toLowerCase().equals("star")) {
            result += 
                     "<g class=\"marker\" style=\"stroke:#000000;fill:"+color+";width:"+width+";height:"+height+"\"> \n" +
                        "<polyline points=\"138.0,123.0,161.0,198.0,100.0,152.0,38.0,198.0,61.0,123.0,0.0,76.0,76.0,76.0,100.0,0.0,123.0,76.0,199.0,76.0\"/>\n" + 
                    "</g>";
        }
        else if(marker.toLowerCase().equals("square")) {
            result += 
                     "<g class=\"marker\" style=\"stroke:#000000;fill:"+color+";width:"+width+";height:"+height+"\"> \n" +
                        " <rect points=\"0.0,0.0,120.0,120.0\"/>\n" + 
                     "</g>";
        }
        return result;
    }
    
    protected String addEntryToLegend(String text, String nameStyle) {
        return "<entry text=\""+ text + "\"/>\n" + 
                        "<entry style=\"" + nameStyle +"\" />";
    }


    public void setStyleCache(IStylesCache styleCache) {
        this.styleCache = styleCache;
    }

    public IStylesCache getStyleCache() {
        return styleCache;
    }
}
