package com.timsanalytics.main.realityTracker.controllers;

import com.timsanalytics.main.realityTracker.beans.ServerSidePaginationResponseContestant;
import com.timsanalytics.main.realityTracker.beans.ServerSidePaginationResponseSeries;
import com.timsanalytics.main.realityTracker.services.ContestantService;
import com.timsanalytics.main.thisApp.beans.ServerSidePaginationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@RestController
@RequestMapping("/reality-tracker/api/v1/contestant")
@Tag(name = "Contestant", description = "Contestant")
public class ContestantController {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final ContestantService contestantService;

    @Autowired
    public ContestantController(ContestantService contestantService) {
        this.contestantService = contestantService;
    }

    @ResponseBody
    @RequestMapping(value = "/ssp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Person List (SSP)", tags = {"Person"}, description = "Get Person List (SSP)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ServerSidePaginationResponseContestant> getPersonList_SSP(@RequestBody ServerSidePaginationRequest serverSidePaginationRequest) {
        long startTime = new Date().getTime();
        try {
            ServerSidePaginationResponseContestant container = this.contestantService.getContestantList_SSP(serverSidePaginationRequest);
            container.setRequestTime(new Date().getTime() - startTime);
            return ResponseEntity.ok()
                    .body(container);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
