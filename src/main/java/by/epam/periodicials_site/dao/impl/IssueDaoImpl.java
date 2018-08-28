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
import by.epam.periodicials_site.dao.IssueDao;
import by.epam.periodicials_site.dao.pool.ConnectionPool;
import by.epam.periodicials_site.entity.Issue;

public class IssueDaoImpl implements IssueDao{
	
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	private static final String READ_FOR_BULICATION_BETWEEN_DATES = "SELECT id, date_of_publication, publication_id, file, description FROM issues WHERE publication_id=? AND TIMESTAMP(date_of_publication) BETWEEN ? AND ?";
	private static final String READ_ISSUE = "SELECT id, date_of_publication, publication_id, file, description FROM issues WHERE id=?";
	private static final String CREATE_ISSUE = "INSERT INTO `periodicals_website`.`issues` (`date_of_publication`, `publication_id`, `file`, `description`) VALUES (?, ?, ?, ?)";
	
	private static final String ID = "id";
	private static final String DATE_OF_PUBLICATION = "date_of_publication";
	private static final String PUBLICATION_ID = "publication_id";
	private static final String DESCRIPTION = "description";
	private static final String FILE = "file";
	
	@Override
	public void create(Issue issue) throws DaoException {
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(CREATE_ISSUE, Statement.RETURN_GENERATED_KEYS)
		){
			ps.setTimestamp(1, new Timestamp(issue.getDateOfPublication().getTime()));
			ps.setInt(2, issue.getPublicationId());
			ps.setString(3, issue.getFile());
			ps.setString(4, issue.getDescription());
			int result = ps.executeUpdate();
			
			if (result > 0) {
				ResultSet resultSet = ps.getGeneratedKeys();
				resultSet.next();
				issue.setId(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			throw new DaoException("Exception creating issue", e);
		}
	}

	@Override
	public Issue read(int id) throws DaoException {
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_ISSUE)
		){
			ps.setInt(1, id);
			
			ResultSet resultSet = ps.executeQuery();
			
			if (resultSet.next()) {
				return formIssue(resultSet);
			}			
		} catch (SQLException e) {
			throw new DaoException("Exception reading issue", e);
		}
		return null;
	}

	@Override
	public List<Issue> readForPublicationBetweenDates(int publicationId, Date startDate, Date endDate) throws DaoException {
		List<Issue> issues = new ArrayList<>();
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_FOR_BULICATION_BETWEEN_DATES)
		){
			ps.setInt(1, publicationId);
			ps.setTimestamp(2, new Timestamp(startDate.getTime()));
			ps.setTimestamp(3, new Timestamp(endDate.getTime()));
			
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Issue issue = formIssue(resultSet);
				issues.add(issue);
			}
		} catch (SQLException e) {
			throw new DaoException("Exception reading issues", e);
		}
		return issues;
	}
	
	private Issue formIssue(ResultSet resultSet) throws SQLException {
		Issue issue = new Issue();
		
		issue.setId(resultSet.getInt(ID));
		issue.setDateOfPublication(resultSet.getDate(DATE_OF_PUBLICATION));
		issue.setPublicationId(resultSet.getInt(PUBLICATION_ID));
		issue.setDescription(resultSet.getString(DESCRIPTION));
		issue.setFile(resultSet.getString(FILE));
		
		return issue;
	}

}
