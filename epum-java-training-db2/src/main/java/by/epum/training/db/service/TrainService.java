package by.epum.training.db.service;

import java.time.LocalDate;
import java.util.List;

import by.epum.training.db.entity.Station;
import by.epum.training.db.entity.Train;
import by.epum.training.db.service.exception.ServiceException;



public interface TrainService {
	 List<Train> findAll() throws ServiceException;

	 List<Train> findByStationsAndDate(Station departure, Station destination,LocalDate date) throws ServiceException;
	 Train findById(Integer id) throws ServiceException;

	 void save(Train train) throws ServiceException;//create or update
	 Train getDuplicate(Train train ) throws ServiceException;
	
}
