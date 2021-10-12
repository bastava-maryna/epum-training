package by.epum.training.db.service.exception;

public class TrainNotExistException extends ServiceException {

	private static final long serialVersionUID = 5789462719615782027L;

	private Integer id;

	public TrainNotExistException(Integer id) {
		this.id = id;
	}

	public String getId() {
		return String.valueOf(id);
	}



}
