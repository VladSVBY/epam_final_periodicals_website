package by.epam.periodicials_site.service;

import java.util.List;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;
import by.epam.periodicials_site.entity.dto.LocalizedPublication;
import by.epam.periodicials_site.entity.dto.PublicationSearchCriteria;
import by.epam.periodicials_site.service.exception.ServiceException;

public interface PublicationService {
	
	List<Publication> readAll(PublicationSearchCriteria criteria);
	
	Publication read(int id, LocaleType locale);
	
	void add(LocalizedPublication localizedPublication) throws ServiceException;
	
	LocalizedPublication readLocalized(int id) throws ServiceException;
	
	void update(LocalizedPublication localizedPublication) throws ServiceException;

}
