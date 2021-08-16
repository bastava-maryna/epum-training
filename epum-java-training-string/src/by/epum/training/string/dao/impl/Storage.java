package by.epum.training.string.dao.impl;

import by.epum.training.string.entity.Book;

public class Storage {
	private static final Storage instance=new Storage();
	
	private Storage() {}

	public static Storage getInstance() {
		return instance;
	};
	
	private Book book;

	public Book getBook() {
		return book;
	}
	
	public void save(Book book) {
		if(this.book!=null) {
			clear();
		}
		this.book=book;
	}
	
	public void clear() {
		book=null;
	}
}
