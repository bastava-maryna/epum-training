package by.epum.training.db.controller.command.book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import by.epum.training.db.controller.Command;
import by.epum.training.db.entity.Station;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.StationService;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BookCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.getSession(false).removeAttribute("requestedTrain");
		request.getSession(false).removeAttribute("train");
		request.getSession(false).removeAttribute("bill");
		request.getSession(false).removeAttribute("errors");
		
		try (ServiceProvider sp=getServiceProvider()){
			StationService service =sp.getStationService();
			List<Station> stations;
			
			stations = service.findAll();
			
			LocalDate today=LocalDate.now();
			
			request.setAttribute("stations", stations);
			request.setAttribute("today", today);
		} catch (ServiceException e) {
			throw new ServletException(e);		
		} 
	
		return "/WEB-INF/jsp/book.jsp";
	}
}
