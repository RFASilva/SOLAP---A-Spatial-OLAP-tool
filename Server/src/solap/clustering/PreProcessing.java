package solap.clustering;

import pt.uminho.ubicomp.concaveHull.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import oracle.spatial.geometry.JGeometry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pt.uminho.ubicomp.concaveHull.ConcaveHullEngine;

import solap.DataRequestProcessor;
import solap.clustering.spatialAlgorithms.DBScan;
import solap.clustering.spatialAlgorithms.PDBSCAN;
import solap.clustering.support.ClusterObject;
import solap.clustering.support.CollectionSpatialObjects;
import solap.clustering.support.IDatabase;
import solap.clustering.support.InputParams;
import solap.clustering.support.Instance;
import solap.clustering.support.RowElement;
import solap.params.SOLAPClusteringParams;
import solap.params.SOLAPDetailGroupParams;
import solap.utils.ConvexHull;
import solap.utils.DBUtils;
import solap.utils.GeoUtils;
import solap.utils.ITriple;
import solap.utils.MappingUtils;
import solap.utils.XMLUtils;

public class PreProcessing {

    public static final String NAME_GROUP = "Group";

    public enum geometries {POINT, POLYGON, LINE};
    
    private Document xmlData;
    private DataRequestProcessor sqlGenerator;
    
    private XMLUtils xmlUtils;
    private DBUtils dbUtils;

    //Associated the semantic attribute with the spatial object for further aggregations
    private List<CollectionSpatialObjects> spatialObjects;
    private IClusteringData clusteringData;
    private List<IDatabase> clusteredObjects;

    public PreProcessing(Document xmlData, DataRequestProcessor sqlGenerator) {
        this.xmlData = xmlData;
        this.sqlGenerator = sqlGenerator;
        this.xmlUtils = new XMLUtils();
        this.dbUtils = new DBUtils();
        this.spatialObjects = new LinkedList<CollectionSpatialObjects>();
        this.clusteringData = new ClusteringData();
        this.clusteredObjects = new LinkedList<IDatabase>();
    }

    public void initializeProcess() {
        int numberSpatialObjects = sqlGenerator.getAssociatedAttributes().size();
        int startindex = sqlGenerator.getParams().getMeasureParams().size();
        
        for(int i = 0; i < numberSpatialObjects; i++) {
            String nameLevel = sqlGenerator.getNameLevelByIndex(i);
            CollectionSpatialObjects objs = null;
            ITriple<String, String, String> ids = sqlGenerator.getParams().getClusteringParams().getInfoBySpatialLevel(nameLevel);
        
            //if ids != null we have a clustering based on spatial hierarchies
            if(ids == null) {
                objs = dbUtils.getDistinctsSpatialObjects(sqlGenerator.computeSpatialQueryByIndex(i, "", ""), sqlGenerator.getHigherLevelNames().size());
            }
            else {
                String geometry = sqlGenerator.getGeometrySpatialByIndex(i);
                if(geometry.equals("polygon") && numberSpatialObjects == 2) {
                    sqlGenerator.centroidObject(i);
                }
                
               objs = dbUtils.getDistinctsSpatialObjects(sqlGenerator.computeSpatialQueryByIndex(i, ids.getFirst(), ids.getSecond()), sqlGenerator.getHigherLevelNames().size());
            }   
                
            objs.setLevelName(nameLevel);
            spatialObjects.add(objs);
        }
    }
    
    public boolean applyClusteringAlgorithm(String geometryType, int numberSpatialObjects, SOLAPClusteringParams params) {
        
        //Spatial Clustering with 2 spatial objects (Based on point spatial clustering)
        boolean doClustering = false;
        boolean discoverSpatialDispersion = false;
        if(params.getMode().equals("AUTO"))
            discoverSpatialDispersion = true;
        
        if(numberSpatialObjects == 2) {
            for(int i = 0; i < numberSpatialObjects; i++) {
                boolean autoClustering = doClustering(i, discoverSpatialDispersion);
                doClustering |= autoClustering;
                
                if(autoClustering) {
                    InputParams parameters =  new InputParams();
                    double eps = DBScan.computeEps(spatialObjects.get(i), params.getGroups(), params.getVariant());
                    
                    String baseZoom = getBaseZoomLevel();
                    String zoom = sqlGenerator.getParams().getClusteringParams().getZoom();
                    
                    /* int baseZ = Integer.parseInt(baseZoom);
                    int z = Integer.parseInt(zoom);
                    
                    //TODO: trabalhar mais sobre isto
                    double factor = (Math.abs(baseZ - z) + 0.0) / (10 + 0.0);
                    eps = eps * (1.5 + factor); */
                    
                    parameters.addParameter(eps*1.35);
                    parameters.addParameter(zoom);
                    parameters.addParameter(baseZoom);
                    clusteredObjects.add(clusteringData.applySpatialPointAlgorithm(IClusteringData.pointAlgorithm.DBSCAN, parameters, spatialObjects.get(i)));
                }
                else {
                    clusteredObjects.add(new DBScan().initializeDatabase(spatialObjects.get(i)));
                }
            }
            if(!doClustering)
                return false;
        }
        
        //Point spatial clustering
        else if(geometryType.toUpperCase().equals(geometries.POINT.toString())) {
            boolean autoClustering = doClustering(0,discoverSpatialDispersion);
            doClustering |= autoClustering;
            if(autoClustering) {
                InputParams parameters = new InputParams();
                
                double eps = DBScan.computeEps(spatialObjects.get(0), params.getGroups(), params.getVariant());

                parameters.addParameter(eps);
                parameters.addParameter(sqlGenerator.getParams().getClusteringParams().getZoom());
                parameters.addParameter(getBaseZoomLevel());
                clusteredObjects.add(clusteringData.applySpatialPointAlgorithm(IClusteringData.pointAlgorithm.DBSCAN, parameters, spatialObjects.get(0)));
            }
            else return false;
        }
        //Polygon spatial clustering
        else if(geometryType.toUpperCase().equals(geometries.POLYGON.toString())) {
            boolean autoClustering = doClustering(0, discoverSpatialDispersion);
            doClustering |= autoClustering;
            if(autoClustering) {

                String query = buildQuery(sqlGenerator.getMapUtils(), params);
                Map<ITriple<String,String,String>, Double> distancesBetweenPolygons = dbUtils.getPreComputedPolygonsDistances(query);
                spatialObjects.get(0).setDistancesBetweenInstances(distancesBetweenPolygons);
                
                InputParams parameters = new InputParams();
                double eps = PDBSCAN.computeEps(spatialObjects.get(0), params.getGroups(), params.getVariant());

                parameters.addParameter(eps);
                parameters.addParameter(sqlGenerator.getParams().getClusteringParams().getZoom());
                parameters.addParameter(getBaseZoomLevel());
                clusteredObjects.add(clusteringData.applySpatialPolygonAlgorithm(IClusteringData.polygonAlgorithm.PDBSCAN, parameters, spatialObjects.get(0)));
                
                //debug
                /*Iterator<Map.Entry<String, List<Instance>>> it =  clusteredObjects.get(0).getIteratorSpatialObjectsByGroup();
                while(it.hasNext()) {
                    Map.Entry<String, List<Instance>> next = it.next();
                    System.out.println("Semantic: " + next.getKey());
                    for(Instance i: next.getValue())
                        System.out.print(", " + i.getSemancticAttribute());
                }*/
            }
            else return false;
        }
        
        return true;
    }
    
    public Document transformXMLData(List<String> associatedAttr, Map<String, String> measuresOperators) {
        //All cases of interaction with one spatial object (point or polygon); For now the spatial object line is not considered yet in this approach
        if(associatedAttr.size() == 1) {
            //One Spatial Object
            return transformXMLDataOneSpatial(associatedAttr, measuresOperators, false, null);
        }
        
        //All situations with two spatial objects
        if(associatedAttr.size() == 2) {
            //Two Spatial Objects
            return transformXMLDataTwoSpatial(associatedAttr, measuresOperators);
        }
        
        return null;
    }
    
    
    private Document transformXMLDataOneSpatial(List<String> associatedAttr,
                                                Map<String, String> measuresOperators, boolean detail, Vector<SOLAPDetailGroupParams> groupsParams) {
        //Initiate a rowset
        Document doc = initializeRowSet();
        
        //Information about cluster id for each semantic attribute
        int numberOfMeasures = sqlGenerator.getParams().getMeasureParams().size();
        String attr = associatedAttr.get(0);
        
        //For each group store the different rows for further aggregations
        Map<String, List<RowElement>> newRows = new HashMap<String, List<RowElement>> ();
        Node rowset = xmlData.getFirstChild();
        Element rowasdset = xmlData.getDocumentElement();
        NodeList rows = rowset.getChildNodes();
        
        for(int i = 0; i < rows.getLength(); i++) {
            Node row = rows.item(i);
            NodeList list = row.getChildNodes();
            
            Element roasdw = (Element)rowasdset.getElementsByTagName("ROW").item(i);
            NodeList semanticList = roasdw.getElementsByTagName(attr.toUpperCase());
            String semanticAttr = semanticList.item(0).getTextContent();
           
            int groupID = -1;
            if(clusteredObjects.get(0).getClusterBySemanticAttr(semanticAttr) != null) {
                groupID = clusteredObjects.get(0).getClusterBySemanticAttr(semanticAttr).getClusterLabel();
            }
           
            
            String groupIDString = NAME_GROUP + groupID;
            if(detail) {
                if(groupsParams != null) {
                    if(!groupIDString.equals(groupsParams.get(0).getGroupId()))  {
                        continue;    
                    }
                }
            }
            
            //If element dont belong to any group add xml element in natural way
            if( groupID == ClusterObject.NOISE || groupID == ClusterObject.UNCLASSIFIED) {     
                Node tempRow = doc.importNode(row, true);
                doc.getFirstChild().appendChild(tempRow);
                continue;
            }
            
            //If element belong to any group is necessary aggregate data
            RowElement newElement = new RowElement(numberOfMeasures);
            
            String compareId = "";
            for(int j=0; j < list.getLength(); j++) {
                // RowElement
                if(list.item(j).getNodeName().equals(semanticList.item(0).getNodeName())) {
                    if(!detail) {
                        compareId = groupID+"";
                        newElement.addToCompareElement(groupID+"");
                        newElement.addElement(list.item(j).getNodeName(), NAME_GROUP + groupID);
                    }
                    else {
                        if(groupsParams.get(0).getDetail().equals("false")) {
                            compareId = groupID+"";
                            newElement.addToCompareElement(groupID+"");
                            newElement.addElement(list.item(j).getNodeName(), NAME_GROUP + groupID);
                        }
                        else {
                            compareId = list.item(j).getTextContent();
                            newElement.addToCompareElement(list.item(j).getTextContent());
                            newElement.addElement(list.item(j).getNodeName(), list.item(j).getTextContent());
                        }
                    }
                }
                else newElement.addElement(list.item(j).getNodeName(), list.item(j).getTextContent());
                
                addCompareElement(newElement, list);
            }
            
            if (newRows.get(compareId+"") == null)
                newRows.put(compareId+"", new LinkedList<RowElement>());
            
            int size = newRows.get(compareId+"").size();
            boolean alreadyExist = false;
            for(int w = 0; w < size; w++) {
                if(newElement.equals(newRows.get(compareId+"").get(w))) {
                    newRows.get(compareId+"").get(w).aggregate(newElement, measuresOperators);
                    alreadyExist = true;
                }
            }
        
            if(!alreadyExist)
                newRows.get(compareId+"").add(newElement);
        }
        
        //Write new xml rows associated to the groups
        addNewRows(newRows, doc);
        
        return doc;
    }
    
    private Document transformXMLDataTwoSpatial(List<String> associatedAttr,
                                                Map<String, String> measuresOperators) {
        
        //Initiate a rowset
        Document doc = initializeRowSet();
        
        //Information about cluster id for each semantic attribute
        int numberOfMeasures = sqlGenerator.getParams().getMeasureParams().size();
        String attr1 = associatedAttr.get(0);
        String attr2 = associatedAttr.get(1);
        
        //For each group store the different rows for further aggregations
        Map<String, List<RowElement>> newRows = new HashMap<String, List<RowElement>> ();
        Node rowset = xmlData.getFirstChild();
        Element rowasdset = xmlData.getDocumentElement();
        NodeList rows = rowset.getChildNodes();
        
        for(int i = 0; i < rows.getLength(); i++) {
            Node row = rows.item(i);
            NodeList list = row.getChildNodes();
            
            Element roasdw = (Element)rowasdset.getElementsByTagName("ROW").item(i);
            NodeList semanticList1 = roasdw.getElementsByTagName(attr1.toUpperCase());
            NodeList semanticList2 = roasdw.getElementsByTagName(attr2.toUpperCase());
            String semanticAttr1 = semanticList1.item(0).getTextContent();
            String semanticAttr2 = semanticList2.item(0).getTextContent();
            
            int groupID1 = clusteredObjects.get(0).getClusterBySemanticAttr(semanticAttr1).getClusterLabel();
            int groupID2 = clusteredObjects.get(1).getClusterBySemanticAttr(semanticAttr2).getClusterLabel();
            
            //If element dont belong to any group add xml element in natural way
            if( (groupID1 == ClusterObject.NOISE || groupID1 == ClusterObject.UNCLASSIFIED) && (groupID2 == ClusterObject.NOISE || groupID2 == ClusterObject.UNCLASSIFIED)) {
                Node tempRow = doc.importNode(row, true);
                doc.getFirstChild().appendChild(tempRow);
              //  System.out.println(xmlUtils.docToXml(doc));
                continue;
            }
                
            //If element belong to any group is necessary aggregate data
            RowElement newElement = new RowElement(numberOfMeasures);
            
            String groupID ="";
            if(groupID1 != ClusterObject.NOISE)
                groupID += groupID1;
            else groupID += semanticAttr1;
            
            if(groupID2 != ClusterObject.NOISE)
                groupID += "," + groupID2;
            else groupID += "," + semanticAttr2;             
            
            newElement.addToCompareElement(groupID);
            
            for(int j=0; j < list.getLength(); j++) {    
                // RowElement
                if(list.item(j).getNodeName().equals(semanticList1.item(0).getNodeName()) && (groupID1 != ClusterObject.NOISE && groupID1 != ClusterObject.UNCLASSIFIED))  
                    newElement.addElement(list.item(j).getNodeName(), NAME_GROUP + groupID1);
                else if(list.item(j).getNodeName().equals(semanticList2.item(0).getNodeName()) && (groupID2 != ClusterObject.NOISE && groupID2  != ClusterObject.UNCLASSIFIED)) {
                    newElement.addElement(list.item(j).getNodeName(), NAME_GROUP + groupID2);
                }
                else 
                    newElement.addElement(list.item(j).getNodeName(), list.item(j).getTextContent());
                
               addCompareElement(newElement, list);
            }
            
            if (newRows.get(groupID) == null)
                newRows.put(groupID, new LinkedList<RowElement>());
            
            int size = newRows.get(groupID).size();
            boolean alreadyExist = false;
            for(int w = 0; w < size; w++) {
                if(newElement.equals(newRows.get(groupID).get(w))) {
                    newRows.get(groupID).get(w).aggregate(newElement, measuresOperators);
                    alreadyExist = true;
                }
            }
        
            if(!alreadyExist)
                newRows.get(groupID).add(newElement);
        }
        
        //Write new xml rows associated to the groups
        addNewRows(newRows, doc);
        
        return doc;
    }
    
    public Document detailGroupXMLData(Vector<SOLAPDetailGroupParams> groupsParams, List<String> associatedAttr, Map<String, String> measuresOperators) {
        boolean necessarySlice = false;
        for(SOLAPDetailGroupParams params: groupsParams) {
            if(!params.getValue().equals("")) {
                necessarySlice = true;
                break;
            }
        }
        
        if(associatedAttr.size()== 1 || !necessarySlice) {
            //return transformXMLDataOneSpatial(associatedAttr, measuresOperators, true, groupsParams);
            return detailGroupXMLWithouSlice(groupsParams, associatedAttr);
        }
        else {
            System.out.println("Detail Group WITH SLICE");
            return detailGroupXMLWithSlice(groupsParams, associatedAttr);
        }
        
    }
    
    private Document detailGroupXMLWithouSlice(Vector<SOLAPDetailGroupParams> groupsParams, List<String> associatedAttr){
        //Initiate a rowset
        Document doc = initializeRowSet();
        
        //For each group store the different rows for further aggregations
        Node rowset = xmlData.getFirstChild();
        Element rowasdset = xmlData.getDocumentElement();
        NodeList rows = rowset.getChildNodes();

        for(int i = 0; i < rows.getLength(); i++) {
            Node row = rows.item(i);
            Element elementRow = (Element)rowasdset.getElementsByTagName("ROW").item(i);
            
            boolean addElement = false;
            for(int j = 0; j < groupsParams.size(); j++) {
                String attr = associatedAttr.get(j);
                NodeList semanticList = elementRow.getElementsByTagName(attr.toUpperCase());
                String semanticAttr = semanticList.item(0).getTextContent();
                
                if(clusteredObjects.get(j).getClusterBySemanticAttr(semanticAttr) == null)
                    continue;
                
                String groupID = NAME_GROUP + clusteredObjects.get(j).getClusterBySemanticAttr(semanticAttr).getClusterLabel();
                
                if(groupID.equals(groupsParams.get(j).getGroupId())) addElement = true;
            }
            
            //If element belong to a group add xml element in natural way
            if(addElement) {
                Node tempRow = doc.importNode(row, true);
                doc.getFirstChild().appendChild(tempRow); 
            }
        }
        
        System.out.println("XML: " + xmlUtils.docToXml(doc));
        
        return doc;
    }
    
    private Document detailGroupXMLWithSlice(Vector<SOLAPDetailGroupParams> groupsParams,
                                         List<String> associatedAttr) {
        //Initiate a rowset
        Document doc = initializeRowSet();
        
        //For each group store the different rows for further aggregations
        Node rowset = xmlData.getFirstChild();
        Element rowasdset = xmlData.getDocumentElement();
        NodeList rows = rowset.getChildNodes();

        for(int i = 0; i < rows.getLength(); i++) {
            Node row = rows.item(i);
            Element elementRow = (Element)rowasdset.getElementsByTagName("ROW").item(i);
            
            boolean addElement = true;
            for(int j = 0; j < associatedAttr.size(); j++) {
                String attr = associatedAttr.get(j);
                NodeList semanticList = elementRow.getElementsByTagName(attr.toUpperCase());
                String semanticAttr = semanticList.item(0).getTextContent();
                
                if(!groupsParams.get(j).getValue().equals("")) {
                    if(semanticAttr.equals(groupsParams.get(j).getValue()))
                        addElement &= true;
                    else addElement = false;
                    continue;
                }
                else if(clusteredObjects.get(j).getClusterBySemanticAttr(semanticAttr) == null)
                    continue;
                
                
                String groupID = NAME_GROUP + clusteredObjects.get(j).getClusterBySemanticAttr(semanticAttr).getClusterLabel();
                
                if(groupID.equals(groupsParams.get(j).getGroupId())) addElement &= true;
                else addElement &= false;
            }
            
            //If element belong to a group add xml element in natural way
            if(addElement) {
                Node tempRow = doc.importNode(row, true);
                doc.getFirstChild().appendChild(tempRow); 
            }
        }
        
        System.out.println("XML: " + xmlUtils.docToXml(doc));
        return doc;
    }

    
    //Return for each group the new spatial object
    public List<Map<String, JGeometry>> buildNewSpatialObjects(String geometryType) {
        List<Map<String, JGeometry>> result = new LinkedList<Map<String, JGeometry>>();
    
        //Polygon spatial clustering
        if(geometryType.toUpperCase().equals(geometries.POLYGON.toString())) {
            /*
             * Possible Solution
             * 
             * Map<String, JGeometry> newObjects = new HashMap<String, JGeometry>();
            
            //Initialize structures Adjacency table
            Map<String, List<String>> adjacency = new HashMap<String, List<String>>(spatialObjects.get(0).getAllInstances().size()*4);
            Map<String, Instance> P = new HashMap<String, Instance>(spatialObjects.get(0).getAllInstances().size()*2);
            GeoUtils.fillAdjacencyTable(adjacency, spatialObjects.get(0).getAllInstances(), P);
        
            //Apply union of polygons
            Iterator<Map.Entry<String, List<Instance>>> it = clusteredObjects.get(0).getIteratorSpatialObjectsByGroup();
            while(it.hasNext()) {
                Map.Entry<String, List<Instance>> next = it.next();
                Map<String, Instance> S = new HashMap<String, Instance>();
                for(Instance i: next.getValue())
                    S.put(i.getSemancticAttribute(), i);
                    
                newObjects.put(next.getKey(), GeoUtils.computeUnionOfPolygons(P, S, adjacency));
            }
            
            result.add(newObjects);
            
            return result;*/
            
            Map<String, JGeometry> newObjects = buildUnionOfPolygons();
            result.add(newObjects);
            return result;
        }
        else if(geometryType.toUpperCase().equals(geometries.POINT.toString()) || sqlGenerator.getAssociatedAttributes().size() == 2) {
            
            for(int i = 0; i < sqlGenerator.getAssociatedAttributes().size(); i++) {
                Map<String, JGeometry> newObjects = new HashMap<String, JGeometry>();
                Iterator<Map.Entry<String, List<Instance>>> it = clusteredObjects.get(i).getIteratorSpatialObjectsByGroup();
                
                while(it.hasNext()) {
                    Map.Entry<String, List<Instance>> next = it.next();
                    newObjects.put(next.getKey(), GeoUtils.computeCentroidOfPoints(next.getValue()));
                }
                
                result.add(newObjects);
            }
            return result;
        }
        
        return null;
    }
    
    private Map<String, JGeometry> buildUnionOfPolygons() {
        String nameTable = "clustering_polygons";
        
        dbUtils.executeQuery("drop table " + nameTable);
        //create new temporary table
        String query = "create table " + nameTable + " (temp_id number primary key";
        query += ", groupId varchar2(200), geom SDO_GEOMETRY)";
        dbUtils.executeQuery(query);
        dbUtils.insertPolygons(clusteredObjects.get(0).getSpatialObjectsGroup());   
        
        return dbUtils.getUnionPolygons();
    }

    
    private Document initializeRowSet() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document doc;
        try {
            docBuilder = factory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            Element e = doc.createElement("ROWSET");
            doc.appendChild(e);
            return doc;
        } catch (Exception e) {
            System.out.println("Error initializing the rowset for creating rows based on spatial clustering: " + e.getMessage());
        }
        
        return null;
    }

    private void addNewRows(Map<String, List<RowElement>> newRows,
                            Document doc) {
        for(List<RowElement> listRows: newRows.values()) {
            for(RowElement rowElement: listRows) {
                rowElement.toXML(doc);
            }
        }
    }
    
    protected void debug(Map<String, ClusterObject> objects) {
        for(ClusterObject o: objects.values()) {
            System.out.println("Semantic Attribute: " + o.getInstance().getSemancticAttribute() + " ClusterID: " + o.getClusterLabel());
        }
    }
    
    public List<IDatabase> getClusteredObjects() {
        return clusteredObjects;
    }
    
    public String getBaseZoomLevel() {
        Document metadata = xmlUtils.filetoDoc(sqlGenerator.getParams().getFilename());
        Element cube = xmlUtils.getElement(metadata, "cube", sqlGenerator.getParams().getCubeId());
        String value = cube.getElementsByTagName("basemap").item(0).getAttributes().getNamedItem("zoomLevel").getNodeValue();
        
        return value;
    }
    
    private boolean doClustering(int i, boolean discoverSpatialDispersion) {
        //TODO: Heuristic
        if(spatialObjects.get(i).numberInstances() == 1)
            return false;
        return true;
    }
    
    private String buildQuery(MappingUtils mappingUtils, SOLAPClusteringParams params) {
        String tableName = mappingUtils.getTableName(params.getTableId());
        return "select * from " + tableName;
    }
    
    public void createPolygonRepresentation() {
        createAuxPolygonRepresentation();
        
        List<Vector<Point>> polygons1 = new LinkedList<Vector<Point>>();
        List<Vector<Point>> polygons2 = new LinkedList<Vector<Point>>();
        
        //Build the polygon representation and insert in auxiliary table
        for(int i = 0; i < sqlGenerator.getAssociatedAttributes().size(); i++) {
            Iterator<Map.Entry<String, List<Instance>>> it = clusteredObjects.get(i).getIteratorSpatialObjectsByGroup();
            while(it.hasNext()) {
                Map.Entry<String, List<Instance>> next = it.next();
                
                Vector<Point> array = new Vector<Point>(next.getValue().size());
                for (Instance point: next.getValue())
                    array.add(new Point(point.getPoint().getX(), point.getPoint().getY()));
                
                //ArrayList<Point2D> polygonRepresentation;
                //Quick Hull Approach: Generates a convex polygon
                //ArrayList<Point2D> polygonRepresentation = ConvexHull.quickHull(array);
                
                //Concave Hull Approach: Generates a concave polygon
                
                int k1 = 3; //Define the inicial K value
                ConcaveHullEngine e1 = new ConcaveHullEngine(); // Create a new engine
                
                Vector<Point> polygonRepresentation = new Vector<Point>();
                if(array.size() == 3)
                    polygonRepresentation = ConvexHull.quickHull(array);
                else
                    polygonRepresentation = e1.doConcaveHull(array, k1); //Calls the service and gets the results
                
                //System.out.println("System.out.println: "+array.size() + " i: " + i);
                //System.out.println(polygonRepresentation);
                
                if(i == 0) polygons1.add(polygonRepresentation);
                else polygons2.add(polygonRepresentation);
            }
        }
        
        dbUtils.insertGroupsPolygons(polygons1, polygons2, sqlGenerator.getAssociatedAttributes().size());
    }
    
    private void createAuxPolygonRepresentation() {

        //Create auxiliary table
        String nameTable = "polygon_point_group";
        dbUtils.executeQuery("drop table " + nameTable);

        String query = "create table " + nameTable + " (temp_id1 number, polygon1 SDO_GEOMETRY";
        
        if(sqlGenerator.getAssociatedAttributes().size() == 2)
            query += ", temp_id2 number, polygon2 SDO_GEOMETRY";
        
        query += ")";
        dbUtils.executeQuery(query);
    }
    
    private void addCompareElement(RowElement element, NodeList row) {
        addCompareElement(element, row, sqlGenerator.getLowerLevelNames());
        addCompareElement(element, row, sqlGenerator.getHigherLevelNames());
        addCompareElement(element, row, sqlGenerator.getSameLevelNames());
        addCompareElement(element, row, sqlGenerator.getDifferentDimensionNames());
    }
    
    private void addCompareElement(RowElement element, NodeList row, Vector<String> vector) {
        for(String attr: vector) {
            for(int i=0; i < row.getLength(); i++ ) {
                if(attr.toUpperCase().equals(row.item(i).getNodeName()))
                    element.addToCompareElement(row.item(i).getTextContent());
            }
        }
    }
}
