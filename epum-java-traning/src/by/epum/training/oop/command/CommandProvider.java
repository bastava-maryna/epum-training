package by.epum.training.oop.command;

import java.util.HashMap;
import java.util.Map;

import by.epum.training.oop.command.impl.CalculateAnnualTaxCommand;
import by.epum.training.oop.command.impl.DisplayAnnualTaxCommand;
import by.epum.training.oop.command.impl.DisplayPeriodTaxCommand;
import by.epum.training.oop.command.impl.GetByIDCommand;
import by.epum.training.oop.command.impl.FindByLastNameCommand;
import by.epum.training.oop.command.impl.GetIncomesCommand;
import by.epum.training.oop.command.impl.GetMonthIncomesCommand;
import by.epum.training.oop.command.impl.GetTaxDataCommand;
import by.epum.training.oop.command.impl.QuitCommand;
import by.epum.training.oop.command.impl.SortAnnualTaxByTaxCommand;


public class CommandProvider {
    private Map<CommandName,Command> commands;
	
	public CommandProvider() {
		commands=new HashMap<CommandName,Command>();
		commands.put(CommandName.GET_BY_ID,new GetByIDCommand());
		commands.put(CommandName.FIND_BY_LAST_NAME,new FindByLastNameCommand());
		commands.put(CommandName.CALCULATE_ANNUAL_TAX, new CalculateAnnualTaxCommand());
		commands.put(CommandName.DISPLAY_ANNUAL_TAX, new DisplayAnnualTaxCommand());
		commands.put(CommandName.GET_INCOMES, new GetIncomesCommand());
		commands.put(CommandName.GET_TAX_DATA, new GetTaxDataCommand());
		commands.put(CommandName.SORT_ANNUAL_TAX_BY_TAX, new SortAnnualTaxByTaxCommand());
		commands.put(CommandName.GET_MONTH_INCOMES, new GetMonthIncomesCommand());
		commands.put(CommandName.DISPLAY_PERIOD_TAX, new DisplayPeriodTaxCommand());
		commands.put(CommandName.QUIT, new QuitCommand());
	}
	
	public Command getCommand(String strCommandName) {
		CommandName commandName=CommandName.valueOf(strCommandName.toUpperCase());
		Command command;
		command=commands.get(commandName);
		return command;
	}
}
