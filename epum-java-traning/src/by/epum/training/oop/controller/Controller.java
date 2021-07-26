package by.epum.training.oop.controller;

import by.epum.training.oop.command.Command;
import by.epum.training.oop.command.CommandProvider;
import by.epum.training.oop.service.exception.ServiceException;


public class Controller {
	//???or private
	public final CommandProvider provider=new CommandProvider();
	
	public String doAction (String request) throws ServiceException {
		//"FindByID id='10060' 
		//"FIND_BY_LAST_NAME lastName=Ivanova"
		//"CalculateAnnualTax id="10060" year="2020"
		//"GetIncomes id="10060" year="2020"
		//"GetTaxData year="2020"
		//"DisplayAnnualTax id=10060 year=2020"
		//SortAnnualTaxByTax id=10060 year=2020"
		//"GET_MONTH_INCOMES id=10060 year=2020 month=5"  
    	//"DISPLAY_Period_TAX id=10060 year=2020 month=5"
		//"QUIT"
		
		String commandName;
		commandName=request.split("\\s+")[0];
		
		Command command=provider.getCommand(commandName);
		
		String responce;
		responce=command.execute(request);
		
		return responce;
	}
	
}
