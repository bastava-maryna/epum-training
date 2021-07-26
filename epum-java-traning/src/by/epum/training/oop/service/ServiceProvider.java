package by.epum.training.oop.service;

import by.epum.training.oop.service.impl.IncomeServiceImpl;
import by.epum.training.oop.service.impl.TaxCalculatorServiceImpl;
import by.epum.training.oop.service.impl.TaxPayerServiceImpl;

public class ServiceProvider {
	private static final ServiceProvider instance=new ServiceProvider();
	
	private ServiceProvider() {}

	public static ServiceProvider getInstance() {
		return instance;
	};
	
	private TaxPayerService taxPayerService=new TaxPayerServiceImpl();
	private IncomeService incomeService=new IncomeServiceImpl();
	private TaxCalculatorService taxCalculatorService=new TaxCalculatorServiceImpl();

	public TaxPayerService getTaxPayerService() {
		return taxPayerService;
	}

	public IncomeService getIncomeService() {
		return incomeService;
	}

	public TaxCalculatorService getTaxCalculatorService() {
		return taxCalculatorService;
	}
	
}
