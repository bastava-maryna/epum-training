package by.epum.training.db.controller.command.user;

import java.io.IOException;

import by.epum.training.db.controller.Command;
import by.epum.training.db.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SignUpCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User loginedUser=(User) request.getSession(false).getAttribute("loginedUser");
		
		if(loginedUser != null) {
			response.sendRedirect(request.getContextPath()+"/jsp/home?message="+"join.message.already");
			return "REDIRECTED";
		}

		return "/WEB-INF/jsp/join.jsp";
	}

}

