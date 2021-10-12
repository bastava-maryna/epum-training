package by.epum.training.db.controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;


public class SessionListener implements HttpSessionListener{
	private static final Logger logger=LogManager.getLogger(SessionListener.class);
	
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		logger.info("Session Created:: ID="+ sessionEvent.getSession().getId());
	 
	}
	
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		logger.info("Session Destroyed:: ID="+ sessionEvent.getSession().getId());
	} 
	    
}
