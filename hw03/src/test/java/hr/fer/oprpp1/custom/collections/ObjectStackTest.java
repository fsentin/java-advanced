package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ObjectStackTest {
	
	@Test
	public void isEmptyFunctionShouldReturnTrue() {
		ObjectStack<Integer> s = new ObjectStack<>();
		assertTrue(s.isEmpty());
	}
	
	@Test
	public void isEmptyFunctionShouldReturnFalse() {
		ObjectStack<Integer> s = new ObjectStack<>();
		s.push(1);
		assertFalse(s.isEmpty());
	}
	
	@Test
	public void sizeFunction() {
		ObjectStack<Integer> s = new ObjectStack<>();
		for(int i = 0; i < 5; i++)
			s.push(1);
		assertEquals(5, s.size());
	}
	
	@Test
	public void sizeFunctionEmptyStack() {
		ObjectStack<Integer> s = new ObjectStack<>();
		assertEquals(0, s.size());
	}
	
	@Test
	public void pushFunction() {
		ObjectStack<Integer> s = new ObjectStack<>();
		for(int i = 0; i < 100; i++)
			s.push(1);
		assertEquals(100, s.size());
	}
	
	@Test
	public void popFunction() {
		ObjectStack<Integer> s = new ObjectStack<>();
		s.push(1);
		assertEquals(1, s.pop());
	}
	
	@Test
	public void popShouldThrowEmptyStackException() {
		ObjectStack<Integer> s = new ObjectStack<>();
		assertThrows(EmptyStackException.class, () -> s.pop());
	}
	
	@Test
	public void peekFunction() {
		ObjectStack<Integer> s = new ObjectStack<>();
		s.push(1);
		assertEquals(1, s.peek());
	}
	
	@Test
	public void peekShouldThrowEmptyStackException() {
		ObjectStack<Integer> s = new ObjectStack<>();
		assertThrows(EmptyStackException.class, () -> s.peek());
	}
	
	@Test
	public void clearFunction() {
		ObjectStack<Integer> s = new ObjectStack<>();
		for(int i = 0; i < 5; i++)
			s.push(1);
		s.clear();
		assertEquals(0, s.size());
	}
	
}
