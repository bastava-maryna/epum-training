package by.epum.training.oop.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Income implements Serializable{

	private static final long serialVersionUID = 8811829999973611649L;

	private LocalDate date;
	private IncomeType type;
	private Double sum;
	
	public Income(LocalDate date, IncomeType type, Double sum) {	
		this.date = date;
		this.type = type;
		this.sum = sum;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public IncomeType getIncomeType() {
		return type;
	}

	public Double getSum() {
		return sum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((sum == null) ? 0 : sum.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Income other = (Income) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (sum == null) {
			if (other.sum != null)
				return false;
		} else if (!sum.equals(other.sum))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+ " date=" + date + ", type=" + type + ", sum=" + sum ;
	}
	
}
