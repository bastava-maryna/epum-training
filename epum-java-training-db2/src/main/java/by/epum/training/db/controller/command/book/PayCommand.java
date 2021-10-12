package by.epum.training.db.controller.command.book;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import by.epum.training.db.controller.Command;
import by.epum.training.db.entity.Bill;
import by.epum.training.db.entity.Bill.Status;
import by.epum.training.db.service.BillService;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.exception.BillNotExistException;
import by.epum.training.db.service.exception.BillNotUniqueException;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PayCommand implements Command {
	private static final String  PAY_NOW="now";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String,String> errors=new HashMap<String,String>();
		String whenPay=request.getParameter("pay");

		Bill bill=(Bill)request.getSession().getAttribute("bill");

		if(bill!=null) {
			if(whenPay.equals(PAY_NOW)) {
				bill.setStatus(Status.PAID);		
			}else {
				bill.setStatus(Status.INVALID);
			}

			try(ServiceProvider sp=getServiceProvider()) {
				BillService billService=sp.getBillService();
				billService.updateBillStatus(bill.getId(), bill.getStatus());
				request.setAttribute("pay", whenPay);

				return "/WEB-INF/jsp/book/final.jsp";
			} catch(BillNotUniqueException e) {
				errors.put("duplicateBill", "Bill with these parameters already exist: "+e.getId());
			} catch(BillNotExistException e) {
				errors.put("notExistBill", "Bill with this id parameter doesnt exist exist: "+e.getId());
			} catch(ServiceException e) {
				throw new ServletException(e.getMessage());
			} catch(Exception e) {
				throw new ServletException(e.getMessage());
			}
		}

		request.getSession(false).setAttribute("errors", errors);
		response.sendRedirect(request.getContextPath()+"/jsp/book/final"
				);
		return "REDIRECTED";
	}
}