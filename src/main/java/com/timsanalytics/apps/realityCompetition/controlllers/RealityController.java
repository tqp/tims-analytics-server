package com.timsanalytics.apps.realityCompetition.controlllers;

import com.timsanalytics.apps.realityCompetition.beans.BestPick;
import com.timsanalytics.apps.realityCompetition.beans.Chart;
import com.timsanalytics.apps.realityCompetition.beans.Score;
import com.timsanalytics.apps.realityCompetition.services.BestPickService;
import com.timsanalytics.apps.realityCompetition.services.ChartService;
import com.timsanalytics.apps.realityCompetition.services.ProjectedScoreService;
import com.timsanalytics.apps.realityCompetition.services.ScoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reality")
@Tag(name = "Reality", description = "Reality Tracker")
public class RealityController {
    private final ScoreService scoreService;
    private final ProjectedScoreService projectedScoreService;
    private final BestPickService bestPickService;
    private final ChartService chartService;

    @Autowired
    public RealityController(ScoreService scoreService,
                             ProjectedScoreService projectedScoreService,
                             BestPickService bestPickService,
                             ChartService chartService) {
        this.scoreService = scoreService;
        this.projectedScoreService = projectedScoreService;
        this.bestPickService = bestPickService;
        this.chartService = chartService;
    }

//    @ResponseBody
//    @GetMapping(value = "/table-data", produces = "application/json")
//    @Operation(summary = "getTableData", tags = {"Reality"}, description = "getTableData")
//    public RealityTable getTableData() throws Exception {
//        return this.realityService.getTableData();
//    }

    @ResponseBody
    @RequestMapping(value = "/my-current-score/{teamKey}/{userKey}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get My Current Score", tags = {"Reality"}, description = "Get My Current Score", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Score> getMyCurrentScore(@Parameter(description = "Team Key", required = true) @PathVariable String teamKey,
                                                   @Parameter(description = "User Key", required = true) @PathVariable String userKey) {
        try {
            Score score = this.scoreService.getScoreByTeamUser(teamKey, userKey);
            return ResponseEntity.ok()
                    .body(score);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/my-projected-score/{teamKey}/{userKey}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get My Projected Score", tags = {"Reality"}, description = "Get My Projected Score", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Score> getMyProjectedScore(@Parameter(description = "Team Key", required = true) @PathVariable String teamKey,
                                                     @Parameter(description = "User Key", required = true) @PathVariable String userKey) {
        try {
            Score score = this.projectedScoreService.getProjectedScoreByTeamUser(teamKey, userKey);
            return ResponseEntity.ok()
                    .body(score);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/current-scores/{teamKey}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Current Scores", tags = {"Reality"}, description = "Get Current Scores", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Score>> getCurrentScoresByTeam(@Parameter(description = "Team Key", required = true) @PathVariable String teamKey) {
        try {
            List<Score> scoreList = this.scoreService.getScoreByTeam_GroupByUser(teamKey);
            return ResponseEntity.ok()
                    .body(scoreList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/projected-scores/{teamKey}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Projected Scores", tags = {"Reality"}, description = "Get Projected Scores", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Score>> getProjectedScoresByTeam(@Parameter(description = "Team Key", required = true) @PathVariable String teamKey) {
        try {
            List<Score> scoreList = this.projectedScoreService.getProjectedScoreByTeam_GroupByUser(teamKey);
            return ResponseEntity.ok()
                    .body(scoreList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/chart/{teamKey}/{userKey}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Chart Data", tags = {"Reality"}, description = "Get Chart Data", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Chart>> getChartPositionByTeamUser(@Parameter(description = "Team Key", required = true) @PathVariable String teamKey,
                                                                  @Parameter(description = "User Key", required = true) @PathVariable String userKey) {
        try {
            List<Chart> chartList = this.chartService.getChartByPositionByTeamUser(teamKey, userKey);
            return ResponseEntity.ok()
                    .body(chartList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/best-picks/{teamKey}/{userKey}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Best Picks", tags = {"Reality"}, description = "Best Picks", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<BestPick>> getBestPicks_RemainderOfGame(@Parameter(description = "Team Key", required = true) @PathVariable String teamKey,
                                                                       @Parameter(description = "User Key", required = true) @PathVariable String userKey) {
        try {
            List<BestPick> chartList = this.bestPickService.getBestPicks(teamKey, userKey, false);
            return ResponseEntity.ok()
                    .body(chartList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/best-picks-against/{teamKey}/{userKey}/{againstUserKey}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Best Picks", tags = {"Reality"}, description = "Best Picks", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<BestPick>> getBestPicksAgainst_RemainderOfGame(@Parameter(description = "Team Key", required = true) @PathVariable String teamKey,
                                                                              @Parameter(description = "User Key", required = true) @PathVariable String userKey,
                                                                              @Parameter(description = "Against User Key", required = true) @PathVariable String againstUserKey) {
        try {
            List<BestPick> chartList = this.bestPickService.getBestPicksAgainst(teamKey, userKey, againstUserKey, false);
            return ResponseEntity.ok()
                    .body(chartList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/root-to-stay/{teamKey}/{userKey}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Root to Stay", tags = {"Reality"}, description = "Root to Stay", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<BestPick>> getBestPicksRootToStay_RemainderOfGame(@Parameter(description = "Team Key", required = true) @PathVariable String teamKey,
                                                                                 @Parameter(description = "User Key", required = true) @PathVariable String userKey) {
        try {
            List<BestPick> chartList = this.bestPickService.getBestPicksRootToStay_RemainderOfGame(teamKey, userKey);
            return ResponseEntity.ok()
                    .body(chartList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/root-to-leave/{teamKey}/{userKey}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Root to Leave", tags = {"Reality"}, description = "Root to Leave", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<BestPick>> getBestPicksRootToLeave_RemainderOfGame(@Parameter(description = "Team Key", required = true) @PathVariable String teamKey,
                                                                                  @Parameter(description = "User Key", required = true) @PathVariable String userKey) {
        try {
            List<BestPick> chartList = this.bestPickService.getBestPicksRootToLeave_RemainderOfGame(teamKey, userKey);
            return ResponseEntity.ok()
                    .body(chartList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/inconsequential/{teamKey}/{userKey}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Inconsequential", tags = {"Reality"}, description = "Inconsequential", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<BestPick>> getBestPicksRootNoImpact_RemainderOfGame(@Parameter(description = "Team Key", required = true) @PathVariable String teamKey,
                                                                                   @Parameter(description = "User Key", required = true) @PathVariable String userKey) {
        try {
            List<BestPick> chartList = this.bestPickService.getBestPicksRootNoImpact_RemainderOfGame(teamKey, userKey);
            return ResponseEntity.ok()
                    .body(chartList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
