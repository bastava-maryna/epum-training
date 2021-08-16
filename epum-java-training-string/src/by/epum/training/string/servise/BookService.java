package by.epum.training.string.servise;

import by.epum.training.string.entity.Book;
import by.epum.training.string.entity.Unit;
import by.epum.training.string.servise.exception.ServiceException;

public interface BookService {
	boolean parseBook(String path) throws ServiceException; 
	Book getBook() throws ServiceException;
	String getTableOfContent() throws ServiceException;
	
	String getGlossaryWordsView() throws ServiceException;
	String getGlossaryView() throws ServiceException;
	
	String modifyText(Unit unit,int option) throws ServiceException;
	String getView(Unit unit);
	String getUnitContent(Unit unit);
	
	
}
