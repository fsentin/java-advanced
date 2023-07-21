package hr.fer.oprpp1.hw04.db;

/**
 * Thrown when a database is given in a wrong format.
 * @author Fani
 *
 */
public class IllegalDatabaseFormatException extends RuntimeException {

	private static final long serialVersionUID = 8032436079423404961L;

	public IllegalDatabaseFormatException() {
		super();
	}

	public IllegalDatabaseFormatException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalDatabaseFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalDatabaseFormatException(String message) {
		super(message);
	}

	public IllegalDatabaseFormatException(Throwable cause) {
		super(cause);
	}

	
}
