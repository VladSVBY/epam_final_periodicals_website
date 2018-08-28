package by.epam.periodicials_site.service;

import java.util.List;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Theme;
import by.epam.periodicials_site.service.exception.ServiceException;

public interface ThemeService {
	
	List<Theme> readAll(LocaleType locale) throws ServiceException;

}
