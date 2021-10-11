package by.epum.training.db.dao;

import java.util.List;

import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Route;


public interface RouteDAO extends DAO<Route, Integer> {
	List<Route> readAll() throws DAOException;
	List<Integer> findByParametrs(Integer departureId, Integer destinationId) throws DAOException;
	
}
