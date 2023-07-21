package hr.fer.oprpp1.hw02.prob1;

/**
 * <code>LexerException</code> is thrown when an error occurs while getting the next token in a lexer.
 * @author Fani
 *
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 7081522377449085575L;

	/**
	 * Constructs a new <code>LexerException</code> with the specified detail message.
	 * @param message the detail message 
	 */
	public LexerException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new <code>LexerException</code> with no detail message.
	 */
	public LexerException() {
		super();
	}

	public LexerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}

	public LexerException(Throwable cause) {
		super(cause);
	}
	
	
	
}
