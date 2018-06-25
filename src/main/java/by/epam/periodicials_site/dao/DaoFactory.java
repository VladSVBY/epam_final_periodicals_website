package by.epam.periodicials_site.dao;

import by.epam.periodicials_site.dao.impl.UserDaoImpl;

public final class DaoFactory {

	private DaoFactory() {}
	
	private static final UserDao USER_DAO = new UserDaoImpl();
	
	public static UserDao getUserDao() {
		return USER_DAO;
	}
}
