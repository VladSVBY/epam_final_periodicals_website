
package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.periodicials_site.entity.User;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.UserService;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class LoginCommand implements Command {
	
	private UserService userService = ServiceFactory.getUserService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String referPage = HttpUtil.getReferPage(request);
		if (!referPage.endsWith(COMMAND_LOGIN)) {
			request.getRequestDispatcher(VIEW_LOGIN).forward(request, response);
		} else {
			
			String loginOrEmail = request.getParameter(REQUEST_PARAM_LOGIN_OR_EMAIL);
			String password = request.getParameter(REQUEST_PARAM_PASSWORD);
			User user = userService.getUser(loginOrEmail, password);
			
			if (user == null) {
				request.setAttribute(REQUEST_ATTR_MSG_LOGIN_FAIL, "");
				request.getRequestDispatcher(VIEW_LOGIN).forward(request, response);
			} else {
				HttpSession session = request.getSession();
				session.setAttribute(SESSION_ATTR_USER_ID, user.getId());
				session.setAttribute(SESSION_ATTR_USER_NAME, user.getName());
				session.setAttribute(SESSION_ATTR_USER_ROLE, user.getRole());
				response.sendRedirect(HttpUtil.formRedirectUrl(request, COMMAND_HOME));
			}
		}
	}
	
}
