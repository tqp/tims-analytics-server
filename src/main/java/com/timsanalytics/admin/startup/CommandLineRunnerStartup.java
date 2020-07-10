package com.timsanalytics.admin.startup;

import com.timsanalytics.admin.services.SchedulerService;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Order(1)
@Component
public class CommandLineRunnerStartup implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final SchedulerService schedulerService;

    @Autowired
    public CommandLineRunnerStartup(SchedulerService schedulerService, Scheduler scheduler) {
        this.logger.trace("CommandLineRunnerStart Component");
        this.schedulerService = schedulerService;
    }

    @Override
    public void run(String... args) {
        this.logger.debug("CommandLineAppStartupRunner -> Application started with command line arguments: {}.", Arrays.toString(args));

        // Start Scheduler
        this.logger.info("Starting Scheduler...");
        this.schedulerService.StartScheduler();

        // Run Selected Jobs at Startup
        this.logger.info("Running Selected Jobs at Startup...");
        this.schedulerService.RunSelectedJobsAtStartup();

    }
}
