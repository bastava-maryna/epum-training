package by.epum.training.db.controller.command.train;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import by.epum.training.db.controller.Command;
import by.epum.training.db.controller.util.Utils;
import by.epum.training.db.entity.Route;
import by.epum.training.db.entity.ScheduleMode;
import by.epum.training.db.entity.Station;
import by.epum.training.db.entity.Train;
import by.epum.training.db.service.RouteService;
import by.epum.training.db.service.ScheduleModeService;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.StationService;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TrainEditCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		try (ServiceProvider sp=getServiceProvider()){
			if(request.getParameter("trainId")!=null) {
				Train train=Utils.trainFromRequest(request);

				request.getSession(false).setAttribute("currentTrain", train);//??
			}
		
			RouteService routeService = sp.getRouteService();
			ScheduleModeService scheduleModeService = sp.getScheduleModeService();
			StationService stationService = sp.getStationService();

			List<Route> routes=routeService.findAll();

			for(Route route:routes) {
				Station departure=route.getDeparture();//station with id but without name
				departure=stationService.findById(departure.getId());
				route.setDeparture(departure);

				Station destination=route.getDestination();//station with id but without name
				destination=stationService.findById(destination.getId());
				route.setDestination(destination);
			}

			List<ScheduleMode> scheduleModes=scheduleModeService.findAll();

			LocalDate today=LocalDate.now();
			
			request.setAttribute("today", today);
			request.setAttribute("scheduleModes", scheduleModes);
			request.setAttribute("routes", routes);
		
		} catch (ServiceException e) {
			throw new ServletException(e.getMessage());
		}

		return "/WEB-INF/jsp/train/edit.jsp";
	}
}
