package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.WebConstantDeclaration;

public class ShowResultPageCommand implements Command{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String message = request.getParameter(REQUEST_PARAM_MESSAGE);
			String returnPage = request.getParameter(REQUEST_PARAM_RETURN_PAGE);
			
			request.setAttribute(WebConstantDeclaration.REQUEST_ATTR_MESSAGE, message);
			request.setAttribute(WebConstantDeclaration.REQUEST_ATTR_RETURN_PAGE, returnPage);
			request.getRequestDispatcher(WebConstantDeclaration.VIEW_RESULT_PAGE).forward(request, response);	
	}

}
