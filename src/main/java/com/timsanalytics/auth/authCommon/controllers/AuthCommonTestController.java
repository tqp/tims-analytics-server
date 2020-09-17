package com.timsanalytics.auth.authCommon.controllers;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.auth.authCommon.services.AuthCommonTestService;
import com.timsanalytics.common.beans.KeyValueString;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/auth-common/test")
@Tag(name = "Test", description = "This is an endpoint to be used for testing purposes.")
public class AuthCommonTestController {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final AuthCommonTestService authCommonTestService;

    @Autowired
    public AuthCommonTestController(AuthCommonTestService authCommonTestService) {
        this.authCommonTestService = authCommonTestService;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Test Endpoint", tags = {"Test"}, description = "This is a test Service.")
    public ResponseEntity<KeyValue> testMethod() {
        this.logger.debug("TestController -> testMethod");
        try {
            return ResponseEntity.ok()
                    .body(this.authCommonTestService.testMethod());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @GetMapping(value = "/test-get-request", produces = "application/json")
    @Operation(summary = "exampleGetRequest", tags = {"Test"}, description = "GET Request")
    public KeyValueString exampleGetRequest() {
        return this.authCommonTestService.exampleGetRequest();
    }

    @ResponseBody
    @GetMapping(value = "/test-post-request", produces = "application/json")
    @Operation(summary = "examplePostRequest", tags = {"Test"}, description = "POST Request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Endpoint Not Found"),
    })
    public KeyValueString examplePostRequest() {
        return this.authCommonTestService.examplePostRequest();
    }

    @ResponseBody
    @GetMapping(value = "/test-get-request-with-path-variable/{pathVariable}", produces = "application/json")
    @Operation(summary = "examplePostRequestWithPathVariable", tags = {"Test"}, description = "POST Request with Path Variable")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Endpoint Not Found"),
    })
    public KeyValueString examplePostRequestWithPathVariable(@Parameter(description = "Path Variable", required = true, example = "pathVariable1") @PathVariable String pathVariable) {
        this.logger.debug("pathVariable: " + pathVariable);
        return this.authCommonTestService.examplePostRequestWithPathVariable();
    }

    @ResponseBody
    @GetMapping(value = "/test-get-request-with-request-body", produces = "application/json")
    @Operation(summary = "examplePostRequestWithRequestBody", tags = {"Test"}, description = "POST Request with Request Body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Endpoint Not Found"),
    })
    public KeyValueString examplePostRequestWithRequestBody(@Parameter(description = "Path Variable", required = true, example = "pathVariable1") @RequestBody KeyValueString requestBody) {
        this.logger.debug("key: " + requestBody.getKey());
        this.logger.debug("value: " + requestBody.getValue());
        return this.authCommonTestService.examplePostRequestWithRequestBody();
    }

}
