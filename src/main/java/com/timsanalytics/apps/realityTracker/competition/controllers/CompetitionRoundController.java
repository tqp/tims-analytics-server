package com.timsanalytics.apps.realityTracker.competition.controllers;

import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionRound;
import com.timsanalytics.apps.realityTracker.competition.services.CompetitionRoundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reality-tracker/api/v1/competition/rounds")
@Tag(name = "Reality-Tracker Comp: Round Controller", description = "Round Endpoints")
public class CompetitionRoundController {
    private final CompetitionRoundService roundService;

    @Autowired
    public CompetitionRoundController(CompetitionRoundService roundService) {
        this.roundService = roundService;
    }

    @GetMapping(value = "/{roundNumber}", produces = "application/json")
    @Operation(summary = "getRoundByRoundNumber", description = "Get Round by Round Number", security = @SecurityRequirement(name = "bearerAuth"))
    public CompetitionRound getRoundByRoundNumber(@Parameter(description = "Round Number", example = "1") @PathVariable("roundNumber") Integer roundNumber) {
        return this.roundService.getRoundByRoundNumber(roundNumber);
    }

    @GetMapping(value = "/last-played", produces = "application/json")
    @Operation(summary = "getLastPlayedRound", description = "Get Last Played Round", security = @SecurityRequirement(name = "bearerAuth"))
    public Integer getLastPlayedRound() {
        return this.roundService.getLastPlayedRound();
    }

    @GetMapping(value = "/final", produces = "application/json")
    @Operation(summary = "getFinalRound", description = "Get Final Round", security = @SecurityRequirement(name = "bearerAuth"))
    public Integer getFinalRound() {
        return this.roundService.getFinalRound();
    }

    @GetMapping(value = "/{roundNumber}/valid", produces = "application/json")
    @Operation(summary = "isRoundNumberValid", description = "Is Round Number Valid", security = @SecurityRequirement(name = "bearerAuth"))
    public Boolean isRoundNumberValid(@Parameter(description = "Round Number", example = "1") @PathVariable("roundNumber") Integer roundNumber) {
        return this.roundService.isRoundNumberValid(roundNumber);
    }
}
