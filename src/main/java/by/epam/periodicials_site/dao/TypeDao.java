package by.epam.periodicials_site.dao;

import java.util.List;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Type;
import by.epam.periodicials_site.entity.dto.LocalizedType;

public interface TypeDao {
	
	List<Type> readAll(LocaleType locale) throws DaoException;
	
	Type read(Integer id, LocaleType locale) throws DaoException;
	
	List<LocalizedType> readAllLocalized() throws DaoException;
	
	void update(LocalizedType localizedType) throws DaoException;
	
	void create(LocalizedType localizedType)  throws DaoException;

}
