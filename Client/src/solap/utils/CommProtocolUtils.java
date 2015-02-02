package solap.utils;

public class CommProtocolUtils {
    
    public static String buildMeasure(String id, String operator) {
        return "<measure id=\"" + id + "\" operator=\"" + operator + "\" />";
    }
    
    public static String buildAttribute(String dimensionId, String levelId, String attributeId) {
        return "<attribute dimensionId=\"" + dimensionId + "\" levelId=\"" + levelId + "\" id=\"" + attributeId + "\" />";
    }
    
    public static String buildLevel(String dimensionId, String levelId) {
        return "<level dimensionId=\"" + dimensionId + "\" id=\"" + levelId + "\" />";
    }
    
    public static String buildSlice(String dimensionId, String levelId, String attributeId, String value) {
        return "<slice dimensionId=\"" + dimensionId + "\" levelId=\"" + levelId + "\" attributeId=\"" + attributeId + "\" operator=\"EQUAL\" value1=\"" + value + "\" />";
    }
    
    public static String buildDetailGroup(String dimensionId, String levelId, boolean group, String value, boolean detail, int counterHigher) {
        if(group)
            return "<detailGroup dimensionId=\"" + dimensionId + "\" levelId=\"" + levelId + "\" groupId=\"" + value + "\" detail=\"" + detail + "\" higherAttrST=\"" + counterHigher + "\" />";
        else return "<detailGroup value=\"" + value + "\" />";
    }
    
    public static String buildSpatialSlice(String dimensionId, String levelId, String attributeId, String layerId, String operator, String value, String unit) {
        String temp = "<spatialSlice dimensionId=\"" + dimensionId + "\" levelId=\"" + levelId + "\" attributeId=\"" + attributeId + "\" layerId=\"" + layerId + "\" operator=\"" + operator + "\"";
        if (unit.compareTo("") != 0)
            temp += " unit=\"" + unit + "\"";
            
        if (value.compareTo("") != 0)
            temp += " value=\"" + value + "\"";
        
        temp += " />";
        
        return temp;
    }
    
    public static String buildFieldFilter(String measureId, String operator, String value) {
        return "<fieldFilter measureId=\"" + measureId + "\" operator=\"" + operator + "\" value1=\"" + value + "\" />";
    }
}
