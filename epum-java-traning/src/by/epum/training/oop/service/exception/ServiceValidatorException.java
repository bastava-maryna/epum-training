package by.epum.training.oop.service.exception;

public class ServiceValidatorException extends ServiceException{

	private static final long serialVersionUID = -4186912551081191517L;
	
	public ServiceValidatorException() {
		super();
	}
		
	public ServiceValidatorException(String message) {
		super(message);
	}
		
	public ServiceValidatorException(Exception e) {//wrapping
		super(e);
	}
		
	public ServiceValidatorException(String message,Exception e) {//wrapping
		super(message,e);
	}

}
