package by.epum.training.oop.dao.impl;


import java.util.List;

import by.epum.training.oop.entity.Income;
import by.epum.training.oop.entity.TaxPayer;

public class TemporaryStorageImpl implements TemporaryStorage{
	
	private final static TemporaryStorage instance=new TemporaryStorageImpl();
	
	private TemporaryStorageImpl() {};
	
	public static TemporaryStorage getInstance() {
		return instance;
	}

	private int year;
	private TaxPayer taxPayer;
	private List<Income>incomes;
	

	@Override
	public TaxPayer getTaxPayer() {
		return taxPayer;
	}
	
	@Override
	public List<Income> getIncomes() {
		return incomes;
	}

	@Override
	public int getYear() {
		return year;
	}
	
	@Override
	public void setYear(int year) {
		this.year = year;
	}
	
	@Override
	public void saveTaxPayer(TaxPayer taxPayer) {
		System.out.println("Before saveTaxPayer"+ this.taxPayer);
		if(this.taxPayer==null || !this.taxPayer.getId().equals(taxPayer.getId())) {
			this.taxPayer = taxPayer;
			incomes=null;
		}	
		System.out.println("After saveTaxPayer"+ this.taxPayer);
	}

	public void saveIncomes(List<Income> incomes) {
		System.out.println("Before saveIncomes"+ this.incomes);
		this.incomes = incomes;
		System.out.println("After saveIncomes"+ this.incomes);
	}
	
}
