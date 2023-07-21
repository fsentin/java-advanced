package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class ArrayIndexedCollectionTest {
	
	@Test
	public void constructorWithInitalCapacityThrowsIllegalArgumentEx() {
		assertThrows(IllegalArgumentException.class, () ->  new ArrayIndexedCollection<>(0) );
	}
	
	@Test
	public void constructorWithOtherCollectionThrowsNullPointerEx() {
		assertThrows(NullPointerException.class, () ->  new ArrayIndexedCollection<>(null) );
	}
	
	@Test
	public void constuctorWithOtherCollection() {
		Collection<Integer> exam = new ArrayIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		Collection<Integer> exam2 = new ArrayIndexedCollection<>(exam);
		assertArrayEquals(exam.toArray(), exam2.toArray());
	}
	
	@Test
	public void sizeFunctionClassic() {
		Collection<Integer> exam = new  ArrayIndexedCollection<>();
		exam.add(1);
		exam.add(2);
		assertEquals(2, exam.size());
	}
	
	@Test
	public void sizeFunctionEmpty() {
		Collection<String> exam = new  ArrayIndexedCollection<>();
		assertEquals(0, exam.size());
	}
	
	@Test
	public void addFunctionShouldThrowNullPointerEx() {
		assertThrows(NullPointerException.class, 
				() ->  {Collection<String> exam = new ArrayIndexedCollection<>(3);
						exam.add(null); });
	}
	
	@Test
	public void addFunctionShouldResizeTheInternalArray() {
		Collection<Integer> exam = new ArrayIndexedCollection<>(3);
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
		Collection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		assertFalse(exam.contains(null));
	}
	
	@Test
	public void containsFunctionShouldReturnTrue() {
		Collection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		assertTrue(exam.contains(1));
	}
	
	@Test
	public void containsFunctionShouldReturnFalse() {
		Collection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		assertFalse(exam.contains(5));
	}
	
	@Test
	public void removeOverridenFalseForNull() {
		Collection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		assertFalse(exam.remove(null));
	}
	
	@Test
	public void removeOverridenFunctionShouldReturnTrue() {
		Collection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		assertTrue(exam.remove(1));
	}
	
	@Test
	public void removeOverridenFunctionShouldReturnFalse() {
		Collection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		assertFalse(exam.remove(5));
	}
	
	@Test
	public void toArrayFunction() {
		Collection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		Object[] expected = {1, 2};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void toArrayFunctionEmptyCollection() {
		Collection<Integer> exam = new ArrayIndexedCollection<>(1);
		Integer[] expected = {};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void forEachFunction() {
		Collection<Integer> exam = new ArrayIndexedCollection<>(2);
		exam.add(1);
		exam.add(2);
		Collection<Integer> added = new ArrayIndexedCollection<>(2);
		added.add(3);
		added.add(4);
		exam.addAll(added);
		Object[] expected = {1, 2, 3, 4};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void clearFunction() {
		Collection<Integer> exam = new ArrayIndexedCollection<>(1);
		exam.add(1);
		exam.add(2);
		exam.add(3);
		exam.add(4);
		exam.add(5);
		exam.add(4);
		exam.clear();
		assertEquals(0, exam.size());
		Collection<Integer> expected = new ArrayIndexedCollection<>();
		assertArrayEquals(expected.toArray(), exam.toArray());
	}
	
	@Test
	public void getFunctionShouldThrowIndexOutOfBoundsExc() {
		ArrayIndexedCollection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		assertThrows(IndexOutOfBoundsException.class, () ->  exam.get(3) );
	}
	
	@Test
	public void getFunctionShouldThrowIndexOutOfBoundsExcSecond() {
		ArrayIndexedCollection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		assertThrows(IndexOutOfBoundsException.class, () ->  exam.get(-1) );
	}
	
	@Test
	public void getFunction() {
		ArrayIndexedCollection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		assertEquals(1, exam.get(0));
	}
	
	@Test
	public void insertFunctionShouldThrowNullPointerExc() {
		ArrayIndexedCollection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		assertThrows(NullPointerException.class, () ->  exam.insert(null, 1) );
	}
	
	@Test
	public void insertFunctionShouldThrowIndexOutOfBoundsExc() {
		ArrayIndexedCollection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		assertThrows(IndexOutOfBoundsException.class, () ->  exam.insert(-1, 3) );
	}
	
	@Test
	public void insertFunctionAtTheEnd() {
		ArrayIndexedCollection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		exam.add(2);
		exam.insert(1, 3);
		Object[] expected = {1, 2, 2, 1};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void insertFunctionAtTheBeginning() {
		ArrayIndexedCollection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		exam.add(2);
		exam.insert(5, 0);
		Object[] expected = {5, 1, 2, 2};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void insertFunctionInTheMiddle() {
		ArrayIndexedCollection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		exam.add(2);
		exam.insert(5, 1);
		Object[] expected = {1, 5, 2, 2};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void indexOfFunctionNull() {
		ArrayIndexedCollection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		assertEquals(-1, exam.indexOf(null));
	}
	
	@Test
	public void indexOfFunctionWithExistentElement() {
		ArrayIndexedCollection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		assertEquals(0, exam.indexOf(1));
	}
	
	@Test
	public void indexOfFunctionWithNonExistentElement() {
		ArrayIndexedCollection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		assertEquals(-1, exam.indexOf(5));
	}
	
	@Test
	public void removeFunctionShouldThrowIndexOutOfBoundsException() {
		ArrayIndexedCollection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		assertThrows(IndexOutOfBoundsException.class, () ->  exam.remove(3) );
	}
	
	@Test
	public void removeFunction() {
		ArrayIndexedCollection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		exam.add(1);
		exam.add(2);
		exam.remove(1);
		Object[] expected = {1, 1, 2};
		assertArrayEquals(expected, exam.toArray());
	}
	@Test
	public void removeFunctionEndingIndex() {
		ArrayIndexedCollection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		exam.add(1);
		exam.add(2);
		exam.remove(3);
		Object[] expected = {1, 2, 1};
		assertArrayEquals(expected, exam.toArray());
	}
	
	@Test
	public void testGeneric() {
		ArrayIndexedCollection<Integer> exam = new ArrayIndexedCollection<>(3);
		exam.add(1);
		exam.add(2);
		exam.add(1);
		String s = "sneaky string";
		assertFalse(exam.contains(s));
	}
	
	@Test
	public void testGeneric2() {
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
	public void testGeneric3() {
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
