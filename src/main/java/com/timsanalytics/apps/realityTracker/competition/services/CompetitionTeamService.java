package com.timsanalytics.apps.realityTracker.competition.services;

import com.timsanalytics.apps.realityTracker.competition.beans.CompetitionTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompetitionTeamService {
    private CompetitionDataService dataService;

    @Autowired
    public CompetitionTeamService(CompetitionDataService dataService) {
        this.dataService = dataService;
    }

    public List<CompetitionTeam> getTeamList() {
        return new ArrayList<>(this.dataService.getTeams());
    }

    public CompetitionTeam getTeamByTeamKey(String teamKey) {
        return this.dataService.getTeams().stream()
                .filter(team -> team.getTeamKey().equalsIgnoreCase(teamKey))
                .findFirst()
                .orElse(null);
    }
}
