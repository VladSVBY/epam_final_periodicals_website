package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.service.ReviewService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class DeleteReviewCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(DeleteReviewCommand.class);
	
	private ReviewService reviewService = ServiceFactory.getReviewService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			int reviewId = Integer.parseInt(request.getParameter(REQUEST_PARAM_REVIEW_ID));			
			
			reviewService.delete(reviewId);
			
			response.sendRedirect(HttpUtil.getReferPage(request));
		} catch (ServiceException e) {
			logger.error("Exception deleteing review", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
	}

}
