package by.epam.periodicials_site.web.util;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import javax.servlet.http.HttpServletRequest;

import by.epam.periodicials_site.entity.LocaleType;

public final class HttpUtil {
	
	private HttpUtil() {}
	
	private static final String CONTROLLER_PATTERN = "/controller";
	
	public static String getReferPage(HttpServletRequest request) {
		return request.getHeader(REQUEST_HEADER_REFER_PAGE);
	}
	
	public static LocaleType getLocale(HttpServletRequest request) {
		String localeName = (String) request.getSession().getAttribute(SESSION_ATTR_LOCALE);
		if (localeName == null) {
			return LocaleType.EN_US;
		}
		try {
			return LocaleType.valueOf(localeName.toUpperCase());
		} catch (IllegalArgumentException e) {
			return LocaleType.EN_US;
		}
	}
	
	public static String formRedirectUrl(HttpServletRequest request, String command) {
		return request.getContextPath() + CONTROLLER_PATTERN + command;
	}

}
