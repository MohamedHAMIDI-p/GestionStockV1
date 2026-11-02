package exception;

public class InvalidEntityException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private final ErreurCodes errorCode;
	
	public InvalidEntityException(String message, ErreurCodes errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ErreurCodes getErrorCode() {
		return errorCode;
	}

}
