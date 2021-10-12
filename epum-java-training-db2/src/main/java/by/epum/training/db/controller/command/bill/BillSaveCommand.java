package by.epum.training.db.controller.command.bill;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import by.epum.training.db.controller.Command;
import by.epum.training.db.controller.util.Utils;
import by.epum.training.db.entity.Bill;
import by.epum.training.db.entity.Bill.Status;
import by.epum.training.db.service.BillService;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.exception.BillDublicateEntryException;
import by.epum.training.db.service.exception.BillNotExistException;
import by.epum.training.db.service.exception.BillNotUniqueException;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BillSaveCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String,String> errors=new HashMap<String,String>();

		Bill currentBill=(Bill)request.getSession(false).getAttribute("currentBill");
		Bill bill=Utils.billFromRequest(request);

		//update part
		if(bill!=null) {
			if(!isChanged(currentBill.getUser().getId(), currentBill.getStatus(),bill.getUser().getId(), bill.getStatus())) {
				response.sendRedirect(request.getContextPath()+"/jsp/bill/edit?message=operation.changeNothing");
				return "REDIRECTED";
			}
		}

		try (ServiceProvider sp=getServiceProvider()){
			BillService billService = sp.getBillService();
			
			billService.save(bill);
		
			request.getSession(false).removeAttribute("currentBill");//set it in bill edit
			request.getSession(false).removeAttribute("errors");

			response.sendRedirect(request.getContextPath()+"/jsp/bill/list?message=operation.complete");
			return "REDIRECTED";

		} catch(BillDublicateEntryException e) {  //control if really need these ex
			errors.put("duplicateEntry", "Entry combination are not unique:  "+e.getCause());
		} catch(BillNotUniqueException e) {  
			errors.put("duplicateBill", "Bill with these parameters already exist: "+e.getId());
		} catch(BillNotExistException e) {
			errors.put("notExistBill", "Bill with these parameters doesnt exist");
		} catch(ServiceException e) {
			throw new ServletException(e);
		} 
		
		request.getSession(false).setAttribute("errors", errors);

		response.sendRedirect(request.getContextPath()+"/jsp/bill/edit");
		return "REDIRECTED";
	}


	private boolean isChanged(Long userId, Status status,Long initialUserId, Status initialStatus) {
		if(userId.equals(initialUserId)&&
				status.equals(initialStatus)				) {
			return false;
		}
		return true;
	}
}
