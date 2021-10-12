package by.epum.training.db.controller.command.route;

import java.io.IOException;
import java.util.Map;

import by.epum.training.db.controller.Command;
import by.epum.training.db.controller.util.Utils;
import by.epum.training.db.controller.util.Validator;
import by.epum.training.db.entity.Route;
import by.epum.training.db.service.RouteService;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.exception.RouteNotExistException;
import by.epum.training.db.service.exception.RouteNotUniqueException;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RouteSaveCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Route currentRoute=(Route)request.getSession(false).getAttribute("currentRoute");
		Route route=Utils.routeFromRequest(request);

		Map<String,String> errors=null;
		errors=Validator.validateRouteEditForm(route.getDeparture().getId(),route.getDestination().getId());
		
		if(errors.isEmpty() ) {
			if(route.getId()!=null) {
				if(!isChanged(route, currentRoute)){
					response.sendRedirect(request.getContextPath()+"/jsp/route/list?message=operation.changeNothing");
					return "REDIRECTED";
				}
			}

			try (ServiceProvider sp=getServiceProvider()){
				RouteService service = sp.getRouteService();

				service.save(route);

				request.getSession(false).removeAttribute("currentRoute");
				request.getSession(false).removeAttribute("errors");

				response.sendRedirect(request.getContextPath()+"/jsp/route/list?message=operation.complete");
				return "REDIRECTED";
				
			} catch(RouteNotUniqueException e) {
				errors.put("duplicateRoute", "Route with these parameters already exist: "+e.getId());
			} catch(RouteNotExistException e) {
				errors.put("noRoute", "Route with these parameters does no exist. ");
			} catch(ServiceException e) {
				throw new ServletException(e);
			} catch(Exception e) {
				throw new ServletException(e);
			}
		}
		request.getSession(false).setAttribute("errors", errors);
	
		response.sendRedirect(request.getContextPath()+"/jsp/route/edit");
		return "REDIRECTED";
	}

	private boolean isChanged(Route newRoute, Route currentRoute) {
		if(currentRoute.getDeparture().getId().equals(newRoute.getDeparture().getId()) 
				&&currentRoute.getDestination().getId().equals(newRoute.getDestination().getId())) {
			return false;
		}
		return true;
	}

}
