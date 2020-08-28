package com.timsanalytics.apps.realityTracker.competition.services;

import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionPick;
import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionPickResult;
import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CompetitionPickResultService {
    private CompetitionDataService dataService;
    private CompetitionPickService pickService;
    private CompetitionResultService resultService;
    private CompetitionRoundService roundService;

    @Autowired
    public CompetitionPickResultService(CompetitionDataService dataService, CompetitionPickService pickService, CompetitionResultService resultService, CompetitionRoundService roundService) {
        this.dataService = dataService;
        this.pickService = pickService;
        this.resultService = resultService;
        this.roundService = roundService;
    }

    // BY PICK

    public CompetitionPickResult getPickResult(String teamKey, String userKey, Integer pickPosition, Integer roundNumber) {
        CompetitionPickResult.Status status = CompetitionPickResult.Status.UNKNOWN;

        // Set invalid status for exceptions and outliers
        if (!this.roundService.isRoundNumberValid(roundNumber) || !this.pickService.isPickPositionValid(pickPosition, roundNumber)) {
            return new CompetitionPickResult(null, null, roundNumber, CompetitionPickResult.Status.INVALID);
        } else {

            // Get the pick for the criteria given
            CompetitionPick specificPick = this.dataService.getPicks().stream()
                    .filter(pick -> pick.getTeamKey().equalsIgnoreCase(teamKey))
                    .filter(pick -> pick.getUserKey().equalsIgnoreCase(userKey))
                    .filter(pick -> pick.getPickPosition().equals(pickPosition))
                    .filter(pick -> pick.getPickPosition() <= this.roundService.getRoundByRoundNumber(roundNumber).getRoundCutoffCount())
                    .findFirst()
                    .orElse(null);

            // Check to see if a result matches the pick (i.e. roundNumber and contestantKey)
            CompetitionResult specificResult = null;
            if (specificPick != null) {

                if (roundNumber <= this.roundService.getLastPlayedRound()) { // Actual Pick Result
                    specificResult = this.dataService.getResults().stream()
                            .filter(result -> result.getRoundNumber().equals(roundNumber))
                            .filter(result -> result.getContestantKey().equalsIgnoreCase(specificPick.getContestantKey()))
                            .findFirst()
                            .orElse(null);
                    status = specificResult != null ? CompetitionPickResult.Status.CORRECT : roundNumber <= this.roundService.getLastPlayedRound() ? CompetitionPickResult.Status.WRONG : CompetitionPickResult.Status.UNKNOWN;
                } else { // Prijected Pick Result
                    specificResult = this.dataService.getResults().stream()
                            .filter(result -> result.getRoundNumber().equals(this.roundService.getLastPlayedRound()))
                            .filter(result -> result.getContestantKey().equalsIgnoreCase(specificPick.getContestantKey()))
                            .findFirst()
                            .orElse(null);
                    status = specificResult != null ? CompetitionPickResult.Status.PROJECTED : CompetitionPickResult.Status.WRONG;
                }
            }
            return new CompetitionPickResult(specificPick, specificResult, roundNumber, status);
        }
    }

    // BY ROUND

    public List<CompetitionPickResult> getAllResultByRound(String teamKey, String userKey, Integer roundNumber) {
        if (!this.roundService.isRoundNumberValid(roundNumber)) {
            throw new IllegalArgumentException("The round number is invalid.");
        } else {
            return Stream.of(this.getPickResultsByRound(teamKey, userKey, roundNumber),
                    this.getNotPickedResultsByRound(teamKey, userKey, roundNumber))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
    }

    public List<CompetitionPickResult> getPickResultsByRound(String teamKey, String userKey, Integer roundNumber) {
        if (!this.roundService.isRoundNumberValid(roundNumber)) {
            throw new IllegalArgumentException("The round number is invalid.");
        } else {
            return this.pickService.getUserPicksByRound(teamKey, userKey, roundNumber).stream()
                    .map(pick -> this.getPickResult(teamKey, userKey, pick.getPickPosition(), roundNumber))
                    .collect(Collectors.toList());
        }
    }

    public List<CompetitionPickResult> getNotPickedResultsByRound(String teamKey, String userKey, Integer roundNumber) {
        if (!this.roundService.isRoundNumberValid(roundNumber)) {
            throw new IllegalArgumentException("The round number is invalid.");
        } else {
            return this.resultService.getResultListByRound(roundNumber).stream()
                    .filter(result -> this.pickService.getUserPicksByRound(teamKey, userKey, roundNumber).stream()
                            .noneMatch(pick -> pick.getContestantKey().equalsIgnoreCase(result.getContestantKey())))
                    .map(result -> new CompetitionPickResult(null, result, roundNumber, CompetitionPickResult.Status.NOT_PICKED))
                    .collect(Collectors.toList());
        }
    }

    // BY TEAM/USER

    public List<CompetitionPickResult> getAllResultsByUser(String teamKey, String userKey) {
        Comparator<CompetitionPickResult> compareByPickPosition = Comparator.comparing(pickResult -> pickResult.getPick()
                != null ? pickResult.getPick().getPickPosition() : null, Comparator.nullsLast(Comparator.naturalOrder()));

        return Stream.of(this.getPickResultsByTeamUser(teamKey, userKey),
                this.getNotPickedResultsByUser(teamKey, userKey))
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(CompetitionPickResult::getRoundNumber)
                        .thenComparing(compareByPickPosition)
                )
                .collect(Collectors.toList());
    }

    public List<CompetitionPickResult> getPickResultsByTeamUser(String teamKey, String userKey) {
        return this.dataService.getRounds().stream()
                .flatMap(round -> this.pickService.getUserPicksByRound(teamKey, userKey, round.getRoundNumber()).stream()
                        .map(pick -> this.getPickResult(teamKey, userKey, pick.getPickPosition(), round.getRoundNumber())))
                .collect(Collectors.toList());
    }

    public List<CompetitionPickResult> getNotPickedResultsByUser(String teamKey, String userKey) {
        return this.dataService.getRounds().stream()
                .flatMap(round -> this.resultService.getResultListByRound(round.getRoundNumber()).stream()
                        .filter(result -> this.pickService.getUserPicksByRound(teamKey, userKey, round.getRoundNumber()).stream()
                                .noneMatch(pick -> pick.getContestantKey().equalsIgnoreCase(result.getContestantKey())))
                        .map(result -> new CompetitionPickResult(null, result, round.getRoundNumber(), CompetitionPickResult.Status.NOT_PICKED)))
                .collect(Collectors.toList());
    }
}
