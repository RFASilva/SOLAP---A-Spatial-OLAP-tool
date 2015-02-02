package solap.styles;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import solap.ResponseExtractor;

import solap.bean.MainBean;

import solap.utils.ITriple;
import solap.utils.TableUtils;
import solap.utils.Triple;
import solap.utils.XMLUtils;

public class ManagerStyles {

    //To be used in string with xml mapviewer request sent to the jsf/javascript
    public static final String TOKEN="%%";
    public static final String TOKEN_SEPARATE_STYLE="newStyle";
    public static final String BASE_STYLE_AREA = "C.FRONTEIRAS";
    
    //Constants the parse xml
    public static final String EMPTY = "";
    public static final String INTERVAL = "intervals";
    public static final String SETS = "sets"; 
    
    //Store allStyles
    private Map<TypeStyle, List<Style>> styles;

    //Store the applicable styles for actual context
    private Map<TypeStyle, List<Style>> applicableStyles;
    //Store the mapping between id and associated style
    private Map<String, Style> idsStyles;
    
    private ResponseExtractor extractor;
    private Context context;
    private StylesRulesManager styleRulesManager;
    
    //Store XML mapviewer style definition to create dynamic the style
    //The last element store the name of style to be rendered
    private String xmlMapViewerStyle;
    //Store XML mapviewer style definition to create dynamic the style
    //The last element store the name of style to be rendered    
    private String xmlMapViewerLegend;
    private ITriple<String, String,String> tempLegend;
    
    // The new version of mapviewer takes a new element <rendering> to specify some
    // features of style
    private String renderingElement;
    
    //Variables to store information to be used by jsf
    private String typeChosen;
    private String styleChosen;
    private Vector<SelectItem> tempApplicableTypeOfStyles;
    private Map<String, TypeStyle> mappingDescriptionTypeStyle;
    private SelectItem[] showApplicableStyles;
    private Map<String, Vector<SelectItem>> tempApplicableStyles;
    
    //To be used to make the name to be used in legend
    private int tempIndexMeasure;
    private int tempIndexHeader;
    private int tempIndexHeaderValue1;
    private int tempIndexHeaderValue2;
    private ArrayList<ITriple<String, String, String>> propertiesStyle;
    private Vector<String> attrs;
    
    private Vector<String> headerList1;
    private Vector<String> headerList2;
    
    private boolean clustering = false;
    
    private IStylesCache<String,String> summarizationStyleCache;
    
    public ManagerStyles() {
        styles = new HashMap<TypeStyle, List<Style>>();
        applicableStyles = new HashMap<TypeStyle, List<Style>>();
        idsStyles = new HashMap<String, Style>();
        styleRulesManager = new StylesRulesManager();
        xmlMapViewerStyle = "";
        tempLegend = new Triple<String, String, String>();
        xmlMapViewerLegend = "";
        renderingElement = "";
        extractor = null;
        context = null;
        mappingDescriptionTypeStyle = new HashMap<String, TypeStyle>();
        typeChosen = "";
        styleChosen = "";
        tempApplicableTypeOfStyles = new Vector<SelectItem>();
        showApplicableStyles = new SelectItem[0];
        tempApplicableStyles = new HashMap<String, Vector<SelectItem>>();
        tempIndexMeasure = 0;
        tempIndexHeader = 0;
        tempIndexHeaderValue1 = 0;
        tempIndexHeaderValue2 = 0;
        attrs = new Vector<String>();
        attrs.add("Attribute");
        attrs.add("Value");
        
        summarizationStyleCache = new StyleCache<String,String>();
    }
    
    public void addStyle(TypeStyle type, Style style) {
        if(styles.get(type) == null)
            styles.put(type, new LinkedList<Style>());
        
        styles.get(type).add(style);
        idsStyles.put(style.getId(), style);    
    }
    
    public void addEntryInLookupTable(Context context, List<TypeStyle> listTypeStyles) {
        styleRulesManager.addEntryLookupTable(context, listTypeStyles);
    }
    
    public Style getStyleById(String id) {
        return idsStyles.get(id);
    }
    
    public void generateStyleForSummarization(Vector<Vector<String>> data, TableUtils tableUtils, String generalizationType) {
        System.out.println("INICIO DA DESCRICAO");
        
        tempLegend.setFirst("<map_request datasource=\"solap\" antialiase=\"true\" format=\"PNG_STREAM\"> \n " +
                            "<legend bgstyle=\"fill:#ffffff;fill-opacity:256;stroke:#ff0000\" profile=\"SMALL\" position=\"SOUTH_WEST\" font=\"Dialog\">\n" + 
        "       <column>");
        tempApplicableTypeOfStyles = new Vector<SelectItem>();
        
        this.context = buildContextForSummarization(data, tableUtils, generalizationType);
        printContextDiscription();
        
        List<TypeStyle> applicableTypeStyles = styleRulesManager.getApplicableTypeStyles(context);
        //System.out.println("Size applicableTypeStyles: " + applicableTypeStyles.size());
        this.applicableStyles = styleRulesManager.getApplicableStyles(context, styles, applicableTypeStyles);
        //System.out.println("Size applicableStyles: " + applicableStyles.size());
        
        this.renderingElement = "";
        
        System.out.println("Vou preencher");
        filledTempAplicableTypeStyles(applicableTypeStyles);
        System.out.println("Vou escolher o estilo a aplicar");
        Style applyThisStyle = applicableStyles.get(applicableTypeStyles.get(0)).get(0);
        System.out.println("NUMERO DE CHARACTERISTICAS DISTINTAS: " + context.getNumDistinctCharacteristics());
        System.out.println("Vou buscar o XML");
        applyThisStyle.setStyleCache(summarizationStyleCache);
        xmlMapViewerStyle = applyThisStyle.toXMLRequestMapViewer(context, 0, tempLegend);
        System.out.println("XML:\n" + xmlMapViewerStyle);
        
        fillSummarizationCache();
        
        makeRenderingElementForSummarization();
              
        tempLegend.setFirst(tempLegend.getFirst() + "</column>\n");
        tempLegend.setFirst(tempLegend.getFirst() +  "</legend>\n");
        addDefinitionStylesToLegend(tempLegend);
        tempLegend.setFirst(tempLegend.getFirst() +  "</map_request>\n");
        xmlMapViewerLegend = tempLegend.getFirst();
    }
    
    private void makeRenderingElementForSummarization(){
        String[] styles = xmlMapViewerStyle.split(ManagerStyles.TOKEN);
        this.renderingElement = "<rendering> <style name=\"" + styles[styles.length - 1] + "\" value_columns=\"alphaNumeric1\"/>\n</rendering>";
        System.out.println("RENDERING ELEMENT:\n" + renderingElement);
    }
    
    // fill the cache the the new information about color classification and
    // at the same time save that information int a String
    private void fillSummarizationCache(){
        summarizationStyleCache.reset();
        String groupsColors="";
        XMLUtils xmlUtils = new XMLUtils();
        String[] tokens = xmlMapViewerStyle.split(ManagerStyles.TOKEN);
        String resultXML ="<style>";
        for(int t=0;t<tokens.length;t=t+2){
            resultXML += tokens[t];
        }
        resultXML += "</style>";
        Document styleDoc = xmlUtils.XmlToDoc(resultXML);
        NodeList buckets = styleDoc.getElementsByTagName("CollectionBucket");
        for(int i=0; i < buckets.getLength(); i++) {
            String label = ((Element)buckets.item(i)).getAttribute("label");
            String style = ((Element)buckets.item(i)).getAttribute("style");
            for(int j=1;i<tokens.length;j=j+2){
                if(style.equals(tokens[j])){
                    int indexFill = tokens[j-1].indexOf("fill:");
                    String sub = tokens[j-1].substring(indexFill);
                    //System.out.println(sub);
                    int index = sub.indexOf(":");
                    int index2 = sub.indexOf(";");
                    String color = sub.substring(index+1,index2);
                    //System.out.println(label + " -> " + color);
                    summarizationStyleCache.addElement(label,color);
                    groupsColors += label + ManagerStyles.TOKEN + color + ManagerStyles.TOKEN; 
                    break;
                }
            }
        }
        
        MainBean mainBean = (MainBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("MainBean");
        mainBean.setGroupsColors(groupsColors.substring(0,groupsColors.length()-2));
    }
    
    public void printContextDiscription(){
        System.out.println("----------------------");
        System.out.println("CONTEXT DESCRIPTION");
        System.out.println("Number of Numerical Columns: "+ context.getNumberNumericalColumns());
        System.out.println("Number of Alphanumerical Columns: "+ context.getNumberAlphaNumericalColumns());
        System.out.println("Number of Measures: "+ context.getNumberOfMeasures());
        System.out.println("----------------------");
    }
    
    // Implement a decision tree to return the possible styles
    // which are applicable
    public void generateStyle(Vector<Vector<String>> data, TableUtils tableUtils) {
        System.out.println("INICIO DA DESCRICAO");
        
        tempLegend.setFirst("<map_request datasource=\"solap\" antialiase=\"true\" format=\"PNG_STREAM\"> \n " +
                            "<legend bgstyle=\"fill:#ffffff;fill-opacity:256;stroke:#ff0000\" profile=\"SMALL\" position=\"SOUTH_WEST\" font=\"Dialog\">\n" + 
        "       <column>");
        
        
        tempApplicableTypeOfStyles = new Vector<SelectItem>();
        this.context = buildContext(data, tableUtils);
        
        List<TypeStyle> applicableTypeStyles = styleRulesManager.getApplicableTypeStyles(context);
        
        this.applicableStyles = styleRulesManager.getApplicableStyles(context, styles, applicableTypeStyles);
        this.renderingElement = "";
        
        
        
        filledTempAplicableTypeStyles(applicableTypeStyles);
        
        Style applyThisStyle = applicableStyles.get(applicableTypeStyles.get(0)).get(0);
        //Don't need the tableUtils in this case
        xmlMapViewerStyle = applyThisStyle.toXMLRequestMapViewer(context, 0, tempLegend);
        
        //System.out.println(xmlMapViewerStyle);
        
        tempLegend.setFirst(tempLegend.getFirst() + "</column>\n");
        tempLegend.setFirst(tempLegend.getFirst() +  "</legend>\n");
        addDefinitionStylesToLegend(tempLegend);
        tempLegend.setFirst(tempLegend.getFirst() +  "</map_request>\n");
        
        xmlMapViewerLegend = tempLegend.getFirst();
        
        if(!(applyThisStyle instanceof Chart)) {
            if(context.getNumberOfNumericalCol() > 1 || context.getNumberAlphaNumericalColumns() > 0)
                makeRenderingElement((CompositeStyle)applyThisStyle);
        }
        
        String[] styles = xmlMapViewerStyle.split(ManagerStyles.TOKEN_SEPARATE_STYLE);
        
        System.out.println( "<styles>\n");
        for(int i = 0; i < styles.length; i++) {
            String[] stylesDefinition = styles[i].split(ManagerStyles.TOKEN);
            for(int j = 0; j < stylesDefinition.length; j+=2) {
                System.out.println("<style name=\"" + stylesDefinition[j+1] + "\">\n");
                System.out.println(stylesDefinition[j]);
                System.out.println("</style>");
            }
        }
        System.out.println( "</styles>\n");

        System.out.println("FIM DA DESCRICAO");
    }
    
    public void changeStyle() {
        tempLegend.setFirst("<map_request datasource=\"solap\" antialiase=\"true\" format=\"PNG_STREAM\"> \n " +
                            "<legend bgstyle=\"fill:#ffffff;fill-opacity:256;stroke:#ff0000\" profile=\"MEDIUM\" position=\"SOUTH_WEST\" font=\"Times New Roman\">\n" + 
        "       <column>");
        
        TypeStyle typeStyleChosen = mappingDescriptionTypeStyle.get(typeChosen);
        int indexStyleChosen = getIndexStyleChosen();
        System.out.println("TYPE OF STYLE: " + typeStyleChosen.getDescription());
        Style applyThisStyle = applicableStyles.get(typeStyleChosen).get(indexStyleChosen);
        xmlMapViewerStyle = applyThisStyle.toXMLRequestMapViewer(context, 0, tempLegend);
        
        System.out.println("CHANGE STYLE: " +  xmlMapViewerStyle);
        
        String[] styles = xmlMapViewerStyle.split(ManagerStyles.TOKEN_SEPARATE_STYLE);
        
      /*  System.out.println( "<styles>\n");
        for(int i = 0; i < styles.length; i++) {
            String[] stylesDefinition = styles[i].split(ManagerStyles.TOKEN);
            for(int j = 0; j < stylesDefinition.length; j+=2) {
                System.out.println("<style name=\"" + stylesDefinition[j+1] + "\">\n");
                System.out.println(stylesDefinition[j]);
                System.out.println("</style>\n");
            }
        }
        
        System.out.println( "</styles>\n");*/
        
        tempLegend.setFirst(tempLegend.getFirst() + "</column>\n");
        tempLegend.setFirst(tempLegend.getFirst() +  "</legend>\n");
        addDefinitionStylesToLegend(tempLegend);
        tempLegend.setFirst(tempLegend.getFirst() +  "</map_request>\n");
        xmlMapViewerLegend = tempLegend.getFirst();
        renderingElement = "";
        
        if(!(applyThisStyle instanceof Chart)) {
            if(context.getNumberOfNumericalCol() > 1 || context.getNumberAlphaNumericalColumns() > 0)
                makeRenderingElement((CompositeStyle)applyThisStyle);
        }
    }
    
    public String getXmlMapViewerStyle() {
        return xmlMapViewerStyle;
    }
    
    public void setExtractor(ResponseExtractor extractor) {
        this.extractor = extractor;
    }
    
    private Context buildContextForSummarization(Vector<Vector<String>> data, TableUtils tableUtils, String generalizationType) {
        Context result = new Context();
        tempIndexHeader = 0;
        tempIndexHeaderValue1 = 0;
        tempIndexHeaderValue2 = 0;
        tempIndexMeasure = 0;
        
        result.addGeometryTypeAll(extractor.getGeometryType());
        
        try {
            result.setNumberNumericalColumns(0);
        }catch(Exception e) {
            //TODO
            System.out.println("ERRO no setNumberNumericalColumns");
        }
        
        result.setNumberAlphaNumericalColumns(0);
        result.setNumberOfMeasures(Integer.parseInt(extractor.getNumberOfMeasures()));
        
        double[] minAndMax = findExtremeValues(data, result);
        
        for(int i = 0; i <result.getNumberNumericalColumns()*2; i+=2) {
            result.addMeasure(getNameOfColumnByIndex(tableUtils), minAndMax[i], minAndMax[i + 1]);
        }
        
        result.setClustering(clustering);
        
        // Add information about summarization process to the context
        result.setSummarization(true);
        result.setGeneralizationType(generalizationType);
        result.setNumDistinctCharacteristics(tableUtils.getNumDistinctCharacteristics());
        result.setData(data);
        result.setNumberOfCreatedGroups(tableUtils.getNumberOfGroups());
        
        return result;
    }
      
    private Context buildContext(Vector<Vector<String>> data, TableUtils tableUtils) {
        Context result = new Context();
        tempIndexHeader = 0;
        tempIndexHeaderValue1 = 0;
        tempIndexHeaderValue2 = 0;
        tempIndexMeasure = 0;
      
        result.addGeometryTypeAll(extractor.getGeometryType());
        
        try {
            result.setNumberNumericalColumns(tableUtils.getNumberOfColumns() - tableUtils.getInLineAttributes().size());
        }catch(Exception e) {
            //TODO
            System.out.println("ERRO no setNumberNumericalColumns");
        }
        
        result.setNumberAlphaNumericalColumns(extractor.getHigherLevelAttributes().size());
        //result.setNumberAlphaNumericalColumns(extractor.getHigherLevelAttributes().size() + extractor.getSameLevelAttributes().size());
        result.setNumberOfMeasures(Integer.parseInt(extractor.getNumberOfMeasures()));
  
        double[] minAndMax = findExtremeValues(data, result);
        
        for(int i = 0; i <result.getNumberNumericalColumns()*2; i+=2) {
            result.addMeasure(getNameOfColumnByIndex(tableUtils), minAndMax[i], minAndMax[i + 1]);
        }
        
        for(int i = 0; i < extractor.getHigherLevelAttributes().size(); i++) {
            String higherAttr = extractor.getHigherLevelAttributes().get(i);
            result.addAlphaNumericColumn(higherAttr, extractor.getRowsetDistincts(higherAttr) );
        }
        
        //for(int i = 0; i < extractor.getSameLevelAttributes().size(); i++) {
            //System.out.println("ENTREI AQUI AO CONSTRUIR O CONTEXTO");
        //    String sameAttr = extractor.getSameLevelAttributes().get(i);
        //    result.addAlphaNumericColumn(sameAttr, extractor.getRowsetDistincts(sameAttr) );
        //}
        
        result.setClustering(clustering);
        
        return result;
    }
    
    private double[] findExtremeValues(Vector<Vector<String>> data, Context result) {
        int numberNumericalColumns = result.getNumberNumericalColumns();
        double[] minAndMax = new double[numberNumericalColumns * 2];
        
        //Store min max to each measure
        for(int i = 0; i < numberNumericalColumns*2; i+=2) {
            minAndMax[i] = Double.MAX_VALUE;
            minAndMax[i+1] = Double.MIN_VALUE;
        }
  
        int index = 0;
        int measureIndex = 0;
        int startIndex = extractor.getHigherLevelAttributes().size() + extractor.getAssociatedAttributes().size() + extractor.getSameLevelAttributes().size();
        
        while(index < data.size()) {  
            measureIndex = 0;
            for(int i = startIndex; i < (startIndex + numberNumericalColumns); i++) {
                String value = data.get(index).get(i);
                
                int indexToAddValue = measureIndex / 2;
                
                if(value.equals("")) {
                    value = "0";
                }
                
                result.addColumnValue(indexToAddValue, Double.parseDouble(value));
                
                double number = 0;
                if(!value.equals(ManagerStyles.EMPTY))
                    number = Double.parseDouble(value);
                
                if (number < minAndMax[measureIndex])
                    minAndMax[measureIndex] = number;
                
                if (number > minAndMax[measureIndex+1])
                    minAndMax[measureIndex+1] = number;
                    
                measureIndex+=2;
            }
            index++;
        }    
 
        return minAndMax;
    }
    
    public static String getName() {
        return new Random().nextLong()+"";
    }

    public String getRenderingElement() {
        return renderingElement;
    }

    private void makeRenderingElement(CompositeStyle style) {
        this.renderingElement = "<rendering> \n";
              
        if( ( (extractor.getMeasureNames().size() == 1) && (context.getNumberNumericalColumns() == 2) ) || (context.getNumberOfSpatialObjects() == 2) ||
        (context.getNumberNumericalColumns() == 2 && clustering))  {
            this.renderingElement += "<style name=\"" + style.getTempNamesStylesToRender().get(0) + "\" value_columns=\"c0\">\n";
            this.renderingElement += "<substyle name=\""+ style.getTempNamesStylesToRender().get(1) + "\" value_columns=\"c1\" changes=\"FILL_COLOR\"/>\n";    
        }
        else if(extractor.getHigherLevelAttributes().size() == 1 && extractor.getMeasureNames().size() == 1) {
            
            if(style.containsVariableSize() && !context.isClustering()) {
                this.renderingElement += "<style name=\"" + style.getTempNamesStylesToRender().get(0) + "\" value_columns=\""+ extractor.getMeasureNames().get(0) +"\">\n";
                this.renderingElement += "<substyle name=\""+ style.getTempNamesStylesToRender().get(1) + "\" value_columns=\"" +extractor.getHigherLevelAttributes().get(0)+ "\" changes=\"FILL_COLOR\"/>\n";
            }
            else if(style.containsVariableSize() && context.isClustering()) {
                this.renderingElement += "<style name=\"" + style.getTempNamesStylesToRender().get(0) + "\" value_columns=\"c0\">\n";
                this.renderingElement += "<substyle name=\""+ style.getTempNamesStylesToRender().get(1) + "\" value_columns=\"alphaNumeric0\" changes=\"FILL_COLOR\"/>\n";    
            }
            else {
                this.renderingElement += "<style name=\"" + style.getTempNamesStylesToRender().get(0) + "\" value_columns=\"" + extractor.getHigherLevelAttributes().get(0) + "\">\n";
                this.renderingElement += "<substyle name=\""+ style.getTempNamesStylesToRender().get(1) + "\" value_columns=\"" + extractor.getMeasureNames().get(0)  + "\" changes=\"FILL_COLOR\"/>\n";
            }
        }
        else {
            this.renderingElement += "<style name=\"" + style.getTempNamesStylesToRender().get(0) + "\" value_columns=\""+extractor.getMeasureNames().get(0)+"\">\n";
            this.renderingElement += "<substyle name=\""+ style.getTempNamesStylesToRender().get(1) + "\" value_columns=\"" + extractor.getMeasureNames().get(1) + "\" changes=\"FILL_COLOR\"/>\n";
        }
        
        this.renderingElement += "</style>";              
        this.renderingElement += "</rendering>";
        
        System.out.println("RENDERING ELEMENT: " + this.renderingElement);
    }

    public String getTypeChosen() {
        return typeChosen;
    }

    public Vector<SelectItem> getTempApplicableTypeOfStyles() {
        return tempApplicableTypeOfStyles;
    }

    private void filledTempAplicableTypeStyles(List<TypeStyle> applicableTypeStyles) {
        tempApplicableStyles.clear();
        int styleCounter = 0;
        for(TypeStyle typeStyle: applicableTypeStyles) {
            String value = typeStyle.getDescription();
            System.out.println("SOU DO TIPO DE ESTILO SIMPLES: " + (typeStyle instanceof TypeSimple));
            System.out.println("ESTOU AQUI DENTRO DO ESTILO COM A DESCRICAO: " + value);
            tempApplicableTypeOfStyles.add(new SelectItem(value));
            
            if(!mappingDescriptionTypeStyle.containsKey(value))
                mappingDescriptionTypeStyle.put(value, typeStyle);
            
            styleCounter = 0;
            List<Style> tempStyles = applicableStyles.get(typeStyle);
            System.out.println("Lista -> " + tempStyles);
            if(tempStyles != null){
                for(Style style: tempStyles) {
                    if(tempApplicableStyles.get(value) == null)
                        tempApplicableStyles.put(value, new Vector<SelectItem>());
                    
                    tempApplicableStyles.get(value).add(new SelectItem("Style"+styleCounter));
                    styleCounter++;
                }
            }
        }

    }
    
    private int getIndexStyleChosen() {
        Vector<SelectItem> possibleStyles = tempApplicableStyles.get(typeChosen);
        
        for(int i = 0; i < possibleStyles.size(); i++)
            if(styleChosen.equals(possibleStyles.elementAt(i).getLabel()))
                return i;
    
        return -1;
    }

    public void setTypeChosen(String typeChosen) {
        this.typeChosen = typeChosen;
        Vector<SelectItem> temp = tempApplicableStyles.get(typeChosen);
        showApplicableStyles = new SelectItem[temp.size()];
        for(int i = 0; i < temp.size(); i++) {
            showApplicableStyles[i] = temp.get(i);
        }

    }

    public SelectItem[] getShowApplicableStyles() {
        return showApplicableStyles;
    }

    public void setStyleChosen(String styleChosen) {
        this.styleChosen = styleChosen;
        
        TypeStyle typeStyleChosen = mappingDescriptionTypeStyle.get(typeChosen);
        int indexStyleChosen = getIndexStyleChosen();

        Style applyThisStyle = applicableStyles.get(typeStyleChosen).get(indexStyleChosen);
        
        
        propertiesStyle = applyThisStyle.info();
    }

    public String getStyleChosen() {
        return styleChosen;
    }

    public String getXmlMapViewerLegend() {
        return xmlMapViewerLegend;
    }

    private void addDefinitionStylesToLegend(ITriple<String, String, String> tempLegend) {
        tempLegend.setFirst(tempLegend.getFirst() +  "<styles>\n");
        String[] styles = xmlMapViewerStyle.split(ManagerStyles.TOKEN_SEPARATE_STYLE);
        
        for(int i = 0; i < styles.length; i++) {
            String[] stylesDefinition = styles[i].split(ManagerStyles.TOKEN);
            for(int j = 0; j < stylesDefinition.length; j+=2) {
                tempLegend.setFirst(tempLegend.getFirst() +  "<style name=\"" + stylesDefinition[j+1] + "\">\n");
                tempLegend.setFirst(tempLegend.getFirst() +  stylesDefinition[j]);
                tempLegend.setFirst(tempLegend.getFirst() +  "</style>\n");
            }
        }
        
        tempLegend.setFirst(tempLegend.getFirst() +  "</styles>\n");
    }

    private String getNameOfColumnByIndex( TableUtils tableUtils) {
        String result = "";
        
        try {
            //When no exists headers
            if(tableUtils.getHeaderAttributes().size() == 0) {
                return extractor.getMeasureNames().get(tempIndexMeasure++);
            }
            else{
                result = extractor.getMeasureNames().get(tempIndexMeasure);
                tempIndexMeasure++;
                
                result += "-" + headerList1.get(tempIndexHeaderValue1);
                if(extractor.getLowerLevelAttributes().size() > 1) {
                    result += "-" + headerList2.get(tempIndexHeaderValue2);
                }
               
                tempIndexHeaderValue2++;
                
                if(extractor.getMeasureNames().size() == tempIndexMeasure ) {
                    tempIndexMeasure = 0;
                    tempIndexHeaderValue1++;
                }
                
                return result;
            }
        } catch (Exception e) {
            //TODO
            return null;
        }
        
    }

    public void setHeaderList1(Vector<String> headerList1) {
        this.headerList1 = headerList1;
    }

    public void setHeaderList2(Vector<String> headerList2) {
        this.headerList2 = headerList2;
    }

    public void setPropertiesStyle(ArrayList<ITriple<String, String, String>> propertiesStyle) {
        this.propertiesStyle = propertiesStyle;
    }

    public ArrayList<ITriple<String, String, String>> getPropertiesStyle() {
        return propertiesStyle;
    }

    public void setAttrs(Vector<String> attrs) {
        this.attrs = attrs;
    }

    public Vector<String> getAttrs() {
        return attrs;
    }
    
    public void setClustering(boolean clustering) {
        this.clustering = clustering;
    }
}
