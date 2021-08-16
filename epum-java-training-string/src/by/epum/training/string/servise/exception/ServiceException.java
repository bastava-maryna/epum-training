package by.epum.training.string.servise.exception;

public class ServiceException extends Exception{
	
	private static final long serialVersionUID = 7236019212410638146L;

	public ServiceException() {
		super();
	}
		
	public ServiceException(String message) {
		super(message);
	}
		
	public ServiceException(Exception e) {
		super(e);
	}
		
	public ServiceException(String message,Exception e) {
		super(message,e);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
}
