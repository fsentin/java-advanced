package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	

	@Test
	public void constuctorWithNoParams() {
		Collection<Integer> exam = new LinkedListIndexedCollection<>();
		assertEquals(0, exam.size());
	}
	
	@Test
	public void constuctorWithOtherCollection() {
		Collection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		Collection<Integer> exam2 = new LinkedListIndexedCollection<>(exam);
		assertArrayEquals(exam.toArray(), exam2.toArray());
	}
	
	@Test
	public void sizeFunctionClassic() {
		Collection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		assertEquals(2, exam.size());
	}
	
	@Test
	public void sizeFunctionEmpty() {
		Collection<Integer> exam = new LinkedListIndexedCollection<>();
		assertEquals(0, exam.size());
	}
	
	@Test
	public void addFunctionShouldThrowNullPointerEx() {
		assertThrows(NullPointerException.class, 
				() ->  {Collection<Integer> exam = new LinkedListIndexedCollection<>();
						exam.add(null); });
	}
	
	@Test
	public void addFunctionClassic() {
		Collection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		exam.add(3);
		exam.add(4);
		exam.add(5);
		exam.add(4);
		exam.add(4);
		exam.add(4);
		exam.add(4);
		exam.add(4);
		assertEquals(10, exam.size());
	}
	
	@Test
	public void containsFunctionShouldReturnFalseForNull() {
		Collection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		assertFalse(exam.contains(null));
	}
	
	@Test
	public void containsFunctionShouldReturnTrue() {
		Collection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		assertTrue(exam.contains(1));
	}
	
	@Test
	public void containsFunctionShouldReturnFalse() {
		Collection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		assertFalse(exam.contains(5));
	}
	
	@Test
	public void removeOverridenFalseForNull() {
		Collection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		assertFalse(exam.remove(null));
	}
	
	@Test
	public void removeOverridenFunctionShouldReturnTrue() {
		Collection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		assertTrue(exam.remove(1));
	}
	
	@Test
	public void removeOverridenFunctionShouldReturnFalse() {
		Collection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		assertFalse(exam.remove(5));
	}
	
	@Test
	public void toArrayFunction() {
		Collection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		Object[] expected = {1, 2};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void toArrayFunctionEmptyCollection() {
		Collection<Integer> exam = new LinkedListIndexedCollection<>();
		Object[] expected = {};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void forEachFunction() {
		//TODO check this
		Collection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		Collection<Integer> added = new LinkedListIndexedCollection<>();
		added.add(3);
		added.add(4);
		exam.addAll(added);
		Integer[] expected = {1, 2, 3, 4};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void clearFunction() {
		Collection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		exam.add(3);
		exam.add(4);
		exam.add(5);
		exam.add(4);
		exam.clear();
		assertEquals(0, exam.size());
		Collection<Integer> expected = new LinkedListIndexedCollection<>();
		assertArrayEquals(expected.toArray(), exam.toArray());
	}
	
	@Test
	public void getFunctionShouldThrowIndexOutOfBoundsExc() {
		LinkedListIndexedCollection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		assertThrows(IndexOutOfBoundsException.class, () ->  exam.get(3) );
	}
	
	@Test
	public void getFunctionShouldThrowIndexOutOfBoundsExcSecond() {
		LinkedListIndexedCollection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		assertThrows(IndexOutOfBoundsException.class, () ->  exam.get(-1) );
	}
	
	@Test
	public void getFunction() {
		LinkedListIndexedCollection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		assertEquals(1, exam.get(0));
	}
	
	@Test
	public void insertFunctionShouldThrowNullPointerExc() {
		LinkedListIndexedCollection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		assertThrows(NullPointerException.class, () ->  exam.insert(null, 1) );
	}
	
	@Test
	public void insertFunctionShouldThrowIndexOutOfBoundsExc() {
		LinkedListIndexedCollection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		assertThrows(IndexOutOfBoundsException.class, () ->  exam.insert(-1, 3) );
	}
	
	@Test
	public void insertFunctionAtTheEnd() {
		LinkedListIndexedCollection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		exam.add(2);
		exam.insert(1, 3);
		Object[] expected = {1, 2, 2, 1};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void insertFunctionAtTheBeginning() {
		LinkedListIndexedCollection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		exam.add(2);
		exam.insert(5, 0);
		Object[] expected = {5, 1, 2, 2};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void insertFunctionInTheMiddle() {
		LinkedListIndexedCollection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		exam.add(2);
		exam.insert(5, 1);
		Object[] expected = {1, 5, 2, 2};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void indexOfFunctionNull() {
		LinkedListIndexedCollection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		assertEquals(-1, exam.indexOf(null));
	}
	
	@Test
	public void indexOfFunctionWithExistentElement() {
		LinkedListIndexedCollection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		assertEquals(0, exam.indexOf(1));
	}
	
	@Test
	public void indexOfFunctionWithNonExistentElement() {
		LinkedListIndexedCollection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		assertEquals(-1, exam.indexOf(5));
	}
	
	@Test
	public void removeFunctionShouldThrowIndexOutOfBoundsException() {
		LinkedListIndexedCollection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		assertThrows(IndexOutOfBoundsException.class, () ->  exam.remove(3) );
	}
	
	@Test
	public void removeFunction() {
		LinkedListIndexedCollection<Integer> exam = new LinkedListIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		exam.add(1);
		exam.add(2);
		exam.remove(1);
		Object[] expected = {1, 1, 2};
		assertArrayEquals(expected, exam.toArray());
	}
	public void testGeneric() {
		ArrayIndexedCollection<Number> exam = new ArrayIndexedCollection<>(3);
		exam.add(666);
		exam.add(90);
		exam.add(13);
		exam.add(1.03);
		ArrayIndexedCollection<Integer> exam2 = new ArrayIndexedCollection<>(3);
		exam2.add(1);
		exam2.add(2);
		exam2.add(1);
		exam.addAll(exam2);
		assertTrue(exam.contains(1));
	}
	
	@Test
	public void testGeneric2() {
		ArrayIndexedCollection<Double> exam = new ArrayIndexedCollection<>(3);
		exam.add(666.0);
		exam.add(90.0);
		exam.add(13.0);
		exam.add(1.03);
		ArrayIndexedCollection<Number> exam2 = new ArrayIndexedCollection<>(exam);
		exam2.add(3);
		assertTrue(exam2.contains(1.03));
	}

}
