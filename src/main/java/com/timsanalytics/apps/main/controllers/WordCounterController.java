package com.timsanalytics.apps.main.controllers;

import com.timsanalytics.apps.main.services.WordCountService;
import com.timsanalytics.common.beans.KeyValueLong;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/word-counter")
@Tag(name = "Word Counter", description = "Word Counter")
public class WordCounterController {
    private final WordCountService wordCountService;

    @Autowired
    public WordCounterController(WordCountService wordCountService) {
        this.wordCountService = wordCountService;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    @Operation(summary = "Word Counter", tags = {"Word Counter"}, security = @SecurityRequirement(name = "bearerAuth"))
    public List<KeyValueLong> getWordCountList() {
        return this.wordCountService.getWordCountList();
    }
}
