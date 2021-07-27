package by.epum.training.oop.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.epum.training.oop.dao.DAOProvider;
import by.epum.training.oop.dao.IncomeDAO;
import by.epum.training.oop.dao.exception.DAOException;
import by.epum.training.oop.entity.Income;
import by.epum.training.oop.entity.IncomeType;
import by.epum.training.oop.service.IncomeService;
import by.epum.training.oop.service.exception.ServiceException;


public class IncomeServiceImpl implements IncomeService {
	
	DAOProvider provider=DAOProvider.getInstance();
	IncomeDAO incomeDAO=provider.getIncomeDAO();
	
	@Override
	public List<Income> getIncomes(Long taxPayerId, String year) throws ServiceException {
		List<Income> result=null;
		
		try {
			result=incomeDAO.getIncomes(taxPayerId, year);
		} catch (DAOException e) {
			//throw new ServiceException(e.getCause());//which is better or use this to log?
			//throw new ServiceException(e.getMessage());
			throw new ServiceException(e);
		}
		
		return result;
	}
	

	@Override
	public List<Income> getMonthIncomes(Long taxPayerId, String year, int monthNumber) throws ServiceException {
		List<Income> yearResult=null;
		
		yearResult=getIncomes(taxPayerId,year);   //here can be exc from getIncomes
		
		if(yearResult==null || yearResult.isEmpty()) {
			return null;
		}
		
		List<Income> monthResult=new ArrayList<Income>();
		
		for(Income in:yearResult) {
			if(in.getDate().getMonthValue()==monthNumber) {
				monthResult.add(in);
				
			}
		}
		
		return monthResult;
	}

		
	@Override
	public Map<Integer, Map<IncomeType, List<Income>>> groupByMonth(List<Income> incomes) {
		Map<Integer,Map<IncomeType,List<Income>>> result=new HashMap<Integer,Map<IncomeType,List<Income>>>();
		
		if(incomes.isEmpty()) {
			return result;//also retrn empty map
		}
		
		for(Income in:incomes) {
			Integer key=in.getDate().getMonthValue();
			IncomeType type=in.getIncomeType();
			if(!result.containsKey(key)) {
				result.put(key, new HashMap<IncomeType,List<Income>>());
			}	
				
			if(result.get(key).containsKey(type)) {
				result.get(key).get(type).add(in);
			}else {
				List<Income> list=new ArrayList<Income>();
				list.add(in);
				result.get(key).put(type, list);
			}
			
		}
		
		return result;	
	}
}
