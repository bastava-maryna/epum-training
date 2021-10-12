package by.epum.training.db.controller.command.station;

import java.io.IOException;
import java.util.Map;

import by.epum.training.db.controller.Command;
import by.epum.training.db.controller.util.Validator;
import by.epum.training.db.entity.Station;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.StationService;
import by.epum.training.db.service.exception.ServiceException;
import by.epum.training.db.service.exception.StationNameNotUniqueException;
import by.epum.training.db.service.exception.StationNotExistException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StationSaveCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String stationName=request.getParameter("stationName");	
		Map<String,String> errors=Validator.validateStationEditForm(stationName);

		if(errors.isEmpty()) {
			Station station = new Station();

			try {
				station.setId(Integer.parseInt(request.getParameter("id")));
			} catch(NumberFormatException e) {}

			station.setStationName(stationName);

			if(errors.isEmpty()) {
				try (ServiceProvider sp=getServiceProvider()){
					StationService service = sp.getStationService();
					service.save(station);

					response.sendRedirect(request.getContextPath()+"/jsp/station/list?message=operation.complete");
					return "REDIRECTED";

				} catch(StationNameNotUniqueException e) {
					errors.put("duplicateStation", "Station with this name already exist: "+e.getStationName());
				} catch(StationNotExistException e) {
					errors.put("noStation", "Such station does no exist. ");
				} catch(ServiceException e) {
					throw new ServletException(e);
				}
			}
		}
		request.setAttribute("errors", errors);
		
		return "/WEB-INF/jsp/station/edit.jsp";
	}
}
