package by.epam.periodicials_site.web.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Theme;
import by.epam.periodicials_site.service.ServiceException;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.ThemeService;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;
import by.epam.periodicials_site.web.util.WebConstantDeclaration;

public class GetAddPublicationFormCommand implements Command{
	
	private ThemeService themeService = ServiceFactory.getThemeService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			LocaleType locale = HttpUtil.getLocale(request);
			List<Theme> themes = themeService.readAll(locale);
			request.setAttribute(WebConstantDeclaration.REQUEST_ATTR_THEMES, themes);
			request.getRequestDispatcher(WebConstantDeclaration.VIEW_ADD_PUBLICATION_FORM).forward(request, response);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
