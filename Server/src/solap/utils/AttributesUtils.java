package solap.utils;

import solap.params.SOLAPAttributeParams;

public class AttributesUtils {
    
    MappingUtils mapUtils;
    
    public AttributesUtils(MappingUtils mapUtils) {
        this.mapUtils = mapUtils;
    }
    
    //function for comparing attributes and levels
    //returns:
    //0 same level
    //1 lower
    //2 higher
    //3 different hierarchy
    //4 different dimension
    public int getType (SOLAPAttributeParams main, SOLAPAttributeParams toCompare) {
       
        //check if same dimension
        if (main.getDimensionId().compareTo(toCompare.getDimensionId()) != 0) {
            return 4;
        }
        //check if same level
        if (main.getLevelId().compareTo(toCompare.getLevelId()) == 0) {
            return 0;
        }
        //check if lower
        if (mapUtils.lowerOrEqual(toCompare.getLevelId(), main.getLevelId())) {
            return 1;
        }
        //check if higher
        if (mapUtils.lowerOrEqual(main.getLevelId(), toCompare.getLevelId())) {
            return 2;
        }
        //else, can't be compared and same dimension -> different hierarchy        
        return 3;
    }
}
