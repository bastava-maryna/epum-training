package by.epum.training.string.dao.exception;

public class DAOException extends Exception {
	
	private static final long serialVersionUID = 3642563971610163631L;

	public DAOException() {
		super();
	}
	
	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(Exception e) {//wrapping
		super(e);
	}
	
	public DAOException(String message,Exception e) {//wrapping
		super(message,e);
	}
}
