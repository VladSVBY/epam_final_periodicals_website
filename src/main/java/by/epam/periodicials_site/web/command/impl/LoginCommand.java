
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
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class LoginCommand implements Command {
	
	private UserService userService = ServiceFactory.getUserService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String referPage = HttpUtil.getReferPage(request);
		if (!referPage.contains(COMMAND_LOGIN) && !referPage.contains(COMMAND_REGISTER)){
			request.getSession().setAttribute(SESSION_ATTR_REFER_PAGE, referPage);
		} 
		if (referPage.contains(COMMAND_LOGIN)) {			
			String loginOrEmail = request.getParameter(REQUEST_PARAM_LOGIN_OR_EMAIL);
			String password = request.getParameter(REQUEST_PARAM_PASSWORD);
			User user = null;
			
			try {
				System.out.println(password);
				user = userService.getUser(loginOrEmail, password);
			} catch (ServiceException e) {
				// logger
				e.printStackTrace();
			}
			
			if (user == null) {
				request.setAttribute(REQUEST_ATTR_MSG_LOGIN_FAIL, "");
				request.getRequestDispatcher(VIEW_LOGIN).forward(request, response);
			} else {
				HttpSession session = request.getSession();
				session.setAttribute(SESSION_ATTR_USER_ID, user.getId());
				session.setAttribute(SESSION_ATTR_USER_NAME, user.getName());
				session.setAttribute(SESSION_ATTR_USER_ROLE, user.getRole());
				
				String path = (String) request.getSession().getAttribute(SESSION_ATTR_REFER_PAGE);
				path = (path != null) ? path : HttpUtil.formRedirectUrl(request, COMMAND_HOME);
				response.sendRedirect(path);
			}
		} else {
			request.getRequestDispatcher(VIEW_LOGIN).forward(request, response);
		}
	}
	
}
