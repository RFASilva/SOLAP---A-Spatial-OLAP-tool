package solap.utils;

import java.awt.geom.Point2D;

public class Segment implements Comparable<Segment>{
    
    private Point2D start;
    private Point2D end;
    private boolean auxiliary;
    private Point2D middlePoint;
    
    public Segment(Point2D start, Point2D end, boolean auxiliary) {
        this.start = start;
        this.end = end;
        this.auxiliary = auxiliary;
        double x = (end.getX()-start.getX())/2;
        double y = (end.getY()-start.getY())/2;
        
        this.middlePoint = new Point2D.Double(x,y);
    }

    public void setStart(Point2D start) {
        this.start = start;
    }

    public Point2D getStart() {
        return start;
    }

    public void setEnd(Point2D end) {
        this.end = end;
    }

    public Point2D getEnd() {
        return end;
    }

    public void setAuxiliary(boolean auxiliary) {
        this.auxiliary = auxiliary;
    }

    public boolean isAuxiliary() {
        return auxiliary;
    }

    public Point2D getMiddlePoint() {
        return middlePoint;
    }

    public int compareTo(Segment o) {
        
         double x1 = this.middlePoint.getX();
         double x2 = o.getMiddlePoint().getX();
         if(x2 > x1)
             return 1;
         else if(x2 < x1)
             return -1;
         else {
             double y1 = this.middlePoint.getY();
             double y2 = this.middlePoint.getY();
             if(y2 > y1)
                 return 1;
             else if(y2 < y1)
                 return -1;
         }
         
         return 0;
    }
}
