package by.epum.training.db.controller.command.station;

import java.util.List;

import by.epum.training.db.controller.Command;
import by.epum.training.db.entity.Station;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.StationService;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StationListCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		try (ServiceProvider sp=getServiceProvider()){
			StationService service =sp.getStationService();
			List<Station> stations;

			stations = service.findAll();
			request.setAttribute("stations", stations);

		} catch (ServiceException e) {
			throw new ServletException(e);		
		}

		return "/WEB-INF/jsp/station/list.jsp";
	}
}
