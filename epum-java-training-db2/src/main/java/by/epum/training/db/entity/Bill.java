package by.epum.training.db.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Bill extends Entity<Long> {
	private static final long serialVersionUID = -5137837374500934397L;
	
	public enum Status {
	        ACTIVE,
	        INVALID,
	        PAID;
	    }
	
	private User user;
	private Carriage carriage;
	private Short place;
	private BigDecimal cost;
	private Status status;
	private  LocalDateTime creationTime;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Carriage getCarriage() {
		return carriage;
	}
	public void setCarriage(Carriage carriage) {
		this.carriage = carriage;
	}
	public Short getPlace() {
		return place;
	}
	public void setPlace(Short place) {
		this.place = place;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public LocalDateTime getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		return Objects.hash(carriage, cost, creationTime, place, status, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bill other = (Bill) obj;
		return Objects.equals(carriage, other.carriage) && Objects.equals(cost, other.cost)
				&& Objects.equals(creationTime, other.creationTime) && Objects.equals(place, other.place)
				&& status == other.status && Objects.equals(user, other.user);
	}
	@Override
	public String toString() {
		return getClass().getSimpleName()+" [user=" + user + ", carriage=" + carriage + ", place=" + place + ", cost=" + cost + ", status="
				+ status + ", creationTime=" + creationTime+ ", id=" + getId() + "]";
	}

	
	 
	 
	 
	
	 

	

}
