package by.epum.training.db.dao;

import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Entity;

public interface DAO <T extends Entity<I>, I> {
	I create (T entity) throws DAOException;
	T read(I id) throws DAOException;
	void update (T entity) throws DAOException;
	void delete(I id) throws DAOException;
	
}
