package by.epum.training.oop.controller;

import by.epum.training.oop.command.Command;
import by.epum.training.oop.command.CommandProvider;
import by.epum.training.oop.service.ServiceException;

public class Controller {
	
	public final CommandProvider provider=new CommandProvider();
	
	public String doAction (String request) throws ServiceException {
		//"FindByID id='10060' 
		//"CalculateAnnualTax id="10060" year="2020"
		//"GetIncomes id="10060" year="2020"
		//"GetTaxData year="2020"
		
		String commandName;
		commandName=request.split("\\s+")[0];
		
		Command command=provider.getCommand(commandName);
		
		String responce;
		responce=command.execute(request);
		
		return responce;
	}
	
}
