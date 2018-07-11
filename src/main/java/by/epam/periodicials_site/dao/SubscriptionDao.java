package by.epam.periodicials_site.dao;

import java.util.List;

import by.epam.periodicials_site.entity.BalanceOperation;
import by.epam.periodicials_site.entity.Subscription;

public interface SubscriptionDao {
	
	void update(Subscription subscription) throws DaoException;
	
	Subscription read(int subscriptionId) throws DaoException;
	
	List<Subscription> readActiveForUser(int userId) throws DaoException;
	
	List<Subscription> readAllForUser(int userId) throws DaoException;
	
	void terminate(Subscription subscription, BalanceOperation balanceOperation) throws DaoException;

}
