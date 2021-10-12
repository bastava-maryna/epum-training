package by.epum.training.db.service.exception;

public class StationNameNotUniqueException extends ServiceException {

	
	private static final long serialVersionUID = -1572749107834694745L;
	private String stationName;

    public StationNameNotUniqueException(String stationName) {
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }
    
  

}
