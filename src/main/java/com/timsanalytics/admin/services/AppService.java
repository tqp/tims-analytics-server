package com.timsanalytics.admin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class AppService {
    private final Environment environment;

    @Autowired
    public AppService(Environment environment) {
        this.environment = environment;
    }

    public String getBuildTimestamp() {
        return this.environment.getProperty("application.buildTimestamp");
    }
}
