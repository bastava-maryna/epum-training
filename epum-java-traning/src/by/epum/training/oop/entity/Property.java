package by.epum.training.oop.entity;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Property implements Serializable{
	
	private static final long serialVersionUID = -5520186395352452889L;
	
	private Long id;
	private Double cost;
	private LocalDate saleDate;
	
	public Property(Long id, Double cost) {
		this.id = id;
		this.cost = cost;
		this.saleDate = null;
	}
	
	public Property(Long id, Double cost,LocalDate saleDate) {
		this(id,cost);
		this.saleDate = saleDate;
	}
	
	public Long getId() {
		return id;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public LocalDate getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = saleDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((saleDate == null) ? 0 : saleDate.hashCode());
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
		Property other = (Property) obj;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (saleDate == null) {
			if (other.saleDate != null)
				return false;
		} else if (!saleDate.equals(other.saleDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" id=" + id + ", cost=" + cost + ", saleDate=" + saleDate ;
	}
	
}
