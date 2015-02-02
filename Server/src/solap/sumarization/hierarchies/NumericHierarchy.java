package solap.sumarization.hierarchies;

import java.util.Vector;

public class NumericHierarchy {
    private Vector<NumericHierarchySegment> segments;
    
    public NumericHierarchy(Vector<NumericHierarchySegment> segments) {
        this.segments = segments;
    }

    public void setSegments(Vector<NumericHierarchySegment> segments) {
        this.segments = segments;
    }

    public Vector<NumericHierarchySegment> getSegments() {
        return segments;
    }
    
    public String getSegmentName(double value){
        String name="";
        for(NumericHierarchySegment s: segments){
            if(s.belongs(value)){
                name = s.getName();
                break;
            }
        }
        return name;
    }
}
