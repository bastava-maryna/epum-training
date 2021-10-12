package by.epum.training.db.service;

import java.util.List;
import java.util.Map;

import by.epum.training.db.entity.Carriage;
import by.epum.training.db.entity.CarriageType;
import by.epum.training.db.service.exception.ServiceException;

public interface CarriageService {
	Carriage findById(Long id) throws ServiceException;
    List<Carriage> findAll() throws ServiceException;
    List<Carriage> findAllByTrain(Integer trainId) throws ServiceException;
    List<Carriage> findAllByTrainAndCarriageType(Integer trainId, CarriageType carriageType) throws ServiceException;
   
    void save(Carriage carriage) throws ServiceException;//create or update
    void delete(Long id) throws ServiceException;
	List<Carriage> findEmpty(Integer trainId)throws ServiceException;
	List<Short> findEmptyNumbers(Integer trainId)throws ServiceException;
	List<Short> findAvailableToAddNumbers(Integer trainId)throws ServiceException;
	
	 Map<Carriage,List<Short>> getAvailablePlacesInTrain(Integer trainId)throws ServiceException;
}

