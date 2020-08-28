package com.timsanalytics.apps.realityTracker.competition.services;

import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitionResultService {
    private CompetitionDataService dataService;

    @Autowired
    public CompetitionResultService(CompetitionDataService dataService) {
        this.dataService = dataService;
    }

    public List<CompetitionResult> getResultList() {
        return this.dataService.getResults().stream()
                .sorted(Comparator.comparing(CompetitionResult::getRoundNumber)
                        .thenComparing(CompetitionResult::getCallOutOrder)
                        .thenComparing(CompetitionResult::getContestantKey))
                //.peek(result -> System.out.println(result.getContestantKey()))
                .collect(Collectors.toList());
    }

    public List<CompetitionResult> getResultListByRound(Integer roundNumber) {
        return this.dataService.getResults().stream()
                .filter(result -> result.getRoundNumber().equals(roundNumber))
                .sorted(Comparator.comparing(CompetitionResult::getCallOutOrder)
                        .thenComparing(CompetitionResult::getContestantKey))
                //.peek(result -> System.out.println(result.getContestantKey()))
                .collect(Collectors.toList());
    }

    public List<CompetitionResult> getRemainingContestantsByRound(Integer roundNumber) {
        return this.dataService.getResults().stream()
                .filter(result -> result.getRoundNumber().equals(roundNumber))
                .sorted(Comparator.comparing(CompetitionResult::getContestantKey))
                .collect(Collectors.toList());
    }
}
