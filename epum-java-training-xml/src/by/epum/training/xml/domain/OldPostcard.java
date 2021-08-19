package by.epum.training.xml.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OldPostcard {
	private Long id;
	private String description;
	private PostcardTheme theme;
	private PostcardType type;
	private boolean isSent;
	private String country;
	private int year;
	private List<String>authors;
	private ValueType value;

	public OldPostcard() {};
	
	public OldPostcard(Long id, String description, PostcardTheme theme, PostcardType type, boolean isSent, String country, int year,
			List<String> authors, ValueType value) {
		this.id = id;
		this.description=description;
		this.theme = theme;
		this.type = type;
		this.isSent = isSent;
		this.country = country;
		this.year = year;
		this.authors = new ArrayList<String>(authors);
		this.value = value;
	}


	public long getId() {
		return id;
	}

	
	public void setId(long id) {
		this.id = id;
	}

	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public PostcardTheme getTheme() {
		return theme;
	}


	public void setTheme(PostcardTheme theme) {
		this.theme = theme;
	}


	public PostcardType getType() {
		return type;
	}


	public void setType(PostcardType type) {
		this.type = type;
	}


	public boolean isSent() {
		return isSent;
	}


	public void setSent(boolean isSent) {
		this.isSent = isSent;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public List<String> getAuthors() {
		return authors;
	}


	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}


	public ValueType getValue() {
		return value;
	}


	public void setValue(ValueType value) {
		this.value = value;
	}


	@Override
	public int hashCode() {
		return Objects.hash(description,authors, country, id, isSent, theme, type, value, year);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OldPostcard other = (OldPostcard) obj;
		return Objects.equals(authors, other.authors) && Objects.equals(country, other.country)&&Objects.equals(description, other.description) &&Objects.equals(id, other.id)
				&& isSent == other.isSent && theme == other.theme && type == other.type && value == other.value
				&& year == other.year;
	}


	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" id=" + id + ",\ndescription="+description+",\ntheme=" + theme + ",\ntype=" + type + ",\nisSent=" + isSent + ",\ncountry="
				+ country + ",\nyear=" + year + ",\nauthors=" + authors.toString() + ",\nvalue=" + value;
	}
	
}
