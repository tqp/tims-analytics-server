package com.timsanalytics.apps.autoTracker.controllers;

import com.timsanalytics.apps.autoTracker.beans.Fill;
import com.timsanalytics.apps.autoTracker.beans.FuelActivity;
import com.timsanalytics.apps.autoTracker.services.FuelActivityService;
import com.timsanalytics.common.beans.KeyValue;
import com.timsanalytics.common.beans.ServerSidePaginationRequest;
import com.timsanalytics.common.beans.ServerSidePaginationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auto-tracker/fuel-activity")
@Tag(name = "Auto Tracker: Fuel Activity", description = "Auto Tracker: Fuel Activity")
public class FuelActivityController {
    private final FuelActivityService fuelActivityService;

    @Autowired
    public FuelActivityController(FuelActivityService fuelActivityService) {
        this.fuelActivityService = fuelActivityService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create Fuel Activity", tags = {"Auto Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public Fill createFuelActivity(@RequestBody Fill fill) {
        try {
            return fuelActivityService.createFuelActivity(fill);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Fuel Activity List (All)", tags = {"Auto Tracker"}, description = "Get Fuel Activity List (All)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<FuelActivity>> getFuelActivityList() {
        try {
            return ResponseEntity.ok()
                    .body(this.fuelActivityService.getFuelActivityList());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/ssp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Fuel Activity List (SSP)", tags = {"Auto Tracker"}, description = "Get Fuel Activity List (SSP)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ServerSidePaginationResponse<FuelActivity>> getFuelActivityList_SSP(@RequestBody ServerSidePaginationRequest serverSidePaginationRequest) {
        long startTime = new Date().getTime();
        try {
            ServerSidePaginationResponse<FuelActivity> container = this.fuelActivityService.getFuelActivityList_SSP(serverSidePaginationRequest);
            container.setRequestTime(new Date().getTime() - startTime);
            return ResponseEntity.ok()
                    .body(container);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = "/{fuelActivityGuid}", method = RequestMethod.GET)
    @Operation(summary = "Get Fuel Activity Detail", tags = {"Auto Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<FuelActivity> getFuelActivityDetail(@Parameter(description = "Fuel Activity GUID", required = true) @PathVariable String fuelActivityGuid) {
        try {
            FuelActivity fuelActivity = fuelActivityService.getFuelActivityDetail(fuelActivityGuid);
            return ResponseEntity.ok()
                    .body(fuelActivity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Fuel Activity", tags = {"Auto Tracker"}, description = "Update Fuel Activity", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Fill> updateFuelActivity(@RequestBody Fill fill) {
        try {
            return ResponseEntity.ok()
                    .body(fuelActivityService.updateFuelActivity(fill));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "{fuelActivityGuid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete Fuel Activity", tags = {"Auto Tracker"}, description = "Delete Fuel Activity", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<KeyValue> deleteFuelActivity(@Parameter(description = "Fuel Activity GUID", required = true) @PathVariable String fuelActivityGuid) {
        try {
            return ResponseEntity.ok()
                    .body(fuelActivityService.deleteFuelActivity(fuelActivityGuid));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
