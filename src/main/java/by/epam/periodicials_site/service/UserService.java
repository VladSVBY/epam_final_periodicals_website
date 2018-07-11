package by.epam.periodicials_site.service;

import by.epam.periodicials_site.entity.User;

public interface UserService {

	User getUser(String loginOrEmail, String password);
	
	User getUser(int userId);
	
	void registerUser(User user) throws ServiceException;
}
