package by.epum.training.oop.service.impl.calculators;

import java.time.LocalDate;
import java.util.List;

import by.epum.training.oop.entity.Car;
import by.epum.training.oop.entity.Income;
import by.epum.training.oop.entity.Property;
import by.epum.training.oop.entity.PropertySaleIncome;
import by.epum.training.oop.entity.RealEstate;
import by.epum.training.oop.entity.TaxData;
import by.epum.training.oop.entity.TaxPayer;
import by.epum.training.oop.service.exception.ServiceException;

public class PropertySaleTaxCalculator extends BaseTaxCalculator {

	@Override
	public BaseTaxCalculator calculate(int month, List<Income> incomes, TaxPayer taxPayer) throws ServiceException {
		
		if(incomes!=null) {
			
			//System.out.println("month= "+month);
			//System.out.println("incomes: "+incomes.toString());
			
			for(Income in:incomes) {
				//System.out.println("all propertyis: "+taxPayer.getProperty().toString());
			
				Property prop=taxPayer.getPropertyById(((PropertySaleIncome) in).getPropertyId());
				
				//System.out.println("income: "+in.toString());
				//System.out.println("property: "+prop.toString());
				
				Double curIncome=in.getSum();
				Double curDeduction=0.0;
			
				if(isDeduction(prop.getClass(), in.getDate(), taxPayer.getProperty())){
					curDeduction= prop.getCost();
					if(curDeduction>curIncome) {
						curDeduction=curIncome;
					}
				}
			
				Double curTaxBase=curIncome-curDeduction;
				Double curTax=curTaxBase*TaxData.ADDITIONAL_RATE.getValue()/100;
			
				this.update(curIncome, curDeduction, curTax);
				prop.setSaleDate(in.getDate());
			}
			
			this.setType(incomes.get(0).getIncomeType());
		}
		
		return this;	
	}
	
	private static LocalDate lastSale(Class<? extends Property> propertyClass, List<Property> property) {
		LocalDate temp=LocalDate.MIN;
		
		for(Property p:property) {
			if(p.getClass()==propertyClass) {
				
				LocalDate cur=p.getSaleDate();
				//System.out.println("temp= "+temp);
				//System.out.println("cur= "+cur);
				if(cur!=null && cur.compareTo(temp)>0) {		
					temp=cur;
					//System.out.println("new temp= "+temp);
				}
			}
		}
		
		return temp;
	}
	
	
	private static boolean isDeduction(Class<? extends Property> propertyClass, LocalDate incomeDate, List<Property> property) throws ServiceException {
		LocalDate lastSale=lastSale(propertyClass, property);
		
		if(lastSale!=null) {
			if(propertyClass==Car.class) {
				return lastSale.isBefore(incomeDate.minusYears(TaxData.CAR_CREDIT_TIME.getValue()));
			}else if(propertyClass==RealEstate.class) {
				return lastSale.isBefore(incomeDate.minusYears(TaxData.REAL_ESTATE_CREDIT_TIME.getValue()));
			}else {
				throw new ServiceException("There is now appropriate tax data credit time for class {propertyClass}");
			}
		}
		
		return false;
	}
	
	public static int getChildrenDeductionNumber(List<LocalDate>children) throws ServiceException {
		throw new ServiceException("This method is not used here");
	}
}
