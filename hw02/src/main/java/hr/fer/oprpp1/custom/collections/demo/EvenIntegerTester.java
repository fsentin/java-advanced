package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.Tester;

/**
 * A tester which determines if an object is an even integer.
 * @author Fani
 *
 */
public class EvenIntegerTester implements Tester {
	
	/**
	 * Evaluates if an object is an even integer.
	 * 
	 * @param obj the tested object
	 * @return <code>true</code> if the object is an even integer, <code>false</code> otherwise.
	 */
	public boolean test(Object obj) {
		 if(!(obj instanceof Integer)) return false;
		 Integer i = (Integer)obj;
		 return i % 2 == 0;
	}

}
