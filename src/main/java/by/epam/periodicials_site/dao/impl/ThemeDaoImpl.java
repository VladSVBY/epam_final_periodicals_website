package by.epam.periodicials_site.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.ThemeDao;
import by.epam.periodicials_site.dao.pool.ConnectionPool;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Theme;

public class ThemeDaoImpl implements ThemeDao{
	
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	private static final String READ_ALL = "SELECT themes.id, name FROM themes JOIN themes_local ON themes.id=themes_local.id_theme WHERE locale=?";
	
	private static final String ID = "id";
	private static final String Name = "name";
	
	@Override
	public List<Theme> readAll(LocaleType locale) throws DaoException {
		List<Theme> themes = new ArrayList<>();
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_ALL)
		){
			ps.setString(1, locale.name());			
			ps.executeQuery();			
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Theme theme = formTheme(resultSet);
				themes.add(theme);
			}
		} catch (SQLException e) {
			// TODO logger
			throw new DaoException("Exception reading themes", e);
		}
		return themes;
	} 
	
	private Theme formTheme(ResultSet resultSet) throws SQLException {
		Theme theme = new Theme();
		theme.setId(resultSet.getShort(ID));
		theme.setName(resultSet.getString(Name));
		return theme;
	}

}
