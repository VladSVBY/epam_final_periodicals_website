package by.epam.periodicials_site.service.impl;

import java.util.List;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.IssueDao;
import by.epam.periodicials_site.entity.Issue;
import by.epam.periodicials_site.entity.Subscription;
import by.epam.periodicials_site.entity.User;
import by.epam.periodicials_site.entity.dto.LocalizedPublication;
import by.epam.periodicials_site.service.IssueService;
import by.epam.periodicials_site.service.PublicationService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.UserService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.service.util.MailSender;

public class IssueServiceImpl implements IssueService{
	
	private IssueDao issueDao = DaoFactory.getIssueDao();
	private UserService userService = ServiceFactory.getUserService();
	private PublicationService publicationService = ServiceFactory.getPublicationService();

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
			
			List<User> users = userService.usersHavingSubscription(issue.getPublicationId(), issue.getDateOfPublication());
			LocalizedPublication localizedPublication = publicationService.readLocalized(issue.getPublicationId());
			
			MailSender.sendNotifications(users, issue, localizedPublication);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
	}

}
