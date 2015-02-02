package solap.styles;

import java.util.HashMap;
import java.util.Map;

public class StyleCache<K,V> implements IStylesCache<K,V> {
    private Map<K,V> cache;
    
    public StyleCache() {
        reset();
    }

    public void reset() {
        cache = new HashMap<K,V>();
    }

    public void addElement(K key, V value) {
        cache.put(key,value);
    }

    public V getElement(K key) {
        return cache.get(key);
    }
    
    public boolean contains(K key){
        return cache.containsKey(key);
    }
    
    public boolean containsValue(V value){
        return cache.containsValue(value);
    }
}
