package by.epum.training.db.controller.command.train;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import by.epum.training.db.controller.Command;
import by.epum.training.db.controller.util.Utils;
import by.epum.training.db.entity.Train;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.TrainService;
import by.epum.training.db.service.exception.ServiceException;
import by.epum.training.db.service.exception.TrainNotExistException;
import by.epum.training.db.service.exception.TrainNotUniqueException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TrainSaveCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String,String> errors=new HashMap<String,String>();

		Train currentTrain=(Train)request.getSession(false).getAttribute("currentTrain");
		Train train=Utils.trainFromRequest(request);

		if(train!=null && train.getId()!=null) {
			if(!isChanged(train,currentTrain) ) {
				response.sendRedirect(request.getContextPath()+"/jsp/train/edit?message=operation.changeNothing");
				return "REDIRECTED";
			}
		}

		try (ServiceProvider sp=getServiceProvider()){
			TrainService service = sp.getTrainService();
			service.save(train);

			request.getSession(false).removeAttribute("currentTrain");
			request.getSession(false).removeAttribute("errors");

			response.sendRedirect(request.getContextPath()+"/jsp/train/list?message=operation.complete");
			return "REDIRECTED";

		} catch(TrainNotUniqueException e) {
			errors.put("duplicateTrain", "Train with these parameters already exist: "+e.getId());
		} catch(TrainNotExistException e) {
			errors.put("noTrain", "Train with this id parameter doesnt exist exist: "+e.getId());
		} catch(ServiceException e) {
			throw new ServletException(e.getMessage());
		} catch(Exception e) {
			throw new ServletException(e.getMessage());
		}

		request.getSession(false).setAttribute("errors", errors);

		response.sendRedirect(request.getContextPath()+"/jsp/train/edit");
		return "REDIRECTED";
	}


	private boolean isChanged(Train newTrain, Train initialTrain) {
		if(newTrain.getTrainName().equals(initialTrain.getTrainName())&&
			newTrain.getRoute().getId().equals(initialTrain.getRoute().getId())&&
			newTrain.getDepartureTime().equals(initialTrain.getDepartureTime())&&
			newTrain.getDestinationTime().equals(initialTrain.getDestinationTime())&&
			newTrain.getPrice().equals(initialTrain.getPrice())&&
			newTrain.getScheduleMode().equals(initialTrain.getScheduleMode())&&
			newTrain.getDepartureDate().equals(initialTrain.getDepartureDate())) {
			return false;
		}
		return true;
	}
}
