package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;
import by.epam.periodicials_site.entity.Subscription;
import by.epam.periodicials_site.service.PublicationService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.SubscriptionService;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class UserSubscriptionHistoryCommand implements Command {
	
	private SubscriptionService subscriptionService = ServiceFactory.getSubscriptionService();
	private PublicationService publicationService = ServiceFactory.getPublicationService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LocaleType locale = HttpUtil.getLocale(request);
		int userId = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);
		
		List<Subscription> subscriptions = subscriptionService.readNonActiveForUser(userId);
		java.util.Map<Integer, String> publicationNames = new HashMap<>();
		for (Subscription subscription : subscriptions) {
			Publication publication = publicationService.read(subscription.getPublicationId(), locale);
			publicationNames.put(publication.getId(), publication.getName());
		}
		
		request.setAttribute(REQUEST_ATTR_SUBSCRIPTION_LIST, subscriptions);
		request.setAttribute(REQUEST_ATTR_PUBLICATION_NAMES, publicationNames);
		request.getRequestDispatcher(VIEW_USER_SUBSCRIPTION_HISTORY).forward(request, response);
	}

}
