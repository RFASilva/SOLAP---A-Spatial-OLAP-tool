package solap.utils;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import pt.uminho.ubicomp.concaveHull.Point;

public class ConvexHull {
    
     public static Vector<Point> quickHull(Vector<Point> points) {
       Vector<Point> convexHull = new Vector<Point>();
       if (points.size() < 3) return (Vector<Point>)points.clone();
       
       // find extremals
       int minPoint = -1, maxPoint = -1;
       double minX = Double.MAX_VALUE;
       double maxX = -9999999;
       
       for (int i = 0; i < points.size(); i++) {
           if (points.get(i).getX() < minX) {
               minX = points.get(i).getX();
               minPoint = i;
           } 
       
           if (points.get(i).getX() > maxX) {
               maxX = points.get(i).getX();
               maxPoint = i;       
           }
       }
       
       Point A = points.get(minPoint);
       Point B = points.get(maxPoint);
       
       convexHull.add(A);
       convexHull.add(B);
       points.remove(A);
       points.remove(B);
       Vector<Point> leftSet = new Vector<Point>();
       Vector<Point> rightSet = new Vector<Point>();
       
       
       for (int i = 0; i < points.size(); i++) {
         Point p = points.elementAt(i);
           if (pointLocation(A,B,p) == -1) {
           leftSet.add(p);
           }
           else {
           rightSet.add(p);
           }
       }
       hullSet(A,B,rightSet,convexHull);
       hullSet(B,A,leftSet,convexHull);
       
       return convexHull;
     }
     
     /*
      * Computes the square of the distance of point C to the segment defined by points AB
      */
     private static double distance(Point A, Point B, Point C) {
       double ABx = B.getX()-A.getX();
       double ABy = B.getY()-A.getY();
       double num = ABx*(A.getY()-C.getY())-ABy*(A.getX()-C.getX());
       if (num < 0) num = -num;
       return num;
     }
     
     private static void hullSet(Point A, Point B, Vector<Point> set, Vector<Point> hull) {
       int insertPosition = hull.indexOf(B);
       if (set.size() == 0) return;
       if (set.size() == 1) {
         Point p = set.elementAt(0);
         set.remove(p);
         hull.add(insertPosition,p);
         return;
       }
       double dist = Double.MIN_VALUE;
       int furthestPoint = -1;
       for (int i = 0; i < set.size(); i++) {
         Point p = set.elementAt(i);
         double distance  = distance(A,B,p);
         if (distance > dist) {
           dist = distance;
           furthestPoint = i;
         }
       }
       Point P = set.elementAt(furthestPoint);
       set.remove(furthestPoint);
       hull.add(insertPosition,P);
       
       // Determine who's to the left of AP
       Vector<Point> leftSetAP = new Vector<Point>();
       for (int i = 0; i < set.size(); i++) {
         Point M = set.elementAt(i);
         if (pointLocation(A,P,M)==1) {
           leftSetAP.add(M);
         }
       }
       
       // Determine who's to the left of PB
       Vector<Point> leftSetPB = new Vector<Point>();
       for (int i = 0; i < set.size(); i++) {
         Point M = set.elementAt(i);
         if (pointLocation(P,B,M)==1) {
           leftSetPB.add(M);
         }
       }
       hullSet(A,P,leftSetAP,hull);
       hullSet(P,B,leftSetPB,hull);
       
     }

     private static double pointLocation(Point A, Point B, Point P) {
       double cp1 = (B.getX()-A.getX())*(P.getY()-A.getY()) - (B.getY()-A.getY())*(P.getX()-A.getX());
       return (cp1>0)?1:-1;
     }
   }
