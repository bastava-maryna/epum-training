package by.epum.training.db.controller.command.carriage;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import by.epum.training.db.controller.Command;
import by.epum.training.db.service.CarriageService;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.exception.CarriageNotExistException;
import by.epum.training.db.service.exception.CarriageNotUniqueException;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CarriageDeleteCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String,String> errors=new HashMap<String,String>();
		Long carriageId=null;

		try {
			carriageId = Long.valueOf(request.getParameter("carriageId"));
		} catch(NumberFormatException e) {}


		if(carriageId!=null) {
			try (ServiceProvider sp=getServiceProvider()){
			
				CarriageService service = sp.getCarriageService();
				service.delete(carriageId);

				request.getSession(false).removeAttribute("currentCarriage");
				request.getSession(false).removeAttribute("errors");

				response.sendRedirect(request.getContextPath()+"/jsp/carriage/list?message="+URLEncoder.encode("Operation  complete successfully.", StandardCharsets.UTF_8));
				return "REDIRECTED";

			} catch(CarriageNotUniqueException e) {
				errors.put("duplicateCarriage", "Carriage with these parameters already exist: "+e.getId());
			} catch(CarriageNotExistException e) {
				errors.put("noCarriage", "Carriage doesnt exist");
			} catch(ServiceException e) {
				throw new ServletException(e);
			}

		}else {
			errors.put("noCarriage", "Carriage doesnt exist");
		}
		
		request.getSession(false).setAttribute("errors", errors);

		response.sendRedirect(request.getContextPath()+"/jsp/carriage/edit?message="+URLEncoder.encode("Errors:", StandardCharsets.UTF_8));
		return "REDIRECTED";
	}
}
