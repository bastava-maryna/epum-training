package by.epum.training.string.entity;

import java.util.List;

public class Part extends StructuralUnit {
	private static final long serialVersionUID = -9135001824085217122L;
	
	private String name;
	
	public Part() {
		super();
		this.name = "";
	}

	public Part(String name) {
		super();
		this.name = name;
	}
	
	public Part(String name, List<Unit> parts) {
		this(name);
		units = parts;
	}

	public String getName() {
		return name;
	}

	public Part(List<Unit> parts) {
		super(parts);
		this.name = "";
	}
}
