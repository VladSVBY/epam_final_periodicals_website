package by.epam.periodicials_site.service.impl;

import java.util.Collections;
import java.util.List;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.PublicationDao;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;
import by.epam.periodicials_site.entity.dto.PublicationSearchCriteria;
import by.epam.periodicials_site.service.PublicationService;

public class PublicationServiceImpl implements PublicationService{
	
	private PublicationDao publicationDao = DaoFactory.getPublicationDao();

	@Override
	public List<Publication> readAll(PublicationSearchCriteria criteria) {
		List<Publication> publications = Collections.emptyList();
		try {
			publications = publicationDao.readAll(criteria);
		} catch (DaoException e) {
			// TODO logger
			e.printStackTrace();
		}
		return publications;
	}

	@Override
	public Publication read(int id, LocaleType locale) {
		try {
			return publicationDao.read(id, locale);
		} catch (DaoException e) {
			// TODO logger
			e.printStackTrace();
		}
		return null;
	}

}
