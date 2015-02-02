package solap.utils;

import java.text.NumberFormat;

import java.util.Vector;

public class LegendUtils {

    private static final double LOG10SCALE = 1/Math.log(10);
    
    private static NumberFormat numberFormat = null;
    
    public static Vector computeNiceNumbers(double ticMinVal, double ticMaxVal, int maxTicks)  {
        double xStep = roundUp((ticMaxVal-ticMinVal)/(maxTicks+0.0));
        int numfracdigits = numFracDigits(xStep);

        // Compute x starting point so it is a multiple of xStep.
        double xStart = xStep*Math.ceil(ticMinVal/xStep);
        Vector xgrid = null;
        Vector labels = new Vector();
                
        for(int i = 0; i <= maxTicks; i++) {
            labels.addElement(formatNum(xStart, numfracdigits));
            xStart += xStep;
        }
            
        return labels;
    }
        
    private static double roundUp(double val) {
        int exponent = (int) Math.floor(log10(val));
        val *= Math.pow(10, -exponent);
        if (val > 5.0) val = 10.0;
        else if (val > 2.0) val = 5.0;
        else if (val > 1.0) val = 2.0;
        val *= Math.pow(10, exponent);
        return val;
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

}
