package by.epum.training.oop.dao;

import by.epum.training.oop.dao.exception.DAOException;

public interface TaxDataDAO {
	void getTaxData(String year) throws DAOException;

}
