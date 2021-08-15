package by.epum.training.string.entity;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import by.epum.training.string.dao.exception.DAOException;

public abstract class StructuralUnit implements Unit,Serializable {  //?? need extends iterable<Unit>
	
	private static final long serialVersionUID = 7368391439959402488L;
	
	protected List<Unit>units;
	private Iterator <Unit> iterator=null;
		
	//this delimiter-to separate the same structural units
	@Override
	public String getDelimiter() {
		return "\n\r";
	}

	public Iterator<Unit> createIterator(){
		if(iterator==null) {
			iterator=new UnitIterator(units.iterator());
		}
		return iterator;
	}
	
	public StructuralUnit() {
		units = new LinkedList<Unit>();
	}
	
	public StructuralUnit(List<Unit> units) {
		this.units = units;
	}

	//add to the end of the list
	@Override
	public void addUnit(Unit unit) {
		if(unit==null) throw new NullPointerException("no need to add null element");
		units.add(unit);
	}

	@Override
	public List<Unit> getAllUnits() {
		return units;
	}
	
	public Unit getUnit(int number) throws DAOException {
		if (units==null) {
			throw new DAOException("this unit contains no elements ");
		}
		if(number>=units.size()) {
			throw new DAOException("this unit contains only "+units.size()+" elements");
		}
		
		return units.get(number);	
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}
	
	@Override 
	public String getUnitView() {
		StringBuilder builder=new StringBuilder();
	
		builder.append(this.getClass().getSimpleName());
	
		return builder.toString();
	}

	@Override
	public String getUnitContent() {			
		StringBuilder builder=new StringBuilder();
		
		for(Unit u:units) {
			builder.append(u.getUnitContent()).append(u.getDelimiter());
		}
		
		return builder.toString();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" [units=" + units.toString() +"]";
	}

}
