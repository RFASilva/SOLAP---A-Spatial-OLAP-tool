package solap;

import java.util.Vector;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import solap.entities.SOLAPAttribute;

import solap.params.SOLAPParamsObject;
import solap.params.SOLAPAttributeParams;

import solap.utils.DBUtils;
import solap.utils.MappingUtils;
import solap.utils.XMLUtils;

import solap.sumarization.SumarizationData;

import solap.tablegenerator.PivotTableGenerator;
import solap.tablegenerator.TableGenerator;

public class SOLAPResponse {
    
    SOLAPParamsObject params;
    Document doc;
    String response;
    
    XMLUtils xmlUtils = new XMLUtils();
    
    //To know how man diferent characteristics we have
    private static final String queryToExecute = "SELECT DISTINCT alphaNumeric1 FROM SUMARIZATION_TABLE";
    
    public SOLAPResponse(SOLAPParamsObject params) {
        //initialize the parameters object
        this.params = params;
        //generate base xml for the response
        generateBaseXml();
        //check the call type and process it
        processCall();
    }
    
    public String getXMLResponse() {
        return response;
    }
    
    private void processCall() { 
        //call the required method based on the request call
        if (params.getCall().compareTo("list_cubes") == 0)
            listCubes();
        else if (params.getCall().compareTo("load_cube") == 0)
            loadCube();
        else if (params.getCall().compareTo("get_data") == 0) {
            if(params.isSpatial()) 
                getDataST();
            else getDataDT();
        }
        else if (params.getCall().compareTo("get_distincts") == 0)
            getDistincts();
        else if (params.getCall().compareTo("generalize") == 0)
            summarizationProcess();
        else if(params.getCall().compareTo("get_factTables") == 0)
            getFactTables();
        
        //transform response doc into string
        XMLUtils xmlUtils = new XMLUtils();
        response = xmlUtils.docToXml(doc);
    }
    
    public void getFactTables(){
        XMLUtils xmlUtils = new XMLUtils();
        Element factTables = doc.createElement("facttables");
        Document metadata = xmlUtils.filetoDoc(params.getFilename());
        NodeList cubeList = metadata.getElementsByTagName("cube");
        int size = cubeList.getLength();
        for (int i = 0; i < size; i++) {
            String factTableId = cubeList.item(i).getAttributes().getNamedItem("factTableRef").getNodeValue();
            Element facttable = xmlUtils.getElementByAttribute(metadata,"table","id",factTableId);
            Element ftElement = doc.createElement("facttable");
            ftElement.setAttribute("id",factTableId);
            ftElement.setAttribute("name",facttable.getAttribute("name"));
            factTables.appendChild(ftElement);
        }
        doc = xmlUtils.addResponseElement(doc, factTables);
        this.response = xmlUtils.docToXml(doc);
    }

    public void summarizationProcess(){
        System.out.println("Entrei aqui para realizar o processo de sumarização");
        params.printSummarizationInfo();
        
        // HERE WE WILL CHOOSE BY THE INFORMATION IN THE PARAMS
        // for now we only have "focus"
        String ProcessType = "focus";
        String generalizationType = params.getGeneralizationParams().getGeneralizationType();
        DataRequestProcessor processor = new DataRequestProcessor(params, ProcessType);
        
        String query = processor.buildFocusSumarizationQuery();
        //String query = processor.getQueryFromFile("C:\\Users\\Tiago\\Documents\\Faculdade\\Mestrado\\2º Ano\\Dissertação\\SOLAP\\Server\\Query.txt");
        System.out.println("Query sumarizacao:\n" + query);
        DBUtils dbUtils = new DBUtils();
        Document xmlData = dbUtils.executeQuery(query);
        
        SumarizationData sumarizationData = new SumarizationData(params, xmlData);
        sumarizationData.applyGeneralizationToData();
        String genResult = sumarizationData.getResponse();
        System.out.println("Resposta para tabela de suporte:\n" + genResult);
        String genResultDetail = sumarizationData.getResponseDetail();
        System.out.println("Resposta para tabela de detalhe:\n" + genResultDetail);
        
        Document genResultDoc = xmlUtils.XmlToDoc(genResult);
        int numMeasures = sumarizationData.getMeasuresNoHierarchy_Name().size();
        Vector<String> inLineAttr = sumarizationData.getNonSpatialAttributesIds();
        Vector<String> measuresHierarchies = sumarizationData.getMeasuresWithHierarchy_Name();
        Vector<SOLAPAttributeParams> spatialAttr = new Vector<SOLAPAttributeParams>();
        spatialAttr.add(sumarizationData.getSpatialAttributeGeom());
        int numberOfGroups = sumarizationData.getNumberOfGroups();
        
        String spatialQuery = processor.buildSpatialQueryForSumarization(genResultDoc,numMeasures,inLineAttr,measuresHierarchies,spatialAttr,generalizationType, numberOfGroups, sumarizationData.getObjectsForGroups());
        System.out.println("Query espacial:\n" + spatialQuery);
        String geomType = sumarizationData.getGeomType();
        
        createSumarizationResponseDocument(genResult, genResultDetail, spatialQuery, geomType, numMeasures, numberOfGroups);
        attachSummarizationRowsetMetadata(sumarizationData);
        attachSummarizationDetailRowsetMetadata(sumarizationData,generalizationType);
        
        System.out.println("Terminei aqui de realizar o processo de sumarização");
    }
    
    
    private void createSumarizationResponseDocument(String genResult, String genResultDetail, String spatialQuery, String geomType, int numMeasures, int numberOfGroups){
        System.out.println("A CRIAR O DOCUMENTO FINAL");
        String result = "<solapplus>";
        
        int count = xmlUtils.XmlToDoc(genResult).getDocumentElement().getElementsByTagName("ROW").getLength();
        result += "<table count=\"" + count + "\" nMeasures=\"" + numMeasures + "\">";
        result += genResult +  "</table>";
        
        count = xmlUtils.XmlToDoc(genResultDetail).getDocumentElement().getElementsByTagName("ROW").getLength();
        result += "<table count=\"" + count + "\" nMeasures=\"" + numMeasures + "\">";
        
        result += genResultDetail +  "</table>";
        
        Document queryResult = new DBUtils().executeQuery(queryToExecute);
        int numberOfDistinctCharacteristics = queryResult.getElementsByTagName("ROW").getLength();
        result += "<query sql=\"" + spatialQuery + "\" geometryType=\"" + geomType + "\" numDistinct=\"" + numberOfDistinctCharacteristics + "\" createdGroups=\"" + numberOfGroups + "\"/></solapplus>";
        //result += "<query sql=\"" + spatialQuery + "\" geometryType=\"" + "polygon" + "\" numDistinct=\"" + numberOfDistinctCharacteristics + "\" /></solapplus>";
        System.out.println(result);
        doc = xmlUtils.XmlToDoc(result);
        System.out.println("ACABEI DE CRIAR O DOCUMENTO FINAL");
    }

    public void listCubes() {
        //utility object
        XMLUtils xmlUtils = new XMLUtils();
        
        //load file data, add it to the base response
        Document newDoc = xmlUtils.filetoDoc("cubes.xml");
        doc = xmlUtils.addResponseElement(doc, newDoc.getDocumentElement());
        this.response = xmlUtils.docToXml(doc);
    }
    
    public void loadCube() {
        //utility object
        XMLUtils xmlUtils = new XMLUtils();
        
        //Get cube info from metadata 
        Document metadata = xmlUtils.filetoDoc(params.getFilename());
        String cubeName = xmlUtils.getAttributeValue(metadata, "cube", params.getCubeId(), "name");
        String cubeDescription = xmlUtils.getAttributeValue(metadata, "cube", params.getCubeId(), "description");
        
        //add the <cube> root element and attributes
        Element element = doc.createElement("cube");
        element.setAttribute("id", params.getCubeId());
        element.setAttribute("name", cubeName);
        element.setAttribute("description", cubeDescription);
        Node tempNode = doc.importNode(element, true);
        doc.getElementsByTagName("solapplus").item(0).appendChild(tempNode);
        //System.out.println("Passei aqui 0");
        generateClientMapInfo(xmlUtils, metadata);
        //System.out.println("Passei aqui 1");
        generateStylesInfo(xmlUtils, metadata);
        //System.out.println("Passei aqui 2");
        generateClientDimensionInfo(xmlUtils, metadata);
        //System.out.println("Passei aqui 3");
        generateClientMeasureInfo(xmlUtils, metadata);
        //System.out.println("Passei aqui 4");
        
        this.response = xmlUtils.docToXml(doc);
        
        System.out.println("RESPOSTA LOAD CUBE SERVIDOR: " + this.response);
    }

    //functions to generate metadata sent to client on load_cube operations
    private void generateClientMeasureInfo(XMLUtils xmlUtils, Document metadata) {
        //<cube> element from metadata (to extract child nodes)
        Element cube = xmlUtils.getElement(metadata, "cube", params.getCubeId());
        Element measuresData = (Element)cube.getElementsByTagName("measures").item(0);
        Element measures = (Element)doc.importNode(measuresData, true);
        
        //for each <measure>
        for (int i = 0; i < measures.getElementsByTagName("measure").getLength(); i++) {
            Element temp = (Element)measures.getElementsByTagName("measure").item(i);
            //remove unnecessary info
            temp.removeAttribute("columnRef");
        }
        
        doc = xmlUtils.addElement(doc, measures, "cube");
    }
    
    private void generateClientMapInfo(XMLUtils xmlUtils, Document metadata) {
        //<cube> and <maps> elements from metadata (to extract child nodes)
        Element cube = xmlUtils.getElement(metadata, "cube", params.getCubeId());
        
        //generate base element
        Element maps = doc.createElement("maps");
        
        //add connection info
        Element temp = (Element)mapserverInfo(xmlUtils, metadata);
        Element connectionElement = (Element)doc.importNode(temp, true);
        maps.appendChild(connectionElement);
           
        //generate <basemap> and attributes
        Element basemap = doc.createElement("basemap");
        //set id
        String mapId = cube.getElementsByTagName("basemap").item(0).getAttributes().getNamedItem("mapRef").getNodeValue();
        basemap.setAttribute("id", mapId);
        //set centerX, centerY and zoomLevel
        String value = cube.getElementsByTagName("basemap").item(0).getAttributes().getNamedItem("centerX").getNodeValue();
        basemap.setAttribute("centerX", value);
        value = cube.getElementsByTagName("basemap").item(0).getAttributes().getNamedItem("centerY").getNodeValue();
        basemap.setAttribute("centerY", value);
        value = cube.getElementsByTagName("basemap").item(0).getAttributes().getNamedItem("zoomLevel").getNodeValue();
        basemap.setAttribute("zoomLevel", value);
        //get <map> element
        Element map = xmlUtils.getElement(metadata, "map", mapId);
        //set name, title and srid
        value = map.getAttribute("name");
        basemap.setAttribute("name", value);
        value = map.getAttribute("title");
        basemap.setAttribute("title", value);
        value = map.getAttribute("srid");
        basemap.setAttribute("srid", value);
        
        //attach <layers> to <basemap>
        Element layers_basemap = doc.createElement("layers");
        NodeList layers_list = map.getElementsByTagName("layer");
        int size = map.getElementsByTagName("layer").getLength();
        for (int i = 0; i < size; i++) {
            Element tempElement = doc.createElement("layer");
            value = layers_list.item(i).getAttributes().getNamedItem("layerRef").getNodeValue();
            tempElement.setAttribute("layerRef", value);
            layers_basemap.appendChild(tempElement);
        }
        
        //generate <layers> and respective attributes
        Element layers = doc.createElement("layers");
        layers_list = cube.getElementsByTagName("layer");
        size = cube.getElementsByTagName("layer").getLength();
        for (int i = 0; i < size; i++) {
            Element tempElement = doc.createElement("layer");
            value = layers_list.item(i).getAttributes().getNamedItem("layerRef").getNodeValue();
            tempElement.setAttribute("id", value);
            //get layer title
            String title = xmlUtils.getAttributeValue(metadata, "layer", value, "title");
            tempElement.setAttribute("title", title);
            //get layer name
            String name = xmlUtils.getAttributeValue(metadata, "layer", value, "name");
            tempElement.setAttribute("name", name);
            //get layer label
            String label = xmlUtils.getAttributeValue(metadata, "layer", value, "label");
            tempElement.setAttribute("label", label);
            //get layer object
            String object = xmlUtils.getAttributeValue(metadata, "layer", value, "object");
            tempElement.setAttribute("object", object);
            //get layer columnRef
            String columnRef = xmlUtils.getAttributeValue(metadata, "layer", value, "columnRef");
            tempElement.setAttribute("columnRef", columnRef);
            layers.appendChild(tempElement);
        }
        
        basemap.appendChild(layers_basemap);
        maps.appendChild(basemap);
        maps.appendChild(layers);
        doc = xmlUtils.addElement(doc, maps, "cube");
        
        System.out.println(xmlUtils.docToXml(doc));
    }
    
    private void generateStylesInfo(XMLUtils xmlUtils, Document metadata) {     
        NodeList stylesXML = metadata.getElementsByTagName("styles");
        Node nodeStyles = stylesXML.item(0);

        Node styles = doc.importNode(nodeStyles, true);
        Node raiz = doc.getLastChild();
        raiz.appendChild(styles);
    }
    
    private void generateClientDimensionInfo(XMLUtils xmlUtils, Document metadata) {
        //<cube> element from metadata (to extract child nodes)
        Element cube = xmlUtils.getElement(metadata, "cube", params.getCubeId());
        
        //generate base element
        Element dimensions = doc.createElement("dimensions");
        
        
        //for each <dimension> referenced by this cube
        Element dimensionsData = (Element)cube.getElementsByTagName("dimensions").item(0);
        NodeList dimensionList = dimensionsData.getElementsByTagName("dimension");
        int size = dimensionsData.getElementsByTagName("dimension").getLength();
        for (int i = 0; i < size; i++) {
            Element dimensionElement = doc.createElement("dimension");
            //set dimensionId and name
            String dimensionId = dimensionList.item(i).getAttributes().getNamedItem("dimensionRef").getNodeValue();
            dimensionElement.setAttribute("id", dimensionId);
            //System.out.println("ID: " + dimensionId);
            String value = xmlUtils.getAttributeValue(metadata, "dimension", dimensionId, "name");
            dimensionElement.setAttribute("name", value);
            
            //add <levels>
            Element dimension = addLevelsInfo(xmlUtils, metadata, dimensionElement, dimensionId);

            //add <hierarchies> to <dimension>
            addHierarchiesInfo(dimensionElement, dimension);
            
            //add <dimension> to <dimensions>
            dimensions.appendChild(dimensionElement);
            //System.out.println("terminei dimensao");
        }
        
        doc = xmlUtils.addElement(doc, dimensions, "cube");
    }
    //auxiliary functions for generating metadata sent to client on load_cube operations
    private Node mapserverInfo(XMLUtils xmlUtils, Document metadata) {
        //<cube> element from metadata (to extract child nodes)
        Element cube = xmlUtils.getElement(metadata, "cube", params.getCubeId());
        String mapserverId = cube.getAttributes().getNamedItem("mapserverRef").getNodeValue();
        //add connection information
        Element mapserver = xmlUtils.getElement(metadata, "mapserver", mapserverId);
        Node connection = mapserver.getElementsByTagName("connection").item(0);
        
        return connection;
    }
    private Element addLevelsInfo(XMLUtils xmlUtils, Document metadata, Element dimensionElement, String dimensionId) {
        //add <levels>
        Element dimensionsExtraData = (Element)metadata.getElementsByTagName("multidimensional").item(0);
        Element dimension = xmlUtils.getElement(dimensionsExtraData, "dimension", dimensionId);
        Element levels = (Element)dimension.getElementsByTagName("levels").item(0);
        NodeList levelList = levels.getElementsByTagName("level");
        int sizeLevels = levels.getElementsByTagName("level").getLength();
        //element to be added to the response
        Element levelsElement = doc.createElement("levels");
        for (int j = 0; j < sizeLevels; j++) {
            Element levelElement = doc.createElement("level");
            String levelId = levelList.item(j).getAttributes().getNamedItem("id").getNodeValue();
            levelElement.setAttribute("id", levelId);
            String levelName = levelList.item(j).getAttributes().getNamedItem("name").getNodeValue();
            levelElement.setAttribute("name", levelName);
            String displayAttribute = levelList.item(j).getAttributes().getNamedItem("displayAttribute").getNodeValue();
            levelElement.setAttribute("displayAttribute", displayAttribute);
            
            
            MappingUtils mapUtils = new MappingUtils(params.getFilename(), params.getCubeId());
            
            String type = "";
            Node spatialAttr = levelList.item(j).getAttributes().getNamedItem("spatialAttribute");
            if(spatialAttr != null) {
                type = mapUtils.getAttributeColumnType(spatialAttr.getNodeValue());
                levelElement.setAttribute("spatialAttribute", spatialAttr.getNodeValue());
                levelElement.setAttribute("spatialType", type);
            }
            
            //add <attribute>s
            addAttributesInfo(xmlUtils, levels, levelElement, levelId);
            addPreComputingInfo(xmlUtils, levels, levelElement, levelId);
            
            levelsElement.appendChild(levelElement);
        }
        //add to <levels> to <dimension>
        dimensionElement.appendChild(levelsElement);
        return dimension;
    }
    private void addHierarchiesInfo(Element dimensionElement, Element dimension) {
        Element hierarchies = (Element)dimension.getElementsByTagName("hierarchies").item(0);
        Element hierarchiesElement = (Element)doc.importNode(hierarchies, true);
        dimensionElement.appendChild(hierarchiesElement);
    }
    
    private void addAttributesInfo(XMLUtils xmlUtils, Element levels, Element levelElement, String levelId) {
        //add <attribute>s
        NodeList attributeList = xmlUtils.getElement(levels, "level", levelId).getElementsByTagName("attribute");
        int sizeAttributes = attributeList.getLength();
        for (int k = 0; k < sizeAttributes; k++) {
            Element attributeElement = doc.createElement("attribute");
            //set <attribute> attributes
            String attributeId = attributeList.item(k).getAttributes().getNamedItem("id").getNodeValue();
            String columnRef = attributeList.item(k).getAttributes().getNamedItem("columnRef").getNodeValue();
            String attributeName = attributeList.item(k).getAttributes().getNamedItem("name").getNodeValue();
            
            attributeElement.setAttribute("id", attributeId);
            attributeElement.setAttribute("columnRef", columnRef);
            attributeElement.setAttribute("name", attributeName);
            levelElement.appendChild(attributeElement);
        }
    }
    
    private void addPreComputingInfo(XMLUtils xmlUtils, Element levels, Element levelElement, String levelId) {
        Element level = (Element)xmlUtils.getElement(levels, "level", levelId).getElementsByTagName("preComputing").item(0);
        System.out.println("PRECOMPUTING: " + level);
        if(level != null) {
            Element preComputingElement = (Element)doc.importNode(level, true);
            levelElement.appendChild(preComputingElement);
        }
    }
    
    public void getDataST() {
        DBUtils dbUtils = new DBUtils();
        boolean clustering = true;
        List<String> geometryTypes = null;
        List<String> spatialQueries = null;
        int size = 0 ;
        Document xmlData = null;
        
        if(params.getClusteringParams() == null)
            clustering = false;
        
        //extract clauses from params object
        DataRequestProcessor clauses = new DataRequestProcessor(params, clustering);
        
        //build spatial SQL query
        spatialQueries = clauses.buildSpatialQuery();
        geometryTypes = clauses.getGeometryType();
        size = spatialQueries.size();

        for(int i = 0; i < size; i++) {
            //manipulate sqlQuery to replace '>' and '<' for '&gt;' and '&lt;'
            spatialQueries.set(i, spatialQueries.get(i).replaceAll("<", "&lt;"));
            spatialQueries.set(i, spatialQueries.get(i).replace(">", "&gt;"));
            spatialQueries.set(i, spatialQueries.get(i).replace("&", "&amp;"));
        }
    
        System.out.println(geometryTypes);
        System.out.println("Spatial queries: " + spatialQueries);
        System.out.println(size);
        System.out.println("XML UTILS: " + xmlUtils);

        //attach query to response and geometry type
        for(int i = 0; i < size; i++) {
            System.out.println("GEOMETRY: " + geometryTypes.get(i));
            Document tempdoc = xmlUtils.XmlToDoc("<query sql=\"" + spatialQueries.get(i) + "\" geometryType=\"" + geometryTypes.get(i) + "\"></query>");
            
            System.out.println("SPATIAL QUERIES: " + spatialQueries.get(i));
            System.out.println("RRR: " + tempdoc);
            
            doc = xmlUtils.addResponseElement(doc, tempdoc.getDocumentElement());
            
            System.out.println("ASFASKDASKBFSDKCFSDKNFSDKNSND");
        }
            
        System.out.println("INTEGRACAO 1");
        
        if(clustering) {
            TableGenerator tableGenerator = clauses.getTableGenerator();
            if(tableGenerator.didClustering()) {
                xmlData = tableGenerator.getClusteringData();
            }
            else xmlData = dbUtils.executeQuery(clauses.buildQuery());
        }
        else xmlData = dbUtils.executeQuery(clauses.buildQuery());
        
        System.out.println("PROCURA 3");
        //attach <table> and <rowset> to response
        int numMeasures = params.getMeasureParams().size();
        int count = xmlData.getDocumentElement().getElementsByTagName("ROW").getLength();
        doc = xmlUtils.addResponseElement(doc, xmlUtils.XmlToDoc("<table count=\"" + count + "\" nMeasures=\"" + numMeasures + "\"></table>").getDocumentElement());
        doc = xmlUtils.addElement(doc, xmlData.getDocumentElement(), "table");
        
        //attach table headers information
        attachRowsetMetadata(clauses);

        System.out.println("response: " + xmlUtils.docToXml(doc));
    }
    
    private void getDataDT() {
        DBUtils dbUtils = new DBUtils();
        boolean clustering = true;
        Document xmlData = null;
        
        if(params.getClusteringParams() == null)
            clustering = false;
        
        //extract clauses from params object
        DataRequestProcessor clauses = new DataRequestProcessor(params, clustering);
        
        if(clustering && params.getGroupDetailParams().size() > 0) {
            System.out.println("DETAIL ANTES");
            xmlData = new PivotTableGenerator(clauses).getDetailTable(clauses.buildQuery());
            System.out.println("DETAIL DEPOIS");
        }
        else {
            System.out.println("DETAIL NORMAL");
            xmlData = dbUtils.executeQuery(clauses.buildQuery());
        }
        
        //attach <table> and <rowset> to response
        int numMeasures = params.getMeasureParams().size();
        int count = xmlData.getDocumentElement().getElementsByTagName("ROW").getLength();
        doc = xmlUtils.addResponseElement(doc, xmlUtils.XmlToDoc("<table count=\"" + count + "\" nMeasures=\"" + numMeasures + "\"></table>").getDocumentElement());
        doc = xmlUtils.addElement(doc, xmlData.getDocumentElement(), "table");
        
        //attach table headers information
        attachRowsetMetadata(clauses);
    }

    public void getDistincts() {
        //utility object
        XMLUtils xmlUtils = new XMLUtils();
        DBUtils dbUtils = new DBUtils();
        
        //extract clauses from params object
        DataRequestProcessor clauses = new DataRequestProcessor(params);
        
        //execute query and add results to reply
        Document xmlData = dbUtils.executeQuery(clauses.buildDistinctQuery());
        doc = xmlUtils.addDistinctValues(doc, xmlData);
    }

    //generate base xml response
    private void generateBaseXml() {
        String baseExpression = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                                "<solapplus>" +
                                "<response>" +
                                "</response>" +
                                "</solapplus>";
        
        
        XMLUtils xmlUtils = new XMLUtils();
        doc = xmlUtils.XmlToDoc(baseExpression);
    }

    private void attachRowsetMetadata(DataRequestProcessor clauses) {
        //associated attributes
        doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<associatedAttributes></associatedAttributes>").getDocumentElement(), "table");
        Vector<String> temp = clauses.getAssociatedAttributes();
        Vector<SOLAPAttribute> info = clauses.getAssociatedInfo();
        for (int i = 0; i < temp.size(); i++) {
            doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<attribute dimensionId=\"" + info.elementAt(i).getDimensionId() + "\" levelId=\"" + info.elementAt(i).getLevelId() + "\" id=\"" + info.elementAt(i).getId() + "\" name=\"" + temp.elementAt(i) + "\"></attribute>").getDocumentElement(), "associatedAttributes");
        }
        
        //main response element for attributes
        doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<attributesLevels></attributesLevels>").getDocumentElement(), "table");
        
        //attributes from different dimensions
        doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<differentDimension></differentDimension>").getDocumentElement(), "attributesLevels");
        temp = clauses.getDifferentDimensionNames();
        for (int i = 0; i < temp.size(); i++) {
            doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<attribute name=\"" + temp.elementAt(i) + "\"></attribute>").getDocumentElement(), "differentDimension");
        }
        
        //attributes from the same level
        doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<sameLevel></sameLevel>").getDocumentElement(), "attributesLevels");
        temp = clauses.getSameLevelNames();
        for (int i = temp.size() - 1; i >=0 ; i--) {
            doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<attribute name=\"" + temp.elementAt(i) + "\"></attribute>").getDocumentElement(), "sameLevel");
        }
        
        //attributes from lower level
        doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<lowerLevel></lowerLevel>").getDocumentElement(), "attributesLevels");
        temp = clauses.getLowerLevelNames();
        for (int i = 0; i < temp.size(); i++) {
            doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<attribute name=\"" + temp.elementAt(i) + "\"></attribute>").getDocumentElement(), "lowerLevel");
        }
        
        //attributes from different dimensions
        doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<higherLevel></higherLevel>").getDocumentElement(), "attributesLevels");
        temp = clauses.getHigherLevelNames();
        for (int i = 0; i < temp.size(); i++) {
            doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<attribute name=\"" + temp.elementAt(i) + "\"></attribute>").getDocumentElement(), "higherLevel");
        }
        
        //attributes from different hierarchies
        doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<differentHierarchy></differentHierarchy>").getDocumentElement(), "attributesLevels");
        temp = clauses.getDifferentHierarchyNames();
        for (int i = 0; i < temp.size(); i++) {
            doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<attribute name=\"" + temp.elementAt(i) + "\"></attribute>").getDocumentElement(), "differentHierarchy");
        }
    }
    
    private void attachSummarizationRowsetMetadata(SumarizationData sumarizationData){
        //associated attributes
        doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<associatedAttributes></associatedAttributes>").getDocumentElement(), "table");
        
        Vector<String> names = new Vector<String>();
        names.add(sumarizationData.getSpatialAttributeName());
        
        Vector<SOLAPAttribute> info = new Vector<SOLAPAttribute>();
        Vector<SOLAPAttributeParams> attParams = new Vector<SOLAPAttributeParams>();
        attParams.add(sumarizationData.getSpatialAttribute());
        for(int i=0; i<attParams.size();i++){
            info.add(new SOLAPAttribute(attParams.get(i).getId(),names.get(i), attParams.get(i).getDimensionId(),attParams.get(i).getLevelId()));
        }
       
        for (int i = 0; i < names.size(); i++) {
            doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<attribute dimensionId=\"" + info.elementAt(i).getDimensionId() + "\" levelId=\"" + info.elementAt(i).getLevelId() + "\" id=\"" + info.elementAt(i).getId() + "\" name=\"" + names.elementAt(i) + "\"></attribute>").getDocumentElement(), "associatedAttributes");
        }
        
        //main response element for attributes
        doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<attributesLevels></attributesLevels>").getDocumentElement(), "table");
        
        //attributes from different dimensions
        doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<differentDimension></differentDimension>").getDocumentElement(), "attributesLevels");
        
        //attributes from the same level
        doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<sameLevel></sameLevel>").getDocumentElement(), "attributesLevels");
        
        Vector<String> temp = new Vector<String>();
        for(String s: sumarizationData.getNonSpatialAttributes())
            temp.add(s);
        for(String s : sumarizationData.getMeasuresWithHierarchy_Name())
            temp.add(s);
        for (int i = 0; i < temp.size() ; i++) {
            doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<attribute name=\"" + temp.elementAt(i) + "\"></attribute>").getDocumentElement(), "sameLevel");
        }
        
        //attributes from lower level
        doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<lowerLevel></lowerLevel>").getDocumentElement(), "attributesLevels");
        
        //attributes from different dimensions
        doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<higherLevel></higherLevel>").getDocumentElement(), "attributesLevels");
        
        //attributes from different hierarchies
        doc = xmlUtils.addElement(doc, xmlUtils.XmlToDoc("<differentHierarchy></differentHierarchy>").getDocumentElement(), "attributesLevels");
    }
    
    private void attachSummarizationDetailRowsetMetadata(SumarizationData sumarizationData, String generalizationType){
        //associated attributes
        doc = xmlUtils.addElement2(doc, xmlUtils.XmlToDoc("<associatedAttributes></associatedAttributes>").getDocumentElement(), "table");
        
        Vector<String> names = new Vector<String>();
        names.add(sumarizationData.getSpatialAttributeName());
        //names.addAll(sumarizationData.getNonSpatialAttributes());
        
        Vector<SOLAPAttribute> info = new Vector<SOLAPAttribute>();
        Vector<SOLAPAttributeParams> attParams = new Vector<SOLAPAttributeParams>();
        attParams.add(sumarizationData.getSpatialAttribute());
        //attParams.addAll(sumarizationData.getNonSpatialAtt()); 
        for(int i=0; i<attParams.size();i++){
            info.add(new SOLAPAttribute(attParams.get(i).getId(),names.get(i), attParams.get(i).getDimensionId(),attParams.get(i).getLevelId()));
        }
        
        for (int i = 0; i < names.size(); i++) {
            doc = xmlUtils.addElement2(doc, xmlUtils.XmlToDoc("<attribute dimensionId=\"" + info.elementAt(i).getDimensionId() + "\" levelId=\"" + info.elementAt(i).getLevelId() + "\" id=\"" + info.elementAt(i).getId() + "\" name=\"" + names.elementAt(i) + "\"></attribute>").getDocumentElement(), "associatedAttributes");
        }
        
        //main response element for attributes
        doc = xmlUtils.addElement2(doc, xmlUtils.XmlToDoc("<attributesLevels></attributesLevels>").getDocumentElement(), "table");
        
        //attributes from different dimensions
        doc = xmlUtils.addElement2(doc, xmlUtils.XmlToDoc("<differentDimension></differentDimension>").getDocumentElement(), "attributesLevels");
        
        //attributes from the same level
        doc = xmlUtils.addElement2(doc, xmlUtils.XmlToDoc("<sameLevel></sameLevel>").getDocumentElement(), "attributesLevels");
        
        Vector<String> temp = new Vector<String>();
        for(String s: sumarizationData.getNonSpatialAttributes())
            temp.add(s);
        for(String s : sumarizationData.getMeasuresWithHierarchy_Name())
            temp.add(s);
        // In this case with want that the detailData contains the number of occorrences 
        // but not at the non-spacial dominant generalization
        if(generalizationType.equals("SPATIAL"))
            temp.add("Occurrences");
        for (int i = 0; i < temp.size() ; i++) {
            doc = xmlUtils.addElement2(doc, xmlUtils.XmlToDoc("<attribute name=\"" + temp.elementAt(i) + "\"></attribute>").getDocumentElement(), "sameLevel");
        }
        //attributes from lower level
        doc = xmlUtils.addElement2(doc, xmlUtils.XmlToDoc("<lowerLevel></lowerLevel>").getDocumentElement(), "attributesLevels");
        
        //attributes from different dimensions
        doc = xmlUtils.addElement2(doc, xmlUtils.XmlToDoc("<higherLevel></higherLevel>").getDocumentElement(), "attributesLevels");
        
        //attributes from different hierarchies
        doc = xmlUtils.addElement2(doc, xmlUtils.XmlToDoc("<differentHierarchy></differentHierarchy>").getDocumentElement(), "attributesLevels");

    }
}
