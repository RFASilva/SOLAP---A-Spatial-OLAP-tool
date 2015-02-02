package solap.tablegenerator;

import java.util.*;
import java.util.Vector;
import oracle.spatial.geometry.JGeometry;
import org.w3c.dom.Document;
import solap.DataRequestProcessor;
import solap.params.SOLAPAttributeParams;
import solap.utils.DBUtils;
import solap.utils.MappingUtils;

public class OneSpatialGenerator extends PivotTableGenerator implements TableGenerator {

    //Store spatial combination and the geometry of the object
    private Map<String, JGeometry> spatialCombination;
    
    public OneSpatialGenerator(){}
    
    public OneSpatialGenerator(String name, Vector<String> headers, int numberMeasures, 
        Vector<String> inlineAttr, String semanticQuery, MappingUtils mapUtils, Vector<SOLAPAttributeParams> spatialAttr, boolean clustering, DataRequestProcessor sqlGenerator) {
        super(name, headers, spatialAttr, numberMeasures, inlineAttr, semanticQuery, mapUtils, clustering, sqlGenerator);
        spatialCombination = new HashMap<String, JGeometry>();
    }

    public void generateTable() {
        super.createTable();
        fillTable();
        super.createIndex("geom");
    }

    public String getQuery() {
        return super.getQuery();
    }
    
    protected void fillTable() {
        for (int i = 0; i < data.size(); i++) {
            insertRow(data.elementAt(i), i);
        }
    }
    
    private void insertRow(Vector<String> rowValues, int index) {
        //Variables to generate INSERT clause
        String spatialAttributeId = spatialAttributes.elementAt(0).getId();
        String spatialLevelId = spatialAttributes.elementAt(0).getLevelId();
        String spatialDimensionId = spatialAttributes.elementAt(0).getDimensionId();
        String spatialAttribute = mapUtils.getAttributeColumn(spatialAttributeId);
        String spatialTableId = mapUtils.getTableId(spatialLevelId, spatialDimensionId);
        String spatialTable = mapUtils.getTableName(spatialTableId);
        String associatedColumn = mapUtils.getAttributeColumn(associatedAttributes.elementAt(0));
        
        //add values to the row
        String query = "insert into " + name + " values (" + index + ", ";
        
        int alphanumericValues = 0;
        for (int i = 0; i < rowValues.size(); i++) {
            if(alphanumericValues < super.inlineAttributes.size()) {
                query += "'" + DBUtils.cleanString(rowValues.elementAt(i)) + "', ";
                alphanumericValues++;
            }
            else query += rowValues.elementAt(i) + ", ";
        }
        String spatialCombination = spatialCombinationInfo2.get(index);
        if(super.clustering && super.groupsObjects.get(0).get(spatialCombination) != null) {
            dbUtils.insertSRowOneSpatialObject(super.groupsObjects.get(0).get(spatialCombination), spatialCombination, rowValues, index, super.name, "group");
        }
        else {
            query += "'" +spatialCombination+ "', ";
            query += "(SELECT " + spatialAttribute + " FROM " + spatialTable + " WHERE " + spatialTable + "." + associatedColumn + " = '" + spatialCombination  + "'), ' ', '" + spatialCombination +"','" + spatialCombination +"' )";
            
            dbUtils.executeQuery(query);
        }
    }
    
    public Document getClusteringData() {
        return super.getClusteringData();
    }

    public boolean didClustering() {
        return super.clustering;
    }
}
