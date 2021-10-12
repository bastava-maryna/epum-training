package by.epum.training.db.controller.command.user;

import java.io.IOException;
import java.util.Map;

import by.epum.training.db.controller.Command;
import by.epum.training.db.controller.util.Utils;
import by.epum.training.db.controller.util.Validator;
import by.epum.training.db.entity.Role;
import by.epum.training.db.entity.User;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.UserService;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PostSignUpCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		String lastName=request.getParameter("lastName");
		String firstName=request.getParameter("firstName");
		String passport=request.getParameter("passport");
		String login=request.getParameter("login");
		String password=request.getParameter("password");
		String passwordConfirmed=request.getParameter("passwordConfirm");
		String email=request.getParameter("email");

		Map<String,String> errors=null;

		User loginedUser=Utils.getLoginedUser(request.getSession(false));
		boolean isAdmin=loginedUser!=null && loginedUser.getRole().equals(Role.ADMIN);

		if(isAdmin){
			errors=Validator.validateSignUpForm(lastName, firstName, passport, email, login, password);
		}else {
			errors=Validator.validateSignUpForm(lastName, firstName, passport, email, login, password, passwordConfirmed);	
		}

		if(errors.isEmpty()) {
			try (ServiceProvider sp=getServiceProvider()){
				UserService userService =sp.getUserService();
				//there is no such useer in db
				User userFromDB=userService.findByLoginAndPassword(login, password);
				if(userFromDB==null){

					if(userService.findByPassport(passport)==null){
						User user = new User();
						user.setLastName(lastName);
						user.setFirstName(firstName);
						user.setPassport(passport);
						user.setLogin(login);
						user.setPassword(password);
						user.setEmail(email);

						if(isAdmin){
							Role role=Role.valueOf(request.getParameter("role"));
							user.setRole(role);
						}else {
							user.setRole(Role.PASSENGER);
						} 				

						Long newUserId=userService.create(user);
						user.setId(newUserId);

						if(!isAdmin) {
							Utils.storeLoginedUser(request.getSession(), user);

							response.sendRedirect(request.getContextPath()+"/jsp/user/person?message=join.message.success");       			
							return "REDIRECTED";
						}

						response.sendRedirect(request.getContextPath()+"/jsp/user/list?message=join.message.success");
						return "REDIRECTED";    
					}else {
						errors.put("duplicatePassport", "User with this passport already exist");
					}
				}else {
					errors.put("duplicateUser", "User with these credentials already exist");
				}
			} catch (ServiceException e) {
				throw new ServletException(e);				
			} 
		}

		request.setAttribute("errors", errors);

		if(isAdmin) {
			return "/WEB-INF/jsp/user/edit.jsp";
		}
	
		return "/WEB-INF/jsp/join.jsp";
	}
}
