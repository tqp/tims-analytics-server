package com.timsanalytics.main.controllers;

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

    @RequestMapping(value = "/fuel-info", method = RequestMethod.GET)
    @Operation(summary = "Get Fuel Info", tags = {"Fuel Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public List<KeyValueLong> getFuelInfo() {
        try {
            return fuelTrackerService.getFuelInfo();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
