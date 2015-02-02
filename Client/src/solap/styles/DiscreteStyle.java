package solap.styles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import java.util.Vector;

import solap.utils.EditUtils;
import solap.utils.ITriple;

public class DiscreteStyle extends Style {

    public enum Distribution {UNIFORM, CUSTOM}
    
    //Store the visual property used by this styleW
    private VisualProperty visualProperty;
    private List<DiscreteValues> sets;
    private int numberOfClasses;
    private String typeOfDistribution;

    public DiscreteStyle(String id, int numberOfClasses, VisualProperty visualProperty, String typeOfDistribution) {
        super(id);
        this.visualProperty = visualProperty;
        this.numberOfClasses = numberOfClasses;
        this.sets = new LinkedList<DiscreteValues>();
        this.typeOfDistribution = typeOfDistribution;
    }

    public void setVisualProperty(VisualProperty visualProperty) {
        this.visualProperty = visualProperty;
    }

    public VisualProperty getVisualProperty() {
        return visualProperty;
    }
    
    public void addDiscreteSet(String label, String[] values) {
        sets.add(new DiscreteValues(label, values));
    }
    
    public void addDiscreteSet(String label, String[] values, String id) {
        sets.add(new DiscreteValues(label, values, id));
    }

    public void setNumberBuckets(int numberBuckets) {
        this.numberOfClasses = numberBuckets;
    }

    public int getNumberBuckets() {
        return numberOfClasses;
    }
    
    public String toXMLRequestMapViewer(Context context, int index, ITriple<String, String, String> xmlLegend) {
        if(visualProperty instanceof VariableShape) {
            return toXMLRequestMapviewerShape((VariableShape)visualProperty, context, index, xmlLegend);
        }
        else if(visualProperty instanceof VariableColor && context.isSummarization()){
            return toXMLRequestMapviewerColorForSummarization((VariableColor)visualProperty, context, xmlLegend);
        }
        else if(visualProperty instanceof VariableColor) {
            return toXMLRequestMapviewerColor((VariableColor)visualProperty, context, index, xmlLegend);
        }
        
        return null;
    }

    private String toXMLRequestMapviewerColorForSummarization(VariableColor variableColor, Context context, ITriple<String, String, String> xmlLegend) {
        System.out.println("ESTOU NO toXMLRequestMapviewerColorForSummarization");
        String result = "";
        variableColor.reset();
        numberOfClasses = context.getNumDistinctCharacteristics();
        List<String> distinctValues = context.getDistinctValuesFromData();
        System.out.println("Nº de valores distintos: " + distinctValues.size());
        Vector<String> colors = chooseColorsToApply(variableColor,distinctValues);
        System.out.println("Passei da escolha de cores");
        for(int i = 0; i< numberOfClasses; i++) {
            if(context.isSpatialPoint()){
                result += "<g class=\"marker\" style=\"stroke:#000000;fill:"+colors.get(i)+";width:10;height:10\"> \n" +
                   "<polyline points=\"138.0,123.0,161.0,198.0,100.0,152.0,38.0,198.0,61.0,123.0,0.0,76.0,76.0,76.0,100.0,0.0,123.0,76.0,199.0,76.0\"/>\n" + 
                "</g>";
            }
            else{
                result+="<g class=\"color\" style=\"stroke:black;fill:" + colors.get(i) + ";\"/> \n";
            }
            result+=ManagerStyles.TOKEN;
            result+=ManagerStyles.getName();
            result+=ManagerStyles.TOKEN;
        }
        
        String[] markers = result.split(ManagerStyles.TOKEN);
        result+="<AdvancedStyle>\n";
        result+="<BucketStyle>\n";
        result+="<Buckets>\n";
        
        //List<String> distinctValues = context.getDistinctValuesFromData();
        //System.out.println("LISTA DE VALORES DISTINTOS:\n" + distinctValues);
        int indexToNames = 1;  
        for(int i = 0; i < numberOfClasses; i++) {
            String discreteValues = distinctValues.get(i).replace(ManagerStyles.TOKEN," ");
            result += "<CollectionBucket seq=\"" + i + "\" label=\""+ discreteValues+"\"\n" + 
            "style=\""+markers[indexToNames]+"\">"+discreteValues+"</CollectionBucket> \n";
            
            // Generate the legend
            String description = createLegendField(distinctValues.get(i));
            xmlLegend.setFirst(xmlLegend.getFirst() + super.addEntryToLegend(description, markers[indexToNames]));
            
            indexToNames+=2;
        }
        
        result+="</Buckets>\n";
        result+="</BucketStyle>\n";
        result+="</AdvancedStyle>\n";   
        result+=ManagerStyles.TOKEN;
        String nameStyle = ManagerStyles.getName();
        result+= nameStyle;
        return result;
    }
    
    private Vector<String> chooseColorsToApply(VariableColor variableColor, List<String> distinctValues){
        Vector<String> colors = new Vector<String>();
        int count = 0;
        for(int i = 0; i < numberOfClasses; i++) {
            //System.out.println("A definir cor para o valor nº"+i);
            String newColor = "";
            if(styleCache != null){
                //System.out.println("Vou utilizar a cache");
                String discreteValues = distinctValues.get(i).replace(ManagerStyles.TOKEN," ");
                if(styleCache.contains(discreteValues)){
                    //System.out.println("Cache hit!!!");
                    newColor = (String) styleCache.getElement(discreteValues);
                }
                else{
                    //System.out.println("Cache miss!!!");
                    newColor = variableColor.getOneRandomColor(count);
                    //System.out.println("Acabei de obter nova cor " + newColor);
                    count++;
                    while(styleCache.containsValue(newColor) || colors.contains(newColor)){
                        newColor = variableColor.getOneRandomColor(count);
                        //System.out.println("Acabei de obter nova cor " + newColor);
                        count++;
                    }
                }
                //System.out.println(discreteValues + " -> " + newColor);
            }
            else{
                newColor = variableColor.getOneRandomColor(i);
                //System.out.println("Acabei de obter nova cor " + newColor);
            }
            colors.add(newColor);
        }
        return colors;
    }
    
    private String createLegendField(String description){
        String result = "";
        String[] tokens = description.split(ManagerStyles.TOKEN);
        int count = 1; //Don't want the first element
        for(String s : tokens){
            if(count == tokens.length)
                result += s;
            else
                result += s + " , ";
            count++;
        }
        return result;
    }

    private String toXMLRequestMapviewerShape(VariableShape variableShape, Context context, int index, ITriple<String, String, String> xmlLegend) {
        String result = "";
        
        for(int i = 0; i< numberOfClasses; i++) {
            result += super.generateMarkerStyle(variableShape.getDefaultShapes(), variableShape.getColor(), variableShape.getSize(), variableShape.getSize() );
        
            result+=ManagerStyles.TOKEN;
            result+=ManagerStyles.getName();
            result+=ManagerStyles.TOKEN;
        }
        
        String[] markers = result.split(ManagerStyles.TOKEN);
        result+="<AdvancedStyle>\n";
        result+="<BucketStyle>\n";
        result+="<Buckets>\n";
        
        
         if(typeOfDistribution.equals(ContinuosStyle.Distribution.UNIFORM.toString())) {
            List<String> distinctValues = context.getDistinctValuesByID((index));
            Map<Integer, String> values = splitValues(distinctValues, numberOfClasses);
             
            int indexToNames = 1;                                       
            for(int i = 0; i < numberOfClasses; i++) {
                String value = values.get(i);
                if(context.isClustering()) {
                    value = EditUtils.cleanString(values.get(i));
                }
                        
                result += "<CollectionBucket seq=\"" + i + "\" label=\""+ values.get(i)+"\"\n" + 
                "style=\""+markers[indexToNames]+"\">"+ value +"</CollectionBucket> \n";
                
                indexToNames+=2;
            }
        }
        else if(typeOfDistribution.equals(DiscreteStyle.Distribution.CUSTOM.toString())) {
            //TODO
        }
        
        result+="</Buckets>\n";
        result+="</BucketStyle>\n";
        result+="</AdvancedStyle>\n";   
        
        result+=ManagerStyles.TOKEN;
        String nameStyle = ManagerStyles.getName();
        result+=nameStyle;
        
        
        //generate the legend
        xmlLegend.setFirst(xmlLegend.getFirst() + super.addEntryToLegend(context.getAlphaNumericAttributeByIndex(index), nameStyle));
        
        return result;
    }
    
    private String toXMLRequestMapviewerColor(VariableColor variableColor,
                                              Context context, int index, ITriple<String, String, String> xmlLegend) {
        String result = "";
        
        variableColor.reset();
        for(int i = 0; i< numberOfClasses; i++) {
            result+="<g class=\"color\" style=\"fill:" + variableColor.getOneColor(i) + "\"/> \n";
        
            result+=ManagerStyles.TOKEN;
            result+=ManagerStyles.getName();
            result+=ManagerStyles.TOKEN;
        }
        
        String[] markers = result.split(ManagerStyles.TOKEN);
        result+="<AdvancedStyle>\n";
        result+="<BucketStyle>\n";
        result+="<Buckets>\n";
        
        
         if(typeOfDistribution.equals(ContinuosStyle.Distribution.UNIFORM.toString())) {
            
            List<String> distinctValues = context.getDistinctValuesByID(index);
            
            int numberDistintcValues = distinctValues.size();
            if(numberDistintcValues <= numberOfClasses) {
                numberOfClasses = numberDistintcValues;
            }
                                                             
            Map<Integer, String> values = splitValues(distinctValues, numberOfClasses);
             
            int indexToNames = 1;                                       
            for(int i = 0; i < numberOfClasses; i++) {
                String discreteValues = values.get(i);
               
                if(context.isClustering()) {
                    discreteValues = "";
                    String[] discreteValuesTemp = discreteValues.split(",");
                    for(int j = 0; j < discreteValuesTemp.length; j++) {
                        if(j == discreteValuesTemp.length - 1) {
                            System.out.println("CLEAN STRING: " + values.get(i));
                            discreteValues += EditUtils.cleanString(values.get(i));
                        }
                        else discreteValues += EditUtils.cleanString(values.get(i)) + ",";
                            
                    }
                }
                    
                result += "<CollectionBucket seq=\"" + i + "\" label=\""+ values.get(i)+"\"\n" + 
                "style=\""+markers[indexToNames]+"\">"+discreteValues+"</CollectionBucket> \n";
                
                indexToNames+=2;
            }
        }
        else if(typeOfDistribution.equals(DiscreteStyle.Distribution.CUSTOM.toString())) {
            //TODO
        }
        
        result+="</Buckets>\n";
        result+="</BucketStyle>\n";
        result+="</AdvancedStyle>\n";   
        
        result+=ManagerStyles.TOKEN;
        String nameStyle = ManagerStyles.getName();
        result+= nameStyle;
        
        //generate the legend
        xmlLegend.setFirst(xmlLegend.getFirst() + super.addEntryToLegend(context.getAlphaNumericAttributeByIndex(index), nameStyle));
        
        return result;
    }

    public void setTypeOfDistribution(String typeOfDistribution) {
        this.typeOfDistribution = typeOfDistribution;
    }

    private Map<Integer, String> splitValues(List<String> distinctValues, int numberOfGroups) {
        Map<Integer, String> result = new HashMap<Integer, String>();
        
        Iterator<String> it = distinctValues.iterator();
        int index = 0;
        while(it.hasNext()) {
            String next = it.next();
            
            if(index == numberOfGroups)
                index = 0;
            
            if(result.get(index) == null) {
                result.put(index, next);    
            }
            else {
                String newValue = result.get(index) + ", " + next;
                result.put(index, newValue);
            }
            index++;
        }
        
        return result;
    }
    
    public ArrayList<ITriple<String, String, String>> info() {
        /*
         * return "nao definido"; */
        return null;
    }
}
