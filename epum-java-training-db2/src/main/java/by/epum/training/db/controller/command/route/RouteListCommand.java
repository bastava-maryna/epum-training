package by.epum.training.db.controller.command.route;

import java.io.IOException;
import java.util.List;

import by.epum.training.db.controller.Command;
import by.epum.training.db.entity.Route;
import by.epum.training.db.entity.Station;
import by.epum.training.db.service.RouteService;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.StationService;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RouteListCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.getSession(false).removeAttribute("currentRoute");
		
		try (ServiceProvider sp=getServiceProvider()){
			RouteService routeService = sp.getRouteService();

			List<Route> routes;
			routes = routeService.findAll();

			for(Route route:routes) {
				StationService stationService = sp.getStationService();
				
				//station with id but without name
				Station departure=route.getDeparture();
				departure=stationService.findById(departure.getId());
				route.setDeparture(departure);

				Station destination=route.getDestination();
				destination=stationService.findById(destination.getId());
				route.setDestination(destination);
			}
	
			request.setAttribute("routes", routes);
		
		} catch (ServiceException e) {
			throw new ServletException(e.getMessage());		
		} 
		
		return "/WEB-INF/jsp/route/list.jsp";
	}
}
