package by.epum.training.db.controller.command.book;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.epum.training.db.controller.Command;
import by.epum.training.db.entity.CarriageType;
import by.epum.training.db.entity.Route;
import by.epum.training.db.entity.ScheduleMode;
import by.epum.training.db.entity.Station;
import by.epum.training.db.entity.Train;
import by.epum.training.db.service.PriceService;
import by.epum.training.db.service.RouteService;
import by.epum.training.db.service.ScheduleModeService;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.StationService;
import by.epum.training.db.service.TrainService;
import by.epum.training.db.service.exception.RouteNotExistException;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BookListCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String,String> errors=new HashMap<String,String>();
		
		try (ServiceProvider sp=getServiceProvider()){
			TrainService trainService = sp.getTrainService();
			StationService stationService = sp.getStationService();
			PriceService priceService = sp.getPriceService();
				
			Train requestedTrain=null;
			requestedTrain=(Train)request.getSession(false).getAttribute("requestedTrain");
	
			List<Train> trains;
			
			if(requestedTrain==null) {
				requestedTrain=new Train();

				Station departure=stationService.findById(Integer.valueOf(request.getParameter("departureStationId")));
				Station destination=stationService.findById(Integer.valueOf(request.getParameter("destinationStationId")));

				RouteService routeService = sp.getRouteService();
				Route route=routeService.findByStations(departure, destination);
				route.setDeparture(departure);
				route.setDestination(destination);

				LocalDate departureDate=LocalDate.parse(request.getParameter("departureDate"));

				requestedTrain.setRoute(route);
				requestedTrain.setDepartureDate(departureDate);


				if(request.getSession(false)!=null) {
					request.getSession(false).setAttribute("requestedTrain",requestedTrain);
				}else {
					request.setAttribute("requestedTrain",requestedTrain);
				}
			}

			trains=trainService.findByStationsAndDate(requestedTrain.getRoute().getDeparture(), requestedTrain.getRoute().getDestination(), requestedTrain.getDepartureDate());

			for(Train train:trains) {
				ScheduleModeService scheduleModeService=sp.getScheduleModeService();
				ScheduleMode scheduleMode=new ScheduleMode();
				scheduleMode=scheduleModeService.findById(train.getScheduleMode().getId());

				train.setScheduleMode(scheduleMode);
			}
			
			Map<Integer,List<BigDecimal>> prices=priceService.getPrices(trains);
			
			LocalDate today=LocalDate.now();
			
			request.setAttribute("trains", trains);
			request.setAttribute("prices", prices);
			request.setAttribute("today", today);
			request.setAttribute("carriageTypes", CarriageType.values());
			
		} catch(RouteNotExistException e) {
			errors.put("noRoute", "Route with these parameters does no exist. ");
		} catch (ServiceException e) {
			throw new ServletException(e);		
		} 
		
		return "/WEB-INF/jsp/book/list.jsp";
	}
}
