package by.epum.training.db.service;

import java.util.List;


import by.epum.training.db.entity.Station;
import by.epum.training.db.service.exception.ServiceException;


public interface StationService {
	List<Station> findAll() throws ServiceException;
	Station findById(Integer id) throws ServiceException;
	Station findByName(String stationName) throws ServiceException;

	void delete(Integer id) throws ServiceException;
	void save(Station station) throws ServiceException;//create or update
}