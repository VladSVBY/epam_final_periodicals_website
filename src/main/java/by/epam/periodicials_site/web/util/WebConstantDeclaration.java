package by.epam.periodicials_site.web.util;

public final class WebConstantDeclaration {
	
	private WebConstantDeclaration() {}
	
	public static final String VIEW_HOME = "/WEB-INF/jsp/home.jsp";
	public static final String VIEW_LOGIN = "/WEB-INF/jsp/login.jsp";
	public static final String VIEW_REGISTER = "/WEB-INF/jsp/register.jsp";
	public static final String VIEW_USER_PROFILE = "/WEB-INF/jsp/user/profile.jsp";
	public static final String VIEW_USER_ACTIVE_SUBSCRIPTIONS = "/WEB-INF/jsp/user/subscriptions.jsp";
	public static final String VIEW_USER_SUBSCRIPTION_HISTORY = "/WEB-INF/jsp/user/subscription_history.jsp";
	public static final String VIEW_USER_BALANCE_OPERATION_HISTORY = "/WEB-INF/jsp/user/balance_operation_history.jsp";
	public static final String VIEW_PUBLICATION_DETAILS = "/WEB-INF/jsp/publication.jsp";
	public static final String VIEW_ADD_PUBLICATION_FORM = "/WEB-INF/jsp/admin/add_publication_form.jsp";
	
	public static final String REQUEST_PARAM_LOGIN_OR_EMAIL = "login_or_email";
	public static final String REQUEST_PARAM_PASSWORD = "password";
	public static final String REQUEST_PARAM_LOCALE = "locale";
	public static final String REQUEST_PARAM_LOGIN = "login";
	public static final String REQUEST_PARAM_NAME = "name";
	public static final String REQUEST_PARAM_SURNAME = "surName";
	public static final String REQUEST_PARAM_EMAIL = "email";
	public static final String REQUEST_PARAM_SUBSCRIPTION_ID = "subscription_id";
	public static final String REQUEST_PARAM_PUBLICATION_ID = "id";
	public static final String REQUEST_PARAM_THEME_ID = "theme";
	public static final String REQUEST_PARAM_SORT_ID = "order";
	public static final String REQUEST_PARAM_REVIEW_TEXT = "text";
	public static final String REQUEST_PARAM_REVIEW_MARK = "mark";
	public static final String REQUEST_PARAM_REVIEW_ID_OF_PUBLICATION = "id_publication";
	public static final String REQUEST_PARAM_SUM_FOR_REPLENISHMENT = "sum";
	
	public static final String SESSION_ATTR_LOCALE = "locale";
	public static final String SESSION_ATTR_REFER_PAGE = "referPage";
	public static final String SESSION_ATTR_USER_ID = "userId";
	public static final String SESSION_ATTR_USER_NAME = "userName";
	public static final String SESSION_ATTR_USER_ROLE = "userRole";
	
	public static final String REQUEST_HEADER_REFER_PAGE = "referer";
	
	public static final String REQUEST_ATTR_MSG_LOGIN_FAIL = "loginFailedMsg";
	public static final String REQUEST_ATTR_PUBLICATION_LIST = "publications";
	public static final String REQUEST_ATTR_PUBLICATION_NAMES = "publicationNames";
	public static final String REQUEST_ATTR_ACTIVE_SUBSCRIPTION_LIST = "active_subscriptions";
	public static final String REQUEST_ATTR_SUBSCRIPTION_LIST = "subscriptions";
	public static final String REQUEST_ATTR_BALANCE_OPERATION_LIST = "balance_operations";
	public static final String REQUEST_ATTR_PUBLICATION = "publication";
	public static final String REQUEST_ATTR_REVIEWS = "reviews";
	public static final String REQUEST_ATTR_THEMES = "themes";
	public static final String REQUEST_ATTR_USER = "user";
	public static final String REQUEST_ATTR_PUBLICATION_SEARCH_CRITERIA = "search_criteria";
	
	public static final String COMMAND_HOME = "/home";
	public static final String COMMAND_LOGIN = "/login";
	public static final String COMMAND_LOGOUT = "/logout";
	public static final String COMMAND_REGISTER = "/register";
	public static final String COMMAND_CHANGE_LOCALE = "/change-locale";
	public static final String COMMAND_SHOW_USER_PROFILE = "/profile";
	public static final String COMMAND_SHOW_USER_ACTIVE_SUBSCRIPTIONS = "/subscriptions";
	public static final String COMMAND_SHOW_USER_SUBSCRIPTION_HISTORY = "/subscription-history";
	public static final String COMMAND_SHOW_USER_BALANCE_OPERATION_HISTORY = "/balance-operation-history";
	public static final String COMMAND_TERMINATE_SUBSCRIPTION = "/terminate-subscription";
	public static final String COMMAND_SHOW_PUBLICATION = "/publication";
	public static final String COMMAND_ADD_REVIEW = "/add-review";
	public static final String COMMAND_REPLENISH_BALANCE = "/replenish-balance";
	
}
