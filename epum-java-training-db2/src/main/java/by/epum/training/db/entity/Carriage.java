package by.epum.training.db.entity;

import java.util.Objects;

public class Carriage extends Entity<Long> {

	private static final long serialVersionUID = 7586577917754978404L;
	
	private Train train;
	private Short carriageNumber;
	private CarriageType carriageType;
	
	
	public Short getCarriageNumber() {
		return carriageNumber;
	}
	public void setCarriageNumber(Short carriageNumber) {
		this.carriageNumber = carriageNumber;
	}
	public CarriageType getCarriageType() {
		return carriageType;
	}
	public void setCarriageType(CarriageType carriageType) {
		this.carriageType = carriageType;
	}
	public Train getTrain() {
		return train;
	}
	public void setTrain(Train train) {
		this.train = train;
	}
	@Override
	public int hashCode() {
		return Objects.hash(carriageNumber, carriageType, train);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carriage other = (Carriage) obj;
		return Objects.equals(carriageNumber, other.carriageNumber) && carriageType == other.carriageType
				&& Objects.equals(train, other.train);
	}
	@Override
	public String toString() {
		return getClass().getSimpleName()+" [train=" + train + ", carriageNumber=" + carriageNumber + ", carriageType=" + carriageType
				+ ", id=" + getId() + "]";
	}
}
