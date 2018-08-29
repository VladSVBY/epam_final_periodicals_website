package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.Issue;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;
import by.epam.periodicials_site.entity.Subscription;
import by.epam.periodicials_site.service.IssueService;
import by.epam.periodicials_site.service.PublicationService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.SubscriptionService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class ShowIssuesCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(ShowIssuesCommand.class);
	
	private SubscriptionService subscriptionService = ServiceFactory.getSubscriptionService();
	private IssueService issueService = ServiceFactory.getIssueService();
	private PublicationService publicationService = ServiceFactory.getPublicationService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			LocaleType locale = HttpUtil.getLocale(request);
			int userId = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);		
			int subscriptionId = Integer.parseInt(request.getParameter(REQUEST_PARAM_SUBSCRIPTION_ID));
			Subscription subscription = subscriptionService.read(subscriptionId);
			Publication publication = publicationService.read(subscription.getPublicationId(), locale);
			
			List<Issue> issues = Collections.emptyList();
			if (subscription.getUserId() == userId) {
				issues = issueService.readForSubscription(subscription);
			}
			
			request.setAttribute(REQUEST_ATTR_ISSUES, issues);
			request.setAttribute(REQUEST_ATTR_PUBLICATION, publication);
			request.getRequestDispatcher(VIEW_AVALIABLE_ISSUES).forward(request, response);
		} catch (ServiceException e) {
			logger.error("Exception showing issues", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
	}

}
