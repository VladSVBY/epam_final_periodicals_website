package by.epam.periodicials_site.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.PublicationDao;
import by.epam.periodicials_site.dao.pool.ConnectionPool;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;
import by.epam.periodicials_site.entity.dto.PublicationSearchCriteria;

public class PublicationDaoImpl implements PublicationDao{
	
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	private static final String READ_BY_ID_AND_LOCALE = "SELECT id, name, description, periodicity, id_theme, id_type, price, rating FROM publications JOIN publications_local ON publications_local.id_publication = publications.id WHERE id = ? AND locale = ?";
	private static final String READ_ALL = "SELECT id, name, description, periodicity, id_theme, id_type, price, rating FROM publications JOIN publications_local ON publications_local.id_publication = publications.id WHERE publications_local.locale='%s'";
	
	private static final String THEME_CLAUSE = " AND id_theme='%d'";
	private static final String TYPE_CLAUSE = " AND id_type='%d'";
	private static final String ORDER_CLAUSE = " ORDER BY %s";
	
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String PERIODICITY = "periodicity";
	private static final String ID_THEME = "id_theme";
	private static final String ID_TYPE = "id_type";
	private static final String PRICE = "price";
	private static final String RATING = "rating";
	
	private static final int ORDER_ALPHABETICALLY = 1;
	private static final int ORDER_BY_PRICE = 2;
	private static final int ORDER_BY_RATING = 3;
	private static final int ALL_THEMES = 0;
	private static final int ALL_TYPES = 0;
	
	@Override
	public Publication read(int id, LocaleType locale) throws DaoException {
		Publication publication = null;
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_BY_ID_AND_LOCALE)
		){
			ps.setInt(1, id);
			ps.setString(2, locale.name());		
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				publication = formPublication(resultSet);
			}
		} catch (SQLException e) {
			// TODO logger
			throw new DaoException("Exception reading publication", e);
		}
		return publication;
	}
	
	@Override
	public List<Publication> readAll(PublicationSearchCriteria criteria) throws DaoException {
		List<Publication> publications = new ArrayList<>();
		String query = formQuery(criteria);
		try (Connection connection = connectionPool.getConnection(); 
				Statement statement = connection.createStatement()
		){		
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Publication publication = formPublication(resultSet);
				publications.add(publication);
			}
		} catch (SQLException e) {
			// TODO logger
			throw new DaoException("Exception reading publications", e);
		}
		return publications;
	}
	
	private Publication formPublication(ResultSet resultSet) throws SQLException {
		Publication publication = new Publication();
		publication.setId(resultSet.getInt(ID));
		publication.setName(resultSet.getString(NAME));
		publication.setDescription(resultSet.getString(DESCRIPTION));
		publication.setPeriodicity(resultSet.getInt(PERIODICITY));
		publication.setThemeId(resultSet.getShort(ID_THEME));
		publication.setTypeID(resultSet.getShort(ID_TYPE));
		publication.setPrice(resultSet.getDouble(PRICE));
		publication.setRating(resultSet.getDouble(RATING));
		return publication;
	}
	
	private String formQuery(PublicationSearchCriteria criteria) {
		StringBuilder query = new StringBuilder(String.format(READ_ALL, criteria.getLocale().name()));
		setTheme(query, criteria);
		setOrder(query, criteria);
		return query.toString();
	}
	
	private void setTheme(StringBuilder query, PublicationSearchCriteria criteria) {
		if (criteria.getThemeId() != ALL_THEMES) {
			query.append(String.format(THEME_CLAUSE, criteria.getThemeId()));
		}
	}
	
	private void setOrder(StringBuilder query, PublicationSearchCriteria criteria) {
		switch (criteria.getOrderId()) {
			case ORDER_ALPHABETICALLY:
				query.append(String.format(ORDER_CLAUSE, NAME));
				break;
			case ORDER_BY_PRICE:
				query.append(String.format(ORDER_CLAUSE, PRICE));
				break;
			case ORDER_BY_RATING:
				query.append(String.format(ORDER_CLAUSE, RATING));
				break;
			default:
				query.append(String.format(ORDER_CLAUSE, NAME));
			}
	}

}
