package by.epam.periodicials_site.web.command;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;


import java.util.HashMap;
import java.util.Map;

import by.epam.periodicials_site.web.command.impl.AddReviewCommand;
import by.epam.periodicials_site.web.command.impl.ReplenishBalanceCommand;
import by.epam.periodicials_site.web.command.impl.TerminateSubscriptionCommand;
import by.epam.periodicials_site.web.command.impl.UserActiveSubscriptionsCommand;
import by.epam.periodicials_site.web.command.impl.UserBalanceOperationCommand;
import by.epam.periodicials_site.web.command.impl.UserProfileCommand;
import by.epam.periodicials_site.web.command.impl.UserSubscriptionHistoryCommand;

public final class CommandProvider {
	
	private static Map<String, Command> commands;
	
	static {
		commands = new HashMap<>();
		commands.put(COMMAND_SHOW_USER_PROFILE, new UserProfileCommand());
		commands.put(COMMAND_SHOW_USER_ACTIVE_SUBSCRIPTIONS, new UserActiveSubscriptionsCommand());
		commands.put(COMMAND_SHOW_USER_SUBSCRIPTION_HISTORY, new UserSubscriptionHistoryCommand());
		commands.put(COMMAND_SHOW_USER_BALANCE_OPERATION_HISTORY, new UserBalanceOperationCommand());
		commands.put(COMMAND_TERMINATE_SUBSCRIPTION, new TerminateSubscriptionCommand());
		commands.put(COMMAND_ADD_REVIEW, new AddReviewCommand());
		commands.put(COMMAND_REPLENISH_BALANCE, new ReplenishBalanceCommand());
		
		commands.putAll(CommandXmlParser.readCommands());
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
