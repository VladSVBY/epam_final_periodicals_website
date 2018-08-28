package by.epam.periodicials_site.service.impl;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.UserDao;
import by.epam.periodicials_site.entity.User;
import by.epam.periodicials_site.service.UserService;
import by.epam.periodicials_site.service.exception.EmailAlreadyExistsException;
import by.epam.periodicials_site.service.exception.LoginAlreadyExistsException;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.service.util.ParamValidator;

public class UserServiceImpl implements UserService{
	
	private UserDao userDao = DaoFactory.getUserDao();

	@Override
	public User getUser(String loginOrEmail, String password) throws ServiceException {
			try {
				return userDao.readByLoginOrEmailAndPassword(loginOrEmail, password);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
	}

	@Override
	public void registerUser(User user) throws ServiceException {
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
	
	

}
