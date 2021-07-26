package by.epum.training.oop.service.impl.calculators;

import java.util.List;

import by.epum.training.oop.entity.Income;
import by.epum.training.oop.entity.TaxData;
import by.epum.training.oop.entity.TaxPayer;
import by.epum.training.oop.service.exception.ServiceException;


public class ChildrenCampVaucherTaxCalculator extends BaseTaxCalculator {
	
	private static Double appliedDeduction=0.0;

	public static void updateAppliedDeduction(Double plusValue) {
		appliedDeduction = appliedDeduction+plusValue;
	}
	
	@Override
	public void clear() {
		appliedDeduction = 0.0;
	}
	
	@Override
	public BaseTaxCalculator calculate(int month, List<Income> incomes, TaxPayer taxPayer) throws ServiceException {
			
		if(incomes!=null) {
			int childrenDeductionNumber=0;
			
			if(!taxPayer.getChildren().isEmpty()) {
				childrenDeductionNumber=getChildrenDeductionNumber(taxPayer.getChildren());
			}
			
			Double monthIncome=0.0;
			Double monthDeduction=0.0;
			
			for(Income in:incomes) {
				monthIncome+=in.getSum();
			}
			
			Double restDeduction=TaxData.CHILDREN_CAMP_VOUCHER_LIMIT.getValue()*childrenDeductionNumber
					-appliedDeduction;
			
			System.out.println("start use camp children voucher calc");        
			System.out.println("static appliedDeduction"+appliedDeduction);
			System.out.println("restDeduction="+restDeduction);
			
			if(restDeduction>0) {
				if(monthIncome>=restDeduction) {
					monthDeduction=restDeduction;
				}else {
					monthDeduction=monthIncome;
				}		
			}
			Double curTaxBase=monthIncome-monthDeduction;
			Double curTax=curTaxBase*TaxData.MAJOR_RATE.getValue()/100;//??13% or no  ??check for 0
		
			this.update(monthIncome, monthDeduction, curTax);
			this.setType(incomes.get(0).getIncomeType());
			updateAppliedDeduction(monthDeduction);
			
			System.out.println("after use camp children voucher calc");        
			System.out.println("static totalSupport"+appliedDeduction);
			System.out.println("restDeduction="+restDeduction);	
			
		}
		
		return this;
	}	

}
