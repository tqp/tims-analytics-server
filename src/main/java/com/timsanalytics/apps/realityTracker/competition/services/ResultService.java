package com.timsanalytics.apps.realityTracker.competition.services;

import com.timsanalytics.apps.realityTracker.competition.beans.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultService {
    private DataService dataService;

    @Autowired
    public ResultService(DataService dataService) {
        this.dataService = dataService;
    }

    public List<Result> getResultList() {
        return this.dataService.getResults().stream()
                .sorted(Comparator.comparing(Result::getRoundNumber)
                        .thenComparing(Result::getCallOutOrder)
                        .thenComparing(Result::getContestantKey))
                //.peek(result -> System.out.println(result.getContestantKey()))
                .collect(Collectors.toList());
    }

    public List<Result> getResultListByRound(Integer roundNumber) {
        return this.dataService.getResults().stream()
                .filter(result -> result.getRoundNumber().equals(roundNumber))
                .sorted(Comparator.comparing(Result::getCallOutOrder)
                        .thenComparing(Result::getContestantKey))
                //.peek(result -> System.out.println(result.getContestantKey()))
                .collect(Collectors.toList());
    }

    public List<Result> getRemainingContestantsByRound(Integer roundNumber) {
        return this.dataService.getResults().stream()
                .filter(result -> result.getRoundNumber().equals(roundNumber))
                .sorted(Comparator.comparing(Result::getContestantKey))
                .collect(Collectors.toList());
    }
}
