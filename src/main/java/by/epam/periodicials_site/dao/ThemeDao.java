package by.epam.periodicials_site.dao;

import java.util.List;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Theme;

public interface ThemeDao {
	
	List<Theme> readAll(LocaleType locale) throws DaoException;

}
