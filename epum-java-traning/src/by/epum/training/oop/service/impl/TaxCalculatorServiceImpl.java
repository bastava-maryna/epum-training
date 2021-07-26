package by.epum.training.oop.service.impl;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import by.epum.training.oop.dao.DAOProvider;
import by.epum.training.oop.dao.exception.DAOException;
import by.epum.training.oop.entity.Income;
import by.epum.training.oop.entity.IncomeType;
import by.epum.training.oop.entity.TaxPayer;
import by.epum.training.oop.service.IncomeService;
import by.epum.training.oop.service.ServiceProvider;
import by.epum.training.oop.service.TaxCalculatorService;
import by.epum.training.oop.service.TaxPayerService;
import by.epum.training.oop.service.exception.ServiceException;
import by.epum.training.oop.service.exception.ServiceValidatorException;
import by.epum.training.oop.service.impl.calculators.BaseTaxCalculator;
import by.epum.training.oop.service.util.Validator;

public class TaxCalculatorServiceImpl implements TaxCalculatorService {	
	
	@Override
	public Map<IncomeType,BaseTaxCalculator> calculateAnnualTax(Long taxPayerId, String year) throws ServiceException {		
		String numberMonthInYear="12";
		return calculatePeriodTax(taxPayerId, year,numberMonthInYear);
	}
	
	@Override
	public Map<IncomeType, BaseTaxCalculator> calculatePeriodTax(Long taxPayerId, String year,String monthNumberPeriodEnd) throws ServiceException {
		ServiceProvider serviceProvider=ServiceProvider.getInstance();
		TaxPayerService taxPayerService=serviceProvider.getTaxPayerService();
		IncomeService incomeService=serviceProvider.getIncomeService();
		
		
		TaxPayer taxPayer=taxPayerService.getById(taxPayerId);
		//or
		//TaxPayer taxPayer1=taxPayerDAO.getById(taxPayerId);
		
		//System.out.println(taxPayer);
		if(taxPayer==null) {
			return null;// suppose if null-taxpayer is not found, if empty-taxpayer is founf but have no incomes???
		}
		
		
		List<Map<IncomeType,BaseTaxCalculator>> taxByMonth=new ArrayList<Map<IncomeType,BaseTaxCalculator>> ();
		Map<IncomeType,BaseTaxCalculator> yearResult=new EnumMap<IncomeType,BaseTaxCalculator>(IncomeType.class);
			
		try {
			getTaxData(year);
			if(!Validator.isTaxDataComplete()) {
				throw new ServiceValidatorException("Tax tariff cant be zero. Report to developer");
			};
			
			//get income data for chosen year grouped by month and type
			//0-Map<IncomeType,List<Income>>-january
			//...
			//11-Map<IncomeType,List<Income>>-december
			
			Map<Integer,Map<IncomeType,List<Income>>> annualData;
			
			
			annualData=incomeService.groupByMonth(incomeService.getIncomes(taxPayerId, year));  //here we can have taxpayer with no incomes so need check
			
			if(annualData.isEmpty()) {
				return yearResult;//also empty
			}
			
			for(int i=0;i<Integer.valueOf(monthNumberPeriodEnd);i++) {
				//get each month incomes,calculate tax for it and store in taxByMonth list
				Map<IncomeType,List<Income>> curMonthIncomes=annualData.get(i+1);
				
				if(curMonthIncomes.isEmpty()) {
					taxByMonth.add(null);  
				}else {	
					Map<IncomeType,BaseTaxCalculator> taxForMonth=calculateMonthlyTax(i+1,annualData.get(i+1),taxPayer);
					taxByMonth.add(taxForMonth);
				}				
			}
			//group by type
			yearResult=consolidate(taxByMonth);
		
		} catch (ServiceValidatorException e) {
			throw new ServiceException(e);
		}
				
		return yearResult;
	}
	
	
	private Map<IncomeType,BaseTaxCalculator>  consolidate(List<Map<IncomeType,BaseTaxCalculator>>taxByMonth) {	
		Map<IncomeType,BaseTaxCalculator> yearResult=new EnumMap<IncomeType,BaseTaxCalculator>(IncomeType.class);
		
		//get the calculations for each month and add to yearResult map grouping by type  
		for(int i=0; i<taxByMonth.size();i++) {
			Map<IncomeType,BaseTaxCalculator> cur=taxByMonth.get(i);
			if(cur!=null) {		
				for(Map.Entry<IncomeType,BaseTaxCalculator> elem: cur.entrySet()) {
					if(yearResult.containsKey(elem.getKey())) {
						yearResult.get(elem.getKey()).update(elem.getValue().getIncome(), elem.getValue().getDeduction(), elem.getValue().getTax());
					}else {
						yearResult.put(elem.getKey(), elem.getValue());
					}
				}
			}
		}
		
		return yearResult;
	}


	private Map<IncomeType,BaseTaxCalculator> calculateMonthlyTax(int month, Map<IncomeType,List<Income>> monthData, TaxPayer taxPayer) throws ServiceException {
		
		Map<IncomeType,BaseTaxCalculator> monthResult=new EnumMap<IncomeType,BaseTaxCalculator>(IncomeType.class);
	
		final CalculatorManager manager=new CalculatorManager();
		
		if(month==1) {
			manager.clear();
		}
		//for each income type calculate tax using predefined algorithm
		for(Map.Entry<IncomeType, List<Income>> entry : monthData.entrySet()) {
			BaseTaxCalculator calculator=manager.getCalculator(entry.getKey());
			
			BaseTaxCalculator temp=calculator.calculate(month,entry.getValue(),taxPayer);
			if(temp!=null) {
				monthResult.put(entry.getKey(), temp);
			}	
		}
		
		return monthResult;
	}
	
	
		//?? maybe need to return string with ok?
	@Override
	public void getTaxData(String year) throws ServiceException {
		try {
			DAOProvider providerDAO=DAOProvider.getInstance();
			providerDAO.getTaxDataDAO().getTaxData(year);
		} catch (DAOException e) {
			throw new ServiceException( e);
		}					
	}

}

