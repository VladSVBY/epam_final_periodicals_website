package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

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
import by.epam.periodicials_site.service.exception.ValidationException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;
import by.epam.periodicials_site.web.util.MessageResolver;
import by.epam.periodicials_site.web.util.WebConstantDeclaration;

public class AddThemeCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(AddThemeCommand.class);
	
	private static final String FAIL_MESSAGE_KEY = "theme.add_failed";
	private static final String SUCCESS_MESSAGE_KEY = "theme.add_success";
	
	private ThemeService themeService = ServiceFactory.getThemeService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LocaleType locale = HttpUtil.getLocale(request);
		try {
			LocalizedTheme theme = formLocalizedTheme(request);
			
			themeService.create(theme);
			
			String message = MessageResolver.getMessage(SUCCESS_MESSAGE_KEY, locale);
			String returnPage = HttpUtil.getReferPage(request);
			String path = HttpUtil.formRedirectUrl(request, COMMAND_SHOW_RESULT_PAGE);
			path = HttpUtil.addParamToPath(path, REQUEST_ATTR_MESSAGE, message);
			path = HttpUtil.addParamToPath(path, REQUEST_ATTR_RETURN_PAGE, returnPage);
			response.sendRedirect(path);
		} catch (ValidationException e) {
			String message = MessageResolver.getMessage(FAIL_MESSAGE_KEY, locale);
			request.setAttribute(FAIL_MESSAGE_ADD_THEME, message);
			request.getRequestDispatcher(COMMAND_ADD_PUBLICATION).forward(request, response);
		} catch (ServiceException e) {
			logger.error("Exception creating localized theme", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
	}
	
	private LocalizedTheme formLocalizedTheme(HttpServletRequest request) {
		LocalizedTheme localizedTheme = new LocalizedTheme();
		
		Map<LocaleType, String> names = new EnumMap<>(LocaleType.class);
		String nameRu = request.getParameter(WebConstantDeclaration.REQUEST_PARAM_NAME_RU);
		String nameEn = request.getParameter(WebConstantDeclaration.REQUEST_PARAM_NAME_EN);
		names.put(LocaleType.RU_BY, nameRu);
		names.put(LocaleType.EN_US, nameEn);
		
		localizedTheme.setDefaultName(nameEn);
		localizedTheme.setLocalizedNames(names);
		return localizedTheme;
	}

}
