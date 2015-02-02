package solap.entities;

import java.io.Serializable;

public class SOLAPPreComputingSharedBorders implements Serializable {
    
    private String tableId;
    private String fromId;
    private String toId;
    private String sharedBorders;


    public SOLAPPreComputingSharedBorders(String tableId, String fromId,
                                     String toId, String sharedBorders) {
        this.tableId = tableId;
        this.fromId = fromId;
        this.toId = toId;
        this.sharedBorders = sharedBorders;
    }


    public String getTableId() {
        return tableId;
    }

    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }

    public String getSharedBorders() {
        return sharedBorders;
    }
}
