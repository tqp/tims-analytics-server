package com.timsanalytics.apps.realityTracker.competition.controllers;

import com.timsanalytics.apps.realityTracker.competition.beans.Player;
import com.timsanalytics.apps.realityTracker.competition.services.CompetitionPlayerService;
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
@RequestMapping("/reality-tracker/api/v1/competition/user")
@Tag(name = "Reality-Tracker Comp: User Controller", description = "User Endpoints")
public class CompetitionPlayerController {
    private final CompetitionPlayerService competitionPlayerService;

    @Autowired
    public CompetitionPlayerController(CompetitionPlayerService competitionPlayerService) {
        this.competitionPlayerService = competitionPlayerService;
    }

    @GetMapping(value = "/", produces = "application/json")
    @Operation(summary = "getUserList", description = "Get User List")
    public List<Player> getUserList() {
        return this.competitionPlayerService.getUserList();
    }

    @GetMapping(value = "/{userKey}", produces = "application/json")
    @Operation(summary = "getUserListByTeamKey", description = "Get User List by User Key")
    public Player getUserByUserKey(@Parameter(description = "User Key", example = "key_user1") @PathVariable("userKey") String userKey) {
        return this.competitionPlayerService.getUserByUserKey(userKey);
    }

    @GetMapping(value = "/{teamKey}", produces = "application/json")
    @Operation(summary = "getUserListByTeamKey", description = "Get User List by Team Key")
    public List<Player> getUserListByTeamKey(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("teamKey") String teamKey) {
        return this.competitionPlayerService.getUserListByTeamKey(teamKey);
    }
}
