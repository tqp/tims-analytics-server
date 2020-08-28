package com.timsanalytics.apps.realityTracker.competition.controllers;

import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionContestant;
import com.timsanalytics.apps.realityTracker.competition.services.CompetitionContestantService;
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
@RequestMapping("/reality-tracker/api/v1/competition/contestants")
@Tag(name = "Reality-Tracker Comp: ContestantController", description = "Contestant Endpoints")
public class CompetitionContestantController {
    private final CompetitionContestantService competitionContestantService;

    @Autowired
    public CompetitionContestantController(CompetitionContestantService competitionContestantService) {
        this.competitionContestantService = competitionContestantService;
    }

    @GetMapping(value = "/", produces = "application/json")
    @Operation(summary = "getContestantList", description = "Get Contestant List", security = @SecurityRequirement(name = "bearerAuth"))
    public List<CompetitionContestant> getContestantList() {
        return this.competitionContestantService.getContestantList();
    }

    @GetMapping(value = "/{contestantKey}", produces = "application/json")
    @Operation(summary = "getContestantListByContestantKey", description = "Get Contestant List by Contestant Key", security = @SecurityRequirement(name = "bearerAuth"))
    public CompetitionContestant getContestantListByContestantKey(@Parameter(description = "Contestant Key", example = "key_Madison") @PathVariable("contestantKey") String contestantKey) {
        return this.competitionContestantService.getContestantByContestantKey(contestantKey);
    }

}
