package solap.styles;

public interface IStylesCache<K,V> {
    public void reset();
    
    public void addElement(K key, V value);
    
    public V getElement(K key);
    
    public boolean contains(K key);
    
    public boolean containsValue(V value);
}
