package by.epum.training.db.entity;

public enum CarriageType {
	BERTH(54),
	COMPARTMENT(36),
	SITTING(90),
	SLEEPING(18);
	
	private final int capacity;

	private CarriageType(int capacity) {
		this.capacity = capacity;
	}

	public int getCapacity() {
		return capacity;
	}
	
}
