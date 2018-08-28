package by.epam.periodicials_site.web.util;

import java.util.ResourceBundle;

import by.epam.periodicials_site.entity.LocaleType;

public final class MessageResolver {
	
	private MessageResolver() {}
	
	private static final ResourceBundle messagesEn = ResourceBundle.getBundle("localization/messages_en_US");
	private static final ResourceBundle messagesRu = ResourceBundle.getBundle("localization/messages_ru_BY");
	
	public static String getMessage(String messageName, LocaleType locale) {
		switch (locale) {
			case EN_US:
				return messagesEn.getString(messageName);
			case RU_BY:
				return messagesRu.getString(messageName);
			default:
				return messagesEn.getString(messageName);
		}
	}
			

}
