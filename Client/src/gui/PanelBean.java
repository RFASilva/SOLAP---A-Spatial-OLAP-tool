package gui;

import javax.faces.context.FacesContext;

import solap.bean.MainBean;

public class PanelBean {
    
    //side panels
    boolean displayControlPanel = true;
    boolean displayDataModelPanel = true;
    
    public void switchControlPanel() {
        if (displayControlPanel) {
            displayControlPanel = false;
        }
        else {
            displayControlPanel = true;
        }
    }
    public void switchDataModelPanel() {
        if (displayDataModelPanel) {
            displayDataModelPanel = false;
        }
        else {
            displayDataModelPanel = true;
        }
    }

    public boolean isDisplayControlPanel() {
        return displayControlPanel;
    }
    public boolean isDisplayDataModelPanel() {
        return displayDataModelPanel;
    }
    
    //panels height
    final static int originalMapHeight = 350;
    final static int originalSupportTableHeight = 140;
    final static int originalDetailTableHeight = 90;
    final static int headerSize = 8;
    private int mapHeight = originalMapHeight;
    private int supportTableHeight = originalSupportTableHeight;
    private int detailTableHeight = originalDetailTableHeight;
    private boolean displayDetailTable = true;
    private boolean displaySupportTable = true;
    private boolean displayMap = true;

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }
    public int getMapHeight() {
        return mapHeight;
    }
    public void setSupportTableHeight(int supportTableHeight) {
        this.supportTableHeight = supportTableHeight;
    }
    public int getSupportTableHeight() {
        return supportTableHeight;
    }
    public void setDetailTableHeight(int detailTableHeight) {
        this.detailTableHeight = detailTableHeight;
    }
    public int getDetailTableHeight() {
        return detailTableHeight;
    }
    
    public void switchMap() {
        //retracting
        if (displayMap) {
            displayMap = false;
        }
        //expanding
        else if (!displayMap) {
            displayMap = true;  
        }
        
        setPanelsHeight();
    }
    public void switchSupportTable() {
        //retracting
        if (displaySupportTable) {
            displaySupportTable = false;
        }
        //expanding
        else if (!displaySupportTable) {
            displaySupportTable = true;
        }
        
        setPanelsHeight();
    }
    public void switchDetailTable() {
        //retracting
        if (displayDetailTable) {
            displayDetailTable = false;
        }
        //expanding
        else if (!displayDetailTable) {
            displayDetailTable = true;
        }
        
        setPanelsHeight();
    }
    
    private void setPanelsHeight() {
        mapHeight = getNewMapHeight();
        supportTableHeight = getNewSTHeight();
        detailTableHeight = getNewDTHeight();
    }
    
    private int getNewMapHeight() {
        int height = originalMapHeight;
        
        if (!displayDetailTable)
            height += originalDetailTableHeight-headerSize;
        if (!displaySupportTable)
            height += originalSupportTableHeight-headerSize;
            
        return height;
    }
    private int getNewSTHeight() {
        int height = originalSupportTableHeight;
        
        if (!displayMap) {
            height += (originalMapHeight-headerSize)/2;
            if (!displayDetailTable) {
                height += (originalMapHeight)/2;
                height += originalDetailTableHeight-headerSize;
            }
        }
            
        return height;
    }
    private int getNewDTHeight() {
        int height = originalDetailTableHeight;
        
        if (!displayMap) {
            height += (originalMapHeight-headerSize)/2;
            if (!displaySupportTable) {
                height += (originalMapHeight)/2;
                height += originalSupportTableHeight-headerSize;   
            }
        }
            
        return height;
    }
    
    private boolean pieChart = false;
    
    public void setPieChart(boolean pieChart) {
        this.pieChart = pieChart;
    }

    public boolean getPieChart() {
        return pieChart;
    }
    
    public void switchPieChart() {
        System.out.println("Pie: " + pieChart);
        
        MainBean mainBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
        mainBean.execute();
    }

    public Object switchDisplay() {
        // Add event code here...
        return null;
    }
}
