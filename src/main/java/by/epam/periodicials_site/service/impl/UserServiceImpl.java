package by.epam.periodicials_site.service.impl;

import java.util.Date;
import java.util.List;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.UserDao;
import by.epam.periodicials_site.entity.User;
import by.epam.periodicials_site.service.UserService;
import by.epam.periodicials_site.service.exception.EmailAlreadyExistsException;
import by.epam.periodicials_site.service.exception.LoginAlreadyExistsException;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.service.exception.ValidationException;
import by.epam.periodicials_site.service.util.Validator;

public class UserServiceImpl implements UserService{
	
	private UserDao userDao = DaoFactory.getUserDao();

	@Override
	public User getUser(String loginOrEmail, String password) throws ServiceException {
		if (!Validator.stringIsNotNullAndNotEmpty(loginOrEmail, password) ) {
			throw new ValidationException("Data is not valid");
		}
		try {
			return userDao.readByLoginOrEmailAndPassword(loginOrEmail, password);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void registerUser(User user) throws ServiceException {
		if (Validator.userIsValid(user)) {
			throw new ValidationException("User data is not valid");
		}
		try {
			if (userDao.loginExists(user.getLogin())) {
				throw new LoginAlreadyExistsException("Login already exists");
			}
			if (userDao.emailExists(user.getEmail())) {
				throw new EmailAlreadyExistsException("Email already exists");
			}
			userDao.create(user);			
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public User getUser(int userId) throws ServiceException {
		try {
			return userDao.read(userId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<User> usersHavingSubscription(int publicationId, Date date) throws ServiceException {
		try {
			return userDao.readUsersHavingSuscription(publicationId, date);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	

}
