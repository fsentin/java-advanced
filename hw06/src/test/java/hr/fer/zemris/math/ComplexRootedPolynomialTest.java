package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ComplexRootedPolynomialTest {

	@Test
	public void toStringTest() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2, 0), Complex.ONE, Complex.ONE_NEG,
				Complex.IM, Complex.IM_NEG);
		ComplexPolynomial cp = crp.toComplexPolynom();
		
		System.out.println(cp.toString());
		System.out.println(cp.derive().toString());
		System.out.println(cp.derive());
		
		assertEquals("(2.0+i0.0)*(z-(1.0+i0.0))*(z-(-1.0+i0.0))*(z-(0.0+i1.0))*(z-(0.0-i1.0))", crp.toString());
		assertEquals("(2.0+i0.0)*z^4+(0.0+i0.0)*z^3+(0.0+i0.0)*z^2+(0.0+i0.0)*z^1+(-2.0+i0.0)", cp.toString());
		assertEquals("(8.0+i0.0)*z^3+(0.0+i0.0)*z^2+(0.0+i0.0)*z^1+(0.0+i0.0)", cp.derive().toString());
		
	}
	
	@Test
	public void applyTest() {
		Complex[] roots = new Complex[] {new Complex(0, 0), new Complex(0, 0)};
		Complex z = new Complex(2, 0);
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, roots);
		
		Complex res = z.multiply(z);
		assertEquals(res.toString(), crp.apply(z).toString());
	}
	
	@Test
	public void indexOfClosestRootForTest() {
		Complex[] roots = new Complex[] {new Complex(1, 0), new Complex(0, -3)};
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, roots);
		
		assertEquals(0, crp.indexOfClosestRootFor(new Complex(2, 0), 3));
		assertEquals(-1, crp.indexOfClosestRootFor(new Complex(1000, 200), 30));
		assertEquals(0, crp.indexOfClosestRootFor(new Complex(0, 0), 5));
	}
	
}
