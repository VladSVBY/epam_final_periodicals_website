package by.epam.periodicials_site.service.util;

import by.epam.periodicials_site.entity.BalanceOperation;
import by.epam.periodicials_site.entity.Review;
import by.epam.periodicials_site.entity.User;
import by.epam.periodicials_site.entity.dto.LocalizedPublication;
import by.epam.periodicials_site.entity.dto.LocalizedTheme;
import by.epam.periodicials_site.entity.dto.LocalizedType;

public final class Validator {
	
	private static final String EMPTY_STRING = "";
	private static final String EMAIL_REG_EX = "[A-Za-z.]{3,40}@[A-Za-z]{3,40}.[A-Za-z]{2,10}";
	
	private Validator() {}

	public static boolean stringIsNotNullAndNotEmpty(String... strings) {
		for (String string : strings) {
			if (string == null || EMPTY_STRING.equals(string)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean emailIsValid(String email) {
		return email.matches(EMAIL_REG_EX);
	}
	
	public static boolean userIsValid(User user) {
		if (stringIsNotNullAndNotEmpty(user.getEmail(), 
										user.getLogin(), 
										user.getPassword(), 
										user.getName(), 
										user.getSurName())
			&& user.getEmail().matches(EMAIL_REG_EX)) {
			return true;
		}
		return false;
	}
	
	public static boolean balanceOperationIsValid(BalanceOperation balanceOperation) {
		if (balanceOperation.getDate() != null){
			return true;
		}
		return false;
	}
	
	public static boolean reviewIsValid(Review review) {
		if (stringIsNotNullAndNotEmpty(review.getText()) && review.getDateOfPublication() != null) {
			return true;
		}
		return false;
	}
	
	public static boolean localizedPublicationIsValid(LocalizedPublication publication) {
		for (String description : publication.getDescriptions().values()) {
			if (description == null || EMPTY_STRING.equals(description)) {
				return false;
			}
		}
		for (String name : publication.getNames().values()) {
			if (name == null || EMPTY_STRING.equals(name)) {
				return false;
			}
		}
		if (publication.getPicturePath() == null || EMPTY_STRING.equals(publication.getPicturePath())) {
			return false;
		}	
		return true;
	}
	
	public static boolean localizedThemeIsValid(LocalizedTheme theme) {
		if (theme.getDefaultName() == null || EMPTY_STRING.equals(theme.getDefaultName())) {
			return false;
		}
		for (String name : theme.getLocalizedNames().values()) {
			if (name == null || EMPTY_STRING.equals(name)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean localizedTypeIsValid(LocalizedType type) {
		if (type.getDefaultName() == null || EMPTY_STRING.equals(type.getDefaultName())) {
			return false;
		}
		for (String name : type.getLocalizedNames().values()) {
			if (name == null || EMPTY_STRING.equals(name)) {
				return false;
			}
		}
		return true;
	}
}
