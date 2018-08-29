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
import by.epam.periodicials_site.entity.Review;
import by.epam.periodicials_site.entity.Theme;
import by.epam.periodicials_site.entity.Type;
import by.epam.periodicials_site.service.PublicationService;
import by.epam.periodicials_site.service.ReviewService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.ThemeService;
import by.epam.periodicials_site.service.TypeService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class ShowPublicationCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(ShowPublicationCommand.class);
	
	private PublicationService publicationService = ServiceFactory.getPublicationService();
	private ReviewService reviewService = ServiceFactory.getReviewService();
	private ThemeService themeService = ServiceFactory.getThemeService();
	private TypeService typeService = ServiceFactory.getTypeService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LocaleType locale = HttpUtil.getLocale(request);
		int publicationId = Integer.parseInt(request.getParameter(REQUEST_PARAM_PUBLICATION_ID));		
		try {
			Publication publication = publicationService.read(publicationId, locale);
			List<Review> reviews = reviewService.readForPublication(publicationId);
			Theme theme = themeService.read(publication.getThemeId(), locale);
			Type type = typeService.read(publication.getTypeID(), locale);
			
			request.setAttribute(REQUEST_ATTR_PUBLICATION, publication);
			request.setAttribute(REQUEST_ATTR_REVIEWS, reviews);
			request.setAttribute(REQUEST_ATTR_THEME, theme);
			request.setAttribute(REQUEST_ATTR_TYPE, type);
			request.getRequestDispatcher(VIEW_PUBLICATION_DETAILS).forward(request, response);
		} catch (ServiceException e) {
			logger.error("Exception showing publication", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
	}
	
}
