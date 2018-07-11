package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;
import by.epam.periodicials_site.entity.Theme;
import by.epam.periodicials_site.entity.dto.PublicationSearchCriteria;
import by.epam.periodicials_site.service.PublicationService;
import by.epam.periodicials_site.service.ServiceException;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.ThemeService;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class HomeCommand implements Command {
	
	private PublicationService publicationService = ServiceFactory.getPublicationService();
	private ThemeService themeService = ServiceFactory.getThemeService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LocaleType locale = HttpUtil.getLocale(request);
		List<Theme> themes = null;
		PublicationSearchCriteria criteria = formSerrchCriteria(request);
		List<Publication> publications = publicationService.readAll(criteria);
		try {
			themes = themeService.readAll(locale);
		} catch (ServiceException e) {
			// TODO logger
			e.printStackTrace();
		}
		
		request.setAttribute(REQUEST_ATTR_PUBLICATION_LIST, publications);
		request.setAttribute(REQUEST_ATTR_THEMES, themes);
		request.setAttribute(REQUEST_ATTR_PUBLICATION_SEARCH_CRITERIA, criteria);
		request.getRequestDispatcher(VIEW_HOME).forward(request, response);
	}
	
	private PublicationSearchCriteria formSerrchCriteria(HttpServletRequest request) {
		PublicationSearchCriteria criteria = new PublicationSearchCriteria();
		LocaleType locale = HttpUtil.getLocale(request);
		criteria.setLocale(locale);
		try {
			int themeId = Integer.parseInt(request.getParameter(REQUEST_PARAM_THEME_ID));
			int sortId = Integer.parseInt(request.getParameter(REQUEST_PARAM_SORT_ID));
			
			criteria.setThemeId(themeId);
			criteria.setOrderId(sortId);
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}
		return criteria;
	}
	
}
