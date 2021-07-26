package by.epum.training.oop.command.impl;

import by.epum.training.oop.command.Command;
import by.epum.training.oop.controller.Viewer;
import by.epum.training.oop.entity.TaxPayer;
import by.epum.training.oop.service.ServiceProvider;
import by.epum.training.oop.service.exception.ServiceException;

public class GetByIDCommand implements Command {

	@Override
	public String execute(String param) {
		String []params;
		params=param.split("\\s+");
		
		String id;	
		String responce;
		
		id=params[1].split("=")[1];
		
		ServiceProvider provider=ServiceProvider.getInstance();
		
		try {
			TaxPayer taxPayer=provider.getTaxPayerService().getById(Long.valueOf(id));
			responce=(taxPayer==null)?"There is no taxPayer with id= "+id : Viewer.showTaxPayerInfo(taxPayer);
		
		} catch (ServiceException e) {
			// or throw
			System.err.println(e.getMessage());
			responce="error";
		}
		
		return responce;
	}
}
