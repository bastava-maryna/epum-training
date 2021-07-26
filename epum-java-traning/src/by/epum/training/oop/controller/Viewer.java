package by.epum.training.oop.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import by.epum.training.oop.entity.Income;
import by.epum.training.oop.entity.IncomeType;
import by.epum.training.oop.entity.Property;
import by.epum.training.oop.entity.TaxData;
import by.epum.training.oop.entity.TaxPayer;
import by.epum.training.oop.service.impl.calculators.BaseTaxCalculator;

public class Viewer {
	public static String showTaxPayerInfo(TaxPayer taxPayer) {
		StringBuilder result=new StringBuilder();
		
		result.append("--------------------------------------------------------------\nTax payer info:\n\t").	
			   append(taxPayer.getLastName()).append(" ").append(taxPayer.getFirstName()).append("\n").
			   append("\tid ").append(taxPayer.getId()).append(", birth date: ").append(taxPayer.getBirthDate()).append("\n").
			   append("\tchildren birth date:\n");
		
		if(taxPayer.getChildren()==null || taxPayer.getChildren().isEmpty()) {
			result.append("\t\tno children\n");
		}else {
			for(LocalDate d:taxPayer.getChildren()) {
				result.append("\t\t").append(d).append("\n");
			}
		}	
		
		result.append("\tproperty:\n");
		
		if(taxPayer.getProperty()==null || taxPayer.getProperty().isEmpty()) {
			result.append("\t\tno property\n");
		}else {
			for(Property p:taxPayer.getProperty()) {
				result.append("\t\t").append(p.getClass().getSimpleName()).append(": id=").append(p.getId()).
				       append(", cost=").append(p.getCost()).
				       append(", sale date: ").append(p.getSaleDate()).append("\n");
			}
		}	
		result.append("--------------------------------------------------------------\n");
		
		return result.toString();
	}
	
	public static String showTaxPayersInfo(List<TaxPayer> taxPayers) {
		StringBuilder result=new StringBuilder();
		if(taxPayers!=null && !taxPayers.isEmpty()) {
			result.append("--------------------------------------------------------------\nTax payers info:\n");	
			int count=1;
			for(TaxPayer t:taxPayers) {
				result.append(count).append("\n").append(showTaxPayerInfo(t));	
				count++;
			}
		}
		
		return result.toString();
	}
	
	public static String showTaxData() {
		StringBuilder result=new StringBuilder();
		result.append("--------------------------------------------------------------\nTax data info:\n");
		
		for(TaxData t: TaxData.values()) {
			result.append(t.name()+" = "+t.getValue()+"\n");
		}
		
		result.append("--------------------------------------------------------------\n");
		
		return result.toString();
	}
	
	public static String showIncomes(List<Income>incomes) {
		StringBuilder result=new StringBuilder();
		
		result.append("--------------------------------------------------------------\nIncomes:\n");
		for(Income in : incomes) {
			   result.append("\t").append(in.getDate()).
			   append(" sum= ").append(in.getSum()).append("  ").append(in.getIncomeType()).append("\n");
		
		}	
			
		result.append("--------------------------------------------------------------\n");
		
		return result.toString();
	}
	
	public static String showCalculatedTax(Map<IncomeType,BaseTaxCalculator> annualTax) {
		StringBuilder result=new StringBuilder();
		result.append("--------------------------------------------------------------\nCalculated tax:\n");
			
		if(annualTax!=null) {
			result.append("----------------------------------------------------------------------\n");
			result.append(String.format("%-28s %7s %11s %10s %8s \n", 
					"INCOME TYPE",
					"INCOME",
					"DEDUCTION",
					"TAX BASE",
					"TAX"));
			result.append("----------------------------------------------------------------------\n");
		
			for(Entry<IncomeType, BaseTaxCalculator> entry: annualTax.entrySet()) {
				result.append(String.format("%-28s %8.2f %10.2f %10.2f %10.2f \n", 
						entry.getKey().toString(),
						entry.getValue().getIncome(),
						entry.getValue().getDeduction(),
						entry.getValue().getIncome()-entry.getValue().getDeduction(),
						entry.getValue().getTax()));			
			}
	
			result.append("----------------------------------------------------------------------\n");
		}
		
		return result.toString();
	}
}

