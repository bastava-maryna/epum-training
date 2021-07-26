package by.epum.training.oop.dao.exception;

public class DAOException extends Exception {
	
	private static final long serialVersionUID = 7061244164907373877L;

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
