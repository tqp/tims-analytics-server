package com.timsanalytics.apps.realityCompetition.services;


import com.timsanalytics.apps.realityCompetition.beans.*;
import com.timsanalytics.apps.realityCompetition.data.DataService_BB22;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final DataService_BB22 dataService;

    @Autowired
    public UserService(DataService_BB22 dataService) {
        this.dataService = dataService;
    }

    public List<User> getUserList() {
        return this.dataService.defineUsers().stream()
                .sorted(Comparator.comparing(User::getUserKey))
                .collect(Collectors.toList());
    }

    public User getUser(String userKey) {
        return this.dataService.defineUsers().stream()
                .filter(user -> user.getUserKey().equalsIgnoreCase(userKey))
                .findFirst()
                .orElse(null);
    }

    // WITH PARAMETERS

    public List<User> getUserListByTeamKey(String teamKey) {
        return this.dataService.definePicks().stream()
                .filter(pick -> pick.getTeamKey().equalsIgnoreCase(teamKey))
                .map(Pick::getUserKey)
                .distinct()
                .map(this::getUser)
                .sorted(Comparator.comparing(User::getUserKey))
                .collect(Collectors.toList());
    }
}
