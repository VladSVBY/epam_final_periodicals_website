package by.epam.periodicials_site.dao;

import java.sql.Connection;
import java.sql.SQLException;

import by.epam.periodicials_site.entity.User;

public interface UserDao {
	
	void create(User user);
	 
	User readByLoginOrEmailAndPassword(String loginOrEmail, String password);
	
	User read(int userId);
	
	void addToBalanceTransaction(int userId, double sum, Connection connection) throws SQLException;
	
	void removeFromBalanceTransaction(int userId, double sum, Connection connection);
}
