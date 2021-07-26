package by.epum.training.oop.service.impl.calculators;

import java.util.List;

import by.epum.training.oop.entity.Income;
import by.epum.training.oop.entity.TaxData;
import by.epum.training.oop.entity.TaxPayer;
import by.epum.training.oop.service.exception.ServiceException;


public class MajorSalaryTaxCalculator extends BaseTaxCalculator{

	private static Double availableDeduction;
	private static Double appliedDeduction;
	
	public static void updateAvailableDeduction(int childrenDeductionNumber) {
		if(childrenDeductionNumber>1) {
			availableDeduction+=childrenDeductionNumber*TaxData.STANDARD_CHILDREN.getValue();
		}else {	
			availableDeduction+=childrenDeductionNumber*TaxData.STANDARD_CHILD.getValue();
		}
		
		availableDeduction+=TaxData.STANDARD_PERSON.getValue();
	}
	
	public static void updateAppliedDeduction(Double usedDeduction) {
		appliedDeduction = appliedDeduction+usedDeduction;
	}
	
	@Override
	public void clear() {
		availableDeduction = 0.0;
		appliedDeduction = 0.0;
	}
	
	@Override
	public BaseTaxCalculator calculate(int month, List<Income> incomes, TaxPayer taxPayer) throws ServiceException {
			
		if(incomes!=null) {
			int childrenDeductionNumber=0;
				
			if(!taxPayer.getChildren().isEmpty()) {
				childrenDeductionNumber=getChildrenDeductionNumber(taxPayer.getChildren());
			}
				
			System.out.println("children= "+childrenDeductionNumber);
				
			updateAvailableDeduction(childrenDeductionNumber);
				
			Double monthIncome=0.0;
			Double monthDeduction=0.0;
				
			for(Income in:incomes) {
				monthIncome+=in.getSum();
			}
				
			Double restDeduction=availableDeduction-appliedDeduction;
				
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
			updateAppliedDeduction(monthDeduction);
		}
			
		return this;
	}	

}
