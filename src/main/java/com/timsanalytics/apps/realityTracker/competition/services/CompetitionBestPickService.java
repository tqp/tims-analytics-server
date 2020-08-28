package com.timsanalytics.apps.realityTracker.competition.services;

import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionBestPick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitionBestPickService {
    private CompetitionDataService dataService;
    private CompetitionRoundService roundService;
    private CompetitionPickService pickService;

    @Autowired
    public CompetitionBestPickService(CompetitionDataService dataService, CompetitionRoundService roundService, CompetitionPickService pickService) {
        this.dataService = dataService;
        this.roundService = roundService;
        this.pickService = pickService;
    }

    public List<CompetitionBestPick> getBestPicksByTeamUserRound(String teamKey, String userKey, Integer roundNumber) {
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
