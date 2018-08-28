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
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.ThemeService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class HomeCommand implements Command {
	
	private static final int THEME_DEFAULT = 0;
	private static final int TYPE_DEFAULT = 0;
	private static final int CURRENT_PAGE_DEFAULT = 1;
	private static final int ITEMS_PER_PAGE_DEFAULT = 10;
	
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
		
		int themeId = THEME_DEFAULT;
		int sortId = TYPE_DEFAULT;
		int currentPage = CURRENT_PAGE_DEFAULT;
		int itemsPerPage = ITEMS_PER_PAGE_DEFAULT;
		try {
			themeId = Integer.parseInt(request.getParameter(REQUEST_PARAM_THEME_ID));
			sortId = Integer.parseInt(request.getParameter(REQUEST_PARAM_SORT_ID));
			currentPage = Integer.parseInt(request.getParameter(REQUEST_PARAM_CURRENT_PAGE));
			itemsPerPage = Integer.parseInt(request.getParameter(REQUEST_PARAM_ITEMS_PER_PAGE));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		criteria.setLocale(locale);
		criteria.setThemeId(themeId);
		criteria.setOrderId(sortId);
		criteria.setCurrentPage(currentPage);
		criteria.setItemsPerPage(itemsPerPage);
		return criteria;
	}
	
}
