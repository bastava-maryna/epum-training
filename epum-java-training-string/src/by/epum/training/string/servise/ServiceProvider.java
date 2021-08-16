package by.epum.training.string.servise;

import by.epum.training.string.servise.impl.BookServiceImpl;

public class ServiceProvider {
	private static final ServiceProvider instance=new ServiceProvider();

	public static ServiceProvider getInstance() {
		return instance;
	}
	
	private BookService bookService=new BookServiceImpl();

	public BookService getBookService() {
		return bookService;
	}
	
	
}
