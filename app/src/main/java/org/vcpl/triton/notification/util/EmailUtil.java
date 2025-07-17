package org.vcpl.triton.notification.util;

import org.vcpl.triton.notification.entity.EmailNotificationTemplateEntity;
import org.vcpl.triton.scheduler.data.DeferralReportData;

import javax.mail.MessagingException;

public interface EmailUtil {
    void sendEmail(String userEmail, EmailNotificationTemplateEntity emailNotificationTemplateEntity, DeferralReportData deferralReportData) throws MessagingException;

    String  sendEmail1(Integer stageId,String anchorName,String userEmail,String nextApprover);

    String changeContent(String content,String applicantName,String currentApprover,String nextApprover);

    String sendEmail(Long stageId, String anchorName, String userEmail, String nextApprover,Long action,String stage);
}
