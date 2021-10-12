package by.epum.training.db.service.exception;

public class TrainNotUniqueException extends ServiceException {

	
	private static final long serialVersionUID = -1572749107834694745L;
	//private String stationName;
	private Integer id;
    public TrainNotUniqueException(Integer id) {
		this.id = id;
	}
    

public String getId() {
	return String.valueOf(id);
}
    
  

}
