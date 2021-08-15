package by.epum.training.string.entity;

import java.util.List;

public class Abzac extends StructuralUnit {
	private static final long serialVersionUID = 97690079889287895L;
	
	public Abzac() {
		super();
	}

	public Abzac(List<Unit> units) {
		super(units);
	}
		
	@Override
	public String getUnitView() {
		return "   "+super.getUnitView();
	}

}
