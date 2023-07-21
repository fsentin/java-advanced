package hr.fer.oprpp1.custom.collections;

/**
 * A model of an object capable of performing some operation on the passed object.
 * @author Fani
 *
 */
public interface Processor {
	
	/**
	 * Performs an operation on the specified object.
	 * 
	 * <p>
	 * Child classes have to implement the correct functionality.
	 * </p>
	 * 
	 * @param value object upon which the operation is performed
	 */
	public void process(Object value);
	
	

}
