package com.timsanalytics.auth.authCommon.utils;

import com.timsanalytics.auth.authCommon.beans.User;
import com.timsanalytics.auth.authCommon.services.AuthService;
import com.timsanalytics.auth.authCommon.services.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// REF: https://www.linkedin.com/pulse/json-web-token-jwt-spring-security-real-world-example-boris-trivic/

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final AuthService authService;
    private final Environment environment;
    private final TokenService tokenService;

    @Autowired
    public TokenAuthenticationFilter(Environment environment,
                                     AuthService authService,
                                     TokenService tokenService) {
        this.environment = environment;
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String TOKEN_PREFIX = environment.getProperty("jwt.token-prefix");
        String HEADER_STRING = environment.getProperty("jwt.header");
        String header = request.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;

        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, "");

            this.logger.debug("authToken: " + authToken);

            try {
                username = tokenService.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                this.logger.error("An error occurred while getting the username from the token.", e);
            } catch (ExpiredJwtException e) {
                this.logger.error("The token has expired and is not valid anymore.");
            } catch (SignatureException e) {
                this.logger.error("Authentication Failed: Username or password is not valid.");
            } catch (Exception e) {
                this.logger.error("Exception: " + e);
            }
        } else {
            this.logger.trace("Couldn't find bearer string. Will ignore the header.");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Get User
            User user = this.authService.getUserAndRolesForAuthentication(username);
            //this.generalUtilsService.PrintObject("TokenAuthenticationFilter -> doFilterInternal: appUser", appUser);

            // Create Authentication
            UsernamePasswordAuthenticationToken authentication =
                    tokenService.getAuthentication(authToken, SecurityContextHolder.getContext().getAuthentication(), user);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                this.logger.debug("Authenticated User: " + username + ". Setting security context.");
                SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
