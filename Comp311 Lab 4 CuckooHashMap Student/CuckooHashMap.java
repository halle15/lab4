import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

public class CuckooHashMap<K, V> implements Map<K, V> {
	private List<MapEntry> table;
	int numBuckets;
	private int size;

	public CuckooHashMap(int numBuckets) {
		table = new ArrayList<MapEntry>(numBuckets);
		this.numBuckets = numBuckets;
		this.size = 0;
		for (int i = 0; i < numBuckets; i++) {
			table.add(i, null);
		}
	}

	public MapEntry getEntry(int index) {
		// This works, don't touch.
		return table.get(index);

	}

	@Override
	public void clear() {
		table = new ArrayList<MapEntry>(10);
		for(int i = 0; i < 10; i++) {
			table.add(null);
		}
		size = 0;
		numBuckets = 10;

	}

	@Override
	public boolean isEmpty() {
		return(size == 0);
	}

	public int size() {
		return size;
	}

	@Override
	public V get(Object key) {
		for (MapEntry e : table) {
			try {
				if (e.getKey().equals(key)) {
					return e.getValue();
				}
			} catch (Exception a) {

			}
		}
		return null;
	}

	@Override
	public boolean containsKey(Object key) {

		for (MapEntry m : table) {
			try {

				if (m.getKey().equals(key)) {
					return true;
				}
			} catch (Exception a) {

			}
		}
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		for (MapEntry m : table) {
			try {

				if (m.getValue().equals(value)) {
					return true;
				}
			} catch (Exception a) {

			}
		}
		return false;
	}

	private int hf1(int hashcode) {
		return Math.abs(hashcode) % numBuckets;

	}

	private int hf2(int hashcode) {
		return (hashcode + 3) % numBuckets;

	}

	private void resize() {
		List<MapEntry> old = table;
		int amt = numBuckets * 2;
		table = new ArrayList<MapEntry>((int) amt);
		numBuckets = amt;

		while (table.size() < amt) {
			table.add(null);
		}

		for (MapEntry e : old) {
			if (e != null) {
				insert(e);
			}
		}

	}

	public V insert(MapEntry e) {

		MapEntry x = e;

		V prevVal = null;

		int currentPos;

		int pos1 = hf1(x.key.hashCode());

		int pos2 = hf2(pos1);

		try {
			
			if (table.get(pos1).key == e.key) {
				size--;
				return table.get(pos1).value;

			}

			if (table.get(pos2).key == e.key) {
				size--;
				return table.get(pos2).value;

			}

		}

		catch (Exception a) {

		}

		currentPos = pos1;
		try {
		prevVal = table.get(currentPos).getValue();
		}catch(Exception c) {
		}

		for (int i = 0; i < 10; i++) {

			if (table.get(currentPos) == null) {

				table.set(currentPos, x);
				
				return null;

			}

			else {

				MapEntry a = table.get(currentPos);

				table.set(currentPos, x);

				x = a;

				prevVal = x.value;

				if (currentPos == hf1(x.key.hashCode())) {

					currentPos = hf2(hf1(x.key.hashCode()));

				}

				else {

					currentPos = hf1(x.key.hashCode());

				}

			}

		}

		resize();

		insert(x);


		return prevVal;

	}

	public V put(K key, V value) {
		MapEntry e = new MapEntry(key, value);
		if (e != null) {
			size++;
		}
		return (insert(e));

		/*
		 * for(int i = 0; i < 10; i++) { if(table.get(pos).isNull()){ table.add(pos, e);
		 * return value; } else { System.out.println("went here"); table.set(hf2(pos),
		 * table.get(pos)); System.out.println(pos + " " + hf2(pos)); table.set(pos, e);
		 * return value; }
		 * 
		 * 
		 * 
		 * //} //return value;
		 */
	}

	public V remove(Object key) {
		if (get(key) != null) {
			@SuppressWarnings("unchecked")
			MapEntry e = new MapEntry((K) key, get(key));

			for (int i = 0; i < table.size(); i++) {
				e = table.get(i);
				if (e != null) {
					if (e.key == key) {
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
		for (MapEntry m : table) {
			if(m != null) {
			r = r.concat("KEY: " + m.key + "|VALUE: " + m.value + "\n");
			}
			else {
				
			}
		}

		return r;
	}
	
	private void putCheck(K k, V v) {
		if(k != null && v != null) {
			put(k, v);
		}
	}

	public void putAll(Map<? extends K, ? extends V> map) {
		
		
	map.forEach((k, v) -> putCheck(k, v)); // if this works i can not believe I implemented a lambda properly
		
		//Iterator<Map.Entry<K, V>> itr = map.entrySet().iterator();
	}
	

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		Set<Map.Entry<K, V>> entrySet = new HashSet<Map.Entry<K, V>>();
		for(MapEntry e : table) {
			if(e != null) {
				Map.Entry<K, V> entry;
				entry = new AbstractMap.SimpleEntry<K, V>(e.getKey(), e.getValue());
				
				entrySet.add(entry);
			}
		}
		return entrySet;
	}

	public Set<K> keySet() {
		Set<K> keySet = new HashSet<K>();
		for(MapEntry e : table) {
			if(e != null) {
				keySet.add(e.getKey());
			}
		}
		
		return keySet;
	}

	public Collection<V> values() {
		Collection<V> vals = new ArrayList<V>();
		for(MapEntry e : table) {
			if(e != null) {
				vals.add(e.getValue());
			}
		}
		return vals;
	}

	public class MapEntry implements Entry<K, V> {
		private K key;
		private V value;

		/**
		 * Creates a MapEntry.
		 * 
		 * @param akey   the key
		 * @param avalue the value
		 */
		public MapEntry(K akey, V avalue) {
			this.key = akey;
			this.value = avalue;
		}

		public boolean isNull() {
			return (key == null && value == null);

		}

		/**
		 * Returns the key for this entry.
		 * 
		 * @see java.util.Map$Entry#getKey()
		 * @return the key for this entry
		 */
		@Override
		public K getKey() {
			return key;
		}

		/**
		 * Returns the value for this entry.
		 * 
		 * @see java.util.Map$Entry#getValue()
		 * @return the value for this entry
		 */
		@Override
		public V getValue() {
			return value;
		}

		/**
		 * Sets the value for this entry.
		 * 
		 * @see java.util.Map$Entry#setValue(V)
		 * @param val
		 * @return the previous value for this entry
		 */
		public V setValue(V newValue) {
			V oldVal = value;
			value = newValue;
			return oldVal;
		}
	}
}
