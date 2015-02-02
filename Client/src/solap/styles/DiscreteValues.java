package solap.styles;

import java.util.List;
import java.util.LinkedList;

public class DiscreteValues {

    private String label;
    private String[] values;
    
    //ID of style associated to this interval
    private String id;

    public DiscreteValues(String label, String[] values) {
        this.label = label;
        this.values = values;
    }
    
    public DiscreteValues(String label, String[] values, String id) {
        this.label = label;
        this.values = values;
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public String getId() {
        return id;
    }
}
