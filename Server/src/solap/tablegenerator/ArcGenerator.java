package solap.tablegenerator;

import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import oracle.spatial.geometry.*;

import solap.utils.ITriple;
import solap.utils.Triple;

public class ArcGenerator {

    public static final int GTYPE_TWO_DIMENSIONAL_LINE_STRING = 2;
    public static final int SRID = 8307;
    public static final int ELEM_TYPE_LINE_STRING = 2;
    public static final int INTERPRETATION_FOR_CONNECTED_ARCS = 2;
    
    //To see if there is already an arc (the arc A to B and the arc B to A must be a different arc)
    private static Map<Point2D.Double, List<ITriple<Double,Double,String>> > orientationArc = new HashMap<Point2D.Double, List<ITriple<Double,Double,String>> >();
    
    private static List<Double[]> allArcs = new LinkedList<Double[]>();
    
    public static void reset() {
        orientationArc.clear();
        allArcs.clear();
    }
    
    public static JGeometry computeArc(JGeometry point1, JGeometry point2) {
        Point2D p1 = point1.getJavaPoint();
        Point2D p2 = point2.getJavaPoint();
        System.out.println(p1);
        System.out.println(p2);
        
        Triple<Double, Double, Double> xyzSpatialObject1 = new Triple<Double, Double, Double>(p1.getX(), p1.getY(), 0.0);
        Triple<Double, Double, Double> xyzSpatialObject2 = new Triple<Double, Double, Double>(p2.getX(), p2.getY(), 0.0);
        
        updateStructures((Point2D.Double)p1,(Point2D.Double)p2);
        
        // Compute the middle point for draw a arc
        Triple<Double, Double, Double> middlePoint = arcMidPoint(xyzSpatialObject1,xyzSpatialObject2);
        
        //compute SDO_ELEM_INFO_ATTR array
        int[] elemInfo = new int[3];
        //offset
        elemInfo[0] = 1;
        //Element-type
        elemInfo[1] = ELEM_TYPE_LINE_STRING;
        //Interpretation
        elemInfo[2] = INTERPRETATION_FOR_CONNECTED_ARCS;
        
        //compute SDO_ATTRIBUTE_ARRAY
        double[] ordinates = new double[6];
        ordinates[0] = p1.getX(); 
        ordinates[1] = p1.getY();
        ordinates[2] = middlePoint.getFirst();
        ordinates[3] = middlePoint.getSecond();
        ordinates[4] = p2.getX();
        ordinates[5] = p2.getY();
        
        //int gtype, int srid, int eleminfo[], double[] ordinates
        JGeometry result = new JGeometry(GTYPE_TWO_DIMENSIONAL_LINE_STRING,
                        SRID, elemInfo, ordinates);
        
        addArc(ordinates, middlePoint.getThird());
        
        return result;
    }
    
    //Compute the midpoint of an arc given the start and end points of the desired arc
    private static Triple<Double, Double, Double> arcMidPoint(Triple<Double, Double, Double> first, Triple<Double, Double, Double> last) {
        Triple<Double, Double, Double> result = new Triple<Double, Double, Double>();
        
        double segmentMidX = ( last.getFirst() + first.getFirst() ) / 2.0;
        double segmentMidY = ( last.getSecond() + first.getSecond() ) / 2.0;
        double lineLenght = Math.sqrt( Math.pow((last.getFirst() - first.getFirst()), 2.0) +  Math.pow((last.getSecond() - first.getSecond()), 2.0));
        double lineHalfLenght = lineLenght / 2.0;
        double radius = lineLenght*1.5;
        
        result.setThird(radius); 
        
        double ang = Math.asin( (lineHalfLenght + 0.0)/(radius + 0.0));
        double h = 0.1*radius;
        
        Random random = new Random();
        
        // Infers what case to consider
        if (first.getFirst() - last.getFirst() == 0) {
            
        }
        else {
            double slope = ( first.getSecond() - last.getSecond() ) / ( first.getFirst() - last.getFirst());
            String orientation = getOrientation(new Point2D.Double(first.getFirst(), first.getSecond()), new Point2D.Double(last.getFirst(), last.getSecond()));
            
            String minInterDirection = "";
            if(orientation.equals("")) {
                int numIntersLeft = numberIntersections("left", first, last, slope, radius,segmentMidX,segmentMidY,ang,h);
                //System.out.println("Left intersections: " + numIntersLeft);
                int numIntersRight = numberIntersections("right", first, last, slope, radius,segmentMidX,segmentMidY,ang,h);   
                //System.out.println("Right intersections: " + numIntersRight);
                       
                if(numIntersLeft >= numIntersRight) minInterDirection = "left";
                else if(numIntersRight > numIntersLeft) minInterDirection ="right";
            }
            
            String description = "";
            if( slope > 0) {
                if(minInterDirection.equals("left") && orientation.equals("")) description = slopePosLeft(result,segmentMidX, segmentMidY, ang, h);
                else if(minInterDirection.equals("right") && orientation.equals("")) description = slopePosRight(result, segmentMidX, segmentMidY, ang, h);
                else if(orientation.equals("left")) {
                    radius = lineLenght*1.5;
                    ang = Math.asin( (lineHalfLenght + 0.0)/(radius + 0.0));
                    h = 0.13*radius;
                    slopePosLeft(result, segmentMidX, segmentMidY, ang, h);
                }
                else if(orientation.equals("right")) {
                    radius = lineLenght*1.5;
                    ang = Math.asin( (lineHalfLenght + 0.0)/(radius + 0.0));
                    h = 0.13*radius;
                    slopePosRight(result,segmentMidX, segmentMidY, ang, h);
                }
            }
            
            if( slope < 0) {
                if(minInterDirection.equals("left") && orientation.equals("")) description = slopeNegLeft(result,segmentMidX, segmentMidY, ang, h);
                else if(minInterDirection.equals("right") && orientation.equals("")) description = slopeNegRight(result, segmentMidX, segmentMidY, ang, h);
                else if(orientation.equals("left")) {
                    radius = lineLenght*1.5;
                    ang = Math.asin( (lineHalfLenght + 0.0)/(radius + 0.0));
                    h = 0.13*radius;
                    slopeNegLeft(result, segmentMidX, segmentMidY, ang, h);
                }
                else if(orientation.equals("right")) {
                    radius = lineLenght*1.5;
                    ang = Math.asin( (lineHalfLenght + 0.0)/(radius + 0.0));
                    h = 0.13*radius;
                    slopeNegRight(result,segmentMidX, segmentMidY, ang, h);
                }
            }
            
            updateArc(new Point2D.Double(first.getFirst(), first.getSecond()), new Point2D.Double(last.getFirst(), last.getSecond()), description);
        }
        
        return result; 
    }

    private static void updateStructures(Point2D.Double p1, Point2D.Double p2) {
        if(orientationArc.get(p1) == null)
            orientationArc.put(p1,new LinkedList<ITriple<Double, Double, String>>());
        
        ITriple<Double, Double, String> tempP2 = new Triple<Double, Double, String>(p2.getX(), p2.getY(), "");
        
        orientationArc.get(p1).add(tempP2);
    }
    
    private static void updateArc(Point2D.Double p1, Point2D.Double p2, String description) {
        
        List<ITriple<Double, Double, String>> dest = orientationArc.get(p1);
        Iterator<ITriple<Double, Double, String>> it = dest.iterator();
        
        while(it.hasNext()) {
            ITriple<Double, Double, String> next = it.next();
            if(next.getFirst() == p2.getX() && next.getSecond() == p2.getY()) {
                next.setThird(description);
                break;
            }
        }
    }
    
    private static String getOrientation(Point2D.Double p1, Point2D.Double p2) {
        if(orientationArc.get(p2) == null)
            return "";
        
        List<ITriple<Double, Double, String>> dest = orientationArc.get(p2);
        Iterator<ITriple<Double, Double, String>> it = dest.iterator();
        
        while(it.hasNext()) {
            ITriple<Double, Double, String> next = it.next();
            if(next.getFirst() == p1.getX() && next.getSecond() == p1.getY()) {
                return next.getThird();
            }
        }
        
        return "";
    }
    
    private static String slopePosLeft(ITriple<Double, Double, Double> m, double x, double y, double ang, double h) {
        //Left arc
        m.setFirst(x - Math.cos(ang)*h);
        m.setSecond(y +  Math.sin(ang)*h);
        //System.out.println("LEFT ARC");
        return "left";
    }
    
    private static String slopePosRight(ITriple<Double, Double, Double> m, double x, double y, double ang, double h) {
        //Right arc
        ang = Math.PI - (Math.PI/2.0) - ang;
        m.setFirst(x + Math.sin(ang)*h);
        m.setSecond(y - Math.cos(ang)*h);
        //System.out.println("RIGHT ARC");
        return "right";
    }
    
    private static String slopeNegLeft(ITriple<Double, Double, Double> m, double x, double y, double ang, double h) {
        //Left arc
        m.setFirst(x + Math.cos(ang)*h);
        m.setSecond(y +  Math.sin(ang)*h);
        //System.out.println("LEFT ARC");
        return "left";
    }
    
    private static String slopeNegRight(ITriple<Double, Double, Double> m, double x, double y, double ang, double h) {
        //Right arc
        ang = Math.PI - (Math.PI/2.0) - ang;
        m.setFirst(x - Math.sin(ang)*h);
        m.setSecond(y - Math.cos(ang)*h);
        //System.out.println("RIGHT ARC");
        return "right";
    }
    
    private static boolean intersect(Double[] arc1, Double[] arc2) {
        if(arc1 == null || arc2 == null)
            return false;
        
        Point2D centerArc1 = center(arc1);
        double radius1 = arc1[6];
        
        Point2D centerArc2 = center(arc2);
        double radius2 = arc2[6];
        
        double distCenters = centerArc1.distance(centerArc2);
        Point2D[] points = null;           
        
        //The circles intersect each other
        if(distCenters < (radius1 + radius2)) points = determineIntersectionPoints(centerArc1, centerArc2, radius1, radius2);
        
        if(points != null) {
            System.out.println("p1: " + points[0]);
            System.out.println("p2: " + points[1]);
            return areIntersectedArcs(arc1, arc2, centerArc1, centerArc2, points[0], points[1], radius1, radius2);
        }
        
        //The circles are separate or one circle is contained within the other
        return false;
    }
    
    //Compute the center of circle given two points and radius
    //[x1,y1,mx,my,x2, y2, radius
    private static Point2D center (Double[] arc) {
        if (arc == null)
            return null;
        
        Point2D result1;
        Point2D result2;
        
        Point2D p1 = new Point2D.Double(arc[0], arc[1]);
        Point2D p2 = new Point2D.Double(arc[4], arc[5]);
        
        double xSquare = (p2.getX() - p1.getX()) * (p2.getX() - p1.getX());
        double ySquare = (p2.getY() - p1.getY()) * (p2.getY() - p1.getY());
        
        double distP1P2 = Math.sqrt(xSquare + ySquare);
        Point2D p3 = new Point2D.Double( ( (p1.getX() + p2.getX()) / 2.0), ( (p1.getY() + p2.getY()) / 2.0));
        
        double tempX = Math.sqrt( (arc[6]*arc[6]) - (((distP1P2+0.0)/2.0)*((distP1P2+0.0)/2.0)) ) * ((p1.getY() - p2.getY()) / (distP1P2+0.0));
        double tempY = Math.sqrt( (arc[6]*arc[6]) - (((distP1P2+0.0)/2.0)*((distP1P2+0.0)/2.0)) ) * ((p2.getX() - p1.getX()) / (distP1P2+0.0));
        
        result1 = new Point2D.Double((p3.getX() + tempX), (p3.getY() + tempY));
        result2 = new Point2D.Double((p3.getX() - tempX), (p3.getY() - tempY));
        
        double distMCenter1 = Math.sqrt( ((result1.getX() - arc[2])*(result1.getX() - arc[2])) +  ( (result1.getY() - arc[3]) * (result1.getY() - arc[3])) );
        double distMCenter2 = Math.sqrt( ((result2.getX() - arc[2])*(result2.getX() - arc[2])) + ( (result2.getY() - arc[3]) * (result2.getY() - arc[3])) );
        
        if(distMCenter1 < distMCenter2) return result1;
        else return result2;
    }
    
    //co: center of the first circle
    //c1: center of the second circle
    //r0: radius of first circle
    //r1: radius of second circle
    private static Point2D[] determineIntersectionPoints(Point2D c0, Point2D c1, double r0, double r1) {
        Point2D[] result = new Point2D.Double[2];
        double d = c0.distance(c1); 
        
        double a = ((r0*r0) - (r1*r1) + (d*d)) / (2.0 * d) ;
        
        double dx = c1.getX() - c0.getX();
        double dy = c1.getY() - c0.getY();
        
        double x2 = c0.getX() + (dx * a/d);
        double y2 = c0.getY() + (dy * a/d);
        
        double h = Math.sqrt((r0*r0) - (a*a));
        
        double offSetx = -dy * (h/d);
        double offSety = dx * (h/d);
        
        result[0] = new Point2D.Double(x2 + offSetx,y2 + offSety);
        result[1] = new Point2D.Double(x2 - offSetx,y2 - offSety);
        
        return result;
    }

    private static boolean areIntersectedArcs(Double[] arc1, Double[] arc2, Point2D c1, Point2D c2, Point2D i1, Point2D i2, double r1, double r2) {
        
        double angc1_i1 = angle(c1.getX(), c1.getY(), i1.getX(), i1.getY());
        double angc1_i2 = angle(c1.getX(), c1.getY(), i2.getX(), i2.getY());
        
        double angc2_i1 = angle(c2.getX(), c2.getY(), i1.getX(), i1.getY());
        double angc2_i2 = angle(c2.getX(), c2.getY(), i2.getX(), i2.getY());
        
        double angc1_e1 = angle(c1.getX(), c1.getY(), arc1[0], arc1[1]);
        double angc1_e2 = angle(c1.getX(), c1.getY(), arc1[4], arc1[5]);
        
        double angc2_e1 = angle(c2.getX(), c2.getY(), arc2[0], arc2[1]);
        double angc2_e2 = angle(c2.getX(), c2.getY(), arc2[4], arc2[5]);
        
        boolean b1_arc1 = isBetween(angc1_i1, angc1_e1, angc1_e2);
        boolean b1_arc2 = isBetween(angc2_i1, angc2_e1, angc2_e2);
        
        if(b1_arc1 && b1_arc2) return true;
        
        boolean b2_arc1 = isBetween(angc1_i2,angc1_e1,angc1_e2);
        boolean b2_arc2 = isBetween(angc2_i2,angc2_e1,angc2_e2);
        
        if(b2_arc1 && b2_arc2) return true;
        
        return false;
    }
    
    private static double angle(double cx,double cy, double x2, double y2) {
        double dist = Math.sqrt( (x2-cx)*(x2-cx) + (y2-cy)*(y2-cy) );
        String quadrant = quadrant(cx,cy,x2,y2);
        double theta = Math.asin( Math.abs(y2-cy) / dist);
        
        if(quadrant.equals("first")) return Math.toDegrees(theta);
        else if(quadrant.equals("second")) return Math.toDegrees( Math.PI-theta);
        else if(quadrant.equals("third")) return Math.toDegrees(Math.PI+theta);
        else if(quadrant.equals("fourth")) return Math.toDegrees((2*Math.PI) - theta);
            
        return -1;
    }
    
    private static String quadrant(double cx,double cy, double x2, double y2) {
        if(x2>=cx && y2>=cy) return "first";
        else if(x2 < cx && y2>=cy) return "second";
        else if(x2 < cx && y2<cy) return "third";
        else if(x2 >= cx && y2<cy) return "fourth";
        
        return "error";
    }

    private static boolean isBetween(double angc1_i1, double angc1_e1,  double angc1_e2) {
        Set<Double> temp = new TreeSet<Double>();
        temp.add(angc1_e1);
        temp.add(angc1_e2);
        
        Iterator<Double> it = temp.iterator();
        if( (it.next() < angc1_i1) && (angc1_i1 < it.next()) ) return true;
        
        return false;
    }

    private static void addArc(double[] ordinates, Double radius) {
        Double[] temp = new Double[7];
        temp[0] = ordinates[0];
        temp[1] = ordinates[1];
        temp[2] = ordinates[2];
        temp[3] = ordinates[3];
        temp[4] = ordinates[4];
        temp[5] = ordinates[5];
        temp[6] = radius;
        
        allArcs.add(temp);
    }

    private static int numberIntersections(String side, Triple<Double, Double, Double> first, Triple<Double, Double, Double> last, double slope, double radius, double segmentMidX, double segmentMidY, double ang, double h) {
        Double[] arc = new Double[7];
        int result = 0;
        
        //Initiate the arc        
        arc[0] = first.getFirst();
        arc[1] = first.getSecond();
        arc[4] = last.getFirst();
        arc[5] = last.getSecond();
        arc[6] = radius;
        
        Triple<Double, Double, Double> middlePoint = new Triple<Double, Double, Double>();
        if(side.equals("left")) {
            if(slope > 0) slopePosLeft(middlePoint,segmentMidX, segmentMidY, ang, h);
            else slopeNegLeft(middlePoint,segmentMidX, segmentMidY, ang, h);            
        }
        
        if(side.equals("right")) {
            if(slope > 0) slopePosRight(middlePoint,segmentMidX, segmentMidY, ang, h);
            else slopeNegRight(middlePoint,segmentMidX, segmentMidY, ang, h);            
        }
        
        arc[2] = middlePoint.getFirst();
        arc[3] = middlePoint.getSecond();
        
        for(Double[] arcPresent: allArcs) {
            if(intersect(arcPresent, arc))
                result++;
        }
        
        return result;
    }
}
