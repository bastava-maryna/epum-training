package by.epum.training.db.controller.command.carriage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import by.epum.training.db.controller.Command;
import by.epum.training.db.entity.Carriage;
import by.epum.training.db.entity.CarriageType;
import by.epum.training.db.entity.Route;
import by.epum.training.db.entity.Station;
import by.epum.training.db.entity.Train;
import by.epum.training.db.service.CarriageService;
import by.epum.training.db.service.PriceService;
import by.epum.training.db.service.RouteService;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.StationService;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CarriageChooseCommand implements Command {
	private static final String CARRIAGE_CHOOSE_PAGE="/WEB-INF/jsp/carriage/choose.jsp"; 
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.getSession(false).removeAttribute("errors");
		System.out.println("Session in carriageChoose:: ID="+request.getSession(false).getId());
		
		LocalTime departureTime=null;
		LocalTime destinationTime=null;
		BigDecimal price=null;		
		Integer trainId = null;
		String trainName=null;
		
		try {
			departureTime=LocalTime.parse(request.getParameter("departureTime"),DateTimeFormatter.ofPattern("HH:mm"));
			destinationTime=LocalTime.parse(request.getParameter("destinationTime"),DateTimeFormatter.ofPattern("HH:mm"));
			price=new BigDecimal(request.getParameter("price"));
			trainId=Integer.valueOf(request.getParameter("trainId"));
			trainName=request.getParameter("trainName");
		} catch(NumberFormatException e) {}
		
		
		
		Train chosenTrain=(Train)request.getSession(false).getAttribute("requestedTrain");
		
		try (ServiceProvider sp=getServiceProvider()){
			//when just loggined, the restore the search parameters 
			if(chosenTrain==null) {
				chosenTrain=new Train();

				StationService stationService = sp.getStationService();
				Station departure=stationService.findById(Integer.valueOf(request.getParameter("departureStationId")));
				Station destination=stationService.findById(Integer.valueOf(request.getParameter("destinationStationId")));

				RouteService routeService = sp.getRouteService();
				Route route=routeService.findByStations(departure, destination);
				route.setDeparture(departure);
				route.setDestination(destination);

				LocalDate departureDate=LocalDate.parse(request.getParameter("departureDate"));

				chosenTrain.setRoute(route);
				chosenTrain.setDepartureDate(departureDate);		
			}

			chosenTrain.setId(trainId);
						
			if( trainId!=null) {  
				PriceService priceService = sp.getPriceService();
				CarriageService carriageService=sp.getCarriageService();
				
				Map<Carriage,List<Short>> availablePlacesInTrain= carriageService.getAvailablePlacesInTrain(trainId);
				
				chosenTrain.setDepartureTime(departureTime);
				chosenTrain.setDestinationTime(destinationTime);
				chosenTrain.setPrice(price);
				chosenTrain.setTrainName(trainName);
				
				Map<CarriageType, BigDecimal> prices=priceService.getTrainPrice(chosenTrain);
				LocalDate today=LocalDate.now();				
				
				request.getSession(false).setAttribute("train", chosenTrain);
				
				request.getSession(false).setAttribute("requestedTrain",chosenTrain);			
				request.setAttribute("availables",availablePlacesInTrain);
				request.setAttribute("prices", prices);
				request.setAttribute("today", today);	
				request.setAttribute("carriageTypes", CarriageType.values());			
			}
		} catch (ServiceException e) {
			throw new ServletException(e);
		} 
		return CARRIAGE_CHOOSE_PAGE;
	}
}
