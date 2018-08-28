package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.SubscriptionService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class TerminateSubscriptionCommand implements Command {
	
	private SubscriptionService subscriptionService = ServiceFactory.getSubscriptionService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int subscriptionId = Integer.parseInt(request.getParameter(REQUEST_PARAM_SUBSCRIPTION_ID));
		try {
			subscriptionService.terminateSubscription(subscriptionId);
		} catch (ServiceException e) {
			// TODO logger
			e.printStackTrace();
		}
		response.sendRedirect(HttpUtil.formRedirectUrl(request, COMMAND_SHOW_USER_SUBSCRIPTION_HISTORY));
	}

}
