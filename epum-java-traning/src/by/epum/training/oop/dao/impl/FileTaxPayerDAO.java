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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.epum.training.oop.dao.TaxPayerDAO;
import by.epum.training.oop.dao.exception.DAOException;
import by.epum.training.oop.dao.exception.DAOResourceException;
import by.epum.training.oop.dao.exception.DAOWrongValueException;
import by.epum.training.oop.entity.Car;
import by.epum.training.oop.entity.Property;
import by.epum.training.oop.entity.RealEstate;
import by.epum.training.oop.entity.TaxPayer;


public class FileTaxPayerDAO implements TaxPayerDAO {
	private static final String PROPERTY_SOURCE="src\\resources\\property_db.txt";
	private static final String TAX_PAYER_SOURCE="src\\resources\\tax_payer_db.txt";
	private final DateTimeFormatter FORMATTER= DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	private TemporaryStorage temporaryStorage=TemporaryStorageImpl.getInstance();
	
	@Override
	public TaxPayer getById(Long id) throws DAOException{
		
		try (BufferedReader reader=new BufferedReader(new FileReader(TAX_PAYER_SOURCE)) ) {   
			String line;
				
			while((line=reader.readLine())!=null) {
				if(line.contains(String.valueOf(id))) {	
					TaxPayer taxPayer=constructTaxPayer(line);	
					if(taxPayer==null) throw new DAOWrongValueException("Cant transforn resource data for id="+id+" to internal type. Report to developer.");
					return taxPayer;
				}
			}
		}catch(FileNotFoundException e) {	
			throw new DAOResourceException (e);
		}catch(IOException e) {	
			throw new DAOResourceException (e);
		}
		return null;
	}

	
	@Override
	public List<TaxPayer> findByLastName(String lastName) throws DAOException {
		List<TaxPayer> result=new ArrayList<>();
		
		try (BufferedReader reader=new BufferedReader(new FileReader(TAX_PAYER_SOURCE)) ) {   
			String line;
				
			while((line=reader.readLine())!=null) {
				if(line.contains(lastName)) {	
					TaxPayer taxPayer=constructTaxPayer(line);
					if(taxPayer==null) throw new DAOWrongValueException("Cant transforn resource data to internal type. Report to developer.");
					result.add(taxPayer);
				}
			}
		}catch(FileNotFoundException e) {	
			throw new DAOResourceException (e);
		}catch(IOException e) {	
			throw new DAOResourceException (e);
		}
		
		return result;
	}

	
	private ArrayList<LocalDate> createChildren(String[] s){
		ArrayList<LocalDate> children=new ArrayList<LocalDate>();
		
		for(String elem: s) {
			if(!elem.isEmpty()) {
				children.add(LocalDate.parse(elem,FORMATTER));
			}
		}
		
		return children;
	}

	
	private TaxPayer constructTaxPayer(String line) throws DAOException{
		String regex= "(?<id>[0-9]*), (?<lastName>[A-Z][a-z]*), (?<firstName>[A-Z][a-z]*), (?<birthDate>[0-9]{2}-[0-9]{2}-[0-9]{4}), \\((?<children>[0-9, -]*)\\)";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(line);
		
		TaxPayer taxPayer=null;
	
		while (matcher.find()) {
			String[] children=matcher.group("children").split(", ");
	
			try {
				taxPayer=new TaxPayer(Long.valueOf(matcher.group("id")), 
										   matcher.group("lastName"), 
										   matcher.group("firstName"),
										   LocalDate.parse(matcher.group("birthDate"),FORMATTER),
										   createChildren(children),
										   createProperties(matcher.group("id")));
				saveToStorage(taxPayer);
			}  catch (DateTimeParseException e) {
				throw new DAOWrongValueException ("Cant convert from resource to internal type. Report to developer",e);
			} catch (NumberFormatException e) {
				throw new DAOException (e);		
			} 
		}
		return taxPayer;
	}
	
	
	private List<Property> createProperties(String id) throws DAOException{
		List<Property> result=new ArrayList<Property>();
		
		try (BufferedReader reader=new BufferedReader(new FileReader(PROPERTY_SOURCE)) ) {   
			String line;
				
			while((line=reader.readLine())!=null) {
				if(!line.startsWith("//") && !line.isBlank()) {
					if(line.contains(String.valueOf(id))) {	
						Property prop=constructProperty(line);
						result.add(prop) ;
					}
				}
			}
		}catch(FileNotFoundException e) {	
			throw new DAOResourceException (e);
		}catch(IOException e) {	
			throw new DAOResourceException (e);
		}
		
		return result;
	}
	
	
	private Property constructProperty(String line) throws DAOException{
		String regex= "(?<taxPayerId>[0-9]*), (?<propertyId>[0-9]*), (?<type>[a-z_]*), (?<cost>[0-9]*), (?<saleDate>[0-9]{2}-[0-9]{2}-[0-9]{4}|0), (?<modelORaddress>[A-Za-z0-9 ]*)";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(line);
		
		Property property=null;
		
		while (matcher.find()) {
			if(matcher.group("type").equals("car")) {
				property=new Car( 
					   Long.valueOf(matcher.group("propertyId")), 
					   Double.valueOf(matcher.group("cost")),
					   (matcher.group("saleDate").equals("0"))?null:LocalDate.parse(matcher.group("saleDate"),FORMATTER),  //here can be exception 
					   matcher.group("modelORaddress"));
			}else if(matcher.group("type").equals("real_estate")) {
				property=new RealEstate( 
						   Long.valueOf(matcher.group("propertyId")), 
						   Double.valueOf(matcher.group("cost")),
						   (matcher.group("saleDate").equals("0"))?null:LocalDate.parse(matcher.group("saleDate"),FORMATTER),
						   matcher.group("modelORaddress"));
			}else {
				throw new DAOException("Cant create property object. Property type from db does not match available apps property types. ");
			}
		}
		
		return property;
	}


	@Override
	public void saveToStorage(TaxPayer taxPayer) {
		temporaryStorage.saveTaxPayer(taxPayer);
	}
	
	/*	
	//not used
	public boolean addTaxPayer(TaxPayer taxPayerToAdd) throws DAOException  {
		
		try {
			if(isTaxPayerExist(taxPayerToAdd.getId())){
				throw new DAOException("taxPayer with such id already exist");
			}
		} catch (DAOException e) {
			throw new DAOException(e);
		}
		return false;
	}
*/
}	