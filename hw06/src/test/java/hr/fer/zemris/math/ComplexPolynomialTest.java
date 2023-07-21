package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ComplexPolynomialTest {
	
	@Test
	public void multiplyTest() {
		ComplexPolynomial cmp1 = new ComplexPolynomial(
				new Complex[] {
						new Complex(1, 0), new Complex(1, 0)
						});
		ComplexPolynomial cmp2 = new ComplexPolynomial(
				new Complex[] {
						new Complex(1, 0), new Complex(1, 0)
						});
		ComplexPolynomial res = new ComplexPolynomial(
				new Complex[] {
						new Complex(1, 0), new Complex(2, 0), new Complex(1,0)
						});
		
		System.out.println(cmp1.multiply(cmp2).toString());
		assertEquals(res.toString(), cmp1.multiply(cmp2).toString());
	}
	
	@Test
	public void orderTest() {
		ComplexPolynomial cmp1 = new ComplexPolynomial(
				new Complex[] {
						new Complex(43, -25), new Complex(3, -5), 
						new Complex(-86, 67), new Complex(1, -11),
						new Complex(5, 8), new Complex(-1, 33)
						});
		
		assertEquals(5, cmp1.order());
	}
	
	@Test
	public void deriveTest() {
		ComplexPolynomial cmp1 = new ComplexPolynomial(
				new Complex[] {
						new Complex(1, 0), new Complex(1, 0), 
						new Complex(1, 0),  new Complex(1, 0),
						new Complex(1, 0),  new Complex(1, 0)
						});
		
		ComplexPolynomial res = new ComplexPolynomial(
				new Complex[] {
						new Complex(1, 0), new Complex(2, 0),
						new Complex(3, 0), new Complex(4, 0),  
						new Complex(5, 0)
						});
		
		assertEquals(res.toString(), cmp1.derive().toString());
	}
	
	@Test
	public void apply() {
		ComplexPolynomial cmp1 = new ComplexPolynomial(
				new Complex[] {
						new Complex(1, 0), new Complex(7, 3), 
						new Complex(2, -1)
						});
		
		assertEquals(new Complex(22, 18).toString(), cmp1.apply(new Complex(2,1)).toString());
	}
	
}
