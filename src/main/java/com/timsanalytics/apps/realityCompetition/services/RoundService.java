package com.timsanalytics.apps.realityCompetition.services;

import com.timsanalytics.apps.realityCompetition.beans.*;
import com.timsanalytics.apps.realityCompetition.data.DataService_BB22;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoundService {
    private final DataService_BB22 dataService;
    private final ResultService resultService;

    @Autowired
    public RoundService(DataService_BB22 dataService,
                        ResultService resultService
    ) {
        this.dataService = dataService;
        this.resultService = resultService;
    }

    public List<Round> getRoundList() {
        return this.dataService.defineRounds().stream()
                .sorted(Comparator.comparing(Round::getRoundNumber))
                .collect(Collectors.toList());
    }

    public Round getRound(Integer roundNumber) {
        return this.dataService.defineRounds().stream()
                .filter(round -> round.getRoundNumber().equals(roundNumber))
                .findFirst()
                .orElse(null);
    }

    public Integer getLastPlayedRoundNumber() {
        return this.resultService.getResultList().stream()
                .map(Result::getRoundNumber)
                .max(Comparator.comparing(x -> x))
                .orElse(null);
    }

    // VALIDATIONS

    public Boolean isRoundNumberValid(int roundNumber) {
        return roundNumber > 0 && this.dataService.defineRounds().stream()
                .anyMatch(round -> round.getRoundNumber().equals(roundNumber));
    }
}
