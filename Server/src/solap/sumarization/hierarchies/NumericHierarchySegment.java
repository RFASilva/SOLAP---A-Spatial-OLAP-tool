package solap.sumarization.hierarchies;

public class NumericHierarchySegment {
    private double min;
    private double max;
    private String name;
    private int occurrencesCount;
    
    public NumericHierarchySegment(double min, double max) {
        this.min = min;
        this.max = max;
        this.name = min + " to " + max;
    }
    
    public String getName(){
        return name;
    }
    
    public String toString(){
        return "Segment = [ " + min + " , " + max + " ] Number of ocurrences = " + occurrencesCount;
    }
    
    public boolean belongs(double value){
        if(value >= min && value <= max)
            return true;
        return false;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMin() {
        return min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMax() {
        return max;
    }
    
    public void incrementOccurrences(){
        occurrencesCount++;    
    }

    public void setOccurrencesCount(int occurrencesCount) {
        this.occurrencesCount = occurrencesCount;
    }

    public int getOccurrencesCount() {
        return occurrencesCount;
    }
}
