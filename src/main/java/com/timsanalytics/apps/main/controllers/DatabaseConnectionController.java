package com.timsanalytics.apps.main.controllers;

import com.timsanalytics.apps.main.services.DatabaseConnectionService;
import com.timsanalytics.common.beans.KeyValueString;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/basic-database-connection")
@Tag(name = "Basic Test Pages", description = "Basic Test Pages")
public class DatabaseConnectionController {
    private final DatabaseConnectionService databaseConnectionService;

    @Autowired
    public DatabaseConnectionController(DatabaseConnectionService databaseConnectionService) {
        this.databaseConnectionService = databaseConnectionService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @Operation(summary = "Get Database Response", tags = {"Basic Test Pages"}, security = @SecurityRequirement(name = "bearerAuth"))
    public KeyValueString getDatabaseResponse() {
        try {
            return databaseConnectionService.getDatabaseResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
