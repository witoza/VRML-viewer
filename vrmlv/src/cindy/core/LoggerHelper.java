package cindy.core;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

/**
 * Utility class which is helpfull to proper initialize logging facility log4j
 * 
 */

public class LoggerHelper {
	static public void initializeLoggingFacility() {
		Properties log4jProp = new Properties();

		log4jProp.put("log4j.logger.malkoln", "DEBUG, stdout1");		
		
		log4jProp.put("log4j.appender.stdout1.layout","org.apache.log4j.PatternLayout"); 
		log4jProp.put("log4j.appender.stdout1.layout.ConversionPattern","[%t] %p %m (%F:%L)%n");
		log4jProp.put("log4j.appender.stdout1","org.apache.log4j.ConsoleAppender"); 

		PropertyConfigurator.configure(log4jProp);
	}
}
