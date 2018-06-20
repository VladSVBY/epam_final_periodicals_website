package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class ChangeLocaleCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String locale =  request.getParameter(REQUEST_PARAM_LOCALE);
		HttpSession session = request.getSession();
		session.setAttribute(SESSION_ATTR_LOCALE, locale);
		response.sendRedirect(HttpUtil.getReferPage(request));
	}

}
