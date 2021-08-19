package by.epum.training.xml.xml_processing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import org.apache.log4j.Logger;

import by.epum.training.xml.domain.OldPostcard;
import by.epum.training.xml.domain.PostcardTheme;
import by.epum.training.xml.domain.PostcardType;
import by.epum.training.xml.domain.ValueType;

public class OldPostcardsXmlStaxCursorReader {
	private static final Logger LOGGER=Logger.getLogger(OldPostcardsXmlStaxCursorReader.class);
	
	public static List<OldPostcard> parseXML(String fileName) throws FileNotFoundException, XMLStreamException{
		List<OldPostcard> postcards = new ArrayList<>();
		OldPostcard op = null;	
		XMLStreamReader reader=null;

		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			reader = inputFactory.createXMLStreamReader(new FileInputStream(fileName));
			
			/**
			 * Returns an integer code for this event.
			 * 0-START_ELEMENT
			 * 1-END_ELEMENT
			 * 2-CHARACTERS
			 * 3-ATTRIBUTE
			 * 4-NAMESPACE
			 * 5-PROCESSING_INSTRUCTION
			 * 6-COMMENT
			 * 7-START_DOCUMENT
			 * 8-END_DOCUMENT
			 * 9-DTD
			 */
	
			while (reader.hasNext()) {
	
				int eventType = reader.next();
				
				System.out.println("eventType="+eventType);
				if (eventType == XMLEvent.START_ELEMENT) {	
					
					switch (reader.getLocalName()) {
						case "oldPostcard":
							op = new OldPostcard();
							String id = reader.getAttributeValue(null,"id");
							op.setId(Long.parseLong(id));
							
							LOGGER.info("\nID "+id);
							//System.out.println("ID "+id);
							break;
							
						case "description":
							 eventType = reader.next();
							 op.setDescription(reader.getText());
		                    
							 LOGGER.info("\nDESCRIPTION: "+reader.getText());
		                     //System.out.println("DESCRIPTION: "+reader.getText());
		                 
							break;
							
						case "theme":
							eventType = reader.next();
							op.setTheme(PostcardTheme.valueOf(reader.getText()));
							
							LOGGER.info("\nTHEME: "+reader.getText());
							//System.out.println("THEME: "+reader.getText());
							
							break;
							
						case "type":
							eventType = reader.next();
							op.setType(PostcardType.valueOf( reader.getText()));
							
							LOGGER.info("\nTYPE: "+reader.getText());
							//
							System.out.println("TYPE: "+reader.getText());
							break;
						case "isSent":
							eventType = reader.next();
							op.setSent(Boolean.getBoolean(reader.getText()));
							
							LOGGER.info("\nISSENT: "+reader.getText());
							//System.out.println("ISSENT: "+reader.getText());
							break;
							
						case "country":
							eventType = reader.next();
							op.setCountry(reader.getText());
							
							LOGGER.info("\nCOUNTRY: "+reader.getText());
							//System.out.println("COUNTRY: "+reader.getText());
							break;
							
						case "year":
							eventType = reader.next();
							op.setYear(Integer.parseInt(reader.getText()));
							
							LOGGER.info("\nYEAR: "+reader.getText());
							//System.out.println("YEAR: "+reader.getText());
							break;
							
						case "authors":
							eventType = reader.next();
							List<String> authors=new ArrayList<>();
							op.setAuthors(authors);
							
							LOGGER.info("AUTHORS empty list created");
							break;
							
						case "value":
							eventType = reader.next();
							op.setValue(ValueType.valueOf( reader.getText()));
							
							LOGGER.info("\nVALUE: "+reader.getText());
							//System.out.println("VALUE: "+reader.getText());
							break;	
							
						case "author":
							eventType = reader.next();
							op.getAuthors().add(reader.getText());
							
							LOGGER.info("\nAUTHOR: "+reader.getText());
							break;	
						}
				}
				if (eventType == XMLEvent.END_ELEMENT) {
				
					if (reader.getLocalName().equals("oldPostcard")) {	
						
						if(op.getAuthors()==null) {
							op.setAuthors(new ArrayList<>());
						}
						
						postcards.add(op);
						LOGGER.info("\nOldPostcard object created:\n"+op.toString());
					}
				}
			}
		} catch (FileNotFoundException exc) {
			LOGGER.error(exc);
			throw new FileNotFoundException(exc.getMessage());
		} catch (XMLStreamException ex) {
			LOGGER.error(ex);
			throw new XMLStreamException(ex.getMessage());
		}catch(FactoryConfigurationError e) {
			LOGGER.error(e);
			throw new FactoryConfigurationError(e.getMessage());
		}
		finally {
			try {
				reader.close();
			} catch (XMLStreamException e) {
				LOGGER.error(e);
				throw new XMLStreamException(e.getMessage());
			}
		}
		
		return postcards;
	}
}