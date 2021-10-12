package by.epum.training.db.service.exception;

public class BillDublicateEntryException extends ServiceException {

	private static final long serialVersionUID = 5789462719615782027L;


	public BillDublicateEntryException() {
		super();
	}
	
	public BillDublicateEntryException(String message) {
		super(message);
	}
	
	public BillDublicateEntryException(Exception e) {
		super(e.getCause());
	}
	
	public BillDublicateEntryException(String message,Exception e) {
		super(message,e);
	}


}
