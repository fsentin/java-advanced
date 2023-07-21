package hr.fer.oprpp1.custom.collections;

/**
 * An interface that tests an object.
 * @author Fani
 *
 */
public interface Tester {
	/**
	 * Test the specified object.
	 * @param obj the tested object
	 * @return <code>true</code> if the object passes the test, </code> otherwise.
	 */
	boolean test(Object obj);
}
