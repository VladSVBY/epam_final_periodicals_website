package by.epam.periodicials_site.dao;

import by.epam.periodicials_site.dao.pool.ConnectionPoolException;
import by.epam.periodicials_site.entity.User;

public interface UserDao {
	
	void create(User user) throws ConnectionPoolException;
	 
	User readByLoginOrEmailAndPassword(String loginOrEmail, String password) throws ConnectionPoolException;
}
