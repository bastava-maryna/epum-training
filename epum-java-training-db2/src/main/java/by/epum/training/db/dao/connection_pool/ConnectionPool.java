package by.epum.training.db.dao.connection_pool;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

import by.epum.training.db.dao.connection_pool.exception.ConnectionPoolException;

public class ConnectionPool {
	private String jdbcUrl;
	private String user;
	private String password;
	private int validTimeout;
	private int maxSize;

	private final static ConnectionPool instance=new ConnectionPool();
	//null is forbidden, bec cannot be reliably distinguished from absence
	private Queue<Connection> availableConnections=new ConcurrentLinkedQueue<Connection>();
	private Set<Connection> usedConnections=new ConcurrentSkipListSet<Connection>(Comparator.comparingInt(Object::hashCode));


	private ConnectionPool() {}

	public static ConnectionPool getInstance() {
		return instance;
	};

	public void init(String driverClass,String jdbcUrl, String user, String password, int minSize, int maxSize, int validTimeout) throws ConnectionPoolException {
		
		try {
			shutdown();
			Driver driver = (Driver)Class.forName(driverClass).getConstructor().newInstance();
			DriverManager.registerDriver(driver);
			this.jdbcUrl=jdbcUrl;
			this.user=user;
			this.password=password;
			this.maxSize=maxSize;
			this.validTimeout=validTimeout;
			for(int i=0;i<minSize;i++) {
				availableConnections.add(createConnection());
			}
		} catch (SQLException |ClassNotFoundException|InstantiationException | IllegalAccessException | IllegalArgumentException |InvocationTargetException |NoSuchMethodException|SecurityException e) {
			throw new ConnectionPoolException(e);
		}

	}


	public Connection getConnection() throws ConnectionPoolException {
		Connection connection=null;
		while(connection==null) {
			
			try {
				connection=availableConnections.poll();
				if(connection!=null) {
					if(!connection.isValid(validTimeout)) {
						try {
							connection.close();
						}catch(SQLException e) {}			
						connection=null;
					}
				}else {
					if(usedConnections.size()<maxSize) {

						connection=createConnection();
					}else {
						throw new ConnectionPoolException("Connection max size is reached");
					}
				}
			}catch(SQLException e) {
				throw new ConnectionPoolException(e);
			}
		}
		System.out.println("availableConnection size="+availableConnections.size()+";  usedConnection size="+usedConnections.size());
		usedConnections.add(connection);
		return (connection);
	}

	private Connection createConnection() throws SQLException {   
		return DriverManager.getConnection(jdbcUrl,user,password);
	}
	//
	void freeConnection(Connection connection) {
		try {
			connection.clearWarnings();
			usedConnections.remove(connection);
			availableConnections.add(connection);
			
		} catch (SQLException e) {
			try {
				connection.close();
			}catch(SQLException ex) {}
		}
	}
	
	public void shutdown() {
		synchronized(availableConnections) {
			synchronized(usedConnections) {
				usedConnections.addAll(availableConnections);
				availableConnections.clear();
				for(Connection con:usedConnections) {
					try {
						con.close();
					}catch(SQLException e) {}
					usedConnections.clear();
				}
			}
		}
	}
}
