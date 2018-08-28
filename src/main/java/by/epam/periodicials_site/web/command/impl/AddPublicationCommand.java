package by.epam.periodicials_site.web.command.impl;

import java.io.IOException;

import java.util.EnumMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.dto.LocalizedPublication;
import by.epam.periodicials_site.service.PublicationService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class AddPublicationCommand implements Command {
	
	private PublicationService publicationService = ServiceFactory.getPublicationService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			LocalizedPublication localizedPublication = formLocalizedPublication(request);
			publicationService.add(localizedPublication);
			System.out.println(request.getParameter("date_of_publication"));
		} catch (ServiceException e) {
			// TODO logger
			e.printStackTrace();
		}
		
	}
	
	private LocalizedPublication formLocalizedPublication(HttpServletRequest request) throws IOException, ServletException {
		LocalizedPublication localizedPublication = new LocalizedPublication();
		
		short themeId = Short.parseShort(request.getParameter("theme"));
		short typeId = Short.parseShort(request.getParameter("type"));
		String nameRu = request.getParameter("name_ru");
		String nameEn = request.getParameter("name_en");
		String descriptionRu = request.getParameter("description_ru");
		String descriptionEn = request.getParameter("description_en");
		double price = Double.parseDouble(request.getParameter("price"));
		
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
