package org.vcpl.triton.scheduler.service;

import org.vcpl.triton.scheduler.data.DeferralReportData;

import javax.mail.MessagingException;
import java.util.List;

public interface IScheduler {
    List<DeferralReportData> sendMailforPendingData() throws MessagingException;
}
