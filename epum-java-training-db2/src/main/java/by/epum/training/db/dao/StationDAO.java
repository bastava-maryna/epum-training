package by.epum.training.db.dao;

import java.util.List;

import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Station;

public interface StationDAO extends DAO<Station,Integer>{
	List<Station> readAll() throws DAOException;
	Station readByName(String stationName) throws DAOException;
}
