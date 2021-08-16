package by.epum.training.string.servise.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import by.epum.training.string.dao.DAOProvider;
import by.epum.training.string.dao.exception.DAOException;
import by.epum.training.string.entity.Book;
import by.epum.training.string.entity.Part;
import by.epum.training.string.entity.Unit;
import by.epum.training.string.entity.Word;
import by.epum.training.string.servise.BookService;
import by.epum.training.string.servise.exception.ServiceException;


public class BookServiceImpl implements BookService {
	private final DAOProvider daoProvider=DAOProvider.getInstance();
	private BookParser bookParser=new BookParser();
	
	private static final Logger LOGGER=Logger.getLogger(BookParser.class);
	
	@Override
	public boolean parseBook(String path) throws ServiceException  {
		boolean result=false;
		daoProvider.getBookDAO().clearStorage();
		
		try {
			String text=daoProvider.getBookDAO().getBookText(path);
			
			String [] parts=bookParser.parseToComponents(text);
			//for this book we obtain 5 components
			//1-annotation
			//2-publishing info
			//3-acknowledgments
			//4-main text
			//5-glossary
			
			LOGGER.info("\n*****Build annotation*****");
			Unit annotation= bookParser.parseToSimplePart(parts[0]);  
			
			LOGGER.info("\n*****Build publishing info*****");
			Unit publishingInfo=bookParser.parseToSimplePart(parts[1]);

			List<String> authors=Arrays.asList(getUnitContent(publishingInfo.getAllUnits().get(0).getAllUnits().get(0)).trim());
			LOGGER.info("\nAuthors:\n"+authors.toString());

			String title=getUnitContent(publishingInfo.getAllUnits().get(0).getAllUnits().get(1));
			LOGGER.info("Title:\n"+title.toString());
			
			LOGGER.info("\n*****Build acknowledgement*****");
			Unit acknowledgement=bookParser.parseToSimplePart(parts[2]);
			
			LOGGER.info("\n*****Build main text*****");
			Unit mainText=bookParser.parseMainToParts(parts[3]);
			
			LOGGER.info("\n*****Build glossary*****");
			Map<Unit,Unit>glossary=buildGlossaryPart(parts[4]);
		
			Book book=new Book(title,authors,annotation,publishingInfo,acknowledgement,mainText,glossary);
			
			if(book!=null) {
				daoProvider.getBookDAO().saveToStorage(book);
				result= true;
			}
		
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}	
		
		return result;
	}

	private Map<Unit,Unit> buildGlossaryPart(String part) throws ServiceException {
		Unit simplePart=bookParser.parseToSimplePart(part);
		List<Unit> units=simplePart.getAllUnits();
		Map<Unit,Unit> glossary=new LinkedHashMap<>();
		
		for(Unit u:units) {
			Unit key=u.getAllUnits().get(0);
			Unit value=u.getAllUnits().get(1);
		
			glossary.put(key, value);	
		}
	
		return glossary;
	}
	
	
	@Override
	public Book getBook() throws ServiceException {	
		Book book=daoProvider.getBookDAO().getBookFromStorage();
		if(book==null) {
			throw new ServiceException(" there no book in storage, parse boo firstly");
		}
		return book;
	}
	
	
	@Override
	//throw exception if parsing is not done jet
	public String getTableOfContent() throws ServiceException {
		Unit mainText=getBook().getMainText();
		StringBuilder builder=new StringBuilder();
		
		Iterator<Unit> it=mainText.createIterator();
		
		while(it.hasNext()) {		
			Unit cur= it.next();
			if(cur.getClass()==Part.class) {	
				builder.append(((Part)cur).getName()).append(cur.getDelimiter());
			}	
		}	
		
		return builder.toString();
	}
	
	
	@Override
	public String modifyText(Unit unit,int option) throws ServiceException {
		//if option=1 - delete repetition of first letter
		//if option=2 - delete repetition of last letter
	
		if(unit==null) {
			throw new ServiceException("impossible modify nothing");
		}
		if(option!=1 && option!=2) {
			throw new ServiceException("no such option");
		}
		
		Iterator<Unit> it=unit.createIterator();
		
		//paragraph -> abzac -> sentence ->word/mark
		while (it.hasNext()) {
			Unit curUnit=it.next();
			
			if(curUnit.getClass()==Word.class) {
				String word=((Word)curUnit).getWord();
				
				word=removeAllRepetition(word,option);
				((Word)curUnit).setWord(word);
			}
		}

		return unit.getUnitContent();
	}
		
	private String removeAllRepetition(String test, int option) throws ServiceException { 
		//if option=1 - delete repetition of first letter
		//if option=2 - delete repetition of last letter
		
		if(test==null) {
			throw new ServiceException("impossible remove from nothing");
		}
		if(test.length()<2) {
			return test;
		}
		
		String checked;
		
		if(option==1) {
			checked=test.substring(0,1 );
			
			return checked+test.replace(checked,"");
			
		}else {
			int testLength=test.length();
			checked=test.substring(testLength-1,testLength);
			
			return test.replace(checked,"")+checked;
		}
	}
	
	@Override
	//throw exception if parsing is not done jet
	public String getGlossaryView() throws ServiceException {
		Map<Unit,Unit> glossary=getBook().getGlossary();
		StringBuilder result=new StringBuilder();
		
		if(glossary!=null) {
			result.append("GLOSSARY\n");
			
			for(Map.Entry<Unit,Unit>entry: glossary.entrySet()) {
				Unit key=entry.getKey();
				Unit value=entry.getValue();
			
				result.append(getUnitContent(key)).append("\n");
				result.append(getUnitContent(value)).append("\n\n");
			}
			
		}else {	
			result.append("THERE ARE NO GLOSSARY WORDS\n");
		}
		return result.toString();
	}		
	
	@Override
	//throw exception if parsing is not done jet
	public String getGlossaryWordsView() throws ServiceException  {
		List<String> glossaryWords=getGlossaryWords();
		StringBuilder result=new StringBuilder();
		
		if(glossaryWords!=null) {
			result.append("GLOSSARY WORDS\n");
			
			for (String s:glossaryWords) {
				result.append(s).append("\n");
			}
			
		}else {	
			result.append("THERE ARE NO GLOSSARY WORDS\n");
		}
		
		return result.toString();
	}	
	
	//throw exception if parsing is not done jet
	private List<String> getGlossaryWords() throws ServiceException {
		Map<Unit,Unit> glossary=getBook().getGlossary();
		List<String> result=new ArrayList<>();

		for(Entry<Unit,Unit> entry: glossary.entrySet()) {
			String cur=getUnitContent(entry.getValue());
				
			if(cur.contains("–")) {
				String[]res=cur.split(" –");
				result.add(res[0]);		
			}else {	
				result.add(cur);
			}
		}
	
		return result;
	}
	
	@Override
	public String getView(Unit unit) {
		StringBuilder result=new StringBuilder();
		Iterator<Unit> iterator=unit.createIterator();
		
		while (iterator.hasNext()) {
			Unit curUnit=iterator.next();
			
			result.append(curUnit.getUnitView()).append("\n");
		}
		return result.toString();
	}

	@Override
	public String getUnitContent(Unit unit) {
		StringBuilder result=new StringBuilder();
		
		result.append(unit.getUnitContent());

		return result.toString();
	}

}
