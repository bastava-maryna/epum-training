package by.epum.training.db.controller;

import java.io.IOException;

import by.epum.training.db.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public interface Command {
	 String execute(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException, IOException;
	 
	 default ServiceProvider getServiceProvider() {   
	    	return new ServiceProvider();
	 }

}
