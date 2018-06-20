package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.periodicials_site.entity.User;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.UserService;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class RegisterCommand implements Command {
	
	private UserService userService = ServiceFactory.getUserService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String referPage = HttpUtil.getReferPage(request);
		if (!referPage.endsWith(COMMAND_REGISTER)) {
			request.getRequestDispatcher(VIEW_REGISTER).forward(request, response);
		} else {
			User user = formUser(request);
			userService.registerUser(user);
			response.sendRedirect(request.getContextPath() + "/controller" + COMMAND_HOME);
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
