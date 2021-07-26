package by.epum.training.oop.service.exception;

public class ServiceException extends Exception{
	private static final long serialVersionUID = -1569473699634736961L;
	
	public ServiceException() {
		super();
	}
		
	public ServiceException(String message) {
		super(message);
	}
		
	public ServiceException(Exception e) {//wrapping
		super(e);
	}
		
	public ServiceException(String message,Exception e) {//wrapping
		super(message,e);
	}
}

