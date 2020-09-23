package com.timsanalytics.apps.realityCompetition.services;

import com.timsanalytics.apps.realityCompetition.beans.*;
import com.timsanalytics.apps.realityCompetition.beans.PickResult;
import com.timsanalytics.apps.realityCompetition.tester.Tester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PickResultService {
    private final PickService pickService;
    private final ResultService resultService;
    private final RoundService roundService;

    @Autowired
    public PickResultService(PickService pickService, ResultService resultService, RoundService roundService) {
        this.pickService = pickService;
        this.resultService = resultService;
        this.roundService = roundService;
    }

    public static void main(String[] args) {
        Tester.main(null);
    }

    // Single Cell: THIS IS THE HEART OF THIS SYSTEM.
    public PickResult getPickResult(String teamKey, String userKey, Integer position, Integer roundNumber) {

        // Set Default/Initial status
        PickResult.Status status = PickResult.Status.UNKNOWN;

        // Get the pick for the criteria provided.
        Pick specificPick = this.pickService.getPick(teamKey, userKey, position);
        // Only picks that are within the cutoff for the round provided.
        specificPick = specificPick.getPosition() <= this.roundService.getRound(roundNumber).getRoundCutoffCount() ? specificPick : null;

        // Check to see if there is a result for the pick.
        Result specificResult = null;

        if (specificPick != null) {

            if (roundNumber <= this.roundService.getLastPlayedRoundNumber()) { // Known Result

                Pick pick = specificPick;
                specificResult = this.resultService.getResultList().stream()
                        .filter(result -> result.getRoundNumber().equals(roundNumber)) // Only check results for the round provided.
                        .filter(result -> result.getContestantKey().equalsIgnoreCase(pick.getContestantKey())) // Only show when the pick matches the result.
                        .findFirst()
                        .orElse(null);

                status = specificResult != null
                        ? // If a result is found
                        PickResult.Status.CORRECT
                        : // If no result is found
                        roundNumber <= this.roundService.getLastPlayedRoundNumber()
                                ? PickResult.Status.WRONG
                                : PickResult.Status.UNKNOWN; // This shouldn't happen since we're only looking at rounds already played.

            } else { // Projected Pick Result

                Pick pick = specificPick;
                specificResult = this.resultService.getResultList().stream()
                        .filter(result -> result.getRoundNumber().equals(this.roundService.getLastPlayedRoundNumber())) // Only check results for the round provided.
                        .filter(result -> result.getContestantKey().equalsIgnoreCase(pick.getContestantKey())) // Only show when the pick matches the result.
                        .findFirst()
                        .orElse(null);

                status = specificResult != null
                        ? // If a result is found
                        PickResult.Status.PROJECTED
                        : // If no result is found
                        PickResult.Status.WRONG;

            }

        } else {
            status = PickResult.Status.OUT_OF_SCOPE;
        }
        return new PickResult(specificPick, specificResult, roundNumber, position, status);
    }

    // AGGREGATIONS

    // Vertical
    public List<PickResult> getPickResultByTeamUserRound(String teamKey, String userKey, Integer roundNumber) {
        if (!this.roundService.isRoundNumberValid(roundNumber)) {
            throw new IllegalArgumentException("The round number is invalid.");
        } else {
            return this.pickService.getPickListByTeamUserRound(teamKey, userKey, roundNumber).stream()
                    .map(pick -> this.getPickResult(teamKey, userKey, pick.getPosition(), roundNumber))
                    .sorted(Comparator.comparing(PickResult::getPosition))
                    .collect(Collectors.toList());
        }
    }

    // Horizontal
    public List<PickResult> getPickResultByTeamUserPosition(String teamKey, String userKey, Integer position) {
        return this.pickService.getPickListByTeamUserPosition(teamKey, userKey, position).stream()
                .map(pick -> this.getPickResult(teamKey, userKey, position, pick.getRoundNumber()))
                .sorted(Comparator.comparing(PickResult::getRoundNumber))
                .collect(Collectors.toList());
    }

    // All (Default Vertical Method)
    public List<PickResult> getPickResultByTeamUser(String teamKey, String userKey) {
        return this.roundService.getRoundList().stream()
                .flatMap(round -> this.pickService.getPickListByTeamUserRound(teamKey, userKey, round.getRoundNumber()).stream()
                        .map(pick -> this.getPickResult(teamKey, userKey, pick.getPosition(), round.getRoundNumber())))
                .collect(Collectors.toList());
    }

    // TODO
    // All (Horizontal Method)
    public List<PickResult> getPickResultByTeamUser_HorizontalAggregation(String teamKey, String userKey) {
        return null;
    }

}
