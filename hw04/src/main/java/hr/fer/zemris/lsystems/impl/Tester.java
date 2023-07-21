package hr.fer.zemris.lsystems.impl;

/**
 * An interface that tests an object.
 * 
 * @param <T> type of the object tested by Tester
 * @author Fani
 *
 */
public interface Tester<T> {
	/**
	 * Test the specified object.
	 * @param obj the tested object
	 * @return <code>true</code> if the object passes the test, </code> otherwise.
	 */
	boolean test(T obj);
}
