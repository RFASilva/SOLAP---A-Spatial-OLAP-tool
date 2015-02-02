package solap.sqlgenerator;

import java.util.Vector;

import solap.entities.SOLAPFactTable;
import solap.entities.SumarizationAttribute;

import solap.entities.SumarizationMeasure;

import solap.params.SOLAPAttributeParams;
import solap.params.SOLAPFieldFilterParams;
import solap.params.SOLAPLevelParams;
import solap.params.SOLAPMeasureParams;
import solap.params.SOLAPSliceParams;
import solap.params.SOLAPSpatialSliceParams;


public interface IQuery {

    public String buildSemanticQuery();
    
    public String buildSpatialQuery();
    
    public String buildDistinctQuery();
    
    public String buildSpatialQueryByIndex(int index, String levelId, String dimensionId, Vector<SOLAPAttributeParams> higherAttr);
    
    public void addMeasure(SOLAPMeasureParams measure, String factTableName);
    
    public void addLevels(Vector<SOLAPLevelParams> levels);
    
    public void addAttribute(SOLAPAttributeParams attr);
    
    public void addSlice(SOLAPSliceParams slice);
    
    public void addSpatialSlice(SOLAPSpatialSliceParams spatialSlice);
    
    public void addMeasurePreFilter(SOLAPFieldFilterParams measurePreFilter);
    
    public String getColumnTypeSpatial(int index);
    
    public void centroidOperation(int index);
    
    //To Summarization
    
    public String buildFocusSummarizationQuery();
    
    public String buildFocusDistinctSummarizationQuery();
    
    public void addSummarizationAttribute(SumarizationAttribute att);
    
    public void addSummarizationMeasure(SumarizationMeasure m);
    
    public void addFactTable(SOLAPFactTable ft);

}
