
package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.User;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.UserService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.service.exception.ValidationException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;
import by.epam.periodicials_site.web.util.MessageResolver;

public class LoginCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(LoginCommand.class);
	
	private static final String INVALID_DATA = "login.invalid_data";
	
	private UserService userService = ServiceFactory.getUserService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
			LocaleType locale = HttpUtil.getLocale(request);
			try {
				String referPage = HttpUtil.getReferPage(request);
				if (!referPage.contains(COMMAND_LOGIN) && !referPage.contains(COMMAND_REGISTER)){
					request.getSession().setAttribute(SESSION_ATTR_REFER_PAGE, referPage);
				} 
				if (referPage.contains(COMMAND_LOGIN)) {			
					String loginOrEmail = request.getParameter(REQUEST_PARAM_LOGIN_OR_EMAIL);
					String password = request.getParameter(REQUEST_PARAM_PASSWORD);
				
					User user = userService.getUser(loginOrEmail, password);
					
					if (user == null) {
						String message = MessageResolver.getMessage(INVALID_DATA, locale);
						request.setAttribute(FAIL_MESSAGE_ADD_PUBLICATION, message);
						request.getRequestDispatcher(VIEW_REGISTER).forward(request, response);
					} else {
						HttpSession session = request.getSession();
						session.setAttribute(SESSION_ATTR_USER_ID, user.getId());
						session.setAttribute(SESSION_ATTR_USER_NAME, user.getName());
						session.setAttribute(SESSION_ATTR_USER_ROLE, user.getRole());
						
						String path = (String) request.getSession().getAttribute(SESSION_ATTR_REFER_PAGE);
						path = (path != null) ? path : HttpUtil.formRedirectUrl(request, COMMAND_HOME);
						response.sendRedirect(path);
					}
				}
			} catch (ValidationException e) {
				String message = MessageResolver.getMessage(INVALID_DATA, locale);
				request.setAttribute(FAIL_MESSAGE_ADD_PUBLICATION, message);
				request.getRequestDispatcher(VIEW_REGISTER).forward(request, response);
			}
			catch (ServiceException e) {
				logger.error("Exception during log in", e);
				request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
			}
	}
	
}
