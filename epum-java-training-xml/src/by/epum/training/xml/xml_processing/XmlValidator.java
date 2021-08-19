package by.epum.training.xml.xml_processing;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;


public class XmlValidator {
	private static final Logger LOGGER=Logger.getLogger(XmlValidator.class);

	public static boolean validate(File xmlFile, File schemaFile) throws IOException, SAXException {
		SchemaFactory factory=SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		try {
			//create StreamSource which represent xml input from Schema instance
			Source schemaFileSource=new StreamSource(schemaFile);
			//Parses the source as a schema and returns it as a schema.
			Schema schema=factory.newSchema(schemaFileSource);

			Validator validator=schema.newValidator();
			Source xmlFileSource=new StreamSource(xmlFile);

			validator.validate(xmlFileSource);	
			
			LOGGER.info(xmlFileSource.getSystemId()+" is valid");
			
			return true;
		}catch(SAXException e) {
			LOGGER.error("xml file is INVALID");
			throw new SAXException(e.getMessage());
		}catch(IOException ex) {
			LOGGER.error("IO Exception"+ex.getMessage());
			throw new IOException(ex.getMessage());
		}
	}
}
