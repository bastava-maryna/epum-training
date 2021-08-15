package by.epum.training.string.entity;

public class Sentence extends StructuralUnit{
	private static final long serialVersionUID = 2308035712243382525L;

	@Override
	public String getDelimiter() {
		return "";
	}

	@Override
	public String getUnitView() {	
		StringBuilder builder=new StringBuilder();
		builder.append("      ").append(super.getUnitView()).append("\n");
		
		for(Unit u:units) {
			builder.append(u.getUnitView()).append("\n");
		}
		
		return builder.toString();
	}
	
}
