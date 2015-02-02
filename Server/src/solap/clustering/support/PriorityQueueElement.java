package solap.clustering.support;

public class PriorityQueueElement {

    private double priority;

    private Object o;

  
    public PriorityQueueElement(double priority, Object o) {
        this.priority = priority;
        this.o = o;
    }
  
    public double getPriority() {
        return priority;
    }

    public Object getObject() {
        return o;
    }
    
}
