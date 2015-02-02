package solap.clustering.support;

import java.awt.geom.Point2D;

import java.util.LinkedList;
import java.util.List;
import oracle.spatial.geometry.JGeometry;

public class Instance {
    
    //Holds the definition for polygon given by spatial oracle
    private static final int TYPE_POLYGON1 = 3;
    private static final int TYPE_POLYGON2 = 7;
    
    //Holds the definition for polygon given by spatial oracle
    private static final int TYPE_POINT = 1;
    
    //Holds the semantic attribute associated to the spatial object
    private String semancticAttribute;
    //Holds the higher attributes values if they are present
    private List<String> higherValues;
    //Holds the spatial object
    private JGeometry spatialObject;
    //Holds the attribute in higher spatial level if he is present
    private String attributeHigherLevel;
    
    //In case de spatial object is a polygon, use the Polygon class as auxiliary
    private Polygon polygon;
    
    //This characterization is formed by the semantic values associated to the spatial object
    private String characterization;
    
    public Instance(String semancticAttribute, JGeometry spatialObject) {
        this.semancticAttribute = semancticAttribute;
        this.spatialObject = spatialObject;
        this.higherValues = new LinkedList<String>();
        
        //System.out.println("GUARDA O POLÍGONO 0: " + spatialObject.getType());
        
       // In case of spatial object is a polygon
        if(spatialObject.getType() == TYPE_POINT) {
            this.spatialObject = spatialObject;
        }
        else if(spatialObject.getType() == TYPE_POLYGON1 || spatialObject.getType() == TYPE_POLYGON2) {
            //System.out.println("GUARDA O POLÍGONO");
            this.spatialObject = spatialObject;
            polygon = new Polygon(spatialObject.getOrdinatesArray());
        }
    }
    
    public void addHigherValue(String value) {
        higherValues.add(value);
    }

    public JGeometry getSpatialObject() {
        return spatialObject;
    }
    
    //In case of spatial object is a point
    public double getX() {
        return spatialObject.getJavaPoint().getX();
    }
    
    public double getY() {
        return spatialObject.getJavaPoint().getY();
    }
    
    public int numberAttributes() {
        //we are only deal with 2 coordinates (x, y)
        return 2;
    }
    
    public Point2D getPoint() {
        return spatialObject.getJavaPoint();
    }
    
    public double getCoordinate(int index) {
        if(index == 0)
            return getX();
        if(index == 1)
            return getY();
        return -1;
    }

    public String getSemancticAttribute() {
        return semancticAttribute;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Instance)) {
            return false;
        }
        final Instance other = (Instance)object;
        if (!(semancticAttribute == null ? other.semancticAttribute == null : semancticAttribute.equals(other.semancticAttribute))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((semancticAttribute == null) ? 0 : semancticAttribute.hashCode());
        return result;
    }

    public List<String> getHigherValues() {
        return higherValues;
    }

    public void setAttributeHigherLevel(String attributeHigherLevel) {
        this.attributeHigherLevel = attributeHigherLevel;
    }

    public String getAttributeHigherLevel() {
        return attributeHigherLevel;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setCharacterization(String characterization) {
        this.characterization = characterization;
    }

    public String getCharacterization() {
        return characterization;
    }
}
