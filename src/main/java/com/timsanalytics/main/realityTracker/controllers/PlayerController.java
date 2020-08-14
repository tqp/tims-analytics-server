package com.timsanalytics.main.realityTracker.controllers;

import com.timsanalytics.main.realityTracker.beans.Player;
import com.timsanalytics.main.realityTracker.services.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/reality-tracker/api/v1/player")
@Tag(name = "Player", description = "Player")
public class PlayerController {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Player List by Season GUID", tags = {"Player"}, description = "Get Player List by Season GUID", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Player>> getPlayerListBySeasonGuid(@RequestParam(value = "season-guid") String seasonGuid) {
        try {
            return ResponseEntity.ok()
                    .body(this.playerService.getPlayerListBySeasonGuid(seasonGuid));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
