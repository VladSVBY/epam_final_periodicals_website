package by.epam.periodicials_site.service.impl;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.UserDao;
import by.epam.periodicials_site.entity.User;
import by.epam.periodicials_site.service.ParamValidator;
import by.epam.periodicials_site.service.ServiceException;
import by.epam.periodicials_site.service.UserService;

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
			if(ParamValidator.validateUserParams(user)) {
				userDao.create(user);
			}				
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
