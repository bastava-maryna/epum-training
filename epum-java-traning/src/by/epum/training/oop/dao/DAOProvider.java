package by.epum.training.oop.dao;

import by.epum.training.oop.dao.impl.FileIncomeDAO;
import by.epum.training.oop.dao.impl.FileTaxDataDAO;
import by.epum.training.oop.dao.impl.FileTaxPayerDAO;

public final class DAOProvider {
	
	private static final DAOProvider instance=new DAOProvider();
	
	private TaxPayerDAO taxPayerDAO=new FileTaxPayerDAO();
	private IncomeDAO incomeDAO=new FileIncomeDAO();
	private TaxDataDAO taxDataDAO=new FileTaxDataDAO();
	
	private DAOProvider() {}

	public TaxPayerDAO getTaxPayerDAO() {
		return taxPayerDAO;
	}

	public IncomeDAO getIncomeDAO() {
		return incomeDAO;
	}

	public TaxDataDAO getTaxDataDAO() {
		return taxDataDAO;
	}

	public static DAOProvider getInstance() {
		return instance;
	};
	
	
	
	

}
