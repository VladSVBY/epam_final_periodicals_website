package by.epam.periodicials_site.service.impl;

import java.util.List;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.TypeDao;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Type;
import by.epam.periodicials_site.entity.dto.LocalizedType;
import by.epam.periodicials_site.service.TypeService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.service.exception.ValidationException;
import by.epam.periodicials_site.service.util.Validator;

public class TypeServiceImpl implements TypeService {
	
	private TypeDao typeDao = DaoFactory.getTypeDao();
	
	public List<Type> readAll(LocaleType locale) throws ServiceException{
		try {
			return typeDao.readAll(locale);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Type read(int id, LocaleType locale) throws ServiceException {
		try {
			return typeDao.read(id, locale);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<LocalizedType> readAllLocalized() throws ServiceException {
		try {
			return typeDao.readAllLocalized();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void create(LocalizedType type) throws ServiceException {
		if (!Validator.localizedTypeIsValid(type)) {
			throw new ValidationException("Localized type data is not valid");
		}
		try {
			typeDao.create(type);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void update(LocalizedType type) throws ServiceException {
		if (!Validator.localizedTypeIsValid(type)) {
			throw new ValidationException("Localized type data is not valid");
		}
		try {
			typeDao.update(type);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

}
