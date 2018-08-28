package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.SubscriptionService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class SubscribeToPublicationCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(SubscribeToPublicationCommand.class);
	
	private SubscriptionService subscriptionService = ServiceFactory.getSubscriptionService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int userId = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);
			int publicationId = Integer.parseInt(request.getParameter(REQUEST_PARAM_SUBSCRIPTION_ID_OF_PUBLICATION));
			int startMonth = Integer.parseInt(request.getParameter(REQUEST_PARAM_START_MONTH));
			int duration = Integer.parseInt(request.getParameter(REQUEST_PARAM_SUBSCRIPTION_DURATION));
		
			subscriptionService.create(userId, publicationId, startMonth, duration);
			response.sendRedirect(HttpUtil.getReferPage(request));
		} catch (ServiceException e) {
			logger.error("Exception making subscription", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}

	}

}
