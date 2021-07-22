package by.epum.training.oop.entity;

import java.time.LocalDate;

public class Car extends Property{
	
	private static final long serialVersionUID = -4016671751323595517L;
	
	private String model;

	public Car(Long id, Double cost, String model) {
		super(id, cost);
		this.model = model;
	}
	
	public Car(Long id, Double cost, LocalDate saleDate, String model) {
		super(id, cost,saleDate );
		this.model = model;
	}

	public String getModel() {
		return model;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		return true;
	}

	@Override
	public String toString() {
		//return this.getClass().getSimpleName()+super.toString()+", model=" + model;
		return super.toString()+", model=" + model;
	}

}
