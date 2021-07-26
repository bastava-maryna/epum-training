package by.epum.training.oop.service.util;

import java.time.LocalDate;

import by.epum.training.oop.entity.TaxData;
import by.epum.training.oop.service.exception.ServiceValidatorException;

public class Validator {
	private static final int START_YEAR= 2000;
	private static final int FIRST_MONTH= 1;
	private static final int LAST_MONTH= 12;
	
	public static boolean isTaxDataComplete() {
		for(TaxData elem:TaxData.values()) {
			if(elem.getValue()==0) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isYearValid(String year) throws ServiceValidatorException {
		int intYear;
	
		try {
			intYear=Integer.valueOf(year);
		}catch (NumberFormatException e){
			throw new ServiceValidatorException("Cant use value as year",e);
		}
		
		if(intYear<START_YEAR || intYear>LocalDate.now().getYear()) {
			return false;
		}
		
		return true;
	}

	public static boolean isMonthValid(String month) throws ServiceValidatorException {
		int intMonth;
	
		try {
			intMonth=Integer.valueOf(month);
		}catch (NumberFormatException e){
			throw new ServiceValidatorException("Cant use value as month",e);
		}
		
		if(intMonth<FIRST_MONTH || intMonth>LAST_MONTH || intMonth>=LocalDate.now().getMonthValue()) {
			return false;
		}
		
		return true;
	}
	
	public static int getStartYear() {
		return START_YEAR;
	}

	
}
