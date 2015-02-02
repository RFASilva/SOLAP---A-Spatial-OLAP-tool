package solap.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import solap.SOLAPServerPortClient;

import solap.entities.InterfaceAttribute;
import solap.entities.InterfaceFilter;
import solap.entities.InterfaceLevel;
import solap.entities.InterfaceMeasure;
import solap.entities.InterfaceSlice;
import solap.entities.InterfaceSpatialSlice;
import solap.entities.SOLAPAttributeHierarchies;
import solap.entities.SOLAPDimension;
import solap.entities.SOLAPFactTable;
import solap.entities.SOLAPLayer;
import solap.entities.SOLAPMapserver;
import solap.entities.SOLAPMeasure;
import solap.entities.SOLAPPreComputingDistance;

import solap.entities.SumarizationMeasure;

import solap.utils.ITriple;

public class ManagerBean {
    public void reset() {
        //setup bean access
        MainBean mainBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
        SessionBean sessionBean = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");
        
        //*********** main bean reset ***********
        mainBean.setRequestText(null);
        mainBean.setResponseText(null);
        mainBean.setResponseTextDT(null);
        mainBean.setExtractor(null);
        mainBean.setExtractorDT(null);
        mainBean.setMeasureList(null);
        mainBean.setHeaderList(null);
        mainBean.setHeaderList2(null);
        mainBean.setData(null);
        mainBean.setDetailData(null);
        mainBean.setStubIndex(null);
        
        mainBean.setNumberOfHeaderAttributes(0);
        mainBean.setNumberOfInLineAttributes(0);
        mainBean.setColspanHeader1(0);
        mainBean.setColspanHeader2(0);
        
        //spatial data
        mainBean.setSqlQuery(null);
        mainBean.setGeometryType(null);
        mainBean.setNumberOfRows(0);
        mainBean.setNumberOfBars(0);
        mainBean.setNumberOfAttribs(0);
        
        //detail table
        mainBean.setTableUtils(null);
        mainBean.setHighlighted(null);
        mainBean.setDetailHeaders(null);
        mainBean.setRowdata0(null);
        mainBean.setRowdata1(null);
        //**************************************
        
        //********** session bean reset **********
        //TODO cubeId and filename reset
        sessionBean.setExtractor(null);
        sessionBean.setXmlUtils(null);
        sessionBean.setTempDrop(null);
        sessionBean.setTempAggOp(null);
        sessionBean.setTempId(null);
        sessionBean.setTempDimensionId(null);
        sessionBean.setTempLevelId(null);
        sessionBean.setTempName(null);
        sessionBean.setTempLayerId(null);
        sessionBean.setTempLayerName1(null);
        sessionBean.setTempFilterName("filter");
        sessionBean.setTempFilterDesc("Enter a description");
        sessionBean.setTempOperator(null);
        sessionBean.setTempOperator1(null);
        sessionBean.setTempOperator2(null);
        sessionBean.setTempValue(null);
        sessionBean.setTempValue1("0");
        sessionBean.setTempValue2("0");
        sessionBean.setTempUnit(null);
        
        sessionBean.setTempDistincts(new ArrayList<SelectItem>());
        sessionBean.setTempSelectedValues(new ArrayList<String>());
        sessionBean.setTempSlider(false);
        sessionBean.setTempSpatialSliceReady(false);
        sessionBean.setTempSpatialAttribute(false);
        sessionBean.setTempSpatialObject(false);
        
        sessionBean.setCaptionsST(new Vector<String>());
        sessionBean.setCaptionsDT(new Vector<String>());
        sessionBean.setCaption(null);
        sessionBean.setCaptionDT(null);
        sessionBean.setEditSlice(false);
        sessionBean.setEditSpatialSlice(false);
        sessionBean.setEditFilter(false);
        
        sessionBean.setDimensions(new Vector<SOLAPDimension>());
        sessionBean.setMeasures(new Vector<SOLAPMeasure>());
        sessionBean.setLayers(new Vector<SOLAPLayer>());
        sessionBean.setLayersLabel(new Vector<SOLAPLayer>());
        sessionBean.setLayersObject(new Vector<SOLAPLayer>());
        sessionBean.setLayerNames(new Vector<SelectItem>());
        
        //save();
        sessionBean.setISlices(new Vector<InterfaceSlice>());
        sessionBean.setISliders(new Vector<InterfaceSlice>());
        sessionBean.setIFilters(new Vector<InterfaceFilter>());
        sessionBean.setISpatialSlices(new Vector<InterfaceSpatialSlice>());
        sessionBean.setIAttributesST(new Vector<InterfaceAttribute>());
        sessionBean.setIMeasuresST(new Vector<InterfaceMeasure>());
        sessionBean.setILevelsST(new Vector<InterfaceLevel>());
        sessionBean.setIAttributesDT(new Vector<InterfaceAttribute>());
        sessionBean.setIMeasuresDT(new Vector<InterfaceMeasure>());
        sessionBean.setILevelsDT(new Vector<InterfaceLevel>());
        //****************************************
        
        
        
        //load();
    }
    
    public void save() {

        //setup bean access
        SessionBean sessionBean =
            (SessionBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");

        //save objects
        // Write to disk with FileOutputStream
        FileOutputStream f_out;
        ObjectOutputStream obj_out;

        // Write object with ObjectOutputStream
        try {
            f_out = new FileOutputStream(sessionBean.getTempSaveName());
            obj_out = new ObjectOutputStream(f_out);
            obj_out.writeObject(sessionBean.getISlices());
            obj_out.writeObject(sessionBean.getISliders());
            obj_out.writeObject(sessionBean.getIFilters());
            obj_out.writeObject(sessionBean.getISpatialSlices());
            obj_out.writeObject(sessionBean.getIAttributesST());
            obj_out.writeObject(sessionBean.getIMeasuresST());
            obj_out.writeObject(sessionBean.getILevelsST());
            obj_out.writeObject(sessionBean.getIAttributesDT());
            obj_out.writeObject(sessionBean.getIMeasuresDT());
            obj_out.writeObject(sessionBean.getILevelsDT());
            obj_out.writeObject(sessionBean.getMapInfo());
            
            obj_out.writeObject(sessionBean.getColumnRefInST());
            obj_out.writeObject(sessionBean.getLabelGroup1());
            obj_out.writeObject(sessionBean.getLabelGroup2());            
            obj_out.writeObject(sessionBean.isClustering());
            obj_out.writeObject(sessionBean.isEditFilter());
            obj_out.writeObject(sessionBean.isEditSlice());
            obj_out.writeObject(sessionBean.isEditSpatialSlice());
            obj_out.writeObject(sessionBean.isLabelGroups1Checked());
            obj_out.writeObject(sessionBean.isLabelGroups2Checked());
            obj_out.writeObject(sessionBean.isLabelGroupsChecked());
            obj_out.writeObject(sessionBean.isNewSpatialObjects());
            
            //Save clustering information
            obj_out.writeObject(sessionBean.getInfoClustering().getBaseLevel());
            obj_out.writeObject(sessionBean.getInfoClustering().getCurrentRestrictedLevel1());
            obj_out.writeObject(sessionBean.getInfoClustering().getCurrentRestrictedLevel2());
            obj_out.writeObject(sessionBean.getInfoClustering().getGroupsParameter());
            obj_out.writeObject(sessionBean.getInfoClustering().getHierarchies1());
            obj_out.writeObject(sessionBean.getInfoClustering().getHierarchies2());
            obj_out.writeObject(sessionBean.getInfoClustering().getHierarchyChosen1());
            obj_out.writeObject(sessionBean.getInfoClustering().getHierarchyChosen2());
            obj_out.writeObject(sessionBean.getInfoClustering().getLevelsInAnalysis());
            obj_out.writeObject(sessionBean.getInfoClustering().getModeClustering());
            obj_out.writeObject(sessionBean.getInfoClustering().getVariant());
            obj_out.writeObject(sessionBean.getInfoClustering().getZoomLevel());
            
            // Save summarization information
            obj_out.writeObject(sessionBean.getInfoSumarization().getCharaterizationByMeasures());
            obj_out.writeObject(sessionBean.getInfoSumarization().getDefineLabels());
            obj_out.writeObject(sessionBean.getInfoSumarization().getDistinct());
            obj_out.writeObject(sessionBean.getInfoSumarization().getGeneralizationType());
            obj_out.writeObject(sessionBean.getInfoSumarization().getRefinementHierarchies());
            obj_out.writeObject(sessionBean.getInfoSumarization().getUseConceptsFrom());
            obj_out.writeObject(sessionBean.getInfoSumarization().getZoomLevel());
            obj_out.writeObject(sessionBean.getInfoSumarization().getGroupsParameter());
            
            obj_out.writeObject(sessionBean.getInfoSumarization().getTempCharaterizationByMeasures());
            obj_out.writeObject(sessionBean.getInfoSumarization().getTempDefineLabels());
            obj_out.writeObject(sessionBean.getInfoSumarization().getTempDistinct());
            obj_out.writeObject(sessionBean.getInfoSumarization().getTempGeneralizationType());
            obj_out.writeObject(sessionBean.getInfoSumarization().getTempRefinementHierarchies());
            obj_out.writeObject(sessionBean.getInfoSumarization().getTempUseConceptsFrom());
            
            // From the session bean
            obj_out.writeObject(sessionBean.getAttributesSumarizationTable());
            obj_out.writeObject(sessionBean.getMeasuresSumarizationTable());
            obj_out.writeObject(sessionBean.getFactTableToSave());
            obj_out.writeObject(sessionBean.getFactTablesToUse());
            obj_out.writeObject(sessionBean.getFactTableItems());
            obj_out.writeObject(sessionBean.getFactTablesSelectedValues());
            obj_out.writeObject(sessionBean.isDiferentDimensions());
            obj_out.writeObject(sessionBean.isNoMeasures());
            
            // From the main bean
            MainBean mainBean = (MainBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
            obj_out.writeObject(mainBean.isSummarization());
            
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND EXCEPTION\n" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO EXCEPTION\n" + e.getMessage());
        }

        //add new session to cubes.xml
        SOLAPServerPortClient portClient = new SOLAPServerPortClient();
        String requestText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
        "<solapplus>\n" + 
        "    <request call=\"save_session\">\n" + 
        "        <params cubeId=\"" + sessionBean.getCubeId() + "\" filename=\"" + sessionBean.getTempSaveName() + "\" />\n" + 
        "    </request>\n" + 
        "</solapplus>";
        
        portClient.executeRequest(requestText);
    }
    
    public void load(String session) {
        //setup bean access
        SessionBean sessionBean = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");
        
        //load objects
        // Read from disk using FileInputStream
        FileInputStream f_in;
        ObjectInputStream obj_in;
        Vector<InterfaceMeasure> obj;

        // Read object using ObjectInputStream
        try {
            f_in = new FileInputStream(session);
            obj_in = new ObjectInputStream (f_in);
            
            //obj_out.writeObject(sessionBean.getISlices());
            Vector<InterfaceSlice> obj2 = (Vector<InterfaceSlice>)obj_in.readObject();
            sessionBean.setISlices(obj2);
            
            //obj_out.writeObject(sessionBean.getISliders());
            Vector<InterfaceSlice> obj3 = (Vector<InterfaceSlice>)obj_in.readObject();
            sessionBean.setISliders(obj3);
            
            //obj_out.writeObject(sessionBean.getIFilters());
            Vector<InterfaceFilter> obj4 = (Vector<InterfaceFilter>)obj_in.readObject();
            sessionBean.setIFilters(obj4);
            
            //obj_out.writeObject(sessionBean.getISpatialSlices());
            Vector<InterfaceSpatialSlice> obj5 = (Vector<InterfaceSpatialSlice>)obj_in.readObject();
            sessionBean.setISpatialSlices(obj5);
            
            //obj_out.writeObject(sessionBean.getIAttributesST());
            Vector<InterfaceAttribute> obj6 = (Vector<InterfaceAttribute>)obj_in.readObject();
            sessionBean.setIAttributesST(obj6);
            
            //obj_out.writeObject(sessionBean.getIMeasuresST());
            obj = (Vector<InterfaceMeasure>)obj_in.readObject();
            sessionBean.setIMeasuresST(obj);
            
            //obj_out.writeObject(sessionBean.getILevelsST());
            Vector<InterfaceLevel> obj7 = (Vector<InterfaceLevel>)obj_in.readObject();
            sessionBean.setILevelsST(obj7);
            
            //obj_out.writeObject(sessionBean.getIAttributesDT());
            Vector<InterfaceAttribute> obj8 = (Vector<InterfaceAttribute>)obj_in.readObject();
            sessionBean.setIAttributesDT(obj8);
            
            //obj_out.writeObject(sessionBean.getIMeasuresST());
            Vector<InterfaceMeasure> obj9 = (Vector<InterfaceMeasure>)obj_in.readObject();
            sessionBean.setIMeasuresDT(obj9);
            
            //obj_out.writeObject(sessionBean.getILevelsST());
            Vector<InterfaceLevel> obj10 = (Vector<InterfaceLevel>)obj_in.readObject();
            sessionBean.setILevelsDT(obj10);
            
            //obj_out.writeObject(sessionBean.getMapInfo());
            SOLAPMapserver obj11 = (SOLAPMapserver) obj_in.readObject();
            sessionBean.setMapInfo(obj11);
            
            sessionBean.setColumnRefInST((String) obj_in.readObject());
            sessionBean.setLabelGroup1((String) obj_in.readObject());
            sessionBean.setLabelGroup2((String) obj_in.readObject());
            sessionBean.setClustering((Boolean) obj_in.readObject());            
            sessionBean.setEditFilter((Boolean) obj_in.readObject());
            sessionBean.setEditSlice((Boolean) obj_in.readObject());
            sessionBean.setEditSpatialSlice((Boolean) obj_in.readObject());
            sessionBean.setLabelGroups1Checked((Boolean) obj_in.readObject());
            sessionBean.setLabelGroups2Checked((Boolean) obj_in.readObject());
            sessionBean.setLabelGroupsChecked((Boolean) obj_in.readObject());
            sessionBean.setNewSpatialObjects((Boolean) obj_in.readObject()); 
            
            //Load Clustering information
            sessionBean.getInfoClustering().setBaseLevel((Integer) obj_in.readObject());
            sessionBean.getInfoClustering().setCurrentRestrictedLevel1((String) obj_in.readObject());
            sessionBean.getInfoClustering().setCurrentRestrictedLevel2((String) obj_in.readObject());
            sessionBean.getInfoClustering().setGroupsParameter((Integer)obj_in.readObject());
            sessionBean.getInfoClustering().setHierarchies1((SelectItem[]) obj_in.readObject());
            sessionBean.getInfoClustering().setHierarchies2((SelectItem[]) obj_in.readObject());
            sessionBean.getInfoClustering().setHierarchyChosen1((String) obj_in.readObject() );
            sessionBean.getInfoClustering().setHierarchyChosen2((String) obj_in.readObject() );
            sessionBean.getInfoClustering().setLevelsInAnalysis((Map<ITriple<String, String, String>,SOLAPPreComputingDistance>)obj_in.readObject() );
            sessionBean.getInfoClustering().setModeClustering((String) obj_in.readObject() );
            sessionBean.getInfoClustering().setVariant((String) obj_in.readObject() );
            sessionBean.getInfoClustering().setZoomLevel((String) obj_in.readObject() );
            
            
            // Loas summarization information
            sessionBean.getInfoSumarization().setCharaterizationByMeasures((String)obj_in.readObject());
            sessionBean.getInfoSumarization().setDefineLabels((String)obj_in.readObject());
            sessionBean.getInfoSumarization().setDistinct((String)obj_in.readObject());
            sessionBean.getInfoSumarization().setGeneralizationType((String)obj_in.readObject());
            sessionBean.getInfoSumarization().setRefinementHierarchies((String)obj_in.readObject());
            sessionBean.getInfoSumarization().setUseConceptsFrom((String)obj_in.readObject());
            sessionBean.getInfoSumarization().setZoomLevel((String)obj_in.readObject());
            sessionBean.getInfoSumarization().setGroupsParameter((Integer)obj_in.readObject());
            
            sessionBean.getInfoSumarization().setTempCharaterizationByMeasures((String)obj_in.readObject());
            sessionBean.getInfoSumarization().setTempDefineLabels((String)obj_in.readObject());
            sessionBean.getInfoSumarization().setTempDistinct((String)obj_in.readObject());
            sessionBean.getInfoSumarization().setTempGeneralizationType((String)obj_in.readObject());
            sessionBean.getInfoSumarization().setTempRefinementHierarchies((String)obj_in.readObject());
            sessionBean.getInfoSumarization().setTempUseConceptsFrom((String)obj_in.readObject());
            
            // From the session bean
            
            Vector<SOLAPAttributeHierarchies> SummObj1 = (Vector<SOLAPAttributeHierarchies>)obj_in.readObject();
            sessionBean.setAttributesSumarizationTable(SummObj1);
            
            Vector<SumarizationMeasure> SummObj2 = (Vector<SumarizationMeasure>)obj_in.readObject();
            sessionBean.setMeasuresSumarizationTable(SummObj2);
            
            Vector<SOLAPFactTable> SummObj3 = (Vector<SOLAPFactTable>)obj_in.readObject();
            sessionBean.setFactTables(SummObj3);
            
            Vector<SOLAPFactTable> SummObj4 = (Vector<SOLAPFactTable>)obj_in.readObject();
            sessionBean.setFactTablesToUse(SummObj4);
            
            List<SelectItem> SummObj5 = (List<SelectItem>)obj_in.readObject();
            sessionBean.setFactTableItems(SummObj5);
            
            List<String> SummObj6 = (List<String>)obj_in.readObject();
            sessionBean.setFactTablesSelectedValues(SummObj6);
            
            sessionBean.setDiferentDimensions((Boolean) obj_in.readObject());
            sessionBean.setNoMeasures((Boolean) obj_in.readObject());
            
            // From the main bean
            MainBean mainBean = (MainBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
            mainBean.setSummarization((Boolean) obj_in.readObject());
            
            
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND EXCEPTION\n" + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("IO EXCEPTION\n" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("CLASS NOT FOUND EXCEPTION\n" + e.getMessage());
        }
    }
}
