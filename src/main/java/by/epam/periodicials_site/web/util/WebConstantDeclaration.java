package by.epam.periodicials_site.web.util;

public final class WebConstantDeclaration {
	
	private WebConstantDeclaration() {}
	
	public static final String VIEW_HOME = "/WEB-INF/jsp/home.jsp";
	public static final String VIEW_LOGIN = "/WEB-INF/jsp/login.jsp";
	public static final String VIEW_REGISTER = "/WEB-INF/jsp/register.jsp";
	
	public static final String REQUEST_PARAM_LOGIN_OR_EMAIL = "login_or_email";
	public static final String REQUEST_PARAM_PASSWORD = "password";
	public static final String REQUEST_PARAM_LOCALE = "locale";
	public static final String REQUEST_PARAM_LOGIN = "login";
	public static final String REQUEST_PARAM_NAME = "name";
	public static final String REQUEST_PARAM_SURNAME = "surName";
	public static final String REQUEST_PARAM_EMAIL = "email";
	
	public static final String SESSION_ATTR_LOCALE = "locale";
	public static final String SESSION_ATTR_REFER_PAGE = "referPage";
	public static final String SESSION_ATTR_USER_ID = "userId";
	public static final String SESSION_ATTR_USER_NAME = "userName";
	
	public static final String REQUEST_HEADER_REFER_PAGE = "referer";
	
	public static final String REQUEST_ATTR_MSG_LOGIN_FAIL = "loginFailedMsg";
	
	public static final String COMMAND_HOME = "/home";
	public static final String COMMAND_LOGIN = "/login";
	public static final String COMMAND_LOGOUT = "/logout";
	public static final String COMMAND_REGISTER = "/register";
	public static final String COMMAND_CHANGE_LOCALE = "/change-locale";
	
}
