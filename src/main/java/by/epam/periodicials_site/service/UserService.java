package by.epam.periodicials_site.service;

import java.util.Date;
import java.util.List;

import by.epam.periodicials_site.entity.User;
import by.epam.periodicials_site.service.exception.ServiceException;

public interface UserService {
	
	User getUser(String loginOrEmail, String password) throws ServiceException;
	
	User getUser(int userId) throws ServiceException;
	
	void registerUser(User user) throws ServiceException;
	
	List<User> usersHavingSubscription(int publicationId, Date date) throws ServiceException;
	
}
