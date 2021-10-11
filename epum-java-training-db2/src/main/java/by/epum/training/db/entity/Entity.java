package by.epum.training.db.entity;

import java.io.Serializable;

public abstract class Entity <I> implements Serializable{
	
	private static final long serialVersionUID = -2364187473014333046L;
	
	private I id;

	public I getId() {
		return id;
	}

	public void setId(I id) {
		this.id = id;
	}

	@Override
	public abstract int hashCode(); 

	@Override
	public abstract boolean equals(Object obj);
	
}
