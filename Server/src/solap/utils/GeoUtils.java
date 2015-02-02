package solap.utils;

import java.awt.geom.Point2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import oracle.spatial.geometry.JGeometry;

import solap.clustering.support.Distances;
import solap.clustering.support.Instance;
import solap.clustering.support.Polygon;

public class GeoUtils {

    //Different coordinate systems of Earth
    public static interface WGS84 {
        public static final double a = 6378137; // m
        public static final double b = 6356752.314245; // m
        public static final double earthFlatCoef = 298.257223563;
    }

    public static interface GRS80 {
        public static final double a = 6378137; // m
        public static final double b = 6356752.314140 ; // m
        public static final double earthFlatCoef = 298.257222101;
    }
    
    //Constants
    private static final double a = 6378137; // m (same for WGS84 and GRS80)
    //Conversion factor for degrees to radians
    private static final double DEGREES_TO_RADIANS = Math.PI / 180.0;
    //Conversion factor for radians to degres
    private static final double RADIANS_TO_DEGREES = 180.0 / Math.PI;

    public enum EarthModel { WGS84, GRS80 }

    private GeoUtils() {
    }
    
    // Convert a collection of points into cartesian XYZ coordinate with specified
    // geodetic system.
    public static Triple[] geo2xyzCollection(double[] points) {
        Triple[] result = new Triple[(points.length / 2)];

        int num = 0;
        for(int i = 0; i < points.length; i+=2) {
            double[] temp = geo2xyz(points[i], points[i+1], 0, EarthModel.WGS84);
            result[num] = new Triple(temp[0], temp[1], temp[2]);
            num++;
        }
        
        return result;
    }
    
    // Convert geodetic coordinate into cartesian XYZ coordinate with specified
    // geodetic system.
    public static double[] geo2xyz(double longitude, double latitude, 
                            double altitude, final EarthModel geoSystem) {
    
        double[] result = new double[3];                            
        double a = 0.0;
        double earthFlatCoef = 0.0;

        if (geoSystem == EarthModel.WGS84) {
            a = WGS84.a;
            earthFlatCoef = WGS84.earthFlatCoef;
        } 
        else if (geoSystem == EarthModel.GRS80) {
            a = GRS80.a;
            earthFlatCoef = GRS80.earthFlatCoef;
        }
        else return null; 

        double e2 = 2.0 / earthFlatCoef - 1.0 / (earthFlatCoef * earthFlatCoef);

        double lat = latitude * DEGREES_TO_RADIANS;
        double lon = longitude * DEGREES_TO_RADIANS;

        double sinLat = Math.sin(lat);
        double cosLat = Math.cos(lat);
        double N = a / Math.sqrt(1 - e2*sinLat*sinLat);

        result[0] = (N + altitude) * cosLat * Math.cos(lon); // in m
        result[1] = (N + altitude) * cosLat * Math.sin(lon); // in m
        result[2] = ((1 - e2) * N + altitude) * sinLat;   // in m
        
        return result;
    }

    // Convert cartesian XYZ coordinate into geodetic coordinate with specified 
    // geodetic system.
    public static Triple<Double, Double, Double> xyz2geo(Triple<Double, Double, Double> xyz, final EarthModel geoSystem) {
        Triple<Double, Double, Double> result = new Triple<Double, Double, Double>();
        double a = 0.0;
        double b = 0.0;
        double earthFlatCoef = 0.0;

        if (geoSystem == EarthModel.WGS84) {
            a = WGS84.a;
            b = WGS84.b;
            earthFlatCoef = WGS84.earthFlatCoef;
        }
        else if (geoSystem == EarthModel.GRS80) {
            a = GRS80.a;
            b = GRS80.b;
            earthFlatCoef = GRS80.earthFlatCoef;
        } 
        else return null;


        double e2 = 2.0 / earthFlatCoef - 1.0 / (earthFlatCoef * earthFlatCoef);
        double ep2 = e2 / (1 - e2);

        double x = xyz.getFirst();
        double y = xyz.getSecond();
        double z = xyz.getThird();
        double s = Math.sqrt(x*x + y*y);
        double theta = Math.atan(z*a/(s*b));

        //Set the longitude 
        double tempx = (float)(Math.atan(y/x) * RADIANS_TO_DEGREES); 
        if (tempx < 0.0 && y >= 0.0)
            tempx += 180.0;
        else if (tempx > 0.0 && y < 0.0) 
            tempx -= 180.0;
        
        result.setFirst(tempx);
        
        //Set the latitude
        result.setSecond( (Math.atan((z + ep2*b*Math.pow(Math.sin(theta), 3))
                        / (s - e2*a*Math.pow(Math.cos(theta), 3))) * RADIANS_TO_DEGREES));
                                       
        result.setThird(xyz.getThird());
        
        return result;                            
    }
    
    public static JGeometry computeCentroidOfPoints(List<Instance> points) {
        double lat = 0.0;
        double longit = 0.0;
        
        for(Instance point: points) {
            lat += point.getX();
            longit += point.getY();
        }
        int numPoints = points.size();
        
        return JGeometry.createPoint(new double[]{(lat/(numPoints+0.0)), (longit/(numPoints+0.0))},2,8307);
    }
    
    public static Point2D computeCentroidOfPolygon(double[] points) {
        double x = 0.0;
        double y = 0.0;
        
        for (int i = 0; i < points.length-3; i++) {
            x = x + (points[i] + points[i+2]) * (points[i+1] * points[i+2] - points[i] * points[i+3]);
            y = y + (points[i+1] + points[i+3]) * (points[i+1] * points[i+2] - points[i] * points[i+3]);
        }
        
        x /= (6 * area(points));
        y /= (6 * area(points));
        return new Point2D.Double(x, y);
    }
    
    // return signed area of polygon
    private static double area(double[] points) {
        double sum = 0.0;
        for (int i = 0; i < points.length-3; i++)
            sum = sum + (points[i] * points[i+3]) - (points[i+1] * points[i+2]);
        
        double area = 0.5 * sum;
        
        //if the points are ordered by clockwise order the sum will be negative but the coordinates will be correct even this case
        return Math.abs(area);
    }
    
    public static double findAngle(Point2D center, Point2D target) {        
        //compute the circle made by the center and this target point
        double ax = target.getX() - center.getX();
        double bx = target.getY() - center.getY();
         
        double r = Math.sqrt(Math.pow(ax,2) + Math.pow(bx,2));
        
        int quadrant = 0;
        
        if(target.getX() >= 0 && target.getY() >= 0)
            quadrant = 1;
        if(target.getX() <= 0 && target.getY() >= 0)
            quadrant = 2;
        if(target.getX() <= 0 && target.getY() <= 0)
            quadrant = 3;
        if(target.getX() >= 0 && target.getY() <= 0)
            quadrant = 4;
        
        double sin = 0.0;
        double cos = 0.0;
        double angle = 0.0;
        
        if(quadrant == 1 || quadrant == 2) {
            cos = (target.getX() - center.getX()) / (r+0.0);
            return Math.toDegrees(Math.acos(cos));
        }
        
        if(quadrant == 3) {
            sin = (target.getY() - center.getY()) / (r+0.0);
            angle = Math.toDegrees(Math.asin(sin));
            return Math.abs(angle) + 180.0;
        }
        
        if(quadrant == 4) {
            cos = (target.getX() - center.getX()) / (r+0.0);
            return Math.toDegrees(Math.acos(cos)) + 270;
        }
        
        return -1;
    }
   
   /* 
    //Algorithm used to compute the union of polygons was proposed in
    // "Efficient Polygon Amalgamation Methods for Spatial OLAP and Spatial Data Mining"
    // and is designated by ADJACENCY algorithm
    public static JGeometry computeUnionOfPolygons(Map<String, Instance> P, Map<String, Instance> S, Map<String, List<String>> adjacency) {
        JGeometry result = null;
        
        //Find DS
        Map<String, Instance> ds = computeDsList(P,S,adjacency);
        Map<String, Instance> dsPlus = computeDsPlusList(P,S,adjacency, ds);
        
        List<Segment> retrieveData = retrieveData(ds, dsPlus);
        removeLineSegments(retrieveData);
        double[] points = new double[retrieveData.size()*2];
        
        int index = 0;
        Iterator<Segment> it = retrieveData.iterator();
        while(it.hasNext()) {
            
          
         //TODO: Colocar os vertices por ordem counterclockwise
          points[index] = 
            points[index + 1] =  
        }
        
        
        return JGeometry.createLinearPolygon(points, 2, 8307);
                 
    }
    
    //Compute the boundary polygons
    private static Map<String, Instance> computeDsList(Map<String, Instance> P,
                                                Map<String, Instance> S,
                                                Map<String, List<String>> adjacency) {
        
        Map<String, Instance> result = new HashMap<Instance, String>(P.size()*2);
                          
        for(Instance inst: S.values()) {
            //NeighbotPolygon is the correspondent p'
            boolean exist = false;
            for(String neighborPolygon: adjacency.get(inst.getSemancticAttribute())) {
                if(!S.containsKey(neighborPolygon)) {
                    exist = true;
                    result.put(inst.getSemancticAttribute(), inst);
                    break;
                }
            }
        }
        
        return result;
    }
    
    //Compute the sub-boundary polygons
    private static Map<Instance, String> computeDsPlusList(Map<String, Instance> P,
                                                           Map<String, Instance> S,
                                                           Map<String, List<String>> adjacency,
                                                           Map<String, Instance> ds) {
        
        Map<String, Instance> result = new HashMap<Instance, String>(P.size()*2);
        
        for(Instance inst: ds.values()) {
            boolean exist = false;
            for(String neighborPolygon: adjacency.get(inst.getSemancticAttribute())) {
                if(P.containsKey(neighborPolygon) && !ds.containsKey(neighborPolygon)) {
                    exist = true;
                    result.put(inst.getSemancticAttribute(), inst);
                    break;
                }
            }
        }
        
        return result;
    }
    
    private static List<Segment> retrieveData(Map<String, Instance> ds,
                                              Map<String, Instance> dsPlus) {
        
        List<Segment> result = new LinkedList<Segment>();
        for(Instance inst: ds.values()) {
            Polygon a = inst.getPolygon();
            Point2D a1 = a.getPoint(0);
            Point2D a2 = null; 
            for(int i = 0; i < a.getNumberPoints() - 1; i++) {
                a2 = a.getPoint(i + 1);
                result.add(new Segment(a1,a2,false));
                a1 = a2;
            }
        }
        
        for(Instance inst: dsPlus.values()) {
            Polygon a = inst.getPolygon();
            Point2D a1 = a.getPoint(0);
            Point2D a2 = null; 
            for(int i = 0; i < a.getNumberPoints() - 1; i++) {
                a2 = a.getPoint(i + 1);
                result.add(new Segment(a1,a2,true));
                a1 = a2;
            }
        }

        
        return result;
    }
    
    private static void removeLineSegments(List<Segment> retrieveData) {
        
        Collections.sort(retrieveData);
        Iterator<Segment> it = retrieveData.iterator();
        
        Point2D previous = it.next().getMiddlePoint();
        while(it.hasNext()) {
            Point2D next = it.next().getMiddlePoint();
            if(next.equals(previous)) {
                previous = next;
                it.remove();
            }
        }
        
        Iterator<Segment> it1 = retrieveData.iterator();
        previous = it.next().getMiddlePoint();
        while(it.hasNext()) {
            Segment next = it.next();
            if(next.isAuxiliary()) {
                it.remove();
            }
        }
        
    }


    
    public static void fillAdjacencyTable(Map<String, List<String>> adjacency, List<Instance> P, Map<String, Instance> aux) {
        for(int i = 0; i < P.size(); i++) {
            Instance inst1 = P.get(i);
            aux.put(inst1.getSemancticAttribute(), inst1);
            for(int j = 0; j < P.size(); j++) {
                if(i != j) {
                    Instance inst2 = P.get(j);
                    boolean shareSegment = shareBoundary(inst1.getPolygon(), inst2.getPolygon());
                    if(shareSegment) {
                        if(adjacency.get(inst1.getSemancticAttribute()) == null)
                            adjacency.put(inst1.getSemancticAttribute(), new LinkedList<String>());
                        adjacency.get(inst1.getSemancticAttribute()).add(inst2.getSemancticAttribute());
                    }
                }
            }
        
        }
    }
    
    private static boolean shareBoundary(Polygon a, Polygon b) {
        Point2D a1 = a.getPoint(0);
        Point2D a2 = null; 
        for(int i = 0; i < a.getNumberPoints() - 1; i++) {
            a2 = a.getPoint(i + 1);
            Point2D[] edge = new Point2D[2];
            edge[0] = a1;
            edge[1] = a2;
            
            Point2D b1 = b.getPoint(0);
            Point2D b2 = null; 
            for(int j = 0; j < b.getNumberPoints()-1; j++) {
                b2 = b.getPoint(j + 1);
                if( equalEdge(edge[0], edge[1], b1, b2) || equalEdge(b1,b2,edge[0],edge[1]))
                    return true;
            }
            
            a1 = a2;
        }
        
        return false;
    }
    
    private static boolean equalEdge(Point2D a1, Point2D a2, Point2D b1, Point2D b2) {
        return (a1.getX() == b2.getX()) && (a1.getY() == b2.getY()) && (a2.getX() == b1.getX()) && (a2.getY() == b1.getY());
    }*/
    
   
}
