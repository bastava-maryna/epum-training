package by.epum.training.db.controller.command.book;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import by.epum.training.db.controller.Command;
import by.epum.training.db.entity.Bill;
import by.epum.training.db.entity.Bill.Status;
import by.epum.training.db.entity.Carriage;
import by.epum.training.db.entity.CarriageType;
import by.epum.training.db.entity.Train;
import by.epum.training.db.entity.User;
import by.epum.training.db.service.BillService;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.exception.BillDublicateEntryException;
import by.epum.training.db.service.exception.BillNotUniqueException;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BookSaveCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String,String> errors=new HashMap<String,String>();

		Bill bill=billFromRequest(request);

		try (ServiceProvider sp=getServiceProvider()){
			BillService billService = sp.getBillService();

			billService.save(bill);

			request.getSession(false).removeAttribute("errors");
			request.getSession(false).setAttribute("bill", bill);
			
			return "/WEB-INF/jsp/book/final.jsp";
			
		} catch(BillDublicateEntryException e) {  
			errors.put("duplicateEntry", "You alredy have active or paid bill on this train:  "+e.getCause());
		} catch(BillNotUniqueException e) { 
			errors.put("duplicateBook", "Such passenger already have active or paid bill on chosen train");
		} catch(ServiceException e) {
			throw new ServletException(e);
		} 
		
		request.getSession(false).setAttribute("errors", errors);

		response.sendRedirect(request.getContextPath()+"/jsp/book/list");
		return "REDIRECTED";	
	}


	private Bill billFromRequest(HttpServletRequest request) {
		Bill bill = new Bill();

		// if passenger -use user from session	
		User user=(User)request.getSession(false).getAttribute("loginedUser");

		Long carriageId=null;
		Short carriageNumber=null;
		Short place=null;
		BigDecimal cost=null;
		CarriageType carriageType=null;

		try {
			carriageId = Long.valueOf(request.getParameter("carriageId"));
			carriageNumber=Short.valueOf(request.getParameter("carriageNumber"));
			carriageType=CarriageType.valueOf(request.getParameter( "carriageType"));
			place = Short.valueOf(request.getParameter("place"));
			cost=new BigDecimal(request.getParameter("cost"));
		} catch(NumberFormatException e) {}

		Carriage carriage=new Carriage();
		carriage.setId(carriageId);
		carriage.setCarriageNumber(carriageNumber);
		carriage.setCarriageType(carriageType);
		bill.setCarriage(carriage);

		bill.setUser(user);
		bill.setPlace(place);
		bill.setCost(cost);

		Train train=(Train)request.getSession(false).getAttribute("train");
		carriage.setTrain(train);
		bill.setStatus(Status.ACTIVE);
		bill.setCreationTime(LocalDateTime.now());

		return bill;
	}
}
