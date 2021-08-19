package by.epum.training.xml.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


public class PropertyFileHandler {
	private static final Logger LOGGER=Logger.getLogger(PropertyFileHandler.class);
	
	private static Properties prop=new Properties();
	
	public static void getPropertiesFromFile() throws FileNotFoundException,IOException{
		try {
			FileInputStream inputStream=new FileInputStream(new File("src\\resource\\config.properties"));
			prop.load(inputStream);
			LOGGER.info("Properties are loaded");
			
		}catch(FileNotFoundException e) {
			LOGGER.error(e);
			throw new FileNotFoundException (e.getMessage());
		}catch(IOException e) {
			LOGGER.error(e);
			throw new IOException (e.getMessage());
		}
		
	}
	
	public static String getPropertyByName(String propertyName) {
		return prop.getProperty(propertyName);
	}
		
}
