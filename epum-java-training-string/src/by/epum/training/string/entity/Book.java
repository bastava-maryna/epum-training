package by.epum.training.string.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Book implements Serializable {
	private static final long serialVersionUID = -1466978646234432677L;
	
	private String title;
	private List<String> authors;
	private Unit annotation;
	private Unit publishingInfo;
	private Unit acknowledgement;
	private Unit mainText;
	private Map<Unit,Unit> glossary;
	
	
	public Book() {};
	
	public Book(String title, List<String> authors, Unit annotation, Unit publishingInfo, Unit acknowledgement,
			Unit mainText, Map<Unit, Unit> glossary) {
		this.title = title;
		this.authors = authors;
		this.annotation = annotation;
		this.publishingInfo = publishingInfo;
		this.acknowledgement = acknowledgement;
		this.mainText = mainText;
		this.glossary = glossary;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getAuthors() {
		return authors;
	}
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
	public Unit getAnnotation() {
		return annotation;
	}
	public void setAnnotation(Unit annotation) {
		this.annotation = annotation;
	}
	
	public Unit getPublishingInfo() {
		return publishingInfo;
	}
	public void setPublishingInfo(Unit publishingInfo) {
		this.publishingInfo = publishingInfo;
	}
	public Unit getMainText() {
		return mainText;
	}
	public void setMainText(Unit mainText) {
		this.mainText = mainText;
	}
	public Unit getAcknowledgement() {
		return acknowledgement;
	}
	public void setAcknowledgement(Unit acknowledgement) {
		this.acknowledgement = acknowledgement;
	}
	public Map<Unit, Unit> getGlossary() {
		return glossary;
	}
	public void setGlossary(Map<Unit, Unit> glossary) {
		this.glossary = glossary;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" [title=" + title + ", authors=" + authors+"]";
	}	
	
}
