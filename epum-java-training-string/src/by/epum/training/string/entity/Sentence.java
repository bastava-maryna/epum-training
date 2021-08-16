package by.epum.training.string.entity;

public class Sentence extends StructuralUnit{
	private static final long serialVersionUID = 2308035712243382525L;

	@Override
	public String getDelimiter() {
		return "";
	}
	
	@Override
	public String getUnitView() {
		return "      "+super.getUnitView();
	}

}
