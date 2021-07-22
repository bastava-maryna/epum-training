package by.epum.training.oop.entity;

import java.time.LocalDate;

public class RoyaltyIncome extends Income{
	
	private static final long serialVersionUID = 7632259149432409896L;
	
	private RoyaltyType royaltyType;

	public RoyaltyIncome(LocalDate date, IncomeType type, Double sum, RoyaltyType royaltyType) {
		super(date, type, sum);
		this.royaltyType=royaltyType;
	}
	
	public RoyaltyType getRoyaltyType() {
		return royaltyType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((royaltyType == null) ? 0 : royaltyType.hashCode());
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
		RoyaltyIncome other = (RoyaltyIncome) obj;
		if (royaltyType != other.royaltyType)
			return false;
		return true;
	}

	@Override
	public String toString() {
	//	return this.getClass().getSimpleName()+ super.toString()+", royaltyType=" + royaltyType;
		return super.toString()+", royaltyType=" + royaltyType;
	}
	
}
