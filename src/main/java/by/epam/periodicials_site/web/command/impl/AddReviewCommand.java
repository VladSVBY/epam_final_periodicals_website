package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.Review;
import by.epam.periodicials_site.service.ReviewService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class AddReviewCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(AddReviewCommand.class);
	
	private ReviewService reviewService = ServiceFactory.getReviewService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			int userId = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);
			Review review = formReview(request);
			review.setUserId(userId);
			
			reviewService.addReview(review);
			response.sendRedirect(HttpUtil.getReferPage(request));
		} catch (ServiceException e) {
			logger.error("Exception adding review", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
	}
	
	private Review formReview(HttpServletRequest request) {
		Review review = new Review();
		review.setDateOfPublication(new Date());
		review.setText(request.getParameter(REQUEST_PARAM_REVIEW_TEXT));
		review.setPublicationId(Integer.parseInt(request.getParameter(REQUEST_PARAM_REVIEW_ID_OF_PUBLICATION)));
		review.setMark(Byte.parseByte(request.getParameter(REQUEST_PARAM_REVIEW_MARK)));
		return review;
	}

}
