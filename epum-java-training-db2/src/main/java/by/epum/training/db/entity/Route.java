package by.epum.training.db.entity;

import java.util.Objects;

public class Route extends Entity<Integer> {
	private static final long serialVersionUID = -4384771069864106365L;

	private Station departure;
	private Station destination;
	
	public Station getDeparture() {
		return departure;
	}
	public void setDeparture(Station departure) {
		this.departure = departure;
	}
	public Station getDestination() {
		return destination;
	}
	public void setDestination(Station destination) {
		this.destination = destination;
	}
	@Override
	public int hashCode() {
		return Objects.hash(departure, destination);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		return Objects.equals(departure, other.departure) && Objects.equals(destination, other.destination);
	}
	@Override
	public String toString() {
		return getClass().getSimpleName()+" [departure=" + departure + ", destination=" + destination + ", id=" + getId()+"]";
	}
	
}
