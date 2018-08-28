package by.epam.periodicials_site.dao;

import java.util.Date;
import java.util.List;

import by.epam.periodicials_site.entity.Issue;

public interface IssueDao {

	void create(Issue issue) throws DaoException;
	
	Issue read(int id) throws DaoException;
	
	List<Issue> readForPublicationBetweenDates(int publicationId, Date startDate, Date endDate) throws DaoException;
}
