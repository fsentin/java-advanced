package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DictionaryTest {
	@Test
    public void testPutAndGet(){
        Dictionary<String, Integer> dict = new Dictionary<>();

        dict.put("Branko", 1);
        dict.put("Marica", 2);

        assertEquals(1, dict.get("Branko"));
    }

    @Test
    public void testRemoveAndGet(){
        Dictionary<String, Integer> dict = new Dictionary<>();

        dict.put("Branko", 1);
        dict.put("Marica", 2);

        dict.remove("Branko");
        assertNull(dict.get("Branko"));
    }

    @Test
    public void testSize(){
        Dictionary<String, Integer> dict = new Dictionary<>();

        dict.put("Branko", 1);
        dict.put("Marica", 2);
        dict.put("Ljubica", 3);
        dict.put("Pavica", 4);
        dict.put("Jozo", 5);

        assertEquals(5, dict.size());
    }

    @Test
    public void testEmpty(){
        Dictionary<String, Integer> dict = new Dictionary<>();

        assertTrue(dict.isEmpty());
    }

    @Test
    public void testIsEmptyAfterClear(){
        Dictionary<String, Integer> dict= new Dictionary<>();

        dict.put("Pavica", 1);
        dict.put("Jozo", 9);

        dict.clear();

        assertTrue(dict.isEmpty());
    }

    @Test
    public void testRemoveGetsOldValue(){
        Dictionary<String, Integer> dict = new Dictionary<>();

        dict.put("Pavica", 5);
        dict.put("Jozo", 9);

        assertEquals(5, dict.remove("Pavica"));
    }

    @Test
    public void testPutOverwrites(){
        Dictionary<String, Integer> dict = new Dictionary<>();

        dict.put("Goga", 1);
        dict.put("Pavica", 2);

        dict.put("Goga", 400);

        assertEquals(400, dict.get("Goga"));
    }

    @Test
    public void testRemove(){
        Dictionary<String, Integer> dict = new Dictionary<>();

        dict.put("Jozica", 9);
        dict.put("Marigoga", 2);

        dict.remove("Marigoga");

        assertEquals(1, dict.size());
    }
}
