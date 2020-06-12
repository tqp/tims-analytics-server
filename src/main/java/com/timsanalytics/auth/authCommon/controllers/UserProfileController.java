package com.timsanalytics.auth.authCommon.controllers;

import com.timsanalytics.auth.authCommon.beans.Role;
import com.timsanalytics.auth.authCommon.beans.User;
import com.timsanalytics.auth.authCommon.services.TokenService;
import com.timsanalytics.auth.authCommon.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/my-profile")
@Tag(name = "My Profile", description = "Profile")
public class UserProfileController {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public UserProfileController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    // READ: ITEM
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @Operation(summary = "Get My Profile", description = "Get My Profile", tags = {"My Profile"})
    public User getMyProfile(HttpServletRequest request) {
        User loggedInUser = this.tokenService.getUserFromRequest(request);
        this.logger.trace("MyProfileController -> getMyProfile: userGuid=" + loggedInUser.getUserGuid());
        try {
            return this.userService.getUser(loggedInUser.getUserGuid());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // UPDATE
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @Operation(summary = "Update My Profile", description = "Update My Profile", tags = {"My Profile"})
    public User updateMyProfile(HttpServletRequest request, @RequestBody User User) {
        this.logger.debug("############################################################################");
        this.logger.debug("MyProfileController -> updateMyProfile: userGuid=" + User.getUserGuid());
        try {
            User loggedInUser = this.tokenService.getUserFromRequest(request);
            this.logger.debug("loggedInUser=" + loggedInUser.getUserGuid());
            User item = this.userService.updateMyProfile(User, loggedInUser);
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // OTHER CRUD

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @Operation(summary = "Get My User Roles", description = "Get My User Roles", tags = {"My Profile"})
    public List<Role> getMyUserRoles(HttpServletRequest request) {
        User loggedInUser = this.tokenService.getUserFromRequest(request);
        try {
            return this.userService.getUserRoles(loggedInUser.getUserGuid());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    @Operation(summary = "Change My Password", description = "Change My Password", tags = {"My Profile"})
    public User changePassword(HttpServletRequest request, @RequestBody User User) {
        this.logger.debug("MyProfileController -> changePassword: userGuid=" + User.getUserGuid());
        try {
            User loggedInUser = this.tokenService.getUserFromRequest(request);
            User item = this.userService.changePassword(User, loggedInUser);
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/confirm-password", method = RequestMethod.PUT)
    @Operation(summary = "Confirm whether the provided password matches my password", description = "Confirm whether the provided password matches my password", tags = {"My Profile"})
    public Boolean confirmUserPassword(HttpServletRequest request, @RequestBody User testPassword) {
        User loggedInUser = this.tokenService.getUserFromRequest(request);
        User User = this.userService.getUser(loggedInUser.getUserGuid());
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.matches(testPassword.getPassword(), User.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // NON-CRUD CONTROLLERS

    @RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
    @Operation(summary = "Get the User ID for a given Username", description = "Get the User ID for a given Username", tags = {"My Profile"})
    public String getUserGuidByUsername(@PathVariable String username) {
        this.logger.debug(("UserController -> getUserIdByUsername: username=" + username));
        try {
            return this.userService.getUserGuidByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/{userGuid}/roles", method = RequestMethod.GET)
    @Operation(summary = "Get User Roles by User GUID", description = "Get User Roles by User GUID", tags = {"My Profile"})
    public List<Role> getUserRoles(@PathVariable String userGuid) {
        this.logger.debug(("UserController -> getUserRoles: userGuid=" + userGuid));
        try {
            return this.userService.getUserRoles(userGuid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
