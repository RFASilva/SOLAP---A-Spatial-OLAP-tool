package solap.entities;

import java.util.Vector;

public class SOLAPMeasure {
    private String id;
    private String name;
    private String type;
    private String format;
    //calculated measure only
    private String formula;
    //numerical measure only
    private Vector<String> aggregationOperators;
    
    public SOLAPMeasure(String id, String name, String type, String format, Vector<String> aggregationOperators) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.format = format;
        this.aggregationOperators = aggregationOperators;
    }
    
    public SOLAPMeasure(String id, String name, String type, String format, String formula) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.format = format;
        this.formula = formula;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getFormula() {
        return formula;
    }

    public void setAggregationOperators(Vector<String> aggregationOperators) {
        this.aggregationOperators = aggregationOperators;
    }

    public Vector<String> getAggregationOperators() {
        return aggregationOperators;
    }
}
