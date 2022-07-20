import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.List;

public class CuckooHashMap<K, V> implements Map<K, V>
{
    private List<MapEntry> table;
    int numBuckets;

    public CuckooHashMap(int numBuckets)
    {
    	table = new ArrayList<MapEntry>(numBuckets);
    	this.numBuckets = numBuckets;
    	for(int i = 0; i < numBuckets; i++) {
    		table.add(i, null);
    	}
    }
    
    public MapEntry getEntry(int index)
    {
        // This works, don't touch.
        return table.get(index);
        
    }
    
    @Override
    public void clear()
    {
    	table = new ArrayList<MapEntry>(10);
    	
    }
    
    @Override
    public boolean isEmpty()
    {
        return table.isEmpty();
    }

    public int size()
    {
    	int ct = 0;
    	for(MapEntry m : table) {
    		if(m != null) {
    			ct += 1;
    		}
    	}
    	return ct;
    }
    
    @Override
    public V get(Object key)
    {
    	for(MapEntry e : table) {
    		try {
    		if(e.getKey().equals(key)) {
    			return e.getValue();
    		}
    		}
    		catch(Exception a) {

    		}
    	}
      return null;
    }

    @Override
    public boolean containsKey(Object key)
    {
    	
        for(MapEntry m : table) {
        	try {
        
        	if(m.getKey().equals(key)) {
        		return true;
        	}
        	}
        	catch(Exception a) {
        		
        	}
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value)
    {
        for(MapEntry m : table) {
        	try {
        
        	if(m.getValue().equals(value)) {
        		return true;
        	}
        	}
        	catch(Exception a) {
        		
        	}
        }
        return false;
    }
    
    private int hf1(int hashcode) {
    	return Math.abs(hashcode) % table.size();
    	
    }
    
    private int hf2(int hashcode) {
    	System.out.println((hashcode + 3) % 20);
    	return (hashcode + 3) % table.size();
    	
    }
    
    private void resize() {
    	List<MapEntry> old = table;
    	int amt = numBuckets * 2;
    	table = new ArrayList<MapEntry>((int) amt);
    	numBuckets = table.size();
    	
    	while(table.size() < amt) {
    		table.add(null);
    	}
    	System.out.println("BRUH: " + table.size());
    	
    	for(MapEntry e : old) {
    		if(e != null) {
    			insert(e);
    			System.out.println("WE INSERTED");
    		}
    	}
    	
    }
    
    public V insert(MapEntry e) {
    	MapEntry x = e;
    	V ret = null;
    	int currentPos;
    	
    	int pos1 = hf1(x.key.hashCode());
    	int pos2 = hf2(pos1);
    	
    	try {
    		
    	if(table.get(pos1).equals(x)) {
    		return table.get(pos1).value;
    	}
    	
    	if(table.get(pos2).equals(x)) {
    		return table.get(pos2).value;
    	}
    	
    	}
    	catch(Exception a) {
    		
    	}
    	
    	currentPos = hf1(pos1);
    	
    	for(int i = 0; i < 10; i++){
    		if(table.get(currentPos) == null) {
    			table.set(currentPos, x);
 
    			return ret;
    		}
    		else {
    			
    			MapEntry a = table.get(currentPos);
    			table.set(currentPos, x);
    			x = a;
    			ret = x.value;
    			if(currentPos == hf1(x.key.hashCode())) {
    				currentPos = hf2(hf1(x.key.hashCode()));
    				
    			}
    			else {
    				currentPos = hf1(x.key.hashCode());
    				
    			}
    			
    			
    		}
    	}
    	resize();
    	insert(x);
    	ret = x.getValue();
    	return ret;
    	// insert rehash

    	
    	
    }

    public V put(K key, V value)
    {
    	MapEntry e = new MapEntry(key, value);

    	return(insert(e));
    	
    	/*for(int i = 0; i < 10; i++) {
    		if(table.get(pos).isNull()){
    			table.add(pos, e);
    			return value;
    		}
    		else {
    			System.out.println("went here");
    			table.set(hf2(pos), table.get(pos));
    			System.out.println(pos + " " + hf2(pos));
    			table.set(pos, e);
    			return value;
    		}

    	
    	
    	//}
    	//return value;*/
    }
    	

    
    public V remove(Object key)
    {
    	if(get(key) != null) {
    		@SuppressWarnings("unchecked")
			MapEntry e = new MapEntry((K) key, get(key)) ;
    		

    		
    		for(int i = 0; i < table.size(); i++) {
    			System.out.println(table.size());
    			e = table.get(i);
    			if(e != null) {
    			if(e.key == key) {
    				table.remove(i);
    				return e.getValue();
    			}
    		}
    		}

    	}
        return null;
    }
    
    @Override
    public String toString() {
    	String r = "";
    	for(MapEntry m : table) {
    		r = r.concat("KEY: " + m.key + "|VALUE: " + m.value + "\n");
    	}
    	
    	return r;
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
        
        public boolean isNull() {
        	return(key == null && value == null);

        	
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
