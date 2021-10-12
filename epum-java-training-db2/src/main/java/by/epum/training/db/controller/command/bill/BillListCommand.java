package by.epum.training.db.controller.command.bill;

import java.io.IOException;
import java.util.List;

import by.epum.training.db.controller.Command;
import by.epum.training.db.entity.Bill;
import by.epum.training.db.entity.Carriage;
import by.epum.training.db.entity.Train;
import by.epum.training.db.entity.User;
import by.epum.training.db.service.BillService;
import by.epum.training.db.service.CarriageService;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.TrainService;
import by.epum.training.db.service.UserService;
import by.epum.training.db.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BillListCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getSession(false).removeAttribute("currentBill");

		try (ServiceProvider sp=getServiceProvider()){
			BillService billService = sp.getBillService();

			List<Bill> bills;
			bills = billService.findAll();

			for(Bill bill:bills) {
				UserService userService = sp.getUserService();
				User user=userService.findById(bill.getUser().getId());
				bill.setUser(user);

				CarriageService carriageService=sp.getCarriageService();
				Carriage carriage=carriageService.findById(bill.getCarriage().getId());
				bill.setCarriage(carriage);

				TrainService trainService=sp.getTrainService();
				Train train=trainService.findById(bill.getCarriage().getTrain().getId());
				carriage.setTrain(train);
			}

			request.setAttribute("bills", bills);

		} catch (ServiceException e) {
			throw new ServletException(e);			
		} 

		return "/WEB-INF/jsp/bill/list.jsp";
	}
}
