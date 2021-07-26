package by.epum.training.oop.command.impl;

import by.epum.training.oop.command.Command;

public class QuitCommand implements Command {

	@Override
	public String execute(String param) {
		
		String responce;		
		responce="Finita la comedia";
	//	System.exit(0);
					//??reachable
	
		return responce;
	}
}
