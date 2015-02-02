package solap.entities;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import solap.bean.SessionBean;

public class SOLAPAttributeHierarchies implements Serializable {
    
    private String dimensionId;
    private String levelId;
    private String attId;
    private String attName;
    private Vector<SOLAPAttributeHierarchy> hierarchies;
    private Boolean haveHierarchy; 
    private Object selectedHierarchy;
    private SOLAPAttributeHierarchy selectedAttributeHierarchy;
    private List<SelectItem> items;
    private String threshold;
    
    private boolean spatial;
    private SOLAPPreComputingSharedBorders preComputed;
    
    public SOLAPAttributeHierarchies(String dimensionId, String levelId, String attId, String attName) {
        this.dimensionId = dimensionId;
        this.levelId = levelId;
        this.attId = attId;
        this.attName = attName;
        hierarchies = new Vector<SOLAPAttributeHierarchy>();
        selectedHierarchy=0;
    }

    public void setAttId(String attId) {
        this.attId = attId;
    }

    public String getAttId() {
        return attId;
    }

    public void setAttName(String attName) {
        this.attName = attName;
    }

    public String getAttName() {
        return attName;
    }

    public void setHierarchies(Vector<SOLAPAttributeHierarchy> hierarchies) {
        this.hierarchies = hierarchies;
    }

    public Vector<SOLAPAttributeHierarchy> getHierarchies() {
        return hierarchies;
    }

    public void setItems(List<SelectItem> items) {
        this.items = items;
    }

    public List<SelectItem> getItems() {
        if(items==null){
            items = new ArrayList<SelectItem>();
            for(int i=0; i<hierarchies.size(); i++){
                SOLAPAttributeHierarchy h = hierarchies.get(i);
                SelectItem item = new SelectItem();
                item.setLabel(h.getDescription());
                item.setValue(i);
                items.add(item);
            }
            selectedHierarchy = items.get(0).getValue();
            selectedAttributeHierarchy = hierarchies.get(Integer.parseInt(selectedHierarchy.toString()));
        }
        return items;
    }

    public void setSelectedHierarchy(Object selectedHierarchy) {
        this.selectedHierarchy = selectedHierarchy;
    }

    public Object getSelectedHierarchy() {
        return selectedHierarchy;
    }
    
    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getThreshold() {
        return threshold;
    }
    
    public String generateChosenHierarchiesXML(){
        String request = "<attribute id=\"" + attId + "\" name=\"" + attName + "\" dimensionId=\"" + dimensionId +"\" levelId=\"" + levelId +"\" threshold=\"" + threshold + "\" >\n";
        boolean isSpatialGeneralization = ((SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean")).getInfoSumarization().getGeneralizationType().equals("SPATIAL");
        
        if(haveHierarchy && ((isSpatialGeneralization && spatial) || !spatial))
            request += selectedAttributeHierarchy.generateUpperLevelAtributesXML();
        
        if(!isSpatialGeneralization){
            request += "<preProcessing ";
            if(preComputed != null){
                request += " sharedBordersPreComputed=\"" + "true" + "\"";
                request += " tableRef=\"" + preComputed.getTableId() + "\"";
            }
            else 
                request += " sharedBordersPreComputed=\"" + "false" + "\"";
            request += "/>";
        }

        request += "</attribute>\n";
        return request;
    }


    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getDimensionId() {
        return dimensionId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setSelectedAttributeHierarchy(SOLAPAttributeHierarchy selectedAttributeHierarchy) {
        this.selectedAttributeHierarchy = selectedAttributeHierarchy;
    }

    public SOLAPAttributeHierarchy getSelectedAttributeHierarchy() {
        System.out.println("getSelectedAttributeHierarchy");
        System.out.println(selectedHierarchy.toString());
        System.out.println(hierarchies.size());
        selectedAttributeHierarchy = hierarchies.get(Integer.parseInt(selectedHierarchy.toString()));
        return selectedAttributeHierarchy;
    }

    public void setHaveHierarchy(Boolean haveHierarchy) {
        this.haveHierarchy = haveHierarchy;
    }

    public Boolean getHaveHierarchy() {
        haveHierarchy = !hierarchies.isEmpty();
        return haveHierarchy;
    }

    public void setSpatial(boolean spatial) {
        this.spatial = spatial;
    }

    public boolean isSpatial() {
        return spatial;
    }

    public void setPreComputed(SOLAPPreComputingSharedBorders preComputed) {
        this.preComputed = preComputed;
    }

    public SOLAPPreComputingSharedBorders getPreComputed() {
        return preComputed;
    }
}
