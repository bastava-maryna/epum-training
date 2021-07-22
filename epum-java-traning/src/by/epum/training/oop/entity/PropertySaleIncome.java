package by.epum.training.oop.entity;

import java.time.LocalDate;

public class PropertySaleIncome extends Income {
	
	private static final long serialVersionUID = 4029480930915716015L;
	
	private Long propertyId;

	public PropertySaleIncome(LocalDate date, IncomeType type, double sum, Long propertyId) {
		super(date, type, sum);
		this.propertyId = propertyId;
	}
	
	public Long getPropertyId() {
		return propertyId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (propertyId ^ (propertyId >>> 32));
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
		PropertySaleIncome other = (PropertySaleIncome) obj;
		if (propertyId != other.propertyId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + ", propertyId=" + propertyId;
	}
	
}
