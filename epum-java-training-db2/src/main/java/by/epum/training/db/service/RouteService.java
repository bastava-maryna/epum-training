package by.epum.training.db.service;



import by.epum.training.db.entity.Route;
import by.epum.training.db.entity.Station;
import by.epum.training.db.service.exception.ServiceException;

import java.util.List;

public interface RouteService {
	Route findById(Integer id) throws ServiceException;
    List<Route> findAll() throws ServiceException;
    Route findByStations(Station departure, Station destination) throws ServiceException;
    
    void save(Route route) throws ServiceException;//create or update
}
