package com.timsanalytics.apps.realityTracker.controllers;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.apps.realityTracker.beans.Contestant;
import com.timsanalytics.apps.realityTracker.beans.ServerSidePaginationResponse;
import com.timsanalytics.apps.realityTracker.services.ContestantService;
import com.timsanalytics.common.beans.ServerSidePaginationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Contestant", tags = {"Contestant"}, description = "Create Contestant", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Contestant> createContestant(@RequestBody Contestant contestant) {
        try {
            return ResponseEntity.ok()
                    .body(contestantService.createContestant(contestant));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/ssp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Contestant List (SSP)", tags = {"Contestant"}, description = "Get Contestant List (SSP)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ServerSidePaginationResponse<Contestant>> getContestantList_SSP(@RequestBody ServerSidePaginationRequest serverSidePaginationRequest) {
        long startTime = new Date().getTime();
        try {
            ServerSidePaginationResponse<Contestant> container = this.contestantService.getContestantList_SSP(serverSidePaginationRequest);
            container.setRequestTime(new Date().getTime() - startTime);
            return ResponseEntity.ok()
                    .body(container);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = "/{contestantGuid}", method = RequestMethod.GET)
    @Operation(summary = "Get Contestant Detail", tags = {"Contestant"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Contestant> getContestantDetail(@Parameter(description = "Contestant GUID", required = true) @PathVariable String contestantGuid) {
        try {
            Contestant contestant = contestantService.getContestantDetail(contestantGuid);
            return ResponseEntity.ok()
                    .body(contestant);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Contestant", tags = {"Contestant"}, description = "Update Contestant", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Contestant> updateContestant(@RequestBody Contestant contestant) {
        try {
            return ResponseEntity.ok()
                    .body(contestantService.updateContestant(contestant));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{contestantGuid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete Contestant", tags = {"Contestant"}, description = "Delete Contestant", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<KeyValue> deleteContestant(@Parameter(description = "Contestant GUID", required = true) @PathVariable String contestantGuid) {
        try {
            return ResponseEntity.ok()
                    .body(contestantService.deleteContestant(contestantGuid));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
