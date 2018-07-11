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

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.ReviewDao;
import by.epam.periodicials_site.dao.pool.ConnectionPool;
import by.epam.periodicials_site.entity.Review;

public class ReviewDaoImpl implements ReviewDao{
	
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	private static final String READ_REVIEWS_FOR_PUBLICATION = "SELECT id, id_user, id_publication, date_of_publication, text, mark FROM reviews WHERE id_publication=? ORDER BY date_of_publication";
	private static final String INSERT_REVIEW = "INSERT INTO `periodicals_website`.`reviews` (`id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (?, ?, ?, ?, ?);";
	
	private static final String ID = "id";
	private static final String ID_USER = "id_user";
	private static final String ID_PUBLICATION = "id_publication";
	private static final String DATE_OF_PUBLICATION = "date_of_publication";
	private static final String TEXT = "text";
	private static final String MARK = "mark";

	@Override
	public List<Review> readForPublication(int publicationId) throws DaoException {
		List<Review> reviews = new ArrayList<>();
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_REVIEWS_FOR_PUBLICATION)
		){
			ps.setInt(1, publicationId);
			
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Review review = formReview(resultSet);
				reviews.add(review);
			}
		} catch (SQLException e) {
			// TODO logger
			throw new DaoException("Exception reading reviews", e);
		}
		return reviews;
	}
	
	@Override
	public void create(Review review) throws DaoException {
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(INSERT_REVIEW, Statement.RETURN_GENERATED_KEYS)
		){
			ps.setInt(1, review.getUserId());
			ps.setInt(2, review.getPublicationId());
			ps.setTimestamp(3, new Timestamp(review.getDateOfPublication().getTime()));
			ps.setString(4, review.getText());
			ps.setByte(5, review.getMark());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO logger
			throw new DaoException("Exception creating review", e);
		}
	}
	
	private Review formReview(ResultSet resultSet) throws SQLException {
		Review review = new Review();
		review.setId(resultSet.getInt(ID));
		review.setUserId(resultSet.getInt(ID_USER));
		review.setPublicationId(resultSet.getInt(ID_PUBLICATION));
		review.setDateOfPublication(new Date(resultSet.getTimestamp(DATE_OF_PUBLICATION).getTime()));
		review.setText(resultSet.getString(TEXT));
		review.setMark(resultSet.getByte(MARK));
		return review;
	}

}
