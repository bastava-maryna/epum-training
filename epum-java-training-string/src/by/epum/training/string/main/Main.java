package by.epum.training.string.main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import by.epum.training.string.entity.Book;
import by.epum.training.string.servise.BookService;
import by.epum.training.string.servise.ServiceProvider;
import by.epum.training.string.servise.exception.ServiceException;



public class Main {

	public static void main(String[] args)  {
		String path="src\\resource\\book.txt";
		ServiceProvider serviceProvider=ServiceProvider.getInstance();
		BookService bookService=serviceProvider.getBookService();
		StringBuilder result=new StringBuilder();
		try {
			bookService.parseBook(path);
			
			Book book=bookService.getBook();
			result.append("book title=").append(book.getTitle()).append("\n");
			result.append("book authors=").append(book.getAuthors()).append("\n\n");
			//System.out.println("book title="+ book.getTitle());
			//System.out.println("book authors="+ book.getAuthors());
			
			String tableOfContent=bookService.getTableOfContent();
			result.append("TABLE of CONTENT\n").append(tableOfContent).append("\n\n");
			//System.out.println("TABLE of CONTENT\n"+tableOfContent);
			
			String glossary=bookService.getGlossaryView();
			result.append(glossary).append("\n");
			//System.out.println(glossary);
			
			String glossaryWords=bookService.getGlossaryWordsView();
			result.append(glossaryWords).append("\n");
			//System.out.println(glossaryWords);
			
			String annotationView=bookService.getView(book.getAnnotation());
			result.append("ANNOTATION VIEW\n").append(annotationView).append("\n");
			//System.out.println("VIEW\n"+annotationView);
			
			String annotation=bookService.getUnitContent(book.getAnnotation());
			result.append("ANNOTATION PART BEFORE MODIFICATION:\n\n").append(annotation).append("\n");
			//System.out.println("ANNOTATION PART BEFORE MODIFICATION\n"+annotation);
			
			String annotationModified=bookService.modifyText(book.getAnnotation(),2);
			result.append("MODIFIED TEXT:\n\n").append(annotationModified).append("\n");
			//System.out.println("MODIFIED TEXT:\n"+annotationModified);
			
			Files.writeString(Paths.get("src\\resource\\result.txt"), result.toString(),StandardCharsets.UTF_8);
			System.out.println("END OF WORK FOR TODAY");
			
		} catch (ServiceException e) {
			System.out.println(e);
			System.out.println("Ask developer to fix problem");
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Problem with write to file");
		}
		
		
	}
}
