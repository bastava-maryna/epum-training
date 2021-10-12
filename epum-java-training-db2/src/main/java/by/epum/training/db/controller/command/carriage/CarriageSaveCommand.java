package by.epum.training.db.controller.command.carriage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import by.epum.training.db.controller.Command;
import by.epum.training.db.controller.util.Utils;
import by.epum.training.db.entity.Carriage;
import by.epum.training.db.service.CarriageService;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.exception.CarriageNotExistException;
import by.epum.training.db.service.exception.CarriageNotUniqueException;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CarriageSaveCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String,String> errors=new HashMap<String,String>();

		Carriage carriage=Utils.carriageFromRequest(request);

		if(carriage.getId()!=null) {
			Carriage currentCarriage=(Carriage) request.getSession(false).getAttribute("currentCarriage");

			if(!isChanged(carriage,currentCarriage )) {
				response.sendRedirect(request.getContextPath()+"/jsp/carriage/list?message=operation.changeNothing");
				return "REDIRECTED";
			}
		}

		try (ServiceProvider sp=getServiceProvider()){
		
			CarriageService service = sp.getCarriageService();
			service.save(carriage);

			request.getSession(false).removeAttribute("currentCarriage");
			request.getSession(false).removeAttribute("errors");

			response.sendRedirect(request.getContextPath()+"/jsp/carriage/list?message=operation.complete");
			return "REDIRECTED";

		} catch(CarriageNotUniqueException e) {
			errors.put("duplicateCarriage", "Carriage with these parameters already exist: "+e.getId());
		} catch(CarriageNotExistException e) {
			System.out.println("in custom not exist exception");//??add smth?
		} catch(ServiceException e) {
			throw new ServletException(e);
		} 

		request.getSession(false).setAttribute("errors", errors);

		response.sendRedirect(request.getContextPath()+"/jsp/carriage/edit");
		return "REDIRECTED";
	}


	private boolean isChanged(Carriage newCarriage,Carriage currentCarriage) {
		if(newCarriage.getTrain().getId().equals(currentCarriage.getTrain().getId())&&
				newCarriage.getCarriageNumber().equals(currentCarriage.getCarriageNumber())&&
				newCarriage.getCarriageType().equals(currentCarriage.getCarriageType())) {
			return true;
		}
		return false;
	}
}
