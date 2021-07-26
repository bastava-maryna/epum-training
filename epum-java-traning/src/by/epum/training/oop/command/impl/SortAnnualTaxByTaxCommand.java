package by.epum.training.oop.command.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import by.epum.training.oop.command.Command;
import by.epum.training.oop.controller.Viewer;
import by.epum.training.oop.entity.IncomeType;
import by.epum.training.oop.service.ServiceProvider;
import by.epum.training.oop.service.exception.ServiceException;
import by.epum.training.oop.service.impl.calculators.BaseTaxCalculator;
import by.epum.training.oop.service.util.Validator;

public class SortAnnualTaxByTaxCommand implements Command{

	@Override
	public String execute(String param) throws ServiceException {
		String []params;
		params=param.split("\\s+");
		
		String id;
		String year;
		String responce;
		
		id=params[1].split("=")[1];
		year=params[2].split("=")[1];		
		
		if(Validator.isYearValid(year)) {
			
			ServiceProvider provider=ServiceProvider.getInstance();
			
			try {
				Map<IncomeType,BaseTaxCalculator> annualTax=provider.getTaxCalculatorService().calculateAnnualTax(Long.valueOf(id), year);
				
				if(annualTax==null) {
					responce="There is no taxPayer with id= "+id;
				}else if(annualTax.isEmpty()){
					responce="TaxPayer with id= "+id+ " has no incomes in "+year +" year";
				}else {
					responce=Viewer.showCalculatedTax(sortByTax(annualTax));
				}
			} catch (ServiceException e) {
				// or throw
				System.err.println(e.getMessage());
				responce="error";
			}
		}else {
			responce="Programm can work with data for year starting from "+Validator.getStartYear()+" to "+LocalDate.now().getYear();
		}
		
		return responce;
	}
	
	
	private Map<IncomeType,BaseTaxCalculator> sortByTax(Map<IncomeType, BaseTaxCalculator> annualTax) {
		List<Entry<IncomeType,BaseTaxCalculator>> list=new ArrayList<Entry<IncomeType,BaseTaxCalculator>>();
		
		for(Entry<IncomeType,BaseTaxCalculator> entry: annualTax.entrySet()) {
			list.add(entry);
		}
		
		Comparator<Entry<IncomeType, BaseTaxCalculator>> compByTax=new Comparator<Entry<IncomeType,BaseTaxCalculator>>(){

			@Override
			public int compare(Entry<IncomeType, BaseTaxCalculator> o1, Entry<IncomeType, BaseTaxCalculator> o2) {
				Double d1=o1.getValue().getTax();
				Double d2=o2.getValue().getTax();   
				return d1.compareTo(d2);
			}
			
		};
		
		Collections.sort(list,compByTax);
		Map<IncomeType,BaseTaxCalculator> sortedMap=new LinkedHashMap<IncomeType,BaseTaxCalculator>();
		for(Entry<IncomeType,BaseTaxCalculator> entry: list) {
			sortedMap.put(entry.getKey(),entry.getValue());
		}
		
		return sortedMap;
	}
}