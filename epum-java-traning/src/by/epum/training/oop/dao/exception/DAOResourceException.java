package by.epum.training.oop.dao.exception;

public class DAOResourceException extends DAOException {
	
	private static final long serialVersionUID = -1500930358868781129L;

	public DAOResourceException() {
		super();
	}
	
	public DAOResourceException(String message) {
		super(message);
	}
	
	public DAOResourceException(Exception e) {//wrapping
		super(e);
	}
	
	public DAOResourceException(String message,Exception e) {//wrapping
		super(message,e);
	}
}
