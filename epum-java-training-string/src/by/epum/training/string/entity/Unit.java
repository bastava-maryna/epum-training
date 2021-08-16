package by.epum.training.string.entity;

import java.util.Iterator;
import java.util.List;

public interface Unit{
	List<Unit>getAllUnits();
	void addUnit(Unit unit);
	
	Iterator<Unit> createIterator();
	
	String getUnitContent();
	String getUnitView();
	String getDelimiter();	

}

