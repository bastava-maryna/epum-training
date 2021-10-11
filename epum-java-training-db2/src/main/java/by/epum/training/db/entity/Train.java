package by.epum.training.db.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Train extends Entity<Integer> {
	private static final long serialVersionUID = 6361384393326449970L;
	
	private String trainName;
	private Route route;
	private LocalTime departureTime;
	private LocalTime destinationTime;
	private BigDecimal price;
	private ScheduleMode scheduleMode;
	private LocalDate departureDate;
	
	public String getTrainName() {
		return trainName;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	public LocalTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}
	public LocalTime getDestinationTime() {
		return destinationTime;
	}
	public void setDestinationTime(LocalTime destinationTime) {
		this.destinationTime = destinationTime;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public ScheduleMode getScheduleMode() {
		return scheduleMode;
	}
	public void setScheduleMode(ScheduleMode scheduleMode) {
		this.scheduleMode = scheduleMode;
	}
	public LocalDate getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}	
	
	@Override
	public int hashCode() {
		return Objects.hash(departureDate, departureTime, destinationTime, price, route, scheduleMode, trainName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Train other = (Train) obj;
		return Objects.equals(departureDate, other.departureDate) && Objects.equals(departureTime, other.departureTime)
				&& Objects.equals(destinationTime, other.destinationTime) && Objects.equals(price, other.price)
				&& Objects.equals(route, other.route) && Objects.equals(scheduleMode, other.scheduleMode)
				&& Objects.equals(trainName, other.trainName);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+" [trainName=" + trainName + ", route=" + route + ", departureTime=" + departureTime
				+ ", destinationTime=" + destinationTime + ", price=" + price + ", scheduleMode=" + scheduleMode
				+ ", departureDate=" + departureDate + ", id=" + getId()+ "]";
	}

}
