package hr.fer.zemris.math;
/**
 * Polynom in complex number notation representing <code>f(z) = z0*(z-z1)*(z-z2)*...*(z-zn)</code>
 * where <code>z</code> and all <code>zi</code> (i = 0, 1, 2...) represent complex numbers.
 * @author Fani
 *
 */
public class ComplexRootedPolynomial {
	
	/**
	 * The z0 in this notation, specifically: <code>f(z) = z0*(z-z1)*(z-z2)*...*(z-zn)</code>
	 */
	private Complex constant;
	
	/**
	 * The roots z1, z2,..., zn in this notation, specifically: <code>f(z) = z0*(z-z1)*(z-z2)*...*(z-zn)</code>
	 */
	private Complex[] roots;
	
	/**
	 * Creates a new polynomial in root based notation. <code>f(z) = z0*(z-z1)*(z-z2)*...*(z-zn)</code>
	 * @param constant z0 in this notation
	 * @param roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = roots;
	}
	
	/**
	 * Computes polynomial value at given point z.
	 * @param z given point
	 * @return polynomial value at given point z.
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		result = result.add(constant);
		
		for (int i = 0; i < roots.length; i++) {
			result = result.multiply(roots[i].negate().add(z));
		}

		return result;
	}
	
	/**
	 * Converts this representation to ComplexPolynomial type
	 * @return representation of ComplexRootedPolynomial in ComplexPolynomial type.
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial cp = new ComplexPolynomial(constant);
		for (int i = 0; i < roots.length; i++) {
			cp = cp.multiply(new ComplexPolynomial(roots[i].negate(), Complex.ONE));
		}
		return cp;
	}
	
	/**
	 * Finds index of closest root for given complex number z that is within
	 * treshold; if there is no such root, returns -1.
	 * first root has index 0, second index 1, etc
	 * @param z given complex number
	 * @param treshold
	 * @return first index of closest root for given complex number z that is within treshold, -1 if there is no such root.
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		double minDistance = Double.MAX_VALUE;
		int index = -1;
		
		double current = 0;
		for (int i = 0; i < roots.length; i++) {
			current = z.sub(roots[i]).module();
			if(current < treshold && current < minDistance) {
				index = i;
				minDistance = current;
			}
		}
		return index;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(constant.toString()); //z0
		
		for (int i = 0; i < roots.length; i++) {
			sb.append("*");
			sb.append("(z-" + roots[i].toString() + ")"); //z1..zn
		}
		
		return sb.toString();	
	}
}
