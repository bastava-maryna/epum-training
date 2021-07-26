package by.epum.training.oop.command.impl;


import java.util.List;

import by.epum.training.oop.command.Command;
import by.epum.training.oop.controller.Viewer;
import by.epum.training.oop.entity.TaxPayer;
import by.epum.training.oop.service.ServiceProvider;
import by.epum.training.oop.service.exception.ServiceException;

public class FindByLastNameCommand implements Command {
	@Override
	public String execute(String param) {
		String []params;
		params=param.split("\\s+");
		
		String lastName;	
		String responce;
		
		lastName=params[1].split("=")[1];
		
		ServiceProvider provider=ServiceProvider.getInstance();
		
		
		try {
			List<TaxPayer> taxPayers=provider.getTaxPayerService().findByLastName(lastName);
			responce=(taxPayers==null)?"There is no taxPayer with lastName= "+lastName : Viewer.showTaxPayersInfo(taxPayers);
		
		} catch (ServiceException e) {
			// o throw
			System.err.println(e.getMessage());
			responce="error";
		}
		
		return responce;
	}
}
