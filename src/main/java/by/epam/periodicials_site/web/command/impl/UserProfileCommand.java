package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.UserService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;

public class UserProfileCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(UserProfileCommand.class);
	
	private UserService userService = ServiceFactory.getUserService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		try {
			int userId = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);
			
			request.setAttribute(REQUEST_ATTR_USER, userService.getUser(userId));
			request.getRequestDispatcher(VIEW_USER_PROFILE).forward(request, response);
		} catch (ServiceException e) {
			logger.error("Exception showing profile", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
	}

}
