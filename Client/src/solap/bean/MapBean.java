package solap.bean;

import javax.faces.context.FacesContext;

public class MapBean {

    private String zoom = "";
    private String x = "";
    private String y = "";
    private String maxZoom = "";
    
    private boolean initZ = true;
    private boolean initX = true;
    private boolean initY = true;
    
    public MapBean() {
        
    }

    public void setZoom(String zoom) {
        System.out.println("set zoom: " + zoom);
        this.zoom = zoom;
        
        SessionBean sessionBean = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");
        sessionBean.setZoom(zoom);
        
        MainBean mainBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
        mainBean.execute();
    }

    public String getZoom() {
        if(initZ) {
            SessionBean sessionBean = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");
            System.out.println("OBTER ZOOM CLASS MAPBEAN");
            if(sessionBean.getMapInfo()!= null) {
                System.out.println("OBTER ZOOM CLASS MAPBEAN NÃO É NULL");
                zoom = sessionBean.getMapInfo().getZoomLevel()+"";
                initZ = false;
            }
        }
        System.out.println("ZOOM no MAPBEAN" + zoom);
        return zoom;
    }
    
    public void setX(String x) {
        System.out.println("SET X: " + x);
        this.x = x;
        SessionBean sessionBean = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");
        
        if(sessionBean != null) {
            if(sessionBean.getMapInfo() != null ){
                if(this.x != null) {
                    if(!this.x.equals(""))
                        sessionBean.getMapInfo().setCenterx(this.x);
                }
            }
        }
    }

    public String getX() {
        if(initX) {
            SessionBean sessionBean = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");
            System.out.println("OBTER GET_X CLASS MAPBEAN");
            if(sessionBean.getMapInfo() != null) {
                System.out.println("OBTER GET_X CLASS MAPBEAN NÃO É NULL");
                x = sessionBean.getMapInfo().getCenterx()+"";
                initX = false;
            }
        }
        
        System.out.println("COORDENADA X" + x);
        return x;
    }

    public void setY(String y) {
        System.out.println("SET Y: " + y);
        this.y = y;
        
        SessionBean sessionBean = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");
        if(sessionBean != null) {
            if(sessionBean.getMapInfo() != null ){
                if(!this.y.equals(""))
                    sessionBean.getMapInfo().setCentery(this.y);
            }
        }
    }

    public String getY() {
        if(initY) {
            SessionBean sessionBean = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");
            System.out.println("OBTER GET_Y CLASS MAPBEAN");
            if(sessionBean.getMapInfo()!= null) {
                System.out.println("OBTER GET_Y CLASS MAPBEAN NÃO É NULL");
                y = sessionBean.getMapInfo().getCentery()+"";
                initY = false;
            }
        }
        
        System.out.println("COORDENADA Y" + y);
        return y;
    }
    
    public void changeZoom() {  
        SessionBean mySession= (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");
        mySession.getMapInfo().setZoomLevel(zoom);
        System.out.println("ZOOM: " + zoom);
    }
    
    public void changeX() {  
        System.out.println("ALTEROU COORD X");  
        SessionBean mySession= (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");
        mySession.getMapInfo().setCenterx(x);
    }
    
    public void changeY() {  
        System.out.println("ALTEROU COORD Y");  
        SessionBean mySession= (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");
        mySession.getMapInfo().setCentery(y);
    }
    
    public void updateMaxZoom() {
        SessionBean mySession= (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");
        mySession.getMapInfo().setMaxZoom(maxZoom);
        System.out.println("MAXZOOM: " + maxZoom);
    }


    public void setMaxZoom(String maxZoom) {
        this.maxZoom = maxZoom;
    }

    public String getMaxZoom() {
        return maxZoom;
    }
}
