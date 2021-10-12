package by.epum.training.db.controller.command.carriage;

import java.io.IOException;
import java.util.List;

import by.epum.training.db.controller.Command;
import by.epum.training.db.controller.util.Utils;
import by.epum.training.db.entity.Carriage;
import by.epum.training.db.entity.CarriageType;
import by.epum.training.db.service.CarriageService;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CarriageEditCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer trainId=null;
		Long carriageId=null;
		try {
			trainId=Integer.valueOf(request.getParameter("trainId"));
			carriageId=Long.valueOf(request.getParameter("carriageId"));
		}catch (NumberFormatException e) {}

		try (ServiceProvider sp=getServiceProvider()){
			Carriage carriage=null;
		
			if(carriageId!=null) {
				carriage=Utils.carriageFromRequest(request);

				request.getSession(false).setAttribute("currentCarriage", carriage);
			}

			CarriageService carriageService = sp.getCarriageService();

			if(request.getParameterMap().containsKey("add")) {
				//those numbers which can be added
				List<Short> availableCarriageNumbers=carriageService.findAvailableToAddNumbers(trainId);
				request.setAttribute("availableCarriageNumbers",availableCarriageNumbers);
			}

			if(request.getParameterMap().containsKey("delete")) {
				//those numbers which can be deleted because of they are empty for now
				List<Carriage> emptyCarriagesInTrain=carriageService.findEmpty(trainId);
				request.setAttribute("emptyCarriagesInTrain",emptyCarriagesInTrain);
			}

			request.setAttribute("carriageTypes", CarriageType.values());
			request.setAttribute("trainId", trainId);

		} catch (ServiceException e) {
			throw new ServletException(e.getMessage());
		} 
		
		return "/WEB-INF/jsp/carriage/edit.jsp";
	}
}
