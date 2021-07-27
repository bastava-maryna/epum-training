package by.epum.training.oop.dao.exception;

public class DAOMissingValueException extends DAOException {
	
	private static final long serialVersionUID = 4730509028657560850L;

	public DAOMissingValueException() {
		super();
	}
	
	public DAOMissingValueException(String message) {
		super(message);
	}
	
	public DAOMissingValueException(Exception e) {//wrapping
		super(e);
	}
	
	public DAOMissingValueException(String message,Exception e) {//wrapping
		super(message,e);
	}
}
