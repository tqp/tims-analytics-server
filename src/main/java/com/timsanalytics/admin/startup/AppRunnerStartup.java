package com.timsanalytics.admin.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunnerStartup implements ApplicationRunner {
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    public AppRunnerStartup() {
        this.logger.trace("AppRunnerStartup");
    }

    @Override
    public void run(ApplicationArguments args) {
        this.logger.trace("AppRunnerStartup -> Application started with command line arguments: {}.", args.getOptionNames());
    }
}
