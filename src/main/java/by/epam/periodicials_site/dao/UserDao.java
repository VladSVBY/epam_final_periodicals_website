package by.epam.periodicials_site.dao;

import by.epam.periodicials_site.entity.User;

public interface UserDao {
	
	void create(User user);
	 
	User readByLoginOrEmailAndPassword(String loginOrEmail, String password);
}
