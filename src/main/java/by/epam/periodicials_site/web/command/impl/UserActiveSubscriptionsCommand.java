package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;
import by.epam.periodicials_site.entity.Subscription;
import by.epam.periodicials_site.service.PublicationService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.SubscriptionService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class UserActiveSubscriptionsCommand implements Command {
	
	private SubscriptionService subscriptionService = ServiceFactory.getSubscriptionService();
	private PublicationService publicationService = ServiceFactory.getPublicationService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LocaleType locale = HttpUtil.getLocale(request);
		int userId = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);
		
		List<Subscription> subscriptions = null;
		Map<Integer, String> publicationNames = new HashMap<>();
		try {
			subscriptions = subscriptionService.readActiveForUser(userId);
			for (Subscription subscription : subscriptions) {
				Publication publication = publicationService.read(subscription.getPublicationId(), locale);
				publicationNames.put(publication.getId(), publication.getName());
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute(REQUEST_ATTR_ACTIVE_SUBSCRIPTION_LIST, subscriptions);
		request.setAttribute(REQUEST_ATTR_PUBLICATION_NAMES, publicationNames);
		request.getRequestDispatcher(VIEW_USER_ACTIVE_SUBSCRIPTIONS).forward(request, response);
	}

}
