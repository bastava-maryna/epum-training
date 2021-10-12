package by.epum.training.db.controller;

import java.io.IOException;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




public class Controller extends HttpServlet{

	private static final long serialVersionUID = -2844907380229498936L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 processRequest(req, resp);
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

	private void processRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		Command command=null;
		String url=getPath(request);

		command = ServletCommandProvider.getCommand(url);

		String path="";
		if(command!=null) {
			try {
				path = command.execute(request, response);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		if(!path.equals("REDIRECTED")) {
			System.out.println(" not redirected, wll be dispatched to="+path);
			request.getRequestDispatcher(path).forward(request, response);
		}
	}


	private String getPath(HttpServletRequest request) {
		String uri = request.getRequestURI();

		System.out.println("URI from input req="+uri);
		
		String path=uri.substring(request.getContextPath().length());
		System.out.println("path from input req="+path);
		return path;
	}

}
