package solap.entities;

import java.util.LinkedList;
import java.util.List;

public class ClusteringParams {
    
    private List<String> names;
    private List<String> values;
    
    public ClusteringParams() {
        this.names = new LinkedList<String>();
        this.values = new LinkedList<String>();
    }
    
    public void addParam(String name, String value) {
        names.add(name);
        values.add(value);
    }
    
    public List<String> getParamByIndex(int index) {
        List<String> result = new LinkedList<String>();
        
        result.add(names.get(index));
        result.add(values.get(index));
        
        return result;
    }
    
    public int size() {
        return names.size();
    }
   
   
}
