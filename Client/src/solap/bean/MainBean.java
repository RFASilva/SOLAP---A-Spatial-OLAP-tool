package solap.bean;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import solap.ResponseExtractor;
import solap.SOLAPServerPortClient;
import solap.entities.ErrorHandler;
import solap.entities.SOLAPAttribute;
import solap.utils.CommProtocolUtils;
import solap.utils.TableUtils;
import solap.utils.XMLUtils;
import solap.styles.ManagerStyles;


public class MainBean {
    SOLAPServerPortClient portClient = new SOLAPServerPortClient();
    
    private String requestText;
    private String responseText;
    private String responseTextDT;
    ResponseExtractor extractor;
    ResponseExtractor extractorDT;
	
    //*** semantic data ***    
    //measure names list
    private Vector<String> measureList;
    //other headers
    private Vector<String> headerList;
    private Vector<String> headerList2;
    //table data
    private Vector<Vector<String>> data;
    //detail table
    private Vector<Vector<String>> detailData;
    
    //index definition for JSF table data
    private Vector<String> stubIndex;
    //other JSF auxiliary info
    private int numberOfHeaderAttributes = 0;
    private int numberOfInLineAttributes = 0;
    private int colspanHeader1 = 0;
    private int colspanHeader2 = 0;
    //********************
    
    //*** spatial data ***
    private String sqlQuery;
    private String geometryType;
    
    //number of rows in the support table (1:1 relationship with the map)
    private int numberOfRows;
    //number of columns and in-line attributes (used to calculate number of bars in chart)
    private int numberOfBars = 0;
        
    private String numberAssocAttr = "";
    private String assoc1 = "";
    private String assoc2 = "";
    
    private ManagerStyles managerStyles = new ManagerStyles();

    //********************
    //used for detail table
    private TableUtils tableUtils;
    private String highlighted;
    private Vector<String> detailHeaders;
    private String rowdata0;
    private String rowdata1;
    
    //used for bar chart legend
    private int numberOfAttribs = 0;
    
    //Mapviewer need this xml element to get information about foi saved in database
    private String hiddenElement;
    
    //Field in spatial query to appear as a legend
    private String legendField = "";
    
    //Manager of error messages
    private ErrorHandler errorHandler;
    
    //Used for sumarizaton
    private String sumarizationResponseText;
    private boolean summarization;
    private String groupsColors="";
    
    public ManagerStyles getManagerStyles() {
        return managerStyles;
    }
    
    public void executeGeneralization(String type) {
        summarization = true;
        errorHandler = new ErrorHandler();
        
        // Get response from server  
        SessionBean myBean = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");
        requestText = myBean.generateGeneralizationXML();
        
        sumarizationResponseText = portClient.executeRequest(requestText);
        
        System.out.println("Ao cliente chegou isto:\n" + sumarizationResponseText);
        
        String supportTableResponse = extractSupportTableSummarizationInfo();
        extractor = new ResponseExtractor(supportTableResponse);
        sqlQuery = extractor.getSQLQuery().get(0) + "%%";
        System.out.println("Query:\n" + sqlQuery);
        geometryType = extractor.getGeometryType().get(0);
        
        try {
            System.out.println("Entrei na parte do table utils");
            tableUtils = new TableUtils(extractor);
            System.out.println("Passei o constructor");
            numberOfHeaderAttributes = tableUtils.getHeaderAttrSize();
            System.out.println("Numero de Headers: " + numberOfHeaderAttributes);
            numberOfInLineAttributes = tableUtils.getInlineAttrSize();
            System.out.println("Numero de InLine: " + numberOfInLineAttributes);
            numberOfRows = tableUtils.getNumberOfRows1();
            System.out.println("Numero de Rows: " + numberOfRows);
            
            hiddenElement = makeHiddenElement();
            setHeaderColspans();
            System.out.println("Load data...");
            loadData(tableUtils);
            System.out.println("Load data done");
        }
        catch(Exception e){
            System.out.println("ERRO NA PARTE DO TABLE UTILS!!!");
            errorHandler.setData(true);
        }
        try {
            managerStyles.setExtractor(extractor);
            headerList = new Vector<String>();
            managerStyles.setHeaderList1(headerList);
            headerList2 = new Vector<String>();
            managerStyles.setHeaderList2(headerList2);
            managerStyles.setClustering(false);
            managerStyles.generateStyleForSummarization(data, tableUtils, type);
        }
        catch (Exception e){
            errorHandler.setDefinitionStyle(true);
        }   
        System.out.println("Cliente: Terminei o processo de generalizacao");
    }
    
    private String extractSupportTableSummarizationInfo(){
        System.out.println("SO QUERO A PARTE DA TABELA DE SUPORTE");
        XMLUtils xmlUtils = new XMLUtils();
        Document supportDocument = xmlUtils.XmlToDoc(sumarizationResponseText);
        supportDocument.getElementsByTagName("solapplus").item(0).removeChild(supportDocument.getElementsByTagName("table").item(1));
        String result = xmlUtils.docToXml(supportDocument);
        System.out.println(result);
        System.out.println("--------------------------------------");
        return result;
    }
    
    private String extractDetailTableSummarizationInfo(){
        System.out.println("SO QUERO A PARTE DA TABELA DE DETALHE");
        XMLUtils xmlUtils = new XMLUtils();
        Document supportDocument = xmlUtils.XmlToDoc(sumarizationResponseText);
        supportDocument.getElementsByTagName("solapplus").item(0).removeChild(supportDocument.getElementsByTagName("table").item(0));
        String result = xmlUtils.docToXml(supportDocument);
        System.out.println(result);
        System.out.println("--------------------------------------");
        return result;
    }
    
    public void execute() {
        //summarization=false;
        errorHandler = new ErrorHandler();
        
        //get response from server  
        SessionBean myBean = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");
        requestText = myBean.generateRequestST();
        
        System.out.println("Numero de metricas: " + myBean.getIMeasuresST().size());
        System.out.println("Numero de niveis: " + myBean.getILevelsST().size());
        
        if(myBean.getILevelsST().size() == 0 || myBean.getIMeasuresST().size() == 0) {
            numberOfRows = 0;
            headerList = new Vector<String>();
            headerList2 = new Vector<String>();
            measureList = new Vector<String>();
            data = new Vector<Vector<String>>();
            data.add(new Vector<String>());
            return;
        }
            
        responseText = portClient.executeRequest(requestText);
        highlighted="";
        
        try {
            evaluateAnswer(responseText);
            extractor = new ResponseExtractor(responseText);
            tableUtils = new TableUtils(extractor);
        
            //Is necessary introduce this element to access to a information of foi objects
            hiddenElement = makeHiddenElement();
            numberOfHeaderAttributes = tableUtils.getHeaderAttrSize();
            numberOfInLineAttributes = tableUtils.getInlineAttrSize();
            
            //set colspans
            setHeaderColspans();
            sqlQuery = "";
            geometryType = "";
            
            getSpatialQuery();
    
            //set number of rows, columns and in-line attributes (used to calculate number of bars in chart)
            numberOfRows = tableUtils.getNumberOfRows1();
            numberOfBars = tableUtils.getNumberColumns() - numberOfInLineAttributes;       
            loadData(tableUtils);
            loadHeader(0);
            loadHeader2(1);
        } catch (Exception e) { errorHandler.setData(true); }
        
        try {
            managerStyles.setExtractor(extractor);
            managerStyles.setHeaderList1(headerList);
            managerStyles.setHeaderList2(headerList2);
            managerStyles.setClustering(myBean.isClustering());
            managerStyles.generateStyle(data, tableUtils);
        } catch (Exception e) { errorHandler.setDefinitionStyle(true); }            
    }
    
    //This element is necessary to acess to the information of foi elements (mapviewer) used in highlighted feature
    private String makeHiddenElement() {
        try {
            String result = "<hidden_info>";
            
            int numberLowerAttr = tableUtils.getHeaderAttrSize();
            SessionBean myBean = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");
        
            if( numberLowerAttr > 0 || myBean.isClustering() || summarization) {
                //for(int i = 0; i < extractor.getHigherLevelAttributes().size(); i++)
                //    result += "<field column=\"" + ("alphaNumeric" + i) + "\" name=\""+ ("alphaNumeric" + i) + "\"/>";
                
                result += "<field column=\"" + "NAME" + "\" name=\""+ "NAME" + "\"/>";
                legendField = "NAME";
            }
            else {
                for(int i = 0; i < extractor.getAssociatedAttributes().size(); i++) {
                    result += "<field column=\"" + extractor.getAssociatedAttributes().get(i) + "\" name=\""+ extractor.getAssociatedAttributes().get(i) + "\"/>";
                    legendField = extractor.getAssociatedAttributes().get(i);
                }
            }
            result += "</hidden_info>";
            
            System.out.println("Hidden XML: "+ result);
            return result;
        } catch (Exception e) {
            //TODO
            return null;
        }
    }
    
    private void getSpatialQuery() {
        //get spatial query
        List<String> tempSqlQueries = extractor.getSQLQuery();
        for(int i = 0; i < tempSqlQueries.size(); i++) {
            System.out.println(tempSqlQueries.get(i));
            if((i - 1)==tempSqlQueries.size())
                sqlQuery += tempSqlQueries.get(i);    
            else {
                sqlQuery += tempSqlQueries.get(i);
                sqlQuery += ManagerStyles.TOKEN;
            }
        }
        
        if(tempSqlQueries.size() > 1)
            numberAssocAttr = 2 + "";
        else numberAssocAttr = 1 + "";
        
        //get spatial query
        List<String> tempSqlGeometryTypes = extractor.getGeometryType();
        for(int i = 0; i < tempSqlGeometryTypes.size(); i++) {
            if((i - 1)==tempSqlGeometryTypes.size())
                geometryType += tempSqlGeometryTypes.get(i);    
            else {
                geometryType += tempSqlGeometryTypes.get(i);
                geometryType += ManagerStyles.TOKEN;
            }
        }
    }
 
    //****** structures loaders ******
    //TODO multiple headers!
    public void loadHeader(int index) throws Exception {
        
        //check if there is a response from the server already
        if (extractor != null) {
            if (numberOfHeaderAttributes > 0)
                headerList = new TableUtils(extractor).getHeaderNames(index, 0);
                //System.out.println(headerList);
        }
        //if not, stub table / do not render
        else {
            headerList = new Vector<String>();
            //headerList.add("First");
            //headerList.add("Second");
        }
    }
    
    public void loadHeader2(int index) throws Exception {
        //check if there is a response from the server already
        if (extractor != null) {
            if (numberOfHeaderAttributes > 1)
                headerList2 = new TableUtils(extractor).getHeaderNames(index, 1);
        }
        //if not, stub table / do not render
        else {
            headerList2 = new Vector<String>();
           // headerList2.add("Third");
           // headerList2.add("Fourth");
        }
    }
    
    public void loadMeasures() throws Exception{
        //check if there is a response from the server already
        if (extractor != null) {
            measureList = new TableUtils(extractor).getMeasureNames();
            
            //TODO changed
            for (int i = 0; i < extractor.getHigherLevelAttributes().size(); i++) {
                if (!measureList.contains(extractor.getHigherLevelAttributes().elementAt(i)))
                    measureList.add(0, extractor.getHigherLevelAttributes().elementAt(i));
            }
            for (int i = 0; i < extractor.getSameLevelAttributes().size(); i++) {
                if (!measureList.contains(extractor.getSameLevelAttributes().elementAt(i)))
                    measureList.add(0, extractor.getSameLevelAttributes().elementAt(i));
            }
            for (int i = extractor.getAssociatedAttributes().size()-1; i >= 0; i--) {
                if (!measureList.contains(extractor.getAssociatedAttributes().elementAt(i)))
                    measureList.add(0, extractor.getAssociatedAttributes().elementAt(i));
            }
        }
        //if not, stub table / do not render
        else {
            measureList = new Vector<String>();
            
        //    measureList.add("M1");
        //    measureList.add("M2");
        }
    }
    
    public void loadColumnsTitle() throws Exception {
        //check if there is a response from the server already
        if (extractor != null) {
            measureList = new Vector<String>();
            
            for (int i = extractor.getAssociatedAttributes().size()-1; i >= 0; i--) {
                if (!measureList.contains(extractor.getAssociatedAttributes().elementAt(i)))
                    measureList.add(extractor.getAssociatedAttributes().elementAt(i));
            }
            for (int i = 0; i < extractor.getSameLevelAttributes().size(); i++) {
                if (!measureList.contains(extractor.getSameLevelAttributes().elementAt(i)))
                    measureList.add(extractor.getSameLevelAttributes().elementAt(i));
            }
            measureList.addAll(new TableUtils(extractor).getMeasureNames());
        }
        //if not, stub table / do not render
        else {
            measureList = new Vector<String>();
            
        //    measureList.add("M1");
        //    measureList.add("M2");
        }
    }
    
    public void loadData(TableUtils tableUtils) throws Exception {
        data = new Vector<Vector<String>>();
        
        //check if there is a response from the server already
        if (extractor != null) {
            data = tableUtils.getStructuredData();
        }
        //if not, stub table / do not render
        else {
            Vector<String> aux1 = new Vector<String>();
          //  aux1.add("");
            data.add(aux1);
        }
        if(summarization)
            extractor = new ResponseExtractor(extractSupportTableSummarizationInfo());
        else
            extractor = new ResponseExtractor(responseText);
    }
    //********************************
    
    //****** JSF accessors ******
    public Vector<String> getHeaderList() throws Exception {         
        loadHeader(0);
        return headerList;
    }
    
    public Vector<String> getHeaderList2()  throws Exception {         
        loadHeader2(1);
        
        return headerList2;
    }
    
    public void setRequestText(String requestText) {
        this.requestText = requestText;
    }
    
    public String getRequestText() {
        return requestText;
    }
    
    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
    
    public String getResponseText() {
        return responseText;
    }
    
    public Vector<String> getMeasureList()  throws Exception {
        if(summarization)
            loadColumnsTitle();
        else
            loadMeasures();
        return measureList;
    }
    
    public Vector<Vector<String>> getData() {
        if (extractor == null) {
            data = new Vector<Vector<String>>();
            Vector<String> aux1 = new Vector<String>();
            aux1.add("");
            data.add(aux1);
        }
        return data;
    }
    
    public Vector<String> getStubIndex() {
        stubIndex = new Vector<String>();
        
        stubIndex.add("");
        
        return stubIndex;
    }
    
    public int getColspanHeader1() {
        return colspanHeader1;
    }
    
    public int getColspanHeader2() {
        return colspanHeader2;
    }
    
    public int getNumberOfHeaderAttributes() {
        return numberOfHeaderAttributes;
    }
    
    public int getNumberOfInLineAttributes() {
        return numberOfInLineAttributes;
    }
    
    public Vector<Vector<String>> getDetailData() {
        if (rowdata0 == null) {
            detailData = new Vector<Vector<String>>();
            Vector<String> aux1 = new Vector<String>();
            aux1.add("");
            detailData.add(aux1);
        }
        return detailData;
    }
    
    public Vector<String> getDetailHeaders() {
        if (rowdata0 == null) {
            detailHeaders = new Vector<String>();
            detailHeaders.add("");
        }
        return detailHeaders;
    }
    
    public int getNumberOfAttribs() {
        if (extractor != null)
            numberOfAttribs = extractor.getAssociatedAttributes().size() + extractor.getSameLevelAttributes().size() + extractor.getHigherLevelAttributes().size();
        System.out.println("NUMERO DE ATRIBUTOS: " + numberOfAttribs);
        return numberOfAttribs;
    }
    
    public void setNumberOfAttribs(int val) {
        numberOfAttribs = val;
    }
    
    //***************************
    public String getSqlQuery() {
        return sqlQuery;
    }
    
    public String getGeometryType() {
        return geometryType;
    }
    
    public int getNumberOfRows() {
        return numberOfRows;
    }
    
    public int getNumberOfBars() {
        System.out.println("NUMERO DE BARRAS: " + numberOfBars);
        return numberOfBars;
    }

    public String getHighlighted() {
        return highlighted;
    }
    
    public void setHighlighted(String value) {
        highlighted = value;
    }
    
    //***************************
    
    //****** auxiliary functions ******
    private void setHeaderColspans()  throws Exception {
        //set colspans
        if (extractor == null || numberOfHeaderAttributes >= 1) {
            colspanHeader1 = Integer.parseInt(extractor.getNumberOfMeasures());
        }
        else {
            colspanHeader1 = 0;
        }
        if (extractor == null || numberOfHeaderAttributes >=2) {
            colspanHeader2 = colspanHeader1 * new TableUtils(extractor).getHeaderValues(0).size();
        }
        else {
            colspanHeader2 = 0;
        }
    }
    //*********************************
    
    //***** detail table methods ******
    
    private String extractInfo(String detailTableResponse){
        System.out.println("SO QUERO UMA DETERMINADA PARTE DA TABELA DE DETALHE");
        String result = detailTableResponse;
        XMLUtils xmlUtils = new XMLUtils();
        Document detailDocument = xmlUtils.XmlToDoc(result);
        Document otherdetailDocument = xmlUtils.XmlToDoc(result);
        //Because all the measures come first and after that come the spatial attribute
        int index = Integer.parseInt(xmlUtils.getAttributeValueFirstElement(detailDocument, "table", "nMeasures"));
        System.out.println(index);
        System.out.println(rowdata0);
        NodeList rows = otherdetailDocument.getElementsByTagName("ROW");
        NodeList rows2 = detailDocument.getElementsByTagName("ROW");
        int count=0;
        for(int i=0; i< rows.getLength();i++){
            Node n = rows.item(i);
            if(n.getAttributes().getNamedItem("belongTo") != null){
                //System.out.println(n.getAttributes().getNamedItem("belongTo").getNodeValue());
                if(!n.getAttributes().getNamedItem("belongTo").getNodeValue().equals(rowdata0)){
                    //System.out.println("ENTREI");
                    detailDocument.getElementsByTagName("ROWSET").item(0).removeChild(rows2.item(i-count));
                    count++;
                }
            }
            //System.out.println(n.getChildNodes().item(index).getTextContent());
            else if(! n.getChildNodes().item(index).getTextContent().equals(rowdata0) ){
                detailDocument.getElementsByTagName("ROWSET").item(0).removeChild(rows2.item(i-count));
                count++;
            }
        }
        
        detailDocument.getElementsByTagName("table").item(0).getAttributes().removeNamedItem("count");
        ((Element)detailDocument.getElementsByTagName("table").item(0)).setAttribute("count",""+(rows.getLength()-count));
        
        result = xmlUtils.docToXml(detailDocument);
        System.out.println(result);
        System.out.println("--------------------------------------");
        return result;
    }
    
    private void drawDetailForSummarization(){
        System.out.println("CARREGUEI NO DETALHE E ENTREI NA FUNCAO");
        
        String detailTableResponse = extractDetailTableSummarizationInfo();
        detailTableResponse = extractInfo(detailTableResponse);
        extractorDT = new ResponseExtractor(detailTableResponse);
        System.out.println("0");
        try {
            Vector<String> combos = tableUtils.getSpatialCombinations();
            System.out.println("1");
            highlighted = "" + getComboIndex(combos, rowdata0);
            System.out.println("2");
            detailData = new Vector<Vector<String>>();
            TableUtils tempForData = new TableUtils(extractorDT);
            System.out.println("3");
            detailData = tempForData.getDetailData();
            System.out.println("4");
            //fill headers
            //buildDetailHeaders(tempForData);
            System.out.println("5");
            System.out.println("Tamanho do detaildata: " + detailData.size());
            if(detailData.isEmpty()){
                Vector<String> aux1 = new Vector<String>();
                int numberOfColumns = tempForData.getNumberOfColumns();
                for(int i=0; i<numberOfColumns; i++)
                    aux1.add("");
                detailData.add(aux1);
            }
            else
                buildDetailHeaders(tempForData);
        }catch (Exception e){
            System.out.println("ERRO NO drawDetailForSummarization: " + e.getMessage());
            errorHandler.setData(true);
        }
        
        System.out.println("SAI DA FUNCAO DE DETALHE");
    }
    
    public void drawDetail() {
        errorHandler = new ErrorHandler();
        
        try {
            //get parameters from click action
            FacesContext context = FacesContext.getCurrentInstance();  
            Map requestMap = context.getExternalContext().getRequestParameterMap();  
            rowdata0 = (String)requestMap.get("rowdata0");
            rowdata1 = (String)requestMap.get("rowdata1");
            assoc1 = rowdata0;
            Vector<String> rowdata = new Vector<String>();
            
            boolean isGroup1 = rowdata0.contains("Group");
            boolean isGroup2 = false;
            
            rowdata.add(rowdata0);
            if (rowdata1 != null) {
                rowdata.add(rowdata1);
                isGroup2 = rowdata1.contains("Group");
                assoc2 = rowdata1;
            }
            
            if(summarization)
                drawDetailForSummarization();
            else{
                String requestDT;
        
                //************** execute request for detail table **********************
                SessionBean myBean = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionBean");
                requestDT = myBean.generateRequestDT(rowdata0, rowdata1);
                
                responseTextDT = portClient.executeRequest(requestDT);
                evaluateAnswer(responseTextDT);
                
                extractorDT = new ResponseExtractor(responseTextDT);
                
                //get associated attributes
                Vector<String> combos = tableUtils.getSpatialCombinations();
                    
                if(!(isGroup1 || isGroup2)) {
                    //associated attribute information (used for slice)
                    Vector<SOLAPAttribute> associated = extractorDT.getAssociatedInfo();
                    
                    //generate slice(s)
                    String slice = "";
                    Document doc = null;
                    XMLUtils xmlUtils = new XMLUtils();
                    for (int i = 0; i < associated.size(); i++) {
                        slice = CommProtocolUtils.buildSlice(associated.elementAt(i).getDimensionId(), associated.elementAt(i).getLevelId(), associated.elementAt(i).getId(), rowdata.elementAt(i));
                        //******************* new request **************************************
                        
                        slice = slice.replaceAll("&", "&amp;");
                        System.out.println("SLICE TO DETAIL TABLE: " + slice);
                        //introduce new slice(s)
                        doc = xmlUtils.XmlToDoc(requestDT);
                        Element elem = xmlUtils.XmlToDoc(slice).getDocumentElement();
                        xmlUtils.addElement(doc, elem, "request");
                        
                        requestDT = xmlUtils.docToXml(doc);
                    }
                    
                    System.out.println("NAO ESTOU a perceber nada disto aiaiaiaisamdadbbfkjsdf sdjkfhsdklf: " + requestDT);
                    responseTextDT = portClient.executeRequest(requestDT);
                }
                else {
                    responseTextDT = portClient.executeRequest(requestDT);
                }
                
                System.out.println("RESPOSTA DO SERVIDOR PARA A TABELA DE DETALHE: " + responseTextDT);
                evaluateAnswer(responseTextDT);
                extractorDT = new ResponseExtractor(responseTextDT);
                
                //get number of associated attributes
                int nAssociated = extractorDT.getAssociatedAttributes().size();
                
                if (nAssociated == 1) {
                    highlighted = "" + getComboIndex(combos, rowdata0);
                }
                else if (nAssociated == 2) {
                    highlighted = "" + getComboIndex(combos, rowdata0+rowdata1);
                }
                
                //fill detail data
                detailData = new Vector<Vector<String>>();
                
                //check if there is a response from the server already
                if (extractorDT != null) {
                    TableUtils tempForData = new TableUtils(extractorDT);
                    detailData = tempForData.getDetailData();
                    
                    //fill headers
                    buildDetailHeaders(tempForData);
                }
                
                //if not, stub table / do not render
                else {
                    Vector<String> aux1 = new Vector<String>();
                    aux1.add("");
                    detailData.add(aux1);
                }
            }
        }catch (Exception e){ 
            errorHandler.setData(true);
        }
    }
    
    private int getComboIndex(Vector<String> combos, String subs) {
        for (int i = 0; i < combos.size(); i++) {
            if (combos.elementAt(i).contains(subs))
                return i;
        }
        return -1;
    }
    
    private void buildDetailHeaders(TableUtils util) throws Exception {
        //if(summarization)
        //    getDetailHeadersForSummarization();
        //else
        detailHeaders = util.getDetailHeaders();
    }
    
    private void getDetailHeadersForSummarization() throws Exception{
        //check if there is a response from the server already
        if (extractor != null) {
            detailHeaders = new Vector<String>();
            
            for (int i = extractor.getAssociatedAttributes().size()-1; i >= 0; i--) {
                if (!detailHeaders.contains(extractor.getAssociatedAttributes().elementAt(i)))
                    detailHeaders.add(extractor.getAssociatedAttributes().elementAt(i));
            }
            for (int i = 0; i < extractor.getSameLevelAttributes().size(); i++) {
                if (!detailHeaders.contains(extractor.getSameLevelAttributes().elementAt(i)))
                    detailHeaders.add(extractor.getSameLevelAttributes().elementAt(i));
            }
            detailHeaders.addAll(new TableUtils(extractor).getMeasureNames());
        }
        //if not, stub table / do not render
        else {
            measureList = new Vector<String>();
            
        //    measureList.add("M1");
        //    measureList.add("M2");
        }
    }
    
    //************* accessors used for manager bean *******************
    public void setResponseTextDT(String responseTextDT) {
        this.responseTextDT = responseTextDT;
    }
    
    public String getResponseTextDT() {
        return responseTextDT;
    }
    
    public void setExtractor(ResponseExtractor extractor) {
        this.extractor = extractor;
    }
    
    public ResponseExtractor getExtractor() {
        return extractor;
    }
    
    public void setExtractorDT(ResponseExtractor extractorDT) {
        this.extractorDT = extractorDT;
    }
    
    public ResponseExtractor getExtractorDT() {
        return extractorDT;
    }
    
    public void setMeasureList(Vector<String> measureList) {
        this.measureList = measureList;
    }
    
    public void setHeaderList(Vector<String> headerList) {
        this.headerList = headerList;
    }
    
    public void setHeaderList2(Vector<String> headerList2) {
        this.headerList2 = headerList2;
    }
    
    public void setData(Vector<Vector<String>> data) {
        this.data = data;
    }
    
    public void setDetailData(Vector<Vector<String>> detailData) {
        this.detailData = detailData;
    }
    
    public void setStubIndex(Vector<String> stubIndex) {
        this.stubIndex = stubIndex;
    }
    
    public void setNumberOfHeaderAttributes(int numberOfHeaderAttributes) {
        this.numberOfHeaderAttributes = numberOfHeaderAttributes;
    }
    
    public void setNumberOfInLineAttributes(int numberOfInLineAttributes) {
        this.numberOfInLineAttributes = numberOfInLineAttributes;
    }
    
    public void setColspanHeader1(int colspanHeader1) {
        this.colspanHeader1 = colspanHeader1;
    }
    
    public void setColspanHeader2(int colspanHeader2) {
        this.colspanHeader2 = colspanHeader2;
    }
    
    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }
    
    public void setGeometryType(String geometryType) {
        this.geometryType = geometryType;
    }
    
    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }
    
    public void setNumberOfBars(int numberOfBars) {
        this.numberOfBars = numberOfBars;
    }

    public void setTableUtils(TableUtils tableUtils) {
        this.tableUtils = tableUtils;
    }
    
    public TableUtils getTableUtils() {
        return tableUtils;
    }
    
    public void setDetailHeaders(Vector<String> detailHeaders) {
        this.detailHeaders = detailHeaders;
    }
    
    public void setRowdata0(String rowdata0) {
        this.rowdata0 = rowdata0;
    }
    
    public String getRowdata0() {
        return rowdata0;
    }
    
    public void setRowdata1(String rowdata1) {
        this.rowdata1 = rowdata1;
    }
    
    public String getRowdata1() {
        return rowdata1;
    }

    public void setHiddenElement(String hiddenElement) {
        this.hiddenElement = hiddenElement;
    }

    public String getHiddenElement() {
        return hiddenElement;
    }

    public void setNumberAssocAttr(String numberAssocAttr) {
        this.numberAssocAttr = numberAssocAttr;
    }

    public String getNumberAssocAttr() {
        return numberAssocAttr;
    }

    public String getAssoc1() {
        return assoc1;
    }

    public String getAssoc2() {
        return assoc2;
    }

    public String getLegendField() {
        return legendField;
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }
    
    public void rebootErrorHandler() {
        errorHandler = new ErrorHandler();
    }

    private void evaluateAnswer(String response) throws Exception {
        Document answer = new XMLUtils().XmlToDoc(response);
        NodeList error = answer.getElementsByTagName("ERROR");
        
        System.out.println("EVALUATE ANSWER: " + response + " ----- " + error.getLength());
        
        if(responseText.equals("empty") || error.getLength() > 0) {
            throw new Exception();   
        }
        else  { /* Seems the answer is right */ }
    }

    public void setSummarization(boolean summarization) {
        this.summarization = summarization;
    }

    public boolean isSummarization() {
        return summarization;
    }

    public void setGroupsColors(String groupsColors) {
        this.groupsColors = groupsColors;
    }

    public String getGroupsColors() {
        return groupsColors;
    }
}
