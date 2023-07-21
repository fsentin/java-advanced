package hr.fer.oprpp1.custom.collections;

/**
 * A model of an object capable of performing some operation on the passed object.
 * 
 * @param <T> type of the objects processed by the Processor
 * @author Fani
 *
 */

public interface Processor<T> {
	
	/**
	 * Performs an operation on the specified object.
	 * <p>
	 * *Note: Inside the class <code>Processor</code> this method is empty.
	 * Child classes have to implement the correct functionality.
	 * </p>
	 * 
	 * @param value object upon which the operation is performed
	 */
	public void process(T value);
	
	

}
