package by.epum.training.db.entity;

import java.util.Objects;

public class ScheduleMode extends Entity<Short> {
	private static final long serialVersionUID = -8007101474676338681L;
	
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScheduleMode other = (ScheduleMode) obj;
		return Objects.equals(description, other.description);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+" [description=" + description + ", id=" + getId() + "]";
	}


	
}
