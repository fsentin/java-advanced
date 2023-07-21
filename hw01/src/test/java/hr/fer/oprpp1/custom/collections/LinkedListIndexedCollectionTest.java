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
		Collection exam = new LinkedListIndexedCollection();
		assertEquals(0, exam.size());
	}
	
	@Test
	public void constuctorWithOtherCollection() {
		Collection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		Collection exam2 = new LinkedListIndexedCollection(exam);
		assertArrayEquals(exam.toArray(), exam2.toArray());
	}
	
	@Test
	public void sizeFunctionClassic() {
		Collection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		assertEquals(2, exam.size());
	}
	
	@Test
	public void sizeFunctionEmpty() {
		Collection exam = new  LinkedListIndexedCollection();
		assertEquals(0, exam.size());
	}
	
	@Test
	public void addFunctionShouldThrowNullPointerEx() {
		assertThrows(NullPointerException.class, 
				() ->  {Collection exam = new LinkedListIndexedCollection();
						exam.add(null); });
	}
	
	@Test
	public void addFunctionClassic() {
		Collection exam = new LinkedListIndexedCollection();
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
		Collection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		assertFalse(exam.contains(null));
	}
	
	@Test
	public void containsFunctionShouldReturnTrue() {
		Collection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		assertTrue(exam.contains(1));
	}
	
	@Test
	public void containsFunctionShouldReturnFalse() {
		Collection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		assertFalse(exam.contains(5));
	}
	
	@Test
	public void removeOverridenFalseForNull() {
		Collection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		assertFalse(exam.remove(null));
	}
	
	@Test
	public void removeOverridenFunctionShouldReturnTrue() {
		Collection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		assertTrue(exam.remove(1));
	}
	
	@Test
	public void removeOverridenFunctionShouldReturnFalse() {
		Collection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		assertFalse(exam.remove(5));
	}
	
	@Test
	public void toArrayFunction() {
		Collection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		Object[] expected = {1, 2};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void toArrayFunctionEmptyCollection() {
		Collection exam = new LinkedListIndexedCollection();
		Object[] expected = {};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void forEachFunction() {
		//TODO check this
		Collection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		Collection added = new LinkedListIndexedCollection();
		added.add(3);
		added.add(4);
		exam.addAll(added);
		Object[] expected = {1, 2, 3, 4};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void clearFunction() {
		Collection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		exam.add(3);
		exam.add(4);
		exam.add(5);
		exam.add(4);
		exam.clear();
		assertEquals(0, exam.size());
		Collection expected = new LinkedListIndexedCollection();
		assertArrayEquals(expected.toArray(), exam.toArray());
	}
	
	@Test
	public void getFunctionShouldThrowIndexOutOfBoundsExc() {
		LinkedListIndexedCollection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		assertThrows(IndexOutOfBoundsException.class, () ->  exam.get(3) );
	}
	
	@Test
	public void getFunctionShouldThrowIndexOutOfBoundsExcSecond() {
		LinkedListIndexedCollection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		assertThrows(IndexOutOfBoundsException.class, () ->  exam.get(-1) );
	}
	
	@Test
	public void getFunction() {
		LinkedListIndexedCollection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		assertEquals(1, exam.get(0));
	}
	
	@Test
	public void insertFunctionShouldThrowNullPointerExc() {
		LinkedListIndexedCollection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		assertThrows(NullPointerException.class, () ->  exam.insert(null, 1) );
	}
	
	@Test
	public void insertFunctionShouldThrowIndexOutOfBoundsExc() {
		LinkedListIndexedCollection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		assertThrows(IndexOutOfBoundsException.class, () ->  exam.insert(-1, 3) );
	}
	
	@Test
	public void insertFunctionAtTheEnd() {
		LinkedListIndexedCollection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		exam.add(2);
		exam.insert(1, 3);
		Object[] expected = {1, 2, 2, 1};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void insertFunctionAtTheBeginning() {
		LinkedListIndexedCollection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		exam.add(2);
		exam.insert(5, 0);
		Object[] expected = {5, 1, 2, 2};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void insertFunctionInTheMiddle() {
		LinkedListIndexedCollection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		exam.add(2);
		exam.insert(5, 1);
		Object[] expected = {1, 5, 2, 2};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void indexOfFunctionNull() {
		LinkedListIndexedCollection exam = new LinkedListIndexedCollection();
		exam.add(1);
		assertEquals(-1, exam.indexOf(null));
	}
	
	@Test
	public void indexOfFunctionWithExistentElement() {
		LinkedListIndexedCollection exam = new LinkedListIndexedCollection();
		exam.add(1);
		assertEquals(0, exam.indexOf(1));
	}
	
	@Test
	public void indexOfFunctionWithNonExistentElement() {
		LinkedListIndexedCollection exam = new LinkedListIndexedCollection();
		exam.add(1);
		assertEquals(-1, exam.indexOf(5));
	}
	
	@Test
	public void removeFunctionShouldThrowIndexOutOfBoundsException() {
		LinkedListIndexedCollection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		assertThrows(IndexOutOfBoundsException.class, () ->  exam.remove(3) );
	}
	
	@Test
	public void removeFunction() {
		LinkedListIndexedCollection exam = new LinkedListIndexedCollection();
		exam.add(1);
		exam.add(2);
		exam.add(1);
		exam.add(2);
		exam.remove(1);
		Object[] expected = {1, 1, 2};
		assertArrayEquals(expected, exam.toArray());
	}
	

}
