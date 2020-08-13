package com.timsanalytics.main.realityTracker.competition.controllers;

import com.timsanalytics.main.realityTracker.competition.beans.Team;
import com.timsanalytics.main.realityTracker.competition.services.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(value = "/", produces = "application/json")
    @Operation(summary = "getTeamList", description = "Get Team List")
    public List<Team> getTeamList() {
        return this.teamService.getTeamList();
    }

    @GetMapping(value = "/{teamKey}", produces = "application/json")
    @Operation(summary = "getTeamByTeamKey", description = "Get Team List by Team Key")
    public Team getTeamByTeamKey(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("teamKey") String teamKey) {
        return this.teamService.getTeamByTeamKey(teamKey);
    }
}
