package by.epum.training.oop.dao;

import java.util.List;

import by.epum.training.oop.dao.exception.DAOException;
import by.epum.training.oop.entity.Income;


public interface IncomeDAO {
	List<Income> getIncomes(Long taxPayerId, String year) throws DAOException;
	void saveToStorage(List<Income> incomes);
}
