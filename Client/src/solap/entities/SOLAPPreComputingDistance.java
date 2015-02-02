package solap.entities;

import java.io.Serializable;

public class SOLAPPreComputingDistance implements Serializable {
    
    private String tableId;
    private String fromId;
    private String toId;
    private String distanceId;


    public SOLAPPreComputingDistance(String tableId, String fromId,
                                     String toId, String distanceId) {
        this.tableId = tableId;
        this.fromId = fromId;
        this.toId = toId;
        this.distanceId = distanceId;
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

    public String getDistanceId() {
        return distanceId;
    }
}
