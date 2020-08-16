package com.timsanalytics.main.realityTracker.controllers;

import com.timsanalytics.main.realityTracker.beans.ListItem;
import com.timsanalytics.main.realityTracker.beans.Player;
import com.timsanalytics.main.realityTracker.services.PlayerService;
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
@RequestMapping("/reality-tracker/api/v1/player")
@Tag(name = "Player", description = "Player")
public class PlayerController {
    private final PlayerService playerService;
    private final PrintObjectService printObjectService;

    @Autowired
    public PlayerController(PlayerService playerService, PrintObjectService printObjectService) {
        this.playerService = playerService;
        this.printObjectService = printObjectService;
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

    // Contestant-Season Add/Remove

    @ResponseBody
    @RequestMapping(value = "/current-seasons/contestant/{contestantGuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Current Seasons By Contestant GUID", tags = {"Player"}, description = "Get Current Seasons By Contestant GUID", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Player>> getCurrentSeasonsByContestantGuid(@PathVariable String contestantGuid) {
        try {
            List<Player> personList = this.playerService.getCurrentSeasonsByContestantGuid(contestantGuid);
            return ResponseEntity.ok()
                    .body(personList);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/available-seasons/contestant/{contestantGuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Available Seasons By Contestant GUID", tags = {"Player"}, description = "Get Available Seasons By Contestant GUID", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Player>> getAvailableSeasonsByContestantGuid(@PathVariable String contestantGuid) {
        try {
            List<Player> personList = this.playerService.getAvailableSeasonsByContestantGuid(contestantGuid);
            return ResponseEntity.ok()
                    .body(personList);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/add-seasons/contestant/{contestantGuid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add Seasons to Contestant", tags = {"Player"}, description = "Add Seasons to Contestant", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<ListItem>> addContestantsToSeason(@PathVariable String contestantGuid,
                                                                 @RequestBody List<ListItem> itemsToAdd) {
        try {
            this.playerService.addContestantsToSeason(contestantGuid, itemsToAdd);
            return ResponseEntity.ok()
                    .body(itemsToAdd);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/remove-seasons/contestant/{contestantGuid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Remove Seasons from Contestant", tags = {"Player"}, description = "Remove Seasons from Contestant", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<ListItem>> removeContestantsFromSeason(@PathVariable String contestantGuid,
                                                                      @RequestBody List<ListItem> itemsToRemove) {
        try {
            this.playerService.removeContestantsFromSeason(contestantGuid, itemsToRemove);
            return ResponseEntity.ok()
                    .body(itemsToRemove);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
