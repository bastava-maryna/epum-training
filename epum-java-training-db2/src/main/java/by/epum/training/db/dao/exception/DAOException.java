package by.epum.training.db.dao.exception;

public class DAOException extends Exception{

	private static final long serialVersionUID = -5460827260870923744L;
	
	public DAOException() {
		super();
	}
	
	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(Exception e) {
		super(e);
	}
	
	public DAOException(String message,Exception e) {
		super(message,e);
	}

	

	public DAOException(Throwable cause) {
		super(cause);
	}
	
	

}
