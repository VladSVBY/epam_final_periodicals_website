package by.epam.periodicials_site.dao;

import by.epam.periodicials_site.dao.impl.UserDaoImpl;

public final class DaoFactory {

	private DaoFactory() {}
	
	public static UserDao getUserDao() {
		return new UserDaoImpl();
	}
}
