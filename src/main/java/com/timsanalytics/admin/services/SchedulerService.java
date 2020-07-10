package com.timsanalytics.admin.services;

import com.timsanalytics.admin.jobs.GenericScheduledJob;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final Scheduler scheduler;
    private final GenericScheduledJob genericScheduledJob;

    @Autowired
    public SchedulerService(Scheduler scheduler,
                             GenericScheduledJob genericScheduledJob
    ) {
        this.scheduler = scheduler;
        this.genericScheduledJob = genericScheduledJob;
    }

    public void StartScheduler() {
        this.logger.trace("SchedulerService -> StartScheduler");

        // Define Generic Job
        JobDetail genericJob = this.genericScheduledJob.genericJob();
        Trigger trigger = this.genericScheduledJob.genericTrigger(genericJob);

        try {
            scheduler.scheduleJob(genericJob, trigger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void RunSelectedJobsAtStartup() {
        try {
            scheduler.triggerJob(JobKey.jobKey("GenericScheduledJob", "GenericScheduler"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
