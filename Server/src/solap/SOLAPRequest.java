package solap;

import java.util.Vector;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import solap.entities.SOLAPFactTable;
import solap.entities.SumarizationAttribute;

import solap.entities.SumarizationMeasure;

import solap.params.SOLAPAttributeParams;
import solap.params.SOLAPClusteringParams;
import solap.params.SOLAPDetailGroupParams;
import solap.params.SOLAPDistinctsParams;
import solap.params.SOLAPFieldFilterParams;
import solap.params.SOLAPGeneralizationParams;
import solap.params.SOLAPGeometricSlicesParams;
import solap.params.SOLAPLevelParams;
import solap.params.SOLAPMeasureFiltersParams;
import solap.params.SOLAPMeasureParams;
import solap.params.SOLAPNFiltersParams;
import solap.params.SOLAPParamsObject;
import solap.params.SOLAPSliceParams;
import solap.params.SOLAPSpatialSliceParams;

import solap.utils.XMLUtils;

public class SOLAPRequest {
    
    //parameters object
    private SOLAPParamsObject paramsObject = new SOLAPParamsObject();
    
    public SOLAPRequest(Document doc) {
        //define call
        Node node = doc.getElementsByTagName("request").item(0);
        paramsObject.setCall(node.getAttributes().getNamedItem("call").getTextContent());
        
        //get main parameters (cubeId, filename, spatial) if present
        extractMainParameters(doc);
        
        //get extra parameters
        extractExtraParameters(doc);
    }

    private void extractMainParameters(Document doc) {    
        Node node;
        String stub;
        //define possible parameters
       
       // System.out.println("OBTER PRINCIPAIS PARAMETROS");
        
        node = doc.getElementsByTagName("params").item(0);
        if (node != null) {
        
            //check for cubeId
            Node node2 = node.getAttributes().getNamedItem("cubeId");
            if (node2 != null) {
                stub = node2.getTextContent();
                //System.out.println("CUBO_ID: " + stub);
                if (stub != null)
                    paramsObject.setCubeId(stub);
            }
            
            //check for filename
            stub = node.getAttributes().getNamedItem("filename").getTextContent();
            if (stub != null)
                paramsObject.setFilename(stub);
            
            //System.out.println("FILENAME: " + stub);
            
            //check for spatial
            node2 = node.getAttributes().getNamedItem("spatial");
            if (node2 != null) {
                stub = node2.getTextContent();
                if (stub != null) {
                    if (stub.compareTo("true") == 0)
                        paramsObject.setSpatial(true);
                    else
                        paramsObject.setSpatial(false);
                }
            }
            
        }
    }

    private void extractExtraParameters(Document doc) {
        //prepare the main element
        Element docElements = doc.getDocumentElement();
        NodeList temp = docElements.getElementsByTagName("request");
        Element request = (Element)temp.item(0);
         
        //extract the parameters based on the call
        //list_cubes and load_cube take no extra parameters
        if (paramsObject.getCall().compareTo("get_data") == 0) {
            extractSliceParams(request);
            extractMeasureParams(request);
            
            //TODO other calls
            extractLevelParams(request);
            extractAttributeParams(request);
            extractFieldFilterParams(request);
            extractMeasureFilterParams(request);
            extractNFilterParams(request);
            extractSpatialSliceParams(request);
            extractGeometricSliceParams(request);
            extractClusteringParams(request);
            extractDetailGroupsParams(request);
        }
        
        else if (paramsObject.getCall().compareTo("get_distincts") == 0) {
            //TODO other calls
            extractDistinctParams(request);
            extractFieldFilterParams(request);
            extractSliceParams(request);
            extractSpatialSliceParams(request);
        }
        
        else if (paramsObject.getCall().compareTo("save_session") == 0) {
            String session = paramsObject.getFilename();
            String cubeId = paramsObject.getCubeId();
            
            XMLUtils xmlUtils = new XMLUtils();
            Document cubes = xmlUtils.filetoDoc("cubes.xml");
            String fragment = "<session name=\"" + session + "\" />";
            
            //Update the the desired cube element
            Node tempNode = cubes.importNode(xmlUtils.XmlToDoc(fragment).getDocumentElement(), true);
            
            NodeList listCubes = cubes.getElementsByTagName("cube");
            for(int i=0; i < listCubes.getLength(); i++) {
                if(((Element) listCubes.item(i)).getAttribute("id").equals(cubeId)) {
                    cubes.getElementsByTagName("cube").item(i).appendChild(tempNode);
                    break;
                }        
            }
        
            //save to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;
            try {
                transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(cubes);
                StreamResult result =  new StreamResult("cubes.xml");
                transformer.transform(source, result);
            } catch (TransformerConfigurationException e) {
                }catch (TransformerException e) {}
        }
        else if(paramsObject.getCall().compareTo("generalize") == 0){
            System.out.println("Entrei aqui para retirar os parametros necessarios");
            extractGeneralizeParams(request);
            extractAttributesToGeneralize(request);
            extractMeasuresToGeneralize(request);
            extractFactTableToUseToGeneralize(request);
            extractSliceParams(request);
            System.out.println("Terminei de retirar os parametros necessarios");
        }
        //TODO: tirei else if (paramsObject.getCall().compareTo("sortedKDistGraphs") == 0) {
        //     extractAttributeParams(request);
        // }
    }
    
    private void extractFactTableToUseToGeneralize(Element request) {
        NodeList elements = request.getElementsByTagName("facttable");
        Vector<SOLAPFactTable> facttables = new Vector<SOLAPFactTable>();
        
        for(int i=0; i < elements.getLength(); i++) {
            String id = ((Element)elements.item(i)).getAttribute("id");
            String name = ((Element)elements.item(i)).getAttribute("name");
            SOLAPFactTable fact = new SOLAPFactTable(id, name);
            facttables.add(fact);
        }
        
        paramsObject.setFactTables(facttables);
       
    }

    private void extractGeneralizeParams(Element request) {
        NodeList elements = request.getElementsByTagName("generalize");
        
        for(int i=0; i < elements.getLength(); i++) {
            String type = ((Element)elements.item(i)).getAttribute("type");
            String refinement = ((Element)elements.item(i)).getAttribute("refine");
            String from = ((Element)elements.item(i)).getAttribute("from");
            String labels = ((Element)elements.item(i)).getAttribute("defineLabels");
            String distinct = ((Element)elements.item(i)).getAttribute("distinct");
            String charMeasures = ((Element)elements.item(i)).getAttribute("charMeasures");
            String zoomLevel = ((Element)elements.item(i)).getAttribute("zoomLevel");
            SOLAPGeneralizationParams genParams = new SOLAPGeneralizationParams(type,refinement,from,labels,distinct,charMeasures,zoomLevel);
            if(type.equals("NON_SPATIAL")){
                String groupsParam = ((Element)elements.item(i)).getAttribute("groupsParam");
                genParams.setGroupsParam(groupsParam);
            }
            paramsObject.setGeneralizationParams(genParams);
        }
       
    }
    
    private void extractAttributesToGeneralize(Element request) {
        NodeList elements = request.getElementsByTagName("attribute");
        Vector<SumarizationAttribute> attributesToGeneralize = new Vector<SumarizationAttribute>();
        
        for(int i=0; i < elements.getLength(); i++) {
            String id = ((Element)elements.item(i)).getAttribute("id");
            String name = ((Element)elements.item(i)).getAttribute("name");
            String dimensionId = ((Element)elements.item(i)).getAttribute("dimensionId");
            String levelId = ((Element)elements.item(i)).getAttribute("levelId");
            String threshold = ((Element)elements.item(i)).getAttribute("threshold");
            SumarizationAttribute attribute = new SumarizationAttribute(id, name, threshold, dimensionId, levelId);
            
            NodeList levelToList = ((Element)elements.item(i)).getElementsByTagName("generalizeAttributeTo");
            if(levelToList.getLength()>0){
                String levelTo = ((Element)levelToList.item(0)).getAttribute("id");
                String type = ((Element)levelToList.item(0)).getAttribute("type");
                attribute.setAttributeToId(levelTo);
                attribute.setToType(type);
            }
            
            NodeList clusteringParameters = ((Element)elements.item(i)).getElementsByTagName("preProcessing");
            if(clusteringParameters.getLength()>0){
                if(((Element)clusteringParameters.item(0)).getAttribute("sharedBordersPreComputed").equals("true")){
                    attribute.setPreComputedSharedBorderTableID( ((Element)clusteringParameters.item(0)).getAttribute("tableRef") );
                }
            }
            
            attributesToGeneralize.add(attribute);
        }
        
        paramsObject.setAttributesToGeneralize(attributesToGeneralize);
    }
    
    private void extractMeasuresToGeneralize(Element request) {
        NodeList elements = request.getElementsByTagName("measure");
        Vector<SumarizationMeasure> measuresToGeneralize = new Vector<SumarizationMeasure>();
        
        for(int i=0; i < elements.getLength(); i++) {
            String id = ((Element)elements.item(i)).getAttribute("id");
            String name = ((Element)elements.item(i)).getAttribute("name");
            String agg = ((Element)elements.item(i)).getAttribute("agg");
            String threshold = ((Element)elements.item(i)).getAttribute("threshold");
            String create = ((Element)elements.item(i)).getAttribute("createHierarchy");
            SumarizationMeasure measure = new SumarizationMeasure(id, name, agg, threshold, create);
            measuresToGeneralize.add(measure);
        }
        
        paramsObject.setMeasuresToGeneralize(measuresToGeneralize);
    }

    private void extractDistinctParams(Element request) {
        //get <distinct> elements
        NodeList elements = request.getElementsByTagName("distinct");
        int nElementsToProcess = elements.getLength();
        int i = 0;
        
        while (i < nElementsToProcess) {
            //temporary object to be pushed into the array
            SOLAPDistinctsParams tempParams = new SOLAPDistinctsParams();
            Element one = (Element)elements.item(i);
            
            //set object's required data members
            tempParams.setLevelId(one.getAttributes().getNamedItem("levelId").getNodeValue());
            tempParams.setAttributeId(one.getAttributes().getNamedItem("attributeId").getNodeValue());
            tempParams.setDimensionId(one.getAttributes().getNamedItem("dimensionId").getNodeValue());
            //check for optional data members
            Node optional = one.getAttributes().getNamedItem("name");
            if (optional != null) {
                tempParams.setName(optional.getNodeValue());
            }
            
            //push the new object into the array
            paramsObject.addToDistincts(tempParams);

            i++;
        }
    }

    private void extractSliceParams(Element request) {
        //get <slice> elements
        NodeList elements = request.getElementsByTagName("slice");
        int nElementsToProcess = elements.getLength();
        int i = 0;
        
        while (i < nElementsToProcess) {
            //temporary object to be pushed into the array
            SOLAPSliceParams tempParams = new SOLAPSliceParams();
            Element one = (Element)elements.item(i);
            
            //set object's required data members
            tempParams.setAttributeId(one.getAttributes().getNamedItem("attributeId").getNodeValue());
            tempParams.setLevelId(one.getAttributes().getNamedItem("levelId").getNodeValue());
            tempParams.setDimensionId(one.getAttributes().getNamedItem("dimensionId").getNodeValue());
            tempParams.setOperator(one.getAttributes().getNamedItem("operator").getNodeValue());
            tempParams.setValue1(one.getAttributes().getNamedItem("value1").getNodeValue());
            
            //check for optional data members
            Node optional = one.getAttributes().getNamedItem("value2");
            if (optional != null) {
                tempParams.setValue2(optional.getNodeValue());
            }
            optional = one.getAttributes().getNamedItem("slider");
            if (optional != null) {
                tempParams.setValue2(optional.getNodeValue());
            }
            
            //push the new object into the array
            paramsObject.addToSlices(tempParams);
            i++;
        }
    }
    
    private void extractMeasureParams(Element request) {
        //get <measure> elements
        NodeList elements = request.getElementsByTagName("measure");
        int nElementsToProcess = elements.getLength();
        int i = 0;
        
        while (i < nElementsToProcess) {
            //temporary object to be pushed into the array
            SOLAPMeasureParams tempParams = new SOLAPMeasureParams();
            Element one = (Element)elements.item(i);
            
            //set object's required data members
            tempParams.setId(one.getAttributes().getNamedItem("id").getNodeValue());
            //check for optional data members
            Node optional = one.getAttributes().getNamedItem("operator");
            if (optional != null) {
                tempParams.setOperator(optional.getNodeValue());
            }
            
            //push the new object into the array
            paramsObject.addToMeasures(tempParams);

            i++;
        }
    }

    private void extractLevelParams(Element request) {
        //get <level> elements
        NodeList elements = request.getElementsByTagName("level");
        int nElementsToProcess = elements.getLength();
        int i = 0;
        
        while (i < nElementsToProcess) {
            //temporary object to be pushed into the array
            SOLAPLevelParams tempParams = new SOLAPLevelParams();
            Element one = (Element)elements.item(i);
            
            //set object's required data members
            tempParams.setId(one.getAttributes().getNamedItem("id").getNodeValue());
            tempParams.setDimensionId(one.getAttributes().getNamedItem("dimensionId").getNodeValue());
            //no optional data members for this element
            
            //push the new object into the array
            //levelParams.add(tempParams);
            paramsObject.addToLevels(tempParams);

            i++;
        }
    }

    private void extractAttributeParams(Element request) {
        //get <attribute> elements
        NodeList elements = request.getElementsByTagName("attribute");
        int nElementsToProcess = elements.getLength();
        int i = 0;
        
        while (i < nElementsToProcess) {
            //temporary object to be pushed into the array
            SOLAPAttributeParams tempParams = new SOLAPAttributeParams();
            Element one = (Element)elements.item(i);
            
            //set object's required data members
            tempParams.setId(one.getAttributes().getNamedItem("id").getNodeValue());
            tempParams.setLevelId(one.getAttributes().getNamedItem("levelId").getNodeValue());
            tempParams.setDimensionId(one.getAttributes().getNamedItem("dimensionId").getNodeValue());
            //no optional data members for this element
            
            //push the new object into the array
            //attributeParams.add(tempParams);
            paramsObject.addToAttributes(tempParams);
            i++;
        }
    }

    public SOLAPParamsObject getParamsObject() {
        return paramsObject;
    }

    private void extractFieldFilterParams(Element request) {
        //get <fieldFilter> elements
        NodeList elements = request.getElementsByTagName("fieldFilter");
        int nElementsToProcess = elements.getLength();
        int i = 0;
        
        while (i < nElementsToProcess) {
            //temporary object to be pushed into the array
            SOLAPFieldFilterParams tempParams = new SOLAPFieldFilterParams();
            Element one = (Element)elements.item(i);
            
            //set object's required data members
            tempParams.setMeasureId(one.getAttributes().getNamedItem("measureId").getNodeValue());
            tempParams.setOperator(one.getAttributes().getNamedItem("operator").getNodeValue());
            tempParams.setValue1(one.getAttributes().getNamedItem("value1").getNodeValue());
            //check for optional data members
            Node optional = one.getAttributes().getNamedItem("value2");
            if (optional != null) {
                tempParams.setValue2(optional.getNodeValue());
            }
            
            //push the new object into the array
            paramsObject.addToFieldFilters(tempParams);

            i++;
        }
    }

    private void extractMeasureFilterParams(Element request) {
        //get <measureFilter> elements
        NodeList elements = request.getElementsByTagName("measureFilter");
        int nElementsToProcess = elements.getLength();
        int i = 0;
        
        while (i < nElementsToProcess) {
            //temporary object to be pushed into the array
            SOLAPMeasureFiltersParams tempParams = new SOLAPMeasureFiltersParams();
            Element one = (Element)elements.item(i);
            
            //set object's required data members
            tempParams.setMeasureId(one.getAttributes().getNamedItem("measureId").getNodeValue());
            tempParams.setOperator(one.getAttributes().getNamedItem("operator").getNodeValue());
            tempParams.setValue1(one.getAttributes().getNamedItem("value1").getNodeValue());
            //check for optional data members
            Node optional = one.getAttributes().getNamedItem("value2");
            if (optional != null) {
                tempParams.setValue2(optional.getNodeValue());
            }
            optional = one.getAttributes().getNamedItem("measureOperator");
            if (optional != null) {
                tempParams.setMeasureOperator(optional.getNodeValue());
            }
            
            //push the new object into the array
            paramsObject.addToMeasureFilters(tempParams);

            i++;
        }
    }

    private void extractNFilterParams(Element request) {
        //get <nFilter> elements
        NodeList elements = request.getElementsByTagName("nFilter");
        int nElementsToProcess = elements.getLength();
        int i = 0;
        
        while (i < nElementsToProcess) {
            //temporary object to be pushed into the array
            SOLAPNFiltersParams tempParams = new SOLAPNFiltersParams();
            Element one = (Element)elements.item(i);
            
            //set object's required data members
            tempParams.setMeasureId(one.getAttributes().getNamedItem("measureId").getNodeValue());
            tempParams.setOperator(one.getAttributes().getNamedItem("operator").getNodeValue());
            tempParams.setNRows(one.getAttributes().getNamedItem("nRows").getNodeValue());
            //check for optional data members
            Node optional = one.getAttributes().getNamedItem("measureOperator");
            if (optional != null) {
                tempParams.setMeasureOperator(optional.getNodeValue());
            }
            
            //push the new object into the array
            paramsObject.addToNFilters(tempParams);

            i++;
        }
    }

    private void extractSpatialSliceParams(Element request) {
        //get <spatialSlice> elements
        NodeList elements = request.getElementsByTagName("spatialSlice");
        int nElementsToProcess = elements.getLength();
        int i = 0;
        
        while (i < nElementsToProcess) {
            //temporary object to be pushed into the array
            SOLAPSpatialSliceParams tempParams = new SOLAPSpatialSliceParams();
            Element one = (Element)elements.item(i);
            
            //set object's required data members
            tempParams.setDimensionId(one.getAttributes().getNamedItem("dimensionId").getNodeValue());
            tempParams.setLevelId(one.getAttributes().getNamedItem("levelId").getNodeValue());
            tempParams.setLayerId(one.getAttributes().getNamedItem("layerId").getNodeValue());
            tempParams.setOperator(one.getAttributes().getNamedItem("operator").getNodeValue());
            tempParams.setAttributeId(one.getAttributes().getNamedItem("attributeId").getNodeValue());
            //check for optional data members
            Node optional = one.getAttributes().getNamedItem("value");
            if (optional != null) {
                tempParams.setValue(optional.getNodeValue());
            }
            optional = one.getAttributes().getNamedItem("unit");
            if (optional != null) {
                tempParams.setUnit(optional.getNodeValue());
            }
            
            //push the new object into the array
            paramsObject.addToSpatialSlices(tempParams);

            i++;
        }
    }

    private void extractGeometricSliceParams(Element request) {
        //get <geometricSlice> elements
        NodeList elements = request.getElementsByTagName("geometricSlice");
        int nElementsToProcess = elements.getLength();
        int i = 0;
        
        while (i < nElementsToProcess) {
            //temporary object to be pushed into the array
            SOLAPGeometricSlicesParams tempParams = new SOLAPGeometricSlicesParams();
            Element one = (Element)elements.item(i);
            
            //set object's required data members
            tempParams.setDimensionId(one.getAttributes().getNamedItem("dimensionId").getNodeValue());
            tempParams.setLevelId(one.getAttributes().getNamedItem("levelId").getNodeValue());
            tempParams.setGType(one.getAttributes().getNamedItem("gType").getNodeValue());
            tempParams.setGSrid(one.getAttributes().getNamedItem("gSrid").getNodeValue());
            tempParams.setGPoint(one.getAttributes().getNamedItem("gPoint").getNodeValue());
            tempParams.setGInfo(one.getAttributes().getNamedItem("gInfo").getNodeValue());
            tempParams.setGOrdinates(one.getAttributes().getNamedItem("gOrdinates").getNodeValue());
            tempParams.setAttributeId(one.getAttributes().getNamedItem("attributeId").getNodeValue());
            //no optional data members
            
            //push the new object into the array
            paramsObject.addToGeometricSlices(tempParams);

            i++;
        }
    }

    private void extractClusteringParams(Element request) {
        //get <spatialSlice> elements
        NodeList elements = request.getElementsByTagName("clustering");
        int nElementsToProcess = elements.getLength();
        int i = 0;
        
        XMLUtils xmlUtils = new XMLUtils();
        while (i < nElementsToProcess) {
            //temporary object to be pushed into the array
            SOLAPClusteringParams tempParams = new SOLAPClusteringParams();
            Element one = (Element)elements.item(i);
            
            //set object's required data members
            tempParams.setMode((one.getAttributes().getNamedItem("mode").getNodeValue()) );
            tempParams.setGroups((one.getAttributes().getNamedItem("groups").getNodeValue()) );
            tempParams.setVariant((one.getAttributes().getNamedItem("variant").getNodeValue()));
            tempParams.setZoom((one.getAttributes().getNamedItem("zoomLevel").getNodeValue()));
            
            String spatialLevel = "";
            String levelId = "";
            String dimensionId = "";
            Element[] parameters = xmlUtils.getAllChildElementNamed(request, "parameters", true);
            for(int j = 0; j < parameters.length; j++) {
                Element param = parameters[j];
                spatialLevel = param.getAttributes().getNamedItem("spatialLevel").getNodeValue();
                
                if(param.getAttributes().getNamedItem("levelId") !=null)
                    levelId = param.getAttributes().getNamedItem("levelId").getNodeValue();
                
                if(param.getAttributes().getNamedItem("dimensionId") !=null)
                    dimensionId = param.getAttributes().getNamedItem("dimensionId").getNodeValue();
                
                tempParams.addSpatialBasedSpatialParams(spatialLevel, dimensionId, levelId);
                String distancesPreComputed = param.getAttributes().getNamedItem("distancesPreComputed").getNodeValue();
                
                if(distancesPreComputed.equals("true")) {
                    tempParams.setTableId( param.getAttributes().getNamedItem("tableRef").getNodeValue());
                    tempParams.setFromId( param.getAttributes().getNamedItem("from").getNodeValue() );
                    tempParams.setToId( param.getAttributes().getNamedItem("to").getNodeValue() );
                    tempParams.setDistancesValuesId(param.getAttributes().getNamedItem("distances").getNodeValue() );    
                }
            }

            //push the new object into the array
            paramsObject.setClusteringParams(tempParams);
            i++;
        }
    }

    private void extractDetailGroupsParams(Element request) {
        //get <attribute> elements
        NodeList elements = request.getElementsByTagName("detailGroup");
        int nElementsToProcess = elements.getLength();
        int i = 0;
        
        while (i < nElementsToProcess) {
            //temporary object to be pushed into the array
            Element one = (Element)elements.item(i);
            SOLAPDetailGroupParams tempParams = null;
            //set object's required data members
            if(one.getAttributes().getNamedItem("value") != null) {
                tempParams = new SOLAPDetailGroupParams(one.getAttributes().getNamedItem("value").getNodeValue());
            }
            else {
                String dimensionId = one.getAttributes().getNamedItem("dimensionId").getNodeValue();
                String levelId = one.getAttributes().getNamedItem("levelId").getNodeValue();
                String groupId = one.getAttributes().getNamedItem("groupId").getNodeValue();
                String detail = one.getAttributes().getNamedItem("detail").getNodeValue();
                tempParams = new SOLAPDetailGroupParams(dimensionId, levelId, groupId, detail);
            }
            
            //attributeParams.add(tempParams);
            paramsObject.addToDetailGroups(tempParams);
            i++;
        }
    }
}
