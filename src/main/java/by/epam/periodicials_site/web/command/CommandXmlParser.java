package by.epam.periodicials_site.web.command;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class CommandXmlParser {
	
	private static final String FILE_PATH = "/commands.xml";
	
	private static final String COMMAND_TAG = "command";
	private static final String CLASS_TAG = "class";
	private static final String URL_PATTERN_TAG = "url-pattern";

	public static Map<String, Command> readCommands(){	
		Map<String, Command> commands = new HashMap<>();
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	        InputStream inputStream = classLoader.getResourceAsStream(FILE_PATH);
	        Document document = builder.parse(inputStream);
	        
	        NodeList commandNodes = document.getDocumentElement().getElementsByTagName(COMMAND_TAG);
	        for (int i = 0; i < commandNodes.getLength(); i++) {
	        	Element element = (Element) commandNodes.item(i);
	        	String urlPattern = getUrlPattern(element);
	        	Command command = getCommand(element);
	        	commands.put(urlPattern, command);
            }
	        
		} catch (ParserConfigurationException | SAXException | IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO logger
			throw new XmlCommandParsingException("Exception during command parsing", e);
		}
		return commands;
	}
	
	private static String getUrlPattern(Element element) {
		NodeList childNodes = element.getElementsByTagName(URL_PATTERN_TAG);
		if (childNodes.getLength() == 0) {
			throw new XmlCommandParsingException("Command url-pattern missed");
		}
		return childNodes.item(0).getTextContent();
	}
	
	private static Command getCommand(Element element) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		NodeList childNodes = element.getElementsByTagName(CLASS_TAG);
		if (childNodes.getLength() == 0) {
			throw new XmlCommandParsingException("Command class missed");
		}
		String className = childNodes.item(0).getTextContent();
		Class<?> commandClass = Class.forName(className);
		return (Command) commandClass.getDeclaredConstructor().newInstance();
	}
}
