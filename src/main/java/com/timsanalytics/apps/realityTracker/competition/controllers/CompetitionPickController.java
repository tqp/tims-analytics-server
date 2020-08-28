package com.timsanalytics.apps.realityTracker.competition.controllers;

import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionPick;
import com.timsanalytics.apps.realityTracker.competition.services.CompetitionPickService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/reality-tracker/api/v1/competition/picks")
@Tag(name = "Reality-Tracker Comp: Pick Controller", description = "Pick Endpoints")
public class CompetitionPickController {
    private final CompetitionPickService pickService;

    @Autowired
    public CompetitionPickController(CompetitionPickService pickService) {
        this.pickService = pickService;
    }

    @GetMapping(value = "/{a_teamKey}/{b_userKey}/{c_roundNumber}", produces = "application/json")
    @Operation(summary = "getUserPicksByRound", description = "Get Result List", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<CompetitionPick>> getUserPicksByRound(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("a_teamKey") String a_teamKey,
                                                                     @Parameter(description = "User Key", example = "key_user1") @PathVariable("b_userKey") String b_userKey,
                                                                     @Parameter(description = "Round Number", example = "2") @PathVariable("c_roundNumber") Integer c_roundNumber) {
        try {
            return ResponseEntity.ok().body(this.pickService.getUserPicksByRound(a_teamKey, b_userKey, c_roundNumber));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(value = "/in-round-cutoff/{a_pickPosition}/{b_roundNumber}", produces = "application/json")
    @Operation(summary = "isPickPositionWithinRoundCutoffThreshold", description = "Is Pick Position Within Round Cutoff Threshold", security = @SecurityRequirement(name = "bearerAuth"))
    public Boolean isPickPositionWithinRoundCutoffThreshold(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("a_pickPosition") Integer a_pickPosition,
                                                            @Parameter(description = "Team Key", example = "key_team1") @PathVariable("b_roundNumber") Integer b_roundNumber) {
        return this.pickService.isPickPositionValid(a_pickPosition, b_roundNumber);
    }
}
