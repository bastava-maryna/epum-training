package by.epum.training.db.controller;


import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import by.epum.training.db.controller.command.ErrorHandlerCommand;
import by.epum.training.db.controller.command.HomeCommand;
import by.epum.training.db.controller.command.LanguageCommand;
import by.epum.training.db.controller.command.ManageCommand;
import by.epum.training.db.controller.command.bill.BillEditCommand;
import by.epum.training.db.controller.command.bill.BillListCommand;
import by.epum.training.db.controller.command.bill.BillSaveCommand;
import by.epum.training.db.controller.command.book.BookCommand;
import by.epum.training.db.controller.command.book.BookListCommand;
import by.epum.training.db.controller.command.book.BookSaveCommand;
import by.epum.training.db.controller.command.book.PayCommand;
import by.epum.training.db.controller.command.carriage.CarriageChooseCommand;
import by.epum.training.db.controller.command.carriage.CarriageDeleteCommand;
import by.epum.training.db.controller.command.carriage.CarriageEditCommand;
import by.epum.training.db.controller.command.carriage.CarriageListCommand;
import by.epum.training.db.controller.command.carriage.CarriageSaveCommand;
import by.epum.training.db.controller.command.route.RouteEditCommand;
import by.epum.training.db.controller.command.route.RouteListCommand;
import by.epum.training.db.controller.command.route.RouteSaveCommand;
import by.epum.training.db.controller.command.station.StationEditCommand;
import by.epum.training.db.controller.command.station.StationListCommand;
import by.epum.training.db.controller.command.station.StationSaveCommand;
import by.epum.training.db.controller.command.train.TrainEditCommand;
import by.epum.training.db.controller.command.train.TrainListCommand;
import by.epum.training.db.controller.command.train.TrainSaveCommand;
import by.epum.training.db.controller.command.user.GetLoginCommand;
import by.epum.training.db.controller.command.user.SignUpCommand;
import by.epum.training.db.controller.command.user.LogoutCommand;
import by.epum.training.db.controller.command.user.PersonInfoCommand;
import by.epum.training.db.controller.command.user.PostPersonInfoCommand;
import by.epum.training.db.controller.command.user.PostSignUpCommand;
import by.epum.training.db.controller.command.user.UserEditCommand;
import by.epum.training.db.controller.command.user.UserListCommand;
import by.epum.training.db.controller.command.user.UserSaveCommand;
import jakarta.servlet.ServletException;



public class ServletCommandProvider {
	//key- command name, value- link to the Command object which can do this command
	//private Map<String,Command> commands=new HashMap<String,Command>();  //??or concurrent
	private static Map<String,Class<? extends Command>> commands=new HashMap<>();  //??or concurrent
 
	static {	
		//when new command appear - we only need to add it here
		commands.put("/",	HomeCommand.class);
		
		commands.put("/jsp/home",	HomeCommand.class);

		commands.put("/jsp/login",	GetLoginCommand.class);
		commands.put("/jsp/join",	SignUpCommand.class);
		commands.put("/jsp/sign",PostSignUpCommand.class);
		commands.put("/jsp/logout",	LogoutCommand.class);		  	    

		commands.put("/jsp/user/person",PersonInfoCommand.class);
		commands.put("/jsp/user/update",PostPersonInfoCommand.class);
		commands.put("/jsp/user/list",UserListCommand.class);
		commands.put("/jsp/user/edit",UserEditCommand.class);
		commands.put("/jsp/user/save",UserSaveCommand.class);

		commands.put("/jsp/manage",ManageCommand.class);

		commands.put("/jsp/station/list",StationListCommand.class);
		commands.put("/jsp/station/edit",StationEditCommand.class);
		commands.put("/jsp/station/save",StationSaveCommand.class);

		commands.put("/jsp/route/list",RouteListCommand.class);
		commands.put("/jsp/route/edit",RouteEditCommand.class);
		commands.put("/jsp/route/save",RouteSaveCommand.class);

		commands.put("/jsp/train/list",TrainListCommand.class);
		commands.put("/jsp/train/edit",TrainEditCommand.class);
		commands.put("/jsp/train/save",TrainSaveCommand.class);

		commands.put("/jsp/carriage/list",CarriageListCommand.class);
		commands.put("/jsp/carriage/edit",CarriageEditCommand.class);
		commands.put("/jsp/carriage/save",CarriageSaveCommand.class);  
		commands.put("/jsp/carriage/delete",CarriageDeleteCommand.class);
		commands.put("/jsp/carriage/choose",CarriageChooseCommand.class); 

		commands.put("/jsp/bill/list",BillListCommand.class);
		commands.put("/jsp/bill/edit",BillEditCommand.class);
		commands.put("/jsp/bill/save",BillSaveCommand.class);

		commands.put("/jsp/book",BookCommand.class);
		commands.put("/jsp/book/list",BookListCommand.class);
		commands.put("/jsp/book/save",BookSaveCommand.class);

		commands.put("/jsp/pay",PayCommand.class);

		commands.put("/jsp/language",LanguageCommand.class);

		commands.put("/jsp/error",	ErrorHandlerCommand.class);

		//can create command for impossible option
	}

	public static Command getCommand(String commandName ) throws ServletException {
		Class<?>command=commands.get(commandName);

		if(command!=null) {
			try {
				return (Command) command.getConstructor().newInstance();

			}catch(InstantiationException | IllegalAccessException | NullPointerException | IllegalArgumentException |  SecurityException | InvocationTargetException | NoSuchMethodException  e) {
				throw new ServletException(e);
			}
		}else {   
			return null;
		}
	}


}

