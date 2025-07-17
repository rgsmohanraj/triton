package org.vcpl.triton.scheduler.configuration;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vcpl.triton.scheduler.controller.SchedulerController;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail EmailTriggerJobDetail() {
        return JobBuilder.newJob(SchedulerController.class)
                .withIdentity("SchedulerController")
                .storeDurably()
                .build();
    }
    @Bean
    public Trigger sampleJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(EmailTriggerJobDetail())
                .withIdentity("sampleJobTrigger")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(9, 0))
                .build();
    }

}
