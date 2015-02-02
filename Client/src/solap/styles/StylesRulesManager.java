package solap.styles;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class StylesRulesManager {
 
    private Map<Context, List<TypeStyle>> decisionTree;
    
    public StylesRulesManager() {
        decisionTree = new HashMap<Context, List<TypeStyle>>();
    }
    
    public void addEntryLookupTable(Context context, List<TypeStyle> listTypeStyles) {
        decisionTree.put(context, listTypeStyles); 
    }
    
    public List<TypeStyle> getApplicableTypeStyles(Context context) {
        return decisionTree.get(context);
    }
    
    public Map<TypeStyle, List<Style>> getApplicableStylesForSemanticCharacterization(Map<TypeStyle, List<Style>> applicableStyles){
        Map<TypeStyle, List<Style>> aux = new HashMap<TypeStyle, List<Style>>(applicableStyles);
        Map<TypeStyle, List<Style>> result = new HashMap<TypeStyle, List<Style>>();
        Set<TypeStyle> keys = aux.keySet();
        for(TypeStyle ts : keys) {
            if(ts instanceof TypeSimple) {
                System.out.println("SOU DO TIPO SIMPLES");
                if(((TypeSimple)ts).getTypeStyle().equalsIgnoreCase("VariableColor")){
                    System.out.println("SOU DE VARIABLE COLOR");
                    result.put(ts,aux.get(ts));
                }
            }
        }
        keys = result.keySet();
        for(TypeStyle ts : keys) {
            System.out.println("SO QUERO VER ESTE PRINT ESCRITO UMA VEZ");
            List<Style> listOfStyles = result.get(ts);
            List<Style> newList = new LinkedList<Style>();
            for(Style s : listOfStyles){
                if(s instanceof DiscreteStyle)
                    newList.add(s);
            }
            result.put(ts, newList);
        }
        return result;
    }

    public Map<TypeStyle, List<Style>> getApplicableStyles(Context context,
                                                    Map<TypeStyle, List<Style>> styles,
                                                    List<TypeStyle> applicableTypes) {
        
        Map<TypeStyle, List<Style>> result = new HashMap<TypeStyle, List<Style>>(styles);
        
        Iterator<Map.Entry<TypeStyle, List<Style>>> it1 = result.entrySet().iterator();
        while(it1.hasNext()) {
            
            Map.Entry<TypeStyle, List<Style>> next1 = it1.next();
            
            if(applicableTypes.contains(next1.getKey())) {
                Iterator<Style> it2 = next1.getValue().iterator();
                
                while(it2.hasNext()) {
                    Style next2 = it2.next();
                    
                    if (toRemoveBasedOnAttr(context, next2, next1.getKey())) {
                        System.out.println("Remove Estilo: " + next2.getId());
                        it2.remove();
                    }
                }
            }
        }
        
        return result;
        
    }
    
    private boolean toRemoveBasedOnAttr(Context context, Style style, TypeStyle typeStyle) {
        
        if(typeStyle instanceof TypeSimple) {
           
            VisualProperty visualProperty = style.getVisualProperty();
          
            if(style instanceof ContinuosStyle) {
                if(context.isSpatialPoint()) {   
                    if(visualProperty instanceof VariableBrightness) {
                        if( ( (VariableBrightness)visualProperty).getMarker().equals(ManagerStyles.EMPTY) ) {
                            return true;
                        }
                    }
                    if(visualProperty instanceof VariableColor) {
                        if( ((VariableColor)visualProperty).getMarker().equals(ManagerStyles.EMPTY)) {
                            return true;
                        }
                    }
                    if(visualProperty instanceof VariableSize) {
                        if( ((VariableSize)visualProperty).getShapeMarker().equals(ManagerStyles.EMPTY)) {
                            return true;
                        }
                    }
                }
                else if(context.getNumberOfSpatialObjects() == 2) {
                    if(visualProperty instanceof VariableSize) {
                        if( !((VariableSize)visualProperty).getShapeMarker().equals(ManagerStyles.EMPTY)) {   
                            return true;
                        }
                    }
                }
            }
        }    
        else if (typeStyle instanceof TypeComposite) {
            //Assume the composite style only is compounds by simple style
            boolean result = true;
            int continuos = 0;
            int discrete = 0;
            
            //The number of discrete and continuos styles must match with the number of alphanumeric columns and numeric columns
            for(Style tempStyle: ((CompositeStyle)style).getStyles()) {
                
                if(tempStyle instanceof ContinuosStyle)
                    continuos++;
                
                if(tempStyle instanceof DiscreteStyle)
                    discrete++;
            }
            
            System.out.println("Continuos: " + continuos + " Discrete: " + discrete);
            System.out.println(context.getNumberAlphaNumericalColumns() + " -- " + context.getNumberNumericalColumns());
            
            if(context.getNumberAlphaNumericalColumns() != discrete) {
                return true;
            }
            if(context.getNumberNumericalColumns() != continuos) {
                return true;
            }
            
            for(Style tempStyle: ((CompositeStyle)style).getStyles()) {        
                result = toRemoveBasedOnAttr(context, tempStyle, new TypeSimple("",tempStyle.getClass().getSimpleName()) );
                if(result)
                    return true;
            }
        }
        
        return false;
    }
}