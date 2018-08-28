package by.epam.periodicials_site.service;

import java.util.List;

import by.epam.periodicials_site.entity.Review;
import by.epam.periodicials_site.service.exception.ServiceException;

public interface ReviewService {
	
	List<Review> readForPublication(int publicationId) throws ServiceException;
	
	void addReview(Review review) throws ServiceException;
	
	void update(Review review) throws ServiceException;
	
	void delete(int reviewId) throws ServiceException;
	
	Review read(int reviewId)  throws ServiceException;
}
