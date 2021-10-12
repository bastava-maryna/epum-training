package by.epum.training.db.service.exception;

public class RouteNotUniqueException extends ServiceException {

	
	private static final long serialVersionUID = -1572749107834694745L;

	private Integer id;

	public RouteNotUniqueException(Integer id) {
		this.id = id;
	}

	public String getId() {
		return String.valueOf(id);
	}

}
