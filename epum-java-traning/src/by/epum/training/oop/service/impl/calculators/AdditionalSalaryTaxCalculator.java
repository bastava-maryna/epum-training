package by.epum.training.oop.service.impl.calculators;

import java.time.LocalDate;
import java.util.List;

import by.epum.training.oop.entity.Income;
import by.epum.training.oop.entity.TaxPayer;
import by.epum.training.oop.service.exception.ServiceException;
import by.epum.training.oop.entity.TaxData;

public class AdditionalSalaryTaxCalculator extends BaseTaxCalculator{
	
	@Override
	public BaseTaxCalculator calculate(int month, List<Income> incomes, TaxPayer taxPayer) {
		
		if(incomes!=null) {			
			Double monthIncome=0.0;
			Double monthDeduction=0.0;
			
			for(Income in:incomes) {
				monthIncome+=in.getSum();
			}
			
			Double curTaxBase=monthIncome;
			Double curTax=curTaxBase*TaxData.MAJOR_RATE.getValue()/100;//??13% or no  ??check for 0
		
			this.update(monthIncome, monthDeduction, curTax);
			this.setType(incomes.get(0).getIncomeType());
		}
		
		return this;
	}
	
	public static int getChildrenDeductionNumber(List<LocalDate>children) throws ServiceException {
		throw new ServiceException("This method is not used here");
	}
}
