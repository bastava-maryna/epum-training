package by.epum.training.db.dao;

import java.time.LocalDate;
import java.util.List;

import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Train;

public interface TrainDAO extends DAO<Train, Integer> {
	List<Train> readAll() throws DAOException;
	Train findByAll(Train train) throws DAOException;
	List<Train> findByStationsAndDate(Integer departureId, Integer destinationId, LocalDate departureDate) throws DAOException;	
}
