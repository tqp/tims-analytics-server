package com.timsanalytics.main.realityTracker.competition.services;

import com.timsanalytics.main.realityTracker.competition.beans.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {
    private DataService dataService;

    @Autowired
    public TeamService(DataService dataService) {
        this.dataService = dataService;
    }

    public List<Team> getTeamList() {
        return new ArrayList<>(this.dataService.getTeams());
    }

    public Team getTeamByTeamKey(String teamKey) {
        return this.dataService.getTeams().stream()
                .filter(team -> team.getTeamKey().equalsIgnoreCase(teamKey))
                .findFirst()
                .orElse(null);
    }
}
