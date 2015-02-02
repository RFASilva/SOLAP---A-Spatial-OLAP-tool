package solap.entities;

import java.io.Serializable;

public class SumarizationManager implements Serializable  {
    private String generalizationType;
    private String refinementHierarchies;
    private String useConceptsFrom;
    private String defineLabels;
    private String distinct;
    private String charaterizationByMeasures;
    private String zoomLevel;
    
 //   private boolean refinementHierarchiesSelected;
    private boolean nonSpatial;
    
    //temporary variables
    private String tempGeneralizationType;
    private String tempRefinementHierarchies;
    private String tempUseConceptsFrom;
    private String tempDefineLabels;
    private String tempDistinct;
    private String tempCharaterizationByMeasures;
    
    private int groupsParameter;
    
    public SumarizationManager() {
        generalizationType="SPATIAL";
        tempGeneralizationType="SPATIAL";
        refinementHierarchies="No";
        tempRefinementHierarchies="No";
        useConceptsFrom="All";
        tempUseConceptsFrom="All";
        defineLabels="No";
        tempDefineLabels="No";
        distinct="No";
        tempDistinct="No";
        charaterizationByMeasures = "No";
        tempCharaterizationByMeasures="No";
        //refinementHierarchiesSelected = refinementHierarchies.equals("Yes");
        nonSpatial = !generalizationType.equals("SPATIAL");
        groupsParameter = 0;
    }
    
    public void saveConfigs(){
        tempGeneralizationType = generalizationType;
        tempRefinementHierarchies = refinementHierarchies;
        tempUseConceptsFrom = useConceptsFrom;
        tempDefineLabels = defineLabels;
        tempDistinct = distinct;
        tempCharaterizationByMeasures = charaterizationByMeasures;
        System.out.println(generalizationType);
        System.out.println(refinementHierarchies);
        System.out.println(useConceptsFrom);
        System.out.println(defineLabels);
        System.out.println(distinct);
        System.out.println(charaterizationByMeasures);
        System.out.println(groupsParameter);
    }
    
    public void oldConfigs(){
        generalizationType = tempGeneralizationType;
        refinementHierarchies = tempRefinementHierarchies;
        useConceptsFrom = tempUseConceptsFrom;
        defineLabels = tempDefineLabels;
        distinct = tempDistinct;
        charaterizationByMeasures = tempCharaterizationByMeasures;
        System.out.println(generalizationType);
        System.out.println(refinementHierarchies);
        System.out.println(useConceptsFrom);
        System.out.println(defineLabels);
        System.out.println(distinct);
        System.out.println(charaterizationByMeasures);
        System.out.println(groupsParameter);
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

    public void setTempGeneralizationType(String tempGeneralizationType) {
        this.tempGeneralizationType = tempGeneralizationType;
    }

    public String getTempGeneralizationType() {
        return tempGeneralizationType;
    }

    public void setTempRefinementHierarchies(String tempRefinementHierarchies) {
        this.tempRefinementHierarchies = tempRefinementHierarchies;
    }

    public String getTempRefinementHierarchies() {
        return tempRefinementHierarchies;
    }

    public void setTempUseConceptsFrom(String tempUseConceptsFrom) {
        this.tempUseConceptsFrom = tempUseConceptsFrom;
    }

    public String getTempUseConceptsFrom() {
        return tempUseConceptsFrom;
    }

    public void setTempDefineLabels(String tempDefineLabels) {
        this.tempDefineLabels = tempDefineLabels;
    }

    public String getTempDefineLabels() {
        return tempDefineLabels;
    }
    
    public String generateOptionsXML(){
        String request = 
            "<generalize type=\"" + generalizationType + "\" refine=\"" + refinementHierarchies + "\"" +
            " from=\"" + useConceptsFrom + "\" defineLabels=\"" + defineLabels + "\"" +
            " charMeasures=\"" + charaterizationByMeasures +"\" distinct=\"" + distinct + "\"" +
            " zoomLevel=\"" + zoomLevel + "\"";
        if(isNonSpatial())
            request +=" groupsParam=\"" + groupsParameter + "\"";
        request += ">\n";
        return request;
    }

   // public void setRefinementHierarchiesSelected(boolean refinementHierarchiesSelected) {
   //     this.refinementHierarchiesSelected = refinementHierarchiesSelected;
    //}

    //public boolean isRefinementHierarchiesSelected() {
      //  refinementHierarchiesSelected = refinementHierarchies.equals("Yes");
       // return refinementHierarchiesSelected;
    //}

    public String getCharaterizationByMeasures() {
        return charaterizationByMeasures;
    }

    public void setCharaterizationByMeasures(String charaterizationByMeasures) {
        this.charaterizationByMeasures = charaterizationByMeasures;
    }

    public void setTempCharaterizationByMeasures(String tempCharaterizationByMeasures) {
        this.tempCharaterizationByMeasures = tempCharaterizationByMeasures;
    }

    public String getTempCharaterizationByMeasures() {
        return tempCharaterizationByMeasures;
    }

    public void setZoomLevel(String zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public String getZoomLevel() {
        return zoomLevel;
    }

    public void setNonSpatial(boolean nonSpatial) {
        this.nonSpatial = nonSpatial;
    }

    public boolean isNonSpatial() {
        nonSpatial = !generalizationType.equals("SPATIAL");
        return nonSpatial;
    }

    public void setDistinct(String distinct) {
        this.distinct = distinct;
    }

    public String getDistinct() {
        return distinct;
    }

    public void setTempDistinct(String tempDistinct) {
        this.tempDistinct = tempDistinct;
    }

    public String getTempDistinct() {
        return tempDistinct;
    }

    public void setGroupsParameter(int groupsParameter) {
        this.groupsParameter = groupsParameter;
    }

    public int getGroupsParameter() {
        return groupsParameter;
    }
}
