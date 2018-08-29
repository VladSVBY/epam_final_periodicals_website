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
import by.epam.periodicials_site.dao.TypeDao;
import by.epam.periodicials_site.dao.pool.ConnectionPool;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Type;
import by.epam.periodicials_site.entity.dto.LocalizedType;

public class TypeDaoImpl implements TypeDao{
	
	private static final Logger logger = LogManager.getLogger(TypeDaoImpl.class);
	
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	private static final String READ_ALL_WITH_LOCALE = "SELECT types.id, name FROM types JOIN types_local ON types.id=types_local.id_type WHERE locale=?";
	private static final String READ_WITH_LOCALE = "SELECT types.id, name FROM types JOIN types_local ON types.id=types_local.id_type WHERE types.id=? locale=?";
	private static final String READ_ALL = "SELECT types.id, default_name, locale, name FROM types JOIN types_local ON types.id=types_local.id_type ORDER BY types.id";
	private static final String CREATE_MAIN_INFO = "INSERT INTO `periodicals_website`.`types` (`default_name`) VALUES (?)";
	private static final String CREATE_LOCALIZED_INFO = "INSERT INTO `periodicals_website`.`types_local` (`id_type`, `locale`, `name`) VALUES (?, ?, ?);";
	private static final String UPDATE_MAIN_INFO = "UPDATE `periodicals_website`.`types` SET `default_name`=? WHERE `id`=?";
	private static final String UPDATE_LOCALIZED_INFO = "UPDATE `periodicals_website`.`types_local` SET `name`=? WHERE `id_type`=? and`locale`=?";
	
	private static final String ID = "id";
	private static final String DEFAULT_NAME = "default_name";
	private static final String LOCALE = "locale";
	private static final String NAME = "name";
	
	@Override
	public List<Type> readAll(LocaleType locale) throws DaoException {
		List<Type> types = new ArrayList<>();
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_ALL_WITH_LOCALE)
		){
			ps.setString(1, locale.name());			
		
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				Type type = formType(resultSet);
				types.add(type);
			}
			return types;
		} catch (SQLException e) {
			throw new DaoException("Exception reading types", e);
		}
	}
	
	@Override
	public Type read(Integer id, LocaleType locale) throws DaoException {
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_WITH_LOCALE)
		){	
			ps.setInt(1, id);
			ps.setString(1, locale.name());
			
			ResultSet resultSet = ps.executeQuery();
			
			Type type = null;
			if (resultSet.next()) {
				type = formType(resultSet);
			}
			return type;
		} catch (SQLException e) {
			throw new DaoException("Exception reading type", e);
		}
	}

	@Override
	public List<LocalizedType> readAllLocalized() throws DaoException {
		List<LocalizedType> localizedTypes = new ArrayList<>();
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_ALL)
		){		
			ps.executeQuery();	
			
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				LocalizedType localizedType = formLocalisedType(resultSet);
				localizedTypes.add(localizedType);
			}
			return localizedTypes;
		} catch (SQLException e) {
			throw new DaoException("Exception reading localized types", e);
		}
	}


	@Override
	public void update(LocalizedType localizedType) throws DaoException {
		Connection connection = null;
		PreparedStatement psMain = null;
		PreparedStatement psAdditional = null;
		try {	
			connection = connectionPool.getConnection();
			psMain = connection.prepareStatement(UPDATE_MAIN_INFO);
			psAdditional = connection.prepareStatement(UPDATE_LOCALIZED_INFO);
			connection.setAutoCommit(false);
			
			psMain.setString(1, localizedType.getDefaultName());
			psMain.setInt(2, localizedType.getId());
			
			psMain.executeUpdate();
			
			for (LocaleType locale : LocaleType.values()) {
				psAdditional.setString(1, localizedType.getLocalizedNames().get(locale));
				psAdditional.setInt(2, localizedType.getId());
				psAdditional.setString(3, locale.name());
				psAdditional.executeUpdate();
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
					connectionPool.rollBack(connection);
			} catch (SQLException e1) {
				throw new DaoException("Exception rollbacking type", e);
			}
			throw new DaoException("Exception updating type", e);
		} finally {
			try {
				connectionPool.closeDbResources(connection, psMain, psAdditional);
			} catch (SQLException e) {
				logger.warn("Closing of DB resources failed", e);
			}
		}	
	}


	@Override
	public void create(LocalizedType localizedType) throws DaoException {
		Connection connection = null;
		PreparedStatement psMain = null;
		PreparedStatement psAdditional = null;
		try {	
			connection = connectionPool.getConnection();
			psMain = connection.prepareStatement(CREATE_MAIN_INFO, Statement.RETURN_GENERATED_KEYS);
			psAdditional = connection.prepareStatement(CREATE_LOCALIZED_INFO);
			connection.setAutoCommit(false);
			
			psMain.setString(1, localizedType.getDefaultName());
			
			psMain.executeUpdate();
			
			ResultSet rSet = psMain.getGeneratedKeys();
			rSet.next();
			localizedType.setId(rSet.getShort(1));
			
			for (LocaleType locale : LocaleType.values()) {
				psAdditional.setInt(1, localizedType.getId());
				psAdditional.setString(2, locale.name());
				psAdditional.setString(3, localizedType.getLocalizedNames().get(locale));
				psAdditional.executeUpdate();
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
					connectionPool.rollBack(connection);
			} catch (SQLException e1) {
				throw new DaoException("Exception rollbacking type", e);
			}
			throw new DaoException("Exception creating type", e);
		} finally {
			try {
				connectionPool.closeDbResources(connection, psMain, psAdditional);
			} catch (SQLException e) {
				logger.warn("Closing of DB resources failed", e);
			}
		}	
	}
	
	private Type formType(ResultSet resultSet) throws SQLException {
		Type type = new Type();
		type.setId(resultSet.getShort(ID));
		type.setName(resultSet.getString(NAME));
		return type;
	}
	
	private LocalizedType formLocalisedType(ResultSet resultSet) throws SQLException {
		LocalizedType localizedType = new LocalizedType();
		
		short id = resultSet.getShort(ID);
		localizedType.setId(id);
		localizedType.setDefaultName(resultSet.getString(DEFAULT_NAME));
		
		Map<LocaleType, String> names = new EnumMap<>(LocaleType.class);
		LocaleType locale = LocaleType.valueOf(resultSet.getString(LOCALE));
		String name = resultSet.getString(NAME);
		names.put(locale, name);
		
		while (resultSet.next()) {
			short nextId = resultSet.getShort(ID);
			if (id == nextId) {
				locale = LocaleType.valueOf(resultSet.getString(LOCALE));
				name = resultSet.getString(NAME);
				names.put(locale, name);
			} else {
				resultSet.previous();
				break;
			}
		}
		
		localizedType.setLocalizedNames(names);		
		return localizedType;
	}

}
