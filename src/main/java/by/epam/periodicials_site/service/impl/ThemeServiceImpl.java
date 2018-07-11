package by.epam.periodicials_site.service.impl;

import java.util.Collections;
import java.util.List;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.ThemeDao;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Theme;
import by.epam.periodicials_site.service.ServiceException;
import by.epam.periodicials_site.service.ThemeService;

public class ThemeServiceImpl implements ThemeService {
	
	private ThemeDao themeDao = DaoFactory.getThemeDao();
	
	public List<Theme> readAll(LocaleType locale) throws ServiceException{
		List<Theme> themes = Collections.emptyList();
		try {
			themes = themeDao.readAll(locale);
		} catch (DaoException e) {
			// TODO logger
			throw new ServiceException(e);
		}
		return themes;
	}

}
