package by.epum.training.oop.dao.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import by.epum.training.oop.dao.IncomeDAO;
import by.epum.training.oop.dao.exception.DAOException;
import by.epum.training.oop.dao.exception.DAOMissingValueException;
import by.epum.training.oop.dao.exception.DAOResourceException;
import by.epum.training.oop.dao.exception.DAOWrongValueException;
import by.epum.training.oop.entity.AdditionalIncome;
import by.epum.training.oop.entity.Income;
import by.epum.training.oop.entity.IncomeType;
import by.epum.training.oop.entity.PropertySaleIncome;
import by.epum.training.oop.entity.RoyaltyIncome;
import by.epum.training.oop.entity.RoyaltyType;


public class FileIncomeDAO implements IncomeDAO {
	private static final String INCOME_SOURCE="src\\resources\\income_db.txt";
	
	private TemporaryStorage temporaryStorage=TemporaryStorageImpl.getInstance();

	@Override
	public List<Income> getIncomes(Long taxPayerId, String year) throws DAOException {
		int yy=Integer.valueOf(year);
		if(temporaryStorage.getTaxPayer()!=null 
				&& temporaryStorage.getTaxPayer().getId().equals(taxPayerId)
				&& temporaryStorage.getYear()==yy
				&& temporaryStorage.getIncomes()!=null) {
			return temporaryStorage.getIncomes();
		}
		
		final DateTimeFormatter FORMATTER= DateTimeFormatter.ofPattern("dd-MM-yyyy");
		List<Income>incomes=new ArrayList<>();	
			
		try (BufferedReader reader=new BufferedReader(new FileReader(INCOME_SOURCE)) ) {   
			String line;
				
			while((line=reader.readLine())!=null) {
				if(!line.startsWith("//")) {
					String [] fromLine=line.split(", ");
					
					int y=LocalDate.parse(fromLine[1], FORMATTER).getYear();
				
					if(fromLine[0].equals(String.valueOf(taxPayerId))&& y==yy) {	
						IncomeType type= IncomeType.valueOf(fromLine[2].toUpperCase());
						Income income=null;
							
						if(type==IncomeType.PROPERTY_SALE ){
							income=new PropertySaleIncome(LocalDate.parse(fromLine[1], FORMATTER),
														  type,
														  Double.valueOf(fromLine[3]), 
														  (fromLine[4]!=null)?Long.valueOf(fromLine[4]):0);
							
						}else if(type==IncomeType.ADDITIONAL_SALARY || type==IncomeType.ADDITIONAL_MATERIAL_SUPPORT ){
							income=new AdditionalIncome(LocalDate.parse(fromLine[1], FORMATTER),
									  type,
							  		  Double.valueOf(fromLine[3]), 
									  Long.valueOf(fromLine[4]));
						}else if(type==IncomeType.ROYALTY ){
							income=new RoyaltyIncome(LocalDate.parse(fromLine[1], FORMATTER),
										type,
										Double.valueOf(fromLine[3]), 
										RoyaltyType.valueOf(fromLine[4]));
						}
						else {
							income=new Income(LocalDate.parse(fromLine[1], FORMATTER),
											  type,
											  Double.valueOf(fromLine[3]));
						}
						
						incomes.add(income);
					}
				}	
			}
			if(temporaryStorage.getTaxPayer()!=null 
					&& temporaryStorage.getTaxPayer().getId().equals(taxPayerId)) {
				temporaryStorage.setYear(yy);
				temporaryStorage.saveIncomes(incomes);
			}
		}catch(IllegalArgumentException |NullPointerException | DateTimeParseException e) {
			throw new DAOWrongValueException("Cant convert from resource to internal type. Report to developer",e);
		}catch(ArrayIndexOutOfBoundsException e) {
			throw new DAOMissingValueException("Missing value in resource. Report to administrator",e);	
		}catch(FileNotFoundException e ) {	
			throw new DAOResourceException (e);
		}catch(IOException e) {	
			throw new DAOResourceException (e);
		}
		return incomes;
	}
	
	@Override
	public void saveToStorage(List<Income> incomes) {
		temporaryStorage.saveIncomes(incomes);
	}

}

