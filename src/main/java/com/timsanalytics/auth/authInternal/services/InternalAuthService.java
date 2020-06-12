package com.timsanalytics.auth.authInternal.services;

import com.timsanalytics.auth.authCommon.beans.User;
import com.timsanalytics.auth.authCommon.services.AuthService;
import com.timsanalytics.auth.authCommon.services.AuthenticationException;
import com.timsanalytics.auth.authCommon.services.TokenService;
import com.timsanalytics.auth.authInternal.beans.InternalAuthResponse;
import com.timsanalytics.auth.authInternal.beans.LoginUser;
import com.timsanalytics.auth.authInternal.dao.InternalAuthDao;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Objects;

@Service
public class InternalAuthService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final AuthService authService;
    private final TokenService tokenService;
    private final InternalAuthDao internalAuthDao;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public InternalAuthService(AuthService authService,
                               TokenService tokenService,
                               InternalAuthDao internalAuthDao,
                               AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.tokenService = tokenService;
        this.internalAuthDao = internalAuthDao;
        this.authenticationManager = authenticationManager; // See SpringWebSecurityConfig.java to see the Override for the authenticationManager Bean.
    }

    public ResponseEntity<?> submitUsernameAndPassword(String a_username, String b_password) {
        this.logger.debug("InternalAuthController -> submitUsernameAndPassword");
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(a_username);
        loginUser.setPassword(b_password);
        return this.attemptAuthentication(loginUser);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @Operation(summary = "Authenticate with User Object", description = "Attempt authentication using a given LoginUser object", tags = {"Authentication - Internal"})
    public ResponseEntity<?> attemptAuthentication(@RequestBody LoginUser loginUser) {

        // Attempt to authenticate the User credentials
        this.authenticate(loginUser.getUsername(), loginUser.getPassword());
        this.logger.debug("The User has been authenticated by the internal authentication provider.");

        // Get AppUser
        User appUser = this.authService.getUserAndRolesForAuthentication(loginUser.getUsername());

        // Update User metadata
        this.internalAuthDao.updateUserRecordFromInternalAuth(appUser);

        // Generate Token
        String token = this.generateToken(appUser);
        this.logger.debug("Token=" + token);

        return ResponseEntity.ok(new InternalAuthResponse(token));
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            // IMPORTANT: This 'authenticate' uses an @Override in this app's CustomAuthenticationProvider.
            // This allows this app to authenticate its own way (instead of using Hibernate or something like that).
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            this.logger.error("Authentication Exception: User is disabled.");
            throw new AuthenticationException("User is disabled", e);
        } catch (UsernameNotFoundException e) {
            this.logger.error("Authentication Exception: Username Not Found.");
            throw new AuthenticationException("UsernameNotFoundException", e);
        } catch (BadCredentialsException e) {
            this.logger.error("Authentication Exception: Bad credentials.");
            throw new AuthenticationException("Bad credentials", e);
        } catch (Exception e) {
            this.logger.error("General Exception. " + e);
            throw new AuthenticationException(e.getMessage(), e);
        }
    }

    private String generateToken(User user) {
        return this.tokenService.generateTokenFromUser(user);
    }

}
