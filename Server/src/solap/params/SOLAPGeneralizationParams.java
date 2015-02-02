package solap.params;

public class SOLAPGeneralizationParams {
    
    private String generalizationType;
    private String refinementHierarchies;
    private String useConceptsFrom;
    private String defineLabels;
    private String distinct;
    private String charMeasures;
    private String zoomLevel;
    private String groupsParam;
    
    public SOLAPGeneralizationParams(String generalizationType, String refinementHierarchies, String useConceptsFrom, String defineLabels, String distinct, String charMeasures, String zoomLevel) {
        this.generalizationType = generalizationType;
        this.refinementHierarchies = refinementHierarchies;
        this.useConceptsFrom = useConceptsFrom;
        this.defineLabels = defineLabels;
        this.distinct = distinct;
        this.charMeasures = charMeasures;
        this.zoomLevel = zoomLevel;
    }

    public void setGeneralizationType(String generalizationType) {
        this.generalizationType = generalizationType;
    }

    public String getGeneralizationType() {
        return generalizationType;
    }

    public void setRefinementHierarchies(String refinementHierarchies) {
        this.refinementHierarchies = refinementHierarchies;
    }

    public String getRefinementHierarchies() {
        return refinementHierarchies;
    }

    public void setUseConceptsFrom(String useConceptsFrom) {
        this.useConceptsFrom = useConceptsFrom;
    }

    public String getUseConceptsFrom() {
        return useConceptsFrom;
    }

    public void setDefineLabels(String defineLabels) {
        this.defineLabels = defineLabels;
    }

    public String getDefineLabels() {
        return defineLabels;
    }

    public void setCharMeasures(String charMeasures) {
        this.charMeasures = charMeasures;
    }

    public String getCharMeasures() {
        return charMeasures;
    }

    public void setZoomLevel(String zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public String getZoomLevel() {
        return zoomLevel;
    }

    public void setDistinct(String distinct) {
        this.distinct = distinct;
    }

    public String getDistinct() {
        return distinct;
    }

    public void setGroupsParam(String groupsParam) {
        this.groupsParam = groupsParam;
    }

    public String getGroupsParam() {
        return groupsParam;
    }
}
