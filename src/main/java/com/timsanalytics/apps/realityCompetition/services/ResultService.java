package com.timsanalytics.apps.realityCompetition.services;

import com.timsanalytics.apps.realityCompetition.beans.*;
import com.timsanalytics.apps.realityCompetition.data.DataService_BB22;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultService {
    private final DataService_BB22 dataService;

    @Autowired
    public ResultService(DataService_BB22 dataService) {
        this.dataService = dataService;
    }

    public List<Result> getResultList() {
        return this.dataService.defineResults().stream()
                .sorted(Comparator.comparing(Result::getRoundNumber)
                        .thenComparing(Result::getCallOutOrder)
                        .thenComparing(Result::getContestantKey)
                )
                .collect(Collectors.toList());
    }

    public Result getResult(Integer roundNumber, Integer callOutOrder) {
        return this.dataService.defineResults().stream()
                .filter(result -> result.getRoundNumber().equals(roundNumber))
                .filter(result -> result.getCallOutOrder().equals(callOutOrder))
                .findFirst()
                .orElse(null);
    }
}
