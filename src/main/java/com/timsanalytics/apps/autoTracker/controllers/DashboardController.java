package com.timsanalytics.apps.autoTracker.controllers;

import com.timsanalytics.apps.autoTracker.services.DashboardService;
import com.timsanalytics.common.beans.KeyValue;
import com.timsanalytics.common.beans.KeyValueDouble;
import com.timsanalytics.common.beans.KeyValueLong;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auto-tracker/dashboard")
@Tag(name = "Auto Tracker: Dashboard", description = "Auto Tracker: Dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @RequestMapping(value = "/longest-time-between-fills", method = RequestMethod.GET)
    @Operation(summary = "Get Time Between Fills", tags = {"Auto Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public KeyValueLong getLongestTimeBetweenFills() {
        return dashboardService.getLongestTimeBetweenFills();
    }

    @RequestMapping(value = "/longest-distance-between-fills", method = RequestMethod.GET)
    @Operation(summary = "Get Longest Single-Tank Distance", tags = {"Auto Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public KeyValueDouble getLongestDistanceBetweenFills() {
        try {
            return dashboardService.getLongestDistanceBetweenFills();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/estimated-1k-date", method = RequestMethod.GET)
    @Operation(summary = "Get Estimated 1k Date", tags = {"Auto Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public KeyValue getEstimated1kDate() {
        try {
            return dashboardService.getEstimated1kDate();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/odometer", method = RequestMethod.GET)
    @Operation(summary = "Get Odometer Data", tags = {"Auto Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public List<KeyValueDouble> getOdometerData() {
        try {
            return dashboardService.getOdometerData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/mpg", method = RequestMethod.GET)
    @Operation(summary = "Get Miles Per Gallon Data", tags = {"Auto Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public List<KeyValueDouble> getMpgData() {
        try {
            return dashboardService.getMpgData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
