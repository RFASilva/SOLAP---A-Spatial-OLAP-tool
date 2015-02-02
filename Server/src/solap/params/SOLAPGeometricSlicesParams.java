package solap.params;

public class SOLAPGeometricSlicesParams {
    
    //required
    private String attributeId;
    private String levelId;
    private String dimensionId;
    private String gType;
    private String gSrid;
    private String gPoint;
    private String gInfo;
    private String gOrdinates;

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getDimensionId() {
        return dimensionId;
    }

    public void setGType(String gType) {
        this.gType = gType;
    }

    public String getGType() {
        return gType;
    }

    public void setGSrid(String gSrid) {
        this.gSrid = gSrid;
    }

    public String getGSrid() {
        return gSrid;
    }

    public void setGPoint(String gPoint) {
        this.gPoint = gPoint;
    }

    public String getGPoint() {
        return gPoint;
    }

    public void setGInfo(String gInfo) {
        this.gInfo = gInfo;
    }

    public String getGInfo() {
        return gInfo;
    }

    public void setGOrdinates(String gOrdinates) {
        this.gOrdinates = gOrdinates;
    }

    public String getGOrdinates() {
        return gOrdinates;
    }
}
