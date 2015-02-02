package solap.utils;

import java.text.Normalizer;

import java.util.Vector;

import solap.entities.InterfaceFilter;
import solap.entities.InterfaceSlice;
import solap.entities.InterfaceSpatialSlice;

public class EditUtils {
    public static InterfaceSlice getInterfaceSlice(Vector<InterfaceSlice> vector, String name) {
        for (int i = 0; i < vector.size(); i++) {
            if (vector.elementAt(i).getFilterName().equals(name))
                return vector.elementAt(i);
        }
        return null;
    }
    
    public static InterfaceSpatialSlice getInterfaceSpatialSlice(Vector<InterfaceSpatialSlice> vector, String name) {
        for (int i = 0; i < vector.size(); i++) {
            if (vector.elementAt(i).getFilterName().compareTo(name) == 0)
                return vector.elementAt(i);
        }
        return null;
    }
    
    public static InterfaceFilter getInterfaceFilter(Vector<InterfaceFilter> vector, String name) {
        for (int i = 0; i < vector.size(); i++) {
            if (vector.elementAt(i).getFilterName().compareTo(name) == 0)
                return vector.elementAt(i);
        }
        return null;
    }
    
    public static String cleanString(String string) {
        String text = Normalizer.normalize(string, Normalizer.Form.NFD);
        text = text.replaceAll("[^\\p{L}\\p{N}]", "");
        
        return text;
    }
}
