package com.timsanalytics.apps.realityTracker.competition.services;

import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionContestant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompetitionContestantService {
    private DataService dataService;

    @Autowired
    public CompetitionContestantService(DataService dataService) {
        this.dataService = dataService;
    }

    public List<CompetitionContestant> getContestantList() {
        return new ArrayList<>(dataService.getContestants());
    }

    public CompetitionContestant getContestantByContestantKey(String contestantKey) {
        return dataService.getContestants().stream()
                .filter(competitionContestant -> competitionContestant.getContestantKey().equalsIgnoreCase(contestantKey))
                .findFirst()
                .orElse(null);
    }
}
