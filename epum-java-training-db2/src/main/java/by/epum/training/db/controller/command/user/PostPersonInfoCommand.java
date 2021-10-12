package by.epum.training.db.controller.command.user;

import java.io.IOException;
import java.util.Map;

import by.epum.training.db.controller.Command;
import by.epum.training.db.controller.util.Utils;
import by.epum.training.db.controller.util.Validator;
import by.epum.training.db.entity.User;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.UserService;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PostPersonInfoCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user=Utils.userFromRequest(request);

		Map<String,String> errors=null;

		errors=Validator.validateSignUpForm(user.getLastName(), user.getFirstName(), user.getPassport(), user.getEmail(), user.getLogin(), user.getPassword());

		if(errors.isEmpty()) {   
			try (ServiceProvider sp=getServiceProvider()){
				UserService userService = sp.getUserService();
				
				userService.save(user);
		
				request.getSession(false).removeAttribute("errors");
				
				Utils.storeLoginedUser(request.getSession(false), user);
				
				response.sendRedirect(request.getContextPath()+"/jsp/user/person?message=person.message.updated");
				return "REDIRECTED";

			} catch (ServiceException e) {
				throw new ServletException(e);
			}
		}
    
		request.getSession(false).setAttribute("errors", errors);
	
		return "/WEB-INF/jsp/user/person.jsp";
	}
}
