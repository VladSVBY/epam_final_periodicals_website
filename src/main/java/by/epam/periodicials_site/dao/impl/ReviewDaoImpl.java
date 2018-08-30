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
import by.epam.periodicials_site.dao.ReviewDao;
import by.epam.periodicials_site.dao.pool.ConnectionPool;
import by.epam.periodicials_site.entity.Review;

public class ReviewDaoImpl implements ReviewDao{
	
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	private static final String READ_REVIEWS_FOR_PUBLICATION = "SELECT id, id_user, id_publication, date_of_publication, text, mark FROM reviews WHERE id_publication=? ORDER BY date_of_publication DESC";
	private static final String READ_REVIEW = "SELECT id, id_user, id_publication, date_of_publication, text, mark FROM reviews WHERE id=?";
	private static final String INSERT_REVIEW = "INSERT INTO `periodicals_website`.`reviews` (`id_user`, `id_publication`, `date_of_publication`, `text`, `mark`) VALUES (?, ?, ?, ?, ?);";
	private static final String UPDATE_REVIEW = "UPDATE `periodicals_website`.`reviews` SET `text`=?, `mark`=? WHERE `id`=?";
	private static final String DELETE_REVIEW = "DELETE FROM `periodicals_website`.`reviews` WHERE `id`=?";
			
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
			
			int result = ps.executeUpdate();
			
			if (result > 0) {
				ResultSet resultSet = ps.getGeneratedKeys();
				resultSet.next();
				review.setId(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			throw new DaoException("Exception creating review", e);
		}
	}

	@Override
	public void update(Review review) throws DaoException {
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(UPDATE_REVIEW)
		){
			ps.setString(1, review.getText());
			ps.setByte(2, review.getMark());
			ps.setInt(3, review.getId());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Exception updating review", e);
		}		
	}

	@Override
	public void delete(int id) throws DaoException {
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(DELETE_REVIEW)
		){
			ps.setInt(1, id);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Exception deleting review", e);
		}
	}
	
	@Override
	public Review read(int id) throws DaoException {
		try (Connection connection = connectionPool.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(READ_REVIEW)
		){
			ps.setInt(1, id);
			
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return formReview(resultSet);
			}
		} catch (SQLException e) {
			throw new DaoException("Exception reading reviews", e);
		}
		return null;
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
