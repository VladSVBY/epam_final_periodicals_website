package by.epam.periodicials_site.dao;

import java.sql.Connection;
import java.sql.SQLException;

import by.epam.periodicials_site.entity.User;

public interface UserDao {
	
	void create(User user) throws DaoException;
	 
	User readByLoginOrEmailAndPassword(String loginOrEmail, String password) throws DaoException;
	
	User read(int userId) throws DaoException;
	
	void addToBalanceTransaction(int userId, double sum, Connection connection) throws DaoException;
	
	void removeFromBalanceTransaction(int userId, double sum, Connection connection) throws DaoException;
}
