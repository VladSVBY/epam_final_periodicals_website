package by.epam.periodicials_site.web.command;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.util.HashMap;
import java.util.Map;

import by.epam.periodicials_site.web.command.impl.ChangeLocaleCommand;
import by.epam.periodicials_site.web.command.impl.HomeCommand;
import by.epam.periodicials_site.web.command.impl.LoginCommand;
import by.epam.periodicials_site.web.command.impl.LogoutCommand;
import by.epam.periodicials_site.web.command.impl.RegisterCommand;

public final class CommandProvider {
	
	private static Map<String, Command> commands;
	
	static {
		commands = new HashMap<>();
		commands.put(COMMAND_LOGIN, new LoginCommand());
		commands.put(COMMAND_CHANGE_LOCALE, new ChangeLocaleCommand());
		commands.put(COMMAND_REGISTER, new RegisterCommand());
		commands.put(COMMAND_HOME, new HomeCommand());
		commands.put(COMMAND_LOGOUT, new LogoutCommand());
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
