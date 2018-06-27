package by.epam.periodicials_site.dao;

import by.epam.periodicials_site.dao.impl.PublicationDaoImpl;
import by.epam.periodicials_site.dao.impl.UserDaoImpl;

public final class DaoFactory {

	private DaoFactory() {}
	
	private static final UserDao USER_DAO = new UserDaoImpl();
	private static final PublicationDao PUBLICATION_DAO = new PublicationDaoImpl();
	
	public static UserDao getUserDao() {
		return USER_DAO;
	}
	
	public static PublicationDao getPublicationDao() {
		return PUBLICATION_DAO;
	}
}
