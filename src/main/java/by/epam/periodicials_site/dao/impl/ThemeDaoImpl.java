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
import by.epam.periodicials_site.dao.ThemeDao;
import by.epam.periodicials_site.dao.pool.ConnectionPool;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Theme;
import by.epam.periodicials_site.entity.dto.LocalizedTheme;

public class ThemeDaoImpl implements ThemeDao{
	
	private static final Logger logger = LogManager.getLogger(ThemeDaoImpl.class);
	
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	private static final String READ_ALL_WITH_LOCALE = "SELECT themes.id, name FROM themes JOIN themes_local ON themes.id=themes_local.id_theme WHERE locale=?";
	private static final String READ_WITH_LOCALE = "SELECT themes.id, name FROM themes JOIN themes_local ON themes.id=themes_local.id_theme WHERE themes.id=? AND locale=?";
	private static final String READ_ALL = "SELECT themes.id, default_name, locale, name FROM themes JOIN themes_local ON themes.id=themes_local.id_theme ORDER BY themes.id";
	private static final String CREATE_MAIN_INFO = "INSERT INTO `periodicals_website`.`themes` (`default_name`) VALUES (?)";
	private static final String CREATE_LOCALIZED_INFO = "INSERT INTO `periodicals_website`.`themes_local` (`id_theme`, `locale`, `name`) VALUES (?, ?, ?);";
	private static final String UPDATE_MAIN_INFO = "UPDATE `periodicals_website`.`themes` SET `default_name`=? WHERE `id`=?";
	private static final String UPDATE_LOCALIZED_INFO = "UPDATE `periodicals_website`.`themes_local` SET `name`=? WHERE `id_theme`=? and`locale`=?";
	
	private static final String ID = "id";
	private static final String DEFAULT_NAME = "default_name";
	private static final String LOCALE = "locale";
	private static final String NAME = "name";
	
	@Override
	public List<Theme> readAll(LocaleType locale) throws DaoException {
		List<Theme> themes = new ArrayList<>();
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_ALL_WITH_LOCALE)
		){
			ps.setString(1, locale.name());			
		
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				Theme theme = formTheme(resultSet);
				themes.add(theme);
			}
			return themes;
		} catch (SQLException e) {
			throw new DaoException("Exception reading themes", e);
		}
	}
	
	@Override
	public Theme read(Integer id, LocaleType locale) throws DaoException {
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_WITH_LOCALE)
		){	
			ps.setInt(1, id);
			ps.setString(2, locale.name());
			
			ResultSet resultSet = ps.executeQuery();
			
			Theme theme = null;
			if (resultSet.next()) {
				theme = formTheme(resultSet);
			}
			return theme;
		} catch (SQLException e) {
			throw new DaoException("Exception reading theme", e);
		}
	}

	@Override
	public List<LocalizedTheme> readAllLocalized() throws DaoException {
		List<LocalizedTheme> localizedThemes = new ArrayList<>();
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_ALL)
		){		
			ps.executeQuery();	
			
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				LocalizedTheme localizedTheme = formLocalisedTheme(resultSet);
				localizedThemes.add(localizedTheme);
			}
			return localizedThemes;
		} catch (SQLException e) {
			throw new DaoException("Exception reading localized themes", e);
		}
	}


	@Override
	public void update(LocalizedTheme localizedTheme) throws DaoException {
		Connection connection = null;
		PreparedStatement psMain = null;
		PreparedStatement psAdditional = null;
		try {	
			connection = connectionPool.getConnection();
			psMain = connection.prepareStatement(UPDATE_MAIN_INFO);
			psAdditional = connection.prepareStatement(UPDATE_LOCALIZED_INFO);
			connection.setAutoCommit(false);
			
			psMain.setString(1, localizedTheme.getDefaultName());
			psMain.setInt(2, localizedTheme.getId());
			
			psMain.executeUpdate();
			
			for (LocaleType locale : LocaleType.values()) {
				psAdditional.setString(1, localizedTheme.getLocalizedNames().get(locale));
				psAdditional.setInt(2, localizedTheme.getId());
				psAdditional.setString(3, locale.name());
				psAdditional.executeUpdate();
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
					connectionPool.rollBack(connection);
			} catch (SQLException e1) {
				throw new DaoException("Exception rollbacking theme", e);
			}
			throw new DaoException("Exception updating theme", e);
		} finally {
			try {
				connectionPool.closeDbResources(connection, psMain, psAdditional);
			} catch (SQLException e) {
				logger.warn("Closing of DB resources failed", e);
			}
		}	
	}


	@Override
	public void create(LocalizedTheme localizedTheme) throws DaoException {
		Connection connection = null;
		PreparedStatement psMain = null;
		PreparedStatement psAdditional = null;
		try {	
			connection = connectionPool.getConnection();
			psMain = connection.prepareStatement(CREATE_MAIN_INFO, Statement.RETURN_GENERATED_KEYS);
			psAdditional = connection.prepareStatement(CREATE_LOCALIZED_INFO);
			connection.setAutoCommit(false);
			
			psMain.setString(1, localizedTheme.getDefaultName());
			
			psMain.executeUpdate();
			
			ResultSet rSet = psMain.getGeneratedKeys();
			rSet.next();
			localizedTheme.setId(rSet.getShort(1));
			
			for (LocaleType locale : LocaleType.values()) {
				psAdditional.setInt(1, localizedTheme.getId());
				psAdditional.setString(2, locale.name());
				psAdditional.setString(3, localizedTheme.getLocalizedNames().get(locale));
				psAdditional.executeUpdate();
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
					connectionPool.rollBack(connection);
			} catch (SQLException e1) {
				throw new DaoException("Exception rollbacking theme", e);
			}
			throw new DaoException("Exception creating theme", e);
		} finally {
			try {
				connectionPool.closeDbResources(connection, psMain, psAdditional);
			} catch (SQLException e) {
				logger.warn("Closing of DB resources failed", e);
			}
		}	
	}
	
	private Theme formTheme(ResultSet resultSet) throws SQLException {
		Theme theme = new Theme();
		theme.setId(resultSet.getShort(ID));
		theme.setName(resultSet.getString(NAME));
		return theme;
	}
	
	private LocalizedTheme formLocalisedTheme(ResultSet resultSet) throws SQLException {
		LocalizedTheme localizedTheme = new LocalizedTheme();
		
		short id = resultSet.getShort(ID);
		localizedTheme.setId(id);
		localizedTheme.setDefaultName(resultSet.getString(DEFAULT_NAME));
		
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
		
		localizedTheme.setLocalizedNames(names);		
		return localizedTheme;
	}

}
