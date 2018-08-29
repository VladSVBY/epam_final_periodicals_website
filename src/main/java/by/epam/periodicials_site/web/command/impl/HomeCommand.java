package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;
import by.epam.periodicials_site.entity.Theme;
import by.epam.periodicials_site.entity.Type;
import by.epam.periodicials_site.entity.dto.PublicationSearchCriteria;
import by.epam.periodicials_site.service.PublicationService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.ThemeService;
import by.epam.periodicials_site.service.TypeService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class HomeCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(HomeCommand.class);
	
	private static final int THEME_DEFAULT = 0;
	private static final int TYPE_DEFAULT = 0;
	private static final int SORT_DEFAULT = 0;
	private static final int CURRENT_PAGE_DEFAULT = 1;
	private static final int ITEMS_PER_PAGE_DEFAULT = 10;
	
	private PublicationService publicationService = ServiceFactory.getPublicationService();
	private ThemeService themeService = ServiceFactory.getThemeService();
	private TypeService typeService = ServiceFactory.getTypeService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		try {
			LocaleType locale = HttpUtil.getLocale(request);
			PublicationSearchCriteria criteria = formSerrchCriteria(request);
			
			List<Publication> publications = publicationService.readAll(criteria);
			List<Theme> themes = themeService.readAll(locale);
			List<Type> types = typeService.readAll(locale);
			
			request.setAttribute(REQUEST_ATTR_PUBLICATION_LIST, publications);
			request.setAttribute(REQUEST_ATTR_THEMES, themes);
			request.setAttribute(REQUEST_ATTR_TYPES, types);
			request.setAttribute(REQUEST_ATTR_PUBLICATION_SEARCH_CRITERIA, criteria);
			request.getRequestDispatcher(VIEW_HOME).forward(request, response);
		} catch (ServiceException e) {
			logger.error("Exception showinf home page", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
		
		
	}
	
	private PublicationSearchCriteria formSerrchCriteria(HttpServletRequest request) {
		PublicationSearchCriteria criteria = new PublicationSearchCriteria();
		LocaleType locale = HttpUtil.getLocale(request);
		
		int themeId;
		int typeId;
		int sortId;
		int currentPage;
		int itemsPerPage;
		try {
			themeId = Integer.parseInt(request.getParameter(REQUEST_PARAM_THEME_ID));
			typeId = Integer.parseInt(request.getParameter(REQUEST_PARAM_TYPE_ID));
			sortId = Integer.parseInt(request.getParameter(REQUEST_PARAM_SORT_ID));
			currentPage = Integer.parseInt(request.getParameter(REQUEST_PARAM_CURRENT_PAGE));
			itemsPerPage = Integer.parseInt(request.getParameter(REQUEST_PARAM_ITEMS_PER_PAGE));
		} catch (NumberFormatException e) {
			themeId = THEME_DEFAULT;
			typeId = TYPE_DEFAULT;
			sortId = SORT_DEFAULT;
			currentPage = CURRENT_PAGE_DEFAULT;
			itemsPerPage = ITEMS_PER_PAGE_DEFAULT;
		}
		
		criteria.setLocale(locale);
		criteria.setThemeId(themeId);
		criteria.setTypeId(typeId);
		criteria.setOrderId(sortId);
		criteria.setCurrentPage(currentPage);
		criteria.setItemsPerPage(itemsPerPage);
		return criteria;
	}
	
}
