package by.epam.periodicials_site.service;

import by.epam.periodicials_site.entity.User;

public final class ParamValidator {
	
	public static boolean validateUserParams(User user){
		if (user.getLogin() == null || "".equals(user.getLogin())) return false;
		if (user.getPassword() == null || "".equals(user.getPassword())) return false;
		if (user.getName() == null || "".equals(user.getName())) return false;
		if (user.getSurName() == null || "".equals(user.getSurName())) return false;
		if (user.getEmail() == null || "".equals(user.getEmail())) return false;
		return true;
	}

}
