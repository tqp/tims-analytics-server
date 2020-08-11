package com.timsanalytics.admin.controllers;

import com.timsanalytics.admin.services.AppService;
import com.timsanalytics.auth.authCommon.beans.KeyValue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/app/v1")
@Tag(name = "App", description = "This is used to query application-related data.")
public class AppController {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final AppService appService;

    @Autowired
    public AppController(AppService appService) {
        this.appService = appService;
    }

    @ResponseBody
    @RequestMapping(value = "/build-timestamp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Build Timestamp", tags = {"App"}, description = "Gets the date-time when the server was last built.", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<KeyValue> getBuildTimestamp() {
        this.logger.trace("AppController -> getBuildTimestamp");
        try {
            return ResponseEntity.ok()
                    .body(new KeyValue("buildTimestamp", this.appService.getBuildTimestamp()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
