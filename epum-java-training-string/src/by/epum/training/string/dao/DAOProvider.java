package by.epum.training.string.dao;

import by.epum.training.string.dao.impl.FileBookDAO;

public class DAOProvider {
	private static final DAOProvider instance=new DAOProvider();
	
	private DAOProvider() {}
	
	private BookDAO bookDAO=new FileBookDAO();

	public static DAOProvider getInstance() {
		return instance;
	}

	public BookDAO getBookDAO() {
		return bookDAO;
	};
	
	
}
