package com.timsanalytics.main.controllers;

import com.timsanalytics.main.beans.KeyValueString;
import com.timsanalytics.main.services.DiagnosticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diagnostics")
@Tag(name = "Diagnostics", description = "This is an endpoint to be used for testing purposes.")
public class DiagnosticsController {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private DiagnosticsService diagnosticsService;

    @Autowired
    public DiagnosticsController(DiagnosticsService diagnosticsService) {
        this.diagnosticsService = diagnosticsService;
    }

    @ResponseBody
    @GetMapping(value = "/test-get-request", produces = "application/json")
    @Operation(summary = "exampleGetRequest", tags = {"Diagnostics"}, description = "GET Request")
    public KeyValueString exampleGetRequest() {
        return this.diagnosticsService.exampleGetRequest();
    }

    @ResponseBody
    @GetMapping(value = "/test-post-request", produces = "application/json")
    @Operation(summary = "examplePostRequest", tags = {"Diagnostics"}, description = "POST Request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Endpoint Not Found"),
    })
    public KeyValueString examplePostRequest() {
        return this.diagnosticsService.examplePostRequest();
    }

    @ResponseBody
    @GetMapping(value = "/test-get-request-with-path-variable/{pathVariable}", produces = "application/json")
    @Operation(summary = "examplePostRequestWithPathVariable", tags = {"Diagnostics"}, description = "POST Request with Path Variable")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Endpoint Not Found"),
    })
    public KeyValueString examplePostRequestWithPathVariable(@Parameter(description = "Path Variable", required = true, example = "pathVariable1") @PathVariable String pathVariable) {
        this.logger.debug("pathVariable: " + pathVariable);
        return this.diagnosticsService.examplePostRequestWithPathVariable();
    }

    @ResponseBody
    @GetMapping(value = "/test-get-request-with-request-body", produces = "application/json")
    @Operation(summary = "examplePostRequestWithRequestBody", tags = {"Diagnostics"}, description = "POST Request with Request Body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "404", description = "Endpoint Not Found"),
    })
    public KeyValueString examplePostRequestWithRequestBody(@Parameter(description = "Path Variable", required = true, example = "pathVariable1") @RequestBody KeyValueString requestBody) {
        this.logger.debug("key: " + requestBody.getKey());
        this.logger.debug("value: " + requestBody.getValue());
        return this.diagnosticsService.examplePostRequestWithRequestBody();
    }

}
