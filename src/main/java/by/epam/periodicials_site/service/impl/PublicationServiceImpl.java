package by.epam.periodicials_site.service.impl;

import java.util.Collections;
import java.util.List;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.PublicationDao;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;
import by.epam.periodicials_site.entity.dto.LocalizedPublication;
import by.epam.periodicials_site.entity.dto.PublicationSearchCriteria;
import by.epam.periodicials_site.service.PublicationService;
import by.epam.periodicials_site.service.ServiceException;

public class PublicationServiceImpl implements PublicationService{
	
	private PublicationDao publicationDao = DaoFactory.getPublicationDao();

	@Override
	public List<Publication> readAll(PublicationSearchCriteria criteria) {
		List<Publication> publications = Collections.emptyList();
		try {
			publications = publicationDao.readAll(criteria);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return publications;
	}

	@Override
	public Publication read(int id, LocaleType locale) {
		try {
			return publicationDao.read(id, locale);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void add(LocalizedPublication localizedPublication) throws ServiceException {
		try {
			publicationDao.create(localizedPublication);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public LocalizedPublication readLocalized(int id) throws ServiceException {
		try {
			return publicationDao.readLocalized(id);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}		
	}

	@Override
	public void update(LocalizedPublication localizedPublication) throws ServiceException {
		try {
			publicationDao.update(localizedPublication);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
	}

}
