package solap.tablegenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import oracle.spatial.geometry.JGeometry;

import org.w3c.dom.Document;

import solap.DataRequestProcessor;

import solap.params.SOLAPAttributeParams;

import solap.utils.ITriple;
import solap.utils.MappingUtils;
import solap.utils.Triple;

public class SpatialIntersectionGenerator extends PivotTableGenerator implements TableGenerator{

    //Store the spatial combinations and the new geometry of the object (arc)
    private Map<ITriple<String, String, String>, JGeometry> spatialCombinations;
    
    //"Handler" to comunicate with SQL generator
    private DataRequestProcessor sqlGenerator;
    
    public SpatialIntersectionGenerator(String name, Vector<String> headers, int numberMeasures, 
        Vector<String> inlineAttr, String semanticQuery, MappingUtils mapUtils, Vector<SOLAPAttributeParams> spatialAttr
        , DataRequestProcessor sqlGenerator, boolean clustering) {
        super(name, headers, spatialAttr, numberMeasures, inlineAttr, semanticQuery, mapUtils, clustering, sqlGenerator);
        
        spatialCombinations = new HashMap<ITriple<String, String, String>, JGeometry>();       
        this.sqlGenerator = sqlGenerator;
    }
    
    public void generateTable() {
        super.createTable();
        fillTable();
        super.createIndex("geom");
    }

    public String getQuery() {
        return super.getQuery();
    }


    private void fillTable() {  
        spatialCombinations = dbUtils.getIntersectedPolygons(sqlGenerator.computeSpatialQuery(), this.numberMeasures, this.numberMeasures + this.numberInlineAttr);
        for (int i = 0; i < data.size(); i++) {
            insertRow(data.elementAt(i), i);
        }
    }
    
    private void insertRow(Vector<String> rowValues, int index) {
        //Make key
        String[] locationsValues = spatialCombinationInfo2.get(index).split(",");
        ITriple<String, String, String> tempKey =
            new Triple<String, String, String>(locationsValues[0], locationsValues[1], "");
        
        //insert row including the new spatial object
        dbUtils.insertSRowOneSpatialObject(spatialCombinations.get(tempKey),
                        spatialCombinationInfo2.get(index), rowValues, index, this.name, "intersection");
    }
    
    
    public Document getClusteringData() {
        return super.getClusteringData();
    }

    public boolean didClustering() {
        return super.clustering;
    }
}
