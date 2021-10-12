package by.epum.training.db.controller.command;



import java.io.IOException;

import by.epum.training.db.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HomeCommand implements Command {
	
	private static final String HOME_PAGE="/home.jsp"; 
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		return HOME_PAGE;
	}

}
















/*
 *  String VIEWS_PATH = "/WEB-INF/views/";

    String LOGIN_VIEW = VIEWS_PATH + "login.jsp";
    String HOME_VIEW = VIEWS_PATH + "index.jsp";
    String SIGN_UP_VIEW = VIEWS_PATH + "signup.jsp";
    String ROUTES_VIEW = VIEWS_PATH + "routes.jsp";
    String REQUESTS_HISTORY_VIEW = VIEWS_PATH + "requestHistory.jsp";
    String REQUEST_VIEW = VIEWS_PATH + "createRequest.jsp";
    String INVOICES_VIEW = VIEWS_PATH + "invoices.jsp";
    String ROUTE_CREATION = VIEWS_PATH + "addRoute.jsp";
 */
