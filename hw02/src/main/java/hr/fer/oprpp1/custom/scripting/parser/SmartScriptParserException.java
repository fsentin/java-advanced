package hr.fer.oprpp1.custom.scripting.parser;
/**
 * Thrown when something goes wrong during parsing.
 * @author Fani
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = -7298940776722804518L;

	public SmartScriptParserException() {
		super();
	}

	public SmartScriptParserException(String message) {
		super(message);
	}

	public SmartScriptParserException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}

}
