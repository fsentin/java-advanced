package hr.fer.zemris.java.fractals;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ReadingUtilsTest {
	@Test
	public void parseComplexTest() {
		String line = "1";
		String line1 = "-1";
		String line2 = "i";
		String line3 = "-i";
		String line4 = " -1 + i0";
		String line5 = "0 - i1";
		String line6 = "-7.3 - i4.2";
		
		assertEquals("(1.0+i0.0)",ReadingUtils.parseComplex(line).toString());
		assertEquals("(-1.0+i0.0)",ReadingUtils.parseComplex(line1).toString());
		assertEquals("(0.0+i1.0)",ReadingUtils.parseComplex(line2).toString());
		assertEquals("(0.0-i1.0)",ReadingUtils.parseComplex(line3).toString());
		assertEquals("(-1.0+i0.0)",ReadingUtils.parseComplex(line4).toString());
		assertEquals("(0.0-i1.0)",ReadingUtils.parseComplex(line5).toString());
		assertEquals("(-7.3-i4.2)",ReadingUtils.parseComplex(line6).toString());
	}
}
