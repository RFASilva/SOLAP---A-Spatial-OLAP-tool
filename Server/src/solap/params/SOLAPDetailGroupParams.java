package solap.params;

public class SOLAPDetailGroupParams {
    
    private String dimensionId;
    private String levelId;
    private String groupId;
    
    private String value = "";
    private String detail;

    public SOLAPDetailGroupParams(String dimensionId, String levelId,
                                  String groupId, String detail) {
        this.dimensionId = dimensionId;
        this.levelId = levelId;
        this.groupId = groupId;
        this.detail = detail;
    }
    
    public SOLAPDetailGroupParams(String value) {
        this.value = value;
    }

    public String getDimensionId() {
        return dimensionId;
    }

    public String getLevelId() {
        return levelId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getDetail() {
        return detail;
    }
}
