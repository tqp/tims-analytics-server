package com.timsanalytics.main.realityTracker.controllers;

import com.timsanalytics.main.realityTracker.beans.Player;
import com.timsanalytics.main.realityTracker.services.PlayerService;
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
@RequestMapping("/reality-tracker/api/v1/player")
@Tag(name = "Player", description = "Player")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @RequestMapping(value = "/{playerGuid}", method = RequestMethod.GET)
    @Operation(summary = "Get Player Detail", tags = {"Player"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Player> getPlayerDetail(@Parameter(description = "Player GUID", required = true) @PathVariable String playerGuid) {
        try {
            Player player = playerService.getPlayerDetail(playerGuid);
            return ResponseEntity.ok()
                    .body(player);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/filtered", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Player List by Season GUID", tags = {"Player"}, description = "Get Player List by Season GUID", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Player>> getPlayerListFiltered(@RequestParam(value = "season-guid", required = false) String seasonGuid,
                                                               @RequestParam(value = "contestant-guid", required = false) String contestantGuid) {
        try {
            return ResponseEntity.ok()
                    .body(this.playerService.getPlayerListFiltered(seasonGuid, contestantGuid));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

//    // Contestant-Player List
//    @ResponseBody
//    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Get Contestant-Season List", tags = {"Season"}, description = "Get Series-Season List", security = @SecurityRequirement(name = "bearerAuth"))
//    public ResponseEntity<List<Player>> getSeasonListByContestantGuid(@RequestParam(value = "contestant-guid") String contestantGuid) {
//        try {
//            return ResponseEntity.ok()
//                    .body(this.playerService.getPlayerListByContestantGuid(contestantGuid));
//        } catch (IllegalArgumentException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//        }
//    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Player", tags = {"Player"}, description = "Update Player", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        try {
            return ResponseEntity.ok()
                    .body(playerService.updatePlayer(player));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
