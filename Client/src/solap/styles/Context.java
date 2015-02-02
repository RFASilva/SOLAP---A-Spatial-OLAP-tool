package solap.styles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import solap.utils.ITriple;
import solap.utils.Triple;


public class Context {

    //Types of data that could be in the headers

    public int getNumberOfMeasures() {
        return numberOfMeasures;
    }

    public void setSummarization(boolean summarization) {
        this.summarization = summarization;
    }

    public boolean isSummarization() {
        return summarization;
    }

    public void setNumDistinctCharacteristics(int numDistinctCharacteristics) {
        this.numDistinctCharacteristics = numDistinctCharacteristics;
    }

    public int getNumDistinctCharacteristics() {
        return numDistinctCharacteristics;
    }

    public void setData(Vector<Vector<String>> data) {
        this.data = data;
    }

    public Vector<Vector<String>> getData() {
        return data;
    }
    
    //Used by DiscreteStyle in summarization process
    public List<String> getDistinctValuesFromData(){
        List<String> result = new LinkedList<String>();
        for(Vector<String> v : data){
            String characteristic = "";
            int limit = v.size();
            if(numberOfMeasures > 0)
                limit = limit - numberOfMeasures;
            for(int i=1;i<limit;i++){
                characteristic += v.get(i);
                if((i+1)!=limit)
                    characteristic += ManagerStyles.TOKEN;
            }
            if(!result.contains(characteristic))
                result.add(characteristic);
        }
        return result;
    }

    public void setNumberOfCreatedGroups(int numberOfCreatedGroups) {
        this.numberOfCreatedGroups = numberOfCreatedGroups;
    }

    public int getNumberOfCreatedGroups() {
        return numberOfCreatedGroups;
    }

    public void setGeneralizationType(String generalizationType) {
        this.generalizationType = generalizationType;
    }

    public String getGeneralizationType() {
        return generalizationType;
    }

    public enum TypeOfAttr {NOMINAL, ORDINAL, DISCRETE}
    
    //Context Definition
    private Map<String, String> geometryType;
    private int numberNumericalColumns;
    private int numberAlphaNumericalColumns;
    
    //Context data
    //Store information about the measures<name, min, max>
    private List<ITriple<String, Double, Double>> measures;
    
    private Map<Integer, List<Double>> columnValues;
    private int numberOfMeasures;
    
    //Store information about the alphanumeric values;
    private Map<String, List<String>> alphanumericValues;
    private List<String> alphaNumericValuesIndex;
    
    private boolean clustering;
    
    //For summarization proposes
    private boolean summarization;
    private String generalizationType;
    private int numDistinctCharacteristics;
    private int numberOfCreatedGroups;
    private Vector<Vector<String>> data; //With all information
    
    public Context() {
        this.geometryType = new HashMap<String, String>();
        this.numberAlphaNumericalColumns = 0;
        this.measures = new LinkedList<ITriple<String, Double, Double>>();
        this.numberOfMeasures = 0;
        this.alphanumericValues = new HashMap<String, List<String>>();
        this.alphaNumericValuesIndex = new LinkedList<String>();
        this.columnValues = new HashMap<Integer, List<Double>>();
        this.clustering = false;
        this.summarization = false;
    }
    
    public void addColumnValue(int columnName, double value) {
        if(columnValues.get(columnName) == null)
            columnValues.put(columnName, new ArrayList<Double>());
        columnValues.get(columnName).add(new Double(value));
    }
    
    public List<Double> getColumnValuesByIndex(int index) {
        return columnValues.get(index);
    }
    
    public void addMeasure(String name, double min, double max) {
        measures.add(new Triple<String, Double, Double>(name, min, (max+1)));
    }
    
    public void addAlphaNumericColumn(String name, List<String> distinctValues) {
        //System.out.println("VOU ADICIONAR ESTE ATRIBUTO: " + name);
        alphaNumericValuesIndex.add(name);
        alphanumericValues.put(name, distinctValues);
    }
    
    public void addGeometryType(String geometryType) {
        this.geometryType.put(geometryType, "");
    }
    
    public void addGeometryTypeAll(List<String> geometryTypes) {
        for(String s: geometryTypes)
            this.geometryType.put(s, "");
    }
    
    public int getNumberOfSpatialObjects() {
        return geometryType.size();
    }
    
    public int getNumberOfNumericalCol() {
        return measures.size();
    }
    
    public ITriple<String, Double, Double> getMeasureByIndex(int index) {
        return measures.get(index);
    }
    
    public String getAlphaNumericAttributeByIndex(int index) {
        return alphaNumericValuesIndex.get(index);
    }
    
    public List<String> getDistinctValuesByID(int index) {
        return alphanumericValues.get(alphaNumericValuesIndex.get(index));
    }
    
    public int getNumberNumericalColumns() {
        return numberNumericalColumns;
    }
    
    public void setNumberNumericalColumns(int numberNumericalColumns) {
        this.numberNumericalColumns = numberNumericalColumns;
    }
    
    public void setNumberOfMeasures(int numberOfMeasures) {
        this.numberOfMeasures = numberOfMeasures;
    }
    
    public void setNumberAlphaNumericalColumns(int numberAlphaNumericalColumns) {
        this.numberAlphaNumericalColumns = numberAlphaNumericalColumns;
    }

    public int getNumberAlphaNumericalColumns() {
        return numberAlphaNumericalColumns;
    }
    
    public boolean isSpatialPoint() {
        if(geometryType.keySet().size() == 1)
            return geometryType.containsKey("point");
        return false;
    }
    
    public boolean isSpatialPolygon() {
        if(geometryType.keySet().size() == 1)
            return geometryType.containsKey("polygon");
        return false;
    }
    
    public boolean isSpatialLine() {
        if(geometryType.keySet().size() == 1)
            return geometryType.containsKey("line");
        return false;
    }
    
    public void setClustering(boolean clustering) {
        this.clustering = clustering;
    }

    public boolean isClustering() {
        return clustering;
    }
    
    @Override
    public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                            + ((geometryType == null) ? 0 : geometryType.hashCode());
            result = prime * result + numberAlphaNumericalColumns;
            result = prime * result + numberNumericalColumns;
            return result;
    }

    @Override
    public boolean equals(Object obj) {
            if (this == obj)
                    return true;
            if (obj == null)
                    return false;
            if (!(obj instanceof Context))
                    return false;
            Context other = (Context) obj;
            if (geometryType == null) {
                    if (other.geometryType != null)
                            return false;
            } else if (!geometryType.equals(other.geometryType))
                    return false;
            if (numberAlphaNumericalColumns != other.numberAlphaNumericalColumns)
                    return false;
            if (numberNumericalColumns != other.numberNumericalColumns)
                    return false;
            return true;
    }
}
