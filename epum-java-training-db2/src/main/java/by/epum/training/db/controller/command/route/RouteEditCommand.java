package by.epum.training.db.controller.command.route;

import java.io.IOException;
import java.util.List;

import by.epum.training.db.controller.Command;
import by.epum.training.db.controller.util.Utils;
import by.epum.training.db.entity.Route;
import by.epum.training.db.entity.Station;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.StationService;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RouteEditCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try (ServiceProvider sp=getServiceProvider()){

			if(request.getParameter("routeId")!=null) {
				Route route=Utils.routeFromRequest(request);

				request.getSession(false).setAttribute("currentRoute", route);
			}
			
			StationService stationService = sp.getStationService();

			List<Station> stations= stationService.findAll();
			
			request.setAttribute("stations", stations);
			
		} catch (ServiceException e) {
			throw new ServletException(e.getMessage());
		}

		return "/WEB-INF/jsp/route/edit.jsp";
	}
}
