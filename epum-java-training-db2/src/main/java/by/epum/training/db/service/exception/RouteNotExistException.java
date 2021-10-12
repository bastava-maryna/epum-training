package by.epum.training.db.service.exception;

public class RouteNotExistException extends ServiceException {

	private static final long serialVersionUID = 5789462719615782027L;

	public RouteNotExistException(String message) {
        super(message);
    }

    public RouteNotExistException(Throwable cause) {
        super(cause);
    }

    public RouteNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
	public RouteNotExistException() {}



}
