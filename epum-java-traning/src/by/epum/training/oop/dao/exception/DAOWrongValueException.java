package by.epum.training.oop.dao.exception;

public class DAOWrongValueException extends DAOException {
	
	private static final long serialVersionUID = 8426044253263366810L;

	public DAOWrongValueException() {
		super();
	}
	
	public DAOWrongValueException(String message) {
		super(message);
	}
	
	public DAOWrongValueException(Exception e) {//wrapping
		super(e);
	}
	
	public DAOWrongValueException(String message,Exception e) {//wrapping
		super(message,e);
	}
}
