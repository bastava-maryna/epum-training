package by.epum.training.db.controller.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.epum.training.db.entity.Bill;
import by.epum.training.db.entity.Bill.Status;
import by.epum.training.db.entity.Carriage;
import by.epum.training.db.entity.CarriageType;
import by.epum.training.db.entity.Role;
import by.epum.training.db.entity.Route;
import by.epum.training.db.entity.ScheduleMode;
import by.epum.training.db.entity.Station;
import by.epum.training.db.entity.Train;
import by.epum.training.db.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Utils {
	private static int TRAIN_CARRIAGE_LIMIT=15;
	private static int REDIRECT_ID=0;
	
	private static final Map<Integer,String> idToUri=new HashMap<Integer,String>();
	private static final Map<String,Integer> uriToId=new HashMap<String,Integer>();
	
	public static List<Short> getCarriageRange(){	
		List <Short> list=new ArrayList<Short>(TRAIN_CARRIAGE_LIMIT);
		
		for(Short i=1;i<=TRAIN_CARRIAGE_LIMIT;i++) {
			list.add(i);
		}
		
		return list;
	}
	
	public static List<Short> getPlaceRange(CarriageType carriageType){
		int carriageCapacity=carriageType.getCapacity();
		
		List <Short> list=new ArrayList<Short>(carriageCapacity);
		for(Short i=1;i<=carriageCapacity;i++) {
			list.add(i);
		}
		
		return list;
	}
	
	public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
		return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
	}
	
	public static void storeLoginedUser(HttpSession session, User loginedUser) {
		session.setAttribute("loginedUser", loginedUser);
	}
	
	public static User getLoginedUser(HttpSession session) {
		if( session==null || session.getAttribute("loginedUser")==null) {
			return null;
		}
		User loginedUser=(User) session.getAttribute("loginedUser");
		return loginedUser;
	}
	
	public static int storeRedirectAfterUserLoginUrl(HttpSession session, String requestUri) {
		Integer id=uriToId.get(requestUri);
		
		if(id==null) {
			id=REDIRECT_ID++;
			
			uriToId.put(requestUri,id);
			idToUri.put(id,requestUri);
		}
		System.out.println("id="+id);
		return id;	
	}
	
	public static String getRedirectAfterUserLoginUrl(HttpSession session, int redirectId) {
		String url=idToUri.get(redirectId);
		
		if(url!=null) {		
			return url;
		}
		return null;	
	}
	
	
	public static String buildUriWithQueryStringFromRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		String requestUri=req.getRequestURI();
		StringBuffer queryString= new StringBuffer();
		
		Set<String> paramNames=req.getParameterMap().keySet();
		
		if(!paramNames.isEmpty()) {
			queryString.append(requestUri).append("?");
			for(String name:paramNames){
				String value=req.getParameter(name);
				queryString.append(name).append("=").append(value).append('&');
			}
			int length=queryString.length();
		
			queryString.setLength(length-1);
			
			return queryString.toString();
		}
		return requestUri;
	}
	
	

	public static String getUri(HttpServletRequest request) {
		
		String uri = request.getRequestURI();
		String context = request.getContextPath();				//		/rbd	
	        
		int postfixIndex = uri.lastIndexOf(".jsp");
		if(postfixIndex != -1) {
			uri = uri.substring(context.length(), postfixIndex);
		} else {
			uri = uri.substring(context.length());
		}
		return uri;
	}
	
	
	public static Train trainFromRequest(HttpServletRequest request) {
		Train train = new Train();

		Integer trainId = null;
		Integer routeId=null;
		LocalTime departureTime=null;
		LocalTime destinationTime=null;
		BigDecimal price=null;
		Short scheduleModeId=null;
		LocalDate departureDate=null;

		try {
		
			routeId = Integer.valueOf(request.getParameter("routeId"));
			departureTime=LocalTime.parse(request.getParameter("departureTime"),DateTimeFormatter.ofPattern("HH:mm"));
			destinationTime=LocalTime.parse(request.getParameter("destinationTime"),DateTimeFormatter.ofPattern("HH:mm"));
			price=new BigDecimal(request.getParameter("price"));
			departureDate=LocalDate.parse(request.getParameter("departureDate"));
			scheduleModeId = Short.valueOf(request.getParameter("scheduleModeId"));
			trainId = Integer.valueOf(request.getParameter("trainId"));
		} catch(NumberFormatException e) {}

		if(trainId!=null ) {
			train.setId(trainId);
		}

		train.setTrainName(request.getParameter("trainName"));
		train.setDepartureTime(departureTime);
		train.setDestinationTime(destinationTime);
		train.setDepartureDate(departureDate);
		train.setPrice(price);

		ScheduleMode scheduleMode=new ScheduleMode(); 
		scheduleMode.setId(scheduleModeId);
		train.setScheduleMode(scheduleMode);

		Route route =new Route();
		route.setId(routeId);
		train.setRoute(route); 

		return train;
	}
	
	public static Route routeFromRequest(HttpServletRequest request) {
		Route route = new Route();

		Integer routeId = null;
		Integer departureId=null;
		Integer destinationId=null;

		try {
			departureId=Integer.valueOf(request.getParameter("departureStationId"));
			destinationId=Integer.valueOf(request.getParameter("destinationStationId"));
			routeId = Integer.valueOf(request.getParameter("routeId"));
		} catch(NumberFormatException e) {}

		if(routeId!=null ) {
			route.setId(routeId);
		}

		Station departure = new Station();
		departure.setId(departureId);
		
		Station destination = new Station();
		destination.setId(destinationId);
		
		route.setDeparture(departure);
		route.setDestination(destination);

		return route;
	}	
	
	public static Carriage carriageFromRequest(HttpServletRequest request) {
		Carriage carriage = new Carriage();

		Long carriageId=null;
		Integer trainId = null;
		Short carriageNumber=null;
		String carriageType=null;
		try {
			trainId=Integer.valueOf(request.getParameter("trainId"));
			carriageNumber=Short.valueOf(request.getParameter("carriageNumber"));
			carriageType=request.getParameter("carriageType");
			carriageId = Long.valueOf(request.getParameter("carriageId"));
		} catch(NumberFormatException e) {}

		if(carriageId!=null ) {
			carriage.setId(carriageId);
		}

		carriage.setCarriageNumber(carriageNumber);	
		carriage.setCarriageType(CarriageType.valueOf(carriageType));
		
		Train train = new Train();
		train.setId(trainId);
		carriage.setTrain(train);
		
		return carriage;
	}	
	
	public static User userFromRequest(HttpServletRequest request) {
		User user = new User();

		String lastName=request.getParameter("lastName");
        String firstName=request.getParameter("firstName");
        String passport=request.getParameter("passport");
        String login=request.getParameter("login");
        String password=request.getParameter("password");
        //String passwordConfirmed=request.getParameter("passwordConfirm");
        String email=request.getParameter("email");
        Role role=null;
		Long userId=null;
        try {
			userId=Long.valueOf(request.getParameter("userId"));
		} catch(NumberFormatException e) {}
        
        try {
			role=Role.valueOf(request.getParameter("role"));
		} catch(NumberFormatException e) {}

		if(userId!=null ) {
			user.setId(userId);
		}
	
		user.setLastName(lastName);
		user.setFirstName(firstName);
		user.setPassport(passport);
		user.setLogin(login);
		user.setPassword(password);
		user.setRole(role);
		user.setEmail(email);
		
		return user;
	}	
	
	public static Bill billFromRequest(HttpServletRequest request) {
		Bill bill = new Bill();

		Long billId = null;	
		Long userId=null;
		Long carriageId=null;
		Short carriageNumber=null;
		Short place=null;
		BigDecimal cost=null;
		LocalDateTime creationTime=null;
		Status status=null;
		try {
			userId = Long.valueOf(request.getParameter("userId"));
			carriageId = Long.valueOf(request.getParameter("carriageId"));
			carriageNumber = Short.valueOf(request.getParameter("carriageNumber"));
			place = Short.valueOf(request.getParameter("place"));
			cost=new BigDecimal(request.getParameter("cost"));
			creationTime=LocalDateTime.parse(request.getParameter("creationTime"));
			status=Status.valueOf(request.getParameter("status"));
			billId = Long.valueOf(request.getParameter("billId"));
		} catch(NumberFormatException e) {}

		if(billId!=null ) {
			bill.setId(billId);
		}

		User user=new User();
		user.setId(userId);
		user.setLastName(request.getParameter("lastName"));
		user.setFirstName(request.getParameter("firstName"));
		user.setPassport(request.getParameter("passport"));
		bill.setUser(user);
		
		Carriage carriage=new Carriage();
		carriage.setId(carriageId);
		carriage.setCarriageNumber(carriageNumber);
		bill.setCarriage(carriage); 
		
		Train train=new Train();
		train.setTrainName(request.getParameter("trainName"));
		carriage.setTrain(train);
		
		bill.setPlace(place);
		bill.setCost(cost);
		bill.setStatus(status);
		bill.setCreationTime(creationTime);
		
		return bill;
	}	
}
