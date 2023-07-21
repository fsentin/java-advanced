package hr.fer.zemris.math;

/**
 * Polynom in complex number notation representing <code>f(z) = (zn)*z^n+(zn-1)*z^(n-1)+...+(z2)*z^2+(z1)*z+(z0)</code>
 * where <code>z</code> and all <code>zi</code> (i = 0, 1, 2...) represent complex numbers.
 * @author Fani
 *
 */
public class ComplexPolynomial {
	
	/**
	 * The factors zn, zn-1,..., z1, z0 of this notation, 
	 * specifically <code>f(z) = (zn)*z^n+(zn-1)*z^(n-1)+...+(z2)*z^2+(z1)*z+(z0)</code>
	 */
	private Complex[] factors;
	
	/**
	 * Creates a new polynomial in order based notation. <code>f(z) = (zn)*z^n+(zn-1)*z^(n-1)+...+(z2)*z^2+(z1)*z+(z0)</code>
	 * @param factors coefficients in the order based notation
	 */
	public ComplexPolynomial(Complex ...factors) {
		this.factors = factors;
	}
	
	/**
	 * Returns factors of this polynomial from order based notation.
	 * @return factors of this polynomial from order based notation.
	 */
	public Complex[] getFactors() {
		return factors;
	}
	
	/**
	 * Returns the order of this polynom.
	 * @return the order of this polynom.
	 */
	public short order() {
		return (short) (factors.length <= 1 ? 0 : factors.length - 1);
	}
	
	/**
	 * Computes a new polynomial that is the result of this polynomial multiplied by a given other polynomial.
	 * @param p given other polynomial
	 * @return result of this polynomial multiplied by a given other polynomial.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] resultFactors = new Complex[this.order() + p.order() + 1];
		for (int i = 0; i < resultFactors.length; i++) {
			resultFactors[i] = Complex.ZERO;
		}
		
		for (int i = 0; i < this.order() + 1; i++) {
			for (int j = 0; j < p.order() + 1; j++) {
				resultFactors[i + j] = resultFactors[i + j].add(this.factors[i].multiply(p.getFactors()[j]));
			}
		}
		return new ComplexPolynomial(resultFactors);
	}
	
	/**
	 * Computes first derivative of this polynomial.
	 * @return first derivative of this polynomial.
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[this.order()];
		for (int i = 1; i < factors.length; i++) {
			newFactors[i - 1] = factors[i].multiply(new Complex(i, 0)); 
		}
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * Computes polynomial value at given point z.
	 * @param z given point
	 * @return polynomial value at given point z.
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		
		for (int i = 0; i < factors.length; i++) {
			result = result.add(factors[i].multiply(z.power(i)));
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//int j = 0;
		for(int i = factors.length - 1; i >= 0; i--) {
			sb.append(factors[i].toString()); //z0..zn
			if(i > 0) sb.append("*z^" + i);
			if(i != 0) sb.append("+");
		}
		
		return sb.toString();
	}

}
