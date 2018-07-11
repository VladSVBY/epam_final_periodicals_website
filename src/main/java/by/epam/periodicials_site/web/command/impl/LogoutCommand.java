
package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class LogoutCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String referPage = HttpUtil.getReferPage(request);
		HttpSession session = request.getSession();
		session.removeAttribute(SESSION_ATTR_USER_ID);
		session.removeAttribute(SESSION_ATTR_USER_NAME);
		session.removeAttribute(SESSION_ATTR_USER_ROLE);
		response.sendRedirect(referPage);
	}
	
}
