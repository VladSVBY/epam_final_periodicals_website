package by.epam.periodicials_site.service;

import by.epam.periodicials_site.service.impl.BalanceOperationServiceImpl;
import by.epam.periodicials_site.service.impl.IssueServiceImpl;
import by.epam.periodicials_site.service.impl.PublicationServiceImpl;
import by.epam.periodicials_site.service.impl.ReviewServiceImpl;
import by.epam.periodicials_site.service.impl.SubscriptionServiceImpl;
import by.epam.periodicials_site.service.impl.ThemeServiceImpl;
import by.epam.periodicials_site.service.impl.UserServiceImpl;

public final class ServiceFactory {
	
	private ServiceFactory() {}
	
	private static final ThemeService THEME_SERVICE = new ThemeServiceImpl();
	private static final UserService USER_SERVICE = new UserServiceImpl();
	private static final PublicationService PUBLICATION_SERVICE = new PublicationServiceImpl();
	private static final SubscriptionService SUBSCRIPTION_SERVICE = new SubscriptionServiceImpl();
	private static final BalanceOperationService BALANCE_OPERATION_SERVICE = new BalanceOperationServiceImpl();
	private static final ReviewService REVIEW_SERVICE = new ReviewServiceImpl();
	private static final IssueService ISSUE_SERVICE = new IssueServiceImpl();

	public static UserService getUserService() {
		return USER_SERVICE;
	}

	public static PublicationService getPublicationService() {
		return PUBLICATION_SERVICE;
	}

	public static BalanceOperationService getBalanceOperationService() {
		return BALANCE_OPERATION_SERVICE;
	}

	public static SubscriptionService getSubscriptionService() {
		return SUBSCRIPTION_SERVICE;
	}

	public static ReviewService getReviewService() {
		return REVIEW_SERVICE;
	}

	public static ThemeService getThemeService() {
		return THEME_SERVICE;
	}

	public static IssueService getIssueService() {
		return ISSUE_SERVICE;
	} 
	
}
