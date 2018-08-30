
package by.epam.periodicials_site.web.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class LogoutCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String referPage = HttpUtil.getReferPage(request);
		
		request.getSession().invalidate();

		response.sendRedirect(referPage);
	}
	
}
