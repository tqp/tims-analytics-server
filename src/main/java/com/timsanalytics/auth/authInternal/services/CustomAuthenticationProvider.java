package com.timsanalytics.auth.authInternal.services;

import com.timsanalytics.auth.authCommon.beans.User;
import com.timsanalytics.auth.authCommon.services.AuthService;
import com.timsanalytics.auth.authCommon.utils.BCryptEncoderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final AuthService authService;
    private final BCryptEncoderService bCryptEncoderService;

    @Autowired
    public CustomAuthenticationProvider(AuthService authService,
                                        BCryptEncoderService bCryptEncoderService) {
        this.authService = authService;
        this.bCryptEncoderService = bCryptEncoderService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        this.logger.debug("CustomAuthenticationProvider -> authenticate");
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = this.authService.getUserForAuthentication(username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        } else if ("Disabled".equalsIgnoreCase(user.getStatus())) {
            throw new DisabledException("UserDisabled");
        }

        Boolean matches = this.bCryptEncoderService.verify(authentication.getCredentials().toString(), user.getPassword());
        this.logger.debug("Credentials Authenticated: " + matches);

        if (matches) {
            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
        } else {
            throw new BadCredentialsException("Bad Credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
