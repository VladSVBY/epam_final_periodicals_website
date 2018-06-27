package by.epam.periodicials_site.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static final String CONTEXT_PARAM_NAME = "encoding";

    private String encodingName;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encodingName = filterConfig.getServletContext().getInitParameter(CONTEXT_PARAM_NAME);
        if (encodingName == null) {
            encodingName = DEFAULT_ENCODING;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        request.setCharacterEncoding(encodingName);
        response.setCharacterEncoding(encodingName);
        chain.doFilter(request, response);
    }

}
