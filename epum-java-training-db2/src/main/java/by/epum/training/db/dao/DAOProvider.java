package by.epum.training.db.dao;

import java.sql.Connection;

public abstract class DAOProvider {
	
	 // DAO list supported by fabric
	  public static final int MY_SQL = 1;
	
	  // concrete fabrics should implement methods for each available DAO
	  public abstract BillDAO getBillDAO();
	  public abstract CarriageDAO getCarriageDAO();
	  public abstract RouteDAO getRouteDAO();
	  public abstract StationDAO getStationDAO();
	  public abstract TrainDAO getTrainDAO();
	  public abstract UserDAO getUserDAO();
	  public abstract ScheduleModeDAO getScheduleModeDAO();
	  public abstract CarriagePriceDAO getCarriagePriceDAO();
	 
	  
	  private Connection connection; 
	  
	  protected Connection getConnection() {
		  return connection;
	  }

	  public void setConnection(Connection connection) {
		  this.connection = connection;
	  }
	  
	  
	  public static DAOProvider getDAOProvider(int whichFactory){//??ex
	 
	    switch (whichFactory) {
	      case MY_SQL:
	          return new MySQLDAOProvider();
	      
	      //...
	      default           :
	          return null;
	    }
	  }
}

