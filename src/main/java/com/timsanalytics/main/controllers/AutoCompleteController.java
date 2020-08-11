package com.timsanalytics.main.controllers;

import com.timsanalytics.main.beans.Person;
import com.timsanalytics.main.services.AutoCompleteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auto-complete")
@Tag(name = "Auto-Complete", description = "This is used for the Auto-Complete demo.")
public class AutoCompleteController {
    private Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final AutoCompleteService autoCompleteService;

    @Autowired
    public AutoCompleteController(AutoCompleteService autoCompleteService) {
        this.autoCompleteService = autoCompleteService;
    }

    @ResponseBody
    @RequestMapping(value = "/last-name", method = RequestMethod.GET, produces = "application/json")
    @Operation(summary = "Auto-Complete Last Name", tags = {"Sample Apps - Auto-Correct"}, security = @SecurityRequirement(name = "bearerAuth"))
    public List<Person> getAutoCompleteLastName(@RequestParam(value = "filter") String filter) {
        this.logger.debug("SampleDataController -> getAutoCompleteLastName: filter=" + filter);
        try {
            return this.autoCompleteService.getAutoCompleteLastName(filter);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/address", method = RequestMethod.GET, produces = "application/json")
    @Operation(summary = "Auto-Complete Address", tags = {"Sample Apps - Auto-Correct"}, security = @SecurityRequirement(name = "bearerAuth"))
    public List<Person> getAutoCompleteAddress(@RequestParam(value = "filter") String filter) {
        this.logger.debug("SampleDataController -> getAutoCompleteAddress: filter=" + filter);
        try {
            return this.autoCompleteService.getAutoCompleteAddress(filter);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
