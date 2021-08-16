package by.epum.training.string.entity;

import java.io.Serializable;
import java.util.Iterator;

public class NullIterator implements Iterator<Unit>,Serializable {
	private static final long serialVersionUID = -6372036839293537970L;

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public Unit next() {
		return null;
	}
	
}
