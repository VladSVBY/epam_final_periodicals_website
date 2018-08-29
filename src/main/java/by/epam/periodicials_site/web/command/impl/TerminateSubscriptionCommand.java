package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.SubscriptionService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;
import by.epam.periodicials_site.web.util.MessageResolver;

public class TerminateSubscriptionCommand implements Command {
	
	private SubscriptionService subscriptionService = ServiceFactory.getSubscriptionService();
	
private static final Logger logger = LogManager.getLogger(TerminateSubscriptionCommand.class);
	
	private static final String SUCCESS_MESSAGE_KEY = "subscription.teminate_succcess";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LocaleType locale = HttpUtil.getLocale(request);
		try {
			int subscriptionId = Integer.parseInt(request.getParameter(REQUEST_PARAM_SUBSCRIPTION_ID));
			
			subscriptionService.terminateSubscription(subscriptionId);
			
			String message = MessageResolver.getMessage(SUCCESS_MESSAGE_KEY, locale);
			String returnPage = HttpUtil.getReferPage(request);
			String path = HttpUtil.formRedirectUrl(request, COMMAND_SHOW_RESULT_PAGE);
			path = HttpUtil.addParamToPath(path, REQUEST_ATTR_MESSAGE, message);
			path = HttpUtil.addParamToPath(path, REQUEST_ATTR_RETURN_PAGE, returnPage);
		} catch (ServiceException e) {
			logger.error("Exception terminating subscription", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
	}

}
