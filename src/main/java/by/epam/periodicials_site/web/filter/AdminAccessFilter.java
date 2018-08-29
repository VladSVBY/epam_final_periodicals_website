package by.epam.periodicials_site.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.periodicials_site.entity.Role;
import by.epam.periodicials_site.web.util.HttpUtil;
import by.epam.periodicials_site.web.util.WebConstantDeclaration;

public class AdminAccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
    	HttpServletResponse httpResponse = (HttpServletResponse) response;
    	Role role = (Role) httpRequest.getSession().getAttribute(WebConstantDeclaration.SESSION_ATTR_USER_ROLE);
    	
    	if (role == null){
    		httpResponse.sendRedirect(HttpUtil.formRedirectUrl(httpRequest, WebConstantDeclaration.COMMAND_LOGIN));
    	} else if (role == Role.ADMIN) {
    		chain.doFilter(httpRequest, httpResponse);
    	} else {
    		httpResponse.sendRedirect(HttpUtil.formRedirectUrl(httpRequest, WebConstantDeclaration.COMMAND_HOME));
    	}
    }

}
