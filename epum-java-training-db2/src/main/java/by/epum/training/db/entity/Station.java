package by.epum.training.db.entity;

import java.util.Objects;

public class Station extends Entity<Integer> {
	private static final long serialVersionUID = 1L;
	
	private String stationName;

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(stationName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Station other = (Station) obj;
		return Objects.equals(stationName, other.stationName);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+" [stationName=" + stationName + ", id=" + getId() + "]";
	}
}
