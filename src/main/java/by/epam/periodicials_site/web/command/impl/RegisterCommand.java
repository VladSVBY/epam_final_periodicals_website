package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.User;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.UserService;
import by.epam.periodicials_site.service.exception.EmailAlreadyExistsException;
import by.epam.periodicials_site.service.exception.LoginAlreadyExistsException;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;
import by.epam.periodicials_site.web.util.MessageResolver;

public class RegisterCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(RegisterCommand.class);
	
	private static final String LOGIN_EXISTS_MESSAGE = "register.login_exists";
	private static final String EMAIL_EXISTS_MESSAGE = "register.email_exists";
	
	private UserService userService = ServiceFactory.getUserService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String referPage = HttpUtil.getReferPage(request);
		if (!referPage.endsWith(COMMAND_REGISTER)) {
			request.getRequestDispatcher(VIEW_REGISTER).forward(request, response);
		} else {
			LocaleType locale = HttpUtil.getLocale(request);
			try {
				User user = formUser(request);
				userService.registerUser(user);
				response.sendRedirect(HttpUtil.formRedirectUrl(request, COMMAND_LOGIN));
				
			} catch (LoginAlreadyExistsException e) {
				String message = MessageResolver.getMessage(LOGIN_EXISTS_MESSAGE, locale);
				request.setAttribute(FAIL_MESSAGE, message);
				request.getRequestDispatcher(VIEW_REGISTER).forward(request, response);
				
			} catch (EmailAlreadyExistsException e) {
				String message = MessageResolver.getMessage(EMAIL_EXISTS_MESSAGE, locale);
				request.setAttribute(FAIL_MESSAGE, message);
				request.getRequestDispatcher(VIEW_REGISTER).forward(request, response);
			}
			
			catch (ServiceException e) {
				logger.error("Exception registrating user", e);
				request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
			}	
		}
	}
	
	private User formUser(HttpServletRequest request) {
		User user = new User();
		user.setLogin(request.getParameter(REQUEST_PARAM_LOGIN));
		user.setPassword(request.getParameter(REQUEST_PARAM_PASSWORD));
		user.setName(request.getParameter(REQUEST_PARAM_NAME));
		user.setSurName(request.getParameter(REQUEST_PARAM_SURNAME));
		user.setEmail(request.getParameter(REQUEST_PARAM_EMAIL));
		return user;
	}
	
}
