package by.epum.training.string.dao.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import by.epum.training.string.dao.BookDAO;
import by.epum.training.string.dao.exception.DAOException;
import by.epum.training.string.entity.Book;
import by.epum.training.string.entity.Unit;

public class FileBookDAO implements BookDAO {
	
	private Storage storage=Storage.getInstance();

	@Override
	public String getBookText(String path) throws DAOException {
		String text="";
		try {
			//Reads all content from a file into a string, decoding from bytes to characters
			//using the UTF-8 charset
			//The method ensures that the file is closed when all content have been read
			//or an I/O error, or other runtime exception, is thrown
			//for files no bigger than2gb
			text=Files.readString(Paths.get(path));	
		}catch (FileNotFoundException e) {
			throw new  DAOException ("Resource file is not found",e);
		} catch (IOException e) {
			throw new  DAOException ("Problem with processing resource file",e);
		}catch (OutOfMemoryError e) {
			throw new  DAOException ("Resource file is too large");  //is it correct to do so
		}
		return text;
	}

	@Override
	public void saveToStorage(Book book) throws DAOException {
		if(book==null) {
			throw new DAOException("Cant store null book");
		}
		storage.save(book);
	}

	@Override
	public Book getBookFromStorage() {
		return storage.getBook();
	}

	@Override
	public void clearStorage() {
		storage.clear();		
	}

} 
	

