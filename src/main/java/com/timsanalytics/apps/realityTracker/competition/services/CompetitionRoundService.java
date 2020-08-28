package com.timsanalytics.apps.realityTracker.competition.services;

import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionResult;
import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionRound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class CompetitionRoundService {
    private CompetitionDataService dataService;

    @Autowired
    public CompetitionRoundService(CompetitionDataService dataService) {
        this.dataService = dataService;
    }

    public CompetitionRound getRoundByRoundNumber(int roundNumber) {
        return this.dataService.getRounds().stream()
                .filter(round -> round.getRoundNumber().equals(roundNumber))
                .findFirst()
                .orElse(null);
    }

    public Integer getLastPlayedRound() {
        return this.dataService.getResults().stream()
                .map(CompetitionResult::getRoundNumber)
                .max(Comparator.comparing(x -> x))
                .orElse(null);
    }

    public Integer getFinalRound() {
        return this.dataService.getRounds().stream()
                .map(CompetitionRound::getRoundNumber)
                .max(Comparator.comparing(x -> x))
                .orElse(null);
    }

    public Boolean isRoundNumberValid(int roundNumber) {
        return roundNumber > 0 && this.dataService.getRounds().stream()
                .anyMatch(round -> round.getRoundNumber().equals(roundNumber));
    }
}
