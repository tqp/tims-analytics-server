package com.timsanalytics.auth.authCommon.controllers;

import com.timsanalytics.auth.authCommon.beans.User;
import com.timsanalytics.auth.authCommon.services.UserService;
import com.timsanalytics.common.beans.ServerSidePaginationRequest;
import com.timsanalytics.common.beans.ServerSidePaginationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@RestController
@RequestMapping("/auth/v1/user")
@Tag(name = "User", description = "User")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @ResponseBody
//    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Create User", tags = {"User"}, description = "Create User", security = @SecurityRequirement(name = "bearerAuth"))
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        try {
//            return ResponseEntity.ok()
//                    .body(userService.createUser(user));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    @ResponseBody
    @RequestMapping(value = "/ssp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get User List (SSP)", tags = {"User"}, description = "Get User List (SSP)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ServerSidePaginationResponse<User>> getUserList_SSP(@RequestBody ServerSidePaginationRequest serverSidePaginationRequest) {
        long startTime = new Date().getTime();
        try {
            ServerSidePaginationResponse<User> container = this.userService.getUserList_SSP(serverSidePaginationRequest);
            container.setRequestTime(new Date().getTime() - startTime);
            return ResponseEntity.ok()
                    .body(container);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

//    @RequestMapping(value = "/{userGuid}", method = RequestMethod.GET)
//    @Operation(summary = "Get User Detail", tags = {"User"}, security = @SecurityRequirement(name = "bearerAuth"))
//    public ResponseEntity<User> getUserDetail(@Parameter(description = "User GUID", required = true) @PathVariable String userGuid) {
//        try {
//            User user = userService.getUserDetail(userGuid);
//            return ResponseEntity.ok()
//                    .body(user);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    @ResponseBody
//    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Update User", tags = {"User"}, description = "Update User", security = @SecurityRequirement(name = "bearerAuth"))
//    public ResponseEntity<User> updateUser(@RequestBody User user) {
//        try {
//            return ResponseEntity.ok()
//                    .body(userService.updateUser(user));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    @ResponseBody
//    @RequestMapping(value = "/{userGuid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Delete User", tags = {"User"}, description = "Delete User", security = @SecurityRequirement(name = "bearerAuth"))
//    public ResponseEntity<KeyValue> deleteUser(@Parameter(description = "User GUID", required = true) @PathVariable String userGuid) {
//        try {
//            return ResponseEntity.ok()
//                    .body(userService.deleteUser(userGuid));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
