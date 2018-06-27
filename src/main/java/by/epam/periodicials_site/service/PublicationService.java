package by.epam.periodicials_site.service;

import java.util.List;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;

public interface PublicationService {
	
	List<Publication> readAll(LocaleType locale);

}
