package com.timsanalytics.main.realityTracker.controllers;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.main.realityTracker.beans.Series;
import com.timsanalytics.main.realityTracker.beans.ServerSidePaginationResponse;
import com.timsanalytics.main.realityTracker.services.SeriesService;
import com.timsanalytics.main.thisApp.beans.ServerSidePaginationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@RestController
@RequestMapping("/reality-tracker/api/v1/series")
@Tag(name = "Series", description = "Series")
public class SeriesController {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final SeriesService seriesService;

    @Autowired
    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Series", tags = {"Series"}, description = "Create Series", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Series> createSeries(@RequestBody Series series) {
        try {
            return ResponseEntity.ok()
                    .body(seriesService.createSeries(series));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/ssp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Series List (SSP)", tags = {"Series"}, description = "Get Series List (SSP)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ServerSidePaginationResponse<Series>> getSeriesList_SSP(@RequestBody ServerSidePaginationRequest serverSidePaginationRequest) {
        long startTime = new Date().getTime();
        try {
            ServerSidePaginationResponse<Series> container = this.seriesService.getSeriesList_SSP(serverSidePaginationRequest);
            container.setRequestTime(new Date().getTime() - startTime);
            return ResponseEntity.ok()
                    .body(container);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = "/{seriesGuid}", method = RequestMethod.GET)
    @Operation(summary = "Get Series Detail", tags = {"Series"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Series> getContestantDetail(@Parameter(description = "Contestant GUID", required = true) @PathVariable String seriesGuid) {
        try {
            Series series = seriesService.getSeriesDetail(seriesGuid);
            return ResponseEntity.ok()
                    .body(series);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Series", tags = {"Series"}, description = "Update Series", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Series> updateSeries(@RequestBody Series series) {
        try {
            return ResponseEntity.ok()
                    .body(seriesService.updateSeries(series));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{seriesGuid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete Series", tags = {"Series"}, description = "Delete Series", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<KeyValue> deleteSeries(@Parameter(description = "Series GUID", required = true) @PathVariable String seriesGuid) {
        try {
            return ResponseEntity.ok()
                    .body(seriesService.deleteSeries(seriesGuid));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
