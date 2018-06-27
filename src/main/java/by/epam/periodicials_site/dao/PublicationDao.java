package by.epam.periodicials_site.dao;

import java.util.List;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;

public interface PublicationDao {
	
	Publication read(int id, LocaleType locale) throws DaoException;
	
	List<Publication> readAll(LocaleType locale) throws DaoException;

}
