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
import by.epam.periodicials_site.entity.dto.LocalizedType;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.TypeService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.service.exception.ValidationException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;
import by.epam.periodicials_site.web.util.MessageResolver;
import by.epam.periodicials_site.web.util.WebConstantDeclaration;

public class UpdateTypeCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(UpdateTypeCommand.class);
	
	private static final String FAIL_MESSAGE_KEY = "type.update_failed";
	
	private TypeService typeService = ServiceFactory.getTypeService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LocaleType locale = HttpUtil.getLocale(request);
		try {
			LocalizedType type = formLocalizedType(request);
			
			typeService.update(type);
			
			response.sendRedirect(HttpUtil.formRedirectUrl(request, COMMAND_EDIT_TYPES));			
		} catch (ValidationException e) {
			String message = MessageResolver.getMessage(FAIL_MESSAGE_KEY, locale);
			request.setAttribute(FAIL_MESSAGE_UPDATE_TYPE, message);
			request.getRequestDispatcher(VIEW_EDIT_TYPES).forward(request, response);
		} catch (ServiceException e) {
			logger.error("Exception updating localized type", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
	}
	
	private LocalizedType formLocalizedType(HttpServletRequest request) {
		LocalizedType localizedType = new LocalizedType();
		
		int thmeId = Integer.parseInt(request.getParameter(WebConstantDeclaration.REQUEST_PARAM_TYPE_ID));
		Map<LocaleType, String> names = new EnumMap<>(LocaleType.class);
		String nameRu = request.getParameter(WebConstantDeclaration.REQUEST_PARAM_NAME_RU);
		String nameEn = request.getParameter(WebConstantDeclaration.REQUEST_PARAM_NAME_EN);
		names.put(LocaleType.RU_BY, nameRu);
		names.put(LocaleType.EN_US, nameEn);
		
		localizedType.setId(thmeId);
		localizedType.setDefaultName(nameEn);
		localizedType.setLocalizedNames(names);
		return localizedType;
	}

}
