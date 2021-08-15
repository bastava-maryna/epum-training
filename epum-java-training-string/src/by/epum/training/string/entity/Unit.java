package by.epum.training.string.entity;

import java.util.Iterator;
import java.util.List;

public interface Unit{
	public List<Unit>getAllUnits();
	public void addUnit(Unit unit);
	
	public Iterator<Unit> createIterator();
	
	public String getUnitContent();
	public String getUnitView();
	public String getDelimiter();	

}

