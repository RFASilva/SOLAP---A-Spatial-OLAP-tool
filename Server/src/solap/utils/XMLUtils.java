package solap.utils;

import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLUtils {
    
    public XMLUtils() {
        //System.out.println("Created XMLUtils Object.");
    }
    
    public String docToXml(Document doc) {
        //Serialize DOM
        OutputFormat format = new OutputFormat(doc); 
        // as a String
        StringWriter stringOut = new StringWriter();    
        XMLSerializer serial = new XMLSerializer(stringOut, format);
        // Display the XML
        try {
            serial.serialize(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return stringOut.toString();
    }
    
    public Document XmlToDoc(String xml) {
        // create a factory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        
        try {
            builder = factory.newDocumentBuilder();

            Reader reader = new CharArrayReader(xml.toCharArray());
            return (builder.parse(new org.xml.sax.InputSource(reader)));
        } catch (Exception e) {
            System.out.println("error doind xml to doc: " + e.getMessage());
            return null;
        }
    }
    
    public Document filetoDoc(String filename) {
        try {
            File xmlFile = new File(filename);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(xmlFile);
            
            return doc;
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Document addResponseElement(Document doc, Element element) {
        Node tempNode = doc.importNode(element, true);
        doc.getElementsByTagName("solapplus").item(0).appendChild(tempNode);
        return doc;
    }
    
    public Document addElement(Document doc, Element element, String root) {
        Node tempNode = doc.importNode(element, true);
        doc.getElementsByTagName(root).item(0).appendChild(tempNode);
        return doc;
    }
    
    //ADD TO THE SECOND OCCURRENCE OF THE ROOT
    //USED TO ATTACH INFO TO THE DETAIL TABLE IN SUMMARIZATION PROCESS
    public Document addElement2(Document doc, Element element, String root) {
        Node tempNode = doc.importNode(element, true);
        doc.getElementsByTagName(root).item(1).appendChild(tempNode);
        return doc;
    }
    
    public String getAttributeValue(Document doc, String elementName, String id, String attribute) {
        NodeList sections = doc.getElementsByTagName(elementName);
        for (int i = 0; i < sections.getLength(); ++i) {
                if (((Element) sections.item(i)).getAttribute("id").equals(id)) {
                        Element element = (Element) sections.item(i);
                        return element.getAttribute(attribute);
                }
        }
        //System.out.println("Not found");
        return null;
    }
    
    public String getAttributeValueFirstElement(Document doc, String elementName, String attribute) {
        Element element = (Element)doc.getElementsByTagName(elementName).item(0);
        return element.getAttribute(attribute);
    }
    
    public Element getElement(Document doc, String elementName, String id) {
        NodeList sections = doc.getElementsByTagName(elementName);
        for (int i = 0; i < sections.getLength(); ++i) {
                if (((Element) sections.item(i)).getAttribute("id").equals(id)) {
                        Element element = (Element) sections.item(i);
                        return element;
                }
        }
        //System.out.println("Not found");
        return null;
    }
    
    public Element getElement(Element elem, String elementName, String id) {
        NodeList sections = elem.getElementsByTagName(elementName);
        for (int i = 0; i < sections.getLength(); ++i) {
                if (((Element) sections.item(i)).getAttribute("id").equals(id)) {
                        Element element = (Element) sections.item(i);
                        return element;
                }
        }
        //System.out.println("Not found");
        return null;
    }
    
    public Element getElementByAttribute(Document doc, String elementName, String attribute, String value) {
        NodeList sections = doc.getElementsByTagName(elementName);
        for (int i = 0; i < sections.getLength(); ++i) {
                if (((Element) sections.item(i)).getAttribute(attribute).equals(value)) {
                        Element element = (Element) sections.item(i);
                        return element;
                }
        }
        System.out.println("Not found");
        return null;
    }
    public Element getElementByAttribute(Element elem, String elementName, String attribute, String value) {
        NodeList sections = elem.getElementsByTagName(elementName);
        for (int i = 0; i < sections.getLength(); ++i) {
                if (((Element) sections.item(i)).getAttribute(attribute).equals(value)) {
                        Element element = (Element) sections.item(i);
                        return element;
                }
        }
        //System.out.println("Not found");
        return null;
    }
    
    //add distinct values for a "get_distincts" response
    public Document addDistinctValues(Document doc, Document xmlData) {
        
        //add the distincts root element
        Element root = doc.createElement("distincts");
        Node tempNode = doc.importNode(root, true);
        doc.getElementsByTagName("solapplus").item(0).appendChild(tempNode);
        
        //go through the results
        Element[] array = getAllChildElementNamed(xmlData.getDocumentElement(), "ROW", true);
        int i = 0;
        String value;
        while (i < array.length) {
            //create a <distinct> element and define it's value attribute
            Element element = doc.createElement("distinct");
            //skip null first element (if present)
            if (array[i].getChildNodes().getLength() > 0) {
                value = array[i].getChildNodes().item(0).getFirstChild().getNodeValue();
                element.setAttribute("value", value);
                //add the <distinct> to the <distinctS>
                tempNode = doc.importNode(element, true);
                doc.getElementsByTagName("distincts").item(0).appendChild(tempNode);
            }
            i++;
        }
        
        //System.out.println(docToXml(doc));
        return doc;
    }
    
    public Element[] getAllChildElementNamed(Element parent, String childName, boolean deepSearch) {
       if (parent == null)
       {
         throw new NullPointerException("Parent element cannot be null");
       }
       NodeList children = parent.getChildNodes();
       ArrayList child = new ArrayList();
       for (int i = 0; i < children.getLength(); i++)
       {
         if (children.item(i).getNodeName().equals(childName))
         {
           child.add(children.item(i));
         }
         else if ((deepSearch) && (children.item(i).getNodeType() == Element.ELEMENT_NODE))
         {
           Element[] childs = getAllChildElementNamed((Element)children.item(i), childName, deepSearch);
           for (int j=0; j< childs.length; j++)
           {
             child.add(childs[j]);
           }
         }
       }
       return (Element[])child.toArray(new Element[0]);
    }
    
    public String getLevelByAttribute(Document doc, String attId){
        NodeList levels = doc.getElementsByTagName("level");
        for (int i = 0; i < levels.getLength(); ++i) {
            NodeList attributes = ((Element) levels.item(i)).getElementsByTagName("attribute");
            for (int j = 0; j < attributes.getLength(); ++j) {
                if(((Element) attributes.item(j)).getAttribute("id").equals(attId))
                    return ((Element) levels.item(i)).getAttribute("id");
            }
        }
        //System.out.println("Not found");
        return null;
    }
    
    public String getDimensionByLevel(Document doc, String levelId){
        NodeList dimensions = doc.getElementsByTagName("dimension");
        for (int i = 0; i < dimensions.getLength(); ++i) {
            NodeList levels = ((Element) dimensions.item(i)).getElementsByTagName("level");
            for (int j = 0; j < levels.getLength(); ++j) {
                if(((Element) levels.item(j)).getAttribute("id").equals(levelId))
                    return ((Element) dimensions.item(i)).getAttribute("id");
            }
        }
        //System.out.println("Not found");
        return null;
    }
}
