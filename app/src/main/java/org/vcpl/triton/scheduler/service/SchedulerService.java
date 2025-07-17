package org.vcpl.triton.scheduler.service;


import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.vcpl.triton.anchor.data.ApplicationData;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.repository.ApplicationRepository;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;
import org.vcpl.triton.anchor.service.ApplicationService;
import org.vcpl.triton.notification.entity.EmailNotificationTemplateEntity;
import org.vcpl.triton.notification.repository.EmailNotificationTemplateRepository;
import org.vcpl.triton.notification.service.EmailService;
import org.vcpl.triton.rbac.service.GroupService;
import org.vcpl.triton.scheduler.data.DeferralReportData;
import org.vcpl.triton.scheduler.data.OtherDeferralReportData;
import org.vcpl.triton.scheduler.repository.OtherDeferralReportRepository;
import org.vcpl.triton.scheduler.repository.SchedulerRepository;
import org.vcpl.triton.workflow.data.WFApprovalStatusData;
import org.vcpl.triton.workflow.entity.DeferralWorkflowEntity;
import org.vcpl.triton.workflow.repository.DeferralWorkflowStatusRepository;
import org.vcpl.triton.workflow.repository.WFApprovalStatusRepository;
import org.vcpl.triton.workflow.service.DeferralWorkflowService;
import org.vcpl.triton.workflow.service.WFApprovalStatusService;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class SchedulerService implements IScheduler{

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private DeferralWorkflowService deferralWorkflowService;

    @Autowired
    private EmailNotificationTemplateRepository emailNotificationTemplateRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private DeferralWorkflowStatusRepository deferralWorkflowStatusRepository;

    @Autowired
    private OtherDeferralReportRepository otherDeferralReportRepository;

    @Autowired
    private WFApprovalStatusService wfApprovalStatusService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private GroupService groupService;

    @Transactional
    public List<DeferralReportData> sendMailforPendingData() throws MessagingException {
        log.info("Send Mail for Pending Data in service layer");
        try {
            //Deferral Data Scheduler
            List<DeferralReportData> pendingDeferralData = this.schedulerRepository.view_deferral_document_report();
            Map<Integer, EmailNotificationTemplateEntity> emailTemplate = getEmailNotificationData();
            List<Long> containsAppId = new ArrayList<>();
            try {
                if(!pendingDeferralData.isEmpty()) {
                    for (DeferralReportData pendingDeferralDatas : pendingDeferralData) {
                        if (Math.abs(Integer.parseInt(pendingDeferralDatas.getDataView())) == 0) {
                            if (!containsAppId.contains(pendingDeferralDatas.getApplicationEntity().getId())) {
                                List<DeferralWorkflowEntity> deferralWorkflowEntities = this.deferralWorkflowStatusRepository.getInvolvingDeferralPersons(pendingDeferralDatas.getApplicationEntity().getId());
                                containsAppId.add(pendingDeferralDatas.getApplicationEntity().getId());
                                for (DeferralWorkflowEntity deferralWorkflow : deferralWorkflowEntities) {
                                    emailService.sendEmail(deferralWorkflow.getApproverInfo(), emailTemplate.get(0), pendingDeferralDatas);
                                }
                                if (pendingDeferralDatas.getRmName() != null) {
                                    emailService.sendEmail(pendingDeferralDatas.getRmName(), emailTemplate.get(0), pendingDeferralDatas);
                                }
                            }
                        } else if (Math.abs(Integer.parseInt(pendingDeferralDatas.getDataView())) == 1) {
                            if (!containsAppId.contains(pendingDeferralDatas.getApplicationEntity().getId())) {
                                List<DeferralWorkflowEntity> deferralWorkflowEntities = this.deferralWorkflowStatusRepository.getInvolvingDeferralPersons(pendingDeferralDatas.getApplicationEntity().getId());
                                containsAppId.add(pendingDeferralDatas.getApplicationEntity().getId());
                                for (DeferralWorkflowEntity deferralWorkflow : deferralWorkflowEntities) {
                                    emailService.sendEmail(deferralWorkflow.getApproverInfo(), emailTemplate.get(1), pendingDeferralDatas);
                                }
                                if (pendingDeferralDatas.getRmName() != null) {
                                    emailService.sendEmail(pendingDeferralDatas.getRmName(), emailTemplate.get(1), pendingDeferralDatas);
                                }
                            }
                        } else if (Math.abs(Integer.parseInt(pendingDeferralDatas.getDataView())) == 2 || Math.abs(Integer.parseInt(pendingDeferralDatas.getDataView())) == 5) {
                            if (!containsAppId.contains(pendingDeferralDatas.getApplicationEntity().getId())) {
                                List<DeferralWorkflowEntity> deferralWorkflowEntities = this.deferralWorkflowStatusRepository.getInvolvingDeferralPersons(pendingDeferralDatas.getApplicationEntity().getId());
                                containsAppId.add(pendingDeferralDatas.getApplicationEntity().getId());
                                for (DeferralWorkflowEntity deferralWorkflow : deferralWorkflowEntities) {
                                    emailService.sendEmail(deferralWorkflow.getApproverInfo(), emailTemplate.get(2), pendingDeferralDatas);
                                }
                                if (pendingDeferralDatas.getRmName() != null) {
                                    emailService.sendEmail(pendingDeferralDatas.getRmName(), emailTemplate.get(2), pendingDeferralDatas);
                                }
                            }
                        }
                    }
                }

                //Other Document

                List<OtherDeferralReportData> otherDeferralReportData = otherDeferralReportRepository.view_other_deferral_document_report();
                if(!otherDeferralReportData.isEmpty()){
                    for(OtherDeferralReportData otherDef : otherDeferralReportData)
                    {
                        if(Math.abs(Integer.parseInt(otherDef.getDataView())) == 0){
                            if(!containsAppId.contains(otherDef.getApplicationEntity().getId())){
                                List<DeferralWorkflowEntity> deferralWorkflowEntities = this.deferralWorkflowStatusRepository.getInvolvingDeferralPersons(otherDef.getApplicationEntity().getId());
                                containsAppId.add(otherDef.getApplicationEntity().getId());
                                for (DeferralWorkflowEntity deferralWorkflow : deferralWorkflowEntities) {
                                    emailService.sendEmail(deferralWorkflow.getApproverInfo(), emailTemplate.get(0), otherDef);
                                }
                                if (otherDef.getRmName() != null) {
                                    emailService.sendEmail(otherDef.getRmName(), emailTemplate.get(0), otherDef);
                                }
                            }
                        }
                        else if(Math.abs(Integer.parseInt(otherDef.getDataView())) == 1)
                        {
                            if(!containsAppId.contains(otherDef.getApplicationEntity().getId())){
                                List<DeferralWorkflowEntity> deferralWorkflowEntities = this.deferralWorkflowStatusRepository.getInvolvingDeferralPersons(otherDef.getApplicationEntity().getId());
                                containsAppId.add(otherDef.getApplicationEntity().getId());
                                for (DeferralWorkflowEntity deferralWorkflow : deferralWorkflowEntities) {
                                    emailService.sendEmail(deferralWorkflow.getApproverInfo(), emailTemplate.get(1), otherDef);
                                }
                                if (otherDef.getRmName() != null) {
                                    emailService.sendEmail(otherDef.getRmName(), emailTemplate.get(1), otherDef);
                                }
                            }
                        }

                        else if(Math.abs(Integer.parseInt(otherDef.getDataView())) == 2 || Math.abs(Integer.parseInt(otherDef.getDataView())) == 5)
                        {
                            if(!containsAppId.contains(otherDef.getApplicationEntity().getId())){
                                List<DeferralWorkflowEntity> deferralWorkflowEntities = this.deferralWorkflowStatusRepository.getInvolvingDeferralPersons(otherDef.getApplicationEntity().getId());
                                containsAppId.add(otherDef.getApplicationEntity().getId());
                                for (DeferralWorkflowEntity deferralWorkflow : deferralWorkflowEntities) {
                                    emailService.sendEmail(deferralWorkflow.getApproverInfo(), emailTemplate.get(2), otherDef);

                                }
                                if (otherDef.getRmName() != null) {
                                    emailService.sendEmail(otherDef.getRmName(), emailTemplate.get(2), otherDef);
                                }
                            }
                        }

                    }
                }

            }catch (Exception e){
                e.printStackTrace();
                log.error("Exception occured while sending email for Pending Deferral Data to: {} , due to: {}", e.getMessage());
            }
            return pendingDeferralData;
        }catch (Exception ex){
            ex.printStackTrace();
            log.error("Exception occured while sending email for Pending Deferral Data to: {} , due to: {}", ex.getMessage());
        }
        return null;
    }

    public Map<Integer,EmailNotificationTemplateEntity> getEmailNotificationData() {
        log.info("Arrange Email Entity and Key Value");
        String[] stage = {"-2","2","0"};
        String[] action = {"T-","T+","T0"};
        try {
            List<EmailNotificationTemplateEntity> emailEntity = emailNotificationTemplateRepository.findNotificationBasedOneActionAndStatus(action, stage);
            Map<Integer, EmailNotificationTemplateEntity> finalData = new HashMap<Integer, EmailNotificationTemplateEntity>();
            for (EmailNotificationTemplateEntity emailData : emailEntity) {
                if (emailData.getAction().equals("0")) {
                    finalData.put(0, emailData);
                } else if (emailData.getAction().equals("2")) {
                    finalData.put(1, emailData);
                } else {
                    finalData.put(2, emailData);
                }
            }
            return finalData;
        }catch (Exception e){
            log.error("Exception occured while getEmailNotificationData to: {} , due to: {}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public void initiateCpWorkFlow() throws UnsupportedEncodingException {
        log.info("Renewal CP WorkFlow");
        List<CustomerInfoEntity> customerInfoEntities = customerInfoRepository.viewExpiredCustomerDetails();
        for(CustomerInfoEntity customerInfoEntity : customerInfoEntities) {
            String status = wfApprovalStatusService.getWFStatus(customerInfoEntity.getId());
            JSONObject json = new JSONObject(status);
            Boolean flag = (Boolean) json.get("status");
            if(flag) {
                log.info("CP Renewal initiated Customer : '"+customerInfoEntity.getCustomerName()+"' | Date : "+ LocalDate.now());
                ApplicationData applicationData = new ApplicationData();
                applicationData.setType(2);
                applicationData.setAppType(2);
                applicationData.setCustId(customerInfoEntity.getId());
                ApplicationEntity applicationEntity = applicationService.saveApplicationDetails(applicationData);

                WFApprovalStatusData wfApprovalStatusData = new WFApprovalStatusData();
                wfApprovalStatusData.setAppId(applicationEntity.getId());
                wfApprovalStatusData.setStageId(35L);
                wfApprovalStatusData.setStatus(0);
                wfApprovalStatusData.setRemarks("Work In Progress");
                wfApprovalStatusData.setNextApproverInfo("CP_CPA_LEAD");
                WFApprovalStatusData wfApprovalStatusData1 = groupService.changeNextStageLeadEmail(wfApprovalStatusData);
                wfApprovalStatusData.setApproverInfo(wfApprovalStatusData1.getNextApproverInfo());

                wfApprovalStatusService.saveWFRenewalFlow(true,wfApprovalStatusData );
            }
        }
    }
}
