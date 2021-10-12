package by.epum.training.db.service.exception;

public class UserNotExistException extends ServiceException {

	private static final long serialVersionUID = 5789462719615782027L;

	private long id;

	public UserNotExistException(Long id) {
		this.id = id;
	}

	public String getId() {
		return String.valueOf(id);
	}



}
