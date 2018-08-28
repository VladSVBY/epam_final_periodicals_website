package by.epam.periodicials_site.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.dao.BalanceOperationDao;
import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.SubscriptionDao;
import by.epam.periodicials_site.dao.pool.ConnectionPool;
import by.epam.periodicials_site.entity.BalanceOperation;
import by.epam.periodicials_site.entity.Subscription;
import by.epam.periodicials_site.entity.SubscriptionStatus;

public class SubscriptionDaoImpl implements SubscriptionDao {
	
	private static final Logger logger = LogManager.getLogger(SubscriptionDaoImpl.class);

	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	private BalanceOperationDao balanceOperationDao = DaoFactory.getBalanceOperationDao();
	
	private static final String CREATE_SUBSCRIPTION = "INSERT INTO `periodicals_website`.`subscriptions` (`id_publication`, `id_user`, `start_date`, `end_date`, `price`, `status`) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String READ_USER_ACTIVE_SUBSCRIPTIONS = "SELECT id, id_user, id_publication, start_date, end_date, price, status FROM subscriptions WHERE id_user=? AND status='ACTIVE'";
	private static final String READ_USER_SUBSCRIPTIONS = "SELECT id, id_user, id_publication, start_date, end_date, price, status FROM subscriptions WHERE id_user=?";
	private static final String UPDATE_SUBSCRIPTION = "UPDATE periodicals_website.subscriptions SET `id_publication`=?, `id_user`=?, `start_date`=?, `end_date`=?, `price`=?, `status`=? WHERE `id`=?";
	private static final String READ_SUBSCRIPTION = "SELECT id, id_user, id_publication, start_date, end_date, price, status FROM subscriptions WHERE id=?";
	
	private static final String ID = "id";
	private static final String USER_ID = "id_user";
	private static final String PUBLICATION_ID = "id_publication";
	private static final String START_DATE = "start_date";
	private static final String END_DATE = "end_date";
	private static final String PRICE = "price";
	private static final String STATUS = "status";
	
	
	@Override
	public void create(Subscription subscription, BalanceOperation balanceOperation) throws DaoException {
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = connectionPool.getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(CREATE_SUBSCRIPTION, Statement.RETURN_GENERATED_KEYS);
			
			ps.setInt(1, subscription.getPublicationId());
			ps.setInt(2, subscription.getUserId());
			ps.setTimestamp(3, new Timestamp(subscription.getStartDate().getTime()));
			ps.setTimestamp(4, new Timestamp(subscription.getEndDate().getTime()));
			ps.setDouble(5, subscription.getPrice());
			ps.setString(6, subscription.getStatus().name());
			ps.executeUpdate();
			
			balanceOperationDao.createTransaction(balanceOperation, connection);
			
			connection.commit();
		} catch (SQLException | DaoException e) {
			try {
				connectionPool.rollBack(connection);
			} catch (SQLException e1) {
				throw new DaoException("Exception rollbacking subscription", e);
			}
			throw new DaoException("Exception terminating subscription", e);
		} finally {
			try {
				connectionPool.closeDbResources(connection, ps);
			} catch (SQLException e) {
				logger.warn("Closing of DB resources failed", e);
			}
		}		
	}

	@Override
	public void update(Subscription subscription) throws DaoException {
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(UPDATE_SUBSCRIPTION)
		){			
			ps.setInt(1, subscription.getPublicationId());
			ps.setInt(2, subscription.getUserId());
			ps.setTimestamp(3, new Timestamp(subscription.getStartDate().getTime()));
			ps.setTimestamp(4, new Timestamp(subscription.getEndDate().getTime()));
			ps.setDouble(5, subscription.getPrice());
			ps.setString(6, subscription.getStatus().name());
			ps.setInt(7, subscription.getId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Exception creating subscription", e);
		}
	}	
	
	@Override
	public List<Subscription> readActiveForUser(int userId) throws DaoException {
		List<Subscription> subscriptions = new ArrayList<>();
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_USER_ACTIVE_SUBSCRIPTIONS)
		){
			
			ps.setInt(1, userId);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Subscription subscription = formSubscription(resultSet);
				subscriptions.add(subscription);
			}
			
		} catch (SQLException e) {
			throw new DaoException("Exception reading subscriptions", e);
		}
		return subscriptions;
	}
	
	@Override
	public List<Subscription> readAllForUser(int userId) throws DaoException {
		List<Subscription> subscriptions = new ArrayList<>();
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_USER_SUBSCRIPTIONS)
		){
			
			ps.setInt(1, userId);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Subscription subscription = formSubscription(resultSet);
				subscriptions.add(subscription);
			}
			
		} catch (SQLException e) {
			throw new DaoException("Exception reading subscriptions", e);
		}
		return subscriptions;
	}
	
	@Override
	public Subscription read(int subscriptionId) throws DaoException {
		Subscription subscription = null;
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_SUBSCRIPTION)
		){
			
			ps.setInt(1, subscriptionId);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				subscription = formSubscription(resultSet);
			}
			
		} catch (SQLException e) {
			throw new DaoException("Exception reading subscriptions", e);
		}
		return subscription;
	}
	
	@Override
	public void terminate(Subscription subscription, BalanceOperation balanceOperation) throws DaoException {
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = connectionPool.getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(UPDATE_SUBSCRIPTION);
			
			ps.setInt(1, subscription.getPublicationId());
			ps.setInt(2, subscription.getUserId());
			ps.setTimestamp(3, new Timestamp(subscription.getStartDate().getTime()));
			ps.setTimestamp(4, new Timestamp(subscription.getEndDate().getTime()));
			ps.setDouble(5, subscription.getPrice());
			ps.setString(6, SubscriptionStatus.TERMINATED.name());
			ps.setInt(7, subscription.getId());
			ps.executeUpdate();
			
			balanceOperationDao.createTransaction(balanceOperation, connection);
			
			connection.commit();
		} catch (SQLException | DaoException e) {
			try {
				connectionPool.rollBack(connection);
			} catch (SQLException e1) {
				throw new DaoException("Exception rollbacking subscription", e);
			}
			throw new DaoException("Exception terminating subscription", e);
		} finally {
			try {
				connectionPool.closeDbResources(connection, ps);
			} catch (SQLException e) {
				logger.warn("Closing of DB resources failed", e);
			}
		}
	}
	
	private Subscription formSubscription(ResultSet resultSet) throws SQLException {
		Subscription subscription = new Subscription();
		subscription.setId(resultSet.getInt(ID));
		subscription.setUserId(resultSet.getInt(USER_ID));
		subscription.setPublicationId(resultSet.getInt(PUBLICATION_ID));
		subscription.setStartDate(new Date(resultSet.getTimestamp(START_DATE).getTime()));
		subscription.setEndDate(new Date(resultSet.getTimestamp(END_DATE).getTime()));
		subscription.setPrice(resultSet.getDouble(PRICE));
		subscription.setStatus(SubscriptionStatus.valueOf(resultSet.getString(STATUS)));
		return subscription;
	}

}
