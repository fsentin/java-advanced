package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;


public class ComplexTest {
	
	@Test
	public void negateTest() {
		Complex complex = new Complex(3,3);
		Complex exp = new Complex(-3, -3);
		
		assertEquals(exp.toString(), complex.negate().toString());
	}
	
	@Test
	public void addTest() {
		Complex num = new Complex(10, 2);
		Complex res = num.add(new Complex(1, -1));
		
		Complex exp = new Complex(11, 1);
		assertEquals(exp.toString(), res.toString());
	}
	
	@Test
	public void subTest() {
		Complex num = new Complex(23, 45);
		Complex res = num.sub(new Complex(-3, 45));
		
		Complex exp = new Complex(26, 0);
		assertEquals(exp.toString(), res.toString());
	}
	
	@Test
	public void multiplyTest() {
		Complex num = new Complex(2, 2);
		Complex res = num.multiply(new Complex(4, 4));
		Complex exp = new Complex(0, 16);
		assertEquals(exp.toString(), res.toString());
	}
	
	@Test
	public void divideTest() {
		Complex num = new Complex(2, 2);
		Complex res = num.divide(new Complex(4, 4));
		Complex exp = new Complex(0.5, 0);
		assertEquals(exp.toString(), res.toString());
	}
	
}
