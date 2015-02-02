package solap;

import java.util.Vector;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import solap.entities.SOLAPAttribute;
import solap.entities.SOLAPDimension;
import solap.entities.SOLAPHierarchy;
import solap.entities.SOLAPLayer;
import solap.entities.SOLAPLevel;
import solap.entities.SOLAPMapserver;
import solap.entities.SOLAPMeasure;

import solap.styles.Style;
import solap.styles.ManagerStyles;

import solap.utils.XMLUtils;
import java.util.Map;
import java.util.HashMap;
import solap.utils.Triple;
import solap.utils.ITriple;
import java.util.List;
import java.util.LinkedList;

import org.w3c.dom.NamedNodeMap;

import solap.entities.SOLAPFactTable;
import solap.entities.SOLAPPreComputingDistance;

import solap.entities.SOLAPPreComputingSharedBorders;

import solap.styles.*;

public class ResponseExtractor {

    //webservice port client
    SOLAPServerPortClient portClient = new SOLAPServerPortClient();
    //response document
    Document response;
    //xml utils
    XMLUtils xmlUtils = new XMLUtils();
    
    public ResponseExtractor(String responseText)  {
        this.response = xmlUtils.XmlToDoc(responseText);
    }
    
    public String getNumDistincCharacteristics(){
        String result ="";
        Element[] queries = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "query", true);
        for(int i = 0; i < queries.length; i++) {
            result = queries[i].getAttribute("numDistinct");
        }
        return result;
    }
    
    public String getNumberOfGroups(){
        String result ="";
        Element[] queries = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "query", true);
        for(int i = 0; i < queries.length; i++) {
            result = queries[i].getAttribute("createdGroups");
        }
        return result;
    }
    
    //********** get_factTables responses *********
    
    public Vector<SOLAPFactTable> getFactTables(){
        Vector<SOLAPFactTable> factTables = new Vector<SOLAPFactTable>();
        
        NodeList list = response.getElementsByTagName("facttable");
        int size = list.getLength();
        for(int i=0;i<size;i++){
            String id = list.item(i).getAttributes().getNamedItem("id").getNodeValue();
            String name = list.item(i).getAttributes().getNamedItem("name").getNodeValue();
            factTables.add(new SOLAPFactTable(id,name));
        }
        
        return factTables;
    }
    
    //********** list_cubes responses *********
    public Map<String, ITriple<String, String, String>> getCubes() {
        Map<String, ITriple<String, String, String>> result = new HashMap<String, ITriple<String, String, String>>();
        Element[] elems = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "cube", true);
        for (int i = 0; i < elems.length; i++) {
            String cubeId = elems[i].getAttribute("id");
            String cubeName = elems[i].getAttribute("name");
            String fileName = elems[i].getAttribute("filename");
           
            result.put(cubeName, new Triple<String, String, String>(cubeId, fileName, cubeName));
        }
        
        return result;
    }
    public String getDescription(String cubeName) {
        Element temp = xmlUtils.getElementByAttribute(response, "cube", "name", cubeName);
        
        if (temp != null)
            return temp.getAttribute("description");
        else
            return "";
    }
    public Vector<String> getSessions(String cubeName) {
        Vector<String> sessionList = new Vector<String>();        
        Element temp = xmlUtils.getElementByAttribute(response, "cube", "name", cubeName);
        
        if (temp != null) {
            Element[] sessions = xmlUtils.getAllChildElementNamed(temp, "session", true);
        
            for (int i = 0; i < sessions.length; i++) {
                sessionList.add(sessions[i].getAttribute("name"));
            }
        }
        
        return sessionList;
    }
    
    //********** get_data responses ***********
    //returns the SQL query
    public List<String> getSQLQuery() {
        List<String> result = new LinkedList<String>();
        
        Element[] queries = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "query", true);
        for(int i = 0; i < queries.length; i++) {
            result.add(queries[i].getAttribute("sql"));
        }
        
        return result;
    }
    
    //return the geometry type
    public List<String> getGeometryType() {
        List<String> result = new LinkedList<String>();
        
        Element[] queries = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "query", true);
        for(int i = 0; i < queries.length; i++) {
            String value = queries[i].getAttribute("geometryType");
            result.add(value);
    
        }
        
        return result;
    
        //return xmlUtils.getAttributeValueFirstElement(response, "query", "geometryType");
    }
    
    //rowset associated metadata retrieval functions
    public String getNumberOfRows() {
        return xmlUtils.getAttributeValueFirstElement(response, "table", "count");
    }
    
    public String getNumberOfMeasures() {
        return xmlUtils.getAttributeValueFirstElement(response, "table", "nMeasures");
    }
    
    public Vector<String> getAssociatedAttributes() {
        Vector<String> values = new Vector<String>();
        
        Element associatedAttributes = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "associatedAttributes", true)[0];
        Element[] elems = xmlUtils.getAllChildElementNamed(associatedAttributes, "attribute", false);
        for (int i = 0; i < elems.length; i++) {
            values.add(elems[i].getAttribute("name"));
        }
        
        return values;
    }
    
    public Vector<SOLAPAttribute> getAssociatedInfo() {
        Vector<SOLAPAttribute> values = new Vector<SOLAPAttribute>();
        
        Element associatedAttributes = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "associatedAttributes", true)[0];
        Element[] elems = xmlUtils.getAllChildElementNamed(associatedAttributes, "attribute", false);
        for (int i = 0; i < elems.length; i++) {
            values.add(new SOLAPAttribute(elems[i].getAttribute("id"), elems[i].getAttribute("name"), elems[i].getAttribute("dimensionId"), elems[i].getAttribute("levelId")));
        }
        
        return values;
    }
    
    public Vector<String> getDifferentDimensionAttributes() {
        Vector<String> values = new Vector<String>();
        
        Element associatedAttributes = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "differentDimension", true)[0];
        Element[] elems = xmlUtils.getAllChildElementNamed(associatedAttributes, "attribute", false);
        for (int i = 0; i < elems.length; i++) {
            values.add(elems[i].getAttribute("name"));
        }
        
        return values;
    }
    
    public Vector<String> getSameLevelAttributes() {
        Vector<String> values = new Vector<String>();
        
        Element associatedAttributes = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "sameLevel", true)[0];
        Element[] elems = xmlUtils.getAllChildElementNamed(associatedAttributes, "attribute", false);
        for (int i = 0; i < elems.length; i++) {
            values.add(elems[i].getAttribute("name"));
        }
        
        return values;
    }
    
    public Vector<String> getLowerLevelAttributes() {
        Vector<String> values = new Vector<String>();
        
        Element associatedAttributes = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "lowerLevel", true)[0];
        Element[] elems = xmlUtils.getAllChildElementNamed(associatedAttributes, "attribute", false);
        for (int i = 0; i < elems.length; i++) {
            values.add(elems[i].getAttribute("name"));
        }
        
        return values;
    }
    
    public Vector<String> getHigherLevelAttributes() {
        Vector<String> values = new Vector<String>();
        
        Element associatedAttributes = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "higherLevel", true)[0];
        Element[] elems = xmlUtils.getAllChildElementNamed(associatedAttributes, "attribute", false);
        for (int i = 0; i < elems.length; i++) {
            values.add(elems[i].getAttribute("name"));
        }
        
        return values;
    }
    public Vector<String> getDifferentHierarchyAttributes() {
        Vector<String> values = new Vector<String>();
        
        Element associatedAttributes = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "differentHierarchy", true)[0];
        Element[] elems = xmlUtils.getAllChildElementNamed(associatedAttributes, "attribute", false);
        for (int i = 0; i < elems.length; i++) {
            values.add(elems[i].getAttribute("name"));
        }
        
        return values;
    }
    
    //rowset and row retrieval functions
    public Element getRowset() {
        return xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "ROWSET", true)[0];  
    }
    
    public String getElementName(int rowPos, int elemPos) {
       // System.out.println("GetElementName: " + response.getDocumentElement());
        Element rowset = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "ROWSET", true)[0];
        Element row = (Element)rowset.getElementsByTagName("ROW").item(rowPos);

        return row.getChildNodes().item(elemPos).getNodeName();
    }
    
    public String getElementValue(int rowPos, int elemPos) {
        Element rowset = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "ROWSET", true)[0];
        Element row = (Element)rowset.getElementsByTagName("ROW").item(rowPos);

        return row.getChildNodes().item(elemPos).getFirstChild().getNodeValue();
    }
    
    public Vector<String> getRowsetDistincts(int elemPos) {
        Vector<String> values = new Vector<String>();
        
        int tempNumberOfRows = Integer.parseInt(getNumberOfRows());
        
        for (int i = 0; i < tempNumberOfRows; i++) {
            if (!values.contains(getElementValue(i, elemPos))) {
                values.add(getElementValue(i, elemPos));
            }
        }
        
        return values;
    }
    
    //returns the position of an element inside a row
    public int getElementPosition(String name) {
        Element rowset = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "ROWSET", true)[0];
        Element oneRow = (Element)rowset.getElementsByTagName("ROW").item(0);
        
        NodeList nodes = oneRow.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (name.compareToIgnoreCase(nodes.item(i).getNodeName()) == 0)
                return i;
        }
    
        //not found
        return -1;
    }
    
    public Vector<String> getMeasureNames() {
        Vector<String> measures = new Vector<String>();
        
        for (int i = 0; i < Integer.parseInt(getNumberOfMeasures()); i++) {
            measures.add(getElementName(0, i));
        }            
        
        return measures;
    }
    
    
    public Vector<String> getRowsetDistincts(String attribute) {
        Vector<String> result = new Vector<String>();
        
        int tempNumberOfRows = Integer.parseInt(getNumberOfRows());
        attribute = attribute.toUpperCase();
        Element rowset = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "ROWSET", true)[0];
        for(int i = 0; i < tempNumberOfRows; i++ ) {
            
            Element oneRow = (Element)rowset.getElementsByTagName("ROW").item(i);    
            NodeList nodes = oneRow.getChildNodes();
            for (int j = 0; j < nodes.getLength(); j++) {
                if (attribute.compareToIgnoreCase(nodes.item(j).getNodeName()) == 0) {
                    String value = nodes.item(j).getTextContent();
                    if(!result.contains(value)) {
                        result.add(value);
                    }
                }
            }
        }
        
        return result;
    }
    
    //********** get_distincts responses *************
    //returns a vector containing the distinct values
    public Vector<String> getDistinctValues() {
        Vector<String> distinctValues = new Vector<String>();
        
        //get an array of <distinct> elements
        Element[] elems = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "distinct", true);
        
        //add all distinct values to the vector
        for (int i = 0; i < elems.length; i++) {
            String value = elems[i].getAttribute("value");
            distinctValues.add(value);
        }

        return distinctValues;
    }
    

    
    
    //********** load_cube responses *************
    //general
    public String getCubeId() {
        return xmlUtils.getAttributeValueFirstElement(response, "cube", "id");
    }
    public String getCubeName() {
        return xmlUtils.getAttributeValueFirstElement(response, "cube", "name");
    }
    
    public String getCubeDescription() {
        return xmlUtils.getAttributeValueFirstElement(response, "cube", "description");
    }
    
    //maps
    public SOLAPMapserver getMapserver() {
        Element maps = (Element)response.getDocumentElement().getElementsByTagName("maps").item(0);
        Element connection = (Element)maps.getElementsByTagName("connection").item(0);
        String host = connection.getAttribute("host");
        String port = connection.getAttribute("port");
        String name = connection.getAttribute("name");
        String datasource = connection.getAttribute("datasource");
        
        return new SOLAPMapserver(host, port, name, datasource, Float.parseFloat(getBasemapCenterX()),
                                        Float.parseFloat(getBasemapCenterY()), Integer.parseInt(getBasemapZoomLevel()),
                                        getBasemapName(), Integer.parseInt(getBasemapSRId()));
    }
    public String getBasemapId() {
        return xmlUtils.getAttributeValueFirstElement(response, "basemap", "id");
    }
    public String getBasemapSRId() {
        return xmlUtils.getAttributeValueFirstElement(response, "basemap", "srid");
    }
    public String getBasemapCenterX() {
        return xmlUtils.getAttributeValueFirstElement(response, "basemap", "centerX");
    }
    public String getBasemapCenterY() {
        return xmlUtils.getAttributeValueFirstElement(response, "basemap", "centerY");
    }
    public String getBasemapZoomLevel() {
        return xmlUtils.getAttributeValueFirstElement(response, "basemap", "zoomLevel");
    }
    public String getBasemapName() {
        return xmlUtils.getAttributeValueFirstElement(response, "basemap", "name");
    }
    public String getBasemapTitle() {
        return xmlUtils.getAttributeValueFirstElement(response, "basemap", "title");
    }
    public Vector<SOLAPLayer> getBasemapLayers() {
        Vector<SOLAPLayer> basemapLayers = new Vector<SOLAPLayer>();
        
        //get <layers> element inside <basemap>
        Element layers = (Element)((Element)response.getElementsByTagName("basemap").item(0)).getElementsByTagName("layers").item(0);
        
        //get an array of <layer>
        Element[] elems = xmlUtils.getAllChildElementNamed(layers, "layer", true);
        
        //add all layerRef values to the vector
        for (int i = 0; i < elems.length; i++) {
            String ref = elems[i].getAttribute("layerRef");
            basemapLayers.add(new SOLAPLayer(ref));
        }
        
        return basemapLayers;
    }
    //layer structures
    Vector<SOLAPLayer> layers = new Vector<SOLAPLayer>();
    Vector<SOLAPLayer> layersLabel = new Vector<SOLAPLayer>();
    Vector<SOLAPLayer> layersObject = new Vector<SOLAPLayer>();
    public Vector<SOLAPLayer> getLayers() {
        //get main <layers> element
        Element layersElem = xmlUtils.getAllChildElementNamed((Element)response.getDocumentElement().getElementsByTagName("maps").item(0), "layers", false)[0];  //deep as false so that only the main <layers> is caught
        //add a new SOLAPLayer for each <layer> element
        Element[] elems = xmlUtils.getAllChildElementNamed(layersElem, "layer", true);
        for (int i = 0; i < elems.length; i++) {
            String id = elems[i].getAttribute("id");
            String title = elems[i].getAttribute("title");
            String name = elems[i].getAttribute("name");
            String columnRef = elems[i].getAttribute("columnRef");
            
            //3 types of layer depending on "object" and "label"
            String label = elems[i].getAttribute("label");
            String object = elems[i].getAttribute("object");
            if (label.compareTo("true") == 0) {
                layersLabel.add(new SOLAPLayer(id, title, name, columnRef));
            }
            else if (object.compareTo("true") == 0) {
                layersObject.add(new SOLAPLayer(id, title, name));
            }
            else {
                layers.add(new SOLAPLayer(id, title, name));
            }
        }
        
        return layers;
    }
    public Vector<SOLAPLayer> getLayersLabel() {
        return layersLabel;
    }
    public Vector<SOLAPLayer> getLayersObject() {
        return layersObject;
    }
    //measures
    public Vector<SOLAPMeasure> getMeasures() {
        Vector<SOLAPMeasure> measures = new Vector<SOLAPMeasure>();
        
        //add a new SOLAPMeasure for each <measure> element
        Element[] elems = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "measure", true);
        for (int i = 0; i < elems.length; i++) {
            String id = elems[i].getAttribute("id");
            String name = elems[i].getAttribute("name");
            String type = elems[i].getAttribute("type");
            String format = elems[i].getAttribute("format");
            
            //numeric measure
            if (type.compareTo("numeric") == 0) {
                Vector<String> aggregationOperators = new Vector<String>();
                
                //add all aggregation operators
                Element[] operators = xmlUtils.getAllChildElementNamed(elems[i], "numeric", true);
                for (int j = 0; j < operators.length; j++) {
                    aggregationOperators.add(operators[j].getAttribute("operator"));
                }
                
                measures.add(new SOLAPMeasure(id, name, type, format, aggregationOperators));
            }
            //calculated measure
            else {
                Element[] calculated = xmlUtils.getAllChildElementNamed(elems[i], "calculated", true);
                String formula = calculated[0].getAttribute("formula");
                measures.add(new SOLAPMeasure(id, name, type, format, formula));
            }
        }
        
        return measures;
    }
    //dimensions
    public Vector<SOLAPDimension> getDimensions() {
        Vector<SOLAPDimension> dimensions = new Vector<SOLAPDimension>();
        
        //get <dimension> elements
        
        Element[] dimensionElems = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "dimension", true);
        for (int i = 0; i < dimensionElems.length; i++) {
            //add general data
            String id = dimensionElems[i].getAttribute("id");
            String name = dimensionElems[i].getAttribute("name");
            
            //add levels data
            Vector<SOLAPLevel> levels = new Vector<SOLAPLevel>();
            Element[] outerLevelsElem = xmlUtils.getAllChildElementNamed(dimensionElems[i], "levels", true);
            Element[] levelsElem = xmlUtils.getAllChildElementNamed(outerLevelsElem[0], "level", true);
            
            Map<String, SOLAPLevel> tempLevels = new HashMap<String, SOLAPLevel>();
            addLevelsData(levels, levelsElem, id, tempLevels);
            
                  
            //add hierarchies data
            Vector<SOLAPHierarchy> hierarchies = new Vector<SOLAPHierarchy>();
            Element[] hierarchiesElem = xmlUtils.getAllChildElementNamed(dimensionElems[i], "hierarchy", true);
            addHierarchiesData(hierarchies, hierarchiesElem, tempLevels);
            
            //add to dimensions vector
            dimensions.add(new SOLAPDimension(id, name, levels, hierarchies));
        }
        
        return dimensions;
    }

    //auxiliary functions
    private void addLevelsData(Vector<SOLAPLevel> levels, Element[] levelsElem, String dimensionId, Map<String, SOLAPLevel> tempLevels) {
        for (int j = 0; j < levelsElem.length; j++) {
            String levelId = levelsElem[j].getAttribute("id");
            String levelName = levelsElem[j].getAttribute("name");
            String spatialType = levelsElem[j].getAttribute("spatialType");
            String spatialAttr = levelsElem[j].getAttribute("spatialAttribute");
            String displayAttributeID = levelsElem[j].getAttribute("displayAttribute");
            
            System.out.println("DISPLAY ATTRIBUTE ID: " + displayAttributeID);
            
            //add attributes data
            Vector<SOLAPAttribute> attributes = new Vector<SOLAPAttribute>();
            Element[] attributesElem = xmlUtils.getAllChildElementNamed(levelsElem[j], "attribute", true);
            for (int k = 0; k < attributesElem.length; k++) {
                String attributeId = attributesElem[k].getAttribute("id");
                String columnRef = attributesElem[k].getAttribute("columnRef");
                String attributeName = attributesElem[k].getAttribute("name");
                attributes.add(new SOLAPAttribute(attributeId, attributeName, dimensionId, levelId, columnRef));
            }
            
            SOLAPLevel newLevel = null;
                
            if(xmlUtils.getAllChildElementNamed(levelsElem[j], "preComputing", true).length > 0) {
                Element preComputing = (Element) xmlUtils.getAllChildElementNamed(levelsElem[j], "preComputing", true)[0];
                Element distance = (Element) xmlUtils.getAllChildElementNamed(preComputing,"distances", true)[0];
                
                String tableRef = distance.getAttribute("tableRef");
                
                String from = xmlUtils.getAllChildElementNamed(distance, "from", true)[0].getAttribute("columnRef");               
                String to = xmlUtils.getAllChildElementNamed(distance, "to", true)[0].getAttribute("columnRef");
                String distanceValue = xmlUtils.getAllChildElementNamed(distance, "distanceValue", true)[0].getAttribute("columnRef");
                
                SOLAPPreComputingDistance preComputingInfo =  new SOLAPPreComputingDistance(tableRef,from,to,distanceValue);
                //newLevel = new SOLAPLevel(levelId, dimensionId, levelName, attributes, spatialType, spatialAttr, displayAttributeID, preComputingInfo);
                
                // Add info about the shared borders
                Element shared = xmlUtils.getAllChildElementNamed(preComputing,"shared_borders", true)[0];
                
                String shared_tableRef = shared.getAttribute("tableRef");
                
                String shared_from = xmlUtils.getAllChildElementNamed(shared, "from", true)[0].getAttribute("columnRef");               
                String shared_to = xmlUtils.getAllChildElementNamed(shared, "to", true)[0].getAttribute("columnRef");
                String shared_border = xmlUtils.getAllChildElementNamed(shared, "shared_border", true)[0].getAttribute("columnRef");
                
                SOLAPPreComputingSharedBorders preComputingSharedBordersInfo =  new SOLAPPreComputingSharedBorders(shared_tableRef,shared_from,shared_to,shared_border);
                newLevel = new SOLAPLevel(levelId, dimensionId, levelName, attributes, spatialType, spatialAttr, displayAttributeID, preComputingInfo, preComputingSharedBordersInfo);
            }
            else newLevel = new SOLAPLevel(levelId, dimensionId, levelName, attributes, spatialType, spatialAttr, displayAttributeID);
            
            levels.add(newLevel);
            tempLevels.put(newLevel.getId(), newLevel);
        }
    }
    private void addHierarchiesData(Vector<SOLAPHierarchy> hierarchies, Element[] hierarchiesElem, Map<String, SOLAPLevel> tempLevels) {
        for (int j = 0; j < hierarchiesElem.length; j++) {
            String hierarchyId = hierarchiesElem[j].getAttribute("id");
            String hierarchyName = hierarchiesElem[j].getAttribute("name");
            String typeHierarchy = hierarchiesElem[j].getAttribute("type");
            
            System.out.println("TIPO DA GEOMETRIA: " + typeHierarchy);
            
            //add attributes data
            Vector<SOLAPLevel> levelRefs = new Vector<SOLAPLevel>();
            Element[] levelRefElem = xmlUtils.getAllChildElementNamed(hierarchiesElem[j], "level", true);
            for (int k = 0; k < levelRefElem.length; k++) {
                String levelRef = levelRefElem[k].getAttribute("levelRef");
                levelRefs.add(tempLevels.get(levelRef));
            }
            
            hierarchies.add(new SOLAPHierarchy(hierarchyId, hierarchyName, typeHierarchy, levelRefs));
        }
    }

    /* Functions to parse information about styles */
    public void fillStyles(ManagerStyles managerStyle) {
        Element[] stylesInfo = xmlUtils.getAllChildElementNamed(response.getDocumentElement(), "styles", true); 

        for(int i = 0; i < stylesInfo.length; i++) {

            //System.out.println("Nome do no: " + stylesInfo[i].getNodeName());
            NodeList node = stylesInfo[i].getChildNodes();
            
            for(int j = 1; j < node.getLength(); j+=2) {
                Node style = node.item(j);
                
                if(style.getNodeName().equals("decisionTree")) {
                    readDecisionTree(style, managerStyle);
                    continue;
                }    
                
                String id = style.getAttributes().getNamedItem("id").getNodeValue();
                
                NodeList styleTypeTemp = style.getChildNodes();
                Node styleType = styleTypeTemp.item(1);
                
                if(styleType.getNodeName().equals("variableBrightness"))
                    parseVariableBrightness(styleType, id, managerStyle);

                if(styleType.getNodeName().equals("variableShape"))
                   parseVariableShape(styleType, id, managerStyle);

                if(styleType.getNodeName().equals("variableColor"))
                   parseVariableColor(styleType, id, managerStyle);

                if(styleType.getNodeName().equals("variableSize"))
                   parseVariableSize(styleType, id, managerStyle);

                if(styleType.getNodeName().equals("variableTexture"))
                    parseVariableTexture(styleType, id, managerStyle);        

                if(styleType.getNodeName().equals("barchart"))
                    parseBarChart(styleType, id, managerStyle);       

                if(styleType.getNodeName().equals("piechart"))
                   parsePieChart(styleType, id, managerStyle);

                if(styleType.getNodeName().equals("compositeStyle"))
                   parseCompositeStyle(styleType, id, managerStyle);
            }
        }
    }
    
    private void parseVariableBrightness(Node style, String id, ManagerStyles managerStyle) {
        Style newStyle = null;
        VisualProperty visualProperty = null;
        NamedNodeMap attrs = style.getAttributes();
      
        String strokeColor = "";
        if(attrs.getNamedItem("strokeColor") != null)
            strokeColor = attrs.getNamedItem("strokeColor").getNodeValue();
            
        String baseColor = "";
        if(attrs.getNamedItem("baseColor") != null) {
            baseColor = attrs.getNamedItem("baseColor").getNodeValue();
            visualProperty = new VariableBrightness(baseColor, strokeColor);
        }
        else visualProperty = new VariableBrightness(strokeColor);
            
        String numberOfClasses = "";
        if(attrs.getNamedItem("numberOfClasses") != null)
            numberOfClasses = attrs.getNamedItem("numberOfClasses").getNodeValue();
        
        String typeOfDistribution = "";
        if(attrs.getNamedItem("typeOfDistribution") != null)
            typeOfDistribution = attrs.getNamedItem("typeOfDistribution").getNodeValue();

        String marker = "";
        if(attrs.getNamedItem("marker") != null)
            marker = attrs.getNamedItem("marker").getNodeValue();  
        
        String size = "";
        if(attrs.getNamedItem("size") != null)
            size = attrs.getNamedItem("size").getNodeValue();
        
        
        ((VariableBrightness)visualProperty).setMarker(marker);
        ((VariableBrightness)visualProperty).setSize(size);
        
        Map<String, String> tempColors;
        
        NodeList childNodes = style.getChildNodes();
        for (int i = 1; i < childNodes.getLength(); i+=2) {
                Node child = style.getChildNodes().item(i);

                if(child.getNodeName().equals("gradientDefinition")) {
                    tempColors = parseColors(child);
                    ((VariableBrightness)visualProperty).setManualColors(tempColors);   
                }

                String typeBuckets = child.getNodeName();
                
                if(typeBuckets.equals(ManagerStyles.INTERVAL)) {
                    newStyle = new ContinuosStyle(id, Integer.parseInt(numberOfClasses), visualProperty , typeOfDistribution);        
                    parseIntervals(child, newStyle);
                    
                }
                if(typeBuckets.equals(ManagerStyles.SETS)) {
                    newStyle = new DiscreteStyle(id, Integer.parseInt(numberOfClasses), visualProperty, typeOfDistribution);        
                    parseSets(child, newStyle);
                }
        }

        //Add the new style properly filled
        managerStyle.addStyle(new TypeSimple("", "VariableBrightness"), newStyle);
    }
    
    private void parseVariableShape(Node style, String id, ManagerStyles managerStyle) {
        Style newStyle = null;
        VisualProperty visualProperty = null;
        
        NamedNodeMap attrs = style.getAttributes();

        String numberOfClasses = "";
        if(attrs.getNamedItem("numberOfClasses") != null)
            numberOfClasses = attrs.getNamedItem("numberOfClasses").getNodeValue();
        
        String typeOfDistribution = "";
        if(attrs.getNamedItem("typeOfDistribution") != null)
            typeOfDistribution = attrs.getNamedItem("typeOfDistribution").getNodeValue();
        
        String color = "";
        if(attrs.getNamedItem("color") != null)
            color = attrs.getNamedItem("color").getNodeValue();
        
        String size = "";
        if(attrs.getNamedItem("size") != null)
            size = attrs.getNamedItem("size").getNodeValue();

        visualProperty = new VariableShape();
        ((VariableShape)visualProperty).setColor(color);
        ((VariableShape)visualProperty).setSize(size);
        
        Map<String, String> tempShapes;

        NodeList childNodes = style.getChildNodes();
        for (int i = 1; i < childNodes.getLength(); i+=2) {
                Node child = style.getChildNodes().item(i);

                if(child.getNodeName().equals("shapes")) {
                    tempShapes = parseShapes(child);
                    ((VariableShape)visualProperty).setManualShapes(tempShapes);
                }

                String typeBuckets = child.getNodeName();
                if(typeBuckets.equals(ManagerStyles.INTERVAL)) {
                        newStyle = new ContinuosStyle(id, Integer.parseInt(numberOfClasses), visualProperty , typeOfDistribution);  
                        parseIntervals(child, newStyle);
                }
                
                if(typeBuckets.equals(ManagerStyles.SETS)) {
                        newStyle = new DiscreteStyle(id, Integer.parseInt(numberOfClasses), visualProperty, typeOfDistribution); 
                        parseSets(child, newStyle);
                }
        }
        //Add the new style properly filled
        managerStyle.addStyle(new TypeSimple("","VariableShape"), newStyle);
    }

 
    private void parseVariableColor(Node style, String id, ManagerStyles managerStyle) {
        Style newStyle = null;
        VisualProperty visualProperty = null;
        NamedNodeMap attrs = style.getAttributes();

        String marker = "";
        if(attrs.getNamedItem("marker") != null)
            marker = attrs.getNamedItem("marker").getNodeValue();
        
        String size = "";
        if(attrs.getNamedItem("size") != null)
            size = attrs.getNamedItem("size").getNodeValue();
            
        String numberOfClasses = "";
        if(attrs.getNamedItem("numberOfClasses") != null)
            numberOfClasses = attrs.getNamedItem("numberOfClasses").getNodeValue();
  
        String typeOfDistribution = "";
        if(attrs.getNamedItem("typeOfDistribution") != null)
            typeOfDistribution = attrs.getNamedItem("typeOfDistribution").getNodeValue();

        visualProperty = new VariableColor();
        ((VariableColor)visualProperty).setMarker(marker);
        ((VariableColor)visualProperty).setSize(size);
        
        Map<String, String> tempColors;
        NodeList childNodes = style.getChildNodes();
        for (int i = 1; i < childNodes.getLength(); i+=2) {
                Node child = style.getChildNodes().item(i);

                if(child.getNodeName().equals("colors")) {
                    tempColors = parseColors(child);
                    ((VariableColor)visualProperty).setManualColors(tempColors);
                }

                String typeBuckets = child.getNodeName();
                if(typeBuckets.equals(ManagerStyles.INTERVAL)) {
                    newStyle = new ContinuosStyle(id, Integer.parseInt(numberOfClasses), visualProperty , typeOfDistribution);  
                    parseIntervals(child, newStyle);
                }
                if(typeBuckets.equals(ManagerStyles.SETS)) {
                    newStyle = new DiscreteStyle(id, Integer.parseInt(numberOfClasses), visualProperty, typeOfDistribution); 
                    parseSets(child, newStyle);
                }
        }
        
        //Add the new style properly filled
        managerStyle.addStyle(new TypeSimple("","VariableColor"), newStyle);
    }

    
    private void parseVariableSize(Node style, String id, ManagerStyles managerStyle) {
        Style newStyle = null;
        VisualProperty visualProperty = null;
        NamedNodeMap attrs = style.getAttributes();

        String shapeMarker = "";
        if(attrs.getNamedItem("marker") != null) {
            shapeMarker = attrs.getNamedItem("marker").getNodeValue();
        }
        
        String initialSize = attrs.getNamedItem("initialSize").getNodeValue();
        
        String finalSize = "";
        if(attrs.getNamedItem("finalSize") != null) {
            finalSize = attrs.getNamedItem("finalSize").getNodeValue();
            visualProperty = new VariableSize(shapeMarker, initialSize, finalSize);
        }
        
        String increment = ""; 
        if(attrs.getNamedItem("increment") != null) {
            increment = attrs.getNamedItem("increment").getNodeValue();
            visualProperty = new VariableSize(shapeMarker, initialSize);
            ((VariableSize)visualProperty).setIncrement(increment);
        }
        
        String numberOfClasses = "";
        if(attrs.getNamedItem("numberOfClasses") != null)
            numberOfClasses = attrs.getNamedItem("numberOfClasses").getNodeValue();
        
        String typeOfDistribution = "";
        if(attrs.getNamedItem("typeOfDistribution") != null)
            typeOfDistribution = attrs.getNamedItem("typeOfDistribution").getNodeValue();
        
        String color = "";
        if(attrs.getNamedItem("color") != null) {
            color = attrs.getNamedItem("color").getNodeValue();
            ((VariableSize)visualProperty).setColor(color);
        }
        
        NodeList childNodes = style.getChildNodes();
        for (int i = 1; i < childNodes.getLength(); i+=2) {
                Node child = style.getChildNodes().item(i);

                String typeBuckets = child.getNodeName();
                if(typeBuckets.equals(ManagerStyles.INTERVAL)) {
                        newStyle = new ContinuosStyle(id, Integer.parseInt(numberOfClasses), visualProperty , typeOfDistribution);  
                        parseIntervals(child, newStyle);
                }
                if(typeBuckets.equals(ManagerStyles.SETS)) {
                        newStyle = new DiscreteStyle(id, Integer.parseInt(numberOfClasses), visualProperty, typeOfDistribution); 
                        parseSets(child, newStyle);
                }
        }
        //Add the new style properly filled
        managerStyle.addStyle(new TypeSimple("","VariableSize"), newStyle);
    }
    
    
    private void parseVariableTexture(Node style, String id, ManagerStyles managerStyle) {
        Style newStyle = null;
        VisualProperty visualProperty = null;
        
        NamedNodeMap attrs = style.getAttributes();

        String numberOfClasses = "";
        if(attrs.getNamedItem("numberOfClasses") != null)
            numberOfClasses = attrs.getNamedItem("numberOfClasses").getNodeValue();
        
        String typeOfDistribution = "";
        if(attrs.getNamedItem("typeOfDistribution") != null)
            typeOfDistribution = attrs.getNamedItem("typeOfDistribution").getNodeValue();
            
        visualProperty = new VariableTexture();
        Map<String, String> tempTextures;
        
        NodeList childNodes = style.getChildNodes();
        for (int i = 1; i < childNodes.getLength(); i+=2) {
                Node child = style.getChildNodes().item(i);

                if(child.getNodeName().equals("textures")) {
                    tempTextures = parseTextures(child);
                    ((VariableTexture)visualProperty).setManualTextures(tempTextures);
                }

                String typeBuckets = child.getNodeName();
                if(typeBuckets.equals(ManagerStyles.INTERVAL)) {
                    newStyle = new ContinuosStyle(id, Integer.parseInt(numberOfClasses), visualProperty , typeOfDistribution);  
                    parseIntervals(child, newStyle);
                }
                if(typeBuckets.equals(ManagerStyles.SETS)) {
                    newStyle = new DiscreteStyle(id, Integer.parseInt(numberOfClasses), visualProperty, typeOfDistribution);
                    parseSets(child, newStyle);
                }
        }
        
        //Add the new style properly filled
        managerStyle.addStyle(new TypeSimple("","VariableTexture"), newStyle);
    }
            
    private void parseBarChart(Node style, String id, ManagerStyles managerStyle) {
        Style newStyle = null;
        
        NamedNodeMap attrs = style.getAttributes();

        String width = "";
        if(attrs.getNamedItem("width") != null)
            width = attrs.getNamedItem("width").getNodeValue();
               
        String height = "";
        if(attrs.getNamedItem("height") != null)
            height = attrs.getNamedItem("height").getNodeValue();

        String numberOfBars = "";
        if(attrs.getNamedItem("numberOfBars") != null)
            numberOfBars = attrs.getNamedItem("numberOfBars").getNodeValue();
            
        String axisOn = "";
        if(attrs.getNamedItem("axisXOn") != null)
            axisOn = attrs.getNamedItem("axisXOn").getNodeValue();
            
        String globalScale = "";
        if(attrs.getNamedItem("globalScale") != null)
            globalScale = attrs.getNamedItem("globalScale").getNodeValue();
            
        newStyle = new BarChart(id, width, height, numberOfBars,axisOn,globalScale);
        
        NodeList childNodes = style.getChildNodes();
        for (int i = 1; i < childNodes.getLength(); i+=2) {
                Node child = style.getChildNodes().item(i);

                if(child.getNodeName().equals("bars"))
                        parseBars(child, newStyle);
        }
        
        //Add the new style properly filled
        managerStyle.addStyle(new TypeSimple("", "BarChart"), newStyle);
    }
            
    private void parsePieChart(Node style, String id, ManagerStyles managerStyle) {
        Style newStyle = null;
        NamedNodeMap attrs = style.getAttributes();
        
        String numberOfSectors = "";
        if(attrs.getNamedItem("numberOfSectors") != null)
            numberOfSectors = attrs.getNamedItem("numberOfSectors").getNodeValue();
            
        String radius = attrs.getNamedItem("radius").getNodeValue();
        NodeList childNodes = style.getChildNodes();
        
        newStyle = new PieChart(id, radius, numberOfSectors);
        
        for (int i = 1; i < childNodes.getLength(); i+=2) {
            Node child = style.getChildNodes().item(i);

            if(child.getNodeName().equals("sectors"))
                    parseSectors(child, newStyle);
        }
        
        //Add the new style properly filled
         managerStyle.addStyle(new TypeSimple("", "PieChart"), newStyle);
    }
            
    private void parseCompositeStyle(Node style, String id, ManagerStyles managerStyle) {
        Style newStyle = new CompositeStyle(id);
        TypeStyle typeStyle = new TypeComposite("");
        
        NodeList childNodes = style.getChildNodes();
        for (int i = 1; i < childNodes.getLength(); i+=2) {
            Node child = style.getChildNodes().item(i);

            NodeList childNodes2 = child.getChildNodes();
            
            for (int j = 1; j < childNodes2.getLength(); j+=2) {
                Node style2 = childNodes2.item(j);
                NamedNodeMap colorAttrs = style2.getAttributes();
                String styleId = colorAttrs.getNamedItem("id").getNodeValue();
                
                Style tempStyle = managerStyle.getStyleById(styleId);                        
                ((CompositeStyle)newStyle).addStyle(tempStyle);
                
                if(!tempStyle.getClass().getSimpleName().equals("CompositeStyle")) {
                    if(tempStyle.getClass().getSimpleName().equals("ContinuosStyle")) {
                        String tempType = ((ContinuosStyle)tempStyle).getVisualProperty().getClass().getSimpleName();
                        ((TypeComposite)typeStyle).addTypeStyle(new TypeSimple("",tempType));
                    }
                    
                    if(tempStyle.getClass().getSimpleName().equals("DiscreteStyle")) {
                        String tempType = ((DiscreteStyle)tempStyle).getVisualProperty().getClass().getSimpleName();
                        ((TypeComposite)typeStyle).addTypeStyle(new TypeSimple("",tempType));
                    }
                    
                    if(tempStyle.getClass().getSimpleName().equals("BarChart")) {
                        ((TypeComposite)typeStyle).addTypeStyle(new TypeSimple("","BarChart"));
                    }
                    
                    if(tempStyle.getClass().getSimpleName().equals("PieChart")) {
                        ((TypeComposite)typeStyle).addTypeStyle(new TypeSimple("","PieChart"));
                    }
                }
                else {
                    
                }
                
            }
        }
        
        //Add the new style properly filled
         managerStyle.addStyle(typeStyle, newStyle);
    }
    
    private Map<String, String> parseTextures(Node child) {
        Map<String, String> result = new HashMap<String, String>();
        NodeList childNodes = child.getChildNodes();

        for (int i = 1; i < childNodes.getLength(); i+=2) {
            Node color = childNodes.item(i);
            NamedNodeMap colorAttrs = color.getAttributes();
            String textureId = colorAttrs.getNamedItem("id").getNodeValue();
            String textureValue = colorAttrs.getNamedItem("textureDescription").getNodeValue();
            result.put(textureId, textureValue);
        }
        
        return result;
    }

    private Map<String, String> parseShapes(Node child) {
        Map<String, String> result = new HashMap<String, String>();
        NodeList childNodes = child.getChildNodes();

        for (int i = 1; i < childNodes.getLength(); i+=2) {
            Node color = childNodes.item(i);
            NamedNodeMap colorAttrs = color.getAttributes();
            String shapeId = colorAttrs.getNamedItem("id").getNodeValue();
            String descriptionShape = colorAttrs.getNamedItem("descriptionShape").getNodeValue();

            result.put(shapeId, descriptionShape);
        }
        
        return result;
    }
    
   private Map<String, String> parseColors(Node child) {
       Map<String, String> result = new HashMap<String, String>();
   
       NodeList childNodes = child.getChildNodes();

       for (int i = 1; i < childNodes.getLength(); i+=2) {
           Node color = childNodes.item(i);
           NamedNodeMap colorAttrs = color.getAttributes();
           String colorId = colorAttrs.getNamedItem("id").getNodeValue();
           String colorValue = colorAttrs.getNamedItem("value").getNodeValue();
           result.put(colorId, colorValue);
       }
       
       return result;
   }
   
    private void parseBars(Node child, Style newStyle) {
        NodeList childNodes = child.getChildNodes();
        for (int i = 1; i < childNodes.getLength(); i+=2) {
            Node color = childNodes.item(i);
            NamedNodeMap barAttrs = color.getAttributes();
            String barColor = barAttrs.getNamedItem("value").getNodeValue();
            String barLabel = "";
            if(barAttrs.getNamedItem("label") != null)
                barLabel = barAttrs.getNamedItem("label").getNodeValue();
                  
           ((BarChart)newStyle).addBar(barLabel, barColor);
        }
        
    }
    
    private void parseSectors(Node child, Style newStyle) {
        NodeList childNodes = child.getChildNodes();
        for (int i = 1; i < childNodes.getLength(); i+=2) {
            Node color = childNodes.item(i);
            NamedNodeMap barAttrs = color.getAttributes();
            String barColor = barAttrs.getNamedItem("value").getNodeValue();
            String barLabel = "";
            if(barAttrs.getNamedItem("label") != null)
                barLabel = barAttrs.getNamedItem("label").getNodeValue();
                  
           ((PieChart)newStyle).addSector(barLabel, barColor);
        }
        
    }
   
    private void parseIntervals(Node intervals, Style newStyle) {
        NodeList intervalsDefinition = intervals.getChildNodes();
        for (int i = 1; i < intervalsDefinition.getLength(); i+=2) {
            Node intervalDefinition = intervalsDefinition.item(i);
            NamedNodeMap intervalAttrs = intervalDefinition.getAttributes();
    
            //Information about the interval
            String label = intervalAttrs.getNamedItem("label").getNodeValue();
            String min = intervalAttrs.getNamedItem("lowerBound").getNodeValue();
            String max = intervalAttrs.getNamedItem("upperBound").getNodeValue();
            
            String id = "";
            if(intervalAttrs.getNamedItem("propertyID") != null) {
                id = intervalAttrs.getNamedItem("propertyID").getNodeValue();
                ((ContinuosStyle)newStyle).addInterval(min, max, label, id);
            }
            else ((ContinuosStyle)newStyle).addInterval(min, max, label);
        }
    }

    private void parseSets(Node child, Style newStyle) {
        NodeList setsDefinition = child.getChildNodes();
        for (int i = 1; i < setsDefinition.getLength(); i+=2) {
                Node setDefinition = setsDefinition.item(i);
                NamedNodeMap intervalAttrs = setDefinition.getAttributes();

                //Information about the set
                String label = "";
                if(intervalAttrs.getNamedItem("label") != null)
                    label = intervalAttrs.getNamedItem("label").getNodeValue();
                
                String allValues = intervalAttrs.getNamedItem("values").getNodeValue();
                
                String[] values = allValues.split(",");

                String id = "";
                if(intervalAttrs.getNamedItem("propertyID") != null) {
                    id = intervalAttrs.getNamedItem("propertyID").getNodeValue();
                    ((DiscreteStyle)newStyle).addDiscreteSet(label, values, id);    
                }
                else ((DiscreteStyle)newStyle).addDiscreteSet(label, values);    
                    
        }
    }

    private void readDecisionTree(Node decisionTree, ManagerStyles managerStyle) {
        Map<String, TypeStyle> temp = new HashMap<String, TypeStyle>();
        
        NodeList childs = decisionTree.getChildNodes();
        for(int i=1; i < childs.getLength(); i+=2) {
            Node node = childs.item(i);
            if(node.getNodeName().equals("typeOfStyles")) {
                NodeList typeOfStyles = node.getChildNodes();
                for(int j = 1; j < typeOfStyles.getLength(); j+=2) {
                    readTypeOfStyle(typeOfStyles.item(j), temp);
                }
            }
            
            if(node.getNodeName().equals("context")) {
              //  System.out.println("Contexto");
                parseContext(managerStyle, node.getChildNodes(), temp);
            }
        }
    }

    private void readTypeOfStyle(Node node, Map<String, TypeStyle> temp) {
      //  System.out.println("Type of style: " + node.getNodeName());
        NamedNodeMap attrs = node.getAttributes();
        String id = attrs.getNamedItem("id").getNodeValue();
        String description = attrs.getNamedItem("description").getNodeValue();
        
        //System.out.println("ID DO TIPO DE ESTILO: " + id);
        
        NodeList childs = node.getChildNodes();
        
        Node child = childs.item(1);
        System.out.println("Value: " + child.getNodeName());
        
        if(child.getNodeName().equals("simpleStyle")) {
            //System.out.println("daskd: " + child.getTextContent());
            temp.put(id, new TypeSimple(description, child.getTextContent()));
        }
            
        if(child.getNodeName().equals("compositeStyle")) {
            TypeStyle type = new TypeComposite(description); 
            
            type = readTypeComposite(type, child.getChildNodes());
            temp.put(id, type);
        }
    }

    private TypeStyle readTypeComposite( TypeStyle type, NodeList compositeWith) {
        
        for(int i = 1; i < compositeWith.getLength(); i+=2) {
            Node composite = compositeWith.item(i);
            if(composite.getNodeName().equals("simpleStyle")) {
                //System.out.println("teste: " + composite.getTextContent());
                ((TypeComposite)type).addTypeStyle(new TypeSimple("",composite.getTextContent()));
            }
            else if(composite.getNodeName().equals("compositeStyle")){
                TypeStyle newType = new TypeComposite("");    
                ((TypeComposite)type).addTypeStyle(readTypeComposite(newType, composite.getChildNodes()));
            }
        }
        
        return type;
    }

    private void parseContext(ManagerStyles managerStyle, NodeList childs, Map<String, TypeStyle> typeOfStyles) {
        Context context = new Context();
        List<TypeStyle> tempTypeOfStyles = new LinkedList<TypeStyle>();
        
        for(int i = 1; i < childs.getLength(); i+=2) {
            Node node = childs.item(i);
          //  System.out.println("Node: " + node.getNodeName());
            if(node.getNodeName().equals("spatialObjects")) {
                parseSpatialObjects(context, node.getChildNodes());
            }
            if(node.getNodeName().equals("numberOfNumericalColumns")) {
                context.setNumberNumericalColumns(Integer.parseInt(node.getTextContent())); ;
            }
            
            if(node.getNodeName().equals("numberOfAlphaNumericColumns")) {
                context.setNumberAlphaNumericalColumns(Integer.parseInt(node.getTextContent()));
            }
            
            if(node.getNodeName().equals("numberOfMeasures"))
                context.setNumberOfMeasures(Integer.parseInt(node.getTextContent()));

            if(node.getNodeName().equals("applicableStyles")) {
                parseApplicableStyles(tempTypeOfStyles, node.getChildNodes(), typeOfStyles);
                managerStyle.addEntryInLookupTable(context, tempTypeOfStyles);        
            }   
        }
    }

    private void parseSpatialObjects(Context context, NodeList childs) {
                                     
        for(int i = 1; i < childs.getLength(); i+=2) {
            Node child = childs.item(i);
            NamedNodeMap attrs = child.getAttributes();
            
            String geometryType = attrs.getNamedItem("geometryType").getNodeValue();
            
            context.addGeometryType(geometryType);
        }
    }

    private void parseApplicableStyles(List<TypeStyle> typeStyles,
                                       NodeList childs,Map<String, TypeStyle> typeOfStyles) {
        for(int i = 1; i < childs.getLength(); i+=2) {
            Node child = childs.item(i);
            NamedNodeMap attrs = child.getAttributes();
            
            String idApplicable = attrs.getNamedItem("id").getNodeValue();
           // System.out.println("ID: " + idApplicable);
            typeStyles.add(typeOfStyles.get(idApplicable));
        }                               
    }
}
