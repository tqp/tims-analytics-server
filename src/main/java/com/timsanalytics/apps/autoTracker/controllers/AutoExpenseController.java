package com.timsanalytics.apps.autoTracker.controllers;

import com.timsanalytics.apps.autoTracker.beans.Expense;
import com.timsanalytics.apps.autoTracker.services.AutoExpenseService;
import com.timsanalytics.common.beans.KeyValue;
import com.timsanalytics.common.beans.ServerSidePaginationRequest;
import com.timsanalytics.common.beans.ServerSidePaginationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auto-tracker/auto-expense")
@Tag(name = "Auto Tracker: Auto Expense", description = "Auto Tracker: Auto Expense")
public class AutoExpenseController {
    private final AutoExpenseService autoExpenseService;

    @Autowired
    public AutoExpenseController(AutoExpenseService autoExpenseService) {
        this.autoExpenseService = autoExpenseService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create Auto Expense", tags = {"Auto Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public Expense createAutoExpense(@RequestBody Expense expense) {
        try {
            return autoExpenseService.createAutoExpense(expense);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Auto Expense List (All)", tags = {"Auto Tracker"}, description = "Get Auto Expense List (All)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Expense>> getAutoExpenseList() {
        try {
            return ResponseEntity.ok()
                    .body(this.autoExpenseService.getAutoExpenseList());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/ssp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Auto Expense List (SSP)", tags = {"Auto Tracker"}, description = "Get Auto Expense List (SSP)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ServerSidePaginationResponse<Expense>> getAutoExpenseList_SSP(@RequestBody ServerSidePaginationRequest serverSidePaginationRequest) {
        long startTime = new Date().getTime();
        try {
            ServerSidePaginationResponse<Expense> container = this.autoExpenseService.getAutoExpenseList_SSP(serverSidePaginationRequest);
            container.setRequestTime(new Date().getTime() - startTime);
            return ResponseEntity.ok()
                    .body(container);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = "/{autoExpenseGuid}", method = RequestMethod.GET)
    @Operation(summary = "Get Auto Expense Detail", tags = {"Auto Tracker"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Expense> getAutoExpenseDetail(@Parameter(description = "Auto Expense GUID", required = true) @PathVariable String autoExpenseGuid) {
        try {
            Expense expense = autoExpenseService.getAutoExpenseDetail(autoExpenseGuid);
            return ResponseEntity.ok()
                    .body(expense);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Auto Expense", tags = {"Auto Tracker"}, description = "Update Auto Expense", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Expense> updateAutoExpense(@RequestBody Expense expense) {
        try {
            return ResponseEntity.ok()
                    .body(autoExpenseService.updateAutoExpense(expense));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "{autoExpenseGuid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete Auto Expense", tags = {"Auto Tracker"}, description = "Delete Auto Expense", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<KeyValue> deleteAutoExpense(@Parameter(description = "Auto Expense GUID", required = true) @PathVariable String autoExpenseGuid) {
        try {
            return ResponseEntity.ok()
                    .body(autoExpenseService.deleteAutoExpense(autoExpenseGuid));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
