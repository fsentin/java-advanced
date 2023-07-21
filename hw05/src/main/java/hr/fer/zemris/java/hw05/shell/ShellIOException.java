package hr.fer.zemris.java.hw05.shell;
/**
 * Special Exception thrown when an error occurs while reading or writing with the shell's environment.
 * @author Fani
 *
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = -4546821855818939876L;

	public ShellIOException() {
		super();
	}

	public ShellIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShellIOException(String message) {
		super(message);
	}

	public ShellIOException(Throwable cause) {
		super(cause);
	}
	
	
}
