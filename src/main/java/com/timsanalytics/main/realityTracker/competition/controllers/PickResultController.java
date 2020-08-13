package com.timsanalytics.main.realityTracker.competition.controllers;

import com.timsanalytics.main.realityTracker.competition.beans.PickResult;
import com.timsanalytics.main.realityTracker.competition.services.PickResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/reality-tracker/api/v1/competition/pick-result")
@Tag(name = "Reality-Tracker Comp: Pick Results Controller", description = "Pick Results Endpoints")
public class PickResultController {
    private final PickResultService pickResultService;

    @Autowired
    public PickResultController(PickResultService pickResultService) {
        this.pickResultService = pickResultService;
    }

    // BY PICK

    @GetMapping(value = "/picked/{a_teamKey}/{b_userKey}/{c_roundNumber}/{d_pickPosition}", produces = "application/json")
    @Operation(summary = "getPickResult", description = "Get Pick Result")
    public ResponseEntity<PickResult> getPickResult(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("a_teamKey") String a_teamKey,
                                                    @Parameter(description = "User Key", example = "key_user1") @PathVariable("b_userKey") String b_userKey,
                                                    @Parameter(description = "Round Number", example = "4") @PathVariable("c_roundNumber") Integer c_roundNumber,
                                                    @Parameter(description = "Pick Position Number", example = "7") @PathVariable("d_pickPosition") Integer d_pickPosition) {
        try {
            return ResponseEntity.ok().body(this.pickResultService.getPickResult(a_teamKey, b_userKey, c_roundNumber, d_pickPosition));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // BY ROUND

    @GetMapping(value = "/all/{a_teamKey}/{b_userKey}/{c_roundNumber}", produces = "application/json")
    @Operation(summary = "getAllResultsByRound", description = "Get all Results by Round")
    public ResponseEntity<List<PickResult>> getAllResultsByRound(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("a_teamKey") String a_teamKey,
                                                                 @Parameter(description = "User Key", example = "key_user1") @PathVariable("b_userKey") String b_userKey,
                                                                 @Parameter(description = "Round Number", example = "2") @PathVariable("c_roundNumber") Integer c_roundNumber) {
        try {
            return ResponseEntity.ok().body(this.pickResultService.getAllResultByRound(a_teamKey, b_userKey, c_roundNumber));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(value = "/picked/{a_teamKey}/{b_userKey}/{c_roundNumber}", produces = "application/json")
    @Operation(summary = "getPickResultsByRound", description = "Get 'Pick Results' By Round")
    public ResponseEntity<List<PickResult>> getPickResultsByRound(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("a_teamKey") String a_teamKey,
                                                                  @Parameter(description = "User Key", example = "key_user1") @PathVariable("b_userKey") String b_userKey,
                                                                  @Parameter(description = "Round Number", example = "2") @PathVariable("c_roundNumber") Integer c_roundNumber) {
        try {
            return ResponseEntity.ok().body(this.pickResultService.getPickResultsByRound(a_teamKey, b_userKey, c_roundNumber));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(value = "/not-picked/{a_teamKey}/{b_userKey}/{c_roundNumber}", produces = "application/json")
    @Operation(summary = "getNotPickedResultsByRound", description = "Get Not Picked By Round")
    public ResponseEntity<List<PickResult>> getNotPickedResultsByRound(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("a_teamKey") String a_teamKey,
                                                                       @Parameter(description = "User Key", example = "key_user1") @PathVariable("b_userKey") String b_userKey,
                                                                       @Parameter(description = "Round Number", example = "2") @PathVariable("c_roundNumber") Integer c_roundNumber) {
        try {
            return ResponseEntity.ok().body(this.pickResultService.getNotPickedResultsByRound(a_teamKey, b_userKey, c_roundNumber));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // BY TEAM/USER

    @GetMapping(value = "/all/{a_teamKey}/{b_userKey}", produces = "application/json")
    @Operation(summary = "getAllResultsByUser", description = "Get Picks Summary By Round")
    public ResponseEntity<List<PickResult>> getAllResultsByUser(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("a_teamKey") String a_teamKey,
                                                                @Parameter(description = "User Key", example = "key_user1") @PathVariable("b_userKey") String b_userKey) {
        try {
            return ResponseEntity.ok().body(this.pickResultService.getAllResultsByUser(a_teamKey, b_userKey));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(value = "/picked/{a_teamKey}/{b_userKey}", produces = "application/json")
    @Operation(summary = "getPickResultsByUser", description = "Get Picks Summary By Round")
    public ResponseEntity<List<PickResult>> getPickResultsByUser(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("a_teamKey") String a_teamKey,
                                                                 @Parameter(description = "User Key", example = "key_user1") @PathVariable("b_userKey") String b_userKey) {
        try {
            return ResponseEntity.ok().body(this.pickResultService.getPickResultsByTeamUser(a_teamKey, b_userKey));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(value = "/not-picked/{a_teamKey}/{b_userKey}", produces = "application/json")
    @Operation(summary = "getNotPickedResultsByUser", description = "Get Picks Summary By Round")
    public ResponseEntity<List<PickResult>> getNotPickedResultsByUser(@Parameter(description = "Team Key", example = "key_team1") @PathVariable("a_teamKey") String a_teamKey,
                                                                      @Parameter(description = "User Key", example = "key_user1") @PathVariable("b_userKey") String b_userKey) {
        try {
            return ResponseEntity.ok().body(this.pickResultService.getNotPickedResultsByUser(a_teamKey, b_userKey));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
