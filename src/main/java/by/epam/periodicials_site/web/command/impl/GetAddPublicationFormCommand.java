package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.VIEW_503_ERROR;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Theme;
import by.epam.periodicials_site.entity.Type;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.ThemeService;
import by.epam.periodicials_site.service.TypeService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;
import by.epam.periodicials_site.web.util.WebConstantDeclaration;

public class GetAddPublicationFormCommand implements Command{
	
	private static final Logger logger = LogManager.getLogger(GetAddPublicationFormCommand.class);
	
	private ThemeService themeService = ServiceFactory.getThemeService();
	private TypeService typeService = ServiceFactory.getTypeService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			LocaleType locale = HttpUtil.getLocale(request);
			
			List<Theme> themes = themeService.readAll(locale);
			List<Type> types = typeService.readAll(locale);
			
			request.setAttribute(WebConstantDeclaration.REQUEST_ATTR_THEMES, themes);
			request.setAttribute(WebConstantDeclaration.REQUEST_ATTR_TYPES, types);
			request.getRequestDispatcher(WebConstantDeclaration.VIEW_ADD_PUBLICATION_FORM).forward(request, response);
		} catch (ServiceException e) {
			logger.error("Exception preparing publication form ", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}		
	}

}
