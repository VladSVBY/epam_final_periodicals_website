package by.epam.periodicials_site.service.impl;

import java.util.Collections;
import java.util.List;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.ReviewDao;
import by.epam.periodicials_site.entity.Review;
import by.epam.periodicials_site.service.ReviewService;
import by.epam.periodicials_site.service.ServiceException;

public class ReviewServiceImpl implements ReviewService {
	
	private ReviewDao reviewDao = DaoFactory.getReviewDao();

	@Override
	public List<Review> readForPublication(int publicationId) throws ServiceException {
		List<Review> reviews = Collections.emptyList();
		try {
			reviews = reviewDao.readForPublication(publicationId);
		} catch (DaoException e) {
			// TODO logger
			throw new ServiceException(e);
		}
		return reviews;
	}

	@Override
	public void addReview(Review review) throws ServiceException {
		try {
			reviewDao.create(review);
		} catch (DaoException e) {
			// TODO logger
			throw new ServiceException(e);
		}		
	}

}
