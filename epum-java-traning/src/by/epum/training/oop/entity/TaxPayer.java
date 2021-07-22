package by.epum.training.oop.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaxPayer implements Serializable {
	
	private static final long serialVersionUID = -3151905058366907307L;
	
	private Long id;
	private String lastName;
	private String firstName;
	private LocalDate birthDate;
	private List<LocalDate> children;
	private List<Property> property;
	
	
	public TaxPayer(Long id, String lastName,  String firstName, LocalDate birthDate) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.children = new ArrayList<LocalDate>();
		this.property=new ArrayList<Property>();
	}
	
	public TaxPayer(Long id, String firstName, String lastName, LocalDate birthDate, List<LocalDate> children,List<Property> property) {
		this(id,firstName,lastName,birthDate);
		this.children.addAll(children);
		this.property.addAll(property);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public List<LocalDate> getChildren() {
		return children;
	}

	public void setChildren(List<LocalDate> children) {
		this.children = children;
	}

	public Long getId() {
		return id;
	}	
	
	public List<Property> getProperty() {
		return property;
	}

	public Property getPropertyById(Long id) {
		if(!property.isEmpty()){
			for(Property p:property) {
				if(p.getId().equals(id)) {
					return p;
				}
			}
		}	
		return null;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", birthDate=" + birthDate
				+ ", children=" + children + ", property=" + property ;
	}
	
}
