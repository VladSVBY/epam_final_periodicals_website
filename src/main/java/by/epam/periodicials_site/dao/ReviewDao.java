package by.epam.periodicials_site.dao;

import java.util.List;

import by.epam.periodicials_site.entity.Review;

public interface ReviewDao {
	
	List<Review> readForPublication(int publicationId) throws DaoException;
	
	void create(Review review) throws DaoException;

}
