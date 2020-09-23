package com.timsanalytics.apps.realityCompetition.services;

import com.timsanalytics.apps.realityCompetition.beans.*;
import com.timsanalytics.apps.realityCompetition.tester.Tester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ChartService {
    private final RoundService roundService;
    private final ContestantService contestantService;
    private final PickResultService pickResultService;
    private final PickService pickService;
    private final ResultService resultService;

    @Autowired
    public ChartService(RoundService roundService,
                        ContestantService contestantService,
                        PickResultService pickResultService,
                        PickService pickService,
                        ResultService resultService
    ) {
        this.roundService = roundService;
        this.contestantService = contestantService;
        this.pickResultService = pickResultService;
        this.pickService = pickService;
        this.resultService = resultService;
    }

    public static void main(String[] args) {
        Tester.main(null);
    }

    public List<Chart> getChartByPosition(String teamKey, String userKey) {
        List<Pick> pickList = this.pickService.getPickListByTeamUser(teamKey, userKey);
        int roundsAlreadyPlayed = this.roundService.getLastPlayedRoundNumber();

        // Get potential remaining round count
        long roundsRemaining = this.resultService.getResultList().stream()
                .filter(result -> result.getRoundNumber().equals(this.roundService.getLastPlayedRoundNumber()))
                .count();

        System.out.println("roundsAlreadyPlayed: " + roundsAlreadyPlayed);
        System.out.println("roundsRemaining    : " + roundsRemaining);

        AtomicInteger i = new AtomicInteger(1);
        pickList.forEach(pick -> {
            for (int j = 1; j < roundsAlreadyPlayed + roundsRemaining; j++) {
                System.out.printf("%-3d| %-15s| %-10s| %-10s\n",
                        i.get(),
                        pick.getContestantKey(),
                        "Round " + j,
                        this.pickResultService.getPickResult(teamKey, userKey, i.get(), j).getStatus()
                );
            }
            i.getAndIncrement();
        });
        return null;
    }
}
