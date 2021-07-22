package by.epum.training.oop.entity;

public enum TaxData {
	 STANDARD_PERSON(0),
	 STANDARD_CHILDREN(0),
	 STANDARD_CHILD(0),
	 STANDARD_LIMIT(0),
	 MAJOR_RATE(0),
	 ADDITIONAL_RATE(0),
	 COMMON_RATE(0),
	 CAR_CREDIT_TIME(0),
	 REAL_ESTATE_CREDIT_TIME(0),
	 CHILDREN_CAMP_VOUCHER_LIMIT(0),
	 ADDITIONAL_MATERIAL_SUPPORT_LIMIT(0),
	 MAJOR_MATERIAL_SUPPORT_LIMIT(0),
	 ABROAD_REMITTANCE_LIMIT(0),
	 LITERATURE(0),
	 MUSIC(0),
	 COMPUTER_PROGRAMM(0),
	 AUDIO_VISUAL(0),
	 INVENTION(0),
	 PAINTING(0);


	private int value;

	private TaxData(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
