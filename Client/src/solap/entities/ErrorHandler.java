package solap.entities;

import java.util.List;
import java.util.Vector;
import solap.utils.ITriple;
import solap.utils.Triple;

public class ErrorHandler {
    
    private boolean error;
    
    private String message;
    private boolean definitionStyle;    
    private boolean data;
    
    
    public ErrorHandler() {
        definitionStyle = false;
        data = false;
        error = false;
    }
    
    private void updateError() {
        if(definitionStyle)
            message = "The style definition for this context was not found";
        else if(data)
            message = "Error in response from the server";
        else
            message = "Error";
    }
    
    private boolean allFalse() {
        return definitionStyle || data; 
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        updateError();
        return message;
    }

    public void setDefinitionStyle(boolean definitionStyle) {
        if(definitionStyle) error = true;
        else if(allFalse()) error = false;
            
        this.definitionStyle = definitionStyle;
    }

    public boolean isDefinitionStyle() {
        return definitionStyle;
    }

    public void setData(boolean data) {
        if(data) error = true;
        else if(allFalse()) error = false;
        
        this.data = data;
    }

    public boolean isData() {
        return data;
    }


    public boolean isError() {
        return error;
    }
    
    //This method has the responsibility to undo the operation which generates an error
    public void undoAddLevelOperation(ITriple<Boolean, Integer, String> infoOperation, Vector<InterfaceLevel> ILevelsST, ClusteringManager infoClustering, Vector<String> captionsST, SOLAPLevel dragged) {
        if(infoOperation.getFirst() && infoOperation.getSecond() == 0) ILevelsST.remove(0);
        else ILevelsST.remove(ILevelsST.size()-1);
        
        infoClustering.removeLevelToAnalysis(new Triple<String, String, String>(dragged.getName(), dragged.getDimensionId(),dragged.getId()));
        
        captionsST.remove(captionsST.size()-1);
    }

    public void undoAddMeasureOperation(List<InterfaceMeasure> IMeasuresST, Vector<String> captionsST) {
        IMeasuresST.remove(IMeasuresST.size() - 1);
        captionsST.remove(captionsST.size()-1);
    }

    public void undoAddAttributeOperation(List<InterfaceAttribute> IAttributesST,
                                                 Vector<String> captionsST) {
        IAttributesST.remove(IAttributesST.size() - 1);
        captionsST.remove(captionsST.size()-1);
    }
}
