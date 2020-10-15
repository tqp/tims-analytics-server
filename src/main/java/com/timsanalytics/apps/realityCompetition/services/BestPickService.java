package com.timsanalytics.apps.realityCompetition.services;


import com.timsanalytics.apps.realityCompetition.beans.BestPick;
import com.timsanalytics.apps.realityCompetition.beans.Pick;
import com.timsanalytics.apps.realityCompetition.beans.PickResult;
import com.timsanalytics.apps.realityCompetition.beans.Result;
import com.timsanalytics.apps.realityCompetition.tester.Tester;
import com.timsanalytics.common.utils.PrintObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BestPickService {
    ResultService resultService;
    RoundService roundService;
    ProjectedScoreService projectedScoreService;
    PickService pickService;
    CompetitionUserService userService;
    PrintObjectService printObjectService;
    PickResultService pickResultService;

    @Autowired
    public BestPickService(ResultService resultService,
                           RoundService roundService,
                           ProjectedScoreService projectedScoreService,
                           PickService pickService,
                           CompetitionUserService userService,
                           PrintObjectService printObjectService,
                           PickResultService pickResultService

    ) {
        this.resultService = resultService;
        this.roundService = roundService;
        this.projectedScoreService = projectedScoreService;
        this.pickService = pickService;
        this.userService = userService;
        this.printObjectService = printObjectService;
        this.pickResultService = pickResultService;
    }

    public static void main(String[] args) {
        Tester.main(null);
    }

    public List<BestPick> getBestPicks(String teamKey, String userKey, Boolean onlyNextRound) {
        List<Result> remainingPlayerList = this.resultService.getResultList().stream()
                // Get only the contestants that are still in the game
                .filter(result -> result.getRoundNumber().equals(this.roundService.getLastPlayedRoundNumber()))
                .collect(Collectors.toList());

        int nextRound = this.roundService.getLastPlayedRoundNumber() + 1;
        int numberOfOpponentsOnTeam = this.userService.getUserListByTeamKey(teamKey).size() - 1;

        return remainingPlayerList.stream()
                .map(result -> {

                    // Get my projected score for the contestant
                    double myPoints = this.pickResultService.getPickResultByTeamUser(teamKey, userKey).stream()
                            .filter(pickResult -> onlyNextRound ? pickResult.getRoundNumber() == nextRound : pickResult.getRoundNumber() >= nextRound)
                            .filter(pickResult -> pickResult.getPick().getContestantKey().equalsIgnoreCase(result.getContestantKey()))
                            .filter(pickResult -> pickResult.getStatus().equals(PickResult.Status.CORRECT) || pickResult.getStatus().equals(PickResult.Status.PROJECTED))
                            .mapToDouble(pickResult -> this.roundService.getRound(pickResult.getRoundNumber()).getRoundPoints())
                            .sum();

                    // Get my team opponents combined score for the contestant
                    double opponentsTotalPoints = this.roundService.getRoundList().stream()
                            .filter(pickResult -> onlyNextRound ? pickResult.getRoundNumber() == nextRound : pickResult.getRoundNumber() >= nextRound)
                            .flatMap(round -> {
                                if (!this.roundService.isRoundNumberValid(round.getRoundNumber())) {
                                    throw new IllegalArgumentException("The round number is invalid.");
                                } else {
                                    return this.pickService.getPickList().stream()
                                            .filter(pick -> pick.getTeamKey().equalsIgnoreCase(teamKey))
                                            .filter(pick -> !pick.getUserKey().equalsIgnoreCase(userKey))
                                            .filter(pick -> this.pickService.isPositionValid(pick.getPosition(), round.getRoundNumber()))
                                            .peek(pick -> pick.setRoundNumber(round.getRoundNumber()))
                                            .sorted(Comparator.comparing(Pick::getPosition));
                                }
                            })
                            .filter(pick -> pick.getContestantKey().equalsIgnoreCase(result.getContestantKey()))
                            .mapToDouble(pickResult -> this.roundService.getRound(pickResult.getRoundNumber()).getRoundPoints())
                            .sum();

                    double opponentsAveragePoints = opponentsTotalPoints / numberOfOpponentsOnTeam;
                    double pointDifferential = myPoints - opponentsAveragePoints;
                    return new BestPick(result.getContestantKey(), myPoints, opponentsAveragePoints, pointDifferential);
                })
                .collect(Collectors.toList());
    }

    public List<BestPick> getBestPicksAgainst(String teamKey, String userKey, String againstUserKey, Boolean onlyNextRound) {
        List<Result> remainingPlayerList = this.resultService.getResultList().stream()
                // Get only the contestants that are still in the game.
                .filter(result -> result.getRoundNumber().equals(this.roundService.getLastPlayedRoundNumber()))
                .collect(Collectors.toList());

        int nextRound = this.roundService.getLastPlayedRoundNumber() + 1;
        int numberOfOpponentsOnTeam = this.userService.getUserListByTeamKey(teamKey).size() - 1;

        return remainingPlayerList.stream()
                .map(result -> {

                    // Get my projected score for the contestant
                    double myPoints = this.pickResultService.getPickResultByTeamUser(teamKey, userKey).stream()
                            .filter(pickResult -> onlyNextRound ? pickResult.getRoundNumber() == nextRound : pickResult.getRoundNumber() >= nextRound)
                            .filter(pickResult -> pickResult.getPick().getContestantKey().equalsIgnoreCase(result.getContestantKey()))
                            .filter(pickResult -> pickResult.getStatus().equals(PickResult.Status.CORRECT) || pickResult.getStatus().equals(PickResult.Status.PROJECTED))
                            .mapToDouble(pickResult -> this.roundService.getRound(pickResult.getRoundNumber()).getRoundPoints())
                            .sum();

                    // Get my opponent's projected score for the contestant
                    double theirPoints = this.pickResultService.getPickResultByTeamUser(teamKey, againstUserKey).stream()
                            .filter(pickResult -> onlyNextRound ? pickResult.getRoundNumber() == nextRound : pickResult.getRoundNumber() >= nextRound)
                            .filter(pickResult -> pickResult.getPick().getContestantKey().equalsIgnoreCase(result.getContestantKey()))
                            .filter(pickResult -> pickResult.getStatus().equals(PickResult.Status.CORRECT) || pickResult.getStatus().equals(PickResult.Status.PROJECTED))
                            .mapToDouble(pickResult -> this.roundService.getRound(pickResult.getRoundNumber()).getRoundPoints())
                            .sum();

                    double pointDifferential = myPoints - theirPoints;
                    return new BestPick(result.getContestantKey(), myPoints, theirPoints, pointDifferential);
                })
                .collect(Collectors.toList());
    }

    public List<BestPick> getBestPicksRootToStay_NextRound(String teamKey, String userKey) {
        return this.getBestPicks(teamKey, userKey, true).stream()
                .filter(bestPick -> bestPick.getPointDifferential() > 0)
                .sorted(Comparator.comparing(BestPick::getPointDifferential).reversed())
                .collect(Collectors.toList());
    }

    public List<BestPick> getBestPicksRootToLeave_NextRound(String teamKey, String userKey) {
        return this.getBestPicks(teamKey, userKey, true).stream()
                .filter(bestPick -> bestPick.getPointDifferential() < 0)
                .sorted(Comparator.comparing(BestPick::getPointDifferential).reversed())
                .collect(Collectors.toList());
    }

    public List<BestPick> getBestPicksNoImpact_NextRound(String teamKey, String userKey) {
        return this.getBestPicks(teamKey, userKey, true).stream()
                .filter(bestPick -> bestPick.getPointDifferential() == 0)
                .sorted(Comparator.comparing(BestPick::getPointDifferential).reversed())
                .collect(Collectors.toList());
    }

    public List<BestPick> getBestPicksRootToStay_RemainderOfGame(String teamKey, String userKey) {
        return this.getBestPicks(teamKey, userKey, false).stream()
                .filter(bestPick -> bestPick.getPointDifferential() > 0)
                .sorted(Comparator.comparing(BestPick::getPointDifferential).reversed())
                .collect(Collectors.toList());
    }

    public List<BestPick> getBestPicksRootToLeave_RemainderOfGame(String teamKey, String userKey) {
        return this.getBestPicks(teamKey, userKey, false).stream()
                .filter(bestPick -> bestPick.getPointDifferential() < 0)
                .sorted(Comparator.comparing(BestPick::getPointDifferential).reversed())
                .collect(Collectors.toList());
    }

    public List<BestPick> getBestPicksRootNoImpact_RemainderOfGame(String teamKey, String userKey) {
        return this.getBestPicks(teamKey, userKey, false).stream()
                .filter(bestPick -> bestPick.getPointDifferential() == 0)
                .sorted(Comparator.comparing(BestPick::getPointDifferential).reversed())
                .collect(Collectors.toList());
    }
}
