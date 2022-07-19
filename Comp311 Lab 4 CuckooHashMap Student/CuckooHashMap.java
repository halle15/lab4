import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CuckooHashMap<K, V> implements Map<K, V>
{
    public CuckooHashMap(int numBuckets)
    {
    }
    
    @Override
    public void clear()
    {
    }
    
    @Override
    public boolean isEmpty()
    {
        return false;
    }

    public int size()
    {
        return 0;
    }
    
    @Override
    public V get(Object key)
    {
        return null;
    }
    
    public MapEntry getEntry(int index)
    {
        return null;
    }

    @Override
    public boolean containsKey(Object key)
    {
        return false;
    }

    @Override
    public boolean containsValue(Object value)
    {
        return false;
    }

    public V put(K key, V value)
    {
        return null;
    }
    
    public V remove(Object key)
    {
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> map)
    {
    }

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet()
    {
        return null;
    }

    public Set<K> keySet()
    {
        return null;
    }

    public Collection<V> values()
    {
        return null;
    }
    
    public class MapEntry implements Entry<K, V>
    {
        private K key;
        private V value;
 
        /**
         * Creates a MapEntry.
         * 
         * @param akey the key
         * @param avalue the value
         */
        public MapEntry(K akey, V avalue)
        {
            this.key = akey;
            this.value = avalue;
        }

        /**
         * Returns the key for this entry.
         * 
         * @see java.util.Map$Entry#getKey()
         * @return the key for this entry
         */
        @Override
        public K getKey()
        {
            return key;
        }

        /**
         * Returns the value for this entry.
         * 
         * @see java.util.Map$Entry#getValue()
         * @return the value for this entry
         */
        @Override
        public V getValue()
        {
            return value;
        }

        /**
         * Sets the value for this entry.
         * 
         * @see java.util.Map$Entry#setValue(V)
         * @param val
         * @return the previous value for this entry
         */
        public V setValue(V newValue)
        {
            V oldVal = value;
            value = newValue;
            return oldVal;
        }
    }
}
