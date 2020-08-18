package com.timsanalytics.apps.realityTracker.controllers;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.apps.realityTracker.beans.Season;
import com.timsanalytics.apps.realityTracker.services.SeasonService;
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

import java.util.List;

@RestController
@RequestMapping("/reality-tracker/api/v1/season")
@Tag(name = "Season", description = "Season")
public class SeasonController {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final SeasonService seasonService;

    @Autowired
    public SeasonController(SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Season", tags = {"Season"}, description = "Create Season", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Season> createSeason(@RequestBody Season season) {
        try {
            return ResponseEntity.ok()
                    .body(seasonService.createSeason(season));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Series-Season List
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Series-Season List", tags = {"Season"}, description = "Get Series-Season List", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Season>> getSeasonListBySeriesGuid(@RequestParam(value = "series-guid") String seriesGuid) {
        try {
            return ResponseEntity.ok()
                    .body(this.seasonService.getSeasonListBySeriesGuid(seriesGuid));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = "/{seasonGuid}", method = RequestMethod.GET)
    @Operation(summary = "Get Season Detail", tags = {"Season"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Season> getContestantDetail(@Parameter(description = "Season GUID", required = true) @PathVariable String seasonGuid) {
        try {
            Season season = seasonService.getSeasonDetail(seasonGuid);
            return ResponseEntity.ok()
                    .body(season);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Season", tags = {"Season"}, description = "Update Season", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Season> updateSeason(@RequestBody Season season) {
        try {
            return ResponseEntity.ok()
                    .body(seasonService.updateSeason(season));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{seasonGuid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete Season", tags = {"Season"}, description = "Delete Season", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<KeyValue> deleteSeason(@Parameter(description = "Season GUID", required = true) @PathVariable String seasonGuid) {
        try {
            return ResponseEntity.ok()
                    .body(seasonService.deleteSeason(seasonGuid));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
