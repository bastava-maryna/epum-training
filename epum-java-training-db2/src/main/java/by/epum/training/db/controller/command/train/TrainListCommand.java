package by.epum.training.db.controller.command.train;

import java.io.IOException;
import java.util.List;

import by.epum.training.db.controller.Command;
import by.epum.training.db.entity.Train;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.TrainService;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TrainListCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getSession(false).removeAttribute("currentTrain");	

		try (ServiceProvider sp=getServiceProvider()){
			TrainService trainService = sp.getTrainService();

			List<Train> trains = trainService.findAll();

			request.setAttribute("trains", trains);
			
		} catch (ServiceException e) {
			throw new ServletException(e);		
		} 

		return "/WEB-INF/jsp/train/list.jsp";
		
	}
}
