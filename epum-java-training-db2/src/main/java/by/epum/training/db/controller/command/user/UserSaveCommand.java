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
import by.epum.training.db.service.exception.UserNotExistException;
import by.epum.training.db.service.exception.UserNotUniqueException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserSaveCommand implements Command {
	//  for now this for admin part
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User currentUser=(User)request.getSession(false).getAttribute("currentUser");
		User user=Utils.userFromRequest(request);

		Map<String,String> errors=null; 

		//here use validation without password confirmed
		errors=Validator.validateSignUpForm(user.getLastName(), user.getFirstName(), user.getPassport(), user.getEmail(), user.getLogin(), user.getPassword());	
		if(errors.isEmpty()) {

			if(user.getId()!=null) {
				if(user.equals(currentUser)) {  
					response.sendRedirect(request.getContextPath()+"/jsp/user/edit?message="+"operation.changeNothing");
					return "REDIRECTED";
				}
			}
			try (ServiceProvider sp=getServiceProvider()){

				UserService userService = sp.getUserService();

				userService.save(user);

				request.getSession(false).removeAttribute("currentUser");
				request.getSession(false).removeAttribute("errors");

				response.sendRedirect(request.getContextPath()+"/jsp/user/list?message="+"person.message.updated");
				return "REDIRECTED";

			} catch(UserNotUniqueException e) {
				errors.put("duplicateUser", "User with these parameters already exist: "+e.getId());
			} catch(UserNotExistException e) {
				errors.put("noUser", "User with this id parameter doesnt exist exist: "+e.getId());
			} 
			catch(ServiceException e) {
				throw new ServletException(e.getMessage());
			}
		}

		request.getSession(false).setAttribute("errors", errors);
		response.sendRedirect(request.getContextPath()+"/jsp/user/edit");

		return "REDIRECTED";
	}

}
