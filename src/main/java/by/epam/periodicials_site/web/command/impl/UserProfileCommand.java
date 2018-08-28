package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.UserService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;

public class UserProfileCommand implements Command {
	
	private UserService userService = ServiceFactory.getUserService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);
		
		try {
			request.setAttribute(REQUEST_ATTR_USER, userService.getUser(userId));
		} catch (ServiceException e) {
			// TODO logger
			e.printStackTrace();
		}
		
		request.getRequestDispatcher(VIEW_USER_PROFILE).forward(request, response);
	}

}
