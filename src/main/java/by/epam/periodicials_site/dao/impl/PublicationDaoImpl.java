package by.epam.periodicials_site.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.PublicationDao;
import by.epam.periodicials_site.dao.pool.ConnectionPool;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;
import by.epam.periodicials_site.entity.dto.LocalizedPublication;
import by.epam.periodicials_site.entity.dto.PublicationSearchCriteria;

public class PublicationDaoImpl implements PublicationDao{
	
	private static final Logger logger = LogManager.getLogger(PublicationDaoImpl.class);
	
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	private static final String READ_BY_ID_AND_LOCALE = "SELECT id, name, description, periodicity, id_theme, id_type, price, rating, picture_path FROM publications JOIN publications_local ON publications_local.id_publication = publications.id WHERE id = ? AND locale = ?";
	private static final String READ_BY_ID = "SELECT id, name, description, periodicity, id_theme, id_type, price, rating, picture_path, locale FROM publications JOIN publications_local ON publications_local.id_publication = publications.id WHERE id = ?";
	private static final String READ_ALL_WITH_LOCALE = "SELECT id, name, description, periodicity, id_theme, id_type, price, rating, picture_path FROM publications JOIN publications_local ON publications_local.id_publication = publications.id WHERE publications_local.locale='%s'";
	private static final String CREATE_MAIN_INFO = "INSERT INTO `periodicals_website`.`publications` (`periodicity`, `id_theme`, `id_type`, `price`, `rating`, `picture_path`) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String CREATE_LOCALIZED_INFO = "INSERT INTO `periodicals_website`.`publications_local` (`id_publication`, `locale`, `name`, `description`) VALUES (?, ?, ?, ?)";
	private static final String UPDATE_MAIN_INFO = "UPDATE `periodicals_website`.`publications` SET `periodicity`=?, `id_theme`=?, `id_type`=?, `price`=?, `rating`=?, `picture_path`=? WHERE `id`=?";
	private static final String UPDATE_LOCALIZED_INFO = "UPDATE `periodicals_website`.`publications_local` SET `name`=?, `description`=? WHERE `id_publication`=? and`locale`=?";
	
	private static final String THEME_CLAUSE = " AND id_theme='%d'";
	private static final String TYPE_CLAUSE = " AND id_type='%d'";
	private static final String ORDER_CLAUSE = " ORDER BY %s";
	private static final String LIMIT_CLAUSE = " LIMIT %d,%d";
	
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String PERIODICITY = "periodicity";
	private static final String ID_THEME = "id_theme";
	private static final String ID_TYPE = "id_type";
	private static final String PRICE = "price";
	private static final String RATING = "rating";
	private static final String PICTURE_PATH = "picture_path";
	private static final String LOCALE = "locale";
	
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
			throw new DaoException("Exception reading publications", e);
		}
		return publications;
	}
	
	@Override
	public void create(LocalizedPublication localizedPublication) throws DaoException {
		Connection connection = null;
		PreparedStatement psMain = null;
		PreparedStatement psAdditional = null;
		try {	
			connection = connectionPool.getConnection();
			psMain = connection.prepareStatement(CREATE_MAIN_INFO, Statement.RETURN_GENERATED_KEYS);
			psAdditional = connection.prepareStatement(CREATE_LOCALIZED_INFO);
			connection.setAutoCommit(false);
			
			psMain.setInt(1, localizedPublication.getPeriodicity());
			psMain.setShort(2, (short)1);
			psMain.setShort(3, (short)1);
			psMain.setDouble(4, localizedPublication.getPrice());
			psMain.setDouble(5, localizedPublication.getRating());
			psMain.setString(6, localizedPublication.getPicturePath());
			
			psMain.executeUpdate();
			
			ResultSet rSet = psMain.getGeneratedKeys();
			rSet.next();
			localizedPublication.setId(rSet.getInt(1));
			
			for (LocaleType locale : LocaleType.values()) {
				psAdditional.setInt(1, localizedPublication.getId());
				psAdditional.setString(2, locale.name());
				psAdditional.setString(3, localizedPublication.getNames().get(locale));
				psAdditional.setString(4, localizedPublication.getDescriptions().get(locale));
				psAdditional.executeUpdate();
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
					connectionPool.rollBack(connection);
			} catch (SQLException e1) {
				throw new DaoException("Exception rollbacking publication", e);
			}
			throw new DaoException("Exception creating publication", e);
		} finally {
			try {
				connectionPool.closeDbResources(connection, psMain, psAdditional);
			} catch (SQLException e) {
				logger.warn("Closing of DB resources failed", e);
			}
		}	
	}

	@Override
	public void update(LocalizedPublication localizedPublication) throws DaoException {
		Connection connection = null;
		PreparedStatement psMain = null;
		PreparedStatement psAdditional = null;
		try {	
			connection = connectionPool.getConnection();
			psMain = connection.prepareStatement(UPDATE_MAIN_INFO);
			psAdditional = connection.prepareStatement(UPDATE_LOCALIZED_INFO);
			connection.setAutoCommit(false);
			
			psMain.setInt(1, localizedPublication.getPeriodicity());
			psMain.setShort(2, localizedPublication.getThemeId());
			psMain.setShort(3, localizedPublication.getTypeID());
			psMain.setDouble(4, localizedPublication.getPrice());
			psMain.setDouble(5, localizedPublication.getRating());
			psMain.setString(6, localizedPublication.getPicturePath());
			psMain.setInt(7, localizedPublication.getId());
			
			psMain.executeUpdate();

			for (LocaleType locale : LocaleType.values()) {
				psAdditional.setString(1, localizedPublication.getNames().get(locale));
				psAdditional.setString(2, localizedPublication.getDescriptions().get(locale));
				psAdditional.setInt(3, localizedPublication.getId());
				psAdditional.setString(4, locale.name());
				psAdditional.executeUpdate();
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
					connection.rollback();
			} catch (SQLException e1) {
				throw new DaoException("Exception rollbacking publication", e);
			}
			throw new DaoException("Exception creating publication", e);
		} finally {
			try {
				connectionPool.closeDbResources(connection, psMain, psAdditional);
			} catch (SQLException e) {
				logger.warn("Closing of DB resources failed", e);
			}
		}	
		
	}

	@Override
	public LocalizedPublication readLocalized(int id) throws DaoException {
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_BY_ID)
		){
			LocalizedPublication localizedPublication = null;
			ps.setInt(1, id);
			
			ResultSet resultSet = ps.executeQuery();		
			
			if (resultSet.next()) {
				localizedPublication = formLocalizedPublication(resultSet);
			}
			return localizedPublication;
		} catch (SQLException e) {
			throw new DaoException("Exception reading localized publication", e);
		}
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
		publication.setPicturePath(resultSet.getString(PICTURE_PATH));
		return publication;
	}
	
	private LocalizedPublication formLocalizedPublication(ResultSet resultSet) throws SQLException {
		LocalizedPublication localizedPublication = new LocalizedPublication();
		
		localizedPublication.setId(resultSet.getInt(ID));
		localizedPublication.setPeriodicity(resultSet.getInt(PERIODICITY));
		localizedPublication.setThemeId(resultSet.getShort(ID_THEME));
		localizedPublication.setTypeID(resultSet.getShort(ID_TYPE));
		localizedPublication.setPrice(resultSet.getDouble(PRICE));
		localizedPublication.setRating(resultSet.getDouble(RATING));
		localizedPublication.setPicturePath(resultSet.getString(PICTURE_PATH));
		
		Map<LocaleType, String> names = new EnumMap<>(LocaleType.class);
		Map<LocaleType, String> descriptions = new EnumMap<>(LocaleType.class);
		do {
			LocaleType locale = LocaleType.valueOf(resultSet.getString(LOCALE));
			String name = resultSet.getString(NAME);
			String description = resultSet.getString(DESCRIPTION);
			names.put(locale, name);
			descriptions.put(locale, description);
		}
		while (resultSet.next());
		localizedPublication.setNames(names);
		localizedPublication.setDescriptions(descriptions);
		
		return localizedPublication;
	}
	
	private String formQuery(PublicationSearchCriteria criteria) {
		StringBuilder query = new StringBuilder(String.format(READ_ALL_WITH_LOCALE, criteria.getLocale().name()));
		setTheme(query, criteria);
		setOrder(query, criteria);
		setLimit(query, criteria);
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
	
	private void setLimit(StringBuilder query, PublicationSearchCriteria criteria) {
		int start = criteria.getItemsPerPage() * (criteria.getCurrentPage() - 1);
		int end = criteria.getItemsPerPage() * (criteria.getCurrentPage());
		query.append(String.format(LIMIT_CLAUSE, start, end));
	}

}
