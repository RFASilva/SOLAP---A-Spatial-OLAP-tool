package solap.entities;

import java.util.Vector;

public class SumarizationAttribute {
    
    private String attId;
    private String name;
    private String dimensionId;
    private String levelId;
    private Vector<SOLAPAttributeHierarchy> hierarchies;
    private String threshold;
    private String attributeToId="";
    private String toType;
    
    private String preComputedSharedBorderTableID;
    
    public SumarizationAttribute(String attId, String name, String threshold, String dimensionId, String levelId) {
        this.attId = attId;
        this.name = name;
        this.dimensionId = dimensionId;
        this.levelId = levelId;
        this.threshold = threshold;
        hierarchies = new Vector<SOLAPAttributeHierarchy>();
    }

    public void setAttId(String attId) {
        this.attId = attId;
    }

    public String getAttId() {
        return attId;
    }

    public void setHierarchies(Vector<SOLAPAttributeHierarchy> hierarchies) {
        this.hierarchies = hierarchies;
    }

    public Vector<SOLAPAttributeHierarchy> getHierarchies() {
        return hierarchies;
    }
    
    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void printInfo(){
        System.out.println("Attribute ID = " + attId + " Name = " + name + " DimensioID = " + dimensionId + " LevelID = " + levelId + " threshold = " + threshold );
        if(!attributeToId.equals(""))
            System.out.println("\t Generalize to Attribute with ID: " + attributeToId + " Type: " + toType);
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

    public void setAttributeToId(String attributeToId) {
        this.attributeToId = attributeToId;
    }

    public String getAttributeToId() {
        return attributeToId;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public String getToType() {
        return toType;
    }

    public void setPreComputedSharedBorderTableID(String preComputedSharedBorderTableID) {
        this.preComputedSharedBorderTableID = preComputedSharedBorderTableID;
    }

    public String getPreComputedSharedBorderTableID() {
        return preComputedSharedBorderTableID;
    }
}
