package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleHashtableTest {
	
    @Test
    public void testHashtable() {
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 2);
        examMarks.put("Ivana", 5); 

        assertEquals(2, examMarks.get("Kristina"));
        assertEquals(5, examMarks.get("Ivana"));
        assertEquals(4, examMarks.size());
    }

    @Test
    public void testContainsKey() {
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 2);
        examMarks.put("Ivana", 5); 

        assertTrue(examMarks.containsKey("Kristina"));
    }

    @Test
    public void testContainsValue() {
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 2);
        examMarks.put("Ivana", 5); 

        assertTrue(examMarks.containsValue(5));
    }

    @Test
    public void testToString() {
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Ivana", 5); 

        assertEquals("[Ante=2, Ivana=5, Jasna=2]", examMarks.toString());
    }
    
    @Test
    public void testRemove() {
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 2);
        examMarks.put("Ivana", 5); 

        examMarks.remove("Kristina");

        assertFalse(examMarks.containsKey("Kristina"));
        assertTrue(examMarks.containsKey("Jasna"));
    }

    @Test
    public void testRemove2() {
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 2);
        examMarks.put("Ivana", 5); 

        examMarks.remove("Ante");

        assertFalse(examMarks.containsKey("Ante"));
        assertTrue(examMarks.containsKey("Ivana"));
        examMarks.remove("Ivana");
        assertFalse(examMarks.containsKey("Ivana"));
        assertTrue(examMarks.containsKey("Kristina"));
        examMarks.remove("Kristina");
        assertFalse(examMarks.containsKey("Kristina"));
        
    }

    @Test
    public void iterator() {
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
        
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5); 
        String s = "";
        for (TableEntry<String, Integer> pair : examMarks) {
            s += String.format("%s => %d\n", pair.getKey(), pair.getValue());
       }
        assertEquals("Ante => 2\nIvana => 5\nJasna => 2\nKristina => 5\n", s);
    }

    @Test
    public void iteratorRemove() {
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 2);
        examMarks.put("Ivana", 5); 
        examMarks.put("Josip", 100);

        Iterator<TableEntry<String, Integer>> it = examMarks.iterator();

        while (it.hasNext()) {
            if(it.next().getKey().equals("Ivana"))
                it.remove();
        }
        assertFalse(examMarks.containsKey("Ivana"));
    }
    
    @Test
    public void iteratorNextThrowsNoSuchElExc() {
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

        examMarks.put("Ivana", 2);
        examMarks.put("Jasna", 2);

        Iterator<TableEntry<String, Integer>> it = examMarks.iterator();
        it.next();
        it.next();

        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    public void iteratorThrowsConcurrentModificationExc() {
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);

        Iterator<TableEntry<String, Integer>> it = examMarks.iterator();

        it.next();
        examMarks.put("Jasna", 2);

        assertThrows(ConcurrentModificationException.class, it::next);
    }

    @Test
    public void iteratorRemoveCalledTwiceThrowsIllegalStateExc() {
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 2);
        examMarks.put("Ivana", 5); 

        var it = examMarks.iterator();
        assertThrows(IllegalStateException.class, () -> {
            					while(it.hasNext()) {
            						if(it.next().getKey().equals("Ivana")) {
            							it.remove();
            							it.remove();
            						}
            					} 
            			});
    }
    
    
    @Test
    public void iteratorFinal() {
    	SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
    	examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 2);
        examMarks.put("Ivana", 5); 
        
    	Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
    	while(iter.hasNext()) {
    		@SuppressWarnings("unused")
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
    		//System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
    		iter.remove();
    	}
    	assertEquals(0, examMarks.size());
    }
    
    @Test
    public void twoIterators() {
    	SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(1000);
    	examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 2);
        examMarks.put("Ivana", 5); 
        
    	Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
    	Iterator<SimpleHashtable.TableEntry<String,Integer>> iter2 = examMarks.iterator();
    	
    	iter.next();
		iter2.next();
		
		iter.remove(); // changes the modification count - second iterator throws exc
		
		//should work fine
		while(iter.hasNext()) {
			iter.next();
			iter.remove();
		}
		
		assertEquals(0, examMarks.size());
		assertThrows(ConcurrentModificationException.class, () -> iter2.next());
    }

}
