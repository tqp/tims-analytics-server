package com.timsanalytics.apps.autoTracker.controllers;

import com.timsanalytics.apps.autoTracker.beans.Station;
import com.timsanalytics.apps.autoTracker.services.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auto-tracker/station")
@Tag(name = "Auto Tracker", description = "Auto Tracker")
public class StationController {
    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    // AUTO-COMPLETE
    @ResponseBody
    @RequestMapping(value = "/auto-complete/station-name", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Auto-Complete Fuel Station Name", tags = {"Auto Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Station>> getAutoCompleteStationName(@RequestParam(value = "filter") String filter) {
        try {
            return ResponseEntity.ok()
                    .body(this.stationService.getAutoCompleteStationName(filter));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
