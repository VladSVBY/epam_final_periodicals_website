package by.epam.periodicials_site.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.api.jdbc.Statement;

import by.epam.periodicials_site.dao.BalanceOperationDao;
import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.UserDao;
import by.epam.periodicials_site.dao.pool.ConnectionPool;
import by.epam.periodicials_site.entity.BalanceOperation;
import by.epam.periodicials_site.entity.BalanceOperationType;

public class BalnceOperationDaoImpl implements BalanceOperationDao{
	
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	private UserDao userDao = DaoFactory.getUserDao();
	
	private static final String READ_USER_BALANCE_OPERATIONS = "SELECT id, id_user, date, sum, type FROM balance_operations WHERE id_user=? ORDER BY date DESC";
	private static final String CREATE_BALANCE_OPERATION = "INSERT INTO `periodicals_website`.`balance_operations` (`id_user`, `date`, `sum`, `type`) VALUES (?, ?, ?, ?)";
	
	private static final String ID = "id";
	private static final String USER_ID = "id_user";
	private static final String DATE = "date";
	private static final String SUM = "sum";
	private static final String TYPE = "type";
	
	@Override
	public List<BalanceOperation> readForUser(int userID) throws DaoException {
		List<BalanceOperation> balanceOperations = new ArrayList<>();
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_USER_BALANCE_OPERATIONS)
		){
			ps.setInt(1, userID);
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {	
				BalanceOperation balanceOperation = formBalanceOpertion(resultSet);
				balanceOperations.add(balanceOperation);
			}
		} catch (SQLException e) {
			// TODO logger
			throw new DaoException("Exception reading balance operations", e);
		}
		return balanceOperations;
	}
	
	@Override
	public void create(BalanceOperation balanceOperation) throws DaoException {
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(CREATE_BALANCE_OPERATION, Statement.RETURN_GENERATED_KEYS)
		){
			connection.setAutoCommit(false);
			ps.setInt(1, balanceOperation.getIdUser());
			ps.setTimestamp(2, new Timestamp(balanceOperation.getDate().getTime()));
			ps.setDouble(3, balanceOperation.getSum());
			ps.setString(4, balanceOperation.getType().name());
			
			ps.executeUpdate();
			if (balanceOperation.getType() == BalanceOperationType.PAYMENT_OF_SUBSCRIPTION) {
				userDao.removeFromBalanceTransaction(balanceOperation.getIdUser(), balanceOperation.getSum(), connection);
			} else {
				userDao.addToBalanceTransaction(balanceOperation.getIdUser(), balanceOperation.getSum(), connection);
			}
			connection.commit();
			 
		} catch (SQLException e) {
			// TODO logger
			throw new DaoException("Exception creating balance operation", e);
		}
	}

	@Override
	public void createTransaction(BalanceOperation balanceOperation, Connection connection) throws DaoException {
		try (PreparedStatement ps = connection.prepareStatement(CREATE_BALANCE_OPERATION, Statement.RETURN_GENERATED_KEYS)
		){
			ps.setInt(1, balanceOperation.getIdUser());
			ps.setTimestamp(2, new Timestamp(balanceOperation.getDate().getTime()));
			ps.setDouble(3, balanceOperation.getSum());
			ps.setString(4, balanceOperation.getType().name());
			
			int result = ps.executeUpdate();
			userDao.addToBalanceTransaction(balanceOperation.getIdUser(), balanceOperation.getSum(), connection);
		} catch (SQLException e) {
			// TODO logger
			throw new DaoException("Exception creating balance operation", e);
		}
	}
	
	private BalanceOperation formBalanceOpertion(ResultSet resultSet) throws SQLException {
		BalanceOperation balanceOperation = new BalanceOperation();
		balanceOperation.setId(resultSet.getInt(ID));
		balanceOperation.setIdUser(resultSet.getInt(USER_ID));
		balanceOperation.setDate(resultSet.getDate(""));
		balanceOperation.setSum(resultSet.getDouble(SUM));
		balanceOperation.setType(BalanceOperationType.valueOf(resultSet.getString(TYPE)));
		return balanceOperation;
	}

}
