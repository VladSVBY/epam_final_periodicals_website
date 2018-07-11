package by.epam.periodicials_site.service;

import java.util.List;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;
import by.epam.periodicials_site.entity.dto.PublicationSearchCriteria;

public interface PublicationService {
	
	List<Publication> readAll(PublicationSearchCriteria criteria);
	
	Publication read(int id, LocaleType locale);

}
