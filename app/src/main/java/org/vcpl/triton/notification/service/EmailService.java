package org.vcpl.triton.notification.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.vcpl.triton.notification.entity.EmailNotificationTemplateEntity;
import org.vcpl.triton.notification.repository.EmailNotificationTemplateRepository;
import org.vcpl.triton.notification.util.EmailUtil;

import org.vcpl.triton.scheduler.data.DeferralReportData;
import org.vcpl.triton.scheduler.data.OtherDeferralReportData;
import org.vcpl.triton.security.config.DatasourceForDbConnect;

@Slf4j
public class EmailService implements EmailUtil {

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    public EmailService(DatasourceForDbConnect data){
        this.fromName = data.getMailUsername();
    }

//    @Value("${spring.mail.username}")
    private final String fromName;

    @Autowired
    private EmailNotificationTemplateRepository emailNotificationTemplateRepository;


    public void sendEmail(String userEmail, EmailNotificationTemplateEntity emailNotificationTemplateEntity, DeferralReportData deferralReportData) throws MessagingException {
        log.info("Sending emails...");
        final AtomicInteger counter = new AtomicInteger(0);
        try {
            Context context = new Context();
            Map<String, Object> map = new HashMap<>();
            map.put("name", userEmail.trim());
            map.put("content",emailNotificationTemplateEntity.getContent());
            map.put("subject",emailNotificationTemplateEntity.getSubject());
            map.put("entityType",deferralReportData.getApplicationEntity().getType()==1?"Anchor":"Counterparty");
            map.put("entityName",deferralReportData.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
            map.put("rmName",emailRemoveSpaceAndSpacelChar(deferralReportData.getRmName()==null?"":deferralReportData.getRmName().trim()));
            map.put("fileName",deferralReportData.getDocumentListEntity().getDisplayName());
            map.put("revisedTime",deferralReportData.getRevisedTime()==null?deferralReportData.getInitialTime(): deferralReportData.getRevisedTime());
            context.setVariables(map);
            String process = springTemplateEngine.process("notification_scheduler", context);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject(emailNotificationTemplateEntity.getSubject());
            helper.setText(process, true);
            helper.setTo(userEmail);
            helper.setFrom(fromName);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | MailException ex) {
            log.error("Exception occured while sending email to: {} , due to: {}", userEmail , ex.getMessage());
            counter.incrementAndGet();
            ex.printStackTrace();
        }
        if (counter.intValue() > 0) {
            counter.intValue();
        }
    }

    public void sendEmail(String userEmail, EmailNotificationTemplateEntity emailNotificationTemplateEntity, OtherDeferralReportData deferralReportData) throws MessagingException {
        log.info("Sending emails...");
        final AtomicInteger counter = new AtomicInteger(0);
        try {
            Context context = new Context();
            Map<String, Object> map = new HashMap<>();
            map.put("name", userEmail.trim());
            map.put("content",emailNotificationTemplateEntity.getContent());
            map.put("subject",emailNotificationTemplateEntity.getSubject());
            map.put("entityType",deferralReportData.getApplicationEntity().getType()==1?"Anchor":"Counterparty");
            map.put("entityName",deferralReportData.getApplicationEntity().getCustomerInfoEntity().getCustomerName());
            map.put("rmName",emailRemoveSpaceAndSpacelChar(deferralReportData.getRmName()==null?"":deferralReportData.getRmName().trim()));
            map.put("fileName",deferralReportData.getDisplayName());
            map.put("revisedTime",deferralReportData.getRevisedTime()==null?deferralReportData.getInitialTime(): deferralReportData.getRevisedTime());
            context.setVariables(map);
            String process = springTemplateEngine.process("notification_scheduler", context);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject(emailNotificationTemplateEntity.getSubject());
            helper.setText(process, true);
            helper.setTo(userEmail);
            helper.setFrom(fromName);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | MailException ex) {
            log.error("Exception occured while sending email to: {} , due to: {}", userEmail , ex.getMessage());
            counter.incrementAndGet();
            ex.printStackTrace();
        }
        if (counter.intValue() > 0) {
            counter.intValue();
        }
    }


    @Override
    public String sendEmail1(Integer appId,String anchorName,String sender,String nextApprover) {
        log.info("Sending emails...");
        final AtomicInteger counter = new AtomicInteger(0);
        EmailNotificationTemplateEntity ent=null;
        try {
            /*Optional<EmailNotificationTemplateEntity> et=emailNotificationTemplateRepository.getTemplateByAction(Long.valueOf(1));
            if(et.isPresent()){
                ent=et.get();
            }*/
            EmailNotificationTemplateEntity et=emailNotificationTemplateRepository.findNotificationBasedOneActionAndStatus("2","A2");
            Context context = new Context();
            Map<String,Object>map = new HashMap<>();
            map.put("anchorName", anchorName);
            map.put("content",changeContent(et.getContent(),anchorName,sender,nextApprover));
            map.put("date", LocalDate.now());
            map.put("appId", appId);
            map.put("sender",sender);
            map.put("applicantName",anchorName);


            context.setVariables(map);
            String process = springTemplateEngine.process("approval", context);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            String subject = StringUtils
                    .join(Arrays.asList(ent.getSubject(), anchorName), ' ');
            helper.setSubject(subject);
            helper.setText(process, true);
            helper.setTo(sender);
            helper.setFrom(fromName);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | MailException ex) {
            log.error("Exception occured while sending email to: {} , due to: {}", sender
                    , ex.getMessage());
            counter.incrementAndGet();
            ex.printStackTrace();
        }
        if (counter.intValue() > 0) {
            return counter.intValue() + " email(s) sending failed. Please verify logs...!!!";
        }
        return "Email(s) sent successfully, Please check your inbox...!!!";
    }

    public String changeContent(String content,String anchorName,String currentApprover,String nextApprover){
    try {
        if (content.contains("<Anchor/CP>")) {
            content = content.replaceAll("<Anchor/CP>", anchorName);
        }
        if (content.contains("<CurrentApproverName>")){}
        if (content.contains("<Submitted to Name>")) {
            content = content.replaceAll("<Submitted to Name>", nextApprover);
        }
        if (content.contains("<Returned by Name>")) {
            content = content.replaceAll("<Returned by Name>", nextApprover);
        }
        if (content.contains("<Returned to Name>")) {
            content = content.replaceAll("<Returned to Name>", nextApprover);
        }
        if (content.contains("<Approved by Name>")) {
            content = content.replaceAll("<Approved by Name>", nextApprover);
        }

        if (content.contains("<Submitted by Name>")) {
           /* String[] currentApprover2 = currentApprover.split("@", 2);
            String currentApproverName = currentApprover2[0];
            currentApprover=currentApproverName.substring(0, 1).toUpperCase() + currentApproverName.substring(1);
            String currentApproverWithSpace = currentApprover.replace(".", " ");
            String words[]=currentApproverWithSpace.split("\\s");
            String nextApproverNameCapitalizeWord="";
            for(String w:words){
                String first=w.substring(0,1);
                String afterfirst=w.substring(1);
                nextApproverNameCapitalizeWord+=first.toUpperCase()+afterfirst+" ";
            }*/
            content = content.replaceAll("<Submitted by Name>",nextApprover);
        }


    }catch (Exception e){
        e.printStackTrace();
    }
        return content;
    }


    public String emailRemoveSpaceAndSpacelChar(String email){
        if(email!=null && email.length()>0) {
            email=email.trim();
            String[] userEmailstr = email.split("@", 2);
            String userEmailstrName = userEmailstr[0];
            userEmailstrName = userEmailstrName.replace(".", " ");
            String words[] = userEmailstrName.split("\\s");
            String capitalizeWord = "";
            for (String w : words) {
                String first = w.substring(0, 1);
                String afterfirst = w.substring(1);
                capitalizeWord += first.toUpperCase() + afterfirst + " ";
            }
            return capitalizeWord.trim();
        }else {
            return "";
        }
    }

    public String sendEmail(Long appId,String anchorName,String userEmail,String nextApprover,Long action,String stage) {
        log.info("Sending email for the Customer [{}] and Action {}",anchorName,action) ;
        final AtomicInteger counter = new AtomicInteger(0);
         EmailNotificationTemplateEntity ent=null;
        try {
               /* Optional<EmailNotificationTemplateEntity> et = emailNotificationTemplateRepository.findById(Long.valueOf(1));
                if (et.isPresent()) {
                    ent = et.get();
                }*/
            EmailNotificationTemplateEntity et=emailNotificationTemplateRepository.findNotificationBasedOneActionAndStatus(String.valueOf(action),stage);
            Context context = new Context();
            Map<String, Object> map = new HashMap<>();
            map.put("sender", emailRemoveSpaceAndSpacelChar(userEmail));
            if(nextApprover.length()>0) {
                map.put("content", changeContent(et.getContent(), anchorName, userEmail, emailRemoveSpaceAndSpacelChar(nextApprover)));
            }else{
                map.put("content", changeContent(et.getContent(), anchorName, userEmail,""));
            }
            map.put("appId",appId);
            map.put("anchorName",anchorName);
            map.put("date", LocalDate.now());

            context.setVariables(map);
            String process = springTemplateEngine.process("approval", context);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            String subject = StringUtils
                    .join(Arrays.asList("SCF-User",et.getSubject(),anchorName, "!!!"), ' ');
            helper.setSubject(subject);
            helper.setText(process, true);
            helper.setTo(userEmail);
            helper.setFrom(fromName);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | MailException ex) {
            log.error("Exception occured while sending email to: {} , due to: {}", userEmail
                    , ex.getMessage());
            counter.incrementAndGet();
            ex.printStackTrace();
        }catch (Exception e){
            log.error("Exception occured while sending email to: {} , due to: {}", userEmail
                    , e.getMessage());
            e.printStackTrace();
            counter.incrementAndGet();
        }
        if (counter.intValue() > 0) {
            return counter.intValue() + " email(s) sending failed. Please verify logs...!!!";
        }
        return "Email(s) sent successfully, Please check your inbox...!!!";
    }


    public void sendSimpleEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}