package com.timsanalytics.main.realityTracker.competition.controllers;

import com.timsanalytics.main.realityTracker.competition.beans.Player;
import com.timsanalytics.main.realityTracker.competition.services.PlayerService;
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
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value = "/", produces = "application/json")
    @Operation(summary = "getUserList", description = "Get User List")
    public List<Player> getUserList() {
        return this.playerService.getUserList();
    }

    @GetMapping(value = "/{userKey}", produces = "application/json")
    @Operation(summary = "getUserListByTeamKey", description = "Get User List by User Key")
    public Player getUserByUserKey(@Parameter(description = "User Key", example = "key_user1") @PathVariable("userKey") String userKey) {
        return this.playerService.getUserByUserKey(userKey);
    }

    @GetMapping(value = "/{teamKey}", produces = "application/json")
    @Operation(summary = "getUserListByTeamKey", description = "Get User List by Team Key")
    public List<Player> getUserListByTeamKey(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("teamKey") String teamKey) {
        return this.playerService.getUserListByTeamKey(teamKey);
    }
}
