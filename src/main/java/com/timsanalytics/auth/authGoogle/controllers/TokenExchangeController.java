package com.timsanalytics.auth.authGoogle.controllers;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.auth.authGoogle.beans.TokenExchange;
import com.timsanalytics.auth.authGoogle.services.TokenExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/token-exchange")
@Tag(name = "Token Exchange", description = "Token Exchange")
public class TokenExchangeController {
    private final TokenExchangeService tokenExchangeService;

    @Autowired
    public TokenExchangeController(TokenExchangeService tokenExchangeService) {
        this.tokenExchangeService = tokenExchangeService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Operation(summary = "Add Item", description = "Add Item", tags = {"Token Exchange"})
    public void addItem(@RequestBody final TokenExchange item) {
        this.tokenExchangeService.addItem(item.getKey(), item.getValue());
    }

    @RequestMapping(value = "/remove/{key}", method = RequestMethod.DELETE)
    @Operation(summary = "Remove Item", description = "Remove Item", tags = {"Token Exchange"})
    public void removeItem(@PathVariable String key) {
        this.tokenExchangeService.removeItem(key);
    }

    @RequestMapping(value = "/item/{key}", method = RequestMethod.GET)
    @Operation(summary = "Get Item", description = "Get Item", tags = {"Token Exchange"})
    public String getItem(@PathVariable String key) {
        return this.tokenExchangeService.getItem(key);
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    @Operation(summary = "Get Item List", description = "Get Item List", tags = {"Token Exchange"})
    public List<KeyValue> getAllItem() {
        return this.tokenExchangeService.getAllItems();
    }

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @Operation(summary = "Generate Short-Lived Token", description = "Generate Short-Lived Token", tags = {"Token Exchange"})
    public String generateShortLivedToken() {
        return "redirect:/exchange?token=" + this.tokenExchangeService.generateShortLivedToken();
    }

    @RequestMapping(value = "/exchange", method = RequestMethod.POST)
    @Operation(summary = "Exchange Token", description = "Exchange Token", tags = {"Token Exchange"})
    public TokenExchange exchangeToken(@RequestBody TokenExchange shortLivedToken) {
        return this.tokenExchangeService.exchangeToken(shortLivedToken);
    }
}
