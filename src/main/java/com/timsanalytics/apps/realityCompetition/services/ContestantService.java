package com.timsanalytics.apps.realityCompetition.services;


import com.timsanalytics.apps.realityCompetition.beans.*;
import com.timsanalytics.apps.realityCompetition.data.DataService_BB22;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContestantService {
    private final DataService_BB22 dataService;

    @Autowired
    public ContestantService(DataService_BB22 dataService) {
        this.dataService = dataService;
    }

    public List<Contestant> getContestantList() {
        return this.dataService.defineContestants().stream()
                .sorted(Comparator.comparing(Contestant::getContestantKey))
                .collect(Collectors.toList());
    }

    public Contestant getContestant(String contestantKey) {
        return dataService.defineContestants().stream()
                .filter(contestant -> contestant.getContestantKey().equalsIgnoreCase(contestantKey))
                .findFirst()
                .orElse(null);
    }

    public Contestant getContestantByTeamUserPosition(String teamKey, String userKey, int position) {
        return dataService.definePicks().stream()
                .filter(pick -> pick.getTeamKey().equals(teamKey))
                .filter(pick -> pick.getUserKey().equals(userKey))
                .filter(pick -> pick.getPosition().equals(position))
                .map(pick -> {
                    return this.getContestant(pick.getContestantKey());
                })
                .findFirst()
                .orElse(null);
    }
}
