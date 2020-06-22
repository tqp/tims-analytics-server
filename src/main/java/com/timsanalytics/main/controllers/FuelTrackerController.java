package com.timsanalytics.main.controllers;

import com.timsanalytics.main.beans.KeyValueDouble;
import com.timsanalytics.main.beans.KeyValueLong;
import com.timsanalytics.main.services.FuelTrackerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fuel-tracker")
@Tag(name = "Fuel Tracker", description = "Fuel Tracker")
public class FuelTrackerController {
    private final FuelTrackerService fuelTrackerService;

    @Autowired
    public FuelTrackerController(FuelTrackerService fuelTrackerService) {
        this.fuelTrackerService = fuelTrackerService;
    }

    @RequestMapping(value = "/longest-distance", method = RequestMethod.GET)
    @Operation(summary = "Get Longest Single-Tank Distance", tags = {"Fuel Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public Double getLongestDistance() {
        try {
            return fuelTrackerService.getLongestDistance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/odometer", method = RequestMethod.GET)
    @Operation(summary = "Get Odometer Data", tags = {"Fuel Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public List<KeyValueDouble> getOdometerData() {
        try {
            return fuelTrackerService.getOdometerData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/mpg", method = RequestMethod.GET)
    @Operation(summary = "Get Miles Per Gallon Data", tags = {"Fuel Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public List<KeyValueDouble> getMpgData() {
        try {
            return fuelTrackerService.getMpgData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
