package by.epum.training.xml.xml_processing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.log4j.Logger;

import by.epum.training.xml.domain.OldPostcard;
import by.epum.training.xml.domain.PostcardTheme;
import by.epum.training.xml.domain.PostcardType;
import by.epum.training.xml.domain.ValueType;


public class OldPostcardsXmlStaxIteratorReader {
	private static final Logger LOGGER=Logger.getLogger(OldPostcardsXmlStaxIteratorReader.class);

	public static List<OldPostcard> parseXML(String fileName) throws XMLStreamException, FileNotFoundException {

		List<OldPostcard> postcards = new ArrayList<>();
		OldPostcard op = null;	
		XMLEventReader reader=null;
		
		try {
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

			//create EventReader and pass xml file to it
			reader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));

			//move along all elements(events) 
			while (reader.hasNext()) {
				//get event if exist
				XMLEvent event = reader.nextEvent();

				// stax api iterator EventTypes:
				//StartDocument         Namespace
				//StartElement			EntutyReference
				//EndElement			ProcessingInstruction
				//Characters			DTD
				//EndDocument			Comment
				//Attribute      
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();

					switch (startElement.getName().getLocalPart()) {
						case "oldPostcard":
							op = new OldPostcard();
							Attribute id = startElement.getAttributeByName(new QName("id"));
							op.setId(Long.parseLong(id.getValue()));
							
							LOGGER.info("\nID "+id.getValue());
							//System.out.println("ID "+id.getValue());
							break;

						case "description":
							event = reader.nextEvent();
							op.setDescription(event.asCharacters().getData());
							
							LOGGER.info("\nDESCRIPTION: "+event.asCharacters().getData());
							//System.out.println("DESCRIPTION: "+event.asCharacters().getData());
							break;

						case "theme":	
							event = reader.nextEvent();
							op.setTheme(PostcardTheme.valueOf( event.asCharacters().getData()));
							
							LOGGER.info("\nTHEME: "+event.asCharacters().getData());
							//System.out.println("THEME: "+event.asCharacters().getData());
							break;

						case "type":
							event = reader.nextEvent();
							op.setType(PostcardType.valueOf( event.asCharacters().getData()));
							
							LOGGER.info("\nTYPE: "+event.asCharacters().getData());
							//System.out.println("TYPE: "+event.asCharacters().getData());
							break;

						case "isSent":	
							event = reader.nextEvent();
							op.setSent(Boolean.getBoolean(event.asCharacters().getData()));
							
							LOGGER.info("\nISSENT: "+event.asCharacters().getData());
							//System.out.println("ISSENT: "+event.asCharacters().getData());
							break;
	
						case "country":	
							event = reader.nextEvent();
							op.setCountry(event.asCharacters().getData());
							
							LOGGER.info("\nCOUNTRY: "+event.asCharacters().getData());
							//System.out.println("COUNTRY: "+event.asCharacters().getData());
							break;

						case "year":
							event = reader.nextEvent();
							op.setYear(Integer.parseInt(event.asCharacters().getData()));
							
							LOGGER.info("\nYEAR: "+event.asCharacters().getData());
							//System.out.println("YEAR: "+event.asCharacters().getData());
							break;
	
						case "authors":
							event=reader.nextEvent();
							List<String> authors=new ArrayList<>();
							op.setAuthors(authors);
							
							LOGGER.info("AUTHORS empty list created");
							break;
	
						case "value":	
							event = reader.nextEvent();
							op.setValue(ValueType.valueOf( event.asCharacters().getData()));
							
							LOGGER.info("\nVALUE: "+event.asCharacters().getData());
							//System.out.println("VALUE: "+event.asCharacters().getData());
							break;

						case "author":
							event=reader.nextEvent();
							op.getAuthors().add(event.asCharacters().getData());
							
							LOGGER.info("\nAUTHOR: "+event.asCharacters().getData());
							break;
					}
				}

				//when reach OldPostCard EndElement , add this card to the list
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					
					if (endElement.getName().getLocalPart().equals("oldPostcard")) {
						
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