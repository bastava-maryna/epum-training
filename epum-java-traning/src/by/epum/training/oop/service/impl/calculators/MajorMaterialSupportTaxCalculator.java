package by.epum.training.oop.service.impl.calculators;

import java.time.LocalDate;
import java.util.List;

import by.epum.training.oop.entity.Income;
import by.epum.training.oop.entity.TaxData;
import by.epum.training.oop.entity.TaxPayer;
import by.epum.training.oop.service.exception.ServiceException;


public class MajorMaterialSupportTaxCalculator extends BaseTaxCalculator {

	private static Double appliedDeduction=0.0;

	public static void updateAppliedDeduction(Double plusValue) {
		appliedDeduction = appliedDeduction+plusValue;
	}
	
	@Override
	public void clear() {
		appliedDeduction = 0.0;
	}

	@Override
	public BaseTaxCalculator calculate(int month, List<Income> incomes, TaxPayer taxPayer) {
		
		if(incomes!=null) {
			Double monthIncome=0.0;
			Double monthDeduction=0.0;
			
			for(Income in:incomes) {
				monthIncome+=in.getSum();
			}
			
			Double restDeduction=TaxData.MAJOR_MATERIAL_SUPPORT_LIMIT.getValue()- appliedDeduction;
			
			if(restDeduction>0) {
				if(monthIncome>=restDeduction) {
					monthDeduction=restDeduction;
				}else {
					monthDeduction=monthIncome;
				}
			}
			
			Double curTaxBase=monthIncome-monthDeduction;
			Double curTax=curTaxBase*TaxData.MAJOR_RATE.getValue()/100;
		
			this.update(monthIncome, monthDeduction, curTax);
			this.setType(incomes.get(0).getIncomeType());
			updateAppliedDeduction(monthIncome); 
		}
		
		return this;
	}
	
	public static int getChildrenDeductionNumber(List<LocalDate>children) throws ServiceException {
		throw new ServiceException("This method is not used here");
	}
}
