package by.epum.training.db.controller.command.user;

import java.io.IOException;

import by.epum.training.db.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutCommand implements Command {
	
	
	private static final String HOME_PAGE="/home.jsp"; 
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		
		 HttpSession session = request.getSession(false);
	        if(session != null) {
	            session.invalidate();
	        }
	        return HOME_PAGE;
	}
}
