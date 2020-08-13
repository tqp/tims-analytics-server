package com.timsanalytics.main.realityTracker.competition.services;

import com.timsanalytics.main.realityTracker.competition.beans.BestPick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BestPickService {
    private DataService dataService;
    private RoundService roundService;
    private PickService pickService;

    @Autowired
    public BestPickService(DataService dataService, RoundService roundService, PickService pickService) {
        this.dataService = dataService;
        this.roundService = roundService;
        this.pickService = pickService;
    }

    public List<BestPick> getBestPicksByTeamUserRound(String teamKey, String userKey, Integer roundNumber) {
        this.dataService.getResults().stream()
                .filter(result -> result.getRoundNumber().equals(this.roundService.getLastPlayedRound()))
                .collect(Collectors.toList())
                .forEach(result -> {

                    this.pickService.getUserPicksByRound(teamKey, userKey, roundNumber).stream()
                            .filter(pick -> pick.getContestantKey().equalsIgnoreCase(result.getContestantKey()))
                            .forEach(pick -> {
                                System.out.println("Pick: " + pick.getContestantKey());
                            });
                });
        return null;
    }
}
