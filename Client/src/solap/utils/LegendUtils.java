package solap.utils;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

public class LegendUtils {

    private static final double LOG10SCALE = (1.0/Math.log(10));
    private static NumberFormat numberFormat = null;
    
    public static ITriple<Double, Double, Double> computeNiceIncrement(double minVal, double maxVal, int numTicks)  {
        ITriple<Double, Double, Double> result = new Triple<Double, Double, Double>();
        
        int numberClasses = numTicks;
        double divisor = Math.pow(2,numberClasses);
        double range = nicenum((maxVal-minVal),false);
        double number = ((range+0.0)/(divisor+0.0));
        double increment = nicenum(number, true);
        
        double temp = 0.0;
        double tempIterations = range / increment;
        double numberIterations = Math.floor(tempIterations/numberClasses);
        
        for(int i = 0; i < numberIterations; i++) {
            temp += increment;
        }
       
        double minLabel = Math.floor(minVal/increment)*increment;
        double maxLabel = Math.ceil(maxVal/increment)*increment;
        
        result.setFirst(minLabel);
        result.setSecond(temp);
        result.setThird(maxLabel);
        
        return result;
    }
        
    public static double nicenum(double val, boolean round) {
        int exponent = (int) Math.floor(log10(val));
        double f = ((val + 0.0) / Math.pow(10, exponent));
        double nf = 0.0;
        
        if(round) {
            if (f < 1.5)
                nf = 1.0;
            else if (f < 3) 
                nf = 2.0;
            else if (f < 7) 
                nf = 5.0;
            else nf = 10;
        }
        else {
            if (f <= 1) 
                nf = 1.0;
            else if (f <= 2.0)
                nf = 2.0;
            else if (f <= 5) 
                nf = 5.0;
            else nf = 10;
        }
        
        return nf*Math.pow(10, exponent);
    }

    private static String formatNum(double num, int numfracdigits) {
         if (numberFormat == null) {
             numberFormat = NumberFormat.getInstance();
             numberFormat.setGroupingUsed(false);
         }
         numberFormat.setMinimumFractionDigits(numfracdigits);
         numberFormat.setMaximumFractionDigits(numfracdigits);
         return numberFormat.format(num);
     }
    
    private static int numFracDigits(double num) {
        int numdigits = 0;
        while (numdigits <= 15 && num != Math.floor(num)) {
            num *= 10.0;
            numdigits += 1;
        }
        return numdigits;
    }
    
    // Auxiliary Methods
    private static double log10(double val) {
        return Math.log(val) * LOG10SCALE; 
    }
    
    private static double exp10(double val) {
        return Math.exp(val / LOG10SCALE); 
    }
    
    private static float flog10(double val) {
        return (float)log10(val); 
    }
    
    private static float fexp10(double val) {
        return (float)exp10(val); 
    }

    //Jenks Natural Breaks Classification method
    //This method compute the best arrangement of values into different classes
    public static int[] getNaturalBreaks(ArrayList<Double> list, int numclass) {
            
        //int numclass;
        int numdata = list.size();
        
        System.out.println("NATURAL BREAKS: " + numdata + "  -----  " + numclass);
        //To solve a bug
        if(numdata < numclass)
            numclass = numdata;
        
        double[][] mat1 = new double[numdata + 1][numclass+ 1];
        double[][] mat2 = new double[numdata + 1][numclass+ 1];
        double[] st = new double[numdata];
        
        for (int i = 1; i <= numclass; i++) {
            mat1[1][i] = 1;
            mat2[1][i] = 0;
            for (int j = 2; j <= numdata; j++)
                mat2[j][i] = Double.MAX_VALUE;
        }
                
        double v = 0;
        for (int l = 2; l <= numdata; l++) {
            double s1 = 0;
            double s2 = 0;
            double w = 0;
            for (int m = 1; m <= l; m++) {
                int i3 = l - m + 1;

                double val = ((Double)list.get(i3-1)).doubleValue();
                
                s2 += val * val;
                s1 += val;
                        
                w++;
                v = s2 - (s1 * s1) / w;
                int i4 = i3 - 1;
                if (i4 != 0) {
                    for (int j = 2; j <= numclass; j++) {
                        if (mat2[l][j] >= (v + mat2[i4][j- 1])) {
                            mat1[l][j] = i3;
                            mat2[l][j] = v + mat2[i4][j -1];
                        };
                    };
                };
            };
                        
            mat1[l][1] = 1;
            mat2[l][1] = v;
        };
                
        int k = numdata;
        int[] kclass = new int[numclass];
       
        kclass[numclass - 1] = list.size() - 1;
         
        for (int j = numclass; j >= 2; j--) {
            System.out.println("rank = " + mat1[k][j]);
            int id =  (int) (mat1[k][j]) - 2;
            System.out.println("val = " + list.get(id));
            //System.out.println(mat2[k][j]);
             
            kclass[j - 2] = id;
                   
            k = (int) mat1[k][j] - 1; 
        };
                
        return kclass;
    }
    
}
