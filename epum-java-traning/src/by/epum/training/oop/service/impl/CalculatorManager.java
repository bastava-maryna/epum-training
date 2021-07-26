package by.epum.training.oop.service.impl;

import java.util.HashMap;
import java.util.Map;


import by.epum.training.oop.entity.IncomeType;
import by.epum.training.oop.service.impl.calculators.AbroadRemmitanceTaxCalculator;
import by.epum.training.oop.service.impl.calculators.AdditionalMaterialSupportTaxCalculator;
import by.epum.training.oop.service.impl.calculators.AdditionalSalaryTaxCalculator;
import by.epum.training.oop.service.impl.calculators.BaseTaxCalculator;
import by.epum.training.oop.service.impl.calculators.ChildrenCampVaucherTaxCalculator;
import by.epum.training.oop.service.impl.calculators.MajorMaterialSupportTaxCalculator;
import by.epum.training.oop.service.impl.calculators.MajorSalaryTaxCalculator;
import by.epum.training.oop.service.impl.calculators.PropertySaleTaxCalculator;
import by.epum.training.oop.service.impl.calculators.RoaltyTaxCalculator;


public class CalculatorManager {
	private Map<IncomeType,BaseTaxCalculator> calculators;
	
	public CalculatorManager() {
		calculators=new HashMap<IncomeType,BaseTaxCalculator>();
		calculators.put(IncomeType.MAJOR_SALARY,new MajorSalaryTaxCalculator());
		calculators.put(IncomeType.ADDITIONAL_SALARY, new AdditionalSalaryTaxCalculator());
		calculators.put(IncomeType.MAJOR_MATERIAL_SUPPORT, new MajorMaterialSupportTaxCalculator());
		calculators.put(IncomeType.ADDITIONAL_MATERIAL_SUPPORT, new AdditionalMaterialSupportTaxCalculator());
		calculators.put(IncomeType.PROPERTY_SALE, new PropertySaleTaxCalculator());
		calculators.put(IncomeType.ABROAD_REMITTANCE, new AbroadRemmitanceTaxCalculator());
		calculators.put(IncomeType.CHILDREN_CAMP_VOUCHER, new ChildrenCampVaucherTaxCalculator());
		calculators.put(IncomeType.ROYALTY, new RoaltyTaxCalculator());
	}
	
	public BaseTaxCalculator getCalculator(IncomeType type) {
		BaseTaxCalculator calculator;
		calculator=calculators.get(type);
		return calculator;
	}
	
	public void clear() {
		if(calculators!=null) {
			for(Map.Entry<IncomeType,BaseTaxCalculator> entry:calculators.entrySet()) {
				entry.getValue().clear();
			}
		}
	}
}
