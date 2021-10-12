package by.epum.training.db.dao;

import by.epum.training.db.dao.impl.MySQLBillDAO;
import by.epum.training.db.dao.impl.MySQLCarriageDAO;
import by.epum.training.db.dao.impl.MySQLCarriagePriceDAO;
import by.epum.training.db.dao.impl.MySQLRouteDAO;
import by.epum.training.db.dao.impl.MySQLScheduleModeDAO;
import by.epum.training.db.dao.impl.MySQLStationDAO;
import by.epum.training.db.dao.impl.MySQLTrainDAO;
import by.epum.training.db.dao.impl.MySQLUserDAO;

public final class MySQLDAOProvider extends DAOProvider{

	public MySQLDAOProvider() {};

	public StationDAO getStationDAO() {
		return new MySQLStationDAO(getConnection());
	}

	public RouteDAO getRouteDAO() {
		return new  MySQLRouteDAO(getConnection());
	}

	@Override
	public BillDAO getBillDAO() {
		return new MySQLBillDAO(getConnection());
	}

	@Override
	public CarriageDAO getCarriageDAO() {
		return new MySQLCarriageDAO(getConnection());
	}

	@Override
	public TrainDAO getTrainDAO() {
		return new MySQLTrainDAO(getConnection());
	}

	@Override
	public UserDAO getUserDAO() {
		return new MySQLUserDAO(getConnection());
	}
	@Override
	public ScheduleModeDAO getScheduleModeDAO() {
		return new MySQLScheduleModeDAO(getConnection());
	}
	
	@Override
	public CarriagePriceDAO getCarriagePriceDAO() {
		return new MySQLCarriagePriceDAO();
	}
}



