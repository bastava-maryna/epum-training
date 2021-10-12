package by.epum.training.db.entity;

import java.math.BigDecimal;
import java.util.Objects;


public class CarriagePricePolicy extends Entity<Long> {
	private static final long serialVersionUID = -2297107728315527067L;
	
	CarriageType type;
	BigDecimal coefficient;
	//creation date
		
	public CarriageType getType() {
		return type;
	}
	public void setType(CarriageType type) {
		this.type = type;
	}
	public BigDecimal getCoefficient() {
		return coefficient;
	}
	public void setCoefficient(BigDecimal coefficient) {
		this.coefficient = coefficient;
	}
	@Override
	public int hashCode() {
		return Objects.hash(coefficient, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarriagePricePolicy other = (CarriagePricePolicy) obj;
		return Objects.equals(coefficient, other.coefficient) && type == other.type;
	}
	@Override
	public String toString() {
		return getClass().getSimpleName()+" [type=" + type + ", coefficient=" + coefficient+ ", id=" + getId() + "]";
	}
	
	
		
}
