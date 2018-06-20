package by.epam.periodicials_site.dao.pool;


public class ConnectionPoolException extends Exception{

	private static final long serialVersionUID = -3172613280076668173L;

	public ConnectionPoolException() {
	}

	public ConnectionPoolException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConnectionPoolException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectionPoolException(String message) {
		super(message);
	}

	public ConnectionPoolException(Throwable cause) {
		super(cause);
	}

}
