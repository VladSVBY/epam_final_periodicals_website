package by.epam.periodicials_site.service;

import java.util.List;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Type;
import by.epam.periodicials_site.entity.dto.LocalizedType;
import by.epam.periodicials_site.service.exception.ServiceException;

public interface TypeService {
	
	List<Type> readAll(LocaleType locale) throws ServiceException;
	
	Type read(int id, LocaleType locale) throws ServiceException;
	
	List<LocalizedType> readAllLocalized() throws ServiceException;
	
	void create(LocalizedType type) throws ServiceException;
	
	void update(LocalizedType type) throws ServiceException;

}
