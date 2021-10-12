package by.epum.training.db.controller.listener;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epum.training.db.dao.connection_pool.ConnectionPool;
import by.epum.training.db.dao.connection_pool.exception.ConnectionPoolException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;




public class ContextManager implements ServletContextListener {
	
	private static final Logger logger=LogManager.getLogger(ContextManager.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		ServletContext context =  event.getServletContext();		
		
		String jdbcDriver = context.getInitParameter("jdbcDriver");
		String jdbcUrl = context.getInitParameter("jdbcUrl");
		String jdbcUser = context.getInitParameter("jdbcUser");
		String jdbcPassword = context.getInitParameter("jdbcPassword");
		int jdbcMinSize = Integer.parseInt( context.getInitParameter("jdbcMinSize"));
		int jdbcMaxSize =Integer.parseInt(  context.getInitParameter("jdbcMaxSize"));
		int jdbcvalidTimeout = Integer.parseInt( context.getInitParameter("jdbcValidTimeout"));
		
		try {
			ConnectionPool.getInstance().init(jdbcDriver , jdbcUrl, jdbcUser, jdbcPassword, jdbcMinSize, jdbcMaxSize, jdbcvalidTimeout);///add parameters
			logger.info("connection pool created with parameters: ", jdbcPassword, jdbcPassword, jdbcPassword, event, context, jdbcDriver, jdbcUrl, jdbcUser, jdbcPassword);
		} catch (ConnectionPoolException e) {
			logger.error("Error connection pool initialization");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ConnectionPool.getInstance().shutdown();
		logger.info("connection pool destroyed");
	}
	

}
