package solap.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.richfaces.event.DropEvent;
import solap.ResponseExtractor;
import solap.SOLAPServerPortClient;
import solap.entities.InterfaceAttribute;
import solap.entities.ClusteringManager;
import solap.entities.ErrorHandler;
import solap.entities.InterfaceFilter;
import solap.entities.InterfaceLevel;
import solap.entities.InterfaceMeasure;
import solap.entities.InterfaceSlice;
import solap.entities.InterfaceSpatialSlice;
import solap.entities.SOLAPAttribute;
import solap.entities.SOLAPAttributeHierarchies;
import solap.entities.SOLAPAttributeHierarchy;
import solap.entities.SOLAPDimension;
import solap.entities.SOLAPFactTable;
import solap.entities.SOLAPHierarchy;
import solap.entities.SOLAPLayer;
import solap.entities.SOLAPLevel;
import solap.entities.SOLAPMeasure;
import solap.entities.SOLAPMapserver;
import solap.entities.SOLAPPreComputingSharedBorders;
import solap.entities.SumarizationManager;

import solap.entities.SumarizationMeasure;

import solap.utils.CommProtocolUtils;
import solap.utils.EditUtils;
import solap.utils.ITriple;
import solap.utils.XMLUtils;

import solap.utils.Triple;

public class SessionBean implements Serializable {
    private String cubeId;
    private String filename;
    
    //Store name of the cube and its xml file name
    private Map<String, ITriple<String, String, String>> cubeInfo = new HashMap<String, ITriple<String, String, String>>();
    
    //models and sessions
    private List<SelectItem> cubesList = new ArrayList<SelectItem>();
    private List<SelectItem> sessionList = new ArrayList<SelectItem>();
    private String tempDescription = "Please select a model";
    private String selectedCube;
    private String selectedSession;
    private String tempSaveName = "new_session";
    private List<SelectItem> sessionsForThisModel = new ArrayList<SelectItem>();
    
    //globals
    SOLAPServerPortClient portClient = new SOLAPServerPortClient();
    ResponseExtractor extractor;
    XMLUtils xmlUtils = new XMLUtils();
    
    //temporary variables (used to receive parameter values / edit slices)
    private DropEvent tempDrop;
    private String tempAggOp;
    private String tempId;
    private String tempDimensionId;
    private String tempLevelId;
    private String tempName;
    private String tempLayerId;
    private String tempLayerName;
    private String tempFilterName = "filter";
    private String tempFilterDesc = "Enter a description";
    private String tempOperator;
    private String tempOperator1;
    private String tempOperator2;
    private String tempValue;
    private String tempValue1 = "0";
    private String tempValue2 = "0";
    private String tempUnit;
    
    //distincts and selected values for slices
    private List<SelectItem> tempDistincts = new ArrayList<SelectItem>();
    private List<String> tempSelectedValues = new ArrayList<String>();
    
    //values and selected for removal TODO add to reset
    private List<SelectItem> tempAvailableM = new ArrayList<SelectItem>();
    private List<String> tempSelectedMValues = new ArrayList<String>();
    private List<SelectItem> tempAvailableA = new ArrayList<SelectItem>();
    private List<String> tempSelectedAValues = new ArrayList<String>();
    private List<SelectItem> tempAvailableL = new ArrayList<SelectItem>();
    private List<String> tempSelectedLValues = new ArrayList<String>();
    
    private InterfaceSlice tempSemanticSlice;
    private InterfaceSpatialSlice tempSpatialSlice;
    private InterfaceFilter tempMeasureFilter;
    
    //defines whether to create a slider or not
    private boolean tempSlider = false;
    
    //defines whether the required components for a spatial slice are present or not
    private boolean tempSpatialSliceReady = false;
    private boolean tempSpatialAttribute = false;
    private boolean tempSpatialObject = false;
    
    //captions for tables (pending operations)
    private Vector<String> captionsST = new Vector<String>();
    private Vector<String> captionsDT = new Vector<String>();
    private String caption;
    private String captionDT;
    
    //edit variables (on/off)
    private boolean editSlice = false;
    private boolean editSpatialSlice = false;
    private boolean editFilter = false;
    
    //Maintain information about the map
    private SOLAPMapserver mapInfo;
    
    //Store info about clustering
    private ClusteringManager infoClustering = new ClusteringManager();
    private boolean clustering;
    
    /* Control labels variables */
    private boolean newSpatialObjects = false;
    private boolean labelGroupsChecked = true;
    
    private String labelGroup1 = "";
    private String labelGroup2 = "";
    private boolean labelGroups1Checked = true;
    private boolean labelGroups2Checked = true;
    /*End Control labels variables */
    
    //Store the columnRef of spatial attributes in support table
    //To be used in javascript because of the labels
    private String columnRefInST = "";
    
    //initialize cube
    public void initializeCube() {
        portClient = new SOLAPServerPortClient();
        newSpatialObjects = false;
        
        labelGroupsChecked = true;
        labelGroups1Checked = true;
        labelGroups2Checked = true;
        
        columnRefInST = "";
        labelGroup1 = "";
        labelGroup2 = "";
            
        String requestText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
        "<solapplus>\n" + 
        "    <request call=\"load_cube\">\n" + 
        "        <params cubeId=\"" + cubeId + "\" filename=\"" + filename + "\" />\n" + 
        "    </request>\n" + 
        "</solapplus>";
        
        String responseText = portClient.executeRequest(requestText);   
        infoClustering = new ClusteringManager();
        extractor = new ResponseExtractor(responseText);
        
        //set entities
        setDimensions();
        setMeasures();
        setLayers();
        setStyles();
        
        //To be used by clustering
        infoClustering.fillSpatialHierarchies(dimensions);
        
        //set mapinfo
        mapInfo = extractor.getMapserver();
        infoClustering.setBaseLevel(mapInfo.getZoomLevel());
        
        MainBean mainBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
        mainBean.rebootErrorHandler();
        
        //load session (if selected)
        if (selectedSession != null && selectedSession != "<NEW>") {
            //Setup bean access
            ManagerBean managerBean = (ManagerBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ManagerBean");
            if (managerBean != null) {
                managerBean.load(selectedSession);
            }
            if(mainBean.isSummarization())
                mainBean.executeGeneralization(infoSummarization.getGeneralizationType());
            else
                mainBean.execute();
        }
        
    }
    
    public void save() {
        ManagerBean managerBean = (ManagerBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ManagerBean");
        managerBean.save();
    }
    
    //dimension information (levels, attributes, hierarchies)
    private Vector<SOLAPDimension> dimensions = new Vector<SOLAPDimension>();
    public Vector<SOLAPDimension> getDimensions() {
        return dimensions;
    }
    
    public void setDimensions() {
        dimensions = extractor.getDimensions();
    }
    
    public void setDimensions(Vector<SOLAPDimension> v) {
        dimensions = v;
    }
    
    //measure information
    private Vector<SOLAPMeasure> measures = new Vector<SOLAPMeasure>();
    public Vector<SOLAPMeasure> getMeasures() {
        return measures;
    }
    public void setMeasures() {
        measures = extractor.getMeasures();
    }
    public void setMeasures(Vector<SOLAPMeasure> v) {
        measures = v;
    }
    
    //layer information
    private Vector<SOLAPLayer> layers = new Vector<SOLAPLayer>();
    private Vector<SOLAPLayer> layersLabel = new Vector<SOLAPLayer>();
    private Vector<SOLAPLayer> layersObject = new Vector<SOLAPLayer>();
    private Vector<SelectItem> layerNames = new Vector<SelectItem>();
    
    public Vector<SOLAPLayer> getLayers() {
        return layers;
    }
    public Vector<SOLAPLayer> getLayersLabel() {
        return layersLabel;
    }
    
    public Vector<SOLAPLayer> getLayersObject() {
        return layersObject;
    }
    
    public Vector<SelectItem> getLayerNames() {
        return layerNames;
    }
    
    public void setLayers() {
        //always get the 3 kinds
        layers = extractor.getLayers();
        layersLabel = extractor.getLayersLabel();
        layersObject = extractor.getLayersObject();
        //fill variable with names for spatial slices
        for (int i = 0; i < layers.size(); i++) {
            layerNames.add(new SelectItem(layers.elementAt(i).getTitle()));
        }
    }
    
    //Styles information
    private void setStyles() {
        MainBean myBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
        extractor.fillStyles(myBean.getManagerStyles());
    }
    
    //dropped item processors
    public void processDropSupportTable(DropEvent dropEvent) {
        //dropped a measure
        if (dropEvent.getDragType().compareTo("measure") == 0) {
            SOLAPMeasure dragged = (SOLAPMeasure)dropEvent.getDragValue();
            FacesContext context = FacesContext.getCurrentInstance();  
            Map requestMap = context.getExternalContext().getRequestParameterMap();  
            String tempAggOp = (String)requestMap.get("operator");
            //add measure to pending operations
            addMeasureToST(dragged.getId(), tempAggOp, dragged.getName());
            //add caption for support table
            captionsST.add(dragged.getName() + " [M]");
            
            MainBean myBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
            myBean.execute();
            
            ErrorHandler errorHandler = myBean.getErrorHandler();
            if(errorHandler.isData())
                errorHandler.undoAddMeasureOperation(IMeasuresST, captionsST);
        }
        
        //dropped an attribute
        if (dropEvent.getDragType().compareTo("attribute") == 0) {
            SOLAPAttribute dragged = (SOLAPAttribute)dropEvent.getDragValue();
            //add attribute to pending operations
            addAttributeToST(dragged.getDimensionId(), dragged.getLevelId(), dragged.getId(), dragged.getName());
            //add caption for support table
            captionsST.add(dragged.getName() + " [A]");
            
            MainBean myBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
            myBean.execute();
            
            ErrorHandler errorHandler = myBean.getErrorHandler();
            if(errorHandler.isData())
                errorHandler.undoAddAttributeOperation(IAttributesST, captionsST);
        }
        
        //dropped a level
        if (dropEvent.getDragType().compareTo("level") == 0) {
            SOLAPLevel dragged = (SOLAPLevel)dropEvent.getDragValue();
            
            //Detects if the new level belongs to same hierarchy of a previous level in the support table
            //In this case is perform a roll-up or drill-down operation.
            ITriple<Boolean, Integer, String> infoOperation = drillUpDown(dragged);
            
            updateInfoToLabels(dragged, infoOperation);
            
            //add attribute to pending operations
            if(infoOperation.getFirst() && infoOperation.getSecond() == 0) addFirstLevelToST(dragged);
            else addLevelToST(dragged);

            infoClustering.addLevelToAnalysis(new Triple<String, String, String>(dragged.getName(), dragged.getDimensionId(),dragged.getId() ), dragged.getPreComputing());
            
            //add caption for support table
            captionsST.add(dragged.getName() + " [L]");
            
            if(IMeasuresST.size() > 0) {
                MainBean myBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
                myBean.execute();
                
                ErrorHandler errorHandler = myBean.getErrorHandler();
                if(errorHandler.isData())
                    errorHandler.undoAddLevelOperation(infoOperation, ILevelsST, infoClustering, captionsST, dragged);  
            }
        }
    }
    public void processDropSlice(DropEvent dropEvent) {
        System.out.println("PROCESS DROP SLICE: " + dropEvent.getDragType());
        
        if (dropEvent.getDragType().compareTo("attribute") == 0) {
            SOLAPAttribute dragged = (SOLAPAttribute)dropEvent.getDragValue();
            tempDrop = dropEvent;
            tempId = dragged.getId();
            tempName = dragged.getName();
            
            System.out.println("Level ID: " + dragged.getLevelId() + " AttributoID: " + dragged.getId() + " DimensionID: " + dragged.getDimensionId());
            
            //get response from server
            String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
            "<solapplus>\n" + 
            "    <request call=\"get_distincts\">\n" + 
            "        <params cubeId=\"" + cubeId + "\" filename=\"" + filename + "\"/>\n" + 
            "        <distinct levelId=\""+ dragged.getLevelId() + "\" attributeId=\"" + dragged.getId() + "\" dimensionId=\"" + dragged.getDimensionId() + "\"/>\n";
            
            for (int i = 0; i < ISlices.size(); i++) request += ISlices.elementAt(i).getXML();
            for (int i = 0; i < ISliders.size(); i++) request += ISliders.elementAt(i).getXML();
            for (int i = 0; i < ISpatialSlices.size(); i++) request += ISpatialSlices.elementAt(i).getXML();
            for (int i = 0; i < IFilters.size(); i++) request += IFilters.elementAt(i).getXML();
                  
            request += "    </request>\n" + 
            "</solapplus>";
            
            String responseText = portClient.executeRequest(request);
            System.out.println("RESPOSTA DO SLICE: " + responseText);
            
            extractor = new ResponseExtractor(responseText);
            
            //create a temporary interface object
            tempSemanticSlice = new InterfaceSlice(dragged.getDimensionId(), dragged.getLevelId(), tempId, tempName);
            
            //convert strings to selectItem
            Vector<String> values = extractor.getDistinctValues();
            //reset tempDistincts
            tempDistincts = new ArrayList<SelectItem>();
            
            for (int i = 0; i < values.size(); i++) {
                
            /* Nao entendo o bug   
             * String temp = values.elementAt(i).replaceAll(","," ");
                
                if(temp.length() > 120)
                    temp = temp.substring(0,120);
                
                System.out.println("ADICIONA NA TEMPDISTINCTs: " + temp);*/
                tempDistincts.add(new SelectItem(values.elementAt(i)));
            }
            
            System.out.println("TERMINA DROP SLICE");
        }
    }
    
    public void processDropSpatialSlice(DropEvent dropEvent) {
        if (dropEvent.getDragType().compareTo("attribute") == 0) {
            tempSpatialAttribute = true;
            SOLAPAttribute dragged = (SOLAPAttribute)dropEvent.getDragValue();
            tempDrop = dropEvent;
            tempId = dragged.getId();
            tempDimensionId = dragged.getDimensionId();
            tempLevelId = dragged.getLevelId();
            tempName = dragged.getName();
        }
        
        if (dropEvent.getDragType().compareTo("layer") == 0) {
            SOLAPLayer dragged = (SOLAPLayer)dropEvent.getDragValue();
            tempLayerId = dragged.getId();
            tempSpatialObject = true;
        }
        if (tempSpatialAttribute && tempSpatialObject) {
            tempSpatialSliceReady = true;
            
            //create interface object
            tempSpatialSlice = new InterfaceSpatialSlice(tempDimensionId, tempLevelId, tempId, tempName, tempLayerId);
        }
    }
    public void processDropMeasureFilter(DropEvent dropEvent) {
        SOLAPMeasure dragged = (SOLAPMeasure)dropEvent.getDragValue();
        tempDrop = dropEvent;
        tempId = dragged.getId();
        tempName = dragged.getName();
        
        //create interface object
        tempMeasureFilter = new InterfaceFilter(tempId, tempName);
    }
    
    public void processDropDetailTable(DropEvent dropEvent) {
        //dropped a measure
        if (dropEvent.getDragType().compareTo("measure") == 0) {
            SOLAPMeasure dragged = (SOLAPMeasure)dropEvent.getDragValue();
            FacesContext context = FacesContext.getCurrentInstance();  
            Map requestMap = context.getExternalContext().getRequestParameterMap();  
            String tempAggOp = (String)requestMap.get("operator");
            //add measure to pending operations
            addMeasureToDT(dragged.getId(), tempAggOp, dragged.getName());
            //add caption for support table
            captionsDT.add(dragged.getName() + " [M]");
        }
        
        //dropped an attribute
        if (dropEvent.getDragType().compareTo("attribute") == 0) {
            SOLAPAttribute dragged = (SOLAPAttribute)dropEvent.getDragValue();
            //add attribute to pending operations
            addAttributeToDT(dragged.getDimensionId(), dragged.getLevelId(), dragged.getId(), dragged.getName());
            //add caption for support table
            captionsDT.add(dragged.getName() + " [A]");
        }
        
        //dropped a level
        if (dropEvent.getDragType().compareTo("level") == 0) {
            SOLAPLevel dragged = (SOLAPLevel)dropEvent.getDragValue();
            //add attribute to pending operations
            addLevelToDT(dragged.getDimensionId(), dragged.getId(), dragged.getName(), "");
            //add caption for support table
            captionsDT.add(dragged.getName() + " [L]");
        }
    }

    //temporary variable accessors
    public void setTempAggOp(String tempAggOp) {
        this.tempAggOp = tempAggOp;
    }
    public String getTempAggOp() {
        return tempAggOp;
    }
    public void setTempName(String tempName) {
        this.tempName = tempName;
    }
    public String getTempName() {
        return tempName;
    }
    public void setTempDimensionId(String temp) {
        tempDimensionId = temp;
    }
    public void setTempLevelId(String temp) {
        tempLevelId = temp;
    }
    public void setTempId(String tempId) {
        this.tempId = tempId;
    }
    public String getTempId() {
        return tempId;
    }
    public void setTempLayerId(String tempId) {
        this.tempLayerId = tempId;
    }
    public String getTempLayerId() {
        return tempLayerId;
    }
    public void setTempLayerName(String name) {
        //layer has been changed on combo box, update layerID
        for (int i = 0; i < layers.size(); i++) {
            if (layers.elementAt(i).getTitle().compareTo(name) == 0)
                setTempLayerId(layers.elementAt(i).getId());
        }
        for (int i = 0; i < layersObject.size(); i++) {
            if (layersObject.elementAt(i).getTitle().compareTo(name) == 0)
                setTempLayerId(layersObject.elementAt(i).getId());
        }
        //update name
        tempLayerName = name;
    }
    public String getTempLayerName() {
        if (layers != null && tempLayerId != null) {
        for (int i = 0; i < layers.size(); i++) {
            if (layers.elementAt(i).getId().compareTo(tempLayerId) == 0)
                return layers.elementAt(i).getTitle();
        }
        for (int i = 0; i < layersObject.size(); i++) {
            if (layersObject.elementAt(i).getId().compareTo(tempLayerId) == 0)
                return layersObject.elementAt(i).getTitle();
        }
        }
        return "none";
    }
    
    public void setTempDistincts(List<SelectItem> td) {
        tempDistincts = td;
    }
    public List<SelectItem> getTempDistincts() {
        return tempDistincts;
    }
    public void setTempSelectedValues(List<String> td) {
        tempSelectedValues = td;
    }
    public List<String> getTempSelectedValues() {
        return tempSelectedValues;
    }
    public void setTempSlider(boolean value) {
        tempSlider = value;
    }
    
    public boolean getTempSlider() {
        return tempSlider;
    }
    public void setTempSpatialAttribute(boolean tempSpatialAttribute) {
        this.tempSpatialAttribute = tempSpatialAttribute;
    }
    public boolean isTempSpatialAttribute() {
        return tempSpatialAttribute;
    }
    public void setTempSpatialObject(boolean tempSpatialObject) {
        this.tempSpatialObject = tempSpatialObject;
    }
    public boolean isTempSpatialObject() {
        return tempSpatialObject;
    }
    public void setTempSpatialSliceReady(boolean value) {
        tempSpatialSliceReady = value;
    }
    public boolean isTempSpatialSliceReady() {
        return (tempSpatialAttribute && tempSpatialObject);
    }
    public void setTempOperator(String tempOperator) {
        this.tempOperator = tempOperator;
    }
    public String getTempOperator() {
        return tempOperator;
    }
    public void setTempValue(String tempValue) {
        this.tempValue = tempValue;
    }
    public String getTempValue() {
        return tempValue;
    }
    public void setTempUnit(String tempUnit) {
        this.tempUnit = tempUnit;
    }
    public String getTempUnit() {
        return tempUnit;
    }
    public void setTempFilterName(String tempFilterName) {
        this.tempFilterName = tempFilterName;
    }
    public String getTempFilterName() {
        return tempFilterName;
    }
    public void setTempFilterDesc(String tempFilterDesc) {
        this.tempFilterDesc = tempFilterDesc;
    }
    public String getTempFilterDesc() {
        return tempFilterDesc;
    }
    public void setTempOperator1(String tempOperator1) {
        this.tempOperator1 = tempOperator1;
    }
    public String getTempOperator1() {
        return tempOperator1;
    }
    public void setTempOperator2(String tempOperator2) {
        this.tempOperator2 = tempOperator2;
    }
    public String getTempOperator2() {
        return tempOperator2;
    }
    public void setTempValue1(String tempValue1) {
        this.tempValue1 = tempValue1;
    }
    public String getTempValue1() {
        return tempValue1;
    }
    public void setTempValue2(String tempValue2) {
        this.tempValue2 = tempValue2;
    }
    public String getTempValue2() {
        return tempValue2;
    }
    public String getCaptionDT() {
        String temp = "Detail Table";
        
        for (int i = 0; i < captionsST.size(); i++) {
            if (i == 0) {
                temp += " (" + captionsST.elementAt(i);
            }
            else {
                temp += ", " + captionsST.elementAt(i);
            }
        }
        for (int i = 0; i < captionsDT.size(); i++) {
            temp += ", " + captionsDT.elementAt(i);
            
            if (captionsDT.size() == i+1) {
                temp += ")";
            } 
        }

        return temp;
    }
    
    public String getCaption() {
        String temp = "Support Table";
        
        for (int i = 0; i < captionsST.size(); i++) {
            if (i == 0) {
                temp += " (" + captionsST.elementAt(i);
            }
            else {
                temp += ", " + captionsST.elementAt(i);
            }
            
            if (captionsST.size() == i+1) {
                temp += ")";
            } 
        }
        return temp;
    }
    
    public boolean isEditSlice() {
        return editSlice;
    }
    public void setEditSlice(boolean value) {
        editSlice = value;
    }
    public void setEditSpatialSlice(boolean editSpatialSlice) {
        this.editSpatialSlice = editSpatialSlice;
    }
    public boolean isEditSpatialSlice() {
        return editSpatialSlice;
    }
    public void setEditFilter(boolean editFilter) {
        this.editFilter = editFilter;
    }
    public boolean isEditFilter() {
        return editFilter;
    }
    public void setTempAvailableM(List<SelectItem> tempAvailableM) {
        this.tempAvailableM = tempAvailableM;
    }
    public List<SelectItem> getTempAvailableM() {
        tempAvailableM = new ArrayList<SelectItem>();
        for (int i = 0; i < IMeasuresST.size(); i++) {
            tempAvailableM.add(new SelectItem(IMeasuresST.get(i).getName() + " (" + IMeasuresST.get(i).getOperator() + ")"));
        }
        return tempAvailableM;
    }
    public void setTempSelectedMValues(List<String> tempSelectedMValues) {
        this.tempSelectedMValues = tempSelectedMValues;
    }
    public List<String> getTempSelectedMValues() {
        return tempSelectedMValues;
    }
    public void setTempAvailableA(List<SelectItem> tempAvailableA) {
        this.tempAvailableA = tempAvailableA;
    }
    public List<SelectItem> getTempAvailableA() {
        tempAvailableA = new ArrayList<SelectItem>();
        for (int i = 0; i < IAttributesST.size(); i++) {
            tempAvailableA.add(new SelectItem(IAttributesST.get(i).getName()));
        }
        return tempAvailableA;
    }
    public void setTempSelectedAValues(List<String> tempSelectedAValues) {
        this.tempSelectedAValues = tempSelectedAValues;
    }
    public List<String> getTempSelectedAValues() {
        return tempSelectedAValues;
    }
    public void setTempAvailableL(List<SelectItem> tempAvailableL) {
        this.tempAvailableL = tempAvailableL;
    }
    public List<SelectItem> getTempAvailableL() {
        tempAvailableL = new ArrayList<SelectItem>();
        for (int i = 0; i < ILevelsST.size(); i++) {
            tempAvailableL.add(new SelectItem(ILevelsST.get(i).getName()));
        }
        return tempAvailableL;
    }
    public void setTempSelectedLValues(List<String> tempSelectedLValues) {
        this.tempSelectedLValues = tempSelectedLValues;
    }
    public List<String> getTempSelectedLValues() {
        return tempSelectedLValues;
    }
    //models and sessions
    public List<SelectItem> getCubesList() {
        //get response from server
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
        "<solapplus>\n" + 
        "    <request call=\"list_cubes\">\n" + 
        "    </request>\n" + 
        "</solapplus>";
        
        String responseText = portClient.executeRequest(request);
        extractor = new ResponseExtractor(responseText);
        
        cubeInfo = extractor.getCubes();
        cubesList = new ArrayList<SelectItem>();
        
        for(String cubename: cubeInfo.keySet()) {
            cubesList.add(new SelectItem(cubename));
        }
        
        return cubesList;
    }
    
    public List<SelectItem> getSessionList() {
        //get response from server
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
        "<solapplus>\n" + 
        "    <request call=\"list_cubes\">\n" + 
        "    </request>\n" + 
        "</solapplus>";
        
        String responseText = portClient.executeRequest(request);
        extractor = new ResponseExtractor(responseText);
        
        Vector<String> list = extractor.getSessions(selectedCube);
        sessionList = new ArrayList<SelectItem>();
        
        for (int i = 0; i < list.size(); i++) {
            sessionList.add(new SelectItem(list.elementAt(i)));
        }
        
        return sessionList;
    }
    public String getTempDescription() {
        tempDescription = extractor.getDescription(selectedCube);
        
        return tempDescription;
    }
    public void setTempDescription(String val) {
        tempDescription = val;
    }
    public String getSelectedCube() {
        return selectedCube;
    }
    public void setSelectedCube(String val) {
        if(cubeInfo.get(val)!=null) {
            cubeId = cubeInfo.get(val).getFirst();
            filename = cubeInfo.get(val).getSecond();    
        }

        selectedCube = val;
    }
    public void setSelectedSession(String selectedSession) {
        this.selectedSession = selectedSession;
    }
    public String getSelectedSession() {
        return selectedSession;
    }
    public void setTempSaveName(String tempSaveName) {
        
        this.tempSaveName = tempSaveName;
    }
    public String getTempSaveName() {
        
        return tempSaveName;
    }
    public List<SelectItem> getSessionsForThisModel() {
        //get response from server
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
        "<solapplus>\n" + 
        "    <request call=\"list_cubes\">\n" + 
        "    </request>\n" + 
        "</solapplus>";
        
        String responseText = portClient.executeRequest(request);
        extractor = new ResponseExtractor(responseText);
        
        Vector<String> list = extractor.getSessions(selectedCube);
        sessionsForThisModel = new ArrayList<SelectItem>();
        
        for (int i = 0; i < list.size(); i++) {
            sessionsForThisModel.add(new SelectItem(list.elementAt(i)));
        }
        
        return sessionsForThisModel;
    }
    public void setSaveNameToClicked() {
        tempSaveName = tempSaveName;
    }
    //**************************************************************************
    
    //********************* request interface elements *************************
    private Vector<InterfaceSlice> ISlices = new Vector<InterfaceSlice>();
    public Vector<InterfaceSlice> getISlices() {
        return ISlices;
    }
    private Vector<InterfaceSlice> ISliders = new Vector<InterfaceSlice>();
    public Vector<InterfaceSlice> getISliders() {
        return ISliders;
    }
    private Vector<InterfaceFilter> IFilters = new Vector<InterfaceFilter>();
    public Vector<InterfaceFilter> getIFilters() {
        return IFilters;
    }
    private Vector<InterfaceSpatialSlice> ISpatialSlices = new Vector<InterfaceSpatialSlice>();
    public Vector<InterfaceSpatialSlice> getISpatialSlices() {
        return ISpatialSlices;
    }
    //support table
    private List<InterfaceAttribute> IAttributesST = new ArrayList<InterfaceAttribute>();
    public List<InterfaceAttribute> getIAttributesST() {
        return IAttributesST;
    }
    public void setIAttributesST(List<InterfaceAttribute> IAttributesST) {
        this.IAttributesST = IAttributesST;
    }
    private List<InterfaceMeasure> IMeasuresST = new ArrayList<InterfaceMeasure>();
    public List<InterfaceMeasure> getIMeasuresST() {
        return IMeasuresST;
    }
    public void setIMeasuresST(List<InterfaceMeasure> IMeasuresST) {
        this.IMeasuresST = IMeasuresST;
    }
    private Vector<InterfaceLevel> ILevelsST = new Vector<InterfaceLevel>();
    public Vector<InterfaceLevel> getILevelsST() {
        return ILevelsST;
    }
    //detail table
    private Vector<InterfaceAttribute> IAttributesDT = new Vector<InterfaceAttribute>();
    public Vector<InterfaceAttribute> getIAttributesDT() {
        return IAttributesDT;
    }
    private Vector<InterfaceMeasure> IMeasuresDT = new Vector<InterfaceMeasure>();
    public Vector<InterfaceMeasure> getIMeasuresDT() {
        return IMeasuresDT;
    }
    private Vector<InterfaceLevel> ILevelsDT = new Vector<InterfaceLevel>();
    public Vector<InterfaceLevel> getILevelsDT() {
        return ILevelsDT;
    }
    //**************************************************************************
    
    //******************** fragment generation functions ***********************
    //support table
    public void addMeasureToST(String id, String operator, String name) {
        InterfaceMeasure temp = new InterfaceMeasure(id, operator, name);
        if (!IMeasuresST.contains(temp)) {
            IMeasuresST.add(temp);
        }
    }
    private void removeSTMeasures() {
        for (int i = 0; i < tempSelectedMValues.size(); i++) {
            String name = tempSelectedMValues.get(i).substring(0, tempSelectedMValues.get(i).indexOf("(")-1);
            String operator = tempSelectedMValues.get(i).substring(tempSelectedMValues.get(i).indexOf("(")+1, tempSelectedMValues.get(i).indexOf(")"));
            
            removeMeasureFromST(name, operator);
        }
        
    }
    
    private void removeMeasureFromST(String name, String operator) {
        for (int i = 0; i < IMeasuresST.size(); i++) {
            if ((IMeasuresST.get(i).getName().equals(name)) && (IMeasuresST.get(i).getOperator().equals(operator))) {
                IMeasuresST.remove(i);
            }
        }
    }
    
    public void addAttributeToST(String dimensionId, String levelId, String id, String name) {
        InterfaceAttribute temp = new InterfaceAttribute(dimensionId, levelId, id, name);
        if (!IAttributesST.contains(temp))
            IAttributesST.add(temp);
        System.out.println(temp.getXML());
    }
    private void removeSTAttributes() {
        for (int i = 0; i < tempSelectedAValues.size(); i++) {
            String name = tempSelectedAValues.get(i);
            
            removeAttributeFromST(name);
        }
    }
    private void removeAttributeFromST(String name) {
        for (int i = 0; i < IAttributesST.size(); i++) {
            if (IAttributesST.get(i).getName().compareTo(name) == 0)
                IAttributesST.remove(i);
        }
    }
    
    private void addLevelToST(SOLAPLevel dragged) {
        InterfaceLevel temp = new InterfaceLevel(dragged);
        if (!ILevelsST.contains(temp))
            ILevelsST.add(temp);
        
    }
    
    private void addFirstLevelToST(SOLAPLevel dragged) {
        InterfaceLevel temp = new InterfaceLevel(dragged);
        if (!ILevelsST.contains(temp))
            ILevelsST.add(0,temp);
        
    }
    
    private void removeSTLevels() {
        for (int i = 0; i < tempSelectedLValues.size(); i++) {
            String name = tempSelectedLValues.get(i);
            
            removeLevelFromST(name);
        }
        
    }
    private void removeLevelFromST(String name) {
        for (int i = 0; i < ILevelsST.size(); i++) {
            if (ILevelsST.get(i).getName().compareTo(name) == 0)
                updateFlags(ILevelsST.remove(i));
        }
    }    
    
    //detail table
    public void addMeasureToDT(String id, String operator, String name) {
        InterfaceMeasure temp = new InterfaceMeasure(id, operator, name);
        if (!IMeasuresDT.contains(temp)) {
            IMeasuresDT.add(temp);
        }
        System.out.println(temp.getXML());
    }
    public void addAttributeToDT(String dimensionId, String levelId, String id, String name) {
        InterfaceAttribute temp = new InterfaceAttribute(dimensionId, levelId, id, name);
        if (!IAttributesDT.contains(temp))
            IAttributesDT.add(temp);
        System.out.println(temp.getXML());
    }
    private void addLevelToDT(String dimensionId, String id, String name, String spatialType) {
        InterfaceLevel temp = new InterfaceLevel(dimensionId, id, name, spatialType);
        if (!ILevelsDT.contains(temp))
            ILevelsDT.add(temp);
        System.out.println(temp.getXML());
    }
    
    //general
    public void addSlice() {
        System.out.println("VAI TENTAR ADICIONAR UM SLICE");
        
        if (!ISlices.contains(tempSemanticSlice)) {
            ISlices.add(tempSemanticSlice);
        }
        if (!ISliders.contains(tempSemanticSlice)) {
            ISliders.add(tempSemanticSlice);
        }
        System.out.println("NAO SEI O QUE SE PASSA");
        
        String thisId;
        
        if (!editSlice) {
            SOLAPAttribute dragged = (SOLAPAttribute)tempDrop.getDragValue();
            thisId = dragged.getId();
        }
        else
            thisId = tempId;
        
        System.out.println("NAO SEI O QUE SE PASSA 22: " + ISlices.size());
        
        //update interface object
        if (!tempSlider) {
            for (int i = 0; i < ISlices.size(); i++) {
                
                System.out.println("This Id: " + thisId);
                System.out.println("SLICES: " + ISlices.elementAt(i).getId());
                if (ISlices.elementAt(i).getId().compareTo(thisId) == 0) {
                    ISlices.elementAt(i).setFilterName(tempFilterName);
                    ISlices.elementAt(i).setFilterDescription(tempFilterDesc);
                    ISlices.elementAt(i).setValues(tempSelectedValues);
                    ISlices.elementAt(i).setSlider(false);
                    ISlices.elementAt(i).setSliderTextValue("");
                }
            }
            System.out.println("NAO SEI O QUE SE PASSA 33");
            //remove from slider
            for (int i = 0; i < ISliders.size(); i++) {
                if (ISliders.elementAt(i).getId().compareTo(thisId) == 0) {
                    ISliders.remove(i);
                }
            }
            System.out.println("NAO SEI O QUE SE PASSA 44");
        }
        if (tempSlider) {
            for (int i = 0; i < ISliders.size(); i++) {
                if (ISliders.elementAt(i).getId().compareTo(thisId) == 0) {
                    ISliders.elementAt(i).setFilterName(tempFilterName);
                    ISliders.elementAt(i).setFilterDescription(tempFilterDesc);
                    ISliders.elementAt(i).setValues(tempSelectedValues);
                    ISliders.elementAt(i).setSlider(true);
                    //ISliders.elementAt(i).setSliderValue(0);
                }
            }
            System.out.println("NAO SEI O QUE SE PASSA 55");
            //remove from slices
            for (int i = 0; i < ISlices.size(); i++) {
                if (ISlices.elementAt(i).getId().compareTo(thisId) == 0) {
                    ISlices.remove(i);
                }
            }
        }
        System.out.println("NAO SEI O QUE SE PASSA 66");
        //set slider to false again
        tempSlider = false;
        tempFilterName = "filter";
        tempFilterDesc = "Enter a description";
        tempSelectedValues = null;
        tempDrop = null;
        editSlice = false;
        
        System.out.println("ADICIONA SLICE AHAHAHAH");
        MainBean myBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
        System.out.println("VAI EXECUTAR O PEDIDO");
        myBean.execute();
    }
    
    public void removeSlice() {
        for (int i = 0; i < ISlices.size(); i++) {
            if (ISlices.elementAt(i).getFilterName().compareTo(tempFilterName) == 0) {
                ISlices.remove(i);
            }
        }
        for (int i = 0; i < ISliders.size(); i++) {
            if (ISliders.elementAt(i).getFilterName().compareTo(tempFilterName) == 0) {
                ISliders.remove(i);
            }
        }
        
        tempFilterName = "filter";
        tempFilterDesc = "Enter a description";
        tempSelectedValues = null;
        tempDrop = null;
        editSlice = false;
        
        MainBean myBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
        myBean.execute();
    }
    
    public void addSpatialSlice() {
        if (!ISpatialSlices.contains(tempSpatialSlice)) {
            ISpatialSlices.add(tempSpatialSlice);
        }
        
        String thisId;
        
        if (!editSpatialSlice) {
            SOLAPAttribute dragged = (SOLAPAttribute)tempDrop.getDragValue();
            thisId = dragged.getId();
        }
        else
            thisId = tempId;
        
        //update interface object
        for (int i = 0; i < ISpatialSlices.size(); i++) {
            if ((ISpatialSlices.elementAt(i).getId().compareTo(thisId) == 0) && (ISpatialSlices.elementAt(i).getLayerId().compareTo(tempLayerId) == 0)) {
                ISpatialSlices.elementAt(i).setFilterName(tempFilterName);
                ISpatialSlices.elementAt(i).setFilterDescription(tempFilterDesc);
                ISpatialSlices.elementAt(i).setOperator(tempOperator);
                ISpatialSlices.elementAt(i).setValue(tempValue);
                ISpatialSlices.elementAt(i).setUnit(tempUnit);
            }
        }
        
        //set default variables
        tempSlider = false;
        tempFilterName = "filter";
        tempFilterDesc = "Enter a description";
        tempSelectedValues = null;
        tempOperator = null;
        tempValue = null;
        tempUnit = null;
        tempDrop = null;
        tempSpatialAttribute = false;
        tempSpatialObject = false;
        editSpatialSlice = false;
        
        MainBean myBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
        myBean.execute();
    }
    
    public void removeSpatialSlice() {
        for (int i = 0; i < ISpatialSlices.size(); i++) {
            if (ISpatialSlices.elementAt(i).getFilterName().compareTo(tempFilterName) == 0) {
                ISpatialSlices.remove(i);
            }
        }
        
        tempSlider = false;
        tempFilterName = "filter";
        tempFilterDesc = "Enter a description";
        tempSelectedValues = null;
        tempOperator = null;
        tempValue = null;
        tempUnit = null;
        tempDrop = null;
        tempSpatialAttribute = false;
        tempSpatialObject = false;
        editSpatialSlice = false;
        
        MainBean myBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
        myBean.execute();
    }
    public void addFieldFilter() {
        if (!IFilters.contains(tempMeasureFilter)) {
            IFilters.add(tempMeasureFilter);
        }
        
        String thisId;
        
        if (!editFilter) {
            SOLAPMeasure dragged = (SOLAPMeasure)tempDrop.getDragValue();
            thisId = dragged.getId();
        }
        else
            thisId = tempId;
        
        String request1 = CommProtocolUtils.buildFieldFilter(thisId, tempOperator1, tempValue1);
        String request2 = CommProtocolUtils.buildFieldFilter(thisId, tempOperator2, tempValue2);
        
        System.out.println(request1);
        System.out.println(request2);
        
        //update interface object
        for (int i = 0; i < IFilters.size(); i++) {
            if (IFilters.elementAt(i).getId().compareTo(thisId) == 0) {
                IFilters.elementAt(i).setFilterName(tempFilterName);
                IFilters.elementAt(i).setFilterDescription(tempFilterDesc);
                IFilters.elementAt(i).setOperator1(tempOperator1);
                IFilters.elementAt(i).setOperator2(tempOperator2);
                IFilters.elementAt(i).setValue1(tempValue1);
                IFilters.elementAt(i).setValue2(tempValue2);
            }
        }
        
        //set default variables
        tempSlider = false;
        tempFilterName = "filter";
        tempFilterDesc = "Enter a description";
        tempSelectedValues = null;
        tempOperator1 = null;
        tempOperator2 = null;
        tempValue1 = "0";
        tempValue2 = "0";
        tempDrop = null;
        editFilter = false;
        
        MainBean myBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
        myBean.execute();
    }
    
    public void removeFilter() {
        for (int i = 0; i < IFilters.size(); i++) {
            if (IFilters.elementAt(i).getFilterName().compareTo(tempFilterName) == 0) {
                IFilters.remove(i);
            }
        }
        
        tempSlider = false;
        tempFilterName = "filter";
        tempFilterDesc = "Enter a description";
        tempSelectedValues = null;
        tempOperator1 = null;
        tempOperator2 = null;
        tempValue1 = "";
        tempValue2 = "";
        tempDrop = null;
        editFilter = false;
        
        MainBean myBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
        myBean.execute();
    }
    //**************************************************************************
    
    //SOLAP+ XML Request Generation Functions
    public String generateRequestST() {
        //prologue
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                                "<solapplus>" +
                                "<request call=\"get_data\">" +
                                "<params cubeId=\"" + cubeId + "\" filename=\"" + filename + "\" spatial=\"true\"/>";
        
        //add levels
        for (int i = 0; i < ILevelsST.size(); i++) {
            request += ILevelsST.elementAt(i).getXML();
        }
        
        //add attributes
        for (int i = 0; i < IAttributesST.size(); i++) {
            request += IAttributesST.get(i).getXML();
        }
        //add measures
        for (int i = 0; i < IMeasuresST.size(); i++) {
            request += IMeasuresST.get(i).getXML();
        }
        
        for (int i = 0; i < ISlices.size(); i++) {
            request += ISlices.elementAt(i).getXML();
        }
        for (int i = 0; i < ISliders.size(); i++) {
            request += ISliders.elementAt(i).getXML();
        }
        for (int i = 0; i < ISpatialSlices.size(); i++) {
            request += ISpatialSlices.elementAt(i).getXML();
        }
        for (int i = 0; i < IFilters.size(); i++) {
            request += IFilters.elementAt(i).getXML();
        }
 
        infoClustering.setZoomLevel(mapInfo.getZoomLevel()+"");
        request += infoClustering.getXML();
        
        //epilogue
        request += "</request>" +
                   "</solapplus>";
        
        return request;
    }
    
    public String generateRequestDT(String assocAttr1, String assocAttr2) {
        //prologue
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                                "<solapplus>" +
                                "<request call=\"get_data\">" +
                                "<params cubeId=\"" + cubeId + "\" filename=\"" + filename + "\" spatial=\"false\"/>";
        
        boolean isGroup1 = assocAttr1.contains("Group");
        boolean isGroup2 = assocAttr2.contains("Group");
        
        
        System.out.println("DETALHE DE GRUPO: " + (isGroup1 || isGroup2));
        
        request += lowerLevelData((isGroup1 || isGroup2));
        
        //Determine if is to request detail data to the finer level of granularity
        boolean detailGroup = false;
        int counterHigher = 0;
        
        //Means the user want see detail information about one group
        System.out.println("PEDIDO PARA TABELA DE DETALHE DE UM GRUPO");
        MainBean myBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
        
        Iterator<ITriple<String, String, String>> levels = infoClustering.getLevelsInAnalisys();
        
        
        
        if(levels.hasNext()) {
            if(isGroup1 || (ILevelsST.size() == 2 && !newSpatialObjects)) {
                ITriple<String, String, String> temp = levels.next();
                //TODO: TESTE  A NOVA FUNCAO
                try {
                    counterHigher = isToRestrictHigherAttr(myBean.getTableUtils().getInLineAttributes(), 0);
                } catch(Exception e) { 
                    //TODO 
                }
                detailGroup = isToDetail(0);
                request += CommProtocolUtils.buildDetailGroup(temp.getSecond(), temp.getThird(), isGroup1, assocAttr1, detailGroup, counterHigher);
            }
        }
        
        if(levels.hasNext()) {
            if(isGroup2 || (ILevelsST.size() == 2 && !newSpatialObjects)) {
                ITriple<String, String, String> temp = levels.next();
                //TODO: TESTE  A NOVA FUNCAO
                try {
                    counterHigher = isToRestrictHigherAttr(myBean.getTableUtils().getInLineAttributes(), 1);
                } catch(Exception e) { 
                    //TODO 
                }
                
                detailGroup = isToDetail(1);
                request += CommProtocolUtils.buildDetailGroup(temp.getSecond(), temp.getThird(), isGroup2, assocAttr2, detailGroup, counterHigher);        
            }
        }
        
        //epilogue
        request += "</request>" +
                   "</solapplus>";
        
        return request;
    }
    
    private String lowerLevelData(boolean clustering) {
        String request = "";
        
        //add levels
        for (int i = 0; i < ILevelsST.size(); i++) {
            request += ILevelsST.elementAt(i).getXML();
        }
        for (int i = 0; i < ILevelsDT.size(); i++) {
            request += ILevelsDT.elementAt(i).getXML();
        }
        //add attributes
        for (int i = 0; i < IAttributesST.size(); i++) {
            request += IAttributesST.get(i).getXML();
        }
        for (int i = 0; i < IAttributesDT.size(); i++) {
            request += IAttributesDT.elementAt(i).getXML();
        }
        //add measures
        for (int i = 0; i < IMeasuresST.size(); i++) {
            request += IMeasuresST.get(i).getXML();
        }
        for (int i = 0; i < IMeasuresDT.size(); i++) {
            request += IMeasuresDT.elementAt(i).getXML();
        }
        
        for (int i = 0; i < ISlices.size(); i++) {
            if(toAddRequest(ISlices.elementAt(i), clustering))
                request += ISlices.elementAt(i).getXML();
        }
        for (int i = 0; i < ISliders.size(); i++) {
            request += ISliders.elementAt(i).getXML();
        }
        for (int i = 0; i < ISpatialSlices.size(); i++) {
            request += ISpatialSlices.elementAt(i).getXML();
        }
        for (int i = 0; i < IFilters.size(); i++) {
            request += IFilters.elementAt(i).getXML();
        }
        
        infoClustering.setZoomLevel(mapInfo.getZoomLevel()+"");
        request += infoClustering.getXML();
        
           
        return request;
    }
    
    public void show() {
        for (int i = 0; i < IMeasuresST.size(); i++) {
            System.out.println(IMeasuresST.get(i).getXML());
        }
        
        MainBean mainBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
        mainBean.execute();
    }
    
    public void removeElements() {
        removeSTMeasures();
        removeSTAttributes();
        removeSTLevels();
        
        tempAvailableM.clear();
        tempAvailableA.clear();
        tempAvailableL.clear();
        
        
        MainBean mainBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
        mainBean.execute();
    }
    
    public void setEditSlice() {
        //get parameters from click action
        FacesContext context = FacesContext.getCurrentInstance();  
        Map requestMap = context.getExternalContext().getRequestParameterMap();  
        String sliceName = (String)requestMap.get("activeSlice");
        //get active InterfaceSlice
        InterfaceSlice slice = EditUtils.getInterfaceSlice(ISlices, sliceName);
        
        //case it's a slider
        if (slice == null) {
            slice = EditUtils.getInterfaceSlice(ISliders, sliceName);
        }
        
        //set the component binds back to the saved values
        tempId = slice.getId();
        tempName = slice.getName();
        tempFilterName = slice.getFilterName();
        tempFilterDesc = slice.getFilterDescription();
        tempLevelId = slice.getLevelId();
        tempDimensionId = slice.getDimensionId();
        tempSlider = slice.isSlider();
        
        tempSemanticSlice = new InterfaceSlice(tempDimensionId, tempLevelId, tempId, tempName);
        
        //get response from server
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
        "<solapplus>\n" + 
        "    <request call=\"get_distincts\">\n" + 
        "        <params cubeId=\"" + cubeId + "\" filename=\"" + filename + "\"/>\n" + 
        "        <distinct levelId=\""+ tempLevelId + "\" attributeId=\"" + tempId + "\" dimensionId=\"" + tempDimensionId + "\"/>\n";
        
        for (int i = 0; i < ISlices.size(); i++)
            if(ISlices.elementAt(i).getId() != tempId)
                request += ISlices.elementAt(i).getXML();
        
        for (int i = 0; i < ISliders.size(); i++) {
            if(ISliders.elementAt(i).getId() != tempId)
                request += ISliders.elementAt(i).getXML();
        }
        for (int i = 0; i < ISpatialSlices.size(); i++) request += ISpatialSlices.elementAt(i).getXML();
        for (int i = 0; i < IFilters.size(); i++) request += IFilters.elementAt(i).getXML();
              
        request += "    </request>\n" + 
        "</solapplus>";
        
        String responseText = portClient.executeRequest(request);
        extractor = new ResponseExtractor(responseText);
        
        
        Vector<String> values = extractor.getDistinctValues();
        
        //reset tempDistincts
        tempDistincts = new ArrayList<SelectItem>();
        for (int i = 0; i < values.size(); i++) {
            tempDistincts.add(new SelectItem(values.elementAt(i)));
        }
        
        //convert strings to selectItem
        List<String> selectedValues = slice.getValues();
        
        //reset tempDistincts
        tempSelectedValues = new ArrayList<String>();
        for (int i = 0; i < selectedValues.size(); i++) {
            tempSelectedValues.add(selectedValues.get(i));
        }
        
        editSlice = true;
    }
    
    public void setEditSpatialSlice() {
        //get parameters from click action
        FacesContext context = FacesContext.getCurrentInstance();  
        Map requestMap = context.getExternalContext().getRequestParameterMap();  
        String sliceName = (String)requestMap.get("activeSpatialSlice");
        //get active InterfaceSlice
        InterfaceSpatialSlice slice = EditUtils.getInterfaceSpatialSlice(ISpatialSlices, sliceName);
        
        //set the component binds back to the saved values
        tempId = slice.getId();
        tempName = slice.getName();
        tempFilterName = slice.getFilterName();
        tempFilterDesc = slice.getFilterDescription();
        tempLevelId = slice.getLevelId();
        tempDimensionId = slice.getDimensionId();
        tempLayerId = slice.getLayerId();
        tempOperator = slice.getOperator();
        tempUnit = slice.getUnit();
        tempValue = slice.getValue();
        
        editSpatialSlice = true;
    }
    
    public void setEditFilter() {
        //get parameters from click action
        FacesContext context = FacesContext.getCurrentInstance();  
        Map requestMap = context.getExternalContext().getRequestParameterMap();  
        String sliceName = (String)requestMap.get("activeFilter");
        //get active InterfaceSlice
        InterfaceFilter slice = EditUtils.getInterfaceFilter(IFilters, sliceName);
        
        //set the component binds back to the saved values
        tempId = slice.getId();
        tempName = slice.getName();
        tempFilterName = slice.getFilterName();
        tempFilterDesc = slice.getFilterDescription();
        tempOperator1 = slice.getOperator1();
        tempOperator2 = slice.getOperator2();
        tempValue1 = slice.getValue1();
        tempValue2 = slice.getValue2();
        editFilter = true;
    }
    
    public void cancelSlice() {
        for(String value: tempSelectedValues)
            System.out.println("CANCEL SLICE: " + value.length());
            
        editSlice = false;
        tempSemanticSlice = null;
        tempName = "";
        tempFilterName = "filter";
        tempFilterDesc = "Enter a description";
        tempSelectedValues = new Vector<String>();
        tempSlider = false;
    }
    
    public void cancelSpatialSlice() {
        editSpatialSlice = false;
        tempSpatialSlice = null;
        tempName = "";
        tempFilterName = "filter";
        tempFilterDesc = "Enter a description";
        tempValue  = "";
        tempUnit = "";
        tempOperator = "";
        tempLayerName = "";
    }
    
    public void cancelFilter() {
        editFilter = false;
        tempMeasureFilter = null;
        tempName = "";
        tempFilterName = "filter";
        tempFilterDesc = "Enter a description";
        tempOperator1 = "";
        tempValue1 = "";
        tempOperator2 = "";
        tempValue2 = "";
    }
    
    //************** accessors for manager bean ********************
    public ResponseExtractor getExtractor() {
        return extractor;
    }
    
    public void setExtractor (ResponseExtractor re) {
        extractor = re;
    }
    
    public void setXmlUtils(XMLUtils xmlUtils) {
        this.xmlUtils = xmlUtils;
    }
    
    public XMLUtils getXmlUtils() {
        return xmlUtils;
    }
    
    public void setTempDrop(DropEvent tempDrop) {
        this.tempDrop = tempDrop;
    }
    
    public DropEvent getTempDrop() {
        return tempDrop;
    }
    
    public String getTempDimensionId() {
        return tempDimensionId;
    }
    
    public String getTempLevelId() {
        return tempLevelId;
    }
    
    public String getTempLayerName1() {
        return tempLayerName;
    }
    
    public void setTempLayerName1(String s) {
        tempLayerName = s;
    }
    
    public boolean isTempSpatialSliceReady1() {
        return tempSpatialSliceReady;
    }
    
    public void setCaptionsST(Vector<String> captionsST) {
        this.captionsST = captionsST;
    }
    
    public Vector<String> getCaptionsST() {
        return captionsST;
    }
    
    public void setCaptionsDT(Vector<String> captionsDT) {
        this.captionsDT = captionsDT;
    }
    
    public Vector<String> getCaptionsDT() {
        return captionsDT;
    }
    
    public void setCaption(String caption) {
        this.caption = caption;
    }
    
    public String getCaption1() {
        return caption;
    }
    
    public void setCaptionDT(String captionDT) {
        this.captionDT = captionDT;
    }
    
    public String getCaptionDT1() {
        return captionDT;
    }
    
    public void setLayers(Vector<SOLAPLayer> v) {
        layers = v;
    }
    
    public void setLayersLabel(Vector<SOLAPLayer> v) {
        layersLabel = v;
    }
    
    public void setLayersObject(Vector<SOLAPLayer> v) {
        layersObject = v;
    }
    
    public void setLayerNames(Vector<SelectItem> layerNames) {
        this.layerNames = layerNames;
    }
    
    public void setISlices(Vector<InterfaceSlice> ISlices) {
        this.ISlices = ISlices;
    }
    
    public void setISliders(Vector<InterfaceSlice> ISliders) {
        this.ISliders = ISliders;
    }
    
    public void setIFilters(Vector<InterfaceFilter> IFilters) {
        this.IFilters = IFilters;
    }
    
    public void setISpatialSlices(Vector<InterfaceSpatialSlice> ISpatialSlices) {
        this.ISpatialSlices = ISpatialSlices;
    }
    
    public void setILevelsST(Vector<InterfaceLevel> ILevelsST) {
        this.ILevelsST = ILevelsST;
    }
    
    public void setIAttributesDT(Vector<InterfaceAttribute> IAttributesDT) {
        this.IAttributesDT = IAttributesDT;
    }
    
    public void setIMeasuresDT(Vector<InterfaceMeasure> IMeasuresDT) {
        this.IMeasuresDT = IMeasuresDT;
    }
    
    public void setILevelsDT(Vector<InterfaceLevel> ILevelsDT) {
        this.ILevelsDT = ILevelsDT;
    }
    
    public String getCubeId() {
        return cubeId;
    }
    
    public String getFilename() {
        return filename;
    }
    
    public SOLAPMapserver getMapInfo() {
        return mapInfo;
    }
    
    public void setMapInfo(SOLAPMapserver mapInfo) {
        this.mapInfo = mapInfo;
    }
    //*************************************************************
    

    public ClusteringManager getInfoClustering() {
        return infoClustering;
    }
    
    public void setZoom(String zoom) {
        mapInfo.setZoomLevel(zoom);   
    }

    public void setClustering(boolean clustering) {
        this.clustering = clustering;
        
        if(!infoClustering.getModeClustering().equals("NO")) this.clustering = true;
        else this.clustering = false;
    }

    public boolean isClustering() {
        if(!infoClustering.getModeClustering().equals("NO")) this.clustering = true;
        else this.clustering = false;
        
        return clustering;
    }

    private ITriple<Boolean, Integer, String> drillUpDown(SOLAPLevel dragged) {
        ITriple<Boolean, Integer, String> result = new Triple<Boolean, Integer, String>();
        
        result.setFirst(false);
        boolean sameDimension = false;
        boolean sameHierarchy = false;
        
        int index = 0;        
        for(int i = 0; i < ILevelsST.size(); i++) {
            //If in support table exist one level from same dimension
            if(dragged.getDimensionId().equals(ILevelsST.get(i).getDimensionId())) {
                sameDimension = true;
                index = i;
                for(int j = 0; j < dimensions.size(); j++) {
                    if(dragged.getDimensionId().equals(dimensions.get(j).getId())) {
                        Vector<SOLAPHierarchy> hierarchies = dimensions.get(j).getHierarchies();
                        
                        for(SOLAPHierarchy hierarchy: hierarchies) {
                            //Get the hierarchy of new spatial attribute
                            if(hierarchy.containsLevel(dragged.getId())) {
                                Iterator<InterfaceLevel> it = ILevelsST.iterator();
                                while(it.hasNext()) {
                                    InterfaceLevel next = it.next();
                                    if(hierarchy.containsLevel(next.getId())) {
                                        it.remove();
                                        sameHierarchy = true;
                                        result.setFirst(true);
                                        result.setSecond(i);
                                    }
                                }
                            }
                        }
                    }
                }
                
                //See if the level is a spatial level
                if(!dragged.getSpatialAttr().equals("")) {
                    if(sameDimension && !sameHierarchy) {
                        boolean polygon = true;
                        for(InterfaceLevel level: ILevelsST) {
                            polygon &= level.getSpatialType().equals("polygon");
                        }
                        if(polygon)
                            newSpatialObjects = true;
                    }
                }
            }
        }
        
        return result;
    }
    
    public String getColumnRefInST() {
        return columnRefInST;
    }

    public boolean isNewSpatialObjects() {
        return newSpatialObjects;
    }

    public void setLabelGroupsChecked(boolean labelGroups) {
        this.labelGroupsChecked = labelGroups;
    }

    public boolean isLabelGroupsChecked() {
        return labelGroupsChecked;
    }

    public String getLabelGroup1() {
        return labelGroup1;
    }

    public String getLabelGroup2() {
        return labelGroup2;
    }

    private void updateInfoToLabels(SOLAPLevel levelDragged, ITriple<Boolean, Integer, String> infoOperation) {
        if(infoOperation.getFirst()) {
               String[] values = columnRefInST.split(",");
               if(values.length < 2) {
                   columnRefInST = levelDragged.getColumnRefDisplayAttribute();
               }
               else {
                   if(infoOperation.getSecond() == 0) columnRefInST = levelDragged.getColumnRefDisplayAttribute() + "," + values[1];
                   if(infoOperation.getSecond() == 1) columnRefInST = values[0] + "," + levelDragged.getColumnRefDisplayAttribute();
               }
        }
        else {
            if(columnRefInST.equals(""))
                columnRefInST = levelDragged.getColumnRefDisplayAttribute();
            else columnRefInST += "," + levelDragged.getColumnRefDisplayAttribute();
        }
        
        if(labelGroup1.equals("")) labelGroup1 = "Label Group " + levelDragged.getName();
        else labelGroup2 = "Label Group " + levelDragged.getName();
    }

    public void setLabelGroups1Checked(boolean LabelGroups1Checked) {
        this.labelGroups1Checked = LabelGroups1Checked;
    }

    public boolean isLabelGroups1Checked() {
        return labelGroups1Checked;
    }

    public void setLabelGroups2Checked(boolean LabelGroups2Checked) {
        this.labelGroups2Checked = LabelGroups2Checked;
    }

    public boolean isLabelGroups2Checked() {
        return labelGroups2Checked;
    }

    public void setColumnRefInST(String columnRefInST) {
        this.columnRefInST = columnRefInST;
    }

    public void setLabelGroup1(String labelGroup1) {
        this.labelGroup1 = labelGroup1;
    }

    public void setLabelGroup2(String labelGroup2) {
        this.labelGroup2 = labelGroup2;
    }

    public void setNewSpatialObjects(boolean newSpatialObjects) {
        this.newSpatialObjects = newSpatialObjects;
    }
    
    
   private boolean isToDetail(int i) {
        String dimensionIdST = ILevelsST.elementAt(i).getDimensionId();
        String levelIdST = ILevelsST.elementAt(i).getId();
        String attributeIdST = "";
        
        System.out.println("DIMENSION SUPPORT TABLE: " + i + " -- " + dimensionIdST);
        System.out.println("LEVEL SUPPORT TABLE: " + i + " -- " + levelIdST);
        
        //Find attribute id support table
        for(SOLAPDimension dim: dimensions) {
            if(dim.getId().equals(dimensionIdST)) {
                for(SOLAPLevel level: dim.getLevels()) {
                    if(level.getId().equals(levelIdST)) {
                        attributeIdST = level.getDisplayAttributeID();
                        System.out.println("DISPLAY ATTRIBUTE: " + attributeIdST);
                    }
                }
            }
        }
        
        for(InterfaceAttribute attr: IAttributesDT) {
            if(attr.getDimensionId().equals(dimensionIdST) && attr.getLevelId().equals(levelIdST) && attr.getId().equals(attributeIdST))
                return true;
        }
        
        return false;
    }
    
    private int isToRestrictHigherAttr(Vector<String> inLineAttributes, int i) {
        int counter = 0;
        for(InterfaceAttribute attr: IAttributesST) {
            if(inLineAttributes.contains(attr.getName())) {
                if(attr.getDimensionId().equals(ILevelsST.get(i).getDimensionId()))
                    counter++;
            }
        }
        
        return counter;
    }
    

    private void updateFlags(InterfaceLevel levelRemoved) {
        updateNewSpatialObjects(levelRemoved);
        updateColumnRefInST();
    }
    
    private void updateNewSpatialObjects(InterfaceLevel levelRemoved) {
        //Update the flag which indicates we are present in case of intersection
        if(!levelRemoved.getSpatialType().equals("")) {
            for(InterfaceLevel levelST: ILevelsST) {
                if(levelRemoved.getDimensionId().equals(levelST.getDimensionId()) && newSpatialObjects) {
                    newSpatialObjects = false;   
                }
            }
        }
    }
                    
    private void updateColumnRefInST() {
        columnRefInST = "";
        
        if(ILevelsST.size() > 0) {
            columnRefInST = ILevelsST.elementAt(0).getDisplayAttributeIDColRef();
            for(int i = 1; i < ILevelsST.size(); i++)
                columnRefInST = "," + ILevelsST.elementAt(i).getDisplayAttributeIDColRef();
        }
    }

    public void setInfoClustering(ClusteringManager infoClustering) {
        this.infoClustering = infoClustering;
    }

    //This method remove the conflited slice. When we have define a slice for the same attribute in support table
    //we need to give priority to the new slice ("when we click in element of support table")
    private boolean toAddRequest(InterfaceSlice interfaceSlice, boolean clustering) {
        if(clustering)
            return true;

        for(InterfaceLevel level: ILevelsST) {
            System.out.println("TO ADD REQUEST: "+ level.getDisplayAttributeID() + " ----- " + interfaceSlice.getId()); 
            if(!level.getDisplayAttributeID().equals(interfaceSlice.getId()))
                return true;
        }
        return false;
    }
    
    
    
    //*************************************************************************
    //Summarization Control
    //*************************************************************************
    
    private SumarizationManager infoSummarization = new SumarizationManager();
    
    private HtmlDataTable dataAST;
    private Vector<SOLAPAttributeHierarchies> attributesSumarizationTable = new Vector<SOLAPAttributeHierarchies>();
    private HtmlDataTable dataMST;
    private Vector<SumarizationMeasure> measuresSumarizationTable = new Vector<SumarizationMeasure>();
    
    private Vector<SOLAPFactTable> factTables = new Vector<SOLAPFactTable>();
    private Vector<SOLAPFactTable> factTablesToUse = new Vector<SOLAPFactTable>();
    
    private List<SelectItem> factTableItems = new ArrayList<SelectItem>();
    private List<String> factTablesSelectedValues = new ArrayList<String>();
    
    private boolean diferentDimensions;
    private boolean noMeasures;
    
    public Vector<SOLAPFactTable> getFactTableToSave(){
        return factTables;
    }
    
    public void processDropSumarizationTable(DropEvent dropEvent) {
        //dropped an attribute
        if (dropEvent.getDragType().compareTo("attribute") == 0) {
            SOLAPAttribute dragged = (SOLAPAttribute) dropEvent.getDragValue();
            SOLAPAttributeHierarchies att = new SOLAPAttributeHierarchies(dragged.getDimensionId(),dragged.getLevelId(),dragged.getId(),dragged.getName());
            att.setHierarchies(getHierarchiesForAttribute(dragged));
            for(SOLAPAttributeHierarchies a : attributesSumarizationTable)
                if(!a.getDimensionId().equals(dragged.getDimensionId()))
                    diferentDimensions=true;
            
            att.setSpatial(isSpatial(dragged));
            att.setPreComputed(getPreComputingSharedBorders(dragged));
            
            addAttributeToSumarizationTable(att);
        }
        //dropped a measure
        else if (dropEvent.getDragType().compareTo("measure") == 0) {
            SOLAPMeasure dragged = (SOLAPMeasure) dropEvent.getDragValue();
            FacesContext context = FacesContext.getCurrentInstance();  
            Map requestMap = context.getExternalContext().getRequestParameterMap();  
            String agg = (String)requestMap.get("operator");
            addMeasureToSumarizationTable(dragged.getId(), agg, dragged.getName());
        }
        //dropped a measure with no operator
        else if (dropEvent.getDragType().compareTo("measure_noop") == 0) {
            SOLAPMeasure dragged = (SOLAPMeasure) dropEvent.getDragValue();
            String agg = "noop";
            addMeasureToSumarizationTable(dragged.getId(), agg, dragged.getName());
        }
        //System.out.println("************************************");
        //System.out.println("VALORES NAS TABELAS:");
        //System.out.println("ATTRIBUTES: " + attributesSumarizationTable.size());
        //System.out.println("MEASURES: " + measuresSumarizationTable.size());
        //System.out.println("************************************");
    }
    
    private SOLAPPreComputingSharedBorders getPreComputingSharedBorders(SOLAPAttribute dragged){
        SOLAPPreComputingSharedBorders result = null;
        for(SOLAPDimension dim : dimensions){
            if(dim.getId().equals(dragged.getDimensionId())){
                for(SOLAPLevel lev : dim.getLevels()){
                    if(lev.getId().equals(dragged.getLevelId())){
                        result = lev.getPreComputingSharedBorders();
                        return result;
                    }
                }
            }
        }
        return result;
    }
    
    private boolean isSpatial(SOLAPAttribute dragged){
        boolean result = false;
        for(SOLAPDimension dim : dimensions){
            if(dim.getId().equals(dragged.getDimensionId())){
                for(SOLAPLevel lev : dim.getLevels()){
                    if(lev.getId().equals(dragged.getLevelId())){
                        result = !lev.getSpatialAttr().isEmpty();
                        return result;
                    }
                }
            }
        }
        return result;
    }
    
    private void addAttributeToSumarizationTable(SOLAPAttributeHierarchies att) {
        if (!attributesSumarizationTable.contains(att))
            attributesSumarizationTable.add(att);
    }
    
    public void addMeasureToSumarizationTable(String id, String operator, String name) {
        String comp_name = name;
        if(!operator.equals("noop"))
            comp_name = operator + "(" + name + ")";
        SumarizationMeasure mea = new SumarizationMeasure(id, operator, comp_name);
        if (!measuresSumarizationTable.contains(mea))
            measuresSumarizationTable.add(mea);
    }
    
    
    public void removeFromAST() {
        int index = dataAST.getRowIndex();
        attributesSumarizationTable.remove(index);
        diferentDimensions=false;
        for(int i=0;i<attributesSumarizationTable.size();i++)
            for(int j=i+1;j<attributesSumarizationTable.size();j++)
                if(!attributesSumarizationTable.get(i).getDimensionId().equals(attributesSumarizationTable.get(j).getDimensionId()))
                    diferentDimensions=true;
    }
    
    public void removeFromMST() {
        int i = dataMST.getRowIndex();
        measuresSumarizationTable.remove(i);
    }
    
    public void printInfo(){
        System.out.println(infoSummarization.getGeneralizationType());
        System.out.println(infoSummarization.getRefinementHierarchies());
        System.out.println(infoSummarization.getUseConceptsFrom());
        System.out.println(infoSummarization.getDefineLabels());
    }
    
    private SOLAPDimension getDimensionById(String dimId){
        for(SOLAPDimension dim : dimensions)
            if(dim.getId()==dimId)
                return dim;
        return null;
    }
    
    private Vector<SOLAPAttributeHierarchy> getHierarchiesForAttribute(SOLAPAttribute att) {
        Vector<SOLAPAttributeHierarchy> vec = new Vector<SOLAPAttributeHierarchy>();
        SOLAPDimension dim = getDimensionById(att.getDimensionId());
        for(SOLAPHierarchy hierarchy : dim.getHierarchies()){
            if(hierarchy.containsLevel(att.getLevelId())){
                SOLAPAttributeHierarchy attHierarchy = new SOLAPAttributeHierarchy(att.getName(), hierarchy.getType());
                boolean found= false;
                for(SOLAPLevel hierarchyLevel : hierarchy.getLevels()){
                    if(found)
                        attHierarchy.addAttribute(hierarchyLevel.getAttributeRefDisplayAttribute());
                    else{
                        if(hierarchyLevel.getId().equals(att.getLevelId())){
                            attHierarchy.addAttribute(hierarchyLevel.getAttributeRefDisplayAttribute());
                            found=true;
                        }
                    }
                }
                attHierarchy.setDescription(hierarchy.getName());
                if(!attHierarchy.getAttributesFromHierarchy().isEmpty())
                    vec.add(attHierarchy);
            }
        }
        return vec;
    }
    
    public void printChosenHierarchies(){
        System.out.println("***************************************************************");
        System.out.println("Tenho este numero de atributos: " + attributesSumarizationTable.size());
        System.out.println("Hierarquias escolhidas:");
        for(SOLAPAttributeHierarchies hierarchies : attributesSumarizationTable)
            if(hierarchies.getHaveHierarchy())
                System.out.println(hierarchies.getSelectedAttributeHierarchy().getDescription());
        System.out.println("***************************************************************");
        addFactTables();
    }
    
    public String generateGeneralizationXML(){
        String request = 
            "<solapplus>\n" + 
                "<request call=\"generalize\">\n" + 
                    "<params cubeId=\"" + cubeId + "\" filename=\"" + filename + "\"/>\n";
        infoSummarization.setZoomLevel(mapInfo.getZoomLevel()+"");
        request += infoSummarization.generateOptionsXML();
        
        for(SOLAPAttributeHierarchies h : attributesSumarizationTable)
            request += h.generateChosenHierarchiesXML();
        for(SumarizationMeasure m : measuresSumarizationTable)
            request += m.generateGeneralizationXML();
        for(SOLAPFactTable f: factTablesToUse)
            request += f.generateGeneralizationXML();
                    
        request += "</generalize>\n";
        for (int i = 0; i < ISlices.size(); i++) {
            request += ISlices.elementAt(i).getXML() + "\n";
        }
        request += "</request>\n" + "</solapplus>";
        return request;
    }
    
    public void startGeneralization(){
        MainBean myBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
        myBean.executeGeneralization(infoSummarization.getGeneralizationType());
    }
    
    private void getFactTables(){
        
        String requestText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
        "<solapplus>\n" + 
        "    <request call=\"get_factTables\">\n" + 
        "        <params cubeId=\"" + cubeId + "\" filename=\"" + filename + "\" />\n" + 
        "    </request>\n" + 
        "</solapplus>";
        
        String responseText = portClient.executeRequest(requestText); 
        System.out.println("A resposta foi esta: " + responseText);
        extractor = new ResponseExtractor(responseText);
        
        factTables = extractor.getFactTables();
        factTableItems = new Vector<SelectItem>();
        
        for(SOLAPFactTable ft : factTables){
            SelectItem item = new SelectItem();
            item.setLabel(ft.getName());
            item.setValue(ft.getId());
            factTableItems.add(item);
        }
        if(factTableItems.size() == 1)
            factTablesSelectedValues.add((String)factTableItems.get(0).getValue());
    }
    
    public void addFactTables(){
        factTablesToUse = new Vector<SOLAPFactTable>();
        for(String v : factTablesSelectedValues){
            for(SOLAPFactTable f : factTables){
                if(f.getId().equals(v)){
                    factTablesToUse.add(f);
                }
            }
        }
    }

    //Gets and Sets

    public void setAttributesSumarizationTable(Vector<SOLAPAttributeHierarchies> attributesSumarizationTable) {
        this.attributesSumarizationTable = attributesSumarizationTable;
    }

    public Vector<SOLAPAttributeHierarchies> getAttributesSumarizationTable() {
        return attributesSumarizationTable;
    }

    public void setMeasuresSumarizationTable(Vector<SumarizationMeasure> measuresSumarizationTable) {
        this.measuresSumarizationTable = measuresSumarizationTable;
    }

    public Vector<SumarizationMeasure> getMeasuresSumarizationTable() {
        return measuresSumarizationTable;
    }

    public void setDataAST(HtmlDataTable dataAST) {
        this.dataAST = dataAST;
    }

    public HtmlDataTable getDataAST() {
        return dataAST;
    }

    public void setDataMST(HtmlDataTable dataMST) {
        this.dataMST = dataMST;
    }

    public HtmlDataTable getDataMST() {
        return dataMST;
    }

    public void setInfoSumarization(SumarizationManager infoSumarization) {
        this.infoSummarization = infoSumarization;
    }

    public SumarizationManager getInfoSumarization() {
        return infoSummarization;
    }

    public void setFactTables(Vector<SOLAPFactTable> factTables) {
        this.factTables = factTables;
    }

    public Vector<SOLAPFactTable> getFactTables1() {
        return factTables;
    }

    public void setFactTableItems(List<SelectItem> factTableItems) {
        this.factTableItems = factTableItems;
    }

    public List<SelectItem> getFactTableItems() {
        if(cubeId!=null && factTables.size()==0)
            getFactTables();
        return factTableItems;
    }

    public void setDiferentDimensions(boolean diferentDimensions) {
        this.diferentDimensions = diferentDimensions;
    }

    public boolean isDiferentDimensions() {
        return diferentDimensions;
    }

    public void setFactTablesSelectedValues(List<String> factTablesSelectedValues) {
        this.factTablesSelectedValues = factTablesSelectedValues;
    }

    public List<String> getFactTablesSelectedValues() {
        return factTablesSelectedValues;
    }

    public void setNoMeasures(boolean noMeasures) {
        this.noMeasures = noMeasures;
    }

    public boolean isNoMeasures() {
        noMeasures = measuresSumarizationTable.isEmpty();
        return noMeasures;
    }

    public void setFactTablesToUse(Vector<SOLAPFactTable> factTablesToUse) {
        this.factTablesToUse = factTablesToUse;
    }

    public Vector<SOLAPFactTable> getFactTablesToUse() {
        return factTablesToUse;
    }
}
