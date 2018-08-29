package by.epam.periodicials_site.dao;

import java.util.List;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Theme;
import by.epam.periodicials_site.entity.dto.LocalizedTheme;

public interface ThemeDao {
	
	List<Theme> readAll(LocaleType locale) throws DaoException;
	
	Theme read(Integer id, LocaleType locale) throws DaoException;
	
	List<LocalizedTheme> readAllLocalized() throws DaoException;
	
	void update(LocalizedTheme localizedTheme) throws DaoException;
	
	void create(LocalizedTheme localizedTheme)  throws DaoException;

}
