package by.epum.training.oop.dao.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import by.epum.training.oop.dao.TaxDataDAO;
import by.epum.training.oop.dao.exception.DAOException;
import by.epum.training.oop.dao.exception.DAOResourceException;
import by.epum.training.oop.dao.exception.DAOWrongValueException;
import by.epum.training.oop.entity.TaxData;

public class FileTaxDataDAO implements TaxDataDAO {
	private static final String TAX_DATA_SOURCE="src\\resources\\tax_param_db.txt";

	@Override
	public void getTaxData(String year) throws DAOException {
	
		try (BufferedReader reader=new BufferedReader(new FileReader(TAX_DATA_SOURCE)) ) {   
			String line;
			
			while((line=reader.readLine())!=null) {
				if(!line.startsWith("//") && !line.isBlank()) {
					String [] fromLine=line.split(", ");
					//form set of all taxes which need to make calculation
					Set<TaxData> data=EnumSet.allOf(TaxData.class);
					
					boolean isYearTaxDataExist=false;
					if(fromLine[0].equals(year)) {	
						isYearTaxDataExist=true;
						TaxData temp= TaxData.valueOf(fromLine[1]);   
					
						if(data.contains(temp) ){
							int taxValue=Integer.valueOf(fromLine[2]);
							if(taxValue<=0) {
								throw new DAOWrongValueException("Tax value cant be negative or equal zero");
							}
							temp.setValue(taxValue);
						}
					}
					if(!isYearTaxDataExist) {
						throw new DAOWrongValueException("Tax data for year "+year+ " doesnt exist. Report to DB administrator");
					}
				}	
			}
			
		}catch(IllegalArgumentException e) {	
			throw new DAOException ("Cant convert from resource data to enum value",e);
		}catch(FileNotFoundException e) {	
			throw new DAOResourceException (e);
		}catch(IOException e) {	
			throw new DAOResourceException (e);
		}
	}

}
