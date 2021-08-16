package by.epum.training.string.entity;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Stack;

public class UnitIterator implements Iterator<Unit>,Serializable{
	private static final long serialVersionUID = -623956007480970359L;
	
	public Stack <Iterator<Unit>> stack=new Stack<>();

	public UnitIterator(Iterator<Unit> iterator) {
		stack.push(iterator);
	}

	@Override
	public boolean hasNext() {
		if(stack.isEmpty()) {
			return false;
		}else {
			Iterator<Unit> iterator=stack.peek();
			if(!iterator.hasNext()) {
				stack.pop();
				return hasNext();
			}else {
				return true;
			}
		}
	}

	@Override
	public Unit next() {
		if(hasNext()) {
			Iterator<Unit> iterator=stack.peek();
			Unit unit=iterator.next();
			stack.push(unit.createIterator());
			return unit;
		}else {
			return null;
		}
	}
}
