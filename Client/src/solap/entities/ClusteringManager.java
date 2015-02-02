package solap.entities;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.util.Vector;

import javax.faces.model.SelectItem;

import solap.utils.ITriple;
import solap.utils.Triple;

public class ClusteringManager implements Serializable {    
    
    private String zoomLevel;
    private int baseLevel;
    //In case of points
    private String variant;
    
    //To be used by dynamic point algorithm
    private Map<String, List<SOLAPHierarchy>> spatialsHierarchies;
    private Map<ITriple<String, String, String>, SOLAPPreComputingDistance> levelsInAnalysis;

    private String modeClustering;
    private int groupsParameter;
    private String hierarchyChosen1;
    private String hierarchyChosen2;
    private SelectItem[] hierarchies1;
    private SelectItem[] hierarchies2;
    
    private String currentRestrictedLevel1 = "";
    private String currentRestrictedLevel2 = "";
    
    
    public ClusteringManager() {
        this.spatialsHierarchies = new HashMap<String, List<SOLAPHierarchy>>();
        this.levelsInAnalysis = new HashMap<ITriple<String, String, String>,SOLAPPreComputingDistance>();
        
        this.variant = "ADHOC";
        this.modeClustering = "NO";
        this.groupsParameter = 0;
        this.hierarchyChosen1 = "None";
        this.hierarchyChosen2 = "None";
        this.currentRestrictedLevel1 = "None";
        this.currentRestrictedLevel2 = "None";
        
        this.hierarchies1 = new SelectItem[0];
        this.hierarchies2 = new SelectItem[0];
    }
    
    //Name, Dimensionid, levelid
    public void addLevelToAnalysis(ITriple<String, String, String> levelInfo, SOLAPPreComputingDistance value) {
        levelsInAnalysis.put(levelInfo, value);
        fillHierarchiesItems(levelInfo, levelsInAnalysis.size()-1);
    }
    
    public void removeLevelToAnalysis(ITriple<String, String, String> levelInfo) {
        levelsInAnalysis.remove(levelInfo);
    }
    
    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getVariant() {
        return variant;
    }

    public String getXML() {
        String request = "";
        
        if (modeClustering.equals("NO"))
            return request;
        
        return generateXML();
    }
    
    private String generateXML() {
        String request = "";
        
        request += "<clustering mode=\"" + modeClustering + "\" groups=\"" + groupsParameter + "\" variant=\"" + variant + "\" zoomLevel=\"" + zoomLevel + "\">";
        
        int counter = 0;
        for(ITriple<String, String,String> level: levelsInAnalysis.keySet()) {
            request += "<parameters spatialLevel=\"" + level.getFirst() + "\"";
            
            if(!variant.equals("ADHOC") ) {
                System.out.println("COUNTER: " + counter);
                boolean toDo = false;
                if(counter == 0 && !hierarchyChosen1.equals("None"))
                    toDo = true;
                
                else if(counter == 1 && !hierarchyChosen2.equals("None"))
                    toDo = true;
                    
                if(toDo) {
                    int spatialHierarchyChosen = findIndexHierarchyChosen(counter);
                    String dimensionId = level.getSecond();
                    SOLAPLevel levelComputed = findLevelToClustering(dimensionId,spatialHierarchyChosen,Integer.parseInt(zoomLevel), 15);
                    request += " levelId=\"" + levelComputed.getId() + "\" dimensionId=\"" + dimensionId + "\"";
                }
            }
            
            //Have the distances pre computed
            if(levelsInAnalysis.get(level) != null) {
                SOLAPPreComputingDistance preComputedDistances = levelsInAnalysis.get(level);
                request += " distancesPreComputed=\"" + "true" + "\"";
                request += " tableRef=\"" + preComputedDistances.getTableId() + "\"";
                request += " from=\"" + preComputedDistances.getFromId() + "\"";
                request += " to=\"" + preComputedDistances.getToId() + "\"";
                request += " distances=\"" + preComputedDistances.getDistanceId() + "\"";
            }
            
            else request += " distancesPreComputed=\"" + "false" + "\"";
            request += "/>";
            counter++;
        }
        
        request+="</clustering>";
        
        System.out.println("CLUSTERING XML: " + request);
        
        return request;
    }
    
    public void setZoomLevel(String zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public String getZoomLevel() {
        return zoomLevel;
    }

    public void setBaseLevel(int baseLevel) {
        System.out.println("BASE LEVEL: " +  baseLevel);
        this.baseLevel = baseLevel;
    }

    public int getBaseLevel() {
        return baseLevel;
    }
    
    public void fillSpatialHierarchies(Vector<SOLAPDimension> dimensions) {
        for(SOLAPDimension dimension: dimensions) {
            for(SOLAPHierarchy hierarchy: dimension.getHierarchies()) {
                if(hierarchy.getType().equals("geometric")) {
                    if(spatialsHierarchies.get(dimension.getId()) == null)
                        spatialsHierarchies.put(dimension.getId(), new LinkedList<SOLAPHierarchy>());
                    spatialsHierarchies.get(dimension.getId()).add(hierarchy);
                }
            }
        }
    }
    
    public SOLAPLevel findLevelToClustering(String dimensionId, int hierarchy, int currentZoom, int maxLevel) {
        System.out.println("INFORMATION ABOUT HIERARCHY: " +dimensionId + " --- " + hierarchy);
        SOLAPHierarchy hierarchyChosen = spatialsHierarchies.get(dimensionId).get(hierarchy);
        
        int levelIndex = findFirstPolygonLevel(hierarchyChosen);
        
        int numberOfLevels = hierarchyChosen.getLevels().size() - levelIndex;
        //TODO
        int numLevels = (int) Math.ceil( ((maxLevel - baseLevel) + 0.0 ) / (numberOfLevels + 0.0));
        
        System.out.println("Number of levels: " + numberOfLevels + " numLevels per x: " + numLevels);
        System.out.println("Level index: " + levelIndex);
        
        System.out.println("BASE LEVEL: " +  baseLevel);
        
        int lowerBound = 0 + baseLevel;
        int higherBound = numLevels + baseLevel;
        
        if(numberOfLevels != 1) {
            for(int i = numberOfLevels; i >= levelIndex ; i--) {
                System.out.println("Currentzoom: " + currentZoom + " lowerBound: " + lowerBound + " HigherBound " + higherBound);
                if(currentZoom >= lowerBound && currentZoom < higherBound) {
                    currentRestrictedLevel1 = hierarchyChosen.getLevels().get(i).getName();
                    return hierarchyChosen.getLevels().get(i);
                }
                
                lowerBound = higherBound;
                higherBound += numLevels;
            }
        }
        else if(numberOfLevels == 1) {
            currentRestrictedLevel1 = hierarchyChosen.getLevels().get(levelIndex).getName();
            return hierarchyChosen.getLevels().get(levelIndex);
        }
        return null;
    }

    private int findFirstPolygonLevel(SOLAPHierarchy hierarchyChosen) {
        for(int i = 0; i < hierarchyChosen.getLevels().size(); i++) {
            if(hierarchyChosen.getLevels().get(i).getSpatialType().equals("polygon"))
                return i;
        }
        
        return -1;
    }

    public void setModeClustering(String modeClustering) {
        this.modeClustering = modeClustering;
    }

    public String getModeClustering() {
        return modeClustering;
    }

    public void setGroupsParameter(int groupsParameter) {
        this.groupsParameter = groupsParameter;
    }

    public int getGroupsParameter() {
        return groupsParameter;
    }

    public void setHierarchyChosen1(String hierarchyChosen) {
        System.out.println("HIERARCHY CHOSEN 1: " + hierarchyChosen);
        this.hierarchyChosen1 = hierarchyChosen;
        if(this.hierarchyChosen1.equals("None") && hierarchyChosen2.equals("None"))
            variant = "ADHOC";
        else
            variant = "BASED_ON_HIERARCHY";
    }

    public String getHierarchyChosen1() {
        return hierarchyChosen1;
    }
    
    public void setHierarchyChosen2(String hierarchyChosen) {
        this.hierarchyChosen2 = hierarchyChosen;
        if(this.hierarchyChosen2.equals("None") && hierarchyChosen1.equals("None"))
            variant = "ADHOC";
        else
            variant = "BASED_ON_HIERARCHY";
    }

    public String getHierarchyChosen2() {
        return hierarchyChosen2;
    }

    public void setHierarchies1(SelectItem[] hierarchies1) {
        this.hierarchies1 = hierarchies1;
    }

    public SelectItem[] getHierarchies1() {
        return hierarchies1;
    }
    
    public void setHierarchies2(SelectItem[] hierarchies2) {
        this.hierarchies2 = hierarchies2;
    }

    public SelectItem[] getHierarchies2() {
        return hierarchies2;
    }
    
    private void fillHierarchiesItems(ITriple<String, String,String> level, int counter) {
        if(spatialsHierarchies.get(level.getSecond()) != null) {
            List<SOLAPHierarchy> spatialHierarchies = spatialsHierarchies.get(level.getSecond());
            
            if(counter == 0)
                hierarchies1 = new SelectItem[spatialHierarchies.size()];
            else
                hierarchies2 = new SelectItem[spatialHierarchies.size()];
            
            int index = 0;
            for(SOLAPHierarchy hierarchy: spatialHierarchies) {
                String result = "";
                for(int i = 1; i < hierarchy.getLevels().size(); i++) {
                    if(hierarchy.getLevels().size() - 1 == i)
                        result += hierarchy.getLevels().get(i).getName();
                    else result += hierarchy.getLevels().get(i).getName() + ", ";
                }
                if(counter == 0)
                    hierarchies1[index] = new SelectItem(result);
                else
                    hierarchies2[index] = new SelectItem(result);
                index++;
            }
        }
    }


    private int findIndexHierarchyChosen(int counter) {
        SelectItem[] hierarchy = null;
        String hierarchyChosen = "";
        if(counter == 0) {
            hierarchy = hierarchies1;
            hierarchyChosen = hierarchyChosen1;
        }
        else {
            hierarchy = hierarchies2;
            hierarchyChosen = hierarchyChosen2;
        }
        
        for(int i = 0; i < hierarchy.length; i++) {
            System.out.println(hierarchy[i].getValue() + " -HGGGJGFHFGH-FGHGFHGFHGF- " + hierarchyChosen);
            if (hierarchy[i].getValue().equals(hierarchyChosen)) {
                return i;
            }
        }
        return -1;
        
    }
    
    public Iterator<ITriple<String, String, String>> getLevelsInAnalisys() {
        return levelsInAnalysis.keySet().iterator();
    }

    public void setCurrentRestrictedLevel1(String currentRestrictedLevel) {
        this.currentRestrictedLevel1 = currentRestrictedLevel;
    }

    public String getCurrentRestrictedLevel1() {
        return currentRestrictedLevel1;
    }

    public void setCurrentRestrictedLevel2(String currentRestrictedLevel2) {
        this.currentRestrictedLevel2 = currentRestrictedLevel2;
    }

    public String getCurrentRestrictedLevel2() {
        return currentRestrictedLevel2;
    }

    public Map<String, List<SOLAPHierarchy>> getSpatialsHierarchies() {
        return spatialsHierarchies;
    }

    public Map<ITriple<String, String, String>, SOLAPPreComputingDistance> getLevelsInAnalysis() {
        return levelsInAnalysis;
    }

    public void setSpatialsHierarchies(Map<String, List<SOLAPHierarchy>> spatialsHierarchies) {
        this.spatialsHierarchies = spatialsHierarchies;
    }

    public void setLevelsInAnalysis(Map<ITriple<String, String, String>, SOLAPPreComputingDistance> levelsInAnalysis) {
        this.levelsInAnalysis = levelsInAnalysis;
    }
}
