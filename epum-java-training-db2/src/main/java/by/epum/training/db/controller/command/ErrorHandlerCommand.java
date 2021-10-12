package by.epum.training.db.controller.command;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epum.training.db.controller.Command;
import by.epum.training.db.controller.listener.ContextManager;
import by.epum.training.db.controller.util.Utils;
import by.epum.training.db.entity.Role;
import by.epum.training.db.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ErrorHandlerCommand implements Command {

		private static final Logger logger=LogManager.getLogger(ContextManager.class);
		
		@Override
		public String execute(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
	        String userMessage="Service unavailable.";
			
			Throwable throwable = (Throwable) request
	                .getAttribute("jakarta.servlet.error.exception");
	        Integer statusCode = (Integer) request
	                .getAttribute("jakarta.servlet.error.status_code");
	        String servletName = (String) request
	                .getAttribute("jakarta.servlet.error.servlet_name");
	        String requestUri = (String) request
	                .getAttribute("jakarta.servlet.error.request_uri");
	       
	        
	        String message="";
	        
	        if (throwable == null && statusCode == null) {
	            message="Error Information Is Missing"; 
	            logger.error(message);
	        } else {
	            message="Errors Details:\nServlet Name=" + servletName + "<br/>Exception Name=" + throwable.getClass( ).getName( ) + 
	            		"<br/>Requested URI=" + requestUri+"<br/>Exception Message=" + throwable.getMessage( ) ;
	            logger.error(message);
	        }
        
	        User user=(User)Utils.getLoginedUser(request.getSession(false));
	        if(user!=null && user.getRole().equals(Role.ADMIN)) {
	        	request.setAttribute("exception", message); 
	        }	else {
	        	request.setAttribute("exception", userMessage);
	        }
	        
	        return "/error.jsp";
		}
}




