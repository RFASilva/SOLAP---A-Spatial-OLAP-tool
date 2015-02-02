package solap.params;

import java.util.Vector;

import solap.entities.SOLAPFactTable;
import solap.entities.SumarizationAttribute;
import solap.entities.SumarizationMeasure;

public class SOLAPParamsObject {
    
    //main parameters
    private String call;
    private String cubeId;
    private String filename;
    private boolean spatial;
    
    //extra parameters
    private Vector<SOLAPDistinctsParams> distinctParams = new Vector<SOLAPDistinctsParams>();
    private Vector<SOLAPSliceParams> sliceParams = new Vector<SOLAPSliceParams>();
    private Vector<SOLAPMeasureParams> measureParams = new Vector<SOLAPMeasureParams>();
    private Vector<SOLAPLevelParams> levelParams;
    private Vector<SOLAPAttributeParams> attributeParams = new Vector<SOLAPAttributeParams>();
    private Vector<SOLAPFieldFilterParams> fieldFilterParams = new Vector<SOLAPFieldFilterParams>();
    private Vector<SOLAPMeasureFiltersParams> measureFilterParams = new Vector<SOLAPMeasureFiltersParams>();
    private Vector<SOLAPNFiltersParams> nFilterParams = new Vector<SOLAPNFiltersParams>();
    private Vector<SOLAPSpatialSliceParams> spatialSliceParams = new Vector<SOLAPSpatialSliceParams>();
    private Vector<SOLAPGeometricSlicesParams> geometricSliceParams = new Vector<SOLAPGeometricSlicesParams>();
    //Only is present one definition for clustering in a request
    private SOLAPClusteringParams clusteringParams;
    
    private Vector<SOLAPDetailGroupParams> groupDetailParams = new Vector<SOLAPDetailGroupParams>();
    
    //Generalization parameters
    private SOLAPGeneralizationParams generalizationParams;
    private Vector<SumarizationAttribute> attributesToGeneralize;
    private Vector<SumarizationMeasure> measuresToGeneralize;
    private Vector<SOLAPFactTable> factTables;
    
    public SOLAPParamsObject() {
        levelParams = new Vector<SOLAPLevelParams>();
    }
    
    public void addToDistincts(SOLAPDistinctsParams input) {
        distinctParams.add(input);
    }
    
    public void addToSlices(SOLAPSliceParams input) {
        sliceParams.add(input);
    }
    
    public void addToMeasures(SOLAPMeasureParams input) {
        measureParams.add(input);
    }
    
    public void addToLevels(SOLAPLevelParams input) {
        levelParams.add(input);
    }
    
    public void addToAttributes(SOLAPAttributeParams input) {
        attributeParams.add(input);
    }
    
    public void addToFieldFilters(SOLAPFieldFilterParams input) {
        fieldFilterParams.add(input);
    }
    
    public void addToMeasureFilters(SOLAPMeasureFiltersParams input) {
        measureFilterParams.add(input);
    }
    
    public void addToNFilters(SOLAPNFiltersParams input) {
        nFilterParams.add(input);
    }
    
    public void addToSpatialSlices(SOLAPSpatialSliceParams input) {
        spatialSliceParams.add(input);
    }
    
    public void addToGeometricSlices(SOLAPGeometricSlicesParams input) {
        geometricSliceParams.add(input);
    }
    
    public void addToDetailGroups(SOLAPDetailGroupParams input) {
        groupDetailParams.add(input);
    }

    public Vector<SOLAPLevelParams> getLevelParams() {
        return levelParams;
    }

    public Vector<SOLAPDistinctsParams> getDistinctParams() {
        return distinctParams;
    }

    public Vector<SOLAPSliceParams> getSliceParams() {
        return sliceParams;
    }

    public Vector<SOLAPMeasureParams> getMeasureParams() {
        return measureParams;
    }

    public Vector<SOLAPAttributeParams> getAttributeParams() {
        return attributeParams;
    }

    public Vector<SOLAPFieldFilterParams> getFieldFilterParams() {
        return fieldFilterParams;
    }

    public Vector<SOLAPMeasureFiltersParams> getMeasureFilterParams() {
        return measureFilterParams;
    }

    public Vector<SOLAPNFiltersParams> getNFilterParams() {
        return nFilterParams;
    }

    public Vector<SOLAPSpatialSliceParams> getSpatialSliceParams() {
        return spatialSliceParams;
    }

    public Vector<SOLAPGeometricSlicesParams> getGeometricSliceParams() {
        return geometricSliceParams;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getCall() {
        return call;
    }

    public void setCubeId(String cubeId) {
        this.cubeId = cubeId;
    }

    public String getCubeId() {
        return cubeId;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setSpatial(boolean spatial) {
        this.spatial = spatial;
    }

    public boolean isSpatial() {
        return spatial;
    }

    public void setClusteringParams(SOLAPClusteringParams clusteringParams) {
        this.clusteringParams = clusteringParams;
    }

    public SOLAPClusteringParams getClusteringParams() {
        return clusteringParams;
    }

    public Vector<SOLAPDetailGroupParams> getGroupDetailParams() {
        return groupDetailParams;
    }

    public void setGeneralizationParams(SOLAPGeneralizationParams generalizationParams) {
        this.generalizationParams = generalizationParams;
    }

    public SOLAPGeneralizationParams getGeneralizationParams() {
        return generalizationParams;
    }

    public void setAttributesToGeneralize(Vector<SumarizationAttribute> attributesToGeneralize) {
        this.attributesToGeneralize = attributesToGeneralize;
    }

    public Vector<SumarizationAttribute> getAttributesToGeneralize() {
        return attributesToGeneralize;
    }

    public void setMeasuresToGeneralize(Vector<SumarizationMeasure> measuresToGeneralize) {
        this.measuresToGeneralize = measuresToGeneralize;
    }

    public Vector<SumarizationMeasure> getMeasuresToGeneralize() {
        return measuresToGeneralize;
    }

    public void setFactTables(Vector<SOLAPFactTable> factTables) {
        this.factTables = factTables;
    }

    public Vector<SOLAPFactTable> getFactTables() {
        return factTables;
    }
    
    public void printSummarizationInfo(){
        for(SumarizationAttribute a: attributesToGeneralize)
            a.printInfo();
        for(SumarizationMeasure m: measuresToGeneralize)
            m.printInfo();
        for(SOLAPFactTable f: factTables)
            f.printInfo();
    }


}
