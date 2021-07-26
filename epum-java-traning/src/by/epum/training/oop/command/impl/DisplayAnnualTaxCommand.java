package by.epum.training.oop.command.impl;

import java.time.LocalDate;
import java.util.Map;

import by.epum.training.oop.command.Command;
import by.epum.training.oop.controller.Viewer;
import by.epum.training.oop.entity.IncomeType;
import by.epum.training.oop.service.ServiceProvider;
import by.epum.training.oop.service.exception.ServiceException;
import by.epum.training.oop.service.impl.calculators.BaseTaxCalculator;
import by.epum.training.oop.service.util.Validator;


public class DisplayAnnualTaxCommand implements Command{

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
					Map<IncomeType,BaseTaxCalculator> res=	provider.getTaxCalculatorService().calculateAnnualTax(Long.valueOf(id), year);
		
					if(res==null) {
						responce="There is no taxPayer with id= "+id;
					}else if(res.isEmpty()){
						responce="TaxPayer with id= "+id+ " has no incomes in "+year +" year";
					}else {
						responce=Viewer.showCalculatedTax(res);
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
		
}
