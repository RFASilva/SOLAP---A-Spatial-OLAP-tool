package solap.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import solap.sumarization.hierarchies.NumericHierarchySegment;

public class SumarizationUtils {
    
    public static Vector<NumericHierarchySegment> getSegmentsMyAlgorithm(ArrayList<Double> list,  int numclass){
        Vector<NumericHierarchySegment> segments = new Vector<NumericHierarchySegment>();
        SortedSet<Double> values = new TreeSet<Double>();
        HashMap<Double,Integer> occurrences = new HashMap<Double,Integer>();
        for(double value : list){
            if(values.contains(value))
                occurrences.put(value,occurrences.get(value) +1);
            else{
                values.add(value);
                occurrences.put(value,1);
            }
        }
        
        int totalOccurrences = list.size();
        int maxOccurrences = totalOccurrences/numclass;
        double segMin=0;
        double segMax=0;
        int segTotal=0;
        boolean beginNewSegment=true;
        int count=0;
        for(double value : values){
            count++;
            if(beginNewSegment){
                segMin=value;
                segMax=value;
                beginNewSegment=false;
            }
            if(values.size() == count){
                segments.add(new NumericHierarchySegment(segMin,value));
                break;
            }
            segTotal += occurrences.get(value);
            if(segTotal > maxOccurrences && segMin != value){
                segments.add(new NumericHierarchySegment(segMin,value));
                segTotal = 0;
                beginNewSegment=true;
            }
            else
                segMax = value;
        }
        segments = computeOccurrences(list,segments);
        return segments;
    }
    
    public static Vector<NumericHierarchySegment> uniformSegments(ArrayList<Double> list, double max, double min, int numclass){
        Vector<NumericHierarchySegment> segments = new Vector<NumericHierarchySegment>();
        double intervalo = max-min/numclass;
        for(int i=0;i<numclass;i++){
            segments.add(new NumericHierarchySegment(min+intervalo*i, min+intervalo*(i+1)));
        }
        segments = computeOccurrences(list,segments);
        return segments;
    }
    
    public static Vector<NumericHierarchySegment> getSegments(ArrayList<Double> list, double max, double min, int numclass) {
        Vector<NumericHierarchySegment> segments = new Vector<NumericHierarchySegment>();
        
        Vector<NumericHierarchySegment> preSegments = buildSegments(list,max,min,numclass);
        double mean = list.size() / numclass;
        double total=0;
        double segMin=0;
        double segMax=0;
        for(NumericHierarchySegment s : preSegments){
            if(segMin == 0)
                segMin = s.getMin();
            total += s.getOccurrencesCount();
            segMax = s.getMax();
            if(total >= mean || segMax==max){
                segments.add(new NumericHierarchySegment(segMin,segMax));
                total=0;
                segMin=0;
            }
        }
        
        segments = computeOccurrences(list,segments);
        return segments;
    }
    
    private static Vector<NumericHierarchySegment> buildSegments(ArrayList<Double> list, double max, double min, int numclass){
        Vector<NumericHierarchySegment> segments = new Vector<NumericHierarchySegment>();
        final int K = 10;
        double intervalo = (max-min) / (numclass*K);
        System.out.println("Min: " + min + " Max: " + max + " Intervalo: " + intervalo);
        for(double i=min; i<=max; i=i+intervalo){
            double intervaloMin = i;
            double intervaloMax = i+intervalo;
            NumericHierarchySegment s = new NumericHierarchySegment(intervaloMin,intervaloMax);
            if(intervaloMax > max)
                intervaloMax = max;
            segments.add(s);
        }
        
        segments = computeOccurrences(list,segments);
        return segments;
    }
    
    private static Vector<NumericHierarchySegment> computeOccurrences(ArrayList<Double> list, Vector<NumericHierarchySegment> segments){
        for(int i=0; i < list.size(); i++) {
            Double value = list.get(i);
            for(NumericHierarchySegment s : segments)
                if(s.belongs(value))
                    s.incrementOccurrences();
        }
        return segments;
    }
}
