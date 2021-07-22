package by.epum.training.oop.entity;

import java.time.LocalDate;

public class RealEstate extends Property {
	
	private static final long serialVersionUID = -3005706570681713428L;
	
	private String address;
	
	public RealEstate(Long id, Double cost, String address) {
		super(id, cost);
		this.address = address;
	}

	public RealEstate(Long id, Double cost, LocalDate saleDate, String address) {
		super(id, cost,saleDate );
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
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
		RealEstate other = (RealEstate) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		return true;
	}

	@Override
	public String toString() {
		//return this.getClass().getSimpleName()+super.toString()+" address=" + address;
		return super.toString()+" address=" + address;
	}
	
}
