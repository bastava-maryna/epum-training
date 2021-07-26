package by.epum.training.oop.service.impl.calculators;

import java.time.LocalDate;
import java.util.List;

import by.epum.training.oop.entity.Income;
import by.epum.training.oop.entity.IncomeType;
import by.epum.training.oop.entity.TaxPayer;
import by.epum.training.oop.service.exception.ServiceException;


public abstract class BaseTaxCalculator {	
	private IncomeType type;
	private Double income;
	private Double deduction;
	private Double tax;

	
	public BaseTaxCalculator() {
		this.type=null;
		this.income = 0.0;
		this.deduction = 0.0;
		this.tax = 0.0;
	}
	
	
	public BaseTaxCalculator(IncomeType type, Double income, Double deduction, Double tax) {	
		this.type=type;
		this.income = income;
		this.deduction = deduction;
		this.tax = tax;
	}
	
	public abstract BaseTaxCalculator calculate(int month, List<Income> incomes, TaxPayer taxPayer) throws ServiceException;
		
	public void clear() {};
	
	public void update(Double plusIncome, Double plusDeduction, Double plusTax) {	
		
		income+=plusIncome;
		deduction+=plusDeduction;
		tax+=plusTax;
	}

	public IncomeType getType() {
		return type;
	}

	public void setType(IncomeType type) {
		this.type = type;
	}

	public Double getIncome() {
		return income;
	}

	public Double getDeduction() {
		return deduction;
	}

	public Double getTax() {
		return tax;
	}

	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" [type=" + type + ", income=" + income +  ", deduction="
					+ deduction + ", tax=" + tax + "]";	
	}
	
	public static int getChildrenDeductionNumber(List<LocalDate>children) throws ServiceException {
		int result=0;
		
		if(children!=null) {
			for(LocalDate d:children) {
				if(d.plusYears(18).getYear()>= LocalDate.now().getYear() 
						&& d.getMonthValue()>=LocalDate.now().getMonthValue() ){
					result++;
				}
			}
		}
		
		return result;
	}
}


