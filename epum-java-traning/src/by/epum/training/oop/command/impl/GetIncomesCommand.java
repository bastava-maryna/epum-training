package by.epum.training.oop.command.impl;

import java.time.LocalDate;
import java.util.List;

import by.epum.training.oop.command.Command;
import by.epum.training.oop.controller.Viewer;
import by.epum.training.oop.entity.Income;
import by.epum.training.oop.service.ServiceProvider;
import by.epum.training.oop.service.exception.ServiceException;
import by.epum.training.oop.service.exception.ServiceValidatorException;
import by.epum.training.oop.service.util.Validator;

public class GetIncomesCommand implements Command{

	@Override
	public String execute(String param) throws ServiceValidatorException {
		String []params;
		params=param.split("\\s+");
		
		String id;
		String year;
		
		id=params[1].split("=")[1];
		year=params[2].split("=")[1];
		String responce;
		
		if(Validator.isYearValid(year)) {
		
			ServiceProvider provider=ServiceProvider.getInstance();
		
			try {
				List<Income> incomes=provider.getIncomeService().getIncomes(Long.valueOf(id), year);
				responce=Viewer.showIncomes(incomes);	
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
