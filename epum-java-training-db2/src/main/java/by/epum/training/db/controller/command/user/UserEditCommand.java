package by.epum.training.db.controller.command.user;

import java.io.IOException;

import by.epum.training.db.controller.Command;
import by.epum.training.db.controller.util.Utils;
import by.epum.training.db.entity.Role;
import by.epum.training.db.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserEditCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if(request.getParameter("userId")!=null) {
			User user=Utils.userFromRequest(request);

			request.getSession(false).setAttribute("currentUser", user);
		}
		
		request.getSession().setAttribute("roles", Role.values());

		return "/WEB-INF/jsp/user/edit.jsp";
	}
}
