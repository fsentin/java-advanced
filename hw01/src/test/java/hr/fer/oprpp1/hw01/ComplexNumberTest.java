package hr.fer.oprpp1.hw01;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ComplexNumberTest {
	
	@Test
	public void constuctor() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		assertEquals("2.0+3.0i", c1.toString());
	}
	
	@Test
	public void fromReal(){
		ComplexNumber c1 = ComplexNumber.fromReal(2);
		assertEquals("2.0", c1.toString());
	}
	
	@Test
	public void fromImaginary(){
		ComplexNumber c1 = ComplexNumber.fromImaginary(3);
		assertEquals("3.0i", c1.toString());
	}
	@Test
	public void fromMagnitudeAndAngle() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(1.35, 1.89);
		assertEquals("-0.42364425513449105+1.2818055800672512i", c1.toString());
		ComplexNumber c2 = ComplexNumber.fromMagnitudeAndAngle(12, Math.PI/2);
		assertTrue(equal(0.00000000000000073, c2.getReal()));
		assertTrue(equal(12, c2.getImaginary()));
	}
	
	
	@Test
	public void parseShouldThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-+2.71"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("+-2.71"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("i2.71"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("2,71"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("2,71lbsbnaiei"));
	}
	
	//"+2.71", "+2.71+3.15i", "+i"
	@Test
	public void parsePureRealNums() {
		ComplexNumber c1 = ComplexNumber.parse("351");
		assertEquals("351.0", c1.toString());
		c1 = ComplexNumber.parse("-317");
		assertEquals("-317.0", c1.toString());
		c1 = ComplexNumber.parse("3.51");
		assertEquals("3.51", c1.toString());
		c1 = ComplexNumber.parse("-3.17");
		assertEquals("-3.17", c1.toString());
		c1 = ComplexNumber.parse("+2.71");
		assertEquals("2.71", c1.toString());
	}
	@Test
	public void parsePureImaginaryNums() {
		ComplexNumber c1 = ComplexNumber.parse("351i");
		assertEquals("351.0i", c1.toString());
		c1 = ComplexNumber.parse("-317i");
		assertEquals("-317.0i", c1.toString());
		c1 = ComplexNumber.parse("3.51i");
		assertEquals("3.51i", c1.toString());
		c1 = ComplexNumber.parse("-3.17i");
		assertEquals("-3.17i", c1.toString());
		c1 = ComplexNumber.parse("+i");
		assertEquals("1.0i", c1.toString());
	}
	 
	@Test
	public void parseMixedNums() {
		ComplexNumber c1 = ComplexNumber.parse("-2.71-3.15i");
		assertEquals("-2.71-3.15i", c1.toString());
		c1 = ComplexNumber.parse("31+24i");
		assertEquals("31.0+24.0i", c1.toString());
		c1 = ComplexNumber.parse("-1-i");
		assertEquals("-1.0-1.0i", c1.toString());
		c1 = ComplexNumber.parse("+2.71+3.15i");
		assertEquals("2.71+3.15i", c1.toString());
	}
	
	@Test
	public void getRealAndGetImaginary() {
		ComplexNumber c1 = ComplexNumber.parse("-2.71-3.15i");
		assertEquals(-2.71, c1.getReal());
		assertEquals(-3.15, c1.getImaginary());
	}
	
	@Test
	public void getAngleFirstQuadrant() {
		ComplexNumber c1 = ComplexNumber.parse("2.71+3.15i");
		//System.out.println(c1.getAngle());
		assertTrue(equal(0.860342857535976953, c1.getAngle()));
	}
	
	@Test
	public void getAngleSecondQuadrant() {
		ComplexNumber c1 = ComplexNumber.parse("-2+3i");
		ComplexNumber c2 = ComplexNumber.fromMagnitudeAndAngle(3.60555127546398912, 2.15879893034246439);
		assertTrue(equal(c1.getAngle(), c2.getAngle()));
	}
	
	@Test
	public void getAngleThirdQuadrant() {
		ComplexNumber c1 = ComplexNumber.parse("-2.71-3.15i");
		assertTrue(equal(4.00193551112576973, c1.getAngle()));
	}
	
	@Test
	public void getAngleFourthQuadrant() {
		ComplexNumber c1 = ComplexNumber.parse("+2.71-3.15i");
		assertTrue(equal(5.42284244964360962, c1.getAngle()));
	}
	
	@Test
	public void getAngle0() {
		ComplexNumber c1 = ComplexNumber.parse("");
		assertTrue(equal(0.0, c1.getAngle()));
	}
	
	@Test
	public void getMagnitude() {
		ComplexNumber c1 = ComplexNumber.parse("+2.71-3.15i");
		assertTrue(equal(4.15530985607571779, c1.getMagnitude()));
	}
	
	@Test
	public void add() {
		ComplexNumber c1 = ComplexNumber.parse("+2.71-3.15i");
		ComplexNumber c2 = ComplexNumber.parse("5.42-6.3i");
		c1 = c1.add(c1);
		assertTrue(c2.equals(c1));
	}
	
	@Test
	public void sub() {
		ComplexNumber c1 = ComplexNumber.parse("+2.71-3.15i");
		ComplexNumber c2 = ComplexNumber.parse("");
		c1 = c1.sub(c1);
		assertTrue(c2.equals(c1));
	}
	
	@Test
	public void mul() {
		ComplexNumber c1 = ComplexNumber.parse("+2.71-3.15i");
		ComplexNumber c2 = ComplexNumber.parse("-2.5784-17.073i");
		c1 = c1.mul(c1);
		assertTrue(equal(c1.getReal(), c2.getReal()) && equal(c1.getImaginary(), c2.getImaginary()));
	}
	
	@Test
	public void div() {
		ComplexNumber c1 = ComplexNumber.parse("+2.71-3.15i");
		ComplexNumber c2 = ComplexNumber.parse("1");
		c1 = c1.div(c1);
		assertTrue(equal(c1.getReal(), c2.getReal()) && equal(c1.getImaginary(), c2.getImaginary()));
	}
	
	@Test
	public void divShouldThrowIllegalArgumentException() {
		ComplexNumber c1 = ComplexNumber.parse("+2.71-3.15i");
		ComplexNumber c2 = ComplexNumber.parse("");
		assertThrows(IllegalArgumentException.class, () -> c1.div(c2));
	}
	
	@Test
	public void power1() {
		ComplexNumber c1 = ComplexNumber.parse("+2.71-3.15i");
		ComplexNumber c2 = ComplexNumber.parse("+2.71-3.15i");
		c1 = c1.power(1);
		assertTrue(equal(c1.getReal(), c2.getReal()) && equal(c1.getImaginary(), c2.getImaginary()));
	}
	
	/*
	 *  couldn't find a calculator precise enough to check these :(
	 */
	@Test
	public void power2() {
		ComplexNumber c1 = ComplexNumber.parse("+2.71-3.15i");
		ComplexNumber c2 = c1.power(2);
		c1 = c1.mul(c1);
		//System.out.println(c2);
		//System.out.println(c1);
		assertTrue(equal(c1.getReal(), c2.getReal()) && equal(c1.getImaginary(), c2.getImaginary()));
	}
	
	
	
	@Test
	public void root() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(8, 2 * Math.PI / 3);
		ComplexNumber expected =  ComplexNumber.fromMagnitudeAndAngle(2, 2 * Math.PI / 9);
		ComplexNumber[] res = c1.root(3);
		
		assertTrue(equal(res[0].getReal(), expected.getReal()) && equal(res[0].getImaginary(), expected.getImaginary()));
		
	}
	
	/**
	 * Determines if two doubles are equal according to 1E-10 precision.
	 * @param d1 first double to be compared
	 * @param d2 second double to be compared
	 * @return <code>true</code> if the doubles are same; <code>false</code> otherwise.
	 */
	public static boolean equal(double d1, double d2) {
		return (abs(d1 - d2) < 1E-10);
	}
	
	

}
