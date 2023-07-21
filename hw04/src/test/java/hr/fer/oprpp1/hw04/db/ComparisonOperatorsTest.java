package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {
	
	@Test
	public void testLESS() {
		var oper = ComparisonOperators.LESS;
		
		assertFalse(oper.satisfied("b", "a"));
		assertTrue(oper.satisfied("A", "B"));
	}
	
	@Test
	public void testLESS_OR_EQUALS() {
		var oper = ComparisonOperators.LESS_OR_EQUALS;
		
		assertFalse(oper.satisfied("b", "a"));
		assertTrue(oper.satisfied("A", "B"));
		assertTrue(oper.satisfied("b", "b"));
	}
	
	@Test
	public void testGREATER() {
		var oper = ComparisonOperators.GREATER;
		
		assertFalse(oper.satisfied("a", "b"));
		assertTrue(oper.satisfied("B", "A"));
	}
	
	@Test
	public void testGREATER_OR_EQUALS() {
		var oper = ComparisonOperators.GREATER_OR_EQUALS;
		
		assertFalse(oper.satisfied("a", "b"));
		assertTrue(oper.satisfied("B", "A"));
		assertTrue(oper.satisfied("b", "b"));
	}
	
	@Test
	public void testEQUALS() {
		var oper = ComparisonOperators.EQUALS;
		
		assertTrue(oper.satisfied("b", "b"));
	}
	
	@Test
	public void testNOT_EQUALS() {
		var oper = ComparisonOperators.NOT_EQUALS;
		
		assertTrue(oper.satisfied("a", "b"));
		assertFalse(oper.satisfied("b", "b"));
	}
	
	@Test
	public void testLIKE() {
		var oper = ComparisonOperators.LIKE;
		
		assertFalse(oper.satisfied("Zagreb", "Aba")); 
		assertFalse(oper.satisfied("AAA", "AA*AA")); 
		assertTrue(oper.satisfied("AAAA", "AA*AA")); 
		assertTrue(oper.satisfied("AAA", "AA*A"));
		assertTrue(oper.satisfied("AAA", "*AA"));
		assertTrue(oper.satisfied("Bosnić", "B*"));
		assertTrue(oper.satisfied("Jakša", "Ja*"));
		assertThrows(IllegalArgumentException.class, () -> oper.satisfied("", "**"));
	}
	
}
