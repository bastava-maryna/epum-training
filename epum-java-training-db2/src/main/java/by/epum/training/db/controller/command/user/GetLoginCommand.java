package by.epum.training.db.controller.command.user;



import java.io.IOException;

import by.epum.training.db.controller.Command;
import by.epum.training.db.controller.util.Utils;
import by.epum.training.db.entity.User;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetLoginCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if(Utils.getLoginedUser(request.getSession(false))!=null){
			response.sendRedirect(request.getContextPath()+"/jsp/home?message="+"login.message.success");
			return "REDIRECTED";
		}

		//have not authenticated jet
		String login = request.getParameter("login");
		String password = request.getParameter("password");

		if(login != null  && password != null && !login.isBlank()) {   
			try (ServiceProvider sp=getServiceProvider()){	
				UserService service = sp.getUserService();

				User user = service.findByLoginAndPassword(login, password);

				if(user != null) {
					Utils.storeLoginedUser(request.getSession(), user);
					
					int redirectId=-1;
	
					try {
						redirectId=Integer.parseInt(request.getParameter("redirectId"));
					}catch (NumberFormatException e){}
					
					String requestUri=Utils.getRedirectAfterUserLoginUrl(request.getSession(false), redirectId);

					if(requestUri!=null) {
						response.sendRedirect(requestUri);

						return "REDIRECTED";
					}else {
						response.sendRedirect(request.getContextPath()+"/jsp/login?message="+"login.message.success");
						return "REDIRECTED";
					}

				} else {
					String errorMessage="Invalid login or password";
					request.setAttribute("errorMessage",errorMessage);

					response.sendRedirect(request.getContextPath()+"/jsp/login?message="+"login.message.invalidLoginOrPassword");
					return "REDIRECTED";
				}
			} catch(Exception e) {
				throw new ServletException(e);
			}
		} 
		else {
			return "/WEB-INF/jsp/login.jsp";
		}
	}	
}





