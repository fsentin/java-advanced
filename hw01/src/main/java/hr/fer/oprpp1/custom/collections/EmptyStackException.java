package hr.fer.oprpp1.custom.collections;

/**
 * <code>EmptyStackException</code> is thrown when methods pop or peek are called on an empty stack.
 * @author Fani
 *
 */
public class EmptyStackException extends RuntimeException {
	
	private static final long serialVersionUID = -6076355322985440959L;
	
	/**
	 * Constructs a new <code>EmptyStackException</code> with the specified detail message.
	 * @param message the detail message 
	 */
	public EmptyStackException(String message) {
        super(message);
    }
	
	/**
	 * Constructs a new <code>EmptyStackException</code> with null as its detail message.
	 */
	public EmptyStackException() {
        this(null);
    }

}
