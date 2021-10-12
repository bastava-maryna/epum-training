package by.epum.training.db.service.exception;

public class UserNotUniqueException extends ServiceException {

	
	private static final long serialVersionUID = -1572749107834694745L;
	//private String stationName;
	private Long id;
    public UserNotUniqueException(Long id) {
		this.id = id;
	}
    

public String getId() {
	return String.valueOf(id);
}
    
  

}
