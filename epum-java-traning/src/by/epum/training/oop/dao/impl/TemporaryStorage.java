package by.epum.training.oop.dao.impl;


import java.util.List;

import by.epum.training.oop.entity.Income;
import by.epum.training.oop.entity.TaxPayer;

public interface TemporaryStorage {
	TaxPayer getTaxPayer();
	void saveTaxPayer(TaxPayer taxPayer);
	List<Income> getIncomes();
	void saveIncomes(List<Income> incomes);
	int getYear();
	void setYear(int year);
	
}
