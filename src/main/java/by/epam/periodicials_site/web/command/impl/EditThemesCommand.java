package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.REQUEST_ATTR_THEMES;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.VIEW_503_ERROR;
import static by.epam.periodicials_site.web.util.WebConstantDeclaration.VIEW_EDIT_THEMES;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.dto.LocalizedTheme;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.ThemeService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;

public class EditThemesCommand implements Command{
	
	private static final Logger logger = LogManager.getLogger(EditThemesCommand.class);

	private ThemeService themeService = ServiceFactory.getThemeService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			List<LocalizedTheme> themes = themeService.readAllLocalized();
			request.setAttribute(REQUEST_ATTR_THEMES, themes);	
			request.getRequestDispatcher(VIEW_EDIT_THEMES).forward(request, response);
		} catch (ServiceException e) {
			logger.error("Exception reading localized themes", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
	}
}
