package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;
import by.epam.periodicials_site.entity.Review;
import by.epam.periodicials_site.entity.Theme;
import by.epam.periodicials_site.service.PublicationService;
import by.epam.periodicials_site.service.ReviewService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.ThemeService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class ShowPublicationCommand implements Command {
	
	private PublicationService publicationService = ServiceFactory.getPublicationService();
	private ReviewService reviewService = ServiceFactory.getReviewService();
	private ThemeService themeService = ServiceFactory.getThemeService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LocaleType locale = HttpUtil.getLocale(request);
		int publicationId = Integer.parseInt(request.getParameter(REQUEST_PARAM_PUBLICATION_ID));
		
		Publication publication = null;
		List<Review> reviews = null;
		try {
			publication = publicationService.read(publicationId, locale);
			reviews = reviewService.readForPublication(publicationId);
			List<Theme> themes = themeService.readAll(locale);			
		} catch (ServiceException e) {
			// TODO logger
			e.printStackTrace();
		}
		
		request.setAttribute(REQUEST_ATTR_PUBLICATION, publication);
		request.setAttribute(REQUEST_ATTR_REVIEWS, reviews);
		request.getRequestDispatcher(VIEW_PUBLICATION_DETAILS).forward(request, response);
	}
	
}
