package solap.utils;

import java.util.Vector;

import solap.ResponseExtractor;

public class TableUtils {
    ResponseExtractor extractor;
    
    //constants
    public static final String NULL_MARK = "";
    //auxiliary structures
    private Vector<String> spatialCombinations;
    
    
    //To improve the performance
    private Vector<String> headerAttributes;
    private Vector<String> inlineAttributes;
    private int numberOfRows;
    private int numberColumns;
    
    public TableUtils(ResponseExtractor extractor) throws Exception {
        this.extractor = extractor;
        this.headerAttributes = this.getHeaderAttributes();
        //System.out.println("PASSEI headers");
        this.inlineAttributes = this.getInLineAttributes();
        //System.out.println("PASSEI inline");
        this.numberOfRows = this.getNumberOfRows();
        //System.out.println("PASSEI rows : " + numberOfRows);
        this.numberColumns = this.getNumberOfColumns();
        //System.out.println("PASSEI columns : " + numberColumns);
    }
    
    public int getNumDistinctCharacteristics(){
        return Integer.parseInt(extractor.getNumDistincCharacteristics());
    }
    
    public int getNumberOfGroups(){
        return Integer.parseInt(extractor.getNumberOfGroups());
    }
    
    public int getHeaderAttrSize() throws Exception {
        return headerAttributes.size();
    }
    
    public int getInlineAttrSize() throws Exception {
        return inlineAttributes.size();
    }
    
    //puts together all attribute names that go in the headers
    public Vector<String> getHeaderAttributes() throws Exception {
        Vector<String> headers = new Vector<String>();
        
        //different hierarchies
        Vector<String> temp = new Vector<String>();
        temp = extractor.getDifferentHierarchyAttributes();
        for (int i = 0; i < temp.size(); i++) {
            headers.add(temp.elementAt(i));
        }
        
        //lower attributes
        temp = new Vector<String>();
        temp = extractor.getLowerLevelAttributes();
        for (int i = 0; i < temp.size(); i++) {
            headers.add(temp.elementAt(i));
        }
        
        //different dimension
        temp = new Vector<String>();
        temp = extractor.getDifferentDimensionAttributes();
        for (int i = 0; i < temp.size(); i++) {
            headers.add(temp.elementAt(i));
        }
        
        return headers;
    }
    
    //puts together all attribute names that go in-line with the data
    public Vector<String> getInLineAttributes() throws Exception {
        Vector<String> inLine = new Vector<String>();
        
        //associated attributes
        Vector<String> temp = new Vector<String>();
        temp = extractor.getAssociatedAttributes();
        for (int i = 0; i < temp.size(); i++) {
            if (!inLine.contains(temp.elementAt(i))) {
                inLine.add(temp.elementAt(i));
            }
        }
        
        //same level
        temp = new Vector<String>();
        temp = extractor.getSameLevelAttributes();
        for (int i = 0; i < temp.size(); i++) {
            if (!inLine.contains(temp.elementAt(i))) {
                inLine.add(temp.elementAt(i));
            }
        }
        
        //higher level
        temp = new Vector<String>();
        temp = extractor.getHigherLevelAttributes();
        for (int i = 0; i < temp.size(); i++) {
            if (!inLine.contains(temp.elementAt(i))) {
                inLine.add(temp.elementAt(i));
            }
        }

        return inLine;
    }
    
    //returns distinct values for attribute i inside headerAttributes
    public Vector<String> getHeaderValues(int index) throws Exception {
        Vector<String> values = new Vector<String>();
        String attribute = this.headerAttributes.elementAt(index);
        
        int elementPositionInRow = extractor.getElementPosition(attribute.toUpperCase());
        values = extractor.getRowsetDistincts(elementPositionInRow);
        
        return values;
    }
    
    //returns correct number of measure names for header
    public Vector<String> getMeasureNames() throws Exception {
        Vector<String> ready = new Vector<String>();
        if(Integer.parseInt(extractor.getNumberOfMeasures()) == 0)
            return ready;
        
        Vector<String> temp = extractor.getMeasureNames();    
        
        int numColumns = this.numberColumns;
        int underColumns = numColumns - this.inlineAttributes.size();
        int repeat = underColumns / Integer.parseInt(extractor.getNumberOfMeasures());
        
        //clone measure headers to match columns
        for (int i = 0; i < repeat; i++) {
            for (int j = 0; j < temp.size(); j++) {
                ready.add(temp.elementAt(j));
            }
        }
        
        return ready;
    }
    
    //returns correct number of attribute names for header
    public Vector<String> getHeaderNames(int index, int pos) throws Exception {
        System.out.println("ENTREI NO GETHEADERNAMES");
        Vector<String> temp = new Vector<String>();
        Vector<String> ready = new Vector<String>();
        
        String attribute = this.headerAttributes.elementAt(index);
        int elementPositionInRow = extractor.getElementPosition(attribute.toUpperCase());
        temp = extractor.getRowsetDistincts(elementPositionInRow);
        
        int numColumns = this.numberColumns;
        int underColumns = numColumns - this.inlineAttributes.size();
        int repeat = underColumns / Integer.parseInt(extractor.getNumberOfMeasures());
        repeat = repeat / temp.size();
        
        //divider for upper headers
        int divider = 1;
        while (pos > 0) {
            divider = divider * getHeaderValues(pos-1).size();
            pos--;
        }
        repeat = repeat / divider;
        
        //clone measure headers to match columns
        for (int i = 0; i < repeat; i++) {
            for (int j = 0; j < temp.size(); j++) {
                ready.add(temp.elementAt(j));
            }
        }
        
        return ready;
    }
    
    //returns number of columns in the final table
    public int getNumberOfColumns() throws Exception {
        int temp = 1;
        
        int temNumberHeadersAttributes = this.headerAttributes.size();
        for (int i = 0; i < temNumberHeadersAttributes; i++) {
            temp *= getHeaderValues(i).size();
        }
        
        return (temp * Integer.parseInt(extractor.getNumberOfMeasures())) + this.inlineAttributes.size();
    }
    //returns number of rows in the final table
    public int getNumberOfRows() throws Exception {
        spatialCombinations = new Vector<String>();
        
        int tempNumberOfRows = Integer.parseInt(extractor.getNumberOfRows());
        //parse all <ROW>
        for (int i = 0; i < tempNumberOfRows; i++) {
            String possibleNewCombination = "";
            //parse all associated attributes
            
            int tempNumberAssocSize = extractor.getAssociatedAttributes().size();
            for (int j = 0; j < tempNumberAssocSize; j++) {
                int pos = extractor.getElementPosition(extractor.getAssociatedAttributes().elementAt(j).toUpperCase());
                possibleNewCombination += extractor.getElementValue(i, pos);
            }
            //add if it's not there already
            if (!spatialCombinations.contains(possibleNewCombination)) {
                spatialCombinations.add(possibleNewCombination);
            }
        }
        
        return spatialCombinations.size();
    }
    
    //returns table data (Vector<Vector<String>>)
    public Vector<Vector<String>> getStructuredData() throws Exception {
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        
        //initialize structure
        for (int i = 0; i < this.numberOfRows; i++) {
            Vector<String> stubs = new Vector<String>();
            for (int j = 0; j < this.numberColumns; j++) {
                stubs.add(NULL_MARK);
            }
            data.add(stubs);
        }
        
        //add data from ROWSET
        data = addRowsetData(data);
        
        return data;
    }
    
    //adds the actual data to the initialized structure
    private Vector<Vector<String>> addRowsetData(Vector<Vector<String>> data) throws Exception {
        //prepare nDistincts auxiliary structure
        Vector<Integer> nDistincts = new Vector<Integer>();
        
        int numberOfMeasures = Integer.parseInt(extractor.getNumberOfMeasures());
        
        nDistincts.add(numberOfMeasures);
        for (int j = 0; j < this.headerAttributes.size(); j++) {
            System.out.println("ENTREI E NAO DEVIA ENTRAR");
            int value = getHeaderValues(j).size();
            nDistincts.add(value);
        }
        //prepare factors auxiliary structure
        Vector<Integer> factors = new Vector<Integer>();
        factors.add(1);     //measures multiplying factor (1)
        for (int j = 0; j < this.headerAttributes.size(); j++) {
            System.out.println("ENTREI E NAO DEVIA ENTRAR");
            int value = factors.elementAt(factors.size()-1) * nDistincts.elementAt(factors.size()-1);
            factors.add(value);
        }
        
        //parse all <ROW>
        for (int i = 0; i < Integer.parseInt(extractor.getNumberOfRows()); i++) {
            //get correct row
            String spatialCombination = "";
            //parse all associated attributes
            for (int k = 0; k < extractor.getAssociatedAttributes().size(); k++) {
                int pos = extractor.getElementPosition(extractor.getAssociatedAttributes().elementAt(k).toUpperCase());
                //System.out.println("VER O VALOR -----> " + extractor.getElementValue(i, pos));
                spatialCombination += extractor.getElementValue(i, pos);
            }
            int row = spatialCombinations.indexOf(spatialCombination);
            int temp = 0;
            //for each header attribute
            for (int j = 0; j < this.headerAttributes.size(); j++) {
                System.out.println("ENTREI E NAO DEVIA ENTRAR");
                int elemPos = extractor.getElementPosition(this.headerAttributes.elementAt(j).toUpperCase());
                Vector <String> attributeDistincts = extractor.getRowsetDistincts(elemPos);
                int valueIndex = attributeDistincts.indexOf(extractor.getElementValue(i, elemPos));
                temp = temp + valueIndex * factors.elementAt(j+1);
            }
            //for each measure (also add number of inline attributes)
            for (int j = 0; j < numberOfMeasures; j++) {
                //System.out.println("ENTREI NAS METRICAS");
                int col = (temp + j) + this.inlineAttributes.size();
                //System.out.println("Linha " + i + " Pos: " + col);
                String measureValue = extractor.getElementValue(i, j);
                //format numbers
                //System.out.println("Antes: " + measureValue);
                if(!measureValue.equals(" "))
                    measureValue = formatNumber(measureValue, 2);
                //System.out.println("Depois: " + measureValue);
                data.elementAt(row).set(col, measureValue);
            }
            //add inline values
            for (int j = 0; j < this.inlineAttributes.size(); j++) {
                //System.out.println(this.inlineAttributes.elementAt(j).toUpperCase());
                int elemPos = extractor.getElementPosition(this.inlineAttributes.elementAt(j).toUpperCase());
                String value = extractor.getElementValue(i, elemPos);
                //System.out.println("row:" + row + " elemPos:" + elemPos + " value:" + value);
                data.elementAt(row).set(j, value);
            }
        }
        
        //return modified structure
        return data;
    }

    public void setSpatialCombinations(Vector<String> spatialCombinations) throws Exception {
        this.spatialCombinations = spatialCombinations;
    }
    public Vector<String> getSpatialCombinations() throws Exception {
        return spatialCombinations;
    }
    
    private String formatNumber(String value, int decimals) throws Exception {
        int indexOfE = value.indexOf("E");
        String newValue = value;
        if(indexOfE >= 0){
            newValue = value.substring(0, value.indexOf("."));
            System.out.println(newValue);
            System.out.println("Valor do e: " + value.substring(indexOfE+1));
            int e = Integer.parseInt(value.substring(indexOfE+1));
            System.out.println("Valor do e: " + e);
            int lengthDecimal = value.substring(value.indexOf(".")+1, indexOfE).length();
            System.out.println("Valor do decimal: " + lengthDecimal);
            int newE = e;
            if(e > 0){
                if(lengthDecimal < e)
                   newE = lengthDecimal;
                newValue += value.substring(value.indexOf(".")+1, value.indexOf(".")+1 + newE);
                System.out.println(newValue);
                if(lengthDecimal < e){
                    for(int i=0;i< e-lengthDecimal; i++)
                        newValue += "0";
                }
                System.out.println(newValue);
                if(lengthDecimal > e)
                    newValue += "." + value.substring(value.indexOf(".")+1 + newE, indexOfE);
                System.out.println(newValue);
            }
            else{
                System.out.println("ENTREI NO CASO EM QUE O E e negativo");
                return value;
                //if(newValue.length() <= Math.abs(e)){
                //    String theNewValue = "0.";
                //    for(int i=0;i< Math.abs(e)-newValue.length(); i++)
                //        theNewValue += "0";
                //    newValue = theNewValue + newValue;
                //}
                //else if(newValue.length() > Math.abs(e)){
                //    String oldValue = newValue;
                //    newValue = newValue.substring(newValue.length()-Math.abs(e));
                //    newValue += "." + oldValue.substring(newValue.length()-Math.abs(e), oldValue.length()-1);
                //}
            }
        }
        if (newValue.indexOf(".") >= 0) {
            if ((newValue.indexOf(".") + decimals) < newValue.length()) {
                return newValue.substring(0, newValue.indexOf(".") + decimals + 1);
            }
            else {
                newValue += "0";
                return newValue;
            }
        }
        else {
            newValue += ".";
            for (int i = 0; i < decimals; i++)
                newValue += "0";
            return newValue;
        }       
    }
    
    
    //********************* detail table ***************************************
    public int getDetailNumberOfRows() throws Exception {
        return Integer.parseInt(extractor.getNumberOfRows());
    }
    
    public int getDetailNumberOfColumns() throws Exception {
        return getHeaderAttributes().size() + Integer.parseInt(extractor.getNumberOfMeasures()) + getInLineAttributes().size();
    }
    
    //TODO: optimized the computation
    
    
    public Vector<Vector<String>> getDetailData() throws Exception {
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        
        //initialize structure
        for (int i = 0; i < getDetailNumberOfRows(); i++) {
            Vector<String> stubs = new Vector<String>();
            for (int j = 0; j < getDetailNumberOfColumns(); j++) {
                stubs.add(NULL_MARK);
            }
            data.add(stubs);
        }
        
        //add rowset data
        data = addDetailRowsetData(data);
        
        return data;
    }
    
    private Vector<Vector<String>> addDetailRowsetData(Vector<Vector<String>> data) throws Exception {
        //parse all <ROW>
        for (int i = 0; i < Integer.parseInt(extractor.getNumberOfRows()); i++) {
            int col = 0;
            //parse all inline attributes
            for (int j = 0; j < getDetailInLineAttributes().size(); j++) {
                int pos = extractor.getElementPosition(getDetailInLineAttributes().elementAt(j).toUpperCase());
                String value = extractor.getElementValue(i, pos);
                data.elementAt(i).set(col, value);
                col++;
            }
            //parse all header attributes
            for (int j = 0; j < getDetailHeaderAttributes().size(); j++) {
                int pos = extractor.getElementPosition(getDetailHeaderAttributes().elementAt(j).toUpperCase());
                String value = extractor.getElementValue(i, pos);
                data.elementAt(i).set(col, value);
                col++;
            }
            //parse all measures
            for (int j = 0; j < getDetailMeasureNames().size(); j++) {
                int pos = extractor.getElementPosition(getDetailMeasureNames().elementAt(j).toUpperCase());
                String value = extractor.getElementValue(i, pos);
                //format numbers
                System.out.println("Antes: " + value);
                value = formatNumber(value, 2);
                System.out.println("Depois: " + value);
                data.elementAt(i).set(col, value);
                col++;
            }
        }
        
        return data;
    }
    
    public Vector<String> getDetailHeaders() throws Exception {
        Vector<String> temp = new Vector<String>();
        
        //parse all inline attributes
        for (int j = 0; j < getDetailInLineAttributes().size(); j++) {
            temp.add(getDetailInLineAttributes().elementAt(j));
        }
        //parse all header attributes
        for (int j = 0; j < getDetailHeaderAttributes().size(); j++) {
            temp.add(getDetailHeaderAttributes().elementAt(j));
        }
        //parse all measures
        for (int j = 0; j < getDetailMeasureNames().size(); j++) {
            temp.add(getDetailMeasureNames().elementAt(j));
        }
        
        return temp;
    }
    
    //auxiliary functions
    //TODO
    private Vector<String> getDetailInLineAttributes() throws Exception {
        return getInLineAttributes();
    }
    
    //TODO
    private Vector<String> getDetailHeaderAttributes() throws Exception {
        return getHeaderAttributes();
    }
    
    //TODO
    private Vector<String> getDetailMeasureNames() throws Exception {
        return extractor.getMeasureNames();
    }

    public int getNumberOfRows1() throws Exception {
        return numberOfRows;
    }

    public int getNumberColumns() throws Exception {
        return numberColumns;
    }
}
