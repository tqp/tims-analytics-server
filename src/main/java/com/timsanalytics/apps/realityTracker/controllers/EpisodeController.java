package com.timsanalytics.apps.realityTracker.controllers;

import com.timsanalytics.apps.realityTracker.beans.Episode;
import com.timsanalytics.apps.realityTracker.beans.Player;
import com.timsanalytics.apps.realityTracker.beans.Season;
import com.timsanalytics.apps.realityTracker.services.EpisodeService;
import com.timsanalytics.apps.realityTracker.services.PlayerService;
import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.utils.PrintObjectService;
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

import java.util.List;

@RestController
@RequestMapping("/reality-tracker/api/v1/episode")
@Tag(name = "Episode", description = "Episode")
public class EpisodeController {
    private final EpisodeService episodeService;
    private final PrintObjectService printObjectService;

    @Autowired
    public EpisodeController(EpisodeService episodeService, PrintObjectService printObjectService) {
        this.episodeService = episodeService;
        this.printObjectService = printObjectService;
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Episode", tags = {"Episode"}, description = "Create Episode", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Episode> createEpisode(@RequestBody Episode episode) {
        try {
            return ResponseEntity.ok()
                    .body(episodeService.createEpisode(episode));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/filtered", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Episode List by Season GUID", tags = {"Episode"}, description = "Get Episode List by Season GUID", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Episode>> getPlayerListFiltered(@RequestParam(value = "season-guid", required = false) String seasonGuid) {
        try {
            return ResponseEntity.ok()
                    .body(this.episodeService.getEpisodeListFiltered(seasonGuid));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = "/{episodeGuid}", method = RequestMethod.GET)
    @Operation(summary = "Get Episode Detail", tags = {"Episode"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Episode> getEpisodeDetail(@Parameter(description = "Episode GUID", required = true) @PathVariable String episodeGuid) {
        try {
            Episode episode = episodeService.getEpisodeDetail(episodeGuid);
            return ResponseEntity.ok()
                    .body(episode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Episode", tags = {"Episode"}, description = "Update Episode", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Episode> updateEpisode(@RequestBody Episode episode) {
        try {
            return ResponseEntity.ok()
                    .body(episodeService.updateEpisode(episode));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{episodeGuid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete Episode", tags = {"Episode"}, description = "Delete Episode", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<KeyValue> deleteEpisode(@Parameter(description = "Episode GUID", required = true) @PathVariable String episodeGuid) {
        try {
            return ResponseEntity.ok()
                    .body(episodeService.deleteEpisode(episodeGuid));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
