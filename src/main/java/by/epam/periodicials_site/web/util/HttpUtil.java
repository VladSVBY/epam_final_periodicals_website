package by.epam.periodicials_site.web.util;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import javax.servlet.http.HttpServletRequest;

public final class HttpUtil {
	
	private HttpUtil() {}
	
	public static String getReferPage(HttpServletRequest request) {
		return request.getHeader(REQUEST_HEADER_REFER_PAGE);
	}

}
