package com.timsanalytics.auth.authInternal.beans;

import java.io.Serializable;

public class InternalAuthResponse implements Serializable {
    private static final long serialVersionUID = 42L;

    private final String token;

    public InternalAuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
