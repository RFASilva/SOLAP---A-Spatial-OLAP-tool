package solap.clustering.support;

public class InputParams {
    
    Object[] inputParameters;
    
    public InputParams(Object ...inputParameters) {
        this.inputParameters = inputParameters;
    
    }
    
    public Object getParameter(int index) {
        return this.inputParameters[index];
    }
    
    public void setParameter(int index, Object value) {
        this.inputParameters[index] = value;
    }
    
    public void addFirstParameter(Object newParameter) {
        Object[] newP = new Object[inputParameters.length + 1];
        
        newP[0] = newParameter;
        
        for(int i = 1; i < newP.length; i++)
            newP[i] = inputParameters[i-1];
        
        
        inputParameters = newP;
    }
    
    public void addParameter(Object newParameter) {
        Object[] newP = new Object[inputParameters.length + 1];
                 
        for(int i = 0; i < inputParameters.length; i++)
            newP[i] = inputParameters[i];
        
        newP[inputParameters.length] = newParameter;
        inputParameters = newP;
    }
    
    public int size() {
        return inputParameters.length;
    }
}
