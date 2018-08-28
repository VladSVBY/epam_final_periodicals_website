package by.epam.periodicials_site.service;

import java.util.List;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Subscription;
import by.epam.periodicials_site.service.exception.ServiceException;

public interface SubscriptionService {
	
	List<Subscription> readActiveForUser(int userId);
	
	List<Subscription> readNonActiveForUser(int userId) throws ServiceException;
	
	void terminateSubscription(int subscriptionId) throws ServiceException;
	
	Subscription read(int id) throws ServiceException;
	
	void create(int userId, int publicationId, int startMonth, int duration) throws ServiceException;

}
