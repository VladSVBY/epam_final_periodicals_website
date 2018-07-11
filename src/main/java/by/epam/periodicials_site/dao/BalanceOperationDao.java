package by.epam.periodicials_site.dao;

import java.sql.Connection;
import java.util.List;

import by.epam.periodicials_site.entity.BalanceOperation;

public interface BalanceOperationDao {
	
	List<BalanceOperation> readForUser(int userID) throws DaoException;
	
	void create(BalanceOperation balanceOperation) throws DaoException;
	
	void createTransaction(BalanceOperation balanceOperation, Connection connection) throws DaoException;

}
