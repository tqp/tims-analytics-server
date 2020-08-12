package com.timsanalytics.auditing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

//@Component // See RequestResponseFilterConfig REF
//@Order(1)
public class AuthFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        this.logger.trace("Initializing Filter: {}", this);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

        try {
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            this.logger.trace("Starting Transaction for req: {}", requestWrapper.getRequestURI());

            String method = requestWrapper.getMethod();
            String path = requestWrapper.getRequestURI();

            String[] ignoredMethods = {"GET"};
            String[] ignoredPaths = {
                    "/api/v1/person/ssp",
                    "/api/v1/person/person-friend-associations-ssp"
            };

            if (Arrays.stream(ignoredMethods).noneMatch(method::equals)) {
                if (Arrays.stream(ignoredPaths).noneMatch(path::equals)) {
                    System.out.println("Auth Filter: " + method + " " + path);
                }
            }

//            System.out.println("Response getStatus: " + responseWrapper.getStatus());
        }
    }

    @Override
    public void destroy() {
        this.logger.debug("Destroying filter: {}", this);
    }
}
