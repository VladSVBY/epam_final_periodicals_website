package by.epam.periodicials_site.dao;

import java.util.List;

import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Type;

public interface TypeDao {
	
	List<Type> readAll(LocaleType locale) throws DaoException;
	
	List<Type> readAllLocalized() throws DaoException;
	
	void update(Type type) throws DaoException;
	
	void create(Type type)  throws DaoException;

}
