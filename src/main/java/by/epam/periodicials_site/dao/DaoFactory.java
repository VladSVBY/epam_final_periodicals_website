package by.epam.periodicials_site.dao;

import by.epam.periodicials_site.dao.impl.BalnceOperationDaoImpl;
import by.epam.periodicials_site.dao.impl.IssueDaoImpl;
import by.epam.periodicials_site.dao.impl.PublicationDaoImpl;
import by.epam.periodicials_site.dao.impl.ReviewDaoImpl;
import by.epam.periodicials_site.dao.impl.SubscriptionDaoImpl;
import by.epam.periodicials_site.dao.impl.ThemeDaoImpl;
import by.epam.periodicials_site.dao.impl.TypeDaoImpl;
import by.epam.periodicials_site.dao.impl.UserDaoImpl;

public final class DaoFactory {

	private DaoFactory() {}
	
	private static final TypeDao TYPE_DAO = new TypeDaoImpl();
	private static final ThemeDao THEME_DAO = new ThemeDaoImpl();
	private static final UserDao USER_DAO = new UserDaoImpl();
	private static final BalanceOperationDao BALANCE_OPERATION_DAO = new BalnceOperationDaoImpl();
	private static final PublicationDao PUBLICATION_DAO = new PublicationDaoImpl();
	private static final SubscriptionDao SUBSCRIPTION_DAO = new SubscriptionDaoImpl();
	private static final ReviewDao REVIEW_DAO = new ReviewDaoImpl();
	private static final IssueDao ISSUE_DAO = new IssueDaoImpl();
	
	public static UserDao getUserDao() {
		return USER_DAO;
	}
	
	public static PublicationDao getPublicationDao() {
		return PUBLICATION_DAO;
	}
	
	public static SubscriptionDao getSubscriptionDao() {
		return SUBSCRIPTION_DAO;
	}

	public static BalanceOperationDao getBalanceOperationDao() {
		return BALANCE_OPERATION_DAO;
	}

	public static ReviewDao getReviewDao() {
		return REVIEW_DAO;
	}

	public static ThemeDao getThemeDao() {
		return THEME_DAO;
	}

	public static IssueDao getIssueDao() {
		return ISSUE_DAO;
	}

	public static TypeDao getTypeDao() {
		return TYPE_DAO;
	}
		
}
