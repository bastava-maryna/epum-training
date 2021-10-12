package by.epum.training.db.controller.command;

import java.io.IOException;

import by.epum.training.db.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LanguageCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		String language = request.getParameter("language");
        request.setAttribute("language", language);
      
        return "/home.jsp";
      
	}
}
