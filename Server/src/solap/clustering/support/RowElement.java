package solap.clustering.support;

import java.io.IOException;
import java.io.StringReader;

import java.math.BigDecimal;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class RowElement {

    public enum aggrOperator {SUM, MIN, MAX, AVG}
    
    private List<String> attrs;
    private List<String> values;
    
    private List<Integer> indexs;
    private int numberMeasures;
    
    //To be used by equals method <groupId, lowerlevelAttributes and higherLevelAttributes>
    private List<String> toCompare;
    
    //To be used with the aggregator operator AVG
    private int numberRows;
    
    public RowElement(int numberMeasures) {
        numberRows = 0;
        this.numberMeasures = numberMeasures;
        this.attrs = new LinkedList<String>();
        this.values = new LinkedList<String>();
        this.indexs = new LinkedList<Integer>();
        this.toCompare = new LinkedList<String>();
    }
    
    public List<String> getToCompare() {
        return toCompare;
    }
    
    public void addElement(String attr, String value) {
        attrs.add(attr);
        values.add(value);
    }
    
    public String getValue(int index) {
        return values.get(index);
    }
    
    public void addToCompareElement(String element) {
        toCompare.add(element);
    }
    
    public void aggregate(RowElement other, Map<String, String> aggOperator) {
        System.out.println("NAME: " + this.attrs);
        System.out.println("Values: " +  this.values);
        System.out.println("TO COMPARE: " + this.toCompare);
        for(int i = 0; i < numberMeasures; i++) {
            //Initialize the values to aggregate
            String value1 = values.get(i);
            String value2 = other.getValue(i);
            String agOp = aggOperator.get(attrs.get(i));
            String result = aggregateValues(value1, value2, agOp);
            values.set(i, result);
            //To know what measure have the operator AVG
            if(agOp.equals(aggrOperator.AVG)) {
                indexs.add(i);
            }
        }
        System.out.println("SAI DO AGGREGATE");
    }
    
    private String aggregateValues(String value1, String value2, String aggOperator) {
        double result = 0.0;
        if(aggOperator.equals(aggrOperator.AVG.toString())) {
            result = Double.parseDouble(value1) + Double.parseDouble(value2);
            numberRows++;
        }
        else if(aggOperator.equals(aggrOperator.MIN.toString())) {
            double v1 = Double.parseDouble(value1);
            double v2 = Double.parseDouble(value2);
            if(v1 < v2)
                result = v1;
            result = v2;
        }
        else if(aggOperator.equals(aggrOperator.MAX.toString())) {
            double v1 = Double.parseDouble(value1);
            double v2 = Double.parseDouble(value2);
            if(v1 > v2)
                result = v1;
            result = v2;
        }
        else if(aggOperator.equals(aggrOperator.SUM.toString())) {
            result = Double.parseDouble(value1) + Double.parseDouble(value2);           
        }
        
        return new BigDecimal(Double.toString(result)).toPlainString();
    }
    

    private String buildXMLDefinition() {
        String result= "<ROW num=\"x\">";
        
        for(int i = 0; i < attrs.size(); i++) {
            if(indexs.contains(i)) {
                String value = (Double.parseDouble(values.get(i)) / (numberRows+0.0)) + "";
                result+="<" + attrs.get(i).toUpperCase() + ">" + value + "</" + attrs.get(i).toUpperCase() + ">";
            }
            else result+="<" + attrs.get(i).toUpperCase() + ">" + values.get(i) + "</" + attrs.get(i).toUpperCase() + ">";
        }
            
        result+="</ROW>";
        
        return result;
    }
    
    public void toXML(Document doc) {
        Node result = null;
        String xmlDefinition = buildXMLDefinition();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            result = docBuilder.parse(
                new InputSource(new StringReader(xmlDefinition))).getDocumentElement();
        } catch (Exception e) {
            System.out.println("Error building xml definition of the row of group" + e.getMessage());
        }
        
        result = doc.importNode(result, true);
        doc.getFirstChild().appendChild(result);
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof RowElement)) {
            return false;
        }
        final RowElement other = (RowElement)object;
        if (!(toCompare == null ? other.toCompare == null : toCompare.equals(other.toCompare))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((toCompare == null) ? 0 : toCompare.hashCode());
        return result;
    }    
}
