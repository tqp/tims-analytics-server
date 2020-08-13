package com.timsanalytics.main.realityTracker.competition.services;

import com.timsanalytics.main.realityTracker.competition.beans.PickResult;
import com.timsanalytics.main.realityTracker.competition.beans.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectedScoringService {
    private com.timsanalytics.main.realityTracker.competition.services.PickResultService pickResultService;
    private PlayerService playerService;
    private com.timsanalytics.main.realityTracker.competition.services.RoundService roundService;

    @Autowired
    public ProjectedScoringService(com.timsanalytics.main.realityTracker.competition.services.PickResultService pickResultService, PlayerService playerService, com.timsanalytics.main.realityTracker.competition.services.RoundService roundService) {
        this.pickResultService = pickResultService;
        this.playerService = playerService;
        this.roundService = roundService;
    }

    public Score getPickResultScore(String teamKey, String userKey, Integer pickPosition, Integer roundNumber) {
        PickResult.Status status = this.pickResultService.getPickResult(teamKey, userKey, pickPosition, roundNumber).getStatus();
        Integer score = status.equals(PickResult.Status.CORRECT) || status.equals(PickResult.Status.PROJECTED) ?
                this.roundService.getRoundByRoundNumber(roundNumber).getRoundPoints() : 0;
        return new Score(teamKey, userKey, roundNumber, pickPosition, score, status);
    }

    public Score getPickResultScoreByTeamUserRound(String teamKey, String userKey, Integer roundNumber) {
        if (!this.roundService.isRoundNumberValid(roundNumber)) {
            throw new IllegalArgumentException("The round number is invalid.");
        } else {
            Integer score = this.pickResultService.getPickResultsByRound(teamKey, userKey, roundNumber).stream()
                    .filter(item -> item.getStatus().equals(PickResult.Status.CORRECT) || item.getStatus().equals(PickResult.Status.PROJECTED))
                    .mapToInt(item -> this.roundService.getRoundByRoundNumber(item.getRoundNumber()).getRoundPoints())
                    .sum();
            return new Score(teamKey, userKey, null, null, score, null);
        }
    }

    public Score getPickResultScoreByTeamUser(String teamKey, String userKey) {
        Integer score = this.pickResultService.getPickResultsByTeamUser(teamKey, userKey).stream()
                .filter(item -> item.getStatus().equals(PickResult.Status.CORRECT) || item.getStatus().equals(PickResult.Status.PROJECTED))
                .mapToInt(item -> this.roundService.getRoundByRoundNumber(item.getRoundNumber()).getRoundPoints())
                .sum();
        return new Score(teamKey, userKey, null, null, score, null);
    }


    public List<Score> getPickResultScoreByTeamUserGroupByRound(String teamKey, String userKey) {
        return this.pickResultService.getPickResultsByTeamUser(teamKey, userKey).stream()
                .filter(item -> item.getStatus().equals(PickResult.Status.CORRECT) || item.getStatus().equals(PickResult.Status.PROJECTED))
                .collect(Collectors.groupingBy(PickResult::getRoundNumber, Collectors.summingInt(item -> this.roundService.getRoundByRoundNumber(item.getRoundNumber()).getRoundPoints())))
                .entrySet().stream()
                .map(e -> new Score(teamKey, userKey, e.getKey(), null, e.getValue(), null))
                .collect(Collectors.toList());
    }

    public List<Score> getPickResultScoreByTeamGroupByUserRound(String teamKey) {
        return this.playerService.getUserListByTeamKey(teamKey).stream()
                .map(user -> this.getPickResultScoreByTeamUserGroupByRound(teamKey, user.getUserKey()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<Score> getPickResultScoreByTeamGroupByUser(String teamKey) {
        return this.playerService.getUserListByTeamKey(teamKey).stream()
                .map(user -> this.getPickResultScoreByTeamUser(teamKey, user.getUserKey()))
                .collect(Collectors.toList());
    }
}
