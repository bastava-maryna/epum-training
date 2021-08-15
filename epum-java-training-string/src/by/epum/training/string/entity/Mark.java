package by.epum.training.string.entity;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class Mark implements Unit,Serializable{
	private static final long serialVersionUID = -3019557152547182492L;
	
	private char mark;

	public Mark(char mark) {
		this.mark = mark;
	}

	public char getMark() {
		return mark;
	}

	public void setMark(char mark) {
		this.mark = mark;
	}	

	@Override
	public void addUnit(Unit unit) {
		throw new UnsupportedOperationException("cant add anything to mark");	
	}

	@Override
	public String getUnitView() {
		return "         "+ this.getClass().getSimpleName()+" "+ String.valueOf(mark)+"\n";	
	}

	@Override
	public List<Unit> getAllUnits() {
		throw new UnsupportedOperationException("mark doesn contain units");
	}
	
	@Override
	public Iterator<Unit> createIterator(){
				return new NullIterator();
	}

	@Override
	public String getUnitContent() {
		return String.valueOf(mark);
	}

	@Override
	public String getDelimiter() {
		return " ";
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" [mark=" + mark + "]";
	}
}
