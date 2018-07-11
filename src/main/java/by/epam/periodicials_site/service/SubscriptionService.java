package by.epam.periodicials_site.service;

import java.util.List;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Subscription;

public interface SubscriptionService {
	
	List<Subscription> readActiveForUser(int userId);
	
	List<Subscription> readNonActiveForUser(int userId);
	
	void terminateSubscription(int subscriptionId) throws ServiceException;

}
