package by.epam.periodicials_site.service.impl;

import java.util.Collections;
import java.util.List;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.PublicationDao;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;
import by.epam.periodicials_site.service.PublicationService;

public class PublicationServiceImpl implements PublicationService{
	
	private PublicationDao publicationDao = DaoFactory.getPublicationDao();

	@Override
	public List<Publication> readAll(LocaleType locale) {
		List<Publication> publications = Collections.emptyList();
		try {
			publications = publicationDao.readAll(locale);
		} catch (DaoException e) {
			// TODO logger
			e.printStackTrace();
		}
		return publications;
	}

}
