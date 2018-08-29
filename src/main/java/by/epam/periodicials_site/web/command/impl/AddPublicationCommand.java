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
import by.epam.periodicials_site.entity.dto.LocalizedPublication;
import by.epam.periodicials_site.service.PublicationService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.service.exception.ValidationException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;
import by.epam.periodicials_site.web.util.MessageResolver;

public class AddPublicationCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(AddPublicationCommand.class);
	
	private static final String FAIL_MESSAGE_KEY = "add_publication.fail";
	private static final String SUCCESS_MESSAGE_KEY = "add_publication.succcess";
	
	private PublicationService publicationService = ServiceFactory.getPublicationService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LocaleType locale = HttpUtil.getLocale(request);
		try {
			LocalizedPublication localizedPublication = formLocalizedPublication(request);
			
			publicationService.add(localizedPublication);
			
			String message = MessageResolver.getMessage(SUCCESS_MESSAGE_KEY, locale);
			String returnPage = HttpUtil.getReferPage(request);
			String path = HttpUtil.formRedirectUrl(request, COMMAND_SHOW_RESULT_PAGE);
			path = HttpUtil.addParamToPath(path, REQUEST_ATTR_MESSAGE, message);
			path = HttpUtil.addParamToPath(path, REQUEST_ATTR_RETURN_PAGE, returnPage);
			response.sendRedirect(path);
		} catch (ValidationException e) {
			String message = MessageResolver.getMessage(FAIL_MESSAGE_KEY, locale);
			request.setAttribute(FAIL_MESSAGE_ADD_PUBLICATION, message);
			request.getRequestDispatcher(VIEW_ADD_PUBLICATION_FORM).forward(request, response);
		}	
		catch (ServiceException e) {
			logger.error("Exception adding publication", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}		
	}
	
	private LocalizedPublication formLocalizedPublication(HttpServletRequest request) throws IOException, ServletException {
		LocalizedPublication localizedPublication = new LocalizedPublication();
		
		short themeId = Short.parseShort(request.getParameter(REQUEST_PARAM_THEME_ID));
		short typeId = Short.parseShort(request.getParameter(REQUEST_PARAM_TYPE_ID));
		String nameRu = request.getParameter(REQUEST_PARAM_NAME_RU);
		String nameEn = request.getParameter(REQUEST_PARAM_NAME_EN);
		String descriptionRu = request.getParameter(REQUEST_PARAM_PUBLICATION_DESCRIPTION_RU);
		String descriptionEn = request.getParameter(REQUEST_PARAM_PUBLICATION_DESCRIPTION_EN);
		double price = Double.parseDouble(request.getParameter(REQUEST_PARAM_PUBLICATION_PRICE));
		
		Map<LocaleType, String> names = new EnumMap<>(LocaleType.class);
		Map<LocaleType, String> descriptions = new EnumMap<>(LocaleType.class);
		names.put(LocaleType.RU_BY, nameRu);
		names.put(LocaleType.EN_US, nameEn);
		descriptions.put(LocaleType.RU_BY, descriptionRu);
		descriptions.put(LocaleType.EN_US, descriptionEn);
		
		String pathToPicture = HttpUtil.uploadPublicationPicture(request);
		
		localizedPublication.setThemeId(themeId);
		localizedPublication.setTypeID(typeId);
		localizedPublication.setNames(names);
		localizedPublication.setDescriptions(descriptions);
		localizedPublication.setPrice(price);
		localizedPublication.setPicturePath(pathToPicture);
		return localizedPublication;
	}

}
