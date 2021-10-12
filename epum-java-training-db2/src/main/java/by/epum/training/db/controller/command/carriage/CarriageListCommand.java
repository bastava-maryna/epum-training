package by.epum.training.db.controller.command.carriage;

import java.io.IOException;
import java.util.List;

import by.epum.training.db.controller.Command;
import by.epum.training.db.entity.Carriage;
import by.epum.training.db.service.CarriageService;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CarriageListCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getSession(false).removeAttribute("currentCarriage");

		try (ServiceProvider sp=getServiceProvider()){
			CarriageService carriageService = sp.getCarriageService();

			List<Carriage> carriages;
			carriages = carriageService.findAll();
			
			request.setAttribute("carriages", carriages);

		} catch (ServiceException e) {
			throw new ServletException(e.getMessage());		
		} 

		return "/WEB-INF/jsp/carriage/list.jsp";    
	}
}
