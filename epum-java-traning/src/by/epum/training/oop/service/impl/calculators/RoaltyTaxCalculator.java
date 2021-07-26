package by.epum.training.oop.service.impl.calculators;

import java.time.LocalDate;
import java.util.List;

import by.epum.training.oop.entity.Income;
import by.epum.training.oop.entity.RoyaltyIncome;
import by.epum.training.oop.entity.TaxData;
import by.epum.training.oop.entity.TaxPayer;
import by.epum.training.oop.service.exception.ServiceException;


public class RoaltyTaxCalculator extends BaseTaxCalculator {

	@Override
	public BaseTaxCalculator calculate(int month, List<Income> incomes, TaxPayer taxPayer) {

		if(incomes!=null) {
			for(Income in:incomes) {
				Double monthDeduction=0.0;
				Double curIncome=in.getSum();
				
				int curTaxRate=TaxData.valueOf(((RoyaltyIncome) in).getRoyaltyType().toString()).getValue();
				Double curTaxBase=curIncome;
				
				//System.out.println(curTaxRate+"%");
				Double curTax=curTaxBase*curTaxRate/100;
				this.update(curIncome, monthDeduction, curTax);
			}
		
			this.setType(incomes.get(0).getIncomeType());
		}
		
		return this;
	}
	
	public static int getChildrenDeductionNumber(List<LocalDate>children) throws ServiceException {
		throw new ServiceException("This method is not used here");
	}
}