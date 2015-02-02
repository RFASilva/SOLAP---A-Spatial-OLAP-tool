package solap.entities;

import javax.faces.event.ValueChangeEvent;

public class SOLAPLayer {
    private String id;
    private String title;
    private String name;
    private String columnRef;
    
    private boolean activate = false;
    
    public SOLAPLayer(String id) {
        this.id = id;
    }
    
    public SOLAPLayer(String id, String title) {
        this.id = id;
        this.title = title;
    }
    
    public SOLAPLayer(String id, String title, String name) {
        this.id = id;
        this.title = title;
        this.name = name;
    }

    public SOLAPLayer(String id, String title, String name, String columnRef) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.columnRef = columnRef;
    }

    //accessors
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getColumnRef() {
        return columnRef;
    }

    public boolean isActivate() {
        return activate;
    }
 
    public void changeActivate() {
        System.out.println("ALTEROU O ACTIVATE: "+ activate);
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
        System.out.println("ALTEROU O ACTIVATE FUNCAO SET: "+ activate);
    }
}
