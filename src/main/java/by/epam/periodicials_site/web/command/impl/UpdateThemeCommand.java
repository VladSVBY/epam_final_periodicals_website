package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.VIEW_503_ERROR;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.dto.LocalizedTheme;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.ThemeService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.WebConstantDeclaration;

public class UpdateThemeCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(UpdateThemeCommand.class);
	
	private ThemeService themeService = ServiceFactory.getThemeService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			LocalizedTheme theme = formLocalizedTheme(request);
			
			themeService.update(theme);
			
		} catch (ServiceException e) {
			logger.error("Exception updating localized theme", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
	}
	
	private LocalizedTheme formLocalizedTheme(HttpServletRequest request) {
		LocalizedTheme localizedTheme = new LocalizedTheme();
		
		int thmeId = Integer.parseInt(request.getParameter(WebConstantDeclaration.REQUEST_PARAM_THEME_ID));
		Map<LocaleType, String> names = new EnumMap<>(LocaleType.class);
		String nameRu = request.getParameter(WebConstantDeclaration.REQUEST_PARAM_NAME_RU);
		String nameEn = request.getParameter(WebConstantDeclaration.REQUEST_PARAM_NAME_EN);
		names.put(LocaleType.RU_BY, nameRu);
		names.put(LocaleType.EN_US, nameEn);
		
		localizedTheme.setId(thmeId);
		localizedTheme.setDefaultName(nameEn);
		localizedTheme.setLocalizedNames(names);
		return localizedTheme;
	}

}
