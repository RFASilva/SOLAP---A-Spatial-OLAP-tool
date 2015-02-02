package solap.clustering.support;

import java.awt.geom.Point2D;

public class Polygon {
    
    //Holds the vertexs of the polygon
    private double[] points;
    
    public Polygon(double[] points) {
        this.points = points;
    }
    
    public Point2D.Double getPoint(int index) {
        index *= 2;
        return new Point2D.Double(points[index], points[index+1]);
    }
    
    public int getNumberPoints() {
        return (points.length + 1) / 2;
    }
 
}
