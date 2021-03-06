package by.epam.periodicials_site.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.UserDao;
import by.epam.periodicials_site.dao.pool.ConnectionPool;
import by.epam.periodicials_site.entity.Role;
import by.epam.periodicials_site.entity.User;

public class UserDaoImpl implements UserDao{
	
	private static final String READ_USER_WITH_EMAIL_OR_LOGIN = "SELECT users.id, login, password, users.name, surname, email, balance, roles.name AS role FROM users JOIN roles ON users.id_role=roles.id WHERE (login=? OR email=?) AND password=?";
	private static final String READ_USER_WITH_ID = "SELECT users.id, login, password, users.name, surname, email, balance, roles.name AS role FROM users JOIN roles ON users.id_role=roles.id WHERE users.id=?";
	private static final String GET_USER_BALANCE = "SELECT balance FROM users WHERE users.id=?";
	private static final String READ_USERS_WITH_SUBSCRIPTION = "SELECT DISTINCT users.id, login, password, users.name, surname, email, balance, roles.name AS role FROM users JOIN roles ON users.id_role=roles.id JOIN subscriptions ON users.id=subscriptions.id_user WHERE id_publication=? AND ? BETWEEN TIMESTAMP(start_date) AND TIMESTAMP(end_date)";
	private static final String CREATE_USER = "INSERT INTO `periodicals_website`.`users` (`login`, `password`, `name`, `surname`, `email`) VALUES (?, ?, ?, ?, ?)";
	private static final String ADD_TO_USER_BALANCE = "UPDATE `periodicals_website`.`users` SET `balance`=(SELECT balance + ? FROM (SELECT balance FROM users WHERE id=?) res) WHERE `id`=?";
	private static final String REMOVE_FROM_USER_BALANCE = "UPDATE `periodicals_website`.`users` SET `balance`=(SELECT balance - ? FROM (SELECT balance FROM users WHERE id=?) res) WHERE `id`=?";
	private static final String FIND_LOGIN = "SELECT id FROM users WHERE login=?";
	private static final String FIND_EMAIL = "SELECT id FROM users WHERE email=?";
	
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();

	@Override
	public void create(User user) throws DaoException{
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)){
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getSurName());
			ps.setString(5, user.getEmail());
			int result = ps.executeUpdate();
			if (result > 0) {
				ResultSet resultSet = ps.getGeneratedKeys();
				resultSet.next();
				user.setId(resultSet.getInt(1));
			}
			
		} catch (SQLException e) {
			throw new DaoException("Exception creating user", e);
		}
	}
	
	@Override
	public User read(int userId) throws DaoException {
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_USER_WITH_ID)){
			ps.setInt(1, userId);

			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return createUser(resultSet);
			}			
		} catch (SQLException e) {
			throw new DaoException("Exception reading user", e);
		}
		return null;
	}

	@Override
	public User readByLoginOrEmailAndPassword(String loginOrEmail, String password) throws DaoException {
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_USER_WITH_EMAIL_OR_LOGIN)){
			ps.setString(1, loginOrEmail);
			ps.setString(2, loginOrEmail);
			ps.setString(3, password);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return createUser(resultSet);
			}			
		} catch (SQLException e) {
			throw new DaoException("Exception reading user", e);
		}
		return null;
	}
	
	@Override
	public void addToBalanceTransaction(int userId, double sum, Connection connection) throws DaoException {
		try (PreparedStatement ps = connection.prepareStatement(ADD_TO_USER_BALANCE)){
			ps.setDouble(1, sum);
			ps.setInt(2, userId);
			ps.setInt(3, userId);
			ps.executeUpdate();
			System.out.println("hello");
		} catch (SQLException e) {
			throw new DaoException("Exception adding to user balance", e);
		}
	}

	@Override
	public void removeFromBalanceTransaction(int userId, double sum, Connection connection) throws DaoException {
		try (PreparedStatement ps = connection.prepareStatement(REMOVE_FROM_USER_BALANCE)){
			ps.setDouble(1, sum);
			ps.setInt(2, userId);
			ps.setInt(3, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Exception removing to user balance", e);
		}
	}
	
	@Override
	public boolean loginExists(String login) throws DaoException {
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(FIND_LOGIN)){
			ps.setString(1, login);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return true;
			}			
		} catch (SQLException e) {
			throw new DaoException("Exception finding login", e);
		}
		return false;
	}

	@Override
	public boolean emailExists(String email) throws DaoException {
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(FIND_EMAIL)){
			ps.setString(1, email);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return true;
			}			
		} catch (SQLException e) {
			throw new DaoException("Exception finding login", e);
		}
		return false;
	}

	@Override
	public List<User> readUsersHavingSuscription(int publicationId, Date date) throws DaoException {
		List<User> users = new ArrayList<>();
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_USERS_WITH_SUBSCRIPTION)){
			ps.setInt(1, publicationId);
			ps.setTimestamp(2, new Timestamp(date.getTime()));
			
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				User user = createUser(resultSet);
				users.add(user);
			}			
		} catch (SQLException e) {
			throw new DaoException("Exception reading user", e);
		}
		return users;
	}

	@Override
	public double getUserBalance(int userId) throws DaoException {
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(GET_USER_BALANCE)){
			ps.setInt(1, userId);

			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return resultSet.getDouble("balance");
			}			
		} catch (SQLException e) {
			throw new DaoException("Exception reading user", e);
		}
		return 0;
	}

	private User createUser(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getInt("id"));
		user.setLogin(resultSet.getString("login"));
		user.setPassword(resultSet.getString("password"));
		user.setName(resultSet.getString("name"));
		user.setSurName(resultSet.getString("surName"));
		user.setEmail(resultSet.getString("email"));
		user.setBalance(resultSet.getDouble("balance"));
		user.setRole(Role.valueOf(resultSet.getString("role")));
		return user;
	}

}
