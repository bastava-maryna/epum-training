package by.epum.training.db.controller.command.station;

import by.epum.training.db.controller.Command;
import by.epum.training.db.entity.Station;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.StationService;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StationEditCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException{

		Integer id = null;

		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch(NumberFormatException e) {}

		if(id != null) {
			try (ServiceProvider sp=getServiceProvider()){
				StationService service = sp.getStationService();

				Station station;
				station = service.findById(id);

				request.setAttribute("station", station);

			} catch (ServiceException e) {
				throw new ServletException(e); 
			}
		}

		return "/WEB-INF/jsp/station/edit.jsp";
	}
}


