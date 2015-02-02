package solap.styles;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

import solap.utils.ITriple;

public class CompositeStyle extends Style {

    //All styles that compose this style
    protected List<Style> styles;
    
    //To use to generate rendering element
    protected List<String> tempNamesStylesToRender;
    
    public CompositeStyle(String id) {
        super(id);
        this.styles = new LinkedList<Style>();
        this.tempNamesStylesToRender = new LinkedList<String>();
    }
    
    public void addStyle(Style style) {
        if(!styles.contains(style))
            styles.add(style);
    }

    public List<Style> getStyles() {
        return this.styles;
    }

    public String toXMLRequestMapViewer(Context context, int index, ITriple<String, String, String> xmlLegend) {
        String result ="";
        this.tempNamesStylesToRender = new LinkedList<String>();
        if(styles.size() == 2) {
            Style style1 = styles.get(0);
            Style style2 = styles.get(1);
            
            //Both styles are ContinuosStyles and are applied continuos values
            if(style1 instanceof ContinuosStyle && style2 instanceof ContinuosStyle) {
       
                //In case of 2 spatial objects
                if(context.getNumberOfSpatialObjects() == 2) {
                    if(isVariableSize(style1) && isVariableColor(style2)) {
                        result += toXMLResquestMapviewerSizeColorLines(context, (ContinuosStyle)style1, (ContinuosStyle)style2, xmlLegend);
                        result += generateStyleForExtremesMarkers();
                        return result;
                    }
                    else if(isVariableSize(style2) && isVariableColor(style1)) {
                        result += toXMLResquestMapviewerSizeColorLines(context, (ContinuosStyle)style2, (ContinuosStyle)style1, xmlLegend);
                        result += generateStyleForExtremesMarkers();
                        return result;
                    }
                    
                    if(isVariableSize(style1) && isVariableBrightness(style2)) {
                        result += toXMLResquestMapviewerSizeBrightnessLines(context, (ContinuosStyle)style1, (ContinuosStyle)style2, xmlLegend);
                        result += generateStyleForExtremesMarkers();
                        return result;
                    }
                    else if(isVariableSize(style2) && isVariableBrightness(style1)) {
                        result += toXMLResquestMapviewerSizeBrightnessLines(context, (ContinuosStyle)style2, (ContinuosStyle)style1, xmlLegend);
                        result += generateStyleForExtremesMarkers();
                        return result;
                    }
                }
                    
                if(isVariableSize(style1) && isVariableColor(style2)) {
                    return toXMLRequestMapviewerSC(context, (ContinuosStyle)style1, (ContinuosStyle)style2, xmlLegend);
                }
                else if(isVariableSize(style2) && isVariableColor(style1)) {
                    return toXMLRequestMapviewerSC(context, (ContinuosStyle)style2, (ContinuosStyle)style1, xmlLegend);
                }
                
                if(isChart(style1) && isVariableBrightness(style2)) {
                   result+=toXMLRequestMapviewer(context, (Chart)style1, (ContinuosStyle)style2);
                }
                
                else if(isChart(style2) && isVariableBrightness(style1)) {
                   result+=toXMLRequestMapviewer(context, (Chart)style2, (ContinuosStyle)style1);
                }
            }
            //One style is Discrete and other is continuos. Applicable in situations with alphanumeric columns
            else if ( (style1 instanceof DiscreteStyle && style2 instanceof ContinuosStyle)){
          
                if(isVariableSize(style2) && isVariableColor(style1))
                    return toXMLRequestMapviewerSC(context, (ContinuosStyle)style2, (DiscreteStyle)style1, xmlLegend);
                
            }
            
            else if(style2 instanceof DiscreteStyle && style1 instanceof ContinuosStyle) {
                if(isVariableSize(style1) && isVariableColor(style2)) {
                    return toXMLRequestMapviewerSC(context, (ContinuosStyle)style1, (DiscreteStyle)style2, xmlLegend);
                }
            }
            
            
            if(isVariableSize(style1) && isVariableBrightness(style2)) {
                return toXMLRequestMapviewerSB(context, (ContinuosStyle)style1, (ContinuosStyle)style2, xmlLegend);
            }
            else if(isVariableSize(style2) && isVariableBrightness(style1)) {
                return toXMLRequestMapviewerSB(context, (ContinuosStyle)style2, (ContinuosStyle)style1, xmlLegend);
            }
            
            //NOTE: discrete style implicit to first
            if(isVariableShape(style1) && isVariableBrightness(style2)) {
                return toXMLRequestMapviewerShB(context, (DiscreteStyle)style1, (ContinuosStyle)style2, xmlLegend);
            }
            else if(isVariableShape(style2) && isVariableBrightness(style1)) {
                return toXMLRequestMapviewerShB(context, (DiscreteStyle)style2, (ContinuosStyle)style1, xmlLegend);
            }
            
            String[] names= result.split(ManagerStyles.TOKEN);
            result+="<AdvancedStyle>\n";
            result+="<CollectionStyle>\n";
            for(int i = 1; i < names.length; i+=2) {
                result+="<style name=\"" + names[i] + "\" />\n";
            }
           
            result+="</CollectionStyle>\n";
            result+="</AdvancedStyle>\n";
            
            result+=ManagerStyles.TOKEN;
            result+=ManagerStyles.getName();
        }
            
        return result;
    }
    
    
    private String toXMLRequestMapviewer(Context context, Chart chart, ContinuosStyle style) {
        String result ="";
        result+=style.toXMLRequestMapViewer(context, 0, null);
        result += ManagerStyles.TOKEN;
        if(chart instanceof BarChart) {
            result+=((BarChart)chart).generateSimpleXML(context, 1);
            result += ManagerStyles.TOKEN;
            result+=ManagerStyles.getName();
            result += ManagerStyles.TOKEN;
        }
        return result;
    }
    
    private String toXMLRequestMapviewerSC(Context context,
                                         ContinuosStyle styleSize,
                                         ContinuosStyle styleColor, ITriple<String, String, String> xmlLegend) {
        String result = "";
        //Put transparent marker
        ((VariableSize)styleSize.getVisualProperty()).setColor("FF0000");
        result += styleSize.toXMLRequestMapViewer(context, 0, xmlLegend);
        
        String[] temp = result.split(ManagerStyles.TOKEN);
        this.tempNamesStylesToRender.add(temp[3]);
        
        result += ManagerStyles.TOKEN;
        result += styleColor.toXMLRequestMapViewer(context, 1, xmlLegend);
        
        temp = result.split(ManagerStyles.TOKEN);
        this.tempNamesStylesToRender.add(temp[temp.length-1]);
        result += ManagerStyles.TOKEN;
        
        return result;
    }
    
    private String toXMLRequestMapviewerSB(Context context,
                                           ContinuosStyle styleSize,
                                           ContinuosStyle styleBrightness, ITriple<String, String, String> xmlLegend) {
        
        String result = "";
        //Put transparent marker
        ((VariableSize)styleSize.getVisualProperty()).setColor("FF0000");
        result += styleSize.toXMLRequestMapViewer(context, 0, xmlLegend);
        
        String[] temp = result.split(ManagerStyles.TOKEN);
        this.tempNamesStylesToRender.add(temp[3]);
        
        result += ManagerStyles.TOKEN;
        result += styleBrightness.toXMLRequestMapViewer(context, 1, xmlLegend);
        
        System.out.println("COMPOSITE COLOR: "+((VariableBrightness)styleBrightness.getVisualProperty()).getBaseColor());
        
        temp = result.split(ManagerStyles.TOKEN);
        this.tempNamesStylesToRender.add(temp[temp.length-1]);
        result += ManagerStyles.TOKEN;
        
        return result;
    }
    
    private String toXMLRequestMapviewerShB(Context context,
                                            DiscreteStyle styleShape,
                                            ContinuosStyle styleBrightness, ITriple<String, String, String> xmlLegend) {
        String result = "";
        //Put transparent marker
        ((VariableShape)styleShape.getVisualProperty()).setColor("FF0000");
        result += styleShape.toXMLRequestMapViewer(context, 0, xmlLegend);
        
        String[] temp = result.split(ManagerStyles.TOKEN);
        //TODO: PROVALVEMENTE NEM SEMPRE E A POSICAO 7 DEPENDE DO NUMERO DE INTERVALOS
        
        System.out.println("VAIS RENDER ESTE ESTILO: " + temp[6]);
        this.tempNamesStylesToRender.add(temp[7]);
        
        result += ManagerStyles.TOKEN;
        result += styleBrightness.toXMLRequestMapViewer(context, 0, xmlLegend);
        
        System.out.println("COMPOSITE COLOR: "+((VariableBrightness)styleBrightness.getVisualProperty()).getBaseColor());
        
        temp = result.split(ManagerStyles.TOKEN);
        this.tempNamesStylesToRender.add(temp[temp.length-1]);
        result += ManagerStyles.TOKEN;
        
        return result;
    }
    
    private String toXMLRequestMapviewerSC(Context context,
                                           ContinuosStyle styleSize,
                                           DiscreteStyle styleColor, ITriple<String, String, String> xmlLegend) {
        String result = "";
        //Put transparent marker
        ((VariableSize)styleSize.getVisualProperty()).setColor("FF0000");
        result += styleSize.toXMLRequestMapViewer(context, 0, xmlLegend);
        
        String[] temp = result.split(ManagerStyles.TOKEN);
        this.tempNamesStylesToRender.add(temp[3]);
        
        result += ManagerStyles.TOKEN;
        result += styleColor.toXMLRequestMapViewer(context, 0, xmlLegend);
        
        temp = result.split(ManagerStyles.TOKEN);
        this.tempNamesStylesToRender.add(temp[temp.length-1]);
    
        result += ManagerStyles.TOKEN;
        
        return result;
    }
    

    private String toXMLResquestMapviewerSizeColorLines(Context context,
                                                        ContinuosStyle styleSize,
                                                        ContinuosStyle styleColor, ITriple<String, String, String> xmlLegend) {
        String result = "";
        //Put transparent marker
        ((VariableSize)styleSize.getVisualProperty()).setColor("#000000");
        result += styleSize.toXMLRequestMapViewer(context, 0, xmlLegend);
        
        String[] temp = result.split(ManagerStyles.TOKEN);
        this.tempNamesStylesToRender.add(temp[temp.length - 1]);
        
        result += ManagerStyles.TOKEN;
        result += styleColor.toXMLRequestMapViewer(context, 1, xmlLegend);
        
        temp = result.split(ManagerStyles.TOKEN);
        this.tempNamesStylesToRender.add(temp[temp.length-1]);
        
        result += ManagerStyles.TOKEN;
        
        return result;
    }
    
    private String toXMLResquestMapviewerSizeBrightnessLines(Context context,
                                                             ContinuosStyle styleSize,
                                                             ContinuosStyle styleBrightness,
                                                             ITriple<String, String, String> xmlLegend) {
        String result = "";
        //Put transparent marker
        ((VariableSize)styleSize.getVisualProperty()).setColor("#000000");
        result += styleSize.toXMLRequestMapViewer(context, 0, xmlLegend);
        
        String[] temp = result.split(ManagerStyles.TOKEN);
        this.tempNamesStylesToRender.add(temp[temp.length - 1]);
        
        result += ManagerStyles.TOKEN;
        result += styleBrightness.toXMLRequestMapViewer(context, 1, xmlLegend);
        
        temp = result.split(ManagerStyles.TOKEN);
        this.tempNamesStylesToRender.add(temp[temp.length-1]);
        
        result += ManagerStyles.TOKEN;
        
        return result;
    }
    
    private boolean isVariableBrightness(Style style) {
        return style.getVisualProperty() instanceof VariableBrightness;
    }
    
    private boolean isVariableSize(Style style) {
        return style.getVisualProperty() instanceof VariableSize;
    }
    
    private boolean isVariableShape(Style style) {
        return style.getVisualProperty() instanceof VariableShape;
    }
    
    private boolean isVariableColor(Style style) {
        return style.getVisualProperty() instanceof VariableColor;
    }
    
    private boolean isVariableTexture(Style style) {
        return style.getVisualProperty() instanceof VariableTexture;
    }
    
    private boolean isChart(Style style) {
        return style instanceof Chart;
    }
     
    public VisualProperty getVisualProperty() {
        return null;
    }

    public List<String> getTempNamesStylesToRender() {
        return tempNamesStylesToRender;
    }

    public boolean containsVariableSize() {
        for(Style style: styles) {
            if(style.getVisualProperty() instanceof VariableSize)
                return true;
        }
        return false;
    }
    
    private String generateStyleForExtremesMarkers() {
        String result = "";
        result += ManagerStyles.TOKEN_SEPARATE_STYLE;
        result += super.generateMarkerStyle("circle","black","9","9");
        result += ManagerStyles.TOKEN;
        result += ManagerStyles.getName();
        result += ManagerStyles.TOKEN_SEPARATE_STYLE;
        result += super.generateMarkerStyle("circle","black","9", "9");
        result += ManagerStyles.TOKEN;
        result += ManagerStyles.getName();
        
        return result;
    }

    public ArrayList<ITriple<String, String, String>> info() {
      /*  String result = "[";
        for(Style style: styles) {
            result += style.info();
        }
        result += "]";
        return result;*/
        //TODO
        return null;
    }

}
