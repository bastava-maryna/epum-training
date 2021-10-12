package by.epum.training.db.controller.command.bill;

import java.io.IOException;
import java.util.List;

import by.epum.training.db.controller.Command;
import by.epum.training.db.controller.util.Utils;
import by.epum.training.db.entity.Bill;
import by.epum.training.db.entity.Bill.Status;
import by.epum.training.db.entity.User;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.UserService;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BillEditCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try (ServiceProvider sp=getServiceProvider()){	
			if(request.getParameter("billId")!=null) {
				Bill bill=Utils.billFromRequest(request);

				request.getSession(false).setAttribute("currentBill", bill);
			}
				
			UserService userService = sp.getUserService();
			List<User> users;
			users = userService.findAll();

			request.setAttribute("statuses", Status.values());
			request.setAttribute("users", users);
		} catch (ServiceException e) {
			throw new ServletException(e);	
		}
		
		return "/WEB-INF/jsp/bill/edit.jsp";
	}
}
