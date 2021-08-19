package by.epum.training.xml.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import by.epum.training.xml.domain.OldPostcard;
import by.epum.training.xml.domain.PostcardTheme;
import by.epum.training.xml.domain.PostcardType;
import by.epum.training.xml.domain.ValueType;
import by.epum.training.xml.xml_processing.OldPostcardsXmlStaxCursorReader;
import by.epum.training.xml.xml_processing.OldPostcardsXmlStaxIteratorReader;
import by.epum.training.xml.xml_processing.XmlValidator;

public class Main {
	private static final Logger LOGGER=Logger.getLogger(Main.class);

	public static void main(String[] args) {

		try {
			PropertyFileHandler.getPropertiesFromFile();

			String xmlFileName = PropertyFileHandler.getPropertyByName("xmlFileName");
			LOGGER.info("xmlFileName="+xmlFileName);
			File xmlFile=new File(xmlFileName);

			String xsdFileName=PropertyFileHandler.getPropertyByName("xsdFileName");
			LOGGER.info("xsdFileName="+xsdFileName);
			File xsdFile=new File(xsdFileName);

			XmlValidator.validate(xmlFile,xsdFile);
			
			
			List<OldPostcard> postcards = OldPostcardsXmlStaxIteratorReader.parseXML(xmlFileName);
		//	List<OldPostcard> postcards = OldPostcardsXmlStaxCursorReader.parseXML(xmlFileName);
			
			System.out.println("***********************");
			for (OldPostcard op : postcards) {
				System.out.println(op.toString());
				System.out.println("***********************");
			}
			
			OldPostcard newCard=new OldPostcard(Long.valueOf(333333333333333L), 
												"Old building with beatiful construction elements", 
												PostcardTheme.ARCHITECTURE, PostcardType.CONGRATULATORY,
												true, "Russia", 1960,List.of("Ivan Grozniy", "Boris Godunov"),
												ValueType.HISTORICAL);
			
			postcards.add(newCard);
			
			System.out.println("New oldPostcard added");
			System.out.println("***********************");
			for (OldPostcard op : postcards) {
				System.out.println(op.toString());
				System.out.println("***********************");
			}
			
			//sort by value, if equal-by id
			Collections.sort(postcards, new Comparator<OldPostcard>() {
				@Override
				public int compare(OldPostcard o1, OldPostcard o2) {
					int res=o1.getValue().compareTo(o2.getValue());
					if(res==0) {
						res=o1.getId().compareTo(o2.getId());
					}
					return res;
				}
			});
			
			System.out.println("After sorting");
			System.out.println("***********************");
			for (OldPostcard op : postcards) {
				System.out.println(op.toString());
				System.out.println("***********************");
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException|XMLStreamException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println("StopExecution. XML file is not valid\n"+e.getMessage());
		}
	}
}
