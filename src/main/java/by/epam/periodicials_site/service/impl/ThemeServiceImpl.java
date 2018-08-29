package by.epam.periodicials_site.service.impl;

import java.util.List;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.ThemeDao;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Theme;
import by.epam.periodicials_site.entity.dto.LocalizedTheme;
import by.epam.periodicials_site.service.ThemeService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.service.exception.ValidationException;
import by.epam.periodicials_site.service.util.Validator;

public class ThemeServiceImpl implements ThemeService {
	
	private ThemeDao themeDao = DaoFactory.getThemeDao();
	
	public List<Theme> readAll(LocaleType locale) throws ServiceException{
		try {
			return themeDao.readAll(locale);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Theme read(int id, LocaleType locale) throws ServiceException {
		try {
			return themeDao.read(id, locale);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<LocalizedTheme> readAllLocalized() throws ServiceException {
		try {
			return themeDao.readAllLocalized();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void create(LocalizedTheme theme) throws ServiceException {
		if (!Validator.localizedThemeIsValid(theme)) {
			throw new ValidationException("Localized theme data is not valid");
		}
		try {
			themeDao.create(theme);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void update(LocalizedTheme theme) throws ServiceException {
		if (!Validator.localizedThemeIsValid(theme)) {
			throw new ValidationException("Localized theme data is not valid");
		}
		try {
			themeDao.update(theme);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

}
