package com.timsanalytics.apps.realityCompetition.services;

import com.timsanalytics.apps.realityCompetition.beans.*;
import com.timsanalytics.apps.realityCompetition.tester.Tester;
import com.timsanalytics.common.utils.PrintObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectedScoreService {
    private final PickService pickService;
    private final PickResultService pickResultService;
    private final UserService userService;
    private final RoundService roundService;
    private final PrintObjectService printObjectService;

    @Autowired
    public ProjectedScoreService(PickService pickService,
                                 PickResultService pickResultService,
                                 UserService userService,
                                 RoundService roundService,
                                 PrintObjectService printObjectService) {
        this.pickService = pickService;
        this.pickResultService = pickResultService;
        this.userService = userService;
        this.roundService = roundService;
        this.printObjectService = printObjectService;
    }

    public static void main(String[] args) {
        Tester.main(null);
    }

    // Single Cell
    public Score getProjectedScore(String teamKey, String userKey, Integer roundNumber, Integer position) {
        PickResult pickResult = this.pickResultService.getPickResult(teamKey, userKey, position, roundNumber);
        if (pickResult.getStatus().equals(PickResult.Status.CORRECT) || pickResult.getStatus().equals(PickResult.Status.PROJECTED)) {
            Double score = pickResult.getStatus().equals(PickResult.Status.CORRECT) || pickResult.getStatus().equals(PickResult.Status.PROJECTED)
                    ? this.roundService.getRound(roundNumber).getRoundPoints()
                    : 0;
            return new Score(teamKey, userKey, roundNumber, position, pickResult.getPick().getContestantKey(), score, pickResult.getStatus());
        } else {
            return new Score(teamKey, userKey, roundNumber, position, pickService.getPick(teamKey, userKey, position).getContestantKey(), 0.0, pickResult.getStatus());
        }
    }

    // AGGREGATIONS

    // Vertical
    public Score getProjectedScoreByTeamUserRound(String teamKey, String userKey, Integer roundNumber) {
        if (!this.roundService.isRoundNumberValid(roundNumber)) {
            throw new IllegalArgumentException("The round number is invalid.");
        } else {
            Double score = this.pickResultService.getPickResultByTeamUserRound(teamKey, userKey, roundNumber).stream()
                    .filter(pickResult -> pickResult.getStatus().equals(PickResult.Status.CORRECT) || pickResult.getStatus().equals(PickResult.Status.PROJECTED))
                    .mapToDouble(pickResult -> this.roundService.getRound(pickResult.getRoundNumber()).getRoundPoints())
                    .sum();
            return new Score(teamKey, userKey, null, null, null, score, null);
        }
    }

    // Horizontal
    public Score getProjectedScoreByTeamUserPosition(String teamKey, String userKey, Integer position) {
        Double score = this.pickResultService.getPickResultByTeamUserPosition(teamKey, userKey, position).stream()
                .filter(pickResult -> pickResult.getStatus().equals(PickResult.Status.CORRECT) || pickResult.getStatus().equals(PickResult.Status.PROJECTED))
                .mapToDouble(pickResult -> this.roundService.getRound(pickResult.getRoundNumber()).getRoundPoints())
                .sum();
        return new Score(teamKey, userKey, null, null, this.pickService.getPick(teamKey, userKey, position).getContestantKey(), score, null);
    }

    // All (Default Vertical Method)
    public Score getProjectedScoreByTeamUser(String teamKey, String userKey) {
        Double score = this.pickResultService.getPickResultByTeamUser(teamKey, userKey).stream()
                .filter(pickResult -> pickResult.getStatus().equals(PickResult.Status.CORRECT) || pickResult.getStatus().equals(PickResult.Status.PROJECTED))
                .mapToDouble(pickResult -> this.roundService.getRound(pickResult.getRoundNumber()).getRoundPoints())
                .sum();
        return new Score(teamKey, userKey, null, null, null, score, null);
    }

    // GROUPINGS

    // Vertical
    public List<Score> getProjectedScoreByTeamUser_GroupByRound(String teamKey, String userKey) {
        return this.pickResultService.getPickResultByTeamUser(teamKey, userKey).stream()
                .filter(pickResult -> pickResult.getStatus().equals(PickResult.Status.CORRECT) || pickResult.getStatus().equals(PickResult.Status.PROJECTED))
                .collect(Collectors.groupingBy(PickResult::getRoundNumber, Collectors.summingDouble(pickResult -> this.roundService.getRound(pickResult.getRoundNumber()).getRoundPoints())))
                .entrySet().stream()
                .map(e -> new Score(teamKey, userKey, e.getKey(), null, null, e.getValue(), null))
                .collect(Collectors.toList());
    }

    // TODO: Something's not right here
    public List<Score> getProjectedScoreByTeam_GroupByUserRound(String teamKey) {
        return this.userService.getUserListByTeamKey(teamKey).stream()
                .map(user -> this.getProjectedScoreByTeamUser_GroupByRound(teamKey, user.getUserKey()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    // Horizontal
    public List<Score> getProjectedScoreByTeamUser_GroupByPosition(String teamKey, String userKey) {
        return this.pickResultService.getPickResultByTeamUser(teamKey, userKey).stream()
                .filter(pickResult -> pickResult.getStatus().equals(PickResult.Status.CORRECT) || pickResult.getStatus().equals(PickResult.Status.PROJECTED))
                .collect(Collectors.groupingBy(PickResult::getPosition, Collectors.summingDouble(pickResult -> this.roundService.getRound(pickResult.getRoundNumber()).getRoundPoints())))
                .entrySet().stream()
                .map(e -> new Score(teamKey, userKey, null, e.getKey(), this.pickService.getPick(teamKey, userKey, e.getKey()).getContestantKey(), e.getValue(), null))
                .collect(Collectors.toList());
    }

    // TODO: Something's not right here
    // All
    public List<Score> getProjectedScoreByTeam_GroupByUser(String teamKey) {
        return this.userService.getUserListByTeamKey(teamKey).stream()
                .map(user -> this.getProjectedScoreByTeamUser(teamKey, user.getUserKey()))
                .collect(Collectors.toList());
    }
}
