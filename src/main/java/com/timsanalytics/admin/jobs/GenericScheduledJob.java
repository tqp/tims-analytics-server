package com.timsanalytics.admin.jobs;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class GenericScheduledJob implements Job {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public JobDetail genericJob() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("name", "GenericScheduledJob");

        return JobBuilder.newJob(GenericScheduledJob.class)
                .withIdentity("GenericScheduledJob", "GenericScheduler")
                .withDescription("GenericScheduledJob")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    public Trigger genericTrigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "GenericScheduler")
                .withDescription("GenericTrigger")
                .withSchedule(
                        CronScheduleBuilder
                                // Cron: Second Minute Hour DayOfMonth Month DayOfWeek Year
                                .cronSchedule("0 0 * * * ?")
                                .withMisfireHandlingInstructionDoNothing()
                )
                .build();
    }

    public void execute(JobExecutionContext context) {
        this.logger.debug("Running Generic Job: " + sdf.format(new Date().getTime()));
        System.gc();
    }
}
