package by.epum.training.db.dao.exception;

public class DAOBillDublicateEntryException extends DAOException {

	private static final long serialVersionUID = 5789462719615782027L;


	public DAOBillDublicateEntryException() {
		super();
	}
	
	public DAOBillDublicateEntryException(String message) {
		super(message);
	}
	
	public DAOBillDublicateEntryException(Exception e) {
		super(e);
	}
	
	public DAOBillDublicateEntryException(String message,Exception e) {
		super(message,e);
	}
	
	public DAOBillDublicateEntryException(Throwable cause) {
		super(cause);
	}

}
