package by.epum.training.string.dao;

import by.epum.training.string.dao.exception.DAOException;
import by.epum.training.string.entity.Book;

public interface BookDAO {
	public String getBookText(String path) throws DAOException;
	public void saveToStorage(Book book) throws DAOException;
	public Book getBookFromStorage();
	public void clearStorage();
	
}
