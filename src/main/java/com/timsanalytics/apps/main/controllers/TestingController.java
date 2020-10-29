package com.timsanalytics.apps.main.controllers;

import com.timsanalytics.apps.main.beans.Person;
import com.timsanalytics.apps.main.services.PersonService;
import com.timsanalytics.apps.main.services.TestingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
@Tag(name = "Test", description = "Test")
public class TestingController {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final TestingService testingService;

    @Autowired
    public TestingController(TestingService testingService) {
        this.testingService = testingService;
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Send Text Message", description = "Send Text Message", tags = {"Test"}, security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<String> sendTextMessage() {
        try {
            return ResponseEntity.ok()
                    .body(testingService.sendTextMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
