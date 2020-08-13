package com.timsanalytics.main.realityTracker.competition.services;

import com.timsanalytics.main.realityTracker.competition.beans.Pick;
import com.timsanalytics.main.realityTracker.competition.beans.PickResult;
import com.timsanalytics.main.realityTracker.competition.beans.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PickResultService {
    private DataService dataService;
    private PickService pickService;
    private ResultService resultService;
    private com.timsanalytics.main.realityTracker.competition.services.RoundService roundService;

    @Autowired
    public PickResultService(DataService dataService, PickService pickService, ResultService resultService, com.timsanalytics.main.realityTracker.competition.services.RoundService roundService) {
        this.dataService = dataService;
        this.pickService = pickService;
        this.resultService = resultService;
        this.roundService = roundService;
    }

    // BY PICK

    public PickResult getPickResult(String teamKey, String userKey, Integer pickPosition, Integer roundNumber) {
        PickResult.Status status = PickResult.Status.UNKNOWN;

        // Set invalid status for exceptions and outliers
        if (!this.roundService.isRoundNumberValid(roundNumber) || !this.pickService.isPickPositionValid(pickPosition, roundNumber)) {
            return new PickResult(null, null, roundNumber, PickResult.Status.INVALID);
        } else {

            // Get the pick for the criteria given
            Pick specificPick = this.dataService.getPicks().stream()
                    .filter(pick -> pick.getTeamKey().equalsIgnoreCase(teamKey))
                    .filter(pick -> pick.getUserKey().equalsIgnoreCase(userKey))
                    .filter(pick -> pick.getPickPosition().equals(pickPosition))
                    .filter(pick -> pick.getPickPosition() <= this.roundService.getRoundByRoundNumber(roundNumber).getRoundCutoffCount())
                    .findFirst()
                    .orElse(null);

            // Check to see if a result matches the pick (i.e. roundNumber and contestantKey)
            Result specificResult = null;
            if (specificPick != null) {

                if (roundNumber <= this.roundService.getLastPlayedRound()) { // Actual Pick Result
                    specificResult = this.dataService.getResults().stream()
                            .filter(result -> result.getRoundNumber().equals(roundNumber))
                            .filter(result -> result.getContestantKey().equalsIgnoreCase(specificPick.getContestantKey()))
                            .findFirst()
                            .orElse(null);
                    status = specificResult != null ? PickResult.Status.CORRECT : roundNumber <= this.roundService.getLastPlayedRound() ? PickResult.Status.WRONG : PickResult.Status.UNKNOWN;
                } else { // Prijected Pick Result
                    specificResult = this.dataService.getResults().stream()
                            .filter(result -> result.getRoundNumber().equals(this.roundService.getLastPlayedRound()))
                            .filter(result -> result.getContestantKey().equalsIgnoreCase(specificPick.getContestantKey()))
                            .findFirst()
                            .orElse(null);
                    status = specificResult != null ? PickResult.Status.PROJECTED : PickResult.Status.WRONG;
                }
            }
            return new PickResult(specificPick, specificResult, roundNumber, status);
        }
    }

    // BY ROUND

    public List<PickResult> getAllResultByRound(String teamKey, String userKey, Integer roundNumber) {
        if (!this.roundService.isRoundNumberValid(roundNumber)) {
            throw new IllegalArgumentException("The round number is invalid.");
        } else {
            return Stream.of(this.getPickResultsByRound(teamKey, userKey, roundNumber),
                    this.getNotPickedResultsByRound(teamKey, userKey, roundNumber))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
    }

    public List<PickResult> getPickResultsByRound(String teamKey, String userKey, Integer roundNumber) {
        if (!this.roundService.isRoundNumberValid(roundNumber)) {
            throw new IllegalArgumentException("The round number is invalid.");
        } else {
            return this.pickService.getUserPicksByRound(teamKey, userKey, roundNumber).stream()
                    .map(pick -> this.getPickResult(teamKey, userKey, pick.getPickPosition(), roundNumber))
                    .collect(Collectors.toList());
        }
    }

    public List<PickResult> getNotPickedResultsByRound(String teamKey, String userKey, Integer roundNumber) {
        if (!this.roundService.isRoundNumberValid(roundNumber)) {
            throw new IllegalArgumentException("The round number is invalid.");
        } else {
            return this.resultService.getResultListByRound(roundNumber).stream()
                    .filter(result -> this.pickService.getUserPicksByRound(teamKey, userKey, roundNumber).stream()
                            .noneMatch(pick -> pick.getContestantKey().equalsIgnoreCase(result.getContestantKey())))
                    .map(result -> new PickResult(null, result, roundNumber, PickResult.Status.NOT_PICKED))
                    .collect(Collectors.toList());
        }
    }

    // BY TEAM/USER

    public List<PickResult> getAllResultsByUser(String teamKey, String userKey) {
        Comparator<PickResult> compareByPickPosition = Comparator.comparing(pickResult -> pickResult.getPick()
                != null ? pickResult.getPick().getPickPosition() : null, Comparator.nullsLast(Comparator.naturalOrder()));

        return Stream.of(this.getPickResultsByTeamUser(teamKey, userKey),
                this.getNotPickedResultsByUser(teamKey, userKey))
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(PickResult::getRoundNumber)
                        .thenComparing(compareByPickPosition)
                )
                .collect(Collectors.toList());
    }

    public List<PickResult> getPickResultsByTeamUser(String teamKey, String userKey) {
        return this.dataService.getRounds().stream()
                .flatMap(round -> this.pickService.getUserPicksByRound(teamKey, userKey, round.getRoundNumber()).stream()
                        .map(pick -> this.getPickResult(teamKey, userKey, pick.getPickPosition(), round.getRoundNumber())))
                .collect(Collectors.toList());
    }

    public List<PickResult> getNotPickedResultsByUser(String teamKey, String userKey) {
        return this.dataService.getRounds().stream()
                .flatMap(round -> this.resultService.getResultListByRound(round.getRoundNumber()).stream()
                        .filter(result -> this.pickService.getUserPicksByRound(teamKey, userKey, round.getRoundNumber()).stream()
                                .noneMatch(pick -> pick.getContestantKey().equalsIgnoreCase(result.getContestantKey())))
                        .map(result -> new PickResult(null, result, round.getRoundNumber(), PickResult.Status.NOT_PICKED)))
                .collect(Collectors.toList());
    }
}
