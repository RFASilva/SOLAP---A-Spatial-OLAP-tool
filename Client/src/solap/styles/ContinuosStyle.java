package solap.styles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import solap.utils.ITriple;
import solap.utils.LegendUtils;
import solap.utils.Triple;


public class ContinuosStyle extends Style {

    public enum Distribution {UNIFORM, NATURALBREAKS, CUSTOM}

    //Store the visual property used by this style
    private VisualProperty visualProperty;
    private List<IntervalValues> intervals;
    private int numberIntervals;
    private String typeOfDistribution;
    
    public ContinuosStyle(String id, int numberIntervals, VisualProperty visualProperty, String typeOfDistribution) {
        super(id);
        this.numberIntervals = numberIntervals;
        this.visualProperty = visualProperty;
        this.intervals = new LinkedList<IntervalValues>();
        this.typeOfDistribution = typeOfDistribution;
    }
    
    public void setVisualProperty(VisualProperty visualProperty) {
        this.visualProperty = visualProperty;
    }

    public VisualProperty getVisualProperty() {
        return visualProperty;
    }

    public void setIntervals(List<IntervalValues> intervals) {
        this.intervals = intervals;
    }

    public List<IntervalValues> getIntervals() {
        return intervals;
    }

    public void setNumberIntervals(int numberIntervals) {
        this.numberIntervals = numberIntervals;
    }

    public int getNumberIntervals() {
        return numberIntervals;
    }

    public void setTypeOfDistribution(String typeOfDistribution) {
        this.typeOfDistribution = typeOfDistribution;
    }

    public String getTypeOfDistribution() {
        return typeOfDistribution;
    }
    
    public void addInterval(String min, String max, String label) {
        intervals.add(new IntervalValues(Integer.parseInt(min), Integer.parseInt(max), label));
    }
    
    public void addInterval(String min, String max, String label, String id) {
        intervals.add(new IntervalValues(Integer.parseInt(min), Integer.parseInt(max), label, id));
    }

    public String toXMLRequestMapViewer(Context context, int index, ITriple<String, String, String> xmlLegend) {
        String result = "";
        System.out.println("AGORA ESTOU AQUI ONDE EU PENSAVA ESTAR");

        if (visualProperty instanceof VariableSize) {
            
            if(context.getNumberOfSpatialObjects() == 2) {
                //Generate the styles to points of both spatial objects
                result += toXMLRequestMapViewerVariableSizeToLine((VariableSize)visualProperty, context, index, xmlLegend);
                
                //TODO: review
                if(!(context.getNumberNumericalColumns() > 1)) {
                    result += ManagerStyles.TOKEN_SEPARATE_STYLE;
                    result += super.generateMarkerStyle("circle","blue", "7", "7");
                    result += ManagerStyles.TOKEN;
                    result += ManagerStyles.getName();
                    result += ManagerStyles.TOKEN_SEPARATE_STYLE;
                    result += super.generateMarkerStyle("circle","blue", "7", "7");
                    result += ManagerStyles.TOKEN;
                    result += ManagerStyles.getName();
                }
            }
            else {
                result+=toXMLRequestMapViewerVariableSizetoMarker((VariableSize)visualProperty, context, index);
                
                result += ManagerStyles.TOKEN;
                String nameStyle = ManagerStyles.getName();
                result += nameStyle;
                
                //generate the legend
                xmlLegend.setFirst(xmlLegend.getFirst() + super.addEntryToLegend(context.getMeasureByIndex(index).getFirst(), nameStyle));
            }
            
        }
        else if(visualProperty instanceof VariableBrightness) {
            //if the spatial object is a polygon the code generate
            //is the next otherwise is different
            ((VariableBrightness)visualProperty).reset();
            
            if(context.getNumberOfSpatialObjects() == 2) {
                //Generate the styles to points of both spatial objects
               
            }
            
            if(context.isSpatialPolygon()) {
                result = "<AdvancedStyle>" + "\n";
                result+=toXMLRequestMapViewerVariableBrightness((VariableBrightness)visualProperty, context, index);
                result += "</AdvancedStyle>";
            
                result += ManagerStyles.TOKEN;
                String nameStyle = ManagerStyles.getName();
                result += nameStyle;
                
                //generate the legend
                xmlLegend.setFirst(xmlLegend.getFirst() + super.addEntryToLegend(context.getMeasureByIndex(index).getFirst(), nameStyle));
            }
            else {
                result += toXMLMapViewerBrightnessToMarkeOrLines((VariableBrightness)visualProperty, context, index, xmlLegend);
            }
        }
        
        else if(visualProperty instanceof VariableColor) {
            ((VariableColor)visualProperty).reset();
            result+=toXMLRequestMapViewerVariableColor((VariableColor)visualProperty, context, index, xmlLegend);
        }
        
  
        return result;
    }
    
    private String toXMLRequestMapViewerVariableSizetoMarker(VariableSize variableSize, Context context, int index) {
        String result = "";
        
        //Generate the marker to be used in style generated below
        result += super.generateMarkerStyle(variableSize.getShapeMarker(), variableSize.getColor(), variableSize.getInitialSize(), variableSize.getInitialSize());
        result += ManagerStyles.TOKEN;
        
        String nameMarker = ManagerStyles.getName();
        result += nameMarker;
        result += ManagerStyles.TOKEN;
        
        //Generate the variable marker size
        result += "<AdvancedStyle>" + "\n";
        result += "<VariableMarkerStyle ";
        result += "basemarker=\""+ nameMarker+ "\" ";
        result += "startsize=\"" + variableSize.getInitialSize() + "\" ";
        result += "increment=\"" + variableSize.getIncrement() + "\" ";
        result += ">" + "\n";
        
        if(typeOfDistribution.equals(ContinuosStyle.Distribution.UNIFORM.toString())) {
            result += generateUniformIntervals(context, null, index, false);
        }
        
        else if(typeOfDistribution.equals(ContinuosStyle.Distribution.NATURALBREAKS.toString())) {
            result += generateNaturalBreaksIntervals(context, null, index, false);
        }
        
        else if(typeOfDistribution.equals(ContinuosStyle.Distribution.CUSTOM.toString())) {
            //TODO
        }
        result+="</VariableMarkerStyle> \n";
        result += "</AdvancedStyle>";
        
        return result;
    }
    
    private String toXMLRequestMapViewerVariableBrightness(VariableBrightness variableBrightness,
                                                           Context context, int index) {
        String result = "<ColorSchemeStyle ";
        result += "basecolor=\"" +  variableBrightness.getBaseColor() + "\" ";
        result += "strokecolor=\"" +  variableBrightness.getStrokeColor() + "\" ";
        result += ">" + "\n";
     
        if(typeOfDistribution.equals(ContinuosStyle.Distribution.UNIFORM.toString())) {
            result += generateUniformIntervals(context, null, index, false);
        }
        else if(typeOfDistribution.equals(ContinuosStyle.Distribution.NATURALBREAKS.toString())) {
            result += generateNaturalBreaksIntervals(context, null, index, false);
        }
        else if(typeOfDistribution.equals(ContinuosStyle.Distribution.CUSTOM.toString())) {
            //TODO
        }

        result+="</ColorSchemeStyle> \n";
        
        return result;
    }
    
    private String toXMLRequestMapViewerVariableColor(VariableColor variableColor,
                                                      Context context, int index, ITriple<String, String, String> xmlLegend) {
        String result = "";
        for(int i = 0; i< this.numberIntervals; i++) {
            if(context.isSpatialPoint() && !(context.getNumberNumericalColumns() == 2)) {
                result += super.generateMarkerStyle(variableColor.getMarker(), variableColor.getOneColor(i), variableColor.getSize(), variableColor.getSize() );
            }
            else {
                result+="<g class=\"color\" style=\"fill:" + variableColor.getOneColor(i) + ";stroke:black\"/> \n";
            }
            
            result+=ManagerStyles.TOKEN;
            result+=ManagerStyles.getName();
            result+=ManagerStyles.TOKEN;
        }
        
        String[] markers = result.split(ManagerStyles.TOKEN);
        result+="<AdvancedStyle>\n";
        result+="<BucketStyle>\n";
        
        if(typeOfDistribution.equals(ContinuosStyle.Distribution.UNIFORM.toString())) {
            result += generateUniformIntervals(context, markers, index, true);    
        }
        else if(typeOfDistribution.equals(ContinuosStyle.Distribution.NATURALBREAKS.toString())) {
            result += generateNaturalBreaksIntervals(context, markers, index, true);
        }
        else if(typeOfDistribution.equals(ContinuosStyle.Distribution.CUSTOM.toString())) {
            //TODO
        }
        
        result+="</BucketStyle>\n";
        result+="</AdvancedStyle>\n";   
        
        result+=ManagerStyles.TOKEN;
        String nameStyle = ManagerStyles.getName();
        result+=nameStyle;
        
        xmlLegend.setFirst(xmlLegend.getFirst() + super.addEntryToLegend(context.getMeasureByIndex(index).getFirst(), nameStyle));
        
        return result;
    }
    
    
    private String toXMLRequestMapViewerVariableSizeToLine(VariableSize variableSize,
                                                           Context context, int index, ITriple<String, String, String> xmlLegend) {
        String result = "";
        int size = Integer.parseInt(variableSize.getInitialSize());
        for(int i = 0; i< this.numberIntervals; i++) { 
            //TODO: ALTEREI PARA POR A SETA
            
            result+="<g class=\"line\" style=\"fill:" + variableSize.getColor() + ";stroke-width:" + size  + "\"> \n";
            result+="<line class=\"base\"/>";
            result+="    <marker-pattern offset=\"60\" interval=\"270\">\n" + 
            "                   <marker>M.ARROW</marker>\n" + 
            "            </marker-pattern>";
            //result+="<line class=\"parallel\" style=\"fill:"+ variableSize.getColor() + ";stroke-width:" + size  +"\"/>";
            result+="</g>";
            
            size += Integer.parseInt(variableSize.getIncrement());
            
            result+=ManagerStyles.TOKEN;
            result+=ManagerStyles.getName();
            result+=ManagerStyles.TOKEN;
        }
        
        String[] markers = result.split(ManagerStyles.TOKEN);
        result+="<AdvancedStyle>\n";
        result+="<BucketStyle>\n";
        
        if(typeOfDistribution.equals(ContinuosStyle.Distribution.UNIFORM.toString())) {
            result += generateUniformIntervals(context, markers, index, true);
        }
        else if(typeOfDistribution.equals(ContinuosStyle.Distribution.NATURALBREAKS.toString())) {
            result += generateNaturalBreaksIntervals(context, markers, index, true);
        }
        else if(typeOfDistribution.equals(ContinuosStyle.Distribution.CUSTOM.toString())) {
            //TODO
        }
        
        result+="</BucketStyle>\n";
        result+="</AdvancedStyle>\n";   
        
        result+=ManagerStyles.TOKEN;
        String nameStyle = ManagerStyles.getName();
        result+=nameStyle;
        
        //generate the legend
        xmlLegend.setFirst(xmlLegend.getFirst() + super.addEntryToLegend(context.getMeasureByIndex(index).getFirst(), nameStyle));
        
        return result;
    }
    
    private String toXMLMapViewerBrightnessToMarkeOrLines(VariableBrightness variableBrightness,
                                                    Context context,
                                                    int index, ITriple<String, String, String> xmlLegend) {
        String result = "";
        for(int i = 0; i< this.numberIntervals; i++) {
            if(context.isSpatialPoint() && !(context.getNumberNumericalColumns() == 2) && !(context.getNumberAlphaNumericalColumns()>0)) {
                result += super.generateMarkerStyle(variableBrightness.getMarker(), ("#"+ variableBrightness.darker(numberIntervals)), variableBrightness.getSize(), variableBrightness.getSize() );
            }
            else {
                result+="<g class=\"color\" style=\"fill:" + ("#"+ variableBrightness.darker(numberIntervals)) + "\"/> \n";
            }
        
            result+=ManagerStyles.TOKEN;
            result+=ManagerStyles.getName();
            result+=ManagerStyles.TOKEN;
        }
        
        String[] markers = result.split(ManagerStyles.TOKEN);
        result+="<AdvancedStyle>\n";
        result+="<BucketStyle>\n";
        
        if(typeOfDistribution.equals(ContinuosStyle.Distribution.UNIFORM.toString())) {
            result += generateUniformIntervals(context, markers, index, true);
        }
        else if(typeOfDistribution.equals(ContinuosStyle.Distribution.NATURALBREAKS.toString())) {
            result += generateNaturalBreaksIntervals(context, markers, index, true);
        }
        else if(typeOfDistribution.equals(ContinuosStyle.Distribution.CUSTOM.toString())) {
            //TODO
        }
  
        result+="</BucketStyle>\n";
        result+="</AdvancedStyle>\n";   
        
        result += ManagerStyles.TOKEN;
        String nameStyle = ManagerStyles.getName();
        result += nameStyle;
        
        xmlLegend.setFirst(xmlLegend.getFirst() + super.addEntryToLegend(context.getMeasureByIndex(index).getFirst(), nameStyle));
        
        return result;
    }
    
    private String generateUniformIntervals(Context context, String markers[], int index, boolean putStyleName) {
        String result ="<Buckets>\n";
        
        ITriple<String, Double, Double> values = context.getMeasureByIndex(index);
        ITriple<Double, Double, Double> legendInfo = LegendUtils.computeNiceIncrement(values.getSecond(), values.getThird(), numberIntervals);
        
        int indexToNames = 1;
        double temp = legendInfo.getFirst();
        for(int i = 0; i < numberIntervals; i++) {
            double lowValue = temp;
            double highValue = lowValue + legendInfo.getSecond();
            
            if (i == numberIntervals -1) {
                highValue = values.getThird() + 1;
            }
                
            if(putStyleName) {
                String nameStyle = markers[indexToNames];
                result+="<RangedBucket low=\"" + lowValue + "\" high=\"" + highValue + "\" style=\"" + nameStyle + "\"/>\n";
            }
            else {
                result+="<RangedBucket low=\"" + lowValue + "\" high=\"" + highValue + "\" />\n";
            }
            
            indexToNames+=2;
            temp = highValue;
        }
        result+="</Buckets>\n";
        
        return result;
    }
    
    private String generateNaturalBreaksIntervals(Context context, String markers[], int index, boolean putStyleName) {
        System.out.println("Gerar intervalos NATURAL BREAKS");
        List<Double> naturalBreaks = getNaturalBreaks(context, index);
        
        String result ="<Buckets>\n";
        
        ITriple<String, Double, Double> values = context.getMeasureByIndex(index);        
        int indexToNames = 1;
        double lowValue = values.getSecond();
        double highValue = 0.0;
        for(int i = 0; i < naturalBreaks.size(); i++) {
            highValue = naturalBreaks.get(i);
            
            if (i == numberIntervals -1) {
                highValue = values.getThird() + 1;
            }
            
            if(putStyleName) {
                String nameStyle = markers[indexToNames];
                result+="<RangedBucket low=\"" + lowValue + "\" high=\"" + highValue + "\" style=\"" + nameStyle + "\"/>\n";
            }
            else result+="<RangedBucket low=\"" + lowValue + "\" high=\"" + highValue + "\" />\n";
            
            lowValue = highValue;
            
            indexToNames+=2;
        }
        
        result+="</Buckets>\n";
        
        return result;
    }
    
    private List<Double> getNaturalBreaks(Context context, int index) {
        List<Double> columnValues = context.getColumnValuesByIndex(index);
        List<Double> jenksOrdered = new LinkedList<Double>();
        int[] jenksClasses = LegendUtils.getNaturalBreaks((ArrayList<Double>)columnValues, numberIntervals);
        
        for(int i = 0; i < jenksClasses.length; i++) {
            if(i == (jenksClasses.length - 1))
                jenksOrdered.add(columnValues.get(jenksClasses[i]) + 1);
            else jenksOrdered.add(LegendUtils.nicenum(columnValues.get(jenksClasses[i]), false));
        }
        
        Collections.sort(jenksOrdered);
        
        System.out.println( "Lista: " + jenksOrdered);
        return jenksOrdered;
    }
    
    private String newXML() {
        String result = "";
        result += ManagerStyles.TOKEN;
        result += ManagerStyles.getName();
        return result;
    }
    
    public ArrayList<ITriple<String, String, String>> info() {
      /*  String result = "[";
        
        result += visualProperty.info();
        result += ", ";
        result += "numberOfIntervals=" + numberIntervals;
        result += ", ";
        result += "typeOfDistribution=" + typeOfDistribution;
        result += "]";

        return result;*/
        ArrayList<ITriple<String, String, String>> result = new ArrayList<ITriple<String, String, String>>();
        result.add(new Triple<String, String, String>("Visual Property", this.getVisualProperty().getClass().getSimpleName(), ""));
        result.add(new Triple<String, String, String>("Number of Classes", numberIntervals+"", ""));
        result.add(new Triple<String, String, String>("Type of Distribution", typeOfDistribution, ""));
        
        return result;
    }
}
