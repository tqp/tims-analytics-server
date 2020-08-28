package com.timsanalytics.apps.realityTracker.competition.controllers;

import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionResult;
import com.timsanalytics.apps.realityTracker.competition.services.CompetitionResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reality-tracker/api/v1/competition/results")
@Tag(name = "Reality-Tracker Comp: Result Controller", description = "Result Endpoints")
public class CompetitionResultController {
    private final CompetitionResultService resultService;

    @Autowired
    public CompetitionResultController(CompetitionResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping(value = "/round", produces = "application/json")
    @Operation(summary = "getResultList", description = "Get Result List", security = @SecurityRequirement(name = "bearerAuth"))
    public List<CompetitionResult> getResultList() {
        return this.resultService.getResultList();
    }

    @GetMapping(value = "/round/{roundNumber}", produces = "application/json")
    @Operation(summary = "getResultListByRound", description = "Get Result List by Round", security = @SecurityRequirement(name = "bearerAuth"))
    public List<CompetitionResult> getResultListByRound(@Parameter(description = "Round Number", example = "1") @PathVariable("roundNumber") Integer roundNumber) {
        return this.resultService.getResultListByRound(roundNumber);
    }

    @GetMapping(value = "/remaining-contestants/{roundNumber}", produces = "application/json")
    @Operation(summary = "getRemainingContestantsByRound", description = "Get Remaining Contestants by Round", security = @SecurityRequirement(name = "bearerAuth"))
    public List<CompetitionResult> getRemainingContestantsByRound(@Parameter(description = "Round Number", example = "1") @PathVariable("roundNumber") Integer roundNumber) {
        return this.resultService.getRemainingContestantsByRound(roundNumber);
    }
}
