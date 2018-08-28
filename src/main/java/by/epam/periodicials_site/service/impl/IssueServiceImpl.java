package by.epam.periodicials_site.service.impl;

import java.util.List;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.IssueDao;
import by.epam.periodicials_site.entity.Issue;
import by.epam.periodicials_site.entity.Subscription;
import by.epam.periodicials_site.service.IssueService;
import by.epam.periodicials_site.service.exception.ServiceException;

public class IssueServiceImpl implements IssueService{
	
	private IssueDao issueDao = DaoFactory.getIssueDao();

	@Override
	public List<Issue> readForSubscription(Subscription subscription) throws ServiceException {
		try {			
			return issueDao.readForPublicationBetweenDates(subscription.getPublicationId(), subscription.getStartDate(), subscription.getEndDate());
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Issue read(int id) throws ServiceException {
		try {
			return issueDao.read(id);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void create(Issue issue) throws ServiceException {
		try {
			issueDao.create(issue);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
	}

}
