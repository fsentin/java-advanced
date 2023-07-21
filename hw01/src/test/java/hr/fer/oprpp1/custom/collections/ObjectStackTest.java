package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ObjectStackTest {
	
	@Test
	public void isEmptyFunctionShouldReturnTrue() {
		ObjectStack s = new ObjectStack();
		assertTrue(s.isEmpty());
	}
	
	@Test
	public void isEmptyFunctionShouldReturnFalse() {
		ObjectStack s = new ObjectStack();
		s.push(1);
		assertFalse(s.isEmpty());
	}
	
	@Test
	public void sizeFunction() {
		ObjectStack s = new ObjectStack();
		for(int i = 0; i < 5; i++)
			s.push(1);
		assertEquals(5, s.size());
	}
	
	@Test
	public void sizeFunctionEmptyStack() {
		ObjectStack s = new ObjectStack();
		assertEquals(0, s.size());
	}
	
	@Test
	public void pushFunction() {
		ObjectStack s = new ObjectStack();
		for(int i = 0; i < 100; i++)
			s.push(1);
		assertEquals(100, s.size());
	}
	
	@Test
	public void popFunction() {
		ObjectStack s = new ObjectStack();
		s.push(1);
		assertEquals(1, s.pop());
	}
	
	@Test
	public void popShouldThrowEmptyStackException() {
		ObjectStack s = new ObjectStack();
		assertThrows(EmptyStackException.class, () -> s.pop());
	}
	
	@Test
	public void peekFunction() {
		ObjectStack s = new ObjectStack();
		s.push(1);
		assertEquals(1, s.peek());
	}
	
	@Test
	public void peekShouldThrowEmptyStackException() {
		ObjectStack s = new ObjectStack();
		assertThrows(EmptyStackException.class, () -> s.peek());
	}
	
	@Test
	public void clearFunction() {
		ObjectStack s = new ObjectStack();
		for(int i = 0; i < 5; i++)
			s.push(1);
		s.clear();
		assertEquals(0, s.size());
	}
	
}
