package by.epum.training.oop.entity;

import java.time.LocalDate;

public class AdditionalIncome extends Income{
	
	private static final long serialVersionUID = 3805354985734441524L;
	
	private Long sourceID;

	public AdditionalIncome(LocalDate date, IncomeType type, double sum, Long sourceID) {
		super(date, type, sum);
		this.sourceID = sourceID;
	}

	public Long getSourceID() {
		return sourceID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((sourceID == null) ? 0 : sourceID.hashCode());
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
		AdditionalIncome other = (AdditionalIncome) obj;
		if (sourceID == null) {
			if (other.sourceID != null)
				return false;
		} else if (!sourceID.equals(other.sourceID))
			return false;
		return true;
	}

	@Override
	public String toString() {
	//	return this.getClass().getSimpleName()+ super.toString() + ", sourceID=" + sourceID;
		return super.toString() + ", sourceID=" + sourceID;
	}	

}
