package com.timsanalytics.apps.realityTracker.competition.services;

import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionPickResult;
import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitionScoringService {
    private CompetitionPickResultService pickResultService;
    private CompetitionPlayerService competitionPlayerService;
    private CompetitionRoundService roundService;

    @Autowired
    public CompetitionScoringService(CompetitionPickResultService pickResultService, CompetitionPlayerService competitionPlayerService, CompetitionRoundService roundService) {
        this.pickResultService = pickResultService;
        this.competitionPlayerService = competitionPlayerService;
        this.roundService = roundService;
    }

    public CompetitionScore getPickResultScore(String teamKey, String userKey, Integer pickPosition, Integer roundNumber) {
        CompetitionPickResult.Status status = this.pickResultService.getPickResult(teamKey, userKey, pickPosition, roundNumber).getStatus();
        if (status.equals(CompetitionPickResult.Status.CORRECT)) {
            Integer score = this.roundService.getRoundByRoundNumber(roundNumber).getRoundPoints();
            return new CompetitionScore(teamKey, userKey, roundNumber, pickPosition, score, null);
        } else {
            return null;
        }
    }

    public CompetitionScore getPickResultScoreByTeamUserRound(String teamKey, String userKey, Integer roundNumber) {
        if (!this.roundService.isRoundNumberValid(roundNumber)) {
            throw new IllegalArgumentException("Ths round number is invalid.");
        } else {
            Integer score = this.pickResultService.getPickResultsByRound(teamKey, userKey, roundNumber).stream()
                    .filter(item -> item.getStatus().equals(CompetitionPickResult.Status.CORRECT))
                    .mapToInt(item -> this.roundService.getRoundByRoundNumber(item.getRoundNumber()).getRoundPoints())
                    .sum();
            return new CompetitionScore(teamKey, userKey, roundNumber, null, score, null);
        }
    }

    public CompetitionScore getPickResultScoreByTeamUser(String teamKey, String userKey) {
        Integer score = this.pickResultService.getPickResultsByTeamUser(teamKey, userKey).stream()
                .filter(item -> item.getStatus().equals(CompetitionPickResult.Status.CORRECT))
                .mapToInt(item -> this.roundService.getRoundByRoundNumber(item.getRoundNumber()).getRoundPoints())
                .sum();
        return new CompetitionScore(teamKey, userKey, null, null, score, null);
    }

    public List<CompetitionScore> getPickResultScoreByTeamUserGroupByRound(String teamKey, String userKey) {
        return this.pickResultService.getPickResultsByTeamUser(teamKey, userKey).stream()
                .filter(item -> item.getStatus().equals(CompetitionPickResult.Status.CORRECT))
                .collect(Collectors.groupingBy(CompetitionPickResult::getRoundNumber, Collectors.summingInt(item -> this.roundService.getRoundByRoundNumber(item.getRoundNumber()).getRoundPoints())))
                .entrySet().stream()
                .map(e -> new CompetitionScore(teamKey, userKey, e.getKey(), null, e.getValue(), null))
                .collect(Collectors.toList());
    }

    public List<CompetitionScore> getPickResultScoreByTeamGroupByUserRound(String teamKey) {
        return this.competitionPlayerService.getUserListByTeamKey(teamKey).stream()
                .map(user -> this.getPickResultScoreByTeamUserGroupByRound(teamKey, user.getUserKey()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<CompetitionScore> getPickResultScoreByTeamGroupByUser(String teamKey) {
        return this.competitionPlayerService.getUserListByTeamKey(teamKey).stream()
                .map(user -> this.getPickResultScoreByTeamUser(teamKey, user.getUserKey()))
                .collect(Collectors.toList());

    }
}

