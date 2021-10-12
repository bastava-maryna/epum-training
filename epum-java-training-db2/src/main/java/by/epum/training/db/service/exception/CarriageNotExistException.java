package by.epum.training.db.service.exception;

public class CarriageNotExistException extends ServiceException {

	private static final long serialVersionUID = 5789462719615782027L;

	private Long id;

	public CarriageNotExistException(Long id) {
		this.id = id;
	}

	public String getId() {
		return String.valueOf(id);
	}



}
