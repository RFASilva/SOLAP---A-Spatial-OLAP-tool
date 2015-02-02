package solap.bean;

import java.util.Vector;

import org.w3c.dom.Element;

import solap.ResponseExtractor;
import solap.SOLAPServerPortClient;

import solap.entities.SOLAPDimension;
import solap.entities.SOLAPLayer;
import solap.entities.SOLAPMapserver;
import solap.entities.SOLAPMeasure;

public class RequestResponseTest {
    SOLAPServerPortClient portClient = new SOLAPServerPortClient();
    
    private String requestText;
    private String responseText;
    
    //extracted information
    private String sqlQuery;
    private String numberRows;
    private String associatedAttributes;
    private String rowsetDistincts;
    private String distinctValues;
    //***** load_cube data *****
    //general
    private String cubeId;
    private String cubeName;
    private String cubeDescription;
    //map
    private String mapserverHost;
    private String basemapId;
    private String basemapName;
    private String basemapTitle;
    private String basemapSRId;
    private String basemapCenterX;
    private String basemapCenterY;
    private String basemapZoomLevel;
    private String basemapLayers;
    private String layers;
    //measures
    private String measures;
    //dimensions
    private String dimensions;
    
    
    public void commandButton_action() {
        //get response from server
        responseText = portClient.executeRequest(requestText);
        
        //extract information
        extractResponseInformation();
    }

    private void extractResponseInformation() {
        //create new ResponseExtractor object
        ResponseExtractor extractor = new ResponseExtractor(responseText);
        //get other informations
       // sqlQuery = extractor.getSQLQuery();
        numberRows = extractor.getNumberOfRows();
        //extractRowsetDistincts(extractor);
        extractAttributeTypes(extractor);
        //extractDistinctValues(extractor);
        
        //extractCubeInformation(extractor);
    }
    
    private void extractDistinctValues(ResponseExtractor extractor) {
        Vector<String> temp = extractor.getDistinctValues();
        
        for (int i = 0; i < temp.size(); i++) {
            distinctValues += temp.elementAt(i) + "\n";
        }
    }

    private void extractCubeInformation(ResponseExtractor extractor) {
        cubeId = extractor.getCubeId();
        cubeName = extractor.getCubeName();
        cubeDescription = extractor.getCubeDescription();
        
        //map connection
        mapserverHost = "http://" + extractor.getMapserver().getHost() + ":" + extractor.getMapserver().getPort();
        
        basemapId = extractor.getBasemapId();
        basemapName = extractor.getBasemapName();
        basemapTitle = extractor.getBasemapTitle();
        basemapSRId = extractor.getBasemapSRId();
        basemapCenterX = extractor.getBasemapCenterX();
        basemapCenterY = extractor.getBasemapCenterY();
        basemapZoomLevel = extractor.getBasemapZoomLevel();
        //get basemapLayers
        Vector<SOLAPLayer> temp = extractor.getBasemapLayers();
        for (int i = 0; i < temp.size(); i++) {
            basemapLayers += temp.elementAt(i).getId() + "\n";
        }
        //get layers
        temp = extractor.getLayers();
        for (int i = 0; i < temp.size(); i++) {
            layers += temp.elementAt(i).getTitle() + "(" + temp.elementAt(i).getId() + ")\n";
        }
        
        //get measures
        Vector<SOLAPMeasure> temp2 = extractor.getMeasures();
        for (int i = 0; i < temp2.size(); i++) {
            //if numeric measure, show aggregation operators
            if (temp2.elementAt(i).getType().compareTo("numeric") == 0) {
                measures += temp2.elementAt(i).getName() + " (";
                for (int j = 0; j < temp2.elementAt(i).getAggregationOperators().size(); j++) {
                    measures += temp2.elementAt(i).getAggregationOperators().elementAt(j) + ", ";
                }
                measures += ")";
            }
            //if calculated measure, show formula
            if (temp2.elementAt(i).getType().compareTo("calculated") == 0) {
                measures += temp2.elementAt(i).getName() + " (" + temp2.elementAt(i).getFormula() + ")";
            }
            measures += "\n";
        }
        
        //get dimensions
        Vector<SOLAPDimension> temp3 = extractor.getDimensions();
        for (int i = 0; i < temp3.size(); i++) {
            dimensions += temp3.elementAt(i).getName() + " (" + temp3.elementAt(i).getId() + ")";
            /*for (int j = 0; j < temp3.elementAt(i).getLevels().size(); j++) {
                dimensions += temp3.elementAt(i).getLevels().elementAt(j).getId() + " ";
                for (int k = 0; k < temp3.elementAt(i).getLevels().elementAt(j).getAttributes().size(); k++) {
                    dimensions += temp3.elementAt(i).getLevels().elementAt(j).getAttributes().elementAt(k).getName() + " ";
                }
                dimensions += ", ";
            }*/
            for (int j = 0; j < temp3.elementAt(i).getHierarchies().size(); j++) {
                dimensions += temp3.elementAt(i).getHierarchies().elementAt(j).getName() + " ";
                for (int k = 0; k < temp3.elementAt(i).getHierarchies().elementAt(j).getLevels().size(); k++) {
                    dimensions += temp3.elementAt(i).getHierarchies().elementAt(j).getLevels().elementAt(k).getId() + " ";
                }
                dimensions += ", ";
            }
            dimensions += "\n";
        }
    }
    
    private void extractRowsetDistincts(ResponseExtractor extractor) {
        Vector<String> vals = extractor.getRowsetDistincts(4);
        
        for (int i = 0; i < vals.size(); i++) {
            rowsetDistincts += vals.elementAt(i) + "\n";
        }
    }
    
    private void extractAttributeTypes(ResponseExtractor extractor) {
        //extractor.getDifferentDimensionAttributes();
        //extractor.getDifferentHierarchyAttributes();
        //extractor.getSameLevelAttributes();
        //extractor.getHigherLevelAttributes();
        //extractor.getLowerLevelAttributes();
        extractor.getMeasureNames();
        Vector<String> temp = extractor.getAssociatedAttributes();
        
        for (int i = 0; i < temp.size(); i++) {
            associatedAttributes += temp.elementAt(i) + "\n";
        }
    }

    public void setRequestText(String requestText) {
        this.requestText = requestText;
    }
    public String getRequestText() {
        return requestText;
    }
    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
    public String getResponseText() {
        return responseText;
    }
    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }
    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setNumberRows(String numberRows) {
        this.numberRows = numberRows;
    }
    public String getNumberRows() {
        return numberRows;
    }
    public void setDistinctValues(String distinctValues) {
        this.distinctValues = distinctValues;
    }
    public String getDistinctValues() {
        return distinctValues;
    }

    public void setCubeId(String cubeId) {
        this.cubeId = cubeId;
    }
    public String getCubeId() {
        return cubeId;
    }
    public void setCubeName(String cubeName) {
        this.cubeName = cubeName;
    }
    public String getCubeName() {
        return cubeName;
    }
    public void setCubeDescription(String cubeDescription) {
        this.cubeDescription = cubeDescription;
    }
    public String getCubeDescription() {
        return cubeDescription;
    }

    public void setBasemapId(String basemapId) {
        this.basemapId = basemapId;
    }
    public String getBasemapId() {
        return basemapId;
    }
    public void setBasemapName(String basemapName) {
        this.basemapName = basemapName;
    }
    public String getBasemapName() {
        return basemapName;
    }
    public void setBasemapTitle(String basemapTitle) {
        this.basemapTitle = basemapTitle;
    }
    public String getBasemapTitle() {
        return basemapTitle;
    }
    public void setBasemapSRId(String basemapSRId) {
        this.basemapSRId = basemapSRId;
    }
    public String getBasemapSRId() {
        return basemapSRId;
    }
    public void setBasemapCenterX(String basemapCenterX) {
        this.basemapCenterX = basemapCenterX;
    }
    public String getBasemapCenterX() {
        return basemapCenterX;
    }
    public void setBasemapCenterY(String basemapCenterY) {
        this.basemapCenterY = basemapCenterY;
    }
    public String getBasemapCenterY() {
        return basemapCenterY;
    }
    public void setBasemapZoomLevel(String basemapZoomLevel) {
        this.basemapZoomLevel = basemapZoomLevel;
    }
    public String getBasemapZoomLevel() {
        return basemapZoomLevel;
    }
    public void setBasemapLayers(String basemapLayers) {
        this.basemapLayers = basemapLayers;
    }
    public String getBasemapLayers() {
        return basemapLayers;
    }
    public void setLayers(String layers) {
        this.layers = layers;
    }
    public String getLayers() {
        return layers;
    }
    
    public void setMeasures(String measures) {
        this.measures = measures;
    }
    public String getMeasures() {
        return measures;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }
    public String getDimensions() {
        return dimensions;
    }

    public void setMapserverHost(String mapserverHost) {
        this.mapserverHost = mapserverHost;
    }
    public String getMapserverHost() {
        return mapserverHost;
    }

    public void setRowsetDistincts(String rowsetDistincts) {
        this.rowsetDistincts = rowsetDistincts;
    }
    public String getRowsetDistincts() {
        return rowsetDistincts;
    }

    public void setAssociatedAttributes(String associatedAttributes) {
        this.associatedAttributes = associatedAttributes;
    }
    public String getAssociatedAttributes() {
        return associatedAttributes;
    }

}
