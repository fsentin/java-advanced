package hr.fer.oprpp1.hw01;

import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

import static java.lang.Math.PI;

/**
 * A model of an unmodifiable complex number. The precision is 1E-10, except multiplication.
 * @author Fani
 *
 */
public class ComplexNumber {
	/**
	 * Real part of a complex number.
	 */
	private final double real;
	
	/**
	 * Real part of a complex number.
	 */
	private final double imaginary;
	
	/**
	 * Creates a new ComplexNumber from given real and imaginary parts.
	 * @param real the real part of a complex number
	 * @param imaginary the imaginary part of a complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		
		if(equal(real, 0.0))
			this.real = 0.0;
		else 
			this.real = real;
		
		if(equal(imaginary, 0.0))
			this.imaginary = 0.0;
		else
			this.imaginary = imaginary;
	}
	
	/**
	 * Creates a new pure real complex number.
	 * 
	 * @param real the real part of the complex number
	 * @return a new instance of ComplexNumber whose imaginary part is zero.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0.0);
	}
	
	/**
	 * Creates a new pure imaginary complex number.
	 * 
	 * @param imaginary the imaginary part of the complex number
	 * @return a new instance of ComplexNumber whose real part is zero.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0.0, imaginary);
	}
	
	/**
	 * Creates a new complex number using the polar notation.
	 * @param magnitude the magnitude of the complex number in polar notation
	 * @param angle the angle of a complex number made with the real axis
	 * @return a new instance of ComplexNumber.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
	}
	
	/**
	 * Creates a new complex number using formatted String.
	 * @param s the formatted string to be parsed
	 * @return a new instance of ComplexNumber made using the specified String.
	 * @throws IllegalArgumentException if the String is given in wrong format
	 */
	public static ComplexNumber parse(String s) {
		// nothing to parse
		if(s.isEmpty()) 
			return new ComplexNumber(0.0, 0.0);
		
		// completely wrong format
		if(s.contains("+-") || s.contains("-+") || s.contains(","))
			throw new IllegalArgumentException("Complex number format can't contain \",\" \"+-\" nor \"-+\"!");
		
		// pure real number
		if(!s.contains("i")) 
			return new ComplexNumber(Double.parseDouble(s), 0.0);
		
		// if i comes before magnitude
		if(!s.endsWith("i"))
			throw new IllegalArgumentException("Magnitude of the imaginary part must come before i!");
		
		
		double realPart, imaginaryPart;
		try {
			
			String[] arr = s.split("");
			String parse = "";
			int i = arr.length - 1;	//i now has index of "i"
			while(i > 0 && !arr[i].equals("+") && !arr[i].equals("-")) { 
				i--;
			}
			if(arr.length == 1 || i == 0 && arr.length == 2 && arr[i].equals("+")) { //it was i 
				return new ComplexNumber(0.0, 1);
			} else if(arr[i + 1].equals("i")) {		// it was +i or -i
				if(arr[i].equals("+"))
					imaginaryPart = 1;
				else 
					imaginaryPart = -1;
			} else  {	
				for(int j = i; j < arr.length - 1; j++) 
					parse += arr[j];
				imaginaryPart = Double.parseDouble(parse);
			}
			parse = "";
			int k = i - 1;
			if(k < 0)
				return new ComplexNumber(0.0, imaginaryPart);
			
			while(k > 0 && !arr[k].equals("+") && !arr[k].equals("+")) { 
				k--;
			}
			
			for(int j = 0; j < i; j++) 
				parse += arr[j];
			realPart = Double.parseDouble(parse);
			return new ComplexNumber(realPart, imaginaryPart);
		
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Your string is not in the correct format!");
		}
	}
	
	
	/**
	 * Returns the real part of the complex number.
	 * 
	 * @return real part of the complex number.
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Returns the imaginary part of the complex number.
	 * 
	 * @return imaginary part of the complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Returns the magnitude of a complex number (used in polar notation).
	 * 
	 * @return the magnitude of a complex number.
	 */
	public double getMagnitude() {
		return sqrt(real * real + imaginary * imaginary);
	}
	
	/**
	 * Returns the angle of a complex number made with the real axis (used in polar notation).
	 * 
	 * @return the angle made with the real axis.
	 */
	public double getAngle() {
		
		// special cases
		if(equal(real, 0.0)) {
			if(equal(imaginary, 0.0))
				return 0.0d;
			else if(imaginary > 0.0)
				return PI/2;
			else if(imaginary < 0.0)
				return 3 * PI/2;
		}
		
		// determine the quadrant
		int quadrant = 0;
		if(real > 0.0 && imaginary > 0.0)
			quadrant = 1;
		else if(real < 0.0 && imaginary > 0.0)
			quadrant = 2;
		else if (real < 0.0 && imaginary < 0.0)
			quadrant = 3;
		else if (real > 0.0 && imaginary < 0.0)
			quadrant = 4;
		
		// calculate as if it is in the first quadrant
		double angle  = 0.0;
		
		// translate into the right quadrant
		switch(quadrant) {
		case 1:
			angle = atan(abs(imaginary / real));
			break;
		case 2: 
			angle = PI - atan(abs(imaginary / real));
			break;
		case 3: 
			angle = PI + atan(abs(imaginary / real));
			break;	
		case 4:
			angle = 2*PI - atan(abs(imaginary / real));
			break;
		default:
			break;
		}
		
		return angle;
	}
	
	/**
	 * Creates a new complex number by adding the given complex number to the current one.
	 * 
	 * @param c given complex number, the addend
	 * @return sum of the given number and the current one.
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.getReal(), imaginary + c.getImaginary());
	}
	
	/**
	 * Creates a new complex number by subtracting the given complex number from the current one.
	 * 
	 * @param c given complex number, the subtrahend
	 * @return difference of subtraction of the given complex number from the current one.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real - c.getReal(), imaginary - c.getImaginary());
	}
	
	/**
	 * Creates a new complex number by multiplying the current one with the given complex number.
	 * 
	 * @param c given complex number, the multiplier
	 * @return product of multiplication of this complex number with the given number.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double x = real;
		double y = imaginary;
		double z = c.getReal();
		double w = c.getImaginary();
		
		return  new ComplexNumber(x*z - y*w,  x*w + z*w);
	}
	
	/**
	 * Creates a new complex number by dividing the current one with the given complex number.
	 * 
	 * @param c given complex number, the divisor
	 * @return quotient of division of this complex number with the given number.
	 * @throws IllegalArgumentException if division by zero is performed
	 */
	public ComplexNumber div(ComplexNumber c) {
		
		if(equal(c.getReal(), 0.0) && equal(c.getImaginary(), 0.0))
			throw new IllegalArgumentException("Division by zero is illegal!");
		
		double x = real;
		double y = imaginary;
		double z = c.getReal();
		double w = c.getImaginary();
		return new ComplexNumber((x*z + y*w)/(z*z + w*w), (y*z - x*w)/(z*z + w*w));
	}
	
	/**
	 * Calculates the multiplication of a complex number by itself. 
	 * @param n number of times to use the number in a multiplication
	 * @return the multiplication of a complex number by itself using that number n times.
	 * @throws IllegalArgumentException if the power is less than zero
	 */
	public ComplexNumber power(int n) {
		if(n < 0)
			throw new IllegalArgumentException("Power can't be " + n + ", it should be positive!");
		
		return fromMagnitudeAndAngle(pow(getMagnitude(), 1.0 * n), getAngle() * 1.0 *n);
	}
	
	/**
	 * Returns an array of complex numbers that are the result of a root of complex number.
	 * @param n the power with which the roots need to be raised to get this complex number
	 * @return an array of numbers which, when raised to the power n yield this complex number.
	 * @throws IllegalArgumentException if the power is less or equal to zero
	 */
	public ComplexNumber[] root(int n){
		if(n <= 0)
			throw new IllegalArgumentException("n can't be " + n + ", it should be 0 or less!");
		
		ComplexNumber[] result = new ComplexNumber[n]; 
		double magnitude = pow(getMagnitude(), 1.0/n); 
		double angle;
		
		for(int k = 0; k < n; k++) {
			angle = getAngle()/(1.0 * n) + (2 * k * PI)/(1.0*n);
			result[k] = fromMagnitudeAndAngle(magnitude*1.0, angle);
		}
		
		return result;
		
	}
	
	/**
	 * Returns the complex number as a String.
	 * @return a string representation of the object.
	 */
	@Override
	public String toString() {
		
		if(equal(real, 0.0) && !equal(imaginary, 0.0))
			return Double.toString(imaginary) + "i";
		
		else if(!equal(real, 0.0) && equal(imaginary, 0.0))
			return Double.toString(real);

		String middle = (imaginary >= 0.0) ? "+": "";
		
		return Double.toString(real) + middle + Double.toString(imaginary) + "i";
	}
	
	/**
	 * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by java.util.HashMap. 
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one. 
	 * @return <code>true</code> if this object is the same as the obj argument; <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof ComplexNumber))
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		
		return equal(this.real, other.real) && equal(this.imaginary, other.imaginary);
	}
	
	/**
	 * Determines if two doubles are equal according to 1E-10 precision.
	 * @param d1 first double to be compared
	 * @param d2 second double to be compared
	 * @return <code>true</code> if the doubles are same; <code>false</code> otherwise.
	 */
	private static boolean equal(double d1, double d2) {
		return (abs(d1 - d2) < 1E-10);
	}
	
}


