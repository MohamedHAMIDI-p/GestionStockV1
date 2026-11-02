package exception;

public class EntityNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID=1L;
	private final ErreurCodes errorCode;

	public EntityNotFoundException(String message,ErreurCodes errorCodes) {
		super(message);
		this.errorCode=errorCodes;
	}
	
	public EntityNotFoundException(String message,Throwable cause,ErreurCodes errorCodes) {
		super(message,cause);
		this.errorCode=errorCodes;
	}
	
	public ErreurCodes getErrorCode() {
		return errorCode;
	}

}
