package by.epam.periodicials_site.service.impl;

import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.UserDao;
import by.epam.periodicials_site.dao.pool.ConnectionPoolException;
import by.epam.periodicials_site.entity.User;
import by.epam.periodicials_site.service.UserService;

public class UserServiceImpl implements UserService{
	
	private UserDao userDao = DaoFactory.getUserDao();

	@Override
	public User getUser(String loginOrEmail, String password) {
		try {
			return userDao.readByLoginOrEmailAndPassword(loginOrEmail, password);
		} catch (ConnectionPoolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
