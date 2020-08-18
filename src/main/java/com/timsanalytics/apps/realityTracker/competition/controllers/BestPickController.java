package com.timsanalytics.apps.realityTracker.competition.controllers;

import com.timsanalytics.apps.realityTracker.competition.beans.BestPick;
import com.timsanalytics.apps.realityTracker.competition.services.BestPickService;
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
@RequestMapping("/reality-tracker/api/v1/competition/best-pick")
@Tag(name = "Reality-Tracker Comp: Best Pick Controller", description = "Best Pick Endpoints")
public class BestPickController {
    private final BestPickService bestPickService;

    @Autowired
    public BestPickController(BestPickService bestPickService) {
        this.bestPickService = bestPickService;
    }

    @GetMapping(value = "/{a_teamKey}/{b_userKey}/{c_roundNumber}", produces = "application/json")
    @Operation(summary = "getBestPicksByTeamUserRound", description = "Get Best Picks by Team, User, Round")
    public List<BestPick> getBestPicksByTeamUserRound(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("a_teamKey") String a_teamKey,
                                                      @Parameter(description = "User Key", example = "key_user1") @PathVariable("b_userKey") String b_userKey,
                                                      @Parameter(description = "Round Number", example = "1") @PathVariable("c_roundNumber") Integer c_roundNumber) {
        return this.bestPickService.getBestPicksByTeamUserRound(a_teamKey, b_userKey, c_roundNumber);
    }
}
