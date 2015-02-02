package solap.styles;

public class IntervalValues {

    private String label;
    private int min;
    private int max;
    //ID of style associated to this interval
    private String id;
    
    public IntervalValues(int min, int max, String label) {
        this.min = min;
        this.max = max;
        this.label = label;
    }
    
    public IntervalValues(int min, int max, String label, String id) {
        this.min = min;
        this.max = max;
        this.label = label;
        this.id = id;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMin() {
        return min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
