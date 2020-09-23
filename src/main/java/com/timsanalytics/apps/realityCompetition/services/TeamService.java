package com.timsanalytics.apps.realityCompetition.services;

import com.timsanalytics.apps.realityCompetition.beans.*;
import com.timsanalytics.apps.realityCompetition.data.DataService_BB22;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private final DataService_BB22 dataService;

    @Autowired
    public TeamService(DataService_BB22 dataService) {
        this.dataService = dataService;
    }

    public List<Team> getTeamList() {
        return this.dataService.defineTeams().stream()
                .sorted(Comparator.comparing(Team::getTeamKey))
                .collect(Collectors.toList());
    }

    public Team getTeamByTeamKey(String teamKey) {
        return this.dataService.defineTeams().stream()
                .filter(team -> team.getTeamKey().equalsIgnoreCase(teamKey))
                .findFirst()
                .orElse(null);
    }
}
