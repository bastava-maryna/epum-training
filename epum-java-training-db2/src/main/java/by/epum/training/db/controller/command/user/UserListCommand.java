package by.epum.training.db.controller.command.user;

import java.io.IOException;
import java.util.List;

import by.epum.training.db.controller.Command;
import by.epum.training.db.entity.User;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.UserService;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserListCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getSession(false).removeAttribute("currentUser");	

		try (ServiceProvider sp=getServiceProvider()){
			UserService userService = sp.getUserService();

			List<User> users = userService.findAll();

			request.setAttribute("users", users);
			
		} catch (ServiceException e) {
			throw new ServletException(e);		
		} 

		return "/WEB-INF/jsp/user/list.jsp";
		
	}
}
