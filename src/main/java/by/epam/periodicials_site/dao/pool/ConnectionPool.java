package by.epam.periodicials_site.dao.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {
	
	private static ConnectionPool instance;
	
	private static final String DB_CONFIG_FILE = "db";
	private static final String RESOURCE_DRIVER_NAME = "db.driver";
	private static final String RESOURCE_URL = "db.url";
	private static final String RESOURCE_USER = "db.user";
	private static final String RESOURCE_PASSWORD = "db.password";
	private static final String RESOURCE_MIN_CONNECTION_COUNT = "db.min_connection_count";
	private static final String RESOURCE_MAX_CONNECTION_COUNT = "db.max_connection_count";
	
	private String url;
	private String user;
	private String password;
	private int maxConnectionCount;
	private int minConnectionCount;
	private final AtomicInteger currentConnectionNumber = new AtomicInteger(0);
	
	private BlockingQueue<ConnectionProxy> freeConnections;
	private ConcurrentHashMap<ConnectionProxy, Long> occupiedConnections;
	private PoolChecker poolChecker;
	private Lock lock = new ReentrantLock();
	
	private ConnectionPool() {
		loadDBPropertiesAndRegisterDriver();
		initializeConnections();
		poolChecker = new PoolChecker();
		poolChecker.start();
	}
	
	public static ConnectionPool getInstance() {
		return instance;
	}
	
	public static void initialize() {
		if (instance == null) {
			instance = new ConnectionPool();
		}
	}
	
	public Connection getConnection() throws SQLException {
		ConnectionProxy connection = freeConnections.poll();
		while (connection == null) {
			createConnection();
			connection = freeConnections.poll();
		}
		occupiedConnections.put(connection, System.currentTimeMillis());
		return connection;
	}
	
	public void releaseConnection(ConnectionProxy connection) throws SQLException {
		connection.setAutoCommit(true);
		connection.setReadOnly(false);
		occupiedConnections.remove(connection);
		freeConnections.offer(connection);
	}
	
	public void closeDbResources(Connection connection, PreparedStatement... preparedStatements) throws SQLException {
		if (connection instanceof ConnectionProxy) {
			releaseConnection((ConnectionProxy) connection);
		}
		for (PreparedStatement pStatement : preparedStatements) {
			if (pStatement != null) {
				pStatement.close();
			}
		}
	}
	
	public void rollBack(Connection connection) throws SQLException {
		if (connection != null) {
			connection.rollback();
		}
	}
	
	public void destroy() {
		poolChecker.interrupt();
		try {
		for (ConnectionProxy connection : freeConnections) {
				connection.realClose();		
		}
		for (Map.Entry<ConnectionProxy, Long> entry : occupiedConnections.entrySet()) {
			entry.getKey().realClose();		
	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadDBPropertiesAndRegisterDriver() {
		ResourceBundle rb = ResourceBundle.getBundle(DB_CONFIG_FILE);
		String driverName = rb.getString(RESOURCE_DRIVER_NAME);
		url = rb.getString(RESOURCE_URL);
		user = rb.getString(RESOURCE_USER);
		password = rb.getString(RESOURCE_PASSWORD);
		minConnectionCount = Integer.parseInt(rb.getString(RESOURCE_MIN_CONNECTION_COUNT));
		maxConnectionCount = Integer.parseInt(rb.getString(RESOURCE_MAX_CONNECTION_COUNT));
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			// TODO logger
			throw new ConnectionPoolException("ConnectionPool initialization failed" ,e);
		}
	}
	
	private void initializeConnections() {
		freeConnections = new ArrayBlockingQueue<>(maxConnectionCount);
		occupiedConnections = new ConcurrentHashMap<>(maxConnectionCount);
		try {
			while (currentConnectionNumber.get() <= minConnectionCount) {
				createConnection();
			}
		} catch (SQLException e) {
			// TODO logger
			throw new ConnectionPoolException("Connection initialization failed", e);
		}
	}
	
	private void createConnection() throws SQLException {
		if (currentConnectionNumber.get() < maxConnectionCount) {
			lock.lock();
			if (currentConnectionNumber.get() < maxConnectionCount) {
				try {
					Connection connection = DriverManager.getConnection(url, user, password);
					ConnectionProxy connectionProxy = new ConnectionProxy(connection);
					freeConnections.add(connectionProxy);
					currentConnectionNumber.incrementAndGet();
				} finally {
					lock.unlock();
				}
			}
		}
	}
	
	private class PoolChecker extends Thread{
		
		private static final int MAX_SECONDS_OF_CONNECTION_OCCUPATION = 30;
		private static final int INTERVAL_BETWEEN_CHECKS_MINUTES = 1;

		@Override
		public void run() {
				try {
					while (!interrupted()) {
						checkOccupiedConnections();
						Thread.sleep(TimeUnit.MINUTES.toMillis(INTERVAL_BETWEEN_CHECKS_MINUTES));
					}
				} catch (InterruptedException e) {
					// TODO logger
			}
		}
		
		private void checkOccupiedConnections() {
			for (Map.Entry<ConnectionProxy, Long> entry : occupiedConnections.entrySet()) {
				ConnectionProxy connection = entry.getKey();
				long takeTime = entry.getValue();
				try {
					checkConnection(connection, takeTime);
					if (connection.isClosed()) {
						occupiedConnections.remove(connection);
						currentConnectionNumber.decrementAndGet();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		private void checkConnection(ConnectionProxy connection, long takeTime) throws SQLException {
			long timeOfOccupation = System.currentTimeMillis() - takeTime;
			if (timeOfOccupation >= TimeUnit.MINUTES.toMillis(MAX_SECONDS_OF_CONNECTION_OCCUPATION) && !connection.isClosed()) {
				connection.realClose();
			}
		}
		
	}
	
}
