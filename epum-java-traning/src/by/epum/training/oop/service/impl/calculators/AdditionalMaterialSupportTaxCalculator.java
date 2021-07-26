package by.epum.training.oop.service.impl.calculators;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.epum.training.oop.entity.AdditionalIncome;
import by.epum.training.oop.entity.Income;
import by.epum.training.oop.entity.TaxData;
import by.epum.training.oop.entity.TaxPayer;
import by.epum.training.oop.service.exception.ServiceException;


public class AdditionalMaterialSupportTaxCalculator extends BaseTaxCalculator {

	private static Map<Long,Double> appliedDeduction=new HashMap<Long,Double>();
	
	public static void updateAppliedDeduction(Long sourceId,Double plusValue) {
		if(appliedDeduction!=null) {
			if(appliedDeduction.containsKey(sourceId)) {
				plusValue+=appliedDeduction.get(sourceId);
			}
			appliedDeduction.put(sourceId, plusValue);
		}
	}
		
	@Override
	public void clear() {
		appliedDeduction.clear();
	}
	
	@Override
	public BaseTaxCalculator calculate(int month, List<Income> incomes, TaxPayer taxPayer) {
		
		if(incomes!=null) {
			Double monthIncome=0.0;
			Double monthDeduction=0.0;
			Map<Long,Double> tempStorage=consolidate(incomes);
			
			for(Map.Entry<Long, Double> elem : tempStorage.entrySet()) {
				Double cur=appliedDeduction.get(elem.getKey());
				if(cur==null) {
					cur=0.0;
				}
				
				if(cur!=null && cur<TaxData.ADDITIONAL_MATERIAL_SUPPORT_LIMIT.getValue()) {
					Double restAvailableDeduction=TaxData.ADDITIONAL_MATERIAL_SUPPORT_LIMIT.getValue()-cur;
					
					if(restAvailableDeduction>0) {
						if(elem.getValue()>restAvailableDeduction) {
							monthDeduction+=restAvailableDeduction;
						}else {
							monthDeduction+=elem.getValue();
						}
					}	
					
					monthIncome+=elem.getValue();
					updateAppliedDeduction(elem.getKey(), elem.getValue());
				}
			}
			
			Double curTaxBase=monthIncome-monthDeduction;
			Double curTax=curTaxBase*TaxData.MAJOR_RATE.getValue()/100;//??13% or no  ??check for 0
		
			this.update(monthIncome, monthDeduction, curTax);
			this.setType(incomes.get(0).getIncomeType());
		}
		
		return this;
	}
	
	private static Map<Long,Double> consolidate(List<Income>incomes){
		Map<Long,Double> tempStorage=new HashMap<Long,Double>();
		
		for(Income in:incomes) {	
			Long sourceId=((AdditionalIncome) in).getSourceID();
			Double tempIncome=0.0;
			
			if(tempStorage.containsKey(sourceId)) {
				tempIncome=tempStorage.get(sourceId)+in.getSum();
				tempStorage.put(sourceId, tempIncome);	
			}
			tempStorage.put(sourceId, in.getSum());		
		}
		
		return tempStorage;
	}
	
	public static int getChildrenDeductionNumber(List<LocalDate>children) throws ServiceException {
		throw new ServiceException("This method is not used here");
	}
}