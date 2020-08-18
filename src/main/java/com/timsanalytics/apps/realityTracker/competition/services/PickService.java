package com.timsanalytics.apps.realityTracker.competition.services;

import com.timsanalytics.apps.realityTracker.competition.beans.Pick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PickService {
    private DataService dataService;
    private RoundService roundService;

    @Autowired
    public PickService(DataService dataService, RoundService roundService) {
        this.dataService = dataService;
        this.roundService = roundService;
    }

    private boolean getRoundCutoffSetting() {
        return true;
    }

    public List<Pick> getUserPicksByRound(String teamKey, String userKey, Integer roundNumber) {
        if (!this.roundService.isRoundNumberValid(roundNumber)) {
            throw new IllegalArgumentException("The round number is invalid.");
        } else {
            return this.dataService.getPicks().stream()
                    .filter(pick -> pick.getTeamKey().equalsIgnoreCase(teamKey)) // Filter picks by Team
                    .filter(pick -> pick.getUserKey().equalsIgnoreCase(userKey)) // Filter picks by User
                    .filter(pick -> isPickPositionValid(pick.getPickPosition(), roundNumber))
                    .sorted(Comparator.comparing(Pick::getPickPosition))
                    .peek(pick -> System.out.println(pick.getContestantKey()))
                    .collect(Collectors.toList());
        }
    }

    public Boolean isPickPositionValid(int pickPosition, int roundNumber) {
        if (this.getRoundCutoffSetting()) { // Only include pick positions within the pre-defined round cutoff counts.
            return pickPosition > 0 && pickPosition <= this.roundService.getRoundByRoundNumber(roundNumber).getRoundCutoffCount();
        } else {
            return pickPosition > 0 && pickPosition <= this.dataService.getResults().stream()
                    .filter(result -> result.getRoundNumber().equals(roundNumber))
                    .count();
        }
    }
}
