package com.timsanalytics.apps.main.controllers;

import com.timsanalytics.apps.main.beans.FuelActivity;
import com.timsanalytics.apps.main.services.AutoTrackerService;
import com.timsanalytics.common.beans.KeyValue;
import com.timsanalytics.common.beans.KeyValueDouble;
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
@RequestMapping("/api/v1/auto-tracker-two")
@Tag(name = "Auto Tracker", description = "Auto Tracker")
public class AutoTrackerController {

    private final AutoTrackerService autoTrackerService;

    @Autowired
    public AutoTrackerController(AutoTrackerService autoTrackerService) {
        this.autoTrackerService = autoTrackerService;
    }

    // Fuel Activity

    @ResponseBody
    @RequestMapping(value = "/fuel-activity/ssp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Fuel Activity List (SSP)", tags = {"Auto Tracker"}, description = "Get Fuel Activity List (SSP)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ServerSidePaginationResponse<FuelActivity>> getFuelActivityList_SSP(@RequestBody ServerSidePaginationRequest serverSidePaginationRequest) {
        long startTime = new Date().getTime();
        try {
            ServerSidePaginationResponse<FuelActivity> container = this.autoTrackerService.getFuelActivityList_SSP(serverSidePaginationRequest);
            container.setRequestTime(new Date().getTime() - startTime);
            return ResponseEntity.ok()
                    .body(container);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = "/fuel-activity/{fuelActivityGuid}", method = RequestMethod.GET)
    @Operation(summary = "Get Fuel Activity Detail", tags = {"Auto Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<FuelActivity> getFuelActivityDetail(@Parameter(description = "Contestant GUID", required = true) @PathVariable String fuelActivityGuid) {
        try {
            FuelActivity fuelActivity = autoTrackerService.getFuelActivityDetail(fuelActivityGuid);
            return ResponseEntity.ok()
                    .body(fuelActivity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Dashboard

    @RequestMapping(value = "/dashboard/longest-time-between-fills", method = RequestMethod.GET)
    @Operation(summary = "Get Time Between Fills", tags = {"Auto Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public KeyValueDouble getLongestTimeBetweenFills() {
        try {
            return autoTrackerService.getLongestTimeBetweenFills();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/dashboard/longest-distance-between-fills", method = RequestMethod.GET)
    @Operation(summary = "Get Longest Single-Tank Distance", tags = {"Auto Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public KeyValueDouble getLongestDistanceBetweenFills() {
        try {
            return autoTrackerService.getLongestDistanceBetweenFills();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/dashboard/estimated-1k-date", method = RequestMethod.GET)
    @Operation(summary = "Get Estimated 1k Date", tags = {"Auto Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public KeyValue getEstimated1kDate() {
        try {
            return autoTrackerService.getEstimated1kDate();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/dashboard/odometer", method = RequestMethod.GET)
    @Operation(summary = "Get Odometer Data", tags = {"Auto Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public List<KeyValueDouble> getOdometerData() {
        try {
            return autoTrackerService.getOdometerData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/dashboard/mpg", method = RequestMethod.GET)
    @Operation(summary = "Get Miles Per Gallon Data", tags = {"Auto Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public List<KeyValueDouble> getMpgData() {
        try {
            return autoTrackerService.getMpgData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
