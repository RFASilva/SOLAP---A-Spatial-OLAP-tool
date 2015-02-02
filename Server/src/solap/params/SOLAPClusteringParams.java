package solap.params;

import java.util.HashMap;
import java.util.Map;

import solap.clustering.support.InputParams;

import solap.utils.ITriple;
import solap.utils.Triple;

public class SOLAPClusteringParams {
    
    private String mode;
    private String groups;
    private String geometry;
    private String variant;
    private String zoom;
    private Map<String, InputParams> params;
    private Map<String, ITriple<String, String, String>> paramsBasedSpatial;
    
    //In case of pre computed distances
    private String tableId;
    private String fromId;
    private String toId;
    private String distancesValuesId;
    
    
    public SOLAPClusteringParams() {
        this.params = new HashMap<String, InputParams>();
        this.paramsBasedSpatial = new HashMap<String, ITriple<String, String, String>>();
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getVariant() {
        return variant;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public String getZoom() {
        return zoom;
    }

    public void addSpatialParams(String spatialLevel, InputParams params) {
        this.params.put(spatialLevel, params);
    }
    
    public void addSpatialBasedSpatialParams(String spatialLevel, String dimensionId, String levelId) {
        this.paramsBasedSpatial.put(spatialLevel, new Triple<String, String, String> (dimensionId, levelId, ""));
    }
    
    public InputParams getParamsBySpatialLevel(String spatialLevel) {
        return params.get(spatialLevel);
    }
    
    public ITriple<String, String, String> getInfoBySpatialLevel(String name) {
        return paramsBasedSpatial.get(name);
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getGroups() {
        return groups;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getToId() {
        return toId;
    }

    public void setDistancesValuesId(String distancesValuesId) {
        this.distancesValuesId = distancesValuesId;
    }

    public String getDistancesValuesId() {
        return distancesValuesId;
    }
}
