package by.epum.training.string.entity;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class Word implements Unit,Serializable{
	private static final long serialVersionUID = -8181351002317275915L;
	
	private String word;

	public Word(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public String getUnitView() {
		 return "         "+ this.getClass().getSimpleName()+" "+ word;
	}
	
	@Override
	public void addUnit(Unit unit) {
		throw new UnsupportedOperationException("cant add anything to word");
	}

	@Override
	public List<Unit> getAllUnits() {
		throw new UnsupportedOperationException("word doesn contain units");
	}	

	@Override
	public Iterator<Unit> createIterator() {
		return new NullIterator();
	}

	@Override
	public String getUnitContent() {
		 return word;
	}

	@Override
	public String getDelimiter() {
		return " ";
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" [word=" + word + "]";
	}

}
