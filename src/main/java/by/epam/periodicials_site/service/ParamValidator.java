package by.epam.periodicials_site.service;

import by.epam.periodicials_site.entity.User;

public final class ParamValidator {
	
	public static void validateUserParams(User user) throws ServiceException {
		if (user.getLogin() == null) throw new ServiceException("Login can't be null");
		if (user.getPassword() == null) throw new ServiceException("Password can't be null");
		if (user.getName() == null) throw new ServiceException("Name can't be null");
		if (user.getSurName() == null) throw new ServiceException("Surname can't be null");
		if (user.getEmail() == null) throw new ServiceException("Email can't be null");
	}

}
