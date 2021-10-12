package by.epum.training.db.service.exception;

public class ScheduleModeNotExistException extends ServiceException {

	private static final long serialVersionUID = 5789462719615782027L;

	private Short id;

	public ScheduleModeNotExistException(Short id) {
		this.id = id;
	}

	public String getId() {
		return String.valueOf(id);
	}



}
