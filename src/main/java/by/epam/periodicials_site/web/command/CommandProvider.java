package by.epam.periodicials_site.web.command;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.util.Map;

public final class CommandProvider {
	
	private static Map<String, Command> commands;
	
	static {		
		commands = CommandXmlParser.readCommands();
	}
	
	public static Command defineCommand(String commandName) {
		Command command = commands.get(commandName);
		if (command == null) {
			command = commands.get(COMMAND_HOME);
		}
		return command;
	}
	
	private CommandProvider() {}

}
