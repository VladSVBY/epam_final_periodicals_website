package by.epam.periodicials_site.dao;

import java.util.List;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;
import by.epam.periodicials_site.entity.dto.LocalizedPublication;
import by.epam.periodicials_site.entity.dto.PublicationSearchCriteria;

public interface PublicationDao {
	
	Publication read(int id, LocaleType locale) throws DaoException;
	
	List<Publication> readAll(PublicationSearchCriteria criteria) throws DaoException;
	
	void create(LocalizedPublication localizedPublication) throws DaoException;
	
	void update(LocalizedPublication localizedPublication) throws DaoException;
	
	LocalizedPublication readLocalized(int id) throws DaoException;
	
	int getTotalCount(PublicationSearchCriteria criteria) throws DaoException;

}
