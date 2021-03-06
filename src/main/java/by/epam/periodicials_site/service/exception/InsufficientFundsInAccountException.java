package by.epam.periodicials_site.service.exception;

public class InsufficientFundsInAccountException extends ServiceException {
	
    private static final long serialVersionUID = -7863971022455229516L;

    public InsufficientFundsInAccountException() {
    }

    public InsufficientFundsInAccountException(String message) {
        super(message);
    }

    public InsufficientFundsInAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientFundsInAccountException(Throwable cause) {
        super(cause);
    }
}
