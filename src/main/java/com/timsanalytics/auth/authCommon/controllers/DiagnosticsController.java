package com.timsanalytics.auth.authCommon.controllers;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/diagnostics")
@Tag(name = "Diagnostics Endpoints", description = "These endpoints are used to test endpoint security.")
public class DiagnosticsController {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    // AUTHORITY ACCESS TESTS

    @ResponseBody
    @RequestMapping(value = "/endpoint/open", method = RequestMethod.GET)
    @Operation(summary = "Tests access to unrestricted endpoints", tags = {"Test Endpoints"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<KeyValue> OpenTest() {
        this.logger.debug("EndpointTestController -> OpenTest");
        return ResponseEntity
                .ok()
                .body(new KeyValue("Open", "Connected to Open Endpoint"));
    }

    @ResponseBody
    @RequestMapping(value = "/endpoint/guest", method = RequestMethod.GET)
    @Operation(summary = "Tests access to Guest-restricted endpoints", tags = {"Test Endpoints"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<KeyValue> GuestTest() {
        this.logger.debug("EndpointTestController -> GuestTest");
        return ResponseEntity
                .ok()
                .body(new KeyValue("Open", "Connected to Guest Endpoint"));
    }

    @ResponseBody
    @RequestMapping(value = "/endpoint/user", method = RequestMethod.GET)
    @Operation(summary = "Tests access to User-restricted endpoints", tags = {"Test Endpoints"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<KeyValue> UserTest() {
        this.logger.debug("EndpointTestController -> UserTest");
        return ResponseEntity
                .ok()
                .body(new KeyValue("Open", "Connected to User Endpoint"));
    }

    @ResponseBody
    @RequestMapping(value = "/endpoint/manager", method = RequestMethod.GET)
    @Operation(summary = "Tests access to Manager-restricted endpoints", tags = {"Test Endpoints"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<KeyValue> ManagerTest() {
        this.logger.debug("EndpointTestController -> ManagerTest");
        return ResponseEntity
                .ok()
                .body(new KeyValue("Open", "Connected to Manager Endpoint"));
    }

    @ResponseBody
    @RequestMapping(value = "/endpoint/admin", method = RequestMethod.GET)
    @Operation(summary = "Tests access to Admin-restricted endpoints", tags = {"Test Endpoints"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<KeyValue> AdminTest() {
        this.logger.debug("EndpointTestController -> AdminTest");
        return ResponseEntity
                .ok()
                .body(new KeyValue("Open", "Connected to Admin Endpoint"));
    }

    @ResponseBody
    @RequestMapping(value = "/endpoint/developer", method = RequestMethod.GET)
    @Operation(summary = "Tests access to Developer-restricted endpoints", tags = {"Test Endpoints"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<KeyValue> DeveloperTest() {
        this.logger.debug("EndpointTestController -> DeveloperTest");
        return ResponseEntity
                .ok()
                .body(new KeyValue("Open", "Connected to Developer Endpoint"));
    }

    // METHOD TESTS

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @Operation(summary = "Perform a test GET request")
    public ResponseEntity<KeyValue> testGet() {
        this.logger.debug("EndpointTestController -> testGet");
        return ResponseEntity
                .ok()
                .body(new KeyValue("Response", "GET Success"));
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @Operation(summary = "Perform a test POST request")
    public ResponseEntity<KeyValue> testPost() {
        this.logger.debug("EndpointTestController -> testPost");
        return ResponseEntity
                .ok()
                .body(new KeyValue("Response", "POST Success"));
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @Operation(summary = "Perform a test PUT request")
    public ResponseEntity<KeyValue> testPut() {
        this.logger.debug("EndpointTestController -> testPut");
        return ResponseEntity
                .ok()
                .body(new KeyValue("Response", "PUT Success"));
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @Operation(summary = "Perform a test DELETE request")
    public ResponseEntity<KeyValue> testDelete() {
        this.logger.debug("EndpointTestController -> testDelete");
        return ResponseEntity
                .ok()
                .body(new KeyValue("Response", "DELETE Success"));
    }

}
