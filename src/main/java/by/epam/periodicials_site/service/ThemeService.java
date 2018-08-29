package by.epam.periodicials_site.service;

import java.util.List;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Theme;
import by.epam.periodicials_site.entity.dto.LocalizedTheme;
import by.epam.periodicials_site.service.exception.ServiceException;

public interface ThemeService {
	
	List<Theme> readAll(LocaleType locale) throws ServiceException;
	
	Theme read(int id, LocaleType locale) throws ServiceException;
	
	List<LocalizedTheme> readAllLocalized() throws ServiceException;
	
	void create(LocalizedTheme theme) throws ServiceException;
	
	void update(LocalizedTheme theme) throws ServiceException;

}
