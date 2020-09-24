package com.timsanalytics.apps.realityCompetition.services;

import com.timsanalytics.apps.realityCompetition.beans.*;
import com.timsanalytics.apps.realityCompetition.tester.Tester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreService {
    private final PickResultService pickResultService;
    private final CompetitionUserService userService;
    private final RoundService roundService;

    @Autowired
    public ScoreService(PickResultService pickResultService, CompetitionUserService userService, RoundService roundService) {
        this.pickResultService = pickResultService;
        this.userService = userService;
        this.roundService = roundService;
    }

    public static void main(String[] args) {
        Tester.main(null);
    }

    // Single Cell
    public Score getScore(String teamKey, String userKey, Integer position, Integer roundNumber) {
        PickResult pickResult = this.pickResultService.getPickResult(teamKey, userKey, position, roundNumber);
        if (pickResult.getStatus().equals(PickResult.Status.CORRECT)) {
            Double score = this.roundService.getRound(roundNumber).getRoundPoints();
            return new Score(teamKey, userKey, roundNumber, position, pickResult.getPick().getContestantKey(), score, pickResult.getStatus());
        } else {
            return new Score(teamKey, userKey, roundNumber, position, pickResult.getPick().getContestantKey(), 0.0, pickResult.getStatus());
        }
    }

    // AGGREGATIONS

    // Vertical
    public Score getScoreByTeamUserRound(String teamKey, String userKey, Integer roundNumber) {
        if (!this.roundService.isRoundNumberValid(roundNumber)) {
            throw new IllegalArgumentException("The round number is invalid.");
        } else {
            List<PickResult> pickResultList = this.pickResultService.getPickResultByTeamUserRound(teamKey, userKey, roundNumber);
            Double score = pickResultList.stream()
                    .filter(pickResult -> pickResult.getStatus().equals(PickResult.Status.CORRECT))
                    .mapToDouble(pickResult -> this.roundService.getRound(pickResult.getRoundNumber()).getRoundPoints())
                    .sum();
            return new Score(teamKey, userKey, roundNumber, null, null, score, null);
        }
    }

    // Horizontal
    public Score getScoreByTeamUserPosition(String teamKey, String userKey, Integer position) {
        List<PickResult> pickResultList = this.pickResultService.getPickResultByTeamUserPosition(teamKey, userKey, position);
        Double score = pickResultList.stream()
                .filter(pickResult -> pickResult.getStatus().equals(PickResult.Status.CORRECT))
                .mapToDouble(pickResult -> this.roundService.getRound(pickResult.getRoundNumber()).getRoundPoints())
                .sum();
        return new Score(teamKey, userKey, null, position, null, score, null);
    }

    // All (Default Vertical Method)
    public Score getScoreByTeamUser(String teamKey, String userKey) {
        Double score = this.pickResultService.getPickResultByTeamUser(teamKey, userKey).stream()
                .filter(pickResult -> pickResult.getStatus().equals(PickResult.Status.CORRECT))
                .mapToDouble(pickResult -> this.roundService.getRound(pickResult.getRoundNumber()).getRoundPoints())
                .sum();
        return new Score(teamKey, userKey, null, null, null, score, null);
    }

    // All (Horizontal Method)
    // TODO
    public Score getScoreByTeamUser_HorizontalMethod(String teamKey, String userKey) {
        return null;
    }

    // GROUPING

    // Vertical
    public List<Score> getScoreByTeamUser_GroupByRound(String teamKey, String userKey) {
        List<PickResult> pickResultList = this.pickResultService.getPickResultByTeamUser(teamKey, userKey);
        return pickResultList.stream()
                .filter(pickResult -> pickResult.getStatus().equals(PickResult.Status.CORRECT))
                .collect(Collectors.groupingBy(PickResult::getRoundNumber, Collectors.summingDouble(pickResult -> this.roundService.getRound(pickResult.getRoundNumber()).getRoundPoints())))
                .entrySet().stream()
                .map(e -> new Score(teamKey, userKey, e.getKey(), null, null, e.getValue(), null))
                .collect(Collectors.toList());
    }

    public List<Score> getScoreByTeam_GroupByUserRound(String teamKey) {
        return this.userService.getUserListByTeamKey(teamKey).stream()
                .map(user -> this.getScoreByTeamUser_GroupByRound(teamKey, user.getUserKey()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<Score> getScoreByTeam_GroupByUser(String teamKey) {
        return this.userService.getUserListByTeamKey(teamKey).stream()
                .map(user -> this.getScoreByTeamUser(teamKey, user.getUserKey()))
                .collect(Collectors.toList());
    }

    // Horizontal
    public List<Score> getScoreByTeamUser_GroupByPosition(String teamKey, String userKey) {
        List<PickResult> pickResultList = this.pickResultService.getPickResultByTeamUser(teamKey, userKey);
        return pickResultList.stream()
                .filter(pickResult -> pickResult.getStatus().equals(PickResult.Status.CORRECT))
                .collect(Collectors.groupingBy(PickResult::getPosition, Collectors.summingDouble(pickResult -> this.roundService.getRound(pickResult.getRoundNumber()).getRoundPoints())))
                .entrySet().stream()
                .map(e -> new Score(teamKey, userKey, e.getKey(), null, null, e.getValue(), null))
                .collect(Collectors.toList());
    }
// TODO
//    public List<Score> getScoreByTeam_GroupByUserPosition(String teamKey) {
//        return this.userService.getUserListByTeamKey(teamKey).stream()
//                .map(user -> this.getScoreByTeamUser_GroupByPosition(teamKey, user.getUserKey()))
//                .flatMap(Collection::stream)
//                .collect(Collectors.toList());
//    }

// TODO
//    public List<Score> getScoreByTeam_GroupByUser_Horizontal(String teamKey) {
//        return this.userService.getUserListByTeamKey(teamKey).stream()
//                .map(user -> this.getScoreByTeamUser(teamKey, user.getUserKey()))
//                .collect(Collectors.toList());
//    }

}
