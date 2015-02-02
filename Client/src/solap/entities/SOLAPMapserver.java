package solap.entities;

import java.io.Serializable;

public class SOLAPMapserver implements Serializable {
    //Connection Info
    private String host = "";
    private String port = "";
    private String name = "";
    private String datasource = "";
    
    //Map Configuration Info
    private String nameBaseMap = "";
    private float centerx;
    private float centery;
    private int zoomLevel;
    private int SRID;

    private String maxZoom = "";
      
    public SOLAPMapserver() {
        this.zoomLevel = 0;
    }
    
    public SOLAPMapserver(String host, String port, String name, String datasource,
            float x, float y, int zoomLevel, String nameBaseMap, int SRID) {
        this.host = host;
        System.out.println("Host: " + host);
        this.port = port;
        System.out.println("Port: " + port);
        this.name = name;
        System.out.println("name: " + name);
        this.datasource = datasource;
        System.out.println("datasource: " + datasource);
        this.centerx = x;
        System.out.println("centerx: " + centerx);
        this.centery = y;
        System.out.println("centery: " + centery);
        this.zoomLevel = zoomLevel;
        System.out.println("zoomLevel: " + zoomLevel);
        this.nameBaseMap = nameBaseMap;
        System.out.println("nameBaseMap: " + nameBaseMap);
        this.SRID = SRID;
        System.out.println("SRID: " + SRID);
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getDatasource() {
        return datasource;
    }
    
    public String getNameBaseMap() {
        return nameBaseMap;
    }
    
    public float getCenterx() {
        return centerx;
    }
    
    public float getCentery() {
        return centery;
    }
    
    public int getZoomLevel() {
        return zoomLevel;
    }
    
    public int getSRID() {
        return SRID;
    }

    public void setNameBaseMap(String nameBaseMap) {
        this.nameBaseMap = nameBaseMap;
    }

    public void setCenterx(String centerx) {
        System.out.println("SOLAPMAPServer class: faz set centerx");
        this.centerx = Float.parseFloat(centerx);
    }

    public void setCentery(String centery) {
        System.out.println("SOLAPMAPServer class: faz set centery");
        this.centery = Float.parseFloat(centery);
    }

    public void setZoomLevel(String zoomLevel) {
        System.out.println("SOLAPMAPServer class: faz set zoomLevel");
        this.zoomLevel = Integer.parseInt(zoomLevel);
    }

    public void setSRID(int SRID) {
        this.SRID = SRID;
    }

    public void setMaxZoom(String maxZoom) {
        this.maxZoom = maxZoom;
        System.out.println("MAXZOOM: " + maxZoom);
    }

    public String getMaxZoom() {
        return maxZoom;
    }
}
