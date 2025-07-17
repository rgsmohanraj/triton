package org.vcpl.triton.scheduler.controller;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vcpl.triton.notification.entity.EmailNotificationTemplateEntity;
import org.vcpl.triton.scheduler.data.DeferralReportData;
import org.vcpl.triton.scheduler.service.SchedulerService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@RestController
@RequestMapping("scheduler")
@Slf4j
public class SchedulerController implements Job{
    Boolean flag=true;
    @Autowired
    private SchedulerService schedulerService;

//    private final String checkDeferralCron="0 0 16-23/1 * * *";
    private final String deferralMailTiming = "0 9 * * * ?"; //"0 9 * * *"

    //    @Scheduled(cron = "0 0 16-23/1 * * *")
//    @Scheduled(cron = checkDeferralCron)
    public void checkDeferralData(){
        System.out.println("Running scheduled task within the range of 4 PM to 12 AM...");
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/sendMailforPendingData",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeferralReportData> sendMailforPendingData() throws MessagingException {
        log.info("send Mail for PendingData");
        return schedulerService.sendMailforPendingData();
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/initiateCpWorkFlow",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void initiateCpWorkFlow() throws MessagingException, UnsupportedEncodingException {
        log.info("Initiate CP WorkFlow");
        schedulerService.initiateCpWorkFlow();
    }

    
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/getEmailNotificationData",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Integer, EmailNotificationTemplateEntity> getEmailNotificationData() throws MessagingException {
        log.info("send Mail for PendingData");
        return schedulerService.getEmailNotificationData();
    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a <code>{@link Trigger}</code>
     * fires that is associated with the <code>Job</code>.
     * </p>
     *
     * <p>
     * The implementation may wish to set a
     * {@link JobExecutionContext#setResult(Object) result} object on the
     * {@link JobExecutionContext} before this method exits.  The result itself
     * is meaningless to Quartz, but may be informative to
     * <code>{@link JobListener}s</code> or
     * <code>{@link TriggerListener}s</code> that are watching the job's
     * execution.
     * </p>
     *
     * @param context
     * @throws JobExecutionException if there is an exception while executing the job.
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            log.info("send Mail for execute method");
            int currentHour = java.time.LocalTime.now().getHour();
            if(currentHour == 9) {
                try {
                    sendMailforPendingData();
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    schedulerService.initiateCpWorkFlow();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            log.error("Exception occured while execute to: {} , due to: {}",e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
