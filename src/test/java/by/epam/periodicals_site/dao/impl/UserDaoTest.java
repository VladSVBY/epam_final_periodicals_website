package by.epam.periodicals_site.dao.impl;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.ReviewDao;
import by.epam.periodicials_site.dao.pool.ConnectionPool;
import by.epam.periodicials_site.entity.Review;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDaoTest {
	
	private  ReviewDao reviewDao = DaoFactory.getReviewDao();
	private static Review expectedReview;
	
	@BeforeClass
    public static void setUpBeforeClass() throws Exception {
       ConnectionPool.initialize();
       expectedReview = new Review(0, 1, 908, new Date(), "text", (byte)4);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        ConnectionPool.getInstance().destroy();
    }

    @Test
    public void a_createTest() throws DaoException {	
    	reviewDao.create(expectedReview);
    }
    
    @Test
    public void b_readTest() throws DaoException {
    	Review testReview = reviewDao.read(expectedReview.getId());

    	assertTrue(expectedReview.getText().equals(testReview.getText()));
    }
    
    @Test
    public void c_updateTest() throws DaoException {
    	expectedReview.setMark((byte)2);
    	
    	reviewDao.update(expectedReview);
    	Review testReview = reviewDao.read(expectedReview.getId());
    	
    	assertTrue(expectedReview.getMark() == testReview.getMark());
    }
    
    @Test
    public void d_deleteTest() throws DaoException {
    	reviewDao.delete(expectedReview.getId());
    	Review testReview = reviewDao.read(expectedReview.getId());

    	assertTrue(testReview == null);
    }
}
