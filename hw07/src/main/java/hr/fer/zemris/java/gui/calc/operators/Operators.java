package hr.fer.zemris.java.gui.calc.operators;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
/**
 * A class of final static operator interfaces for unary and binary operators.
 * @author Fani
 *
 */
public class Operators {
	
	/*** UNARY OPERATORS ***/
	
	/**
	 * Represents the operation of multiplicative inverse (reciprocal of a number).
	 */
	public static final DoubleUnaryOperator INVERSE = (num) -> 1.0 / num;
	
	
	
	/**
	 * Represents the operation of logarithm with the base 10.
	 */
	public static final DoubleUnaryOperator LOG = (num) -> Math.log10(num);
	
	/**
	 * Represents the operation of raising the number 10 to the power of.
	 */
	public static final DoubleUnaryOperator TENXP = (num) -> Math.pow(10, num);
	
	
	
	/**
	 * Represents the operation of a natural logarithm (base e).
	 */
	public static final DoubleUnaryOperator LN = (num) -> Math.log(num);
	
	/**
	 * Represents the operation of raising the number e to the power of.
	 */
	public static final DoubleUnaryOperator EXP = (num) -> Math.exp(num);
	
	
	
	/**
	 * Represents the trigonometric operation of sine.
	 */
	public static final DoubleUnaryOperator SIN = (num) -> Math.sin(num);
	
	/**
	 * Represents the trigonometric operation arc of sine.
	 */
	public static final DoubleUnaryOperator ASIN = (num) -> Math.asin(num);
	
	
	
	/**
	 * Represents the trigonometric operation of cosine.
	 */
	public static final DoubleUnaryOperator COS = (num) -> Math.cos(num);
	
	/**
	 * Represents the trigonometric operation arc of cosine.
	 */
	public static final DoubleUnaryOperator ACOS = (num) -> Math.acos(num);
	
	
	
	/**
	 * Represents the trigonometric operation arc of tangent.
	 */
	public static final DoubleUnaryOperator TAN = (num) -> Math.tan(num);
	
	/**
	 * Represents the trigonometric operation arc of tangent.
	 */
	public static final DoubleUnaryOperator ATAN = (num) -> Math.atan(num);
	
	
	
	/**
	 * Represents the trigonometric operation cotangent.
	 */
	public static final DoubleUnaryOperator CTG = (num) -> 1.0 / Math.tan(num);
	
	/**
	 * Represents the trigonometric operation arc of cotangent.
	 */
	public static final DoubleUnaryOperator ACTG = (num) -> Math.PI / 2 - Math.atan(num);
	
	
	/*** BINARY OPERATORS ***/
	
	/**
	 * Represents the operation of addition.
	 */
	public static final DoubleBinaryOperator PLUS = (op1, op2) -> op1 + op2;
	
	/**
	 * Represents the operation of subtraction.
	 */
	public static final DoubleBinaryOperator MINUS = (op1, op2) -> op1 - op2;
	
	/**
	 * Represents the operation of multiplication.
	 */
	public static final DoubleBinaryOperator MUL = (op1, op2) -> op1 * op2;
	
	/**
	 * Represents the operation of division.
	 */
	public static final DoubleBinaryOperator DIV = (op1, op2) -> op1 / op2;
	
	/**
	 * Represents the operation of rasing a number to the power of another number.
	 */
	public static final DoubleBinaryOperator X_POW_N = (x, n) -> Math.pow(x, n);
	
	/**
	 * Represents the operation of taking an nth root of a number.
	 */
	public static final DoubleBinaryOperator NTH_ROOT_OF_X = (x, n) -> Math.pow(x, 1.0/n);
	
}
