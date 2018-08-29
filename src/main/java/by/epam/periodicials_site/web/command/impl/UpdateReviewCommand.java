package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Review;
import by.epam.periodicials_site.service.ReviewService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.service.exception.ValidationException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;
import by.epam.periodicials_site.web.util.MessageResolver;

public class UpdateReviewCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(UpdateReviewCommand.class);
	
	private static final String FAIL_MESSAGE_KEY = "review.update_fail";
	
	private ReviewService reviewService = ServiceFactory.getReviewService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LocaleType locale = HttpUtil.getLocale(request);
		try {
			int reviewId = Integer.parseInt(request.getParameter(REQUEST_PARAM_REVIEW_ID));		
			
			Review review = reviewService.read(reviewId);
			updateReview(review, request);
			
			reviewService.update(review);
			response.sendRedirect(HttpUtil.getReferPage(request));
		} catch (ValidationException e) {
			String message = MessageResolver.getMessage(FAIL_MESSAGE_KEY, locale);
			request.setAttribute(FAIL_MESSAGE_ADD_PUBLICATION, message);
			request.getRequestDispatcher("").forward(request, response);
		} catch (ServiceException e) {
			logger.error("Exception updating review", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
	}
	
	private void updateReview(Review review, HttpServletRequest request) {
		review.setMark(Byte.parseByte(request.getParameter(REQUEST_PARAM_REVIEW_MARK)));
		review.setText(request.getParameter(REQUEST_PARAM_REVIEW_TEXT));
	}

}
