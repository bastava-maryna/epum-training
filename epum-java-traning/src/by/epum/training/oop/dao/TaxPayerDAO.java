package by.epum.training.oop.dao;

import java.util.List;

import by.epum.training.oop.dao.exception.DAOException;
import by.epum.training.oop.entity.TaxPayer;

public interface TaxPayerDAO {
	TaxPayer getById(Long id) throws DAOException;
	List<TaxPayer> findByLastName(String lastName) throws DAOException;
	void saveToStorage(TaxPayer taxPayer);
}
