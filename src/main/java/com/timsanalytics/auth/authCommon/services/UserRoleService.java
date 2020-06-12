package com.timsanalytics.auth.authCommon.services;

import com.timsanalytics.auth.authCommon.beans.User;
import com.timsanalytics.auth.authCommon.dao.UserRoleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final UserRoleDao userRoleDao;

    @Autowired
    public UserRoleService(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    public String createUserRole(String userGuid, String roleGuid, String status, User loggedInUser) {
        this.logger.debug("UserRoleService -> createUserRole");
        String item = this.userRoleDao.createUserRole(userGuid, roleGuid, status, loggedInUser);
        this.logger.debug("UserRoleService -> createUserRole -> response: " + item);
        return item;
    }

    public String updateUserRole(String userGuid, String roleGuid, String status, User loggedInUser) {
        this.logger.debug("UserRoleService -> updateUserRole");
        String item = this.userRoleDao.updateUserRole(userGuid, roleGuid, status, loggedInUser);
        this.logger.debug("UserRoleService -> updateUserRole -> response: " + item);
        return item;
    }
}
