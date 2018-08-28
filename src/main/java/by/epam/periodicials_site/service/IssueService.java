package by.epam.periodicials_site.service;

import java.util.List;

import by.epam.periodicials_site.entity.Issue;
import by.epam.periodicials_site.entity.Subscription;
import by.epam.periodicials_site.service.exception.ServiceException;

public interface IssueService {

	List<Issue> readForSubscription(Subscription subscription) throws ServiceException;
	
	Issue read(int id)  throws ServiceException;
	
	void create(Issue issue) throws ServiceException;
}
