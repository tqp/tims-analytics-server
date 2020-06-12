package com.timsanalytics.auth.authGoogle.controllers;

import com.timsanalytics.auth.authCommon.services.AuthenticationException;
import com.timsanalytics.auth.authGoogle.beans.GoogleAuthConfig;
import com.timsanalytics.auth.authGoogle.services.GoogleAuthService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/google-auth")
@Tag(name = "Authentication - Google", description = "Authentication - Google")
public class GoogleAuthController {
    private GoogleAuthService googleAuthService;

    @Autowired
    public GoogleAuthController(GoogleAuthService googleAuthService) {
        this.googleAuthService = googleAuthService;
    }

    @RequestMapping(value = "/get-google-auth-config", method = RequestMethod.GET)
    @Operation(summary = "Get Google Authentication Configuration", description = "Get Google Authentication Configuration", tags = {"Authentication - Google"})
    public GoogleAuthConfig getGoogleAuthConfig() {
        return this.googleAuthService.getGoogleAuthConfig();
    }

    @RequestMapping(value = "/response", method = RequestMethod.GET)
    @Operation(summary = "Receive Google Authorization Code", description = "Receive Google Authorization Code", tags = {"Authentication - Google"})
    public void receiveGoogleAuthorizationCode(@RequestParam(name = "code", required = false) String code, HttpServletResponse response) throws Exception {
        this.googleAuthService.receiveGoogleAuthorizationCode(code, response);
    }

    // Exception Handling
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    // Sample Code
    @RequestMapping(value = "/redirect", method = RequestMethod.POST)
    @Hidden
    @Operation(summary = "Redirect", description = "Redirect", tags = {"Authentication - Google"})
    public void redirect(HttpServletResponse response) throws Exception {
        response.sendRedirect("http://cnn.com");
    }

}
