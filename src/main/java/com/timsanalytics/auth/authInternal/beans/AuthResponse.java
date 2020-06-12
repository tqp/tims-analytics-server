package com.timsanalytics.auth.authInternal.beans;

import java.io.Serializable;

public class AuthResponse implements Serializable {
    private static final long serialVersionUID = 42L;

    private final String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
