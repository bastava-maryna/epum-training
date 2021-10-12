package by.epum.training.db.service.exception;

public class TransactionException extends ServiceException {

	private static final long serialVersionUID = -5157085286731876123L;
	
	public TransactionException() {
		super();
	}
	
	public TransactionException(String message) {
		super(message);
	}
	
	public TransactionException(Exception e) {
		super(e.getCause());
	}
	
	public TransactionException(String message,Exception e) {
		super(message,e);
	}
}
