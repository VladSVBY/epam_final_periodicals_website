package by.epam.periodicials_site.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.PublicationDao;
import by.epam.periodicials_site.dao.pool.ConnectionPool;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;

public class PublicationDaoImpl implements PublicationDao{
	
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	private static final String READ_BY_ID_AND_LOCALE = "SELECT id, name, description, periodicity, id_theme, id_type, price, rating FROM publications JOIN publications_local ON publications_local.id_publication = publications.id WHERE id = ? AND locale = ?";
	private static final String READ_ALL_BY_LOCALE = "SELECT id, name, description, periodicity, id_theme, id_type, price, rating FROM publications JOIN publications_local ON publications_local.id_publication = publications.id AND publications_local.locale=?";
	
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String PERIODICITY = "periodicity";
	private static final String ID_THEME = "id_theme";
	private static final String ID_TYPE = "id_type";
	private static final String PRICE = "price";
	private static final String RATING = "rating";
	
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
			throw new DaoException("Can't read publication", e);
		}
		return publication;
	}
	
	@Override
	public List<Publication> readAll(LocaleType locale) throws DaoException {
		List<Publication> publications = new ArrayList<>();
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_ALL_BY_LOCALE)
		){
			ps.setString(1, locale.name());
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Publication publication = formPublication(resultSet);
				publications.add(publication);
			}
		} catch (SQLException e) {
			// TODO logger
			throw new DaoException("Can't read publications", e);
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

}
