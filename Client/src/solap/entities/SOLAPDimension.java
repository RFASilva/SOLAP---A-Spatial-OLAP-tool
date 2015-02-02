package solap.entities;

import java.util.Vector;

public class SOLAPDimension {
    private String id;
    private String name;
    private Vector<SOLAPLevel> levels;
    private Vector<SOLAPHierarchy> hierarchies;
    
    public SOLAPDimension(String id, String name, Vector<SOLAPLevel> levels, Vector<SOLAPHierarchy> hierarchies) {
        this.id = id;
        this.name = name;
        this.levels = levels;
        this.hierarchies = hierarchies;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLevels(Vector<SOLAPLevel> levels) {
        this.levels = levels;
    }

    public Vector<SOLAPLevel> getLevels() {
        return levels;
    }

    public void setHierarchies(Vector<SOLAPHierarchy> hierarchies) {
        this.hierarchies = hierarchies;
    }

    public Vector<SOLAPHierarchy> getHierarchies() {
        return hierarchies;
    }
}
