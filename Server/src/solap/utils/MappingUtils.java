package solap.utils;

import java.util.HashMap;
import java.util.Map;

import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.NodeList;

import solap.entities.SOLAPMappedDimension;

public class MappingUtils {
    String filename;
    String cubeId;
    String aggregate;
    
    XMLUtils xmlUtils = new XMLUtils();
    Document metadata;
    
    //constructors
    public MappingUtils (String filename, String cubeId, String aggregate) {
        this.filename = filename;
        this.cubeId = cubeId;
        this.aggregate = aggregate;
        
        metadata = xmlUtils.filetoDoc(filename);
    }
    public MappingUtils (String filename, String cubeId) {
        this.filename = filename;
        this.cubeId = cubeId;
        
        metadata = xmlUtils.filetoDoc(filename);
    }
    
    public String getMeasureColumn(String measureId) {
                
        //get colRef from <measure>
        String colRef = xmlUtils.getAttributeValue(metadata, "measure", measureId, "columnRef");
        
        //get column name
        String colName = xmlUtils.getAttributeValue(metadata, "column", colRef, "name");
        
        return colName;
    }
    
    public String getPrimaryAttributeIdFromLevel(String levelId) {
        Element element = xmlUtils.getElement(metadata, "level", levelId);
        return element.getAttribute("primaryAttribute");
    }
    public String getSortAttributeIdFromLevel(String levelId) {
        Element element = xmlUtils.getElement(metadata, "level", levelId);
        return element.getAttribute("sortAttribute");
    }
    public String getDisplayAttributeIdFromLevel(String levelId) {
        Element element = xmlUtils.getElement(metadata, "level", levelId);
        return element.getAttribute("displayAttribute");
    }
    public String getSpatialAttributeIdFromLevel(String levelId) {
        Element element = xmlUtils.getElement(metadata, "level", levelId);
        return element.getAttribute("spatialAttribute");
    }
    public String getNameLevel(String levelId) {
        Element element = xmlUtils.getElement(metadata, "level", levelId);
        return element.getAttribute("name");
    }
    public String getAttributeColumn(String attributeId) { 
        //get colRef from <attribute>
        String colRef = xmlUtils.getAttributeValue(metadata, "attribute", attributeId, "columnRef");
        
        //get column name
        String colName = xmlUtils.getAttributeValue(metadata, "column", colRef, "name");
        
        return colName;
    }
    public String getAttributeColumnType(String attributeId) { 
        //get colRef from <attribute>
        String colRef = xmlUtils.getAttributeValue(metadata, "attribute", attributeId, "columnRef");
        
        String type = xmlUtils.getAttributeValue(metadata, "column", colRef, "type");
        
        if (type.compareTo("geometry:polygon") == 0)
            return "polygon";
        if (type.compareTo("geometry:point") == 0)
            return "point";
        
        //else
        return "none";
    }
    public String getAttributeName(String attributeId) {
        return xmlUtils.getAttributeValue(metadata, "attribute", attributeId, "name");
    }
    
    public String getKeyColumn(String dimensionId) {
        //get tableRef from <dimension>
        String tableRef = xmlUtils.getAttributeValue(metadata, "dimension", dimensionId, "masterTableRef");
        
        //get <table> element
        Element element = (Element)xmlUtils.getElement(metadata, "table", tableRef).getElementsByTagName("constraints").item(0);
        element = (Element)element.getElementsByTagName("primaryKey").item(0);
        element = (Element)element.getElementsByTagName("column").item(0);
        
        String colRef = element.getAttribute("columnRef");
        
        //return column name
        return xmlUtils.getAttributeValue(metadata, "column", colRef, "name");
    }
    public String getKeyColumnTable(String tableId) {
        
        //get <table> element
        Element element = (Element)xmlUtils.getElement(metadata, "table", tableId).getElementsByTagName("constraints").item(0);
        element = (Element)element.getElementsByTagName("primaryKey").item(0);
        element = (Element)element.getElementsByTagName("column").item(0);
        
        String colRef = element.getAttribute("columnRef");
        
        //return column name
        return xmlUtils.getAttributeValue(metadata, "column", colRef, "name");
    }
    public String getForeignKey(String table1, String table2) {
        Element tableElement = xmlUtils.getElementByAttribute(metadata, "table", "name", table1);
        Element element = (Element)tableElement.getElementsByTagName("constraints").item(0);
        element = xmlUtils.getElementByAttribute(element, "foreignKey", "foreignTableRef", getTableId(table2));
        element = (Element)element.getElementsByTagName("column").item(0);
        
        String keyRef = element.getAttribute("columnRef");
        //System.out.println("KeyRef: " + keyRef);
        
        tableElement = (Element)tableElement.getElementsByTagName("columns").item(0);
        tableElement = xmlUtils.getElementByAttribute(tableElement, "column", "id", keyRef);
        String foreignKey = tableElement.getAttribute("name");
        
        return foreignKey;
    }
    
    public String getTableId(String levelId, String dimensionId) {
        //get table id
        String tableId;
        tableId = xmlUtils.getAttributeValue(metadata, "level", levelId, "tableRef");
        if (tableId == "") {
            System.out.println("ENTREI AQUI!!");
            //tableRef does not exist, use dimension's name as table (star-schema dimension)
            tableId = xmlUtils.getAttributeValue(metadata, "dimension", dimensionId, "masterTableRef");
        }
        return tableId;
    }
    
    public String getTableId2(String levelId, String dimensionId) {
        return xmlUtils.getAttributeValue(metadata, "level", levelId, "tableRef");
    }
    
    public String getDimensionTableId(String dimensionId) {
        //get tableRef from <dimension>
        return xmlUtils.getAttributeValue(metadata, "dimension", dimensionId, "masterTableRef");
    }
    public String getDimensionId(String tableId) {
        Element element = xmlUtils.getElementByAttribute(metadata, "dimension", "masterTableRef", tableId);  
        return element.getAttribute("id");
    }
    public String getTableId(String tableName) {
        Element element = xmlUtils.getElementByAttribute(metadata, "table", "name", tableName);
        return element.getAttribute("id");
    }
    public String getTableName(String tableId) {
        //get table name
        return xmlUtils.getAttributeValue(metadata, "table", tableId, "name");
    }
    
    public String getLayerTableName(String layerId) {
        String tableRef = xmlUtils.getAttributeValue(metadata, "layer", layerId, "tableRef");
        return getTableName(tableRef);
    }
    public String getLayerGeomAttribute(String layerId) {
        String columnRef = xmlUtils.getAttributeValue(metadata, "layer", layerId, "columnRef");
        return xmlUtils.getAttributeValue(metadata, "column", columnRef, "name");
    }
    
    public String getFactTableName(String cubeId) {
        //get table id
        String tableId = xmlUtils.getAttributeValue(metadata, "cube", cubeId, "factTableRef");
        //return fact table name
        return getTableName(tableId);
    }
    public String getAggregateName(String aggregateId) {
        //get table id
        String tableId = xmlUtils.getAttributeValue(metadata, "aggregate", aggregateId, "factTableRef");
        //return fact table name
        return getTableName(tableId);
    }
    
    public String getDimensionName(String id) {
                
        //get tableRef from <dimension>
        String tableRef = xmlUtils.getAttributeValue(metadata, "dimension", id, "masterTableRef");
        
        //get column name
        String tableName = xmlUtils.getAttributeValue(metadata, "table", tableRef, "name");
        
        return tableName;
    }
    
    public String getMeasureOperator(String id) {
        //get aggregation operator (from metadata) (calculated measure only)
        Element element = xmlUtils.getElement(metadata, "measure", id);
        element = (Element)element.getElementsByTagName("aggregationOperators").item(0);    //only one aggregation operator in a calculated measure (0)
        element = (Element)element.getElementsByTagName("calculated").item(0);              //only one aggregation operator in a calculated measure (0)
        
        return element.getAttribute("formula");
    }
    
    //creates a map of (originalDimensionId, DimensionName) for the aggregate in use
    public Map<String, SOLAPMappedDimension> mapDimensions(String aggregateId, boolean isAggregate) {
        Map<String, SOLAPMappedDimension> map = new HashMap<String, SOLAPMappedDimension>();
        
        //get aggregate dimensions
        Element element;
        Element[] dimensions;
        //if aggregate
        if (isAggregate) {
            element = xmlUtils.getElement(metadata, "aggregate", aggregateId);
        }
        //if fact table
        else {
            element = xmlUtils.getElement(metadata, "cube", aggregateId);
            element = (Element)element.getElementsByTagName("dimensions").item(0);
        }
        
        //get all dimensions
        dimensions = xmlUtils.getAllChildElementNamed(element, "dimension", true);
        
        //for each dimension, fill the map with the respective aggregate dimension
        String tableName;
        String attribute;
        for (int i = 0; i < dimensions.length; i++) {
            if (isAggregate) {
                tableName = getDimensionName(dimensions[i].getAttribute("conformDimensionRef"));
                attribute = dimensions[i].getAttribute("conformDimensionRef");
            }
            else {
                tableName = getDimensionName(dimensions[i].getAttribute("dimensionRef"));
                attribute = dimensions[i].getAttribute("dimensionRef");
            }
            
            //creates a new object and adds it to the map
            SOLAPMappedDimension mappedDimension = new SOLAPMappedDimension(tableName, getKeyColumn(attribute));
            map.put(dimensions[i].getAttribute("dimensionRef"), mappedDimension);
        }
        
        return map;
    }
    
    //aggregate related auxiliary functions
    public String getLowestAggregateLevel(String aggregateId, String dimensionId) {
        //get <aggregate> element
        Element element = xmlUtils.getElement(metadata, "aggregate", aggregateId);
        
        //get dimensionId (can be different from main dimension as it is an aggregate)
        element = xmlUtils.getElementByAttribute(element, "dimension", "dimensionRef", dimensionId);
        String newDimensionId = element.getAttribute("conformDimensionRef");
        
        //assuming lowest level of a dimension is always the first in the metadata
        element = xmlUtils.getElement(metadata, "dimension", newDimensionId);
        element = (Element)element.getElementsByTagName("levels").item(0);
        element = (Element)element.getElementsByTagName("level").item(0);
        
        return element.getAttribute("id");
    }
    public Vector<String> getTopAggregates() {
        Vector<String> topAggregates = new Vector<String>();
        
        //get <aggregate> node list
        Element element = xmlUtils.getElement(metadata, "cube", cubeId);
        element = (Element)element.getElementsByTagName("aggregateChildren").item(0);
        NodeList nodes = element.getElementsByTagName("aggregateChild");
        
        //check aggregates and add to vector if top="true"
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getAttributes().getNamedItem("top").getNodeValue().compareTo("true") == 0)
                topAggregates.add(nodes.item(i).getAttributes().getNamedItem("aggregateRef").getNodeValue());
        }
        
        return topAggregates;
    }
    public Vector<String> getChildAggregates(String aggregateId) {
        Vector<String> children = new Vector<String>();
        
        //get <aggregateChild> element
        Element element = xmlUtils.getElementByAttribute(metadata, "aggregateChild", "aggregateRef", aggregateId);
        //get <child> elements
        Element[] elements = xmlUtils.getAllChildElementNamed(element, "child", false);
        //retrieve values
        for (int i = 0; i < elements.length; i++) {
            children.add(elements[i].getAttribute("aggregateRef"));
        }
        
        return children;
    }
    public boolean lowerOrEqual(String lowerLevel, String queryLevel) {
        //System.out.println("Comparing " + lowerLevel + " with " + queryLevel);
        Vector<String> levelsToCheck = new Vector<String>();
        levelsToCheck.add(lowerLevel);
        
        //check child paths
        while (!levelsToCheck.isEmpty()) {
            String level = levelsToCheck.firstElement();
            if ((level.compareTo(queryLevel) == 0) || (xmlUtils.getAttributeValue(metadata, "level", level, "mapsLevelRef").compareTo(queryLevel) == 0))
                return true;
            else {
                Element element = xmlUtils.getElement(metadata, "level", level);
                element = (Element)element.getElementsByTagName("upperLevels").item(0);
                NodeList nodes = element.getElementsByTagName("upperLevel");
            
                //check <upperLevel>s and return true if queryLevel is present
                for (int i = 0; i < nodes.getLength(); i++) {
                    if (nodes.item(i).getAttributes().getNamedItem("levelRef").getNodeValue().compareTo(queryLevel) == 0)
                        return true;
                    else
                        levelsToCheck.add(nodes.item(i).getAttributes().getNamedItem("levelRef").getNodeValue());
                }
            }
            
            levelsToCheck.remove(levelsToCheck.firstElement());
        }
        
        return false;
    }
    
    public String getLevelByAttribute(String attId){
        return xmlUtils.getLevelByAttribute(metadata,attId);
    }
    
    public String getDimensionByLevel(String levelId){
        return xmlUtils.getDimensionByLevel(metadata,levelId);
    }
    
    public String getLevelTable(String levelId){
        return xmlUtils.getAttributeValue(metadata, "level", levelId, "tableRef");
    }

    public String getFilename() {
        return filename;
    }

    public String getCubeId() {
        return cubeId;
    }
}
