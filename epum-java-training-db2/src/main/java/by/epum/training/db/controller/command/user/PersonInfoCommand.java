package by.epum.training.db.controller.command.user;

import java.io.IOException;

import by.epum.training.db.controller.Command;
import by.epum.training.db.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PersonInfoCommand implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		User currentUser=(User) request.getSession(false).getAttribute("loginedUser");
		//if already authenticate
		if(currentUser != null) {	
			return "/WEB-INF/jsp/user/person.jsp";
		}
		else {			
			response.sendRedirect(request.getContextPath()+"/jsp/login?message=person.message.need");
			return "REDIRECTED";
		}
	}
}
