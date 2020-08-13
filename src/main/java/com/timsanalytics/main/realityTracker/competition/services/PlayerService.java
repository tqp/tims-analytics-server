package com.timsanalytics.main.realityTracker.competition.services;

import com.timsanalytics.main.realityTracker.competition.beans.Pick;
import com.timsanalytics.main.realityTracker.competition.beans.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private com.timsanalytics.main.realityTracker.competition.services.DataService dataService;

    @Autowired
    public PlayerService(com.timsanalytics.main.realityTracker.competition.services.DataService dataService) {
        this.dataService = dataService;
    }

    public List<Player> getUserList() {
        return new ArrayList<>(this.dataService.getUsers());
    }

    public Player getUserByUserKey(String userKey) {
        return this.dataService.getUsers().stream()
                .filter(player -> player.getUserKey().equalsIgnoreCase(userKey))
                .findFirst()
                .orElse(null);
    }

    public List<Player> getUserListByTeamKey(String teamKey) {
        // Gets a list of Users for a given Team, using Picks
        return this.dataService.getPicks().stream()
                .filter(pick -> pick.getTeamKey().equalsIgnoreCase(teamKey))
                .map(Pick::getUserKey)
                .distinct()
                .map(this::getUserByUserKey)
                .collect(Collectors.toList());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
