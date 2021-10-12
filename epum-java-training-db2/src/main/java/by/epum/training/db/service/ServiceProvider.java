package by.epum.training.db.service;

import java.sql.Connection;

import by.epum.training.db.dao.DAOProvider;
import by.epum.training.db.dao.connection_pool.ConnectionPool;
import by.epum.training.db.dao.connection_pool.exception.ConnectionPoolException;
import by.epum.training.db.service.exception.ServiceException;
import by.epum.training.db.service.impl.BillServiceImpl;
import by.epum.training.db.service.impl.CarriageServiceImpl;
import by.epum.training.db.service.impl.PriceServiceImpl;
import by.epum.training.db.service.impl.RouteServiceImpl;
import by.epum.training.db.service.impl.ScheduleModeServiceImpl;
import by.epum.training.db.service.impl.StationServiceImpl;
import by.epum.training.db.service.impl.TrainServiceImpl;
import by.epum.training.db.service.impl.TransactionImpl;
import by.epum.training.db.service.impl.UserServiceImpl;



public final class ServiceProvider  implements AutoCloseable{
	private DAOProvider daoProvider;
	private Connection connection;

	public Connection getConnection() throws ServiceException {
		if(connection==null) {
			try {
				connection=ConnectionPool.getInstance().getConnection();
		
			} catch (ConnectionPoolException e) {
				throw new ServiceException(e);
			} }
		return connection;
	}

	protected DAOProvider getDaoProvider() throws ServiceException {
		if(daoProvider==null) {
			daoProvider=  DAOProvider.getDAOProvider(DAOProvider.MY_SQL);
			daoProvider.setConnection(getConnection());
		}
		return daoProvider;
	}


	public Transaction getTransaction() throws ServiceException {
		TransactionImpl transaction = new TransactionImpl();
		transaction.setConnection(getConnection());
		return transaction;
	}

	public UserService getUserService() throws ServiceException {
		UserServiceImpl userService= new UserServiceImpl();	
		userService.setUserDAO(getDaoProvider().getUserDAO());
		userService.setTransaction(getTransaction());
		
		return userService;		
	}

	public BillService getBillService() throws ServiceException {
		BillServiceImpl billService= new BillServiceImpl();
		billService.setBillDAO(getDaoProvider().getBillDAO());
		billService.setTransaction(getTransaction());
		
		return billService;		
	}

	public RouteService getRouteService() throws ServiceException {
		RouteServiceImpl routeService= new RouteServiceImpl();
		routeService.setRouteDAO(getDaoProvider().getRouteDAO());
		routeService.setTransaction(getTransaction());
		
		return routeService;
	}

	public StationService getStationService() throws ServiceException {
		StationServiceImpl stationService= new StationServiceImpl();
		stationService.setStationDAO(getDaoProvider().getStationDAO());
		stationService.setTransaction(getTransaction());
		
		return stationService;
	}

	public CarriageService getCarriageService() throws ServiceException {
		CarriageServiceImpl carriageService= new CarriageServiceImpl();
		carriageService.setCarriageDAO(getDaoProvider().getCarriageDAO());
		carriageService.setTransaction(getTransaction());
		
		return carriageService;
	}

	public TrainService getTrainService() throws ServiceException {
		TrainServiceImpl trainService= new TrainServiceImpl();
		trainService.setTrainDAO(getDaoProvider().getTrainDAO());
		trainService.setTransaction(getTransaction());
		
		return trainService;
	}

	public ScheduleModeService getScheduleModeService() throws ServiceException {
		ScheduleModeServiceImpl scheduleModeService= new ScheduleModeServiceImpl();
		scheduleModeService.setScheduleModeDAO(getDaoProvider().getScheduleModeDAO());
		scheduleModeService.setTransaction(getTransaction());
		
		return scheduleModeService;
	}

	public PriceService getPriceService() throws ServiceException {
		PriceServiceImpl priceService= new PriceServiceImpl();
		priceService.setCarriagePriceDAO(getDaoProvider().getCarriagePriceDAO());

		return priceService;
	}
	
	@Override
	public void close()  {
		try {			
			connection.close();
			connection=null;
		}catch (Exception e){}
	}
}
