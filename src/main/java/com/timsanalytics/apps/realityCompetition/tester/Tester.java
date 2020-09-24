package com.timsanalytics.apps.realityCompetition.tester;

import com.timsanalytics.apps.realityCompetition.data.DataService_BB22;
import com.timsanalytics.apps.realityCompetition.services.*;
import com.timsanalytics.common.utils.PrintObjectService;

public class Tester {
    public static void main(String[] args) {
        PrintObjectService printObjectService = new PrintObjectService();
        DataService_BB22 dataService = new DataService_BB22();

        TeamService teamService = new TeamService(dataService);
        CompetitionUserService userService = new CompetitionUserService(dataService);
        CompetitionContestantService contestantService = new CompetitionContestantService(dataService);
        ResultService resultService = new ResultService(dataService);
        RoundService roundService = new RoundService(dataService, resultService);
        PickService pickService = new PickService(dataService, roundService, resultService);
        PickResultService pickResultService = new PickResultService(pickService, resultService, roundService);
        ChartService chartService = new ChartService(roundService, contestantService, pickResultService, pickService, resultService);
        ScoreService scoreService = new ScoreService(pickResultService, userService, roundService);
        ProjectedScoreService projectedScoreService = new ProjectedScoreService(pickService, pickResultService, userService, roundService, printObjectService);
        BestPickService bestPickService = new BestPickService(
                resultService, roundService, projectedScoreService, pickService, userService, printObjectService, pickResultService
        );

        String teamKey = "key_team2";
        String userKey = "key_user1";
        printObjectService.PrintObject("\nResult", chartService.getChartByPosition(teamKey, userKey));
    }


}
