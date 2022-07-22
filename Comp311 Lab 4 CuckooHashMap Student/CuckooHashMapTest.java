import java.util.HashMap;
import java.util.Hashtable;

import junit.framework.TestCase;

public class CuckooHashMapTest extends TestCase
{
    public void testPutGet() {
        CuckooHashMap<Integer, String> m = new CuckooHashMap<>(10);
        HashMap<Integer, String> ma = new HashMap<>(10);
        
        assertEquals(0, m.size());
        assertNull(m.get(3));

        m.put(3, "three");
        m.put(3, "three");
        ma.put(3, "three");
        ma.put(3, "three");
        assertEquals(1, ma.size());
        assertEquals(1, m.size());
       // assertEquals("three", m.get(3));
        
        

    }
    
    public void testPutAll() {
        HashMap<Integer, String> m = new HashMap<>(10);
        CuckooHashMap<Integer, String> ma = new CuckooHashMap<>(10);
        
        assertEquals(0, m.size());
        assertNull(m.get(3));

        m.put(3, "three");
        m.put(3, "three");
        ma.put(3, "three");

        assertEquals(1, m.size());
       // assertEquals("three", m.get(3));
        
        for(int i = 0; i < 25; i++) {
        	m.put(i, "TEST" + i);
        }
        m.put(11, "test");
        HashMap<Integer, String> g = new HashMap<>(10);
        g.putAll(m);
        ma.putAll(m);
        assertEquals(25, g.size());
        assertEquals(25, ma.size());
    }
    
    /*public void testwhat() {
        CuckooHashMap<String, String> m = new CuckooHashMap<>(10);
        assertEquals(0, m.size());
        assertNull(m.get(3));

        m.put("3", "three");
        assertEquals(1, m.size());
        assertEquals("three", m.get("3"));
        assertEquals("three", m.getEntry(3).getValue());
        

    }*/
    
    public void testRealHash() {
        HashMap<Integer, String> m = new HashMap<>(10);
        Hashtable<Integer, String> t = new Hashtable<>(10);
        CuckooHashMap<Integer, String> c = new CuckooHashMap<>(10);
        assertEquals(0, m.size());
        assertNull(m.get(3));

        for(int i = 0; i < 10; i++) {
        	m.put(i, "TEST" + i);
        	c.put(i, "TEST" + i);
        	t.put(i, "TEST" + i);
        }
        assertEquals(10, m.size());
        assertEquals(10, c.size());
        
        m.put(1,  "BREAK!");
        c.put(1,  "BREAK!");
        t.put(1, "BREAK!");
        assertEquals(10, t.size());
        assertEquals(10, m.size());
        assertEquals(10, c.size());
        
        m.put(3, "BURN!!!!");
        assertEquals(10, m.size());
        
        m.put(7, "DIE!!!!!!!!");
        assertEquals(10, m.size());
        
    }
    
    public void testCollis() {
        CuckooHashMap<Integer, String> m = new CuckooHashMap<>(10);
        assertEquals(0, m.size());
        assertNull(m.get(3));

        for(int i = 0; i < 10; i++) {
        	m.put(i, "TEST" + i);
        }
        assertEquals(10, m.size());
        
        m.put(1,  "BREAK!");
        assertEquals(10, m.size());
        
        m.put(3, "BURN!!!!");
        assertEquals(10, m.size());
        
        m.put(7, "DIE!!!!!!!!");
        assertEquals(10, m.size());
        
    }
    
    public void testEntrySet() {
        CuckooHashMap<Integer, String> m = new CuckooHashMap<>(10);
    	HashMap<Integer, String> am = new HashMap<>(10);
        assertEquals(0, m.size());
        assertNull(m.get(3));
        
        am.put(1, "t");
        m.put(1, "t");
        
        am.put(2, "a");
        m.put(2, "a");
        
        assertEquals(m.entrySet(), am.entrySet());
    }
    
    public void testPutAllEmptyToEmpty() {
        CuckooHashMap<Integer, String> m = new CuckooHashMap<>(10);
    	HashMap<Integer, String> am = new HashMap<>(10);
        m.putAll(am);
        assertEquals(m.size(), 0);
        
        for(int i = 0; i < 10; i++) {
        	m.put(i, "TEST" + i);
        }
        assertEquals(10, m.size());
        m.putAll(am);
        assertEquals(10, m.size());
    }
    
    public void testPutAll1() {
    	/* CuckooHashMap<Integer, String> m = new CuckooHashMap<>(10);
        CuckooHashMap a = m; // make sure that after clear this is still the same object
    	HashMap<Integer, String> am = new HashMap<>(10);
        m.putAll(am);
        assertEquals(m.size(), 0);
        
        for(int i = 0; i < 50; i++) {
        	am.put(i, "TEST" + i);
        }
       /* assertEquals(50, am.size());
        assertEquals(0, m.size());
        m.putAll(am);
        assertEquals(50, m.size());
        m.putAll(m);
        assertEquals(50, m.size());*/
    	
    	CuckooHashMap<Integer, String> m = new CuckooHashMap<>(10);
        HashMap<Integer, String> am = new HashMap<>(20);
        for(int i = 0; i < 20; i++) {
        	am.put(i*2, "TEST" + i);
        }
        m.clear();
        //assertEquals(m, a);
        m.putAll(am);
        assertEquals(20, m.size());
    }
    
    public void testPutAllEmpty() {
        CuckooHashMap<Integer, String> m = new CuckooHashMap<>(10);
        CuckooHashMap<Integer, String> empty = new CuckooHashMap<>(10);
        assertEquals(0, m.size());
        assertNull(m.get(3));

        for(int i = 0; i < 10; i++) {
        	m.put(i, "TEST" + i);
        }
        assertEquals(10, m.size());
        
        m.put(1,  "BREAK!");
        assertEquals(10, m.size());
        
        m.put(3, "BURN!!!!");
        assertEquals(10, m.size());
        
        m.put(7, "DIE!!!!!!!!");
        assertEquals(10, m.size());
        
        empty.putAll(m);
        assertEquals(10, empty.size());
        
        m.clear();
        empty = new CuckooHashMap<>(10);
        assertEquals(m.toString(), empty.toString());
        
    }
}
