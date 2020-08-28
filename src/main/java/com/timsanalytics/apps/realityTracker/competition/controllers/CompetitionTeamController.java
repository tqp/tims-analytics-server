package com.timsanalytics.apps.realityTracker.competition.controllers;

import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionTeam;
import com.timsanalytics.apps.realityTracker.competition.services.CompetitionTeamService;
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
@RequestMapping("/reality-tracker/api/v1/competition/team")
@Tag(name = "Reality-Tracker Comp: Team Controller", description = "Team Endpoints")
public class CompetitionTeamController {
    private final CompetitionTeamService teamService;

    @Autowired
    public CompetitionTeamController(CompetitionTeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(value = "/", produces = "application/json")
    @Operation(summary = "getTeamList", description = "Get Team List", security = @SecurityRequirement(name = "bearerAuth"))
    public List<CompetitionTeam> getTeamList() {
        return this.teamService.getTeamList();
    }

    @GetMapping(value = "/{teamKey}", produces = "application/json")
    @Operation(summary = "getTeamByTeamKey", description = "Get Team List by Team Key", security = @SecurityRequirement(name = "bearerAuth"))
    public CompetitionTeam getTeamByTeamKey(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("teamKey") String teamKey) {
        return this.teamService.getTeamByTeamKey(teamKey);
    }
}
