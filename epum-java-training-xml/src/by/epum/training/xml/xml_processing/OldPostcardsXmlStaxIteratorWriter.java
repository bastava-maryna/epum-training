package by.epum.training.xml.xml_processing;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.apache.log4j.Logger;

import by.epum.training.xml.domain.OldPostcard;

public class OldPostcardsXmlStaxIteratorWriter {
	private static final Logger LOGGER=Logger.getLogger(OldPostcardsXmlStaxIteratorWriter.class);

	public static void writeToXml(List<OldPostcard> postcards, String outputFileName) throws FileNotFoundException, XMLStreamException  {
		XMLEventWriter writer=null;

		try {
			XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
			XMLEventFactory eventFactory = XMLEventFactory.newInstance();

			//Create a new XMLEventWriter that writes to a stream
			writer = outputFactory.createXMLEventWriter(new FileOutputStream(outputFileName));

			XMLEvent event = eventFactory.createStartDocument("UTF-8", "1.0");
			writer.add(event);

			writer.add(eventFactory.createStartElement("", "", "oldPostcards"));
			writer.add(eventFactory.createAttribute("xmlns","http://www.bastava_maryna.org/oldPostcardCatalogue"));
			writer.add(eventFactory.createAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance"));
			writer.add(eventFactory.createAttribute("xsi:schemaLocation","http://www.bastava_maryna.org/oldPostcardCatalogue oldPostcard.xsd"));

			for(OldPostcard op:postcards) {
				writer.add(eventFactory.createStartElement("", "", "oldPostcard"));
				writer.add(eventFactory.createAttribute("id", op.getId().toString()));

				writer.add(eventFactory.createStartElement("", "", "description"));
				writer.add(eventFactory.createCharacters(op.getDescription()));
				writer.add(eventFactory.createEndElement("", "", "description"));

				writer.add(eventFactory.createStartElement("", "", "theme"));
				writer.add(eventFactory.createCharacters(op.getTheme().toString()));
				writer.add(eventFactory.createEndElement("", "", "theme"));

				writer.add(eventFactory.createStartElement("", "", "type"));
				writer.add(eventFactory.createCharacters(op.getType().toString()));
				writer.add(eventFactory.createEndElement("", "", "type"));

				writer.add(eventFactory.createStartElement("", "", "isSent"));
				writer.add(eventFactory.createCharacters(String.valueOf(op.isSent())));
				writer.add(eventFactory.createEndElement("", "", "isSent"));

				writer.add(eventFactory.createStartElement("", "", "country"));
				writer.add(eventFactory.createCharacters(op.getCountry()));
				writer.add(eventFactory.createEndElement("", "", "country"));

				//year is optional
				if(op.getYear()!=0) {
					writer.add(eventFactory.createStartElement("", "", "year"));
					writer.add(eventFactory.createCharacters(String.valueOf(op.getYear())));
					writer.add(eventFactory.createEndElement("", "", "year"));
				}

				//authors is optional
				if(!op.getAuthors().isEmpty()) {
					writer.add(eventFactory.createStartElement("", "", "authors"));
					List<String> authors=op.getAuthors();
					//create author elements
					for(int i=0;i<authors.size();i++) {
						writer.add(eventFactory.createStartElement("", "", "author"));
						writer.add(eventFactory.createCharacters(authors.get(i)));
						writer.add(eventFactory.createEndElement("", "", "author"));
					}	
					writer.add(eventFactory.createEndElement("", "", "authors"));
				}	

				writer.add(eventFactory.createStartElement("", "", "value"));
				writer.add(eventFactory.createCharacters(op.getValue().toString()));
				writer.add(eventFactory.createEndElement("", "", "value"));

				writer.add(eventFactory.createEndElement("", "", "oldPostcard"));
			}

			writer.add(eventFactory.createEndDocument());

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
				writer.flush();
				writer.close();
			} catch (XMLStreamException e) {
				LOGGER.error(e);
				throw new XMLStreamException(e.getMessage());
			}
		}
	}
}
