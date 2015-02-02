package solap.entities;

import java.io.Serializable;

import java.util.List;
import java.util.Vector;

import javax.faces.model.SelectItem;

import solap.utils.CommProtocolUtils;

public class InterfaceSlice implements Serializable {
    
    private String id;
    private String dimensionId;
    private String levelId;
    private String name;
    private String filterName;
    private String filterDescription;
    private List<String> values;
    private boolean slider;
    private int sliderOptions;
    private int sliderValue = 0;
    private String sliderTextValue;
    
    //constructor
    public InterfaceSlice(String dimensionId, String levelId, String id, String name) {
        this.id = id;
        this.dimensionId = dimensionId;
        this.levelId = levelId;
        this.name = name;
    }
    
    //request generation
    public String getXML() {
        String temp = "";
        
        //if regular slice, generate all requests
        if (!slider) {
            for (int i = 0; i < values.size(); i++) {
                temp += CommProtocolUtils.buildSlice(dimensionId, levelId, id, values.get(i));
            }
        }
        
        //if slider, generate the active request
        else if (slider) {
            temp = CommProtocolUtils.buildSlice(dimensionId, levelId, id, getSliderTextValue());
        }
        
        return temp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterDescription(String filterDescription) {
        this.filterDescription = filterDescription;
    }

    public String getFilterDescription() {
        return filterDescription;
    }

    public void setValues(List<String> values) {
        this.values = values;
        sliderOptions = values.size() - 1;
    }

    public List<String> getValues() {
        return values;
    }

    public void setSlider(boolean slider) {
        this.slider = slider;
    }

    public boolean isSlider() {
        return slider;
    }

    public int getSliderOptions() {
        return sliderOptions;
    }
    
    public void setSliderOptions(int sliderOptions) {
        this.sliderOptions = sliderOptions;
    }

    public void setSliderValue(int sliderValue) {
        this.sliderValue = sliderValue;
    }

    public int getSliderValue() {
        return sliderValue;
    }

    public void setSliderTextValue(String sliderTextValue) {
        this.sliderTextValue = sliderTextValue;
    }

    public String getSliderTextValue() {
        if(sliderValue < values.size()) {
            return values.get(sliderValue); 
        }
        sliderValue = 0;
        return values.get(sliderValue);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof InterfaceSlice)) {
            return false;
        }
        final InterfaceSlice other = (InterfaceSlice)object;
        if (!(id == null ? other.id == null : id.equals(other.id))) {
            return false;
        }
        if (!(dimensionId == null ? other.dimensionId == null : dimensionId.equals(other.dimensionId))) {
            return false;
        }
        if (!(levelId == null ? other.levelId == null : levelId.equals(other.levelId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        result = PRIME * result + ((dimensionId == null) ? 0 : dimensionId.hashCode());
        result = PRIME * result + ((levelId == null) ? 0 : levelId.hashCode());
        return result;
    }
}
