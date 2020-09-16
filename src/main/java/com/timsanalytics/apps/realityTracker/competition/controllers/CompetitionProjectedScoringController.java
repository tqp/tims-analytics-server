package com.timsanalytics.apps.realityTracker.competition.controllers;

import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionScore;
import com.timsanalytics.apps.realityTracker.competition.services.CompetitionProjectedScoringService;
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
@RequestMapping("/reality-tracker/api/v1/competition/projected-scoring")
@Tag(name = "Reality-Tracker Comp: Projected Scoring Controller", description = "Projected Scoring Endpoints")
public class CompetitionProjectedScoringController {
    private final CompetitionProjectedScoringService projectedScoringService;

    @Autowired
    public CompetitionProjectedScoringController(CompetitionProjectedScoringService projectedScoringService) {
        this.projectedScoringService = projectedScoringService;
    }

    // BY TEAM-USER-ROUND-PICK

    @GetMapping(value = "/current/{a_teamKey}/{b_userKey}/{c_roundNumber}/{d_pickPosition}", produces = "application/json")
    @Operation(summary = "getPickResultScore", description = "Get 'Pick Result' Score", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CompetitionScore> getPickResultScore(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("a_teamKey") String a_teamKey,
                                                               @Parameter(description = "User Key", example = "key_user1") @PathVariable("b_userKey") String b_userKey,
                                                               @Parameter(description = "Round Number", example = "4") @PathVariable("c_roundNumber") Integer c_roundNumber,
                                                               @Parameter(description = "Round Number", example = "7") @PathVariable("d_pickPosition") Integer d_pickPosition) {
        try {
            return ResponseEntity.ok().body(this.projectedScoringService.getPickResultScore(a_teamKey, b_userKey, c_roundNumber, d_pickPosition));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // BY TEAM-USER-ROUND

    @GetMapping(value = "/current/{a_teamKey}/{b_userKey}/{c_roundNumber}", produces = "application/json")
    @Operation(summary = "getPickResultScoreByTeamUserRound", description = "Get 'Pick Result' Score by Team, User, Round", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CompetitionScore> getPickResultScoreByTeamUserRound(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("a_teamKey") String a_teamKey,
                                                                              @Parameter(description = "User Key", example = "key_user1") @PathVariable("b_userKey") String b_userKey,
                                                                              @Parameter(description = "Round Number", example = "4") @PathVariable("c_roundNumber") Integer c_roundNumber) {
        try {
            return ResponseEntity.ok().body(this.projectedScoringService.getPickResultScoreByTeamUserRound(a_teamKey, b_userKey, c_roundNumber));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // BY TEAM-USER

    @GetMapping(value = "/current/{a_teamKey}/{b_userKey}", produces = "application/json")
    @Operation(summary = "getPickResultScoreByTeamUser", description = "Get 'Pick Result' Score by Team, User", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CompetitionScore> getPickResultScoreByTeamUser(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("a_teamKey") String a_teamKey,
                                                                         @Parameter(description = "User Key", example = "key_user1") @PathVariable("b_userKey") String b_userKey) {
        try {
            return ResponseEntity.ok().body(this.projectedScoringService.getPickResultScoreByTeamUser(a_teamKey, b_userKey));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(value = "/current/{a_teamKey}/{b_userKey}/group-by-round", produces = "application/json")
    @Operation(summary = "getPickResultScoreByTeamUserGroupByRound", description = "Get 'Pick Result' Score by Team, User, Grouped by Round", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<CompetitionScore>> getPickResultScoreByTeamUserGroupByRound(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("a_teamKey") String a_teamKey,
                                                                                           @Parameter(description = "User Key", example = "key_user1") @PathVariable("b_userKey") String b_userKey) {
        try {
            return ResponseEntity.ok().body(this.projectedScoringService.getPickResultScoreByTeamUserGroupByRound(a_teamKey, b_userKey));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // BY TEAM

    @GetMapping(value = "/current/{a_teamKey}/group-by-user", produces = "application/json")
    @Operation(summary = "getPickResultScoreByTeamGroupByUser", description = "Get 'Pick Result' Score by Team, User", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<CompetitionScore>> getPickResultScoreByTeamGroupByUser(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("a_teamKey") String a_teamKey) {
        try {
            return ResponseEntity.ok().body(this.projectedScoringService.getPickResultScoreByTeamGroupByUser(a_teamKey));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(value = "/current/{a_teamKey}/group-by-user-and-round", produces = "application/json")
    @Operation(summary = "getPickResultScoreByTeamUserGroupByUserRound", description = "Get 'Pick Result' Score by Team, User, Grouped by Round", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<CompetitionScore>> getPickResultScoreByTeamUserGroupByUserRound(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("a_teamKey") String a_teamKey) {
        try {
            return ResponseEntity.ok().body(this.projectedScoringService.getPickResultScoreByTeamGroupByUserRound(a_teamKey));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}