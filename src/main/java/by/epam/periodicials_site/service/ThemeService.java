package by.epam.periodicials_site.service;

import java.util.List;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Theme;

public interface ThemeService {
	
	List<Theme> readAll(LocaleType locale) throws ServiceException;

}
