package by.epum.training.oop.service;

import java.util.Map;

import by.epum.training.oop.entity.IncomeType;
import by.epum.training.oop.service.exception.ServiceException;
import by.epum.training.oop.service.impl.calculators.BaseTaxCalculator;


public interface TaxCalculatorService {
	Map<IncomeType,BaseTaxCalculator> calculateAnnualTax(Long taxPayerId, String year) throws ServiceException;
	void getTaxData(String year) throws ServiceException;
	Map<IncomeType,BaseTaxCalculator> calculatePeriodTax(Long taxPayerId, String year, String monthNumberPeriodEnd) throws ServiceException;
}
