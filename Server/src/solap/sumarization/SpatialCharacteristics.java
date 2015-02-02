package solap.sumarization;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

public class SpatialCharacteristics {
    private static final String separationCharacter = "%%";
    private static final String UNDEFINED_STRING ="*UNDEFINED*";
    
    private HashMap<String,Integer> characteristics;
    private HashMap<String,HashMap<String,Double>> measuresValues;
    
    public SpatialCharacteristics() {
        this.characteristics = new HashMap<String,Integer>();
        this.measuresValues = new HashMap<String,HashMap<String,Double>>();
    }

    public SpatialCharacteristics(HashMap<String, Integer> characteristics) {
        this.characteristics = characteristics;
        this.measuresValues = new HashMap<String,HashMap<String,Double>>();
    }
    
    public void addCharacteristic(String characteristic){
        if(characteristics.containsKey(characteristic))
            characteristics.put(characteristic,characteristics.get(characteristic)+1);
        else
            characteristics.put(characteristic,1);
    }
    
    public void printCharacteristics(){
        Set<String> nonSpatialValues = characteristics.keySet();
        for(String nsValue : nonSpatialValues){
            System.out.println("\t " + nsValue.replace(separationCharacter," | ") + " : " + characteristics.get(nsValue));
            Set<String> measuresName = measuresValues.keySet();
            for(String name : measuresName){
                String operator = name.split("_")[0];
                System.out.println("\t\t " + name + " : " + getMeasureValueForCharacteristic(name,nsValue,operator)); 
            }
        }
    }
    
    public String getPredominantCharacteristicByMeasure(String measureName){
        //System.out.println("ESTOU NA ESCOLHA DA CARACTERISTICA DOMINANTE NA PARTE DE ESCOLHA PELO VALOR DA METRICA: " + measureName);
        String bestCharacterization = "";
        HashMap<String,Double> characteristicsValues = measuresValues.get(measureName);
        String operator = measureName.split("_")[0];
        double current = 0;
        if(operator.equals("MIN"))
            current = Integer.MAX_VALUE;
        else
            current = Integer.MIN_VALUE;
        Set<String> keys = characteristicsValues.keySet();
        for(String characteristic : keys){
            double value = getMeasureValueForCharacteristic(measureName,characteristic,operator);
            if(operator.equals("MIN")){
                if(value < current){
                    current=value;
                    bestCharacterization = characteristic;
                } 
            }
            else{
                //System.out.println(current + " E MENOR QUE " + value);
                if(value > current){
                    //System.out.println("ENTREI AQUI EVIDENTEMENTE");
                    current=value;
                    bestCharacterization = characteristic;
                } 
            }
        }
        return bestCharacterization;
    }
    
    public String getPredominantCharacteristicBySemanticAtt(){
        String bestCharacterization = "";
        Set<String> keys = characteristics.keySet();
        int max = Integer.MIN_VALUE;
        int countMaxs = 1;
        for(String caracteristic : keys){
            int value = characteristics.get(caracteristic);
            if(value > max){
                max=value;
                bestCharacterization = caracteristic;
                countMaxs = 1;
            }
            else if (value == max)
                countMaxs++;
        }
        if(countMaxs > 1){
            int numberOfFields = bestCharacterization.split(separationCharacter).length;
            bestCharacterization = "";
            for(int i=0;i<numberOfFields;i++){
                bestCharacterization += UNDEFINED_STRING;
                if(i != numberOfFields-1)
                    bestCharacterization += separationCharacter;
            }
        }
        return bestCharacterization;
    }
    
    public Vector<String> getDetailResults(){
        Vector<String> result = new Vector<String>();
        Set<String> keys = characteristics.keySet();
        for(String k: keys)
            result.add(k);
        return result;
    }
    
    public Vector<Integer> getOccurrences(){
        Vector<Integer> values = new Vector<Integer>();
        Set<String> keys = characteristics.keySet();
        for(String caracteristic : keys)
            values.add(characteristics.get(caracteristic));
        return values;
    }
    
    public void addMeasureValue(String measureName, String characteristic, double measureValue, String operator){
        if(!measuresValues.containsKey(measureName)){
            HashMap<String,Double> map = new HashMap<String,Double>();
            map.put(characteristic,measureValue);
            measuresValues.put(measureName, map);
        }
        else{
            HashMap<String,Double> characteristicsValues = measuresValues.get(measureName);
            if(characteristicsValues.containsKey(characteristic))
                measureValue = calcValue(characteristicsValues.get(characteristic), measureValue, operator);
            characteristicsValues.put(characteristic,measureValue);
            measuresValues.put(measureName,characteristicsValues);
        }   
    }

    private double calcValue(Double aggvalue, double measureValue, String operator) {
        double result = aggvalue;
        if(operator.equals("SUM") || operator.equals("AVG")){
            result = aggvalue + measureValue;
        }
        else if(operator.equals("MAX")){
            if(measureValue > aggvalue)
                result = measureValue;
        }
        else if(operator.equals("MIN")){
            if(measureValue < aggvalue)
                result = measureValue;
        }
        return result;
    }
    
    public double getMeasureValueForCharacteristic(String measureName, String characteristic, String operator){
        //System.out.println(measureName);
        //System.out.println(characteristic);
        //if(!measuresValues.containsKey(measureName))
        //    System.out.println("Nao devia aparecer isto escrito");
        //if(! measuresValues.get(measureName).containsKey(characteristic))
        //    System.out.println("Nao devia aparecer isto escrito");
        double result = 0; 
        if(characteristic.contains("*UNDEFINED*"))
            return result;
        result = measuresValues.get(measureName).get(characteristic);
        if(operator.equals("AVG")){
            double numOccurrences = characteristics.get(characteristic);
            result = result / numOccurrences;
        }
        return result;
    }
}
