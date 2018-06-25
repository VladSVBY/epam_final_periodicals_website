package by.epam.periodicials_site.dao.pool;

public class ConnectionPoolException extends RuntimeException{

	private static final long serialVersionUID = 341167489014415017L;

	public ConnectionPoolException() {
		super();
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
