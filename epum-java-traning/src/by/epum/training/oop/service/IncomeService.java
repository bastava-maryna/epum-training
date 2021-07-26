package by.epum.training.oop.service;

import java.util.List;
import java.util.Map;

import by.epum.training.oop.entity.Income;
import by.epum.training.oop.entity.IncomeType;
import by.epum.training.oop.service.exception.ServiceException;

public interface IncomeService {
	List<Income> getIncomes(Long taxPayerId, String year) throws ServiceException;
	Map<Integer,Map<IncomeType,List<Income>>> groupByMonth(List<Income> incomes);  
	List<Income> getMonthIncomes(Long taxPayerId, String year,int monthNumber) throws ServiceException;
	
}
