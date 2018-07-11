package by.epam.periodicials_site.service;

import java.util.List;

import by.epam.periodicials_site.entity.Review;

public interface ReviewService {
	
	List<Review> readForPublication(int publicationId) throws ServiceException;
	
	void addReview(Review review) throws ServiceException;
}
