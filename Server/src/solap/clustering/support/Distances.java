package solap.clustering.support;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Distances {
    
    //Compute euclidian distances between two points normalizing the points
    public static double computeEuclidianDistance(Point2D point1, Point2D point2) {
        double dist = 0.0;
        
        double cXDistance = point1.getX() - point2.getX();
        dist += Math.pow(cXDistance, 2.0);
        
        double cYDistance = point1.getY() - point2.getY();
        dist += Math.pow(cYDistance, 2.0);
        
        
        return Math.sqrt(dist);
    }
    
    //Compute euclidian distances between two points 
    public static double computeEuclidianDistance(Point2D point1, Point2D point2, double[] mins, double[] maxs) {
        double dist = 0.0;
        
        double cXDistance = computeDistance(0, point1.getX(), point2.getX(), mins, maxs);
        dist += Math.pow(cXDistance, 2.0);
        
        double cYDistance = computeDistance(1, point1.getY(), point2.getY(), mins, maxs);
        dist += Math.pow(cYDistance, 2.0);
    
        return Math.sqrt(dist);
    }
    
    private static double computeDistance(int index, double v, double v1, double[] mins, double[] maxs) {
      return norm(v, index, mins, maxs) - norm(v1, index, mins, maxs);
    }

    private static double norm(double x, int i, double[] mins, double[] maxs) {
        return (x - mins[i]) / (maxs[i] - mins[i]);
    }
    
    //The algorithm used to compute the distance was proposed by M. J. Atallah
    //The complexity of the algorithm is O(n+m) where n and m is the number of vertexs
    //of each polygon
    public static double computeHausdorffDistance(Polygon polygonA, Polygon polygonB, double[] mins, double[] maxs)  {
        double result = 0.0;
        System.out.println("     INICIA ALGORITMO");
        //From a1, find the closest point b1 and compute
        Point2D.Double a1 = polygonA.getPoint(0);
        Point2D minBi = null;
        
        //To used further in next cycly
        int indexPolygonBi1 = 0;
        
        double minEucliDist = Double.MAX_VALUE;
        Point2D.Double b1 = polygonB.getPoint(0);
        Point2D.Double b2 = null;
        for(int i = 0; i < polygonB.getNumberPoints()-1; i++) {
            b2 = polygonB.getPoint(i+1);
            
            Point2D bi = checkForClosePoint(a1, b1, b2, polygonB, mins, maxs);
            
            if(bi != null) {
                double euclidDist = computeEuclidianDistance(a1, bi, mins, maxs);
                if(euclidDist < minEucliDist) {
                    //System.out.println("Dentro do if: " + bi.toString());
                    minEucliDist = euclidDist;
                    minBi = bi;
                    indexPolygonBi1 = i;
                }
            }
            
            b1 = b2;
        }
        
        //Assign previous distance to the result
        result = minEucliDist;
        
        //System.out.println("Distance: " + result);
        Point2D.Double ai = null;
        Point2D.Double bi = null;
        Point2D.Double ai1 = null;
        Point2D bi1 = null;
        
        System.out.println("     COMPLETA FASE 1");        
        for(int i = 0; i < polygonA.getNumberPoints()-1; i++) {
            ai1 = polygonA.getPoint(i+1);
            ai = polygonA.getPoint(i);
            bi = polygonB.getPoint(indexPolygonBi1);
            
            int pos = findPosition(ai1, ai, bi);

            //Left
            if(pos == 1) {    
                bi = polygonB.getPoint(indexPolygonBi1);
                
                   
                if(bi1 != null) {
                    bi1 = checkForClosePoint(ai1, bi, new Point2D.Double(bi1.getX(), bi1.getY()) , polygonB, mins, maxs);
                }                   
                if(bi1 == null) {   
                    do {         
                        //scannnig B counterclockwise
                        if(indexPolygonBi1 + 1 == polygonB.getNumberPoints())
                            indexPolygonBi1 = 1;
                        else indexPolygonBi1++;
                        
                        bi1 = polygonB.getPoint(indexPolygonBi1);  
                        Point2D temp = new Point2D.Double( bi1.getX(), bi1.getY());
                    
                        bi1 = checkForClosePoint(ai1, bi, new Point2D.Double(bi1.getX(), bi1.getY()) , polygonB, mins, maxs);
                        
                        bi = new Point2D.Double(temp.getX(), temp.getY());
                        
                    } while (bi1 == null);    
                }
                
            }
            
            //right
            else if(pos == 2) {
                bi = polygonB.getPoint(indexPolygonBi1);
                       
                    do {
                        if(indexPolygonBi1 - 1 == -1)
                            indexPolygonBi1 = polygonB.getNumberPoints()-2;
                        else indexPolygonBi1--;
                       
                        bi1 = polygonB.getPoint(indexPolygonBi1);
                        Point2D temp = new Point2D.Double( bi1.getX(), bi1.getY());
            
                        bi1 = checkForClosePoint(ai1, bi, new Point2D.Double(bi1.getX(), bi1.getY()) , polygonB, mins, maxs);
                        bi = new Point2D.Double( temp.getX(), temp.getY());
                        
                    } while (bi1 == null);
                    
            }
            
            //Elsewhere
            else bi1 = bi;
            
            double di1 = computeEuclidianDistance(ai1, bi1, mins, maxs);
            result = Math.max(result, di1);
        }
        
        return result;
    }

    //Return 1 if the point is to left of the line
    //Return 2 if the point is to right of the line
    //Return -1 elsewhere
    private static int findPosition(Point2D.Double b, Point2D.Double ai, Point2D.Double bi) {
        double x0 = b.getX();
        double y0 = b.getY();
        
        double x1 = ai.getX();
        double x2 = bi.getX();
        
        double y1 = ai.getY();
        double y2 = bi.getY();
        
        double m = (x2-x1)*(y2-y1);
        double p1 = (x0-x1)*(y0-y1);
        double p2 = (x2-x0)*(y2-y0);
        
        double p3 = (x2-x0)*(y0-y1)*2;
        
        //The point is left
        if (m<p1+p2+p3) return 1;
        //The point is right
        else if(m>p1+p2+p3) return 2;
        else return -1;
    }
    
    //Function z =  (a, b1, b2) : 
    private static Point2D checkForClosePoint(Point2D.Double a, Point2D.Double b1, Point2D.Double b2, Polygon polygon2, double[] mins, double[] maxs) {
        double deltaX = (b2.getX() - b1.getX());
        double deltaY = (b2.getY() - b1.getY());
        
        //slope of the perpendicular line
        double mPerpendicular = (-1*deltaX)/(deltaY+0.0);
        
        //The slope intercept form of a line is y=mx + b;
        //Thus, we need to find the b = y-mx;
        
        double b = a.getY() - (mPerpendicular*a.getX());
        
        Line throughAPerp = new Line(mPerpendicular, b);
        Line2D.Double lineb1b2 = new Line2D.Double(b1,b2);
        
        Point2D z = throughAPerp.intersectionPoint(lineb1b2);
        
        //if z is between b1  b2 then return z 
        if(z != null) {
            return z;
        }
        
        //Compute at b2 a line P perpendicular to the line ab2
        double tempSlopePerp = (-1*(b2.getX() - a.getX()) / (b2.getY() - a.getY()));
        double tempB = b2.getY() - (tempSlopePerp*b2.getX());
        
        Line p = new Line(tempSlopePerp, tempB);
    
        //if P is a supporting line of B then return b2
        if(isSupportingLine(b2, p, polygon2, mins, maxs)) {
            return b2;
        }
        else {
            return null;
        }
    }

    private static boolean isSupportingLine(Point2D.Double b2, Line p, Polygon polygon2, double[] mins, double[] maxs) {
        int side = -2;
        boolean first = true;
        
        double ymin = p.y(mins[0]);
        double ymax = p.y(maxs[0]);
        
        Point2D.Double lowerBound = new Point2D.Double (mins[0], ymin);
        Point2D.Double upperBound = new Point2D.Double (maxs[0], ymax);
                                                
        for(int i = 0; i < polygon2.getNumberPoints(); i++) {
            Point2D.Double point = polygon2.getPoint(i);
            
            if((b2.getX() == point.getX()) && (b2.getY() == point.getY()))
                continue;
            
            if(first) {
                side = findPosition(point, new Point2D.Double (mins[0], ymin), new Point2D.Double (maxs[0], ymax));
                first = false;
                continue;
            }
            
            if(side != findPosition(point, lowerBound, upperBound)) {
                return false;
            }
        }
        
        return true;
    }
    
    public static double computeBoundaryAdjustedHausdorffDistance(Polygon polygonA, Polygon polygonB, double[] mins, double[] maxs) {
        //Perimeter of polygon A
        
        System.out.println("BEGIN PERIMETROS: " + +polygonA.getNumberPoints());
        double sp = perimeter(polygonA);     
        
        //Perimeter of polygon B
        double sq = perimeter(polygonB);
        System.out.println("END PERIMETROS: " + polygonB.getNumberPoints());     
        
        //Shared Boundary between polygons
        System.out.println("BEGIN PARTILHA FRONTEIRA");
        double spq =  shareBoundary(polygonA, polygonB);
        System.out.println("END PARTILHA FRONTEIRA: " + spq);
        
        System.out.println("BEGIN HAUSDORFF");
        double hausdorff = computeHausdorffDistance(polygonA, polygonB, mins, maxs);
        System.out.println("END HAUSDORFF");
        
        double boundaryFactor = (1-((2*spq)/(sp+sq)));
        
        return boundaryFactor * hausdorff;
    }
                   
    private static double perimeter(Polygon polygon) {
       double result = 0.0;
       
       Point2D p1 = polygon.getPoint(0);
       Point2D p2 = null;
       for(int i = 0; i < polygon.getNumberPoints() - 1; i++) {
           p2 = polygon.getPoint(i+1);
           result += computeEuclidianDistance(p1,p2);
       }
       
       return result;
    }
    
    public static double shareBoundary(Polygon a, Polygon b) {
        double result = 0.0;
        
        Point2D a1 = a.getPoint(0);
        Point2D a2 = null; 
        for(int i = 0; i < a.getNumberPoints() - 1; i++) {
            //System.out.print(i + ", ");
            a2 = a.getPoint(i + 1);
            Point2D[] edge = new Point2D[2];
            edge[0] = a1;
            edge[1] = a2;
            
            boolean isShared = false;
            Point2D b1 = b.getPoint(0);
            Point2D b2 = null; 
            for(int j = 0; j < b.getNumberPoints()-1; j++) {
                b2 = b.getPoint(j + 1);
                if( equalEdge(edge[0], edge[1], b1, b2) || equalEdge(b1,b2,edge[0],edge[1])) {
                    isShared = true;
                    break;
                }
            }
            
            if(isShared) {
                result += computeEuclidianDistance(edge[0], edge[1]);
                isShared = false;
            }
            
            a1 = a2;
        }
        
        return result;
    }
    
    private static boolean equalEdge(Point2D a1, Point2D a2, Point2D b1, Point2D b2) {
        return (a1.getX() == b2.getX()) && (a1.getY() == b2.getY()) && (a2.getX() == b1.getX()) && (a2.getY() == b1.getY());
    }
    
}
