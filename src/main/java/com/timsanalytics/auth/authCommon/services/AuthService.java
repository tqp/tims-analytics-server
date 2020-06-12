package com.timsanalytics.auth.authCommon.services;

import com.timsanalytics.auth.authCommon.beans.User;
import com.timsanalytics.auth.authCommon.dao.AuthDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final AuthDao authDao;

    @Autowired
    public AuthService(AuthDao authDao) {
        this.authDao = authDao;
    }

    public User getUserForAuthentication(String username) throws UsernameNotFoundException, DisabledException {
        this.logger.debug("AuthService -> getUserForAuthentication: username=" + username);
        return this.authDao.getUserForAuthentication(username);
    }

    public User getUserAndRolesForAuthentication(String username) throws UsernameNotFoundException {
        this.logger.debug("AuthService -> getUserAndRolesForAuthentication: username=" + username);
        User user = this.authDao.getUserForAuthentication(username);
        if (user != null) {
            this.logger.debug("AuthService -> getUserAndRolesForAuthentication: userGuid=" + user.getUserGuid());
            user.setRoles(this.authDao.getRolesByUser(user.getUserGuid()));
            return user;
        }
        return null;
    }
}
