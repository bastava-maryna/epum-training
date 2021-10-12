package by.epum.training.db.dao.connection_pool.exception;

public class ConnectionPoolException extends Exception { static final long serialVersionUID = 3750778835961025747L;

	public ConnectionPoolException(String message) {
		super(message);
	}

	public ConnectionPoolException(Throwable cause) {
		super(cause);
	}

}
